/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.NhaCungCapDAO;
import com.edusys.entity.KhachHang;
import com.edusys.entity.NhaCungCap;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Auth;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnNhaCungCap extends javax.swing.JPanel {

    NhaCungCapDAO nccDao = new NhaCungCapDAO();
    boolean SXNCC = true;
    int row = -1;
    int MaNCCTS;

    /**
     * Creates new form pnNhaCungCap
     */
    public pnNhaCungCap() {
        initComponents();
        filltable();
        init();
    }

    void init() {
        row = -1;
        updateStatus();
        filltable();
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblNhaCungCap.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiemKH.getText();
            List<NhaCungCap> list = nccDao.selectByKeyword(keyWord);
            for (int i = 0; i < list.size(); i++) {
                NhaCungCap ncc = list.get(i);
                Object[] row = {
                    i + 1,
                    ncc.getMaNCC(),
                    ncc.getTenNCC(),
                    ncc.getDiaChi(),
                    ncc.getSDT(),
                    ncc.getEmail(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    NhaCungCap getForm() {
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC(txtMaNCC.getText());
        ncc.setTenNCC(txtTenNCC.getText());
        ncc.setDiaChi(txtDiaChi.getText());
        ncc.setSDT(txtSdt.getText());
        ncc.setEmail(txtEmail.getText());
        return ncc;
    }

    void setForm(NhaCungCap ncc) {
        txtMaNCC.setText(ncc.getMaNCC());
        txtTenNCC.setText(ncc.getTenNCC());
        txtDiaChi.setText(ncc.getDiaChi());
        txtSdt.setText(ncc.getSDT());
        txtEmail.setText(ncc.getEmail());
    }

    void clearForm() {
        row = -1;
        NhaCungCap ncc = new NhaCungCap();
        setForm(ncc);
        updateStatus();
    }

    boolean batLoi() {
        if (txtTenNCC.getText().isEmpty() && txtDiaChi.getText().isEmpty()
                && txtSdt.getText().isEmpty() && txtEmail.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống");
            return false;
        }
        if (txtTenNCC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên nhà cung cấp");
            return false;
        }

        String chinhQuy = "^[\\p{L}'][ \\p{L}'-]{0,254}[\\p{L}']$";
        if (!txtTenNCC.getText().matches(chinhQuy)) {
            JOptionPane.showMessageDialog(this, "Tên không được thêm số hoặc ký tự đặt biệt vào");
            return false;
        }

        if (txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống địa chỉ");
            return false;
        }
        if (!txtDiaChi.getText().matches(chinhQuy)) {
            JOptionPane.showMessageDialog(this, "Tên không được thêm số hoặc ký tự đặt biệt vào");
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

        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống email");
            return false;
        }
        String email = "^([A-Za-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if (!txtEmail.getText().matches(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ");
            return false;
        }

        return true;
    }

    void insert() {
        NhaCungCap ncc = getForm();
        try {
            nccDao.insert(ncc);
            filltable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    void update() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền cập nhật nhà cung cấp này");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp cần cập nhật");
        } else {
            try {
                if (batLoi() == true) {
                    NhaCungCap ncc = getForm();
                    nccDao.update(ncc);
                    filltable();
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
            }
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nhà cung cấp này");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp cần xoá");
        } else {
            String maNCC = txtMaNCC.getText();
            int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
            if (confirmResponse == JOptionPane.YES_OPTION) {
                try {
                    nccDao.delete(maNCC);
                    filltable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        }
    }

    void edit() {
        try {
            String maNCC = (String) tblNhaCungCap.getValueAt(row, 1);
            NhaCungCap ncc = nccDao.selectById(maNCC);
            if (ncc != null) {
                setForm(ncc);
                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void first() {
        row = 0;
        edit();
    }

    void next() {
        if (row < tblNhaCungCap.getRowCount() - 1) {
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
        row = tblNhaCungCap.getRowCount() - 1;
        edit();
    }

    private void timKiem() {
        filltable();
        clearForm();
        row = -1;
        updateStatus();

    }

    void updateStatus() {
        boolean edit = (row >= 0);
        boolean first = (row == 0);
        boolean last = (row == tblNhaCungCap.getRowCount() - 1);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void sapXep() {
        if (SXNCC == true) {
            SXNCC = false;
            DefaultTableModel model = (DefaultTableModel) tblNhaCungCap.getModel();
            model.setRowCount(0);
            try {
                List<NhaCungCap> list = nccDao.sapxepgiamdan();
                for (int i = 0; i < list.size(); i++) {
                    NhaCungCap ncc = list.get(i);
                    Object[] row = {
                        i + 1,
                        ncc.getMaNCC(),
                        ncc.getTenNCC(),
                        ncc.getDiaChi(),
                        ncc.getSDT(),
                        ncc.getEmail(),};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
            btnSapXep.setIcon(new ImageIcon("src\\com\\edusys\\icons\\icons8-len24trang.png"));
        } else if (SXNCC == false) {
            SXNCC = true;
            DefaultTableModel model = (DefaultTableModel) tblNhaCungCap.getModel();
            model.setRowCount(0);
            try {
                List<NhaCungCap> list = nccDao.sapxep();
                for (int i = 0; i < list.size(); i++) {
                    NhaCungCap ncc = list.get(i);
                    Object[] row = {
                        i + 1,
                        ncc.getMaNCC(),
                        ncc.getTenNCC(),
                        ncc.getDiaChi(),
                        ncc.getSDT(),
                        ncc.getEmail(),};
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

        pnInfoNv1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTenNCC = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnSapXep = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtMaNCC = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhaCungCap = new javax.swing.JTable();
        txtTimKiemKH = new javax.swing.JTextField();
        lblTimKiem = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnInfoNv1.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin nhà cung cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Mã nhà cung cấp*");
        pnInfoNv1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv1.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 500, 40));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Tên nhà cung cấp*");
        pnInfoNv1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, -1, -1));

        txtTenNCC.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv1.add(txtTenNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 70, 500, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Địa chỉ*");
        pnInfoNv1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Số điện thoại*");
        pnInfoNv1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 140, -1, -1));

        txtSdt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv1.add(txtSdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 160, 500, 40));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Email*");
        pnInfoNv1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

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
        pnInfoNv1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 130, 40));

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
        pnInfoNv1.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 130, 40));

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
        pnInfoNv1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, 130, 40));

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
        pnInfoNv1.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 130, 40));

        btnSapXep.setBackground(new java.awt.Color(106, 139, 255));
        btnSapXep.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSapXep.setForeground(new java.awt.Color(255, 255, 255));
        btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonXuong.png"))); // NOI18N
        btnSapXep.setText("Sắp xếp");
        btnSapXep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSapXep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSapXepMouseClicked(evt);
            }
        });
        pnInfoNv1.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 130, 40));

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
        pnInfoNv1.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 355, 80, 30));

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
        pnInfoNv1.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 355, 80, 30));

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
        pnInfoNv1.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 355, 80, 30));

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
        pnInfoNv1.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 355, 80, 30));

        txtMaNCC.setEditable(false);
        txtMaNCC.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtMaNCC.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pnInfoNv1.add(txtMaNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 500, 40));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        pnInfoNv1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 500, 40));

        add(pnInfoNv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1560, 410));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu nhà cung cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNhaCungCap.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblNhaCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhaCungCap.setGridColor(new java.awt.Color(255, 255, 255));
        tblNhaCungCap.setRowHeight(30);
        tblNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNhaCungCapMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhaCungCap);
        if (tblNhaCungCap.getColumnModel().getColumnCount() > 0) {
            tblNhaCungCap.getColumnModel().getColumn(0).setMinWidth(50);
            tblNhaCungCap.getColumnModel().getColumn(0).setMaxWidth(50);
            tblNhaCungCap.getColumnModel().getColumn(1).setMinWidth(120);
            tblNhaCungCap.getColumnModel().getColumn(1).setMaxWidth(120);
        }

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 400));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 1560, 470));

        txtTimKiemKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemKHCaretUpdate(evt);
            }
        });
        add(txtTimKiemKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 470, 300, 30));

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTimKiem.setText("Tìm kiếm theo tên");
        add(lblTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 450, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        if (batLoi() == true) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\MaTuSinh\\MANCCTS.dat"))) {
                MaNCCTS = dis.readInt();
                MaNCCTS += 1;
                if (MaNCCTS >= 10) {
                    txtMaNCC.setText(String.valueOf("ncc" + MaNCCTS));
                } else if (MaNCCTS < 10) {
                    txtMaNCC.setText(String.valueOf("ncc0" + MaNCCTS));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            insert();
            filltable();
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\MaTuSinh\\MANCCTS.dat"))) {
                dos.writeInt(MaNCCTS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        update();
        filltable();
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

    private void tblNhaCungCapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhaCungCapMousePressed
        if (evt.getClickCount() == 2) {
            row = tblNhaCungCap.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblNhaCungCapMousePressed

    private void txtTimKiemKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemKHCaretUpdate
        timKiem();
    }//GEN-LAST:event_txtTimKiemKHCaretUpdate


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
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnInfoNv1;
    private javax.swing.JTable tblNhaCungCap;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNCC;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTimKiemKH;
    // End of variables declaration//GEN-END:variables
}
