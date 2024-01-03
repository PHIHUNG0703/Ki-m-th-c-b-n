/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.HoaDonCTDAO;
import com.edusys.dao.HoaDonDAO;
import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.HoaDon;
import com.edusys.entity.HoaDonCT;
import com.edusys.utils.Auth;
import com.edusys.utils.JdbcHelper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnHoaDonduphong extends javax.swing.JPanel {

    int row = -1;
    int rowct = -1;
    HoaDonDAO hdDao = new HoaDonDAO();
    HoaDonCTDAO hdctDao = new HoaDonCTDAO();
    NhanVienDAO nvDao = new NhanVienDAO();
    int MaKHTS;

    /**
     * Creates new form pnHoaDon
     */
    public pnHoaDonduphong() {
        initComponents();
        init();
        txtTenNV.setText(Auth.user.getHoTen());
    }

    void init() {
        row = -1;
//        updateStatus();
        filltableHD();
        fillcomboboxtenKH();
        filltableHDCT2();
        fillcomboboxtenSP();
        fillcomboboxtenLoai();
    }

    void tongTien() {
        String sql = "SELECT SUM(TONGTIEN) FROM HOADONCT WHERE MAHD like '" + tblHoaDon.getValueAt(row, 0) + "'";
        System.out.println(tblHoaDon.getValueAt(row, 0));
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                txtTongTienHD.setText(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void filltableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiem.getText();
            List<HoaDon> list = hdDao.selectByKeyword(keyWord);
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getTenNV(),
                    hd.getTenKH(),
                    hd.getNgayBan(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }

    void filltableHDCT1() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonCT.getModel();
        model.setRowCount(0);
        try {
            String keyWord = String.valueOf(tblHoaDon.getValueAt(row, 0));
            List<HoaDonCT> list = hdctDao.chonhoadon(keyWord);
            for (HoaDonCT hdct : list) {
                Object[] row = {
                    hdct.getMaHDCT(),
                    hdct.getTenSP(),
                    hdct.getTenLoai(),
                    hdct.getGiaBan(),
                    hdct.getSoLuong(),
                    hdct.getDvTinh(),
                    hdct.getPtGiamGia(),
                    hdct.getTienGiamGia(),
                    hdct.getTongTien(),
                    hdct.getMaHD()};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }

    void fillcomboboxtenKH() {
        String sql = "SELECT TENKH FROM KHACHHANG";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                cboTenKH.addItem(rs.getString(1));
            }
        } catch (Exception e) {

        }
    }

    void fillcomboboxtenSP() {
        String sql = "SELECT TENSP FROM SANPHAM";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                cboTenSP.addItem(rs.getString(1));
            }
        } catch (Exception e) {

        }
    }

    void fillcomboboxtenLoai() {
        String sql = "SELECT TENLOAI FROM LOAISANPHAM";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                cboTenLoai.addItem(rs.getString(1));
            }
        } catch (Exception e) {

        }
    }

    HoaDon getForm() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(txtMaHD.getText());
        hd.setMaNV(Auth.user.getMaNV());
        hd.setTenNV(txtTenNV.getText());
        Ma();
        hd.setMaKH(lblMaKH.getText());
        hd.setTenKH(String.valueOf(cboTenKH.getSelectedItem()));
        hd.setNgayBan(dcNgayBan.getDate());
        hd.setTongTien(Float.parseFloat(txtTongTienHD.getText()));
        return hd;
    }

    void setForm(HoaDon hd) {
        Date ngaysinh = hd.getNgayBan();
        txtMaHD.setText(hd.getMaHD());
        txtTenNV.setText(Auth.user.getHoTen());
        cboTenKH.setSelectedItem(tblHoaDon.getValueAt(row, 2));
        dcNgayBan.setDate(ngaysinh);
        txtTongTienHD.setText(String.valueOf(hd.getTongTien()));
    }

    HoaDonCT getFormCT() {
        HoaDonCT hdct = new HoaDonCT();
        hdct.setMaHDCT(txtMaHDCT.getText());
        hdct.setTenSP(String.valueOf(cboTenSP.getSelectedItem()));
        hdct.setTenLoai(String.valueOf(cboTenLoai.getSelectedItem()));
        return hdct;
    }

    void setFormCT(HoaDonCT hdct) {
        txtMaHDCT.setText(hdct.getMaHDCT());
        cboTenSP.setSelectedItem(tblHoaDonCT2.getValueAt(rowct, 1));
        cboTenLoai.setSelectedItem(tblHoaDonCT2.getValueAt(rowct, 2));
    }

    void clearForm() {
        HoaDon hd = new HoaDon();
        this.setForm(hd);
    }

    void insert() {
        try {
            HoaDon hd = getForm();
            hdDao.insert(hd);
            filltableHD();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }
    int Check;

    void check() {
        String maHD = (String) tblHoaDon.getValueAt(row, 0);
        String sql = "SELECT * FROM HOADONCT WHERE MAHD LIKE '" + maHD + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                int Check = rs.getInt(1);
            }
        } catch (Exception e) {

        }
    }

    void delete() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa khách hàng này");
        } else {
            HoaDonCT hdct = new HoaDonCT();
            String maHD = txtMaHD.getText();
            int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
            if (confirmResponse == JOptionPane.YES_OPTION) {
                try {
                    if (Check == 0) {
                        hdDao.delete(maHD);
                        filltableHD();
                        clearForm();
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                    } else if (Check > 0) {
                        hdctDao.xoaHoaDonCT(maHD);
                        hdDao.delete(maHD);
                        filltableHD();
                        clearForm();
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại" + e.getMessage());
                }
            }
        }
    }

    void Ma() {
        String sql = "SELECT MAKH FROM KHACHHANG WHERE TENKH LIKE '" + cboTenKH.getSelectedItem() + "'";
        Object a = cboTenKH.getSelectedItem();
        System.out.println(a);
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                lblMaKH.setText(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void update() {
        HoaDon hd = getForm();
        try {
            hdDao.update(hd);
            filltableHD();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
            System.out.println(e);
        }
    }

    void edit() {
        try {
            String maHD = (String) tblHoaDon.getValueAt(row, 0);
            HoaDon hd = hdDao.selectById(maHD);
            if (hd != null) {
                filltableHDCT1();
                tongTien();
                setForm(hd);
//                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void editCT() {
        try {
            String maHDCT = (String) tblHoaDonCT2.getValueAt(rowct, 0);
            HoaDonCT hdct = hdctDao.selectById(maHDCT);
            System.out.println(maHDCT);
            if (hdct != null) {
                setFormCT(hdct);
//                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }

    private void timKiem() {
        filltableHD();
        clearForm();
        row = -1;
    }

    void giamGia() {
        try {
            float giaBan = Float.parseFloat(txtGiaBan.getText());
            float soLuong = Float.parseFloat(txtSoLuong.getText());
            float phanTram = Float.parseFloat(txtPTGiamGia.getText());
            if (txtPTGiamGia.getText().isEmpty()) {
                txtTienGiamGia.setText("");
                txtTongTien.setText("");
            }
            txtTienGiamGia.setText(String.valueOf(giaBan * soLuong - (giaBan * soLuong * (1 - phanTram * 0.01))));
            txtTongTien.setText(String.valueOf(giaBan * soLuong * (1 - phanTram * 0.01)));
        } catch (Exception e) {
        }
    }

    void filltableHDCT2() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonCT2.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiemHDCT.getText();
            List<HoaDonCT> list = hdctDao.selectByKeyword(keyWord);
            for (HoaDonCT hdct : list) {
                Object[] row = {
                    hdct.getMaHD(),
                    hdct.getTenSP(),
                    hdct.getTenLoai(),
                    hdct.getGiaBan(),
                    hdct.getSoLuong(),
                    hdct.getDvTinh(),
                    hdct.getPtGiamGia(),
                    hdct.getTienGiamGia(),
                    hdct.getTongTien()};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonCT = new javax.swing.JTable();
        pnInfoNv1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnSapXep = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtMaHD = new javax.swing.JTextField();
        dcNgayBan = new com.toedter.calendar.JDateChooser();
        cboTenKH = new javax.swing.JComboBox<>();
        txtTenNV = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        lblTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        lblMaKH = new javax.swing.JLabel();
        txtTongTienHD = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTimKiem1 = new javax.swing.JLabel();
        txtTimKiemHDCT = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoaDonCT2 = new javax.swing.JTable();
        pnInfoNv2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnThem1 = new javax.swing.JButton();
        btnCapNhat1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        btnLamMoi1 = new javax.swing.JButton();
        btnSapXep1 = new javax.swing.JButton();
        btnFirst1 = new javax.swing.JButton();
        btnPrev1 = new javax.swing.JButton();
        btnNext1 = new javax.swing.JButton();
        btnLast1 = new javax.swing.JButton();
        txtMaHDCT = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtTienGiamGia = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtDVTinh = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        txtPTGiamGia = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        cboTenSP = new javax.swing.JComboBox<>();
        cboTenLoai = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDonCT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblHoaDonCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn chi tiết", "Tên sản phẩm", "Tên loại", "Giá bán", "Số lượng", "Đơn vị tính", "% Giảm giá", "Tiền giảm giá", "Tổng tiền"
            }
        ));
        tblHoaDonCT.setGridColor(new java.awt.Color(255, 255, 255));
        tblHoaDonCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonCTMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDonCT);

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 210));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 740, 1560, 280));

        pnInfoNv1.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.black, java.awt.Color.white, null, null), "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Mã hóa đơn");
        pnInfoNv1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Tên nhân viên");
        pnInfoNv1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Tên khách hàng");
        pnInfoNv1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Ngày bán");
        pnInfoNv1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 140, -1, -1));

        btnThem.setBackground(new java.awt.Color(106, 139, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setBorder(null);
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 100, 30));

        btnCapNhat.setBackground(new java.awt.Color(106, 139, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapNhatMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 100, 30));

        btnXoa.setBackground(new java.awt.Color(106, 139, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xoá");
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, 100, 30));

        btnLamMoi.setBackground(new java.awt.Color(106, 139, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 100, 30));

        btnSapXep.setBackground(new java.awt.Color(106, 139, 255));
        btnSapXep.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSapXep.setForeground(new java.awt.Color(255, 255, 255));
        btnSapXep.setText("Sắp xếp");
        btnSapXep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSapXep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSapXepMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, 100, 30));

        btnFirst.setBackground(new java.awt.Color(106, 139, 255));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setText("|<");
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFirst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFirstMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 230, 80, 30));

        btnPrev.setBackground(new java.awt.Color(106, 139, 255));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setText("<");
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrevMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 230, 80, 30));

        btnNext.setBackground(new java.awt.Color(106, 139, 255));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">");
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNextMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 230, 80, 30));

        btnLast.setBackground(new java.awt.Color(106, 139, 255));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setText(">|");
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLastMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 230, 80, 30));

        txtMaHD.setEnabled(false);
        pnInfoNv1.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 500, 40));
        pnInfoNv1.add(dcNgayBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 160, 500, 40));

        pnInfoNv1.add(cboTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 500, 40));

        txtTenNV.setEditable(false);
        pnInfoNv1.add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 70, 500, 40));

        jPanel2.add(pnInfoNv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1550, 280));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Ngày bán"
            }
        ));
        tblHoaDon.setGridColor(new java.awt.Color(255, 255, 255));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 210));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 1560, 280));

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTimKiem.setText("Tìm kiếm theo tên");
        jPanel2.add(lblTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 320, -1, -1));

        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });
        jPanel2.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 340, 300, 30));

        lblMaKH.setFont(new java.awt.Font("Segoe UI", 0, 1)); // NOI18N
        jPanel2.add(lblMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, -1, -1));

        txtTongTienHD.setEditable(false);
        jPanel2.add(txtTongTienHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 690, 300, 30));

        jLabel1.setText("Tổng tiền:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 670, -1, -1));

        jTabbedPane1.addTab("Hóa đơn", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTimKiem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTimKiem1.setText("Tìm kiếm theo tên");
        jPanel3.add(lblTimKiem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 500, -1, -1));

        txtTimKiemHDCT.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemHDCTCaretUpdate(evt);
            }
        });
        jPanel3.add(txtTimKiemHDCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 520, 300, 30));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDonCT2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblHoaDonCT2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn chi tiết", "Tên sản phẩm", "Tên loại", "Giá bán", "Số lượng", "Đơn vị tính", "% Giảm giá", "Tiền giảm giá", "Tổng tiền"
            }
        ));
        tblHoaDonCT2.setGridColor(new java.awt.Color(255, 255, 255));
        tblHoaDonCT2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonCT2MousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblHoaDonCT2);

        jPanel6.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 390));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 1560, 460));

        pnInfoNv2.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.black, java.awt.Color.white, null, null), "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Mã hóa đơn chi tiết");
        pnInfoNv2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Tên sản phẩm");
        pnInfoNv2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Tên loại");
        pnInfoNv2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Giá bán");
        pnInfoNv2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 140, -1, -1));

        btnThem1.setBackground(new java.awt.Color(106, 139, 255));
        btnThem1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem1.setForeground(new java.awt.Color(255, 255, 255));
        btnThem1.setText("Thêm");
        btnThem1.setBorder(null);
        btnThem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThem1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnThem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 100, 30));

        btnCapNhat1.setBackground(new java.awt.Color(106, 139, 255));
        btnCapNhat1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCapNhat1.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat1.setText("Cập nhật");
        btnCapNhat1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapNhat1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnCapNhat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, 100, 30));

        btnXoa1.setBackground(new java.awt.Color(106, 139, 255));
        btnXoa1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnXoa1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa1.setText("Xoá");
        btnXoa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoa1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnXoa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 410, 100, 30));

        btnLamMoi1.setBackground(new java.awt.Color(106, 139, 255));
        btnLamMoi1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLamMoi1.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi1.setText("Làm mới");
        btnLamMoi1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLamMoi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoi1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnLamMoi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 100, 30));

        btnSapXep1.setBackground(new java.awt.Color(106, 139, 255));
        btnSapXep1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSapXep1.setForeground(new java.awt.Color(255, 255, 255));
        btnSapXep1.setText("Sắp xếp");
        btnSapXep1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSapXep1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSapXep1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnSapXep1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 410, 100, 30));

        btnFirst1.setBackground(new java.awt.Color(106, 139, 255));
        btnFirst1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnFirst1.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst1.setText("|<");
        btnFirst1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFirst1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFirst1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnFirst1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 410, 80, 30));

        btnPrev1.setBackground(new java.awt.Color(106, 139, 255));
        btnPrev1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnPrev1.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev1.setText("<");
        btnPrev1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrev1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnPrev1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 410, 80, 30));

        btnNext1.setBackground(new java.awt.Color(106, 139, 255));
        btnNext1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnNext1.setForeground(new java.awt.Color(255, 255, 255));
        btnNext1.setText(">");
        btnNext1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNext1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnNext1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 410, 80, 30));

        btnLast1.setBackground(new java.awt.Color(106, 139, 255));
        btnLast1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLast1.setForeground(new java.awt.Color(255, 255, 255));
        btnLast1.setText(">|");
        btnLast1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLast1MouseClicked(evt);
            }
        });
        pnInfoNv2.add(btnLast1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 410, 80, 30));

        txtMaHDCT.setEnabled(false);
        pnInfoNv2.add(txtMaHDCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 500, 40));

        txtGiaBan.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtGiaBanCaretUpdate(evt);
            }
        });
        pnInfoNv2.add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 160, 500, 40));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Số lượng");
        pnInfoNv2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        txtSoLuong.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSoLuongCaretUpdate(evt);
            }
        });
        pnInfoNv2.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 500, 40));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Tiền giảm giá");
        pnInfoNv2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, -1, -1));
        pnInfoNv2.add(txtTienGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, 240, 40));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Đơn vị tính");
        pnInfoNv2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 230, -1, -1));
        pnInfoNv2.add(txtDVTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 250, 500, 40));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Thành tiền");
        pnInfoNv2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 320, -1, -1));
        pnInfoNv2.add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 340, 500, 40));

        txtPTGiamGia.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPTGiamGiaCaretUpdate(evt);
            }
        });
        pnInfoNv2.add(txtPTGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 240, 40));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("% Giảm giá");
        pnInfoNv2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, -1, -1));

        pnInfoNv2.add(cboTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 70, 500, 40));

        pnInfoNv2.add(cboTenLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 500, 40));

        jPanel3.add(pnInfoNv2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1550, 460));

        jTabbedPane1.addTab("Hóa đơn chi tiết", jPanel3);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1620, 1080));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1620, 1080));
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonCT2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonCT2MousePressed
        if (evt.getClickCount() == 2) {
            rowct = tblHoaDonCT2.getSelectedRow();
            editCT();
        }
    }//GEN-LAST:event_tblHoaDonCT2MousePressed

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        timKiem();
    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMousePressed
        if (evt.getClickCount() == 2) {
            row = tblHoaDon.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblHoaDonMousePressed

    private void btnLastMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseClicked
        //        last();
    }//GEN-LAST:event_btnLastMouseClicked

    private void btnNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseClicked
        //        next();
    }//GEN-LAST:event_btnNextMouseClicked

    private void btnPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseClicked
        //        prev();
    }//GEN-LAST:event_btnPrevMouseClicked

    private void btnFirstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFirstMouseClicked
        //        first();
    }//GEN-LAST:event_btnFirstMouseClicked

    private void btnSapXepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSapXepMouseClicked
        //        SXKH();
    }//GEN-LAST:event_btnSapXepMouseClicked

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        clearForm();
    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        delete();
        tongTien();
    }//GEN-LAST:event_btnXoaMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        Ma();
        update();
        tongTien();
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\MaTuSinh\\MAHDTS.dat"))) {
            MaKHTS = dis.readInt();
            MaKHTS += 1;
            txtMaHD.setText(String.valueOf("hd0" + MaKHTS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        insert();
        filltableHD();
        tongTien();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\MaTuSinh\\MAHDTS.dat"))) {
            dos.writeInt(MaKHTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void tblHoaDonCTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonCTMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonCTMousePressed

    private void btnLast1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLast1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLast1MouseClicked

    private void btnNext1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNext1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNext1MouseClicked

    private void btnPrev1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrev1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrev1MouseClicked

    private void btnFirst1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFirst1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFirst1MouseClicked

    private void btnSapXep1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSapXep1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSapXep1MouseClicked

    private void btnLamMoi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoi1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoi1MouseClicked

    private void btnXoa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoa1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoa1MouseClicked

    private void btnCapNhat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhat1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhat1MouseClicked

    private void btnThem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThem1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThem1MouseClicked

    private void txtGiaBanCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtGiaBanCaretUpdate
        giamGia();
    }//GEN-LAST:event_txtGiaBanCaretUpdate

    private void txtSoLuongCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSoLuongCaretUpdate
        giamGia();
    }//GEN-LAST:event_txtSoLuongCaretUpdate

    private void txtPTGiamGiaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPTGiamGiaCaretUpdate
        giamGia();
    }//GEN-LAST:event_txtPTGiamGiaCaretUpdate

    private void txtTimKiemHDCTCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemHDCTCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemHDCTCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnCapNhat1;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirst1;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoi1;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLast1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNext1;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrev1;
    private javax.swing.JButton btnSapXep;
    private javax.swing.JButton btnSapXep1;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JComboBox<String> cboTenKH;
    private javax.swing.JComboBox<String> cboTenLoai;
    private javax.swing.JComboBox<String> cboTenSP;
    private com.toedter.calendar.JDateChooser dcNgayBan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JLabel lblTimKiem1;
    private javax.swing.JPanel pnInfoNv1;
    private javax.swing.JPanel pnInfoNv2;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonCT;
    private javax.swing.JTable tblHoaDonCT2;
    private javax.swing.JTextField txtDVTinh;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaHDCT;
    private javax.swing.JTextField txtPTGiamGia;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTienGiamGia;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiemHDCT;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongTienHD;
    // End of variables declaration//GEN-END:variables
}
