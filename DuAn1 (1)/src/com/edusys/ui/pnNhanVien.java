/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Auth;
import com.edusys.utils.XDate;
import com.edusys.utils.XImage;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
public class pnNhanVien extends javax.swing.JPanel {

    JFileChooser fileChooser = new JFileChooser("src\\com\\edusys\\images");
    NhanVienDAO nvDao = new NhanVienDAO();
    boolean hienMK = true;
    boolean SXNV = true;
    int row = -1;
    int MaTS;

    /**
     * Creates new form pnNhanVien
     */
    public pnNhanVien() {
        initComponents();
        init();
    }

    void init() {
        row = -1;
        filltable();
        updateStatus();
    }

    private void timKiem() {
        row = -1;
        filltable();
        clearForm();
        updateStatus();
    }

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMa.getText());
        nv.setHoTen(txtTenNV.getText());
        nv.setNgaySinh(dcNgaySinh.getDate());
        nv.setSdt(txtSdt.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setVaiTro(rdoQuanLy.isSelected());
        nv.setGioiTinh(rdoNam.isSelected());
        nv.setHinh(lblHinh.getToolTipText());
        return nv;
    }

    void setForm(NhanVien nv) {
        Date ngaysinh = nv.getNgaySinh();
        txtMa.setText(nv.getMaNV());
        txtTenNV.setText(nv.getHoTen());
        txtMatKhau.setText(nv.getMatKhau());
        txtSdt.setText(nv.getSdt());
        dcNgaySinh.setDate(ngaysinh);
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
        row = -1;
        NhanVien nv = new NhanVien();
        setForm(nv);
        updateStatus();
        lblHinh.setIcon(null);
        rdoGioiTinh.clearSelection();
        rdoVaiTro.clearSelection();
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiemNV.getText();
            List<NhanVien> list = nvDao.selectByKeyword(keyWord);
            for (int i = 0; i < list.size(); i++) {
                NhanVien nv = list.get(i);
                Object[] row = {
                    i + 1,
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    XDate.toString(nv.getNgaySinh(), "dd/MM/yyyy"),
                    nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                    nv.isGioiTinh() ? "Nam" : "Nữ",};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    boolean batLoi() {
        if (txtTenNV.getText().isEmpty() && new String(txtMatKhau.getPassword()).isEmpty()
                && txtSdt.getText().isEmpty() && txtEmail.getText().isBlank()
                && txtDiaChi.getText().isEmpty() && !rdoQuanLy.isSelected() && !rdoNhanVien.isSelected()
                && !rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống thông tin");
            return false;
        }
        if (txtTenNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên nhân viên");
            return false;
        }

        String tenNV = "^[\\p{L}'][ \\p{L}'-]{0,254}[\\p{L}']$";
        if (!txtTenNV.getText().matches(tenNV)) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được thêm số hoặc ký tự đặt biệt vào");
            return false;
        }

        if (txtMatKhau.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống mật khẩu");
            return false;
        }

        String matKhau = "^[a-zA-Z0-9]{8}+$";
        if (!txtMatKhau.getText().matches(matKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được có ký tự đặt biệt và phải có đủ 8 ký tự");
            return false;
        }

        if (txtSdt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống số điện thoại");
            return false;
        }

        String soDienThoai = "^0[0-9]{9}$";
        if (!txtSdt.getText().matches(soDienThoai)) {
            JOptionPane.showMessageDialog(this, "Số điên thoại chỉ được nhập số ,chỉ nhập 10 số và số 0 ở đầu");
            return false;
        }

        if (dcNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày chưa được chọn");
            return false;
        }
        Date ngayhientai = new Date();
        Date ngaySinh = dcNgaySinh.getDate();
        if (dcNgaySinh.getDate().equals(XDate.toString(ngayhientai, "dd/MM/yyyy"))) {
            JOptionPane.showMessageDialog(this, "Ngày không được là ngày hiện tại", "Warning", JOptionPane.WARNING_MESSAGE);
            dcNgaySinh.requestFocus();
            return false;
        }
        int pheptinh = ngayhientai.getYear() - ngaySinh.getYear();
        if (pheptinh < 18) {
            JOptionPane.showMessageDialog(this, "Nhân viên phải ít nhất 18 tuổi");
            dcNgaySinh.requestFocus();
            return false;
        }

        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống email");
            return false;
        }
        String email = "^([A-Za-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if (!txtEmail.getText().matches(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ");
            return false;
        }
        if (txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống địa chỉ");
            return false;
        }
        String diaChi = "^[\\\\p{L}0-9\\\\s]+$";
        if (!txtDiaChi.getText().matches(diaChi)) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được thêm số hoặc ký tự đặt biệt vào");
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
            NhanVien nv = getForm();
            nvDao.insert(nv);
            filltable();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }

    void update() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền cập nhật nhân viên");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhân viên cần cập nhật");
        } else {
            try {
                if (batLoi() == true) {
                    NhanVien nv = getForm();
                    nvDao.update(nv);
                    filltable();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
            }
        }

    }

    void delete() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nhân viên");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhân viên cần xoá");
        } else {
            String manv = txtMa.getText();
            if (manv.equalsIgnoreCase(Auth.user.getMaNV())) {
                JOptionPane.showMessageDialog(this, "Bạn không thể xóa chính bạn");
            } else {
                int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
                if (confirmResponse == JOptionPane.YES_OPTION) {
                    try {
                        nvDao.delete(manv);
                        filltable();
                        clearForm();
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại");
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
        row = 0;
        edit();
    }

    void next() {
        if (row < tblNhanVien.getRowCount() - 1) {
            row++;
            edit();
        }
    }

    void prev() {
        if (row > 0) {
            row--;
            edit();
        }
    }

    void last() {
        row = tblNhanVien.getRowCount() - 1;
        edit();
    }

    void edit() {
        try {
            String maNV = (String) tblNhanVien.getValueAt(row, 1);
            NhanVien nv = nvDao.selectById(maNV);
            if (nv != null) {
                setForm(nv);
                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
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

    void sapXep() {
        if (SXNV == true) {
            SXNV = false;
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            try {
                List<NhanVien> list = nvDao.sapxepgiamdan();
                for (int i = 0; i < list.size(); i++) {
                    NhanVien nv = list.get(i);
                    Object[] row = {
                        i + 1,
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        XDate.toString(nv.getNgaySinh(), "dd/MM/yyyy"),
                        nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                        nv.isGioiTinh() ? "Nam" : "Nữ",};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\0buttonLen.png"));
        } else if (SXNV == false) {
            SXNV = true;
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            try {
                List<NhanVien> list = nvDao.sapxep();
                for (int i = 0; i < list.size(); i++) {
                    NhanVien nv = list.get(i);
                    Object[] row = {
                        i + 1,
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        XDate.toString(nv.getNgaySinh(), "dd/MM/yyyy"),
                        nv.isVaiTro() ? "Quản lý" : "Nhân viên",
                        nv.isGioiTinh() ? "Nam" : "Nữ",};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\0buttonXuong.png"));
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
        txtMa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
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
        dcNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtTimKiemNV = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnInfoNv.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHienMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonMatDong.png"))); // NOI18N
        lblHienMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHienMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHienMKMouseClicked(evt);
            }
        });
        pnInfoNv.add(lblHienMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 140, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Mã nhân viên*");
        pnInfoNv.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, -1));

        txtMa.setEditable(false);
        txtMa.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtMa.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pnInfoNv.add(txtMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 500, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Tên nhân viên*");
        pnInfoNv.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, -1, -1));

        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv.add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, 500, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Mật khẩu*");
        pnInfoNv.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, -1, -1));

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 500, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Số điện thoại*");
        pnInfoNv.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 110, -1, -1));

        txtSdt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv.add(txtSdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 130, 240, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Email*");
        pnInfoNv.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, -1, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 500, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Vai trò*");
        pnInfoNv.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, -1, -1));

        rdoVaiTro.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoQuanLy.setText("Quản lý");
        pnInfoNv.add(rdoQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 280, -1, -1));

        rdoVaiTro.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoNhanVien.setText("Nhân viên");
        pnInfoNv.add(rdoNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, -1, -1));

        rdoGioiTinh.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoNam.setText("Nam");
        pnInfoNv.add(rdoNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 290, -1, -1));

        rdoGioiTinh.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdoNu.setText("Nữ");
        pnInfoNv.add(rdoNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 290, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Giới tính*");
        pnInfoNv.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 270, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Hình*");
        pnInfoNv.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        btnThem.setBackground(new java.awt.Color(106, 139, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonThem.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(null);
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 130, 40));

        btnCapNhat.setBackground(new java.awt.Color(106, 139, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonCapNhat.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapNhatMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 130, 40));

        btnXoa.setBackground(new java.awt.Color(106, 139, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonXoa.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, 130, 40));

        btnLamMoi.setBackground(new java.awt.Color(106, 139, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonLamMoi.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 350, 130, 40));

        btnSapXep.setBackground(new java.awt.Color(106, 139, 255));
        btnSapXep.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSapXep.setForeground(new java.awt.Color(255, 255, 255));
        btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonXuong.png"))); // NOI18N
        btnSapXep.setText("Sắp xếp ");
        btnSapXep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSapXep.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSapXep.setIconTextGap(5);
        btnSapXep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSapXepMouseClicked(evt);
            }
        });
        pnInfoNv.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 350, 130, 40));

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
        pnInfoNv.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 355, 80, 30));

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
        pnInfoNv.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 355, 80, 30));

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
        pnInfoNv.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 355, 80, 30));

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
        pnInfoNv.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 355, 80, 30));

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });
        pnInfoNv.add(lblHinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 180, 240));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Địa chỉ*");
        pnInfoNv.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 190, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 210, 500, 40));

        dcNgaySinh.setDateFormatString("dd/MM/yyyy");
        dcNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv.add(dcNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 130, 240, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Ngày sinh*");
        pnInfoNv.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 110, -1, -1));

        add(pnInfoNv, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1560, 410));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Email", "Địa chỉ", "Ngày sinh", "Vai trò", "Giới tính"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Long.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(30);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNhanVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setMinWidth(50);
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblNhanVien.getColumnModel().getColumn(0).setMaxWidth(50);
            tblNhanVien.getColumnModel().getColumn(1).setMinWidth(90);
            tblNhanVien.getColumnModel().getColumn(1).setPreferredWidth(90);
            tblNhanVien.getColumnModel().getColumn(1).setMaxWidth(90);
            tblNhanVien.getColumnModel().getColumn(2).setMinWidth(250);
            tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(250);
            tblNhanVien.getColumnModel().getColumn(2).setMaxWidth(250);
            tblNhanVien.getColumnModel().getColumn(3).setMinWidth(120);
            tblNhanVien.getColumnModel().getColumn(3).setPreferredWidth(120);
            tblNhanVien.getColumnModel().getColumn(3).setMaxWidth(120);
            tblNhanVien.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblNhanVien.getColumnModel().getColumn(5).setPreferredWidth(10);
            tblNhanVien.getColumnModel().getColumn(6).setMinWidth(120);
            tblNhanVien.getColumnModel().getColumn(6).setPreferredWidth(10);
            tblNhanVien.getColumnModel().getColumn(6).setMaxWidth(120);
            tblNhanVien.getColumnModel().getColumn(7).setMinWidth(100);
            tblNhanVien.getColumnModel().getColumn(7).setPreferredWidth(100);
            tblNhanVien.getColumnModel().getColumn(7).setMaxWidth(100);
            tblNhanVien.getColumnModel().getColumn(8).setMinWidth(70);
            tblNhanVien.getColumnModel().getColumn(8).setPreferredWidth(70);
            tblNhanVien.getColumnModel().getColumn(8).setMaxWidth(70);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 400));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 1560, 470));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Tìm kiếm theo tên");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 450, -1, -1));

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
        add(txtTimKiemNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 470, 300, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void lblHienMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHienMKMouseClicked
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xem mật khẩu");
        } else {
            if (hienMK == true) {
                txtMatKhau.setEchoChar((char) 0);
                lblHienMK.setIcon(new ImageIcon("src\\com\\edusys\\icons\\0buttonMatMo"));
                hienMK = false;
            } else {
                txtMatKhau.setEchoChar('*');
                lblHienMK.setIcon(new ImageIcon("src\\com\\edusys\\icons\\0buttonMatDong"));
                hienMK = true;
            }
        }
    }//GEN-LAST:event_lblHienMKMouseClicked

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        if (batLoi() == true) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\MaTuSinh\\MANV.dat"))) {
                MaTS = dis.readInt();
                MaTS += 1;
                if (MaTS < 10) {
                    txtMa.setText(String.valueOf("NV00" + MaTS));
                } else if (MaTS < 100) {
                    txtMa.setText(String.valueOf("NV0" + MaTS));
                } else if (MaTS < 1000) {
                    txtMa.setText(String.valueOf("NV" + MaTS));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            insert();
            filltable();
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\MaTuSinh\\MANV.dat"))) {
                dos.writeInt(MaTS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        update();
        filltable();
        new formTrangChu().vaiTroTenMenu();
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
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
    private com.toedter.calendar.JDateChooser dcNgaySinh;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTextField txtMa;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiemNV;
    // End of variables declaration//GEN-END:variables
}
