/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.PhieuNhapDAO;
import com.edusys.entity.PhieuNhap;
import com.edusys.utils.Auth;
import com.edusys.utils.XDate;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnPhieuNhap extends javax.swing.JPanel {

    PhieuNhapDAO pnDao = new PhieuNhapDAO();
    boolean SXKH = true;
    int row = -1;
    int MaKHTS;

    /**
     * Creates new form pnPhieuNhap
     */
    public pnPhieuNhap() {
        initComponents();
        filltable();
    }

    void init() {
        row = -1;
//        updateStatus();
        filltable();
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
        model.setRowCount(0);
        try {

            List<PhieuNhap> list = pnDao.selectByKeyword();
            for (PhieuNhap pn : list) {
                Object[] row = {
                    pn.getMaPNCT(),
                    XDate.toString(pn.getNgayNhap(), "dd/MM/yyyy"),
                    pn.getTenNCC(),
                    pn.getTenNV(),
                    pn.getTenSP(),
                    pn.getTenLoai(),
                    pn.getDonGiaNhap(),
                    pn.getSoLuong(),
                    pn.getDvTinh(),
                    pn.getTongTien(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu" + e);
            System.out.println(e);
        }
    }

    PhieuNhap getForm() {
        PhieuNhap pn = new PhieuNhap();
        pn.setMaPN(txtMaPN.getText());
        pn.setNgayNhap(dcNgayNhap.getDate());
        pn.setTenNCC(txtTenNV.getText());
        pn.setTenNV(txtTenNV.getText());
        pn.setTenSP(txtTenSP.getText());
        pn.setTenLoai(txtTenLoai.getText());
        pn.setDonGiaNhap(Float.parseFloat(txtDonGiaNhap.getText()));
        pn.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        pn.setDvTinh(txtDonViTinh.getText());
        pn.setTongTien(Float.parseFloat(txtTongTien.getText()));
        return pn;
    }

    void setForm(PhieuNhap pn) {
        Date ngayNhap = pn.getNgayNhap();
        txtMaPN.setText(pn.getMaPN());
        dcNgayNhap.setDate(ngayNhap);
        txtTenNV.setText(pn.getTenNV());
        txtTenNCC.setText(pn.getTenNCC());
        txtTenSP.setText(pn.getTenSP());
        txtTenLoai.setText(pn.getTenLoai());
        txtDonGiaNhap.setText(String.valueOf(pn.getDonGiaNhap()));
        txtSoLuong.setText(String.valueOf(pn.getSoLuong()));
        txtDonViTinh.setText(pn.getDvTinh());
        txtTongTien.setText(String.valueOf(pn.getTongTien()));
    }

    void clearForm() {
        PhieuNhap pn = new PhieuNhap();
        this.setForm(pn);
    }

    void insert() {
        PhieuNhap pn = getForm();
        try {
            pnDao.insert(pn);
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
            String makh = txtMaPN.getText();
            int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
            if (confirmResponse == JOptionPane.YES_OPTION) {
                try {
                    pnDao.delete(makh);
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
        PhieuNhap kh = getForm();
        try {
            pnDao.update(kh);
            this.filltable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    void edit() {
        try {
            String maPNCT = (String) tblPhieuNhap.getValueAt(row, 0);
            PhieuNhap pn = pnDao.selectById(maPNCT);
            if (pn != null) {
                setForm(pn);
                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.print(e);
        }
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void next() {
        if (this.row < tblPhieuNhap.getRowCount() - 1) {
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
        this.row = tblPhieuNhap.getRowCount() - 1;
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
        boolean last = (row == tblPhieuNhap.getRowCount() - 1);
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
            DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
            model.setRowCount(0);
            try {
                List<PhieuNhap> list = pnDao.sapxepgiamdan();
                for (PhieuNhap pn : list) {
                    Object[] row = {
                        pn.getMaPNCT(),
                        pn.getNgayNhap(),
                        pn.getTenNCC(),
                        pn.getTenNV(),
                        pn.getTenSP(),
                        pn.getTenLoai(),
                        pn.getDonGiaNhap(),
                        pn.getSoLuong(),
                        pn.getDvTinh(),
                        pn.getTongTien(),};
                    model.addRow(row);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            }
        } else if (SXKH == false) {
            SXKH = true;
            DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
            model.setRowCount(0);
            try {
                List<PhieuNhap> list = pnDao.sapxep();
                for (PhieuNhap pn : list) {
                    Object[] row = {
                        pn.getMaPNCT(),
                        pn.getNgayNhap(),
                        pn.getTenNCC(),
                        pn.getTenNV(),
                        pn.getTenSP(),
                        pn.getTenLoai(),
                        pn.getDonGiaNhap(),
                        pn.getSoLuong(),
                        pn.getDvTinh(),
                        pn.getTongTien(),};
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

        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        txtTimKiemKH = new javax.swing.JTextField();
        lblTimKiem = new javax.swing.JLabel();
        pnInfoNv1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTenNCC = new javax.swing.JTextField();
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
        txtMaPN = new javax.swing.JTextField();
        txtDonGiaNhap = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTenLoai = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDonViTinh = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        dcNgayNhap = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Dữ liệu phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu nhập", "Ngày nhập", "Tên nhà cung cấp", "Tên nhân viên", "Tên sản phẩm", "Tên loại", "Đơn giá nhập", "Số lượng", "Đơn vị tính", "Thành tiền"
            }
        ));
        tblPhieuNhap.setGridColor(new java.awt.Color(255, 255, 255));
        tblPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblPhieuNhapMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblPhieuNhap);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 1500, 390));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, 1560, 460));

        txtTimKiemKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemKHCaretUpdate(evt);
            }
        });
        add(txtTimKiemKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 540, 300, 30));

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTimKiem.setText("Tìm kiếm theo tên sản phẩm");
        add(lblTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 520, -1, -1));

        pnInfoNv1.setBackground(new java.awt.Color(255, 255, 255));
        pnInfoNv1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnInfoNv1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Mã phiếu nhập");
        pnInfoNv1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));
        pnInfoNv1.add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 400, 40));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Ngày nhập");
        pnInfoNv1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Tên nhân viên");
        pnInfoNv1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Tên sản phẩm");
        pnInfoNv1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 140, -1, -1));
        pnInfoNv1.add(txtTenNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 70, 400, 40));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Đơn giá nhập");
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
        pnInfoNv1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 130, 40));

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
        pnInfoNv1.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, 130, 40));

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
        pnInfoNv1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 410, 130, 40));

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
        pnInfoNv1.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, 130, 40));

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
        pnInfoNv1.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, 130, 40));

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
        pnInfoNv1.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 410, 80, 30));

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
        pnInfoNv1.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 410, 80, 30));

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
        pnInfoNv1.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 410, 80, 30));

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
        pnInfoNv1.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 410, 80, 30));

        txtMaPN.setEnabled(false);
        pnInfoNv1.add(txtMaPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 400, 40));
        pnInfoNv1.add(txtDonGiaNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 400, 40));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Số lượng");
        pnInfoNv1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 230, -1, -1));
        pnInfoNv1.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 400, 40));
        pnInfoNv1.add(txtTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 400, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tên nhà cung cấp");
        pnInfoNv1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, -1, -1));
        pnInfoNv1.add(txtTenLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 160, 400, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tên loại");
        pnInfoNv1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 140, -1, -1));
        pnInfoNv1.add(txtDonViTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 250, 400, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Đơn vị tính");
        pnInfoNv1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 230, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Đơn vị tính");
        pnInfoNv1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 230, -1, -1));
        pnInfoNv1.add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 400, 40));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Thành tiền");
        pnInfoNv1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, -1, -1));

        dcNgayNhap.setDateFormatString("dd/MM/yyyy");
        pnInfoNv1.add(dcNgayNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 400, 40));

        add(pnInfoNv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 1560, 470));
    }// </editor-fold>//GEN-END:initComponents

    private void tblPhieuNhapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuNhapMousePressed
        if (evt.getClickCount() == 2) {
            row = tblPhieuNhap.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblPhieuNhapMousePressed

    private void txtTimKiemKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemKHCaretUpdate
        timKiem();
    }//GEN-LAST:event_txtTimKiemKHCaretUpdate

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        try (DataInputStream dis = new DataInputStream(new FileInputStream("D:\\DuAn1NetBean\\DuAn1\\src\\MaTuSinh\\MAKHTS.dat"))) {
            MaKHTS = dis.readInt();
            MaKHTS += 1;
            if (MaKHTS >= 10) {
                txtMaPN.setText(String.valueOf("pn" + MaKHTS));
            } else if (MaKHTS < 10) {
                txtMaPN.setText(String.valueOf("pn0" + MaKHTS));
            }
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
    private com.toedter.calendar.JDateChooser dcNgayNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnInfoNv1;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTextField txtDonGiaNhap;
    private javax.swing.JTextField txtDonViTinh;
    private javax.swing.JTextField txtMaPN;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiemKH;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
