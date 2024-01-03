/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.LoaiSanPhamDAO;
import com.edusys.dao.NhaCungCapDAO;
import com.edusys.dao.SanPhamDAO;
import com.edusys.entity.LoaiSanPham;
import com.edusys.entity.SanPham;
import com.edusys.entity.NhaCungCap;
import com.edusys.utils.Auth;
import com.edusys.utils.XImage;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnSanPham extends javax.swing.JPanel {

    SanPhamDAO spDao = new SanPhamDAO();
    LoaiSanPhamDAO lspDao = new LoaiSanPhamDAO();
    NhaCungCapDAO nccDao = new NhaCungCapDAO();
    JFileChooser fileChooser = new JFileChooser("src\\com\\edusys\\images");
    int row = -1;
    boolean hienMK = true;
    private JPanel moDoiMatKhau;
    boolean SXNV = true;
    int MaNVTS = 0;
    String MANV;

    /**
     * Creates new form pnSanPham
     */
    public pnSanPham() {
        initComponents();
        fillComBoBoxNCC();
        fillComBoBoxLoaiSP();
        fillComBoBoxTenLoai();
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
                    sp.getSoLuong(),
                    sp.getGia(),
                    sp.getDvTinh(),
                    sp.getTenNCC(),
                    sp.getGhiChu(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
            System.out.println(e);
        }
    }

    void filltableLoaiSP() {
        Object theoLoaiSP = cboTheoLoaiSP.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = String.valueOf(theoLoaiSP);
            List<SanPham> list = spDao.selectByKeyword(keyWord);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getTenLoai(),
                    sp.getSoLuong(),
                    sp.getGia(),
                    sp.getDvTinh(),
                    sp.getTenNCC(),
                    sp.getGhiChu(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
            System.out.println(e);
        }
    }

    void filltableNCC() {
        Object theoNCC = cboTheoNCC.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = String.valueOf(theoNCC);
            List<SanPham> list = spDao.selectByKeywordncc(keyWord);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getTenLoai(),
                    sp.getSoLuong(),
                    sp.getGia(),
                    sp.getDvTinh(),
                    sp.getTenNCC(),
                    sp.getGhiChu(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
            System.out.println(e);
        }
    }

    void filltableGia() {
        Object keyWord = cboGiaSP.getSelectedItem();
        String keyWord2 = txtLocGia.getText();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord1 = String.valueOf(keyWord);
            List<SanPham> list = spDao.loctheogia(keyWord1, keyWord2);
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getTenLoai(),
                    sp.getSoLuong(),
                    sp.getGia(),
                    sp.getDvTinh(),
                    sp.getTenNCC(),
                    sp.getGhiChu(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
            System.out.println(e);
        }
    }

    void fillComBoBoxLoaiSP() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTheoLoaiSP.getModel();
        model.removeAllElements();
        try {
            List<LoaiSanPham> list = lspDao.selectAll();
            for (LoaiSanPham lsp : list) {
                model.addElement(lsp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    void fillComBoBoxNCC() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTheoNCC.getModel();
        model.removeAllElements();
        try {
            List<NhaCungCap> list = nccDao.selectAll();
            for (NhaCungCap ncc : list) {
                model.addElement(ncc);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    void fillComBoBoxTenLoai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenLoai.getModel();
        model.removeAllElements();
        try {
            List<LoaiSanPham> list = lspDao.selectAll();
            for (LoaiSanPham lsp : list) {
                model.addElement(lsp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    void init() {
        row = -1;
        updateStatus();
    }

    private void timKiem() {
        filltable();
        clearForm();
        row = -1;
        updateStatus();
    }

    SanPham getForm() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtMaSP.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setTenLoai(txtTenLoai.getText());
        sp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sp.setGia(Float.parseFloat(txtGiaBan.getText()));
        sp.setDvTinh(txtDonViTinh.getText());
        sp.setTenNCC(txtNhaCungCap.getText());
        sp.setGhiChu(txtGhiChu.getText());
        sp.setHinh(lblHinh.getToolTipText());
        return sp;
    }

    void setForm(SanPham sp) {
        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSP());
        txtTenLoai.setText(sp.getTenLoai());
        txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
        txtGiaBan.setText(String.valueOf(sp.getGia()));
        txtDonViTinh.setText(sp.getDvTinh());
        txtNhaCungCap.setText(sp.getTenNCC());
        txtGhiChu.setText(sp.getGhiChu());
        if (sp.getHinh() != null) {
            lblHinh.setToolTipText(sp.getHinh());
            lblHinh.setIcon(XImage.read(sp.getHinh()));
            Icon i = lblHinh.getIcon();
            ImageIcon icon = (ImageIcon) i;
            Image image = icon.getImage().getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), Image.SCALE_SMOOTH);
            lblHinh.setIcon(new ImageIcon(image));
        }
    }

    void clearForm() {
        SanPham sp = new SanPham();
        this.setForm(sp);
    }

//    boolean batLoi() {
//        SanPham sp = new SanPham();
//        if (txtTenSP.getText().isEmpty() && new String(txtTenLoai.getPassword()).isEmpty()
//                && txtTenLoai.getText().isEmpty() && txtGiaBan.getText().isBlank()
//                && txtDonViTinh.getText().isEmpty() && !rdoQuanLy.isSelected() && !rdoNhanVien.isSelected()
//                && !rdoNam.isSelected() && !rdoNu.isSelected()) {
//            JOptionPane.showMessageDialog(this, "Không được bỏ trống");
//            return false;
//        }
//        if (txtTenSP.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên nhân viên");
//            return false;
//        }
//        if (new String(txtTenLoai.getPassword()).isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên mật khẩu");
//            return false;
//        }
//        if (txtTenLoai.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên só điện thoại");
//            return false;
//        }
//        if (txtGiaBan.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên email");
//            return false;
//        }
//        if (txtDonViTinh.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên dia chi");
//            return false;
//        }
//        if (!rdoQuanLy.isSelected() && !rdoNhanVien.isSelected()) {
//            JOptionPane.showMessageDialog(this, "Chưa chọn vai trò");
//            return false;
//        }
//        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
//            JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
//            return false;
//        }
//        if (lblHinh.getIcon() == null) {
//            JOptionPane.showMessageDialog(this, "Chưa chọn hình");
//            return false;
//        }
//
//        return true;
//    }
    void insert() {
        try {
//            if (batLoi() == true) {
            SanPham sp = getForm();
            spDao.insert(sp);
            this.filltable();
            this.clearForm();
//            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nhân viên này");
        } else {
            String manv = txtMaSP.getText();
            if (manv.equalsIgnoreCase(Auth.user.getMaNV())) {
                JOptionPane.showMessageDialog(this, "bạn không thể xóa chính bạn");
            } else {
                int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
                if (confirmResponse == JOptionPane.YES_OPTION) {
                    try {
                        spDao.delete(manv);
                        this.filltable();
                        this.clearForm();
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại" + e.getMessage());
                    }
                }
            }
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

    void SXNV() {
        if (SXNV == true) {
            SXNV = false;
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0);
            try {
                List<SanPham> list = spDao.sapxepgiamdan();
                for (SanPham sp : list) {
                    Object[] row = {
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getTenLoai(),
                        sp.getSoLuong(),
                        sp.getGia(),
                        sp.getDvTinh(),
                        sp.getTenNCC(),
                        sp.getGhiChu(),};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-len24trang.png"));
        } else if (SXNV == false) {
            SXNV = true;
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0);
            try {
                List<SanPham> list = spDao.sapxep();
                for (SanPham sp : list) {
                    Object[] row = {
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getTenLoai(),
                        sp.getSoLuong(),
                        sp.getGia(),
                        sp.getDvTinh(),
                        sp.getTenNCC(),
                        sp.getGhiChu(),};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-xuong24trang.png"));
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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTenLoai = new javax.swing.JTextField();
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
        txtNhaCungCap = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        cboTenLoai = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtTimKiemSP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboTheoNCC = new javax.swing.JComboBox<>();
        cboTheoLoaiSP = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cboGiaSP = new javax.swing.JComboBox<>();
        txtLocGia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(null);

        pnInfoNv.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv.setLayout(null);

        jLabel6.setText("Mã sản phẩm");
        pnInfoNv.add(jLabel6);
        jLabel6.setBounds(370, 30, 72, 16);

        txtMaSP.setEditable(false);
        txtMaSP.setEnabled(false);
        txtMaSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMaSPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtMaSPMouseExited(evt);
            }
        });
        pnInfoNv.add(txtMaSP);
        txtMaSP.setBounds(370, 50, 500, 40);

        jLabel7.setText("Tên sản phẩm");
        pnInfoNv.add(jLabel7);
        jLabel7.setBounds(970, 30, 74, 16);
        pnInfoNv.add(txtTenSP);
        txtTenSP.setBounds(970, 50, 500, 40);

        jLabel8.setText("Tên loại");
        pnInfoNv.add(jLabel8);
        jLabel8.setBounds(370, 110, 41, 16);

        jLabel9.setText("Số lượng");
        pnInfoNv.add(jLabel9);
        jLabel9.setBounds(970, 110, 47, 16);
        pnInfoNv.add(txtTenLoai);
        txtTenLoai.setBounds(370, 130, 240, 40);

        jLabel10.setText("Giá bán");
        pnInfoNv.add(jLabel10);
        jLabel10.setBounds(370, 190, 40, 16);
        pnInfoNv.add(txtGiaBan);
        txtGiaBan.setBounds(370, 210, 500, 40);

        jLabel2.setText("Hình");
        pnInfoNv.add(jLabel2);
        jLabel2.setBounds(40, 30, 26, 16);

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
        pnInfoNv.add(btnThem);
        btnThem.setBounds(80, 350, 100, 30);

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
        pnInfoNv.add(btnCapNhat);
        btnCapNhat.setBounds(210, 350, 100, 30);

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
        pnInfoNv.add(btnXoa);
        btnXoa.setBounds(340, 350, 100, 30);

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
        pnInfoNv.add(btnLamMoi);
        btnLamMoi.setBounds(470, 350, 100, 30);

        btnSapXep.setBackground(new java.awt.Color(106, 139, 255));
        btnSapXep.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSapXep.setForeground(new java.awt.Color(255, 255, 255));
        btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-xuong24trang.png"))); // NOI18N
        btnSapXep.setText("Sắp xếp ");
        btnSapXep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSapXep.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSapXep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSapXepMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnSapXep);
        btnSapXep.setBounds(600, 350, 130, 30);

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
        pnInfoNv.add(btnFirst);
        btnFirst.setBounds(1030, 350, 80, 30);

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
        pnInfoNv.add(btnPrev);
        btnPrev.setBounds(1150, 350, 80, 30);

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
        pnInfoNv.add(btnNext);
        btnNext.setBounds(1270, 350, 80, 30);

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
        pnInfoNv.add(btnLast);
        btnLast.setBounds(1390, 350, 80, 30);

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });
        pnInfoNv.add(lblHinh);
        lblHinh.setBounds(40, 50, 300, 240);

        jLabel11.setText("Đơn vị tính");
        pnInfoNv.add(jLabel11);
        jLabel11.setBounds(970, 190, 58, 16);
        pnInfoNv.add(txtDonViTinh);
        txtDonViTinh.setBounds(970, 210, 500, 40);
        pnInfoNv.add(txtNhaCungCap);
        txtNhaCungCap.setBounds(370, 290, 500, 40);

        jLabel12.setText("Nhà cung cấp");
        pnInfoNv.add(jLabel12);
        jLabel12.setBounds(370, 270, 74, 16);
        pnInfoNv.add(txtGhiChu);
        txtGhiChu.setBounds(970, 290, 500, 40);

        jLabel17.setText("Ghi chú");
        pnInfoNv.add(jLabel17);
        jLabel17.setBounds(970, 270, 41, 16);
        pnInfoNv.add(txtSoLuong);
        txtSoLuong.setBounds(970, 130, 500, 40);

        cboTenLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenLoaiActionPerformed(evt);
            }
        });
        pnInfoNv.add(cboTenLoai);
        cboTenLoai.setBounds(630, 130, 240, 40);

        add(pnInfoNv);
        pnInfoNv.setBounds(30, 30, 1560, 410);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel1.setLayout(null);

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Tên loại", "Số lượng", "Giá bán", "Đơn vị tính", "Tên nhà cung cấp", "Ghi chú"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 40, 1500, 400);

        add(jPanel1);
        jPanel1.setBounds(30, 510, 1560, 470);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tìm kiếm theo tên nhà cung cấp");
        add(jLabel14);
        jLabel14.setBounds(610, 450, 178, 16);

        txtTimKiemSP.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemSPCaretUpdate(evt);
            }
        });
        add(txtTimKiemSP);
        txtTimKiemSP.setBounds(1290, 470, 300, 30);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Tìm kiếm theo tên sản phẩm");
        add(jLabel15);
        jLabel15.setBounds(1290, 450, 158, 16);

        cboTheoNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTheoNCCActionPerformed(evt);
            }
        });
        add(cboTheoNCC);
        cboTheoNCC.setBounds(610, 470, 300, 30);

        cboTheoLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTheoLoaiSPActionPerformed(evt);
            }
        });
        add(cboTheoLoaiSP);
        cboTheoLoaiSP.setBounds(950, 470, 300, 30);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Tìm kiếm theo tên loại");
        add(jLabel16);
        jLabel16.setBounds(950, 450, 125, 16);

        cboGiaSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ">", ">=", "=", "<", "<=" }));
        cboGiaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiaSPActionPerformed(evt);
            }
        });
        add(cboGiaSP);
        cboGiaSP.setBounds(430, 470, 44, 30);
        add(txtLocGia);
        txtLocGia.setBounds(510, 470, 60, 30);

        jLabel1.setText("Lọc theo giá bán");
        add(jLabel1);
        jLabel1.setBounds(430, 450, 88, 16);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaSPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaSPMouseEntered

    }//GEN-LAST:event_txtMaSPMouseEntered

    private void txtMaSPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaSPMouseExited

    }//GEN-LAST:event_txtMaSPMouseExited

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked

    }//GEN-LAST:event_btnThemMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked

    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked

    }//GEN-LAST:event_btnXoaMouseClicked

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        clearForm();
    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void btnSapXepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSapXepMouseClicked
        SXNV();
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

    private void cboTheoNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTheoNCCActionPerformed
        filltableNCC();
    }//GEN-LAST:event_cboTheoNCCActionPerformed

    private void cboTheoLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTheoLoaiSPActionPerformed
        filltableLoaiSP();
    }//GEN-LAST:event_cboTheoLoaiSPActionPerformed

    private void cboTenLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenLoaiActionPerformed
        Object tenLoai = cboTenLoai.getSelectedItem();
        txtTenLoai.setText(String.valueOf(tenLoai));
    }//GEN-LAST:event_cboTenLoaiActionPerformed

    private void cboGiaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiaSPActionPerformed
        Object tenLoai = cboTenLoai.getSelectedItem();
    }//GEN-LAST:event_cboGiaSPActionPerformed


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
    private javax.swing.JComboBox<String> cboGiaSP;
    private javax.swing.JComboBox<String> cboTenLoai;
    private javax.swing.JComboBox<String> cboTheoLoaiSP;
    private javax.swing.JComboBox<String> cboTheoNCC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPanel pnInfoNv;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtLocGia;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNhaCungCap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiemSP;
    // End of variables declaration//GEN-END:variables
}
