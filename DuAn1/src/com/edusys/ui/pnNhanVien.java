/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Auth;
import com.edusys.utils.XImage;
import java.awt.Color;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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
public class pnNhanVien extends javax.swing.JPanel {

    JFileChooser fileChooser = new JFileChooser("src\\com\\edusys\\images");
    int row = -1;
    NhanVienDAO NVdao = new NhanVienDAO();
    boolean hienMK = true;
    private JPanel moDoiMatKhau;
    boolean SXNV = true;
    int MaNVTS = 0;
    String MANV;

    /**
     * Creates new form pnNhanVien
     */
    public pnNhanVien() {
        initComponents();
        filltable();
        init();
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

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setHoTen(txtTenNV.getText());
        nv.setSdt(txtSoDienThoai.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setVaiTro(rdoQuanLy.isSelected());
        nv.setGioiTinh(rdoNam.isSelected());
        nv.setHinh(lblHinh.getToolTipText());
        return nv;
    }

    void setForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtTenNV.setText(nv.getHoTen());
        txtMatKhau.setText(nv.getMatKhau());
        txtSoDienThoai.setText(nv.getSdt());
        txtEmail.setText(nv.getEmail());
        txtDiaChi.setText(nv.getDiaChi());
        rdoQuanLy.setSelected(nv.isVaiTro());
        rdoNhanVien.setSelected(!nv.isVaiTro());
        rdoNam.setSelected(nv.isGioiTinh());
        rdoNu.setSelected(!nv.isGioiTinh());
        if (nv.getHinh() != null) {
            lblHinh.setToolTipText(nv.getHinh());
            lblHinh.setIcon(XImage.read(nv.getHinh()));
            Icon i = lblHinh.getIcon();
            ImageIcon icon = (ImageIcon) i;
            Image image = icon.getImage().getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), Image.SCALE_SMOOTH);
            lblHinh.setIcon(new ImageIcon(image));
        }
    }

    void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiemNV.getText();
            List<NhanVien> list = NVdao.selectByKeyword(keyWord);
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                    nv.isGioiTinh() ? "Nam" : "Nữ",};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    boolean batLoi() {
        NhanVien nv = new NhanVien();
        if (txtTenNV.getText().isEmpty() && new String(txtMatKhau.getPassword()).isEmpty()
                && txtSoDienThoai.getText().isEmpty() && txtEmail.getText().isBlank()
                && txtDiaChi.getText().isEmpty() && !rdoQuanLy.isSelected() && !rdoNhanVien.isSelected()
                && !rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống");
            return false;
        }
        if (txtTenNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên nhân viên");
            return false;
        }
        if (new String(txtMatKhau.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên mật khẩu");
            return false;
        }
        if (txtSoDienThoai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên só điện thoại");
            return false;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên email");
            return false;
        }
        if (txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên dia chi");
            return false;
        }
        if (!rdoQuanLy.isSelected() && !rdoNhanVien.isSelected()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn vai trò");
            return false;
        }
        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
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
                NhanVien nv = getForm();
                NVdao.insert(nv);
                this.filltable();
                this.clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nhân viên này");
        } else {
            String manv = txtMaNV.getText();
            if (manv.equalsIgnoreCase(Auth.user.getMaNV())) {
                JOptionPane.showMessageDialog(this, "bạn không thể xóa chính bạn");
            } else {
                int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
                if (confirmResponse == JOptionPane.YES_OPTION) {
                    try {
                        NVdao.delete(manv);
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
        NhanVien nv = getForm();
        try {
            NVdao.update(nv);
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
        if (this.row < tblNhanVien.getRowCount() - 1) {
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
        this.row = tblNhanVien.getRowCount() - 1;
        this.edit();
    }

    void edit() {
        try {
            String maNV = (String) tblNhanVien.getValueAt(row, 0);
            NhanVien nv = NVdao.selectById(maNV);
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
        boolean last = (row == tblNhanVien.getRowCount() - 1);
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
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            try {
                List<NhanVien> list = NVdao.sapxepgiamdan();
                for (NhanVien nv : list) {
                    Object[] row = {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                        nv.isGioiTinh() ? "Nam" : "Nữ",};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-len24trang.png"));
        } else if (SXNV == false) {
            SXNV = true;
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            try {
                List<NhanVien> list = NVdao.sapxep();
                for (NhanVien nv : list) {
                    Object[] row = {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                        nv.isGioiTinh() ? "Nam" : "Nữ",};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
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

        rdoVaiTro = new javax.swing.ButtonGroup();
        rdoGioiTinh = new javax.swing.ButtonGroup();
        pnInfoNv = new javax.swing.JPanel();
        lblHienMK = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        rdoQuanLy = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
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
        txtDiaChi = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtTimKiemNV = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(null);

        pnInfoNv.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv.setLayout(null);

        lblHienMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-closed-eye-24.png"))); // NOI18N
        lblHienMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHienMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHienMKMouseClicked(evt);
            }
        });
        pnInfoNv.add(lblHienMK);
        lblHienMK.setBounds(840, 140, 24, 24);

        jLabel6.setText("Mã nhân viên");
        pnInfoNv.add(jLabel6);
        jLabel6.setBounds(370, 30, 72, 16);

        txtMaNV.setEditable(false);
        txtMaNV.setEnabled(false);
        txtMaNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMaNVMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtMaNVMouseExited(evt);
            }
        });
        pnInfoNv.add(txtMaNV);
        txtMaNV.setBounds(370, 50, 500, 40);

        jLabel7.setText("Tên nhân viên");
        pnInfoNv.add(jLabel7);
        jLabel7.setBounds(970, 30, 74, 16);
        pnInfoNv.add(txtTenNV);
        txtTenNV.setBounds(970, 50, 500, 40);

        jLabel8.setText("Mật khẩu");
        pnInfoNv.add(jLabel8);
        jLabel8.setBounds(370, 110, 50, 16);
        pnInfoNv.add(txtMatKhau);
        txtMatKhau.setBounds(370, 130, 500, 40);

        jLabel9.setText("Số điện thoại");
        pnInfoNv.add(jLabel9);
        jLabel9.setBounds(970, 110, 69, 16);
        pnInfoNv.add(txtSoDienThoai);
        txtSoDienThoai.setBounds(970, 130, 500, 40);

        jLabel10.setText("Email");
        pnInfoNv.add(jLabel10);
        jLabel10.setBounds(370, 190, 29, 16);
        pnInfoNv.add(txtEmail);
        txtEmail.setBounds(370, 210, 500, 40);

        jLabel12.setText("Vai trò");
        pnInfoNv.add(jLabel12);
        jLabel12.setBounds(370, 260, 34, 16);

        rdoVaiTro.add(rdoQuanLy);
        rdoQuanLy.setText("Quản lý");
        pnInfoNv.add(rdoQuanLy);
        rdoQuanLy.setBounds(370, 280, 67, 25);

        rdoVaiTro.add(rdoNhanVien);
        rdoNhanVien.setText("Nhân viên");
        pnInfoNv.add(rdoNhanVien);
        rdoNhanVien.setBounds(440, 280, 79, 25);

        rdoGioiTinh.add(rdoNam);
        rdoNam.setText("Nam");
        pnInfoNv.add(rdoNam);
        rdoNam.setBounds(970, 290, 51, 25);

        rdoGioiTinh.add(rdoNu);
        rdoNu.setText("Nữ");
        pnInfoNv.add(rdoNu);
        rdoNu.setBounds(1030, 290, 41, 25);

        jLabel13.setText("Giới tính");
        pnInfoNv.add(jLabel13);
        jLabel13.setBounds(970, 270, 45, 16);

        jLabel2.setText("Hình");
        pnInfoNv.add(jLabel2);
        jLabel2.setBounds(70, 30, 26, 16);

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
        lblHinh.setBounds(70, 50, 180, 240);

        jLabel11.setText("Địa chỉ");
        pnInfoNv.add(jLabel11);
        jLabel11.setBounds(970, 190, 36, 16);
        pnInfoNv.add(txtDiaChi);
        txtDiaChi.setBounds(970, 210, 500, 40);

        add(pnInfoNv);
        pnInfoNv.setBounds(30, 30, 1560, 410);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel1.setLayout(null);

        tblNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Email", "Địa chỉ", "Vai trò", "Giới tính"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNhanVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 0, 0);

        add(jPanel1);
        jPanel1.setBounds(30, 510, 1560, 470);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tìm kiếm theo tên");
        add(jLabel14);
        jLabel14.setBounds(1290, 450, 103, 16);

        txtTimKiemNV.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemNVCaretUpdate(evt);
            }
        });
        txtTimKiemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemNVActionPerformed(evt);
            }
        });
        add(txtTimKiemNV);
        txtTimKiemNV.setBounds(1290, 470, 300, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void lblHienMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHienMKMouseClicked
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xem mật khẩu");
        } else {
            if (hienMK == true) {
                txtMatKhau.setEchoChar((char) 0);
                lblHienMK.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-eye-24 (1).png"));
                hienMK = false;
            } else {
                txtMatKhau.setEchoChar('*');
                lblHienMK.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-closed-eye-24.png"));
                hienMK = true;
            }
        }
    }//GEN-LAST:event_lblHienMKMouseClicked

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        if (batLoi() == true) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\MaTuSinh\\MANVTS.dat"))) {
                MaNVTS = dis.readInt();
                MaNVTS += 1;
                txtMaNV.setText(String.valueOf("nv0" + MaNVTS));
            } catch (IOException e) {
                e.printStackTrace();
            }
            insert();
            filltable();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\MaTuSinh\\MANVTS.dat"))) {
                dos.writeInt(MaNVTS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        update();
        filltable();
        new TrangChu().vaiTroTenMenu();
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        delete();
        filltable();
    }//GEN-LAST:event_btnXoaMouseClicked

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        clearForm();
        lblHinh.setIcon(null);
        rdoVaiTro.clearSelection();
        rdoGioiTinh.clearSelection();
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
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblHinhMousePressed

    private void txtTimKiemNVCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemNVCaretUpdate
        timKiem();
    }//GEN-LAST:event_txtTimKiemNVCaretUpdate

    private void txtTimKiemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemNVActionPerformed
        timKiem();
    }//GEN-LAST:event_txtTimKiemNVActionPerformed

    private void tblNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMousePressed
        if (evt.getClickCount() == 2) {
            row = tblNhanVien.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblNhanVienMousePressed

    private void txtMaNVMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaNVMouseEntered
        txtMaNV.setDisabledTextColor(Color.red);
        String a = txtMaNV.getText();
        MANV = a;
        txtMaNV.setText("Mã nhân viên tự động tạo không cần nhập");
    }//GEN-LAST:event_txtMaNVMouseEntered

    private void txtMaNVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaNVMouseExited
        if (txtMaNV.getText() != null) {
            txtMaNV.setText(MANV);
            txtMaNV.setDisabledTextColor(Color.black);
        } else {
            txtMaNV.setText("");
            txtMaNV.setDisabledTextColor(Color.black);
        }
    }//GEN-LAST:event_txtMaNVMouseExited


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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHienMK;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPanel pnInfoNv;
    private javax.swing.ButtonGroup rdoGioiTinh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.ButtonGroup rdoVaiTro;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiemNV;
    // End of variables declaration//GEN-END:variables
}
