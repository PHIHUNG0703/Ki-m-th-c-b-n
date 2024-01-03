/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.LoaiSanPhamDAO;
import com.edusys.entity.KhachHang;
import com.edusys.entity.LoaiSanPham;
import com.edusys.entity.LoaiSanPham;
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
public class pnLoaiSanPham extends javax.swing.JPanel {

    int row = -1;
    LoaiSanPhamDAO lspDao = new LoaiSanPhamDAO();
    boolean SXLSP = true;
    int MaKHTS;

    /**
     * Creates new form pnLoaiSP
     */
    public pnLoaiSanPham() {
        initComponents();
        init();
    }

    void init() {
        row = -1;
        updateStatus();
        filltable();
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblLoaiSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimKiem.getText();
            List<LoaiSanPham> list = lspDao.selectByKeyword(keyWord);
            for (LoaiSanPham slp : list) {
                Object[] row = {
                    slp.getMaLoai(),
                    slp.getTenLoai(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    LoaiSanPham getForm() {
        LoaiSanPham lsp = new LoaiSanPham();
        lsp.setMaLoai(txtMaLoai.getText());
        lsp.setTenLoai(txtTenLoai.getText());
        return lsp;
    }

    void setForm(LoaiSanPham lsp) {
        txtMaLoai.setText(lsp.getMaLoai());
        txtTenLoai.setText(lsp.getTenLoai());
    }

    void clearForm() {
        LoaiSanPham lsp = new LoaiSanPham();
        this.setForm(lsp);
    }

    void insert() {
        LoaiSanPham slp = getForm();
        try {
            lspDao.insert(slp);
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
            String maLSP = txtMaLoai.getText();
            int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
            if (confirmResponse == JOptionPane.YES_OPTION) {
                try {
                    lspDao.delete(maLSP);
                    this.filltable();
                    this.clearForm();
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        }
    }

    void update() {
        LoaiSanPham lsp = getForm();
        try {
            lspDao.update(lsp);
            this.filltable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    void edit() {
        try {
            String maLSP = (String) tblLoaiSanPham.getValueAt(row, 0);
            LoaiSanPham lsp = lspDao.selectById(maLSP);
            if (lsp != null) {
                setForm(lsp);
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
        if (this.row < tblLoaiSanPham.getRowCount() - 1) {
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
        this.row = tblLoaiSanPham.getRowCount() - 1;
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
        boolean last = (row == tblLoaiSanPham.getRowCount() - 1);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void SXKH() {
        if (SXLSP == true) {
            SXLSP = false;
            DefaultTableModel model = (DefaultTableModel) tblLoaiSanPham.getModel();
            model.setRowCount(0);
            try {
                List<LoaiSanPham> list = lspDao.sapxepgiamdan();
                for (LoaiSanPham slp : list) {
                    Object[] row = {
                        slp.getMaLoai(),
                        slp.getTenLoai(),};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
        } else if (SXLSP == false) {
            SXLSP = true;
            DefaultTableModel model = (DefaultTableModel) tblLoaiSanPham.getModel();
            model.setRowCount(0);
            try {
                List<LoaiSanPham> list = lspDao.sapxeptangdan();
                for (LoaiSanPham slp : list) {
                    Object[] row = {
                        slp.getMaLoai(),
                        slp.getTenLoai(),};
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
        jLabel16 = new javax.swing.JLabel();
        txtTenLoai = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnSapXep = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtMaLoai = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoaiSanPham = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        lblTimKiem = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnInfoNv1.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.black, java.awt.Color.white, null, null), "Thông tin loại sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Mã loại");
        pnInfoNv1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Tên loại");
        pnInfoNv1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, -1, -1));
        pnInfoNv1.add(txtTenLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 70, 500, 40));

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
        pnInfoNv1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 100, 30));

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
        pnInfoNv1.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 350, 100, 30));

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
        pnInfoNv1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, 100, 30));

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
        pnInfoNv1.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, 100, 30));

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
        pnInfoNv1.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, 100, 30));

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

        txtMaLoai.setEnabled(false);
        pnInfoNv1.add(txtMaLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 500, 40));

        add(pnInfoNv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1550, 410));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu loại sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblLoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã loại", "Tên loại"
            }
        ));
        tblLoaiSanPham.setGridColor(new java.awt.Color(255, 255, 255));
        tblLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblLoaiSanPhamMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblLoaiSanPham);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 470));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 1560, 540));

        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });
        add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 470, 300, 30));

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTimKiem.setText("Tìm kiếm theo tên");
        add(lblTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 450, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        timKiem();
    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void btnLastMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseClicked
        last();
    }//GEN-LAST:event_btnLastMouseClicked

    private void btnNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseClicked
        next();
    }//GEN-LAST:event_btnNextMouseClicked

    private void btnPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseClicked
        prev();
    }//GEN-LAST:event_btnPrevMouseClicked

    private void btnFirstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFirstMouseClicked
        first();
    }//GEN-LAST:event_btnFirstMouseClicked

    private void btnSapXepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSapXepMouseClicked
        SXKH();
    }//GEN-LAST:event_btnSapXepMouseClicked

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        clearForm();
    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        delete();
    }//GEN-LAST:event_btnXoaMouseClicked

    private void btnCapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseClicked
        update();
    }//GEN-LAST:event_btnCapNhatMouseClicked

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        try (DataInputStream dis = new DataInputStream(new FileInputStream("D:\\DuAn1NetBean\\DuAn1\\src\\MaTuSinh\\MALSPTS.dat"))) {
            MaKHTS = dis.readInt();
            MaKHTS += 1;
            txtMaLoai.setText(String.valueOf("slp0" + MaKHTS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        insert();
        filltable();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:\\DuAn1NetBean\\DuAn1\\src\\MaTuSinh\\MALSPTS.dat"))) {
            dos.writeInt(MaKHTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void tblLoaiSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiSanPhamMousePressed
        if (evt.getClickCount() == 2) {
            row = tblLoaiSanPham.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblLoaiSanPhamMousePressed


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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnInfoNv1;
    private javax.swing.JTable tblLoaiSanPham;
    private javax.swing.JTextField txtMaLoai;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
