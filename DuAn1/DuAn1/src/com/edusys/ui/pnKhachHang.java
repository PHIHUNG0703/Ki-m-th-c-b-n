/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.KhachHangDAO;
import com.edusys.entity.KhachHang;
import com.edusys.utils.Auth;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnKhachHang extends javax.swing.JPanel {

    KhachHangDAO khDao = new KhachHangDAO();
    boolean SXKH = true;
    int row = -1;
    int MaKHTS;

    /**
     * Creates new form pnKhachHang
     */
    public pnKhachHang() {
        initComponents();
        init();

    }

    void init() {
        row = -1;
        updateStatus();
        filltable();
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiemKH.getText();
            List<KhachHang> list = khDao.selectByKeyword(keyWord);
            for (KhachHang kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getSdt(),
                    kh.getDiaChi(),
                    kh.getGhiChu(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setMaKH(txtMaKH.getText());
        kh.setTenKH(txtTenKH.getText());
        kh.setSdt(txtSdt.getText());
        kh.setDiaChi(txtDiaChi.getText());
        kh.setGhiChu(txtGhiChu.getText());
        return kh;
    }

    void setForm(KhachHang kh) {
        txtMaKH.setText(kh.getMaKH());
        txtTenKH.setText(kh.getTenKH());
        txtSdt.setText(kh.getSdt());
        txtDiaChi.setText(kh.getDiaChi());
        txtGhiChu.setText(kh.getGhiChu());
    }

    void clearForm() {
        KhachHang kh = new KhachHang();
        this.setForm(kh);
    }

    boolean batLoi() {
        if (txtTenKH.getText().isEmpty() && txtSdt.getText().isEmpty()
                && txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống");
            return false;
        }
        if (txtTenKH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống tên khách hàng");
            return false;
        }
        if (txtSdt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống mật khẩu");
            return false;
        }
        if (txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống số điện thoại");
            return false;
        }
        return true;
    }

    void insert() {
        try {
            KhachHang kh = getForm();
            khDao.insert(kh);
            this.filltable();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa khách hàng này");
        } else {
            String makh = txtMaKH.getText();
            int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
            if (confirmResponse == JOptionPane.YES_OPTION) {
                try {
                    khDao.delete(makh);
                    this.filltable();
                    this.clearForm();
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại" + e.getMessage());
                }
            }
        }
    }

    void update() {
        KhachHang kh = getForm();
        try {
            khDao.update(kh);
            this.filltable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    void edit() {
        try {
            String maKH = (String) tblKhachHang.getValueAt(row, 0);
            KhachHang kh = khDao.selectById(maKH);
            if (kh != null) {
                setForm(kh);
                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void next() {
        if (this.row < tblKhachHang.getRowCount() - 1) {
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
        this.row = tblKhachHang.getRowCount() - 1;
        this.edit();
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
        boolean last = (row == tblKhachHang.getRowCount() - 1);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void SXKH() {
        if (SXKH == true) {
            SXKH = false;
            DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
            model.setRowCount(0);
            try {
                List<KhachHang> list = khDao.sapxepgiamdan();
                for (KhachHang kh : list) {
                    Object[] row = {
                        kh.getMaKH(),
                        kh.getTenKH(),
                        kh.getSdt(),
                        kh.getDiaChi(),
                        kh.getGhiChu(),};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
        } else if (SXKH == false) {
            SXKH = true;
            DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
            model.setRowCount(0);
            try {
                List<KhachHang> list = khDao.sapxep();
                for (KhachHang kh : list) {
                    Object[] row = {
                        kh.getMaKH(),
                        kh.getTenKH(),
                        kh.getSdt(),
                        kh.getDiaChi(),
                        kh.getGhiChu(),};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
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
        txtSdt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
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
        txtMaKH = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        txtTimKiemKH = new javax.swing.JTextField();
        lblTimKiem = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnInfoNv1.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.black, java.awt.Color.white, null, null), "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Mã khách hàng");
        pnInfoNv1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));
        pnInfoNv1.add(txtSdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 500, 40));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Tên khách hàng");
        pnInfoNv1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, -1, -1));
        pnInfoNv1.add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 70, 500, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Số điện thoại");
        pnInfoNv1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Địa chỉ");
        pnInfoNv1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 140, -1, -1));
        pnInfoNv1.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 160, 500, 40));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Ghi Chú");
        pnInfoNv1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        btnThem.setBackground(new java.awt.Color(106, 139, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/them.png"))); // NOI18N
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
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/capnhat.png"))); // NOI18N
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
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/xoa.png"))); // NOI18N
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
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/lammoi.png"))); // NOI18N
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
        btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-xuong24trang.png"))); // NOI18N
        btnSapXep.setText("Sắp xếp");
        btnSapXep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSapXep.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSapXep.setIconTextGap(5);
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
        pnInfoNv1.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 350, 80, 30));

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
        pnInfoNv1.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 350, 80, 30));

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
        pnInfoNv1.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 350, 80, 30));

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
        pnInfoNv1.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 350, 80, 30));

        txtMaKH.setEnabled(false);
        pnInfoNv1.add(txtMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 500, 40));

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        pnInfoNv1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 500, -1));

        add(pnInfoNv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1550, 410));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setGridColor(new java.awt.Color(255, 255, 255));
        tblKhachHang.setShowGrid(true);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhachHangMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachHang);

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
        try (DataInputStream dis = new DataInputStream(new FileInputStream("D:\\DuAn1NetBean\\DuAn1\\src\\MaTuSinh\\MAKHTS.dat"))) {
            MaKHTS = dis.readInt();
            MaKHTS += 1;
            txtMaKH.setText(String.valueOf("kh0" + MaKHTS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        insert();
        filltable();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:\\DuAn1NetBean\\DuAn1\\src\\MaTuSinh\\MAKHTS.dat"))) {
            dos.writeInt(MaKHTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        update();
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        delete();
    }//GEN-LAST:event_btnXoaMouseClicked

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        clearForm();
    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void btnSapXepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSapXepMouseClicked
        SXKH();
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

    private void tblKhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMousePressed
        if (evt.getClickCount() == 2) {
            row = tblKhachHang.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblKhachHangMousePressed

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnInfoNv1;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiemKH;
    // End of variables declaration//GEN-END:variables
}
