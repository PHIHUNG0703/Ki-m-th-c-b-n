/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.HoaDonCTDAO;
import com.edusys.dao.HoaDonDAO;
import com.edusys.dao.SanPhamDAO;
import com.edusys.entity.HoaDon;
import com.edusys.entity.HoaDonCT;
import com.edusys.entity.NhanVien;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnBanHang extends javax.swing.JPanel {

    HoaDonDAO hdDao = new HoaDonDAO();
    HoaDonCTDAO hdctDao = new HoaDonCTDAO();

    int row = 0;

    /**
     * Creates new form pnHoaDon
     */
    public pnBanHang() {
        initComponents();
        init();
    }

    void init() {
        row = 0;
        fillComBoBoxTen();
        filltableHD();
    }

    void tongTien() {
        String sql = "SELECT SUM(TONGTIEN) FROM HOADONCT WHERE MAHD like '" + txtMaHD.getText() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                txtThanhTien.setText(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void edit() {
        try {
            String maHD = (String) tblHoaDon.getValueAt(row, 1);
            HoaDon hd = hdDao.selectById(maHD);
            if (hd != null) {
                filltableHDCT();
                tongTien();
                setForm(hd);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void editCT() {
        try {
            String maHDCT = (String) tblSanPham.getValueAt(row, 1);
            HoaDonCT hdct = hdctDao.selectById(maHDCT);
            if (hdct != null) {
                setFormCT(hdct);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void filltableHDCT() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = String.valueOf(tblHoaDon.getValueAt(row, 1));
            List<HoaDonCT> list = hdctDao.chonhoadon(keyWord);
            for (int i = 0; i < list.size(); i++) {
                HoaDonCT hdct = list.get(i);
                Object[] row = {
                    i + 1,
                    hdct.getMaHDCT(),
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

    void filltableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
//            String keyWord = txtTimKiem.getText();
            List<HoaDon> list = hdDao.selectAll();
            for (int i = 0; i < list.size(); i++) {
                HoaDon hd = list.get(i);
                Object[] row = {
                    i + 1,
                    hd.getMaHD(),
                    hd.getTenNV(),
                    hd.getTenKH(),
                    hd.getNgayBan()};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }

    void fillComBoBoxTen() {
        String sql1 = "SELECT MAKH FROM KHACHHANG";
        String sql2 = "SELECT MANV FROM NHANVIEN";
        String sql3 = "SELECT TENSP FROM SANPHAM";
        try {
            ResultSet rs = JdbcHelper.query(sql1);
            while (rs.next()) {
                cboMaKH.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            ResultSet rs = JdbcHelper.query(sql2);
            while (rs.next()) {
                cboMaNV.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            ResultSet rs = JdbcHelper.query(sql3);
            while (rs.next()) {
                cboTenSP.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void LayDuLieu() {
        String sql1 = "SELECT TENKH, SDT, DIACHI FROM KHACHHANG WHERE MAKH LIKE '" + cboMaKH.getSelectedItem() + "'";
        String sql2 = "SELECT TENNV FROM NHANVIEN WHERE MANV LIKE '" + cboMaNV.getSelectedItem() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql1);
            while (rs.next()) {
                txtTenKH.setText(rs.getString("TENKH"));
                txtSdt.setText(rs.getString("SDT"));
                txtDiaChi.setText(rs.getString("DIACHI"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            ResultSet rs = JdbcHelper.query(sql2);
            while (rs.next()) {
                txtTenNV.setText(rs.getString("TENNV"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    HoaDon getForm() {
        HoaDon hd = new HoaDon();
        hd.setMaNV(String.valueOf(cboMaNV.getSelectedItem()));
        hd.setMaKH(String.valueOf(cboMaKH.getSelectedItem()));
        hd.setTongTien(Float.parseFloat(txtThanhTien.getText()));
        System.out.println(hd.getMaKH());
        System.out.println(hd.getMaNV());
        return hd;
    }

    void setForm(HoaDon hd) {
        txtMaHD.setText(hd.getMaHD());
        txtTenNV.setText((String) tblHoaDon.getValueAt(row, 2));
        txtTenKH.setText((String) tblHoaDon.getValueAt(row, 3));
        txtNgayBan.setText(String.valueOf(hd.getNgayBan()));
        txtThanhTien.setText(String.valueOf(hd.getTongTien()));
    }

    HoaDonCT getFormCT() {
        HoaDonCT hdct = new HoaDonCT();
        String sql = "SELECT MASP,A.MALOAI,B.TENLOAI,GIABAN,DVTINH,PTGIAMGIA,TIENGIAMGIA,TONGTIEN "
                + "FROM SANPHAM A "
                + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI "
                + "WHERE TENSP LIKE '" + cboTenSP.getSelectedItem() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                hdct.setMaHD((String) tblHoaDon.getValueAt(row, 1));
                hdct.setMaSP(rs.getString("MASP"));
                hdct.setTenSP(String.valueOf(cboTenSP.getSelectedItem()));
                hdct.setMaLoai(rs.getString("MALOAI"));
                hdct.setTenLoai(rs.getString("TENLOAI"));
                hdct.setGiaBan(rs.getFloat("GIABAN"));
                hdct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                hdct.setDvTinh(rs.getString("DVTINH"));
                hdct.setPtGiamGia(rs.getFloat("PTGIAMGIA"));
                hdct.setTienGiamGia(rs.getFloat("TIENGIAMGIA"));
                hdct.setTongTien(rs.getFloat("TONGTIEN"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return hdct;
    }

    void setFormCT(HoaDonCT hdct) {
        cboTenSP.setSelectedItem(tblSanPham.getValueAt(row, 2));
        txtSoLuong.setText(String.valueOf(hdct.getSoLuong()));
        txtThanhTien.setText(String.valueOf(hdct.getTongTien()));
    }

    void clearForm() {
        HoaDon hd = new HoaDon();
        setForm(hd);
    }

    void clearFormCT() {
        HoaDonCT hdct = new HoaDonCT();
        setFormCT(hdct);
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

    void insertCT() {
        try {
            HoaDonCT hdct = getFormCT();
            hdctDao.insert(hdct);
            filltableHDCT();
            clearFormCT();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        cboMaKH = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtNgayBan = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cboMaNV = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblTongTien123 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cboTenSP = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        txtThanhTien = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin khách hàng"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Tên khách hàng");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel3.setText("Mã khách hàng");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));
        jPanel1.add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 200, 40));

        jLabel4.setText("Số điện thoại");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, -1, -1));
        jPanel1.add(txtSdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, 200, 40));

        cboMaKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vui lòng chọn mã khách hàng" }));
        cboMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaKHActionPerformed(evt);
            }
        });
        jPanel1.add(cboMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 200, 40));

        jButton3.setText("Tạo hóa đơn");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 140, -1, -1));

        jLabel14.setText("Ngày bán");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));
        jPanel1.add(txtNgayBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 200, 40));

        jLabel15.setText("Mã hóa đơn");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, -1, -1));
        jPanel1.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 200, 40));

        jLabel16.setText("Mã nhân viên");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, -1, -1));

        cboMaNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vui lòng chọn mã nhân viên" }));
        cboMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNVActionPerformed(evt);
            }
        });
        jPanel1.add(cboMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 200, 40));

        jLabel17.setText("Tên nhân viên");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, -1, -1));
        jPanel1.add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 200, 40));

        jLabel7.setText("Mã sản phẩm");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, -1, -1));

        lblTongTien123.setFont(new java.awt.Font("Segoe UI", 0, 1)); // NOI18N
        lblTongTien123.setText("0");
        jPanel1.add(lblTongTien123, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, -1, -1));
        jPanel1.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 130, 200, 40));

        jLabel2.setText("Địa chỉ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 110, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 1130, 210));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setText("Số lượng");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        cboTenSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vui lòng chọn sản phẩm" }));
        cboTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenSPActionPerformed(evt);
            }
        });
        jPanel5.add(cboTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 390, 50));

        jButton4.setText("Thêm sản phẩm");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel5.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, -1, -1));

        jLabel21.setText("Tiền khách trả");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel22.setText("Tiền thối");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));
        jPanel5.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 390, 40));
        jPanel5.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 390, 40));

        jLabel20.setText("Thành tiền");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));
        jPanel5.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 390, 40));
        jPanel5.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 390, 40));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 260, 420, 660));

        tblSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HDCT", "Tên sản phẩm", "Tên loại", "Giá bán", "Số lượng", "Đơn vị tính", "%  Giảm giá", "Tiền giảm giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 700, 670));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Ngày bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDon);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 450, 890));
    }// </editor-fold>//GEN-END:initComponents

    private void cboMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaKHActionPerformed
        LayDuLieu();
    }//GEN-LAST:event_cboMaKHActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        insert();
    }//GEN-LAST:event_jButton3MouseClicked

    private void cboMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNVActionPerformed
        LayDuLieu();
    }//GEN-LAST:event_cboMaNVActionPerformed

    private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMousePressed
        if (evt.getClickCount() == 2) {
            row = tblHoaDon.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblHoaDonMousePressed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        insertCT();
    }//GEN-LAST:event_jButton4MouseClicked

    private void cboTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenSPActionPerformed
//        layDuLieuSP();
    }//GEN-LAST:event_cboTenSPActionPerformed

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        if (evt.getClickCount() == 2) {
            row = tblSanPham.getSelectedRow();
            editCT();
        }
    }//GEN-LAST:event_tblSanPhamMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboMaKH;
    private javax.swing.JComboBox<String> cboMaNV;
    private javax.swing.JComboBox<String> cboTenSP;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JLabel lblTongTien123;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtNgayBan;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtThanhTien;
    // End of variables declaration//GEN-END:variables
}
