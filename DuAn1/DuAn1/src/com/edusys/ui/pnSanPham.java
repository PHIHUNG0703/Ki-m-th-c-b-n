/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.SanPhamDAO;
import com.edusys.entity.SanPham;
import com.edusys.utils.Auth;
import com.edusys.utils.JdbcHelper;
import com.edusys.utils.XImage;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnSanPham extends javax.swing.JPanel {

    JFileChooser fileChooser = new JFileChooser("src\\com\\edusys\\images");
    SanPhamDAO spDao = new SanPhamDAO();
    boolean SXSP = true;
    int row = -1;
    int MaSPTS;

    /**
     * Creates new form pnSanPham
     */
    public pnSanPham() {
        initComponents();
        init();
    }

    void init() {
        row = -1;
        updateStatus();
        fillComBoBox();
        filltable();
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiemSP.getText();
            List<SanPham> list = spDao.selectByKeyword(keyWord);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getTenLoai(),
                    new DecimalFormat("###,###").format(sp.getGia()) + " VNĐ",
                    new DecimalFormat("###").format(sp.getSoLuong()),
                    sp.getDvTinh(),
                    sp.getTenNCC(),
                    sp.getGhiChu(),
                    sp.getPtGiamGia() + "%",
                    new DecimalFormat("###,###").format(sp.getTienGiamGia()) + " VNĐ",
                    new DecimalFormat("###,###").format(sp.getTongTien()) + " VNĐ",};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
            System.out.println(e);
        }
    }

    void filltableLoaiSP() {
        Object theoLoaiSP = cboTimLoai.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = String.valueOf(theoLoaiSP);
            List<SanPham> list = spDao.locTheoLoai(keyWord);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getTenLoai(),
                    new DecimalFormat("###,###").format(sp.getGia()) + " VNĐ",
                    new DecimalFormat("###").format(sp.getSoLuong()),
                    sp.getDvTinh(),
                    sp.getTenNCC(),
                    sp.getGhiChu(),
                    sp.getPtGiamGia() + "%",
                    new DecimalFormat("###,###").format(sp.getTienGiamGia()) + " VNĐ",
                    new DecimalFormat("###,###").format(sp.getTongTien()) + " VNĐ",};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
            System.out.println(e);
        }
    }

    void filltableNCC() {
        Object theoNCC = cboTimNCC.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = String.valueOf(theoNCC);
            List<SanPham> list = spDao.locTheoNhaCC(keyWord);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getTenLoai(),
                    new DecimalFormat("###,###").format(sp.getGia()) + " VNĐ",
                    new DecimalFormat("###").format(sp.getSoLuong()),
                    sp.getDvTinh(),
                    sp.getTenNCC(),
                    sp.getGhiChu(),
                    sp.getPtGiamGia() + "%",
                    new DecimalFormat("###,###").format(sp.getTienGiamGia()) + " VNĐ",
                    new DecimalFormat("###,###").format(sp.getTongTien()) + " VNĐ",};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
            System.out.println(e);
        }
    }

    void fillComBoBox() {
        String sql1 = "SELECT TENLOAI FROM LOAISANPHAM";
        String sql2 = "SELECT TENNCC FROM NHACUNGCAP";
        try {
            ResultSet rs = JdbcHelper.query(sql1);
            while (rs.next()) {
                cboTenLoai.addItem(rs.getString("TENLOAI"));
                cboTimLoai.addItem(rs.getString("TENLOAI"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            ResultSet rs = JdbcHelper.query(sql2);
            while (rs.next()) {
                cboTenNhaCungCap.addItem(rs.getString("TENNCC"));
                cboTimNCC.addItem(rs.getString("TENNCC"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void timKiem() {
        filltable();
        clearForm();
        row = -1;
        updateStatus();
    }

    void Ma() {
        String sql1 = "SELECT MALOAI FROM LOAISANPHAM WHERE TENLOAI like '" + cboTenLoai.getSelectedItem() + "'";
        String sql2 = "SELECT MANCC FROM NHACUNGCAP WHERE TENNCC like '" + cboTenNhaCungCap.getSelectedItem() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql1);
            while (rs.next()) {
                lblMaLoai.setText(rs.getString("MALOAI"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = JdbcHelper.query(sql2);
            while (rs.next()) {
                lblMaNCC.setText(rs.getString("MANCC"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SanPham getForm() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtMaSP.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setMaLoai(lblMaLoai.getText());
        sp.setTenLoai(String.valueOf(cboTenLoai.getSelectedItem()));
        sp.setTenNCC(String.valueOf(cboTenNhaCungCap.getSelectedItem()));
        sp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sp.setGia(Float.parseFloat(txtGiaBan.getText()));
        sp.setDvTinh(txtDonViTinh.getText());
        sp.setMaNCC(lblMaNCC.getText());
        sp.setGhiChu(txtGhiChu.getText());
        sp.setHinh(lblHinh.getToolTipText());
        sp.setPtGiamGia(Float.parseFloat(txtPTGiamGia.getText()));
        sp.setTienGiamGia(Float.parseFloat(txtTienGiamGia.getText()));
        sp.setTongTien(Float.parseFloat(txtTongTien.getText()));
        return sp;
    }

    void setForm(SanPham sp) {
        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSP());
        lblMaLoai.setText(sp.getMaLoai());
        cboTenLoai.setSelectedItem(tblSanPham.getValueAt(row, 2));
        cboTenNhaCungCap.setSelectedItem(tblSanPham.getValueAt(row, 6));
        txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
        txtGiaBan.setText(String.valueOf(sp.getGia()));
        txtDonViTinh.setText(sp.getDvTinh());
        lblMaNCC.setText(sp.getMaNCC());
        txtGhiChu.setText(sp.getGhiChu());
        if (sp.getHinh() != null) {
            lblHinh.setToolTipText(sp.getHinh());
            lblHinh.setIcon(XImage.read(sp.getHinh()));
            Icon i = lblHinh.getIcon();
            ImageIcon icon = (ImageIcon) i;
            Image image = icon.getImage().getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), Image.SCALE_SMOOTH);
            lblHinh.setIcon(new ImageIcon(image));
        }
        txtPTGiamGia.setText(String.valueOf(sp.getPtGiamGia()));
        txtTienGiamGia.setText(String.valueOf(sp.getTienGiamGia()));
        txtTongTien.setText(String.valueOf(sp.getTongTien()));
    }

    void clearForm() {
        row = -1;
        SanPham sp = new SanPham();
        this.setForm(sp);
        lblHinh.setIcon(new ImageIcon(""));
        updateStatus();
    }

    boolean batLoi() {
        SanPham sp = new SanPham();
        if (txtMaSP.getText().isEmpty() && txtTenSP.getText().isEmpty()
                && txtGiaBan.getText().isEmpty()
                && txtSoLuong.getText().isEmpty()
                && lblHinh.getIcon() == null) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống");
            return false;
        }
        if (txtMaSP.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên nhân viên");
            return false;
        }
        if (txtTenSP.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên só điện thoại");
            return false;
        }
        if (txtGiaBan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống giá bán");
            return false;
        }
        if (txtSoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống số lượng");
            return false;
        }
        if (txtDonViTinh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống đơn vị tính");
            return false;
        }
        if (lblHinh.getIcon() == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hình");
            return false;
        }
        return true;
    }

    void insert() {
        try {
            if (batLoi() == true) {
                SanPham sp = getForm();
                spDao.insert(sp);
                this.filltable();
                this.clearForm();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }

    void update() {
        SanPham sp = getForm();
        try {
            spDao.update(sp);
            this.filltable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
            System.out.println(e);
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nhân viên này");
        } else {
            String maSP = txtMaSP.getText();
            if (maSP.equalsIgnoreCase(Auth.user.getMaNV())) {
                JOptionPane.showMessageDialog(this, "bạn không thể xóa chính bạn");
            } else {
                int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
                if (confirmResponse == JOptionPane.YES_OPTION) {
                    try {
                        spDao.delete(maSP);
                        this.filltable();
                        this.clearForm();
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại");
                        System.out.println(e);
                    }
                }
            }
        }
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName());
            lblHinh.revalidate();
            lblHinh.repaint();
            Image image = icon.getImage().getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), Image.SCALE_SMOOTH);
            lblHinh.setIcon(new ImageIcon(image));
        }
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void next() {
        if (this.row < tblSanPham.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void last() {
        this.row = tblSanPham.getRowCount() - 1;
        this.edit();
    }

    void edit() {
        try {
            String maSP = (String) tblSanPham.getValueAt(row, 0);
            SanPham nv = spDao.selectById(maSP);
            if (nv != null) {
                setForm(nv);
                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void updateStatus() {
        boolean edit = (row >= 0);
        boolean first = (row == 0);
        boolean last = (row == tblSanPham.getRowCount() - 1);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void sapXep() {
        if (SXSP == true) {
            SXSP = false;
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0);
            try {
                List<SanPham> list = spDao.sapXepGiamDan();
                for (SanPham sp : list) {
                    Object[] row = {
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getTenLoai(),
                        new DecimalFormat("###,###").format(sp.getGia()) + " VNĐ",
                        new DecimalFormat("###").format(sp.getSoLuong()),
                        sp.getDvTinh(),
                        sp.getTenNCC(),
                        sp.getGhiChu(),
                        sp.getPtGiamGia() + "%",
                        new DecimalFormat("###,###").format(sp.getTienGiamGia()) + " VNĐ",
                        new DecimalFormat("###,###").format(sp.getTongTien()) + " VNĐ",};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-len24trang.png"));
        } else if (SXSP == false) {
            SXSP = true;
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0);
            try {
                List<SanPham> list = spDao.sapXepTangDan();
                for (SanPham sp : list) {
                    Object[] row = {
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getTenLoai(),
                        new DecimalFormat("###,###").format(sp.getGia()) + " VNĐ",
                        new DecimalFormat("###").format(sp.getSoLuong()),
                        sp.getDvTinh(),
                        sp.getTenNCC(),
                        sp.getGhiChu(),
                        sp.getPtGiamGia() + "%",
                        new DecimalFormat("###,###").format(sp.getTienGiamGia()) + " VNĐ",
                        new DecimalFormat("###,###").format(sp.getTongTien()) + " VNĐ",};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-xuong24trang.png"));
        }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnInfoNv = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnSapXep = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblHinh = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDonViTinh = new javax.swing.JTextField();
        txtGhiChu = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboTenNhaCungCap = new javax.swing.JComboBox<>();
        cboTenLoai = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtPTGiamGia = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTienGiamGia = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtTimKiemSP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboTimNCC = new javax.swing.JComboBox<>();
        cboTimLoai = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        lblMaLoai = new javax.swing.JLabel();
        lblMaNCC = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnInfoNv.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Mã sản phẩm");
        pnInfoNv.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, -1));

        txtMaSP.setEnabled(false);
        pnInfoNv.add(txtMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 500, 40));

        jLabel7.setText("Tên sản phẩm");
        pnInfoNv.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, -1, -1));
        pnInfoNv.add(txtTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, 500, 40));

        jLabel9.setText("Số lượng");
        pnInfoNv.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, -1, -1));

        jLabel10.setText("Giá bán");
        pnInfoNv.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 110, -1, -1));

        txtGiaBan.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtGiaBanCaretUpdate(evt);
            }
        });
        pnInfoNv.add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 130, 500, 40));

        jLabel2.setText("Hình");
        pnInfoNv.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        btnThem.setBackground(new java.awt.Color(106, 139, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/them.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(null);
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.setIconTextGap(5);
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 130, 40));

        btnCapNhat.setBackground(new java.awt.Color(106, 139, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/capnhat.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCapNhat.setIconTextGap(5);
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapNhatMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 440, 130, 40));

        btnXoa.setBackground(new java.awt.Color(106, 139, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/xoa.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.setFocusable(false);
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXoa.setIconTextGap(5);
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 440, 130, 40));

        btnLamMoi.setBackground(new java.awt.Color(106, 139, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/lammoi.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLamMoi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLamMoi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLamMoi.setIconTextGap(5);
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 440, 130, 40));

        btnSapXep.setBackground(new java.awt.Color(106, 139, 255));
        btnSapXep.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSapXep.setForeground(new java.awt.Color(255, 255, 255));
        btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-xuong24trang.png"))); // NOI18N
        btnSapXep.setText("Sắp xếp ");
        btnSapXep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSapXep.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSapXep.setIconTextGap(5);
        btnSapXep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSapXepMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 440, 130, 40));

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
        pnInfoNv.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 445, 80, 30));

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
        pnInfoNv.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 445, 80, 30));

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
        pnInfoNv.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 445, 80, 30));

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
        pnInfoNv.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 445, 80, 30));

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });
        pnInfoNv.add(lblHinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 300, 240));

        jLabel11.setText("Đơn vị tính");
        pnInfoNv.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 190, -1, -1));
        pnInfoNv.add(txtDonViTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 210, 500, 40));
        pnInfoNv.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 290, 500, 40));

        jLabel17.setText("Ghi chú");
        pnInfoNv.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 270, -1, -1));

        txtSoLuong.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSoLuongCaretUpdate(evt);
            }
        });
        pnInfoNv.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 500, 40));

        jLabel1.setText("Chọn nhà cung cấp");
        pnInfoNv.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, -1, -1));

        cboTenNhaCungCap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vui lòng chọn nhà cung cấp" }));
        pnInfoNv.add(cboTenNhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, 500, 40));

        cboTenLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vui lòng chọn loại sản phẩm" }));
        pnInfoNv.add(cboTenLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 500, 40));

        jLabel3.setText("Chọn loại");
        pnInfoNv.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, -1, -1));

        jLabel18.setText("% Giảm giá");
        pnInfoNv.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, -1, -1));

        txtPTGiamGia.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPTGiamGiaCaretUpdate(evt);
            }
        });
        pnInfoNv.add(txtPTGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, 240, 40));

        jLabel19.setText("Tiền giảm giá");
        pnInfoNv.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, -1, -1));

        txtTienGiamGia.setEditable(false);
        pnInfoNv.add(txtTienGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 370, 240, 40));

        txtTongTien.setEditable(false);
        pnInfoNv.add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 370, 500, 40));

        jLabel4.setText("Thành tiền");
        pnInfoNv.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 350, -1, -1));

        add(pnInfoNv, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1560, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Tên loại", "Giá bán", "Số lượng", "Đơn vị tính", "Tên nhà cung cấp", "Ghi chú", "% Giảm giá", "Tiền giảm giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 380));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 1560, 450));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tìm kiếm theo tên nhà cung cấp");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 540, -1, -1));

        txtTimKiemSP.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemSPCaretUpdate(evt);
            }
        });
        add(txtTimKiemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 560, 300, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Tìm kiếm theo tên sản phẩm");
        add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 540, -1, -1));

        cboTimNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vui lòng chọn nhà cung cấp cần tìm" }));
        cboTimNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTimNCCActionPerformed(evt);
            }
        });
        add(cboTimNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 560, 300, 30));

        cboTimLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vui lòng chọn tên loại cần tìm" }));
        cboTimLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTimLoaiActionPerformed(evt);
            }
        });
        add(cboTimLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 560, 300, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Tìm kiếm theo tên loại");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 540, -1, -1));

        lblMaLoai.setForeground(new java.awt.Color(255, 255, 255));
        add(lblMaLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, -1, -1));

        lblMaNCC.setForeground(new java.awt.Color(255, 255, 255));
        add(lblMaNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        Ma();
        try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\MaTuSinh\\MASPTS.dat"))) {
            MaSPTS = dis.readInt();
            MaSPTS += 1;
            txtMaSP.setText(String.valueOf("sp0" + MaSPTS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        insert();
        filltable();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\MaTuSinh\\MASPTS.dat"))) {
            dos.writeInt(MaSPTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        Ma();
        update();
        filltable();
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        Ma();
        delete();
        filltable();
    }//GEN-LAST:event_btnXoaMouseClicked

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        clearForm();
    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void btnSapXepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSapXepMouseClicked
        sapXep();
    }//GEN-LAST:event_btnSapXepMouseClicked

    private void btnFirstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFirstMouseClicked
        first();
    }//GEN-LAST:event_btnFirstMouseClicked

    private void btnPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseClicked
        prev();
    }//GEN-LAST:event_btnPrevMouseClicked

    private void btnNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseClicked
        next();
    }//GEN-LAST:event_btnNextMouseClicked

    private void btnLastMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseClicked
        last();
    }//GEN-LAST:event_btnLastMouseClicked

    private void lblHinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMousePressed
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblHinhMousePressed

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        if (evt.getClickCount() == 2) {
            row = tblSanPham.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void txtTimKiemSPCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemSPCaretUpdate
        filltable();
    }//GEN-LAST:event_txtTimKiemSPCaretUpdate

    private void cboTimNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTimNCCActionPerformed
        filltableNCC();
    }//GEN-LAST:event_cboTimNCCActionPerformed

    private void cboTimLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTimLoaiActionPerformed
        filltableLoaiSP();
    }//GEN-LAST:event_cboTimLoaiActionPerformed

    private void txtPTGiamGiaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPTGiamGiaCaretUpdate
        giamGia();
    }//GEN-LAST:event_txtPTGiamGiaCaretUpdate

    private void txtSoLuongCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSoLuongCaretUpdate
        giamGia();
    }//GEN-LAST:event_txtSoLuongCaretUpdate

    private void txtGiaBanCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtGiaBanCaretUpdate
        giamGia();
    }//GEN-LAST:event_txtGiaBanCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSapXep;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboTenLoai;
    private javax.swing.JComboBox<String> cboTenNhaCungCap;
    private javax.swing.JComboBox<String> cboTimLoai;
    private javax.swing.JComboBox<String> cboTimNCC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblMaLoai;
    private javax.swing.JLabel lblMaNCC;
    private javax.swing.JPanel pnInfoNv;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtPTGiamGia;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTienGiamGia;
    private javax.swing.JTextField txtTimKiemSP;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
