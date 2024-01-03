/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import java.sql.ResultSet;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Auth;
import com.edusys.utils.JdbcHelper;
import com.edusys.utils.XImage;
import java.awt.Color;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author cutr0
 */
public class TrangChu extends javax.swing.JFrame {

    private JPanel childPanel;

    /**
     * Creates new form TrangChu
     */
    public TrangChu() {
        initComponents();
        setExtendedState(TrangChu.MAXIMIZED_BOTH);
        vaiTroTenMenu();
        moPn(new pnTrangChu());
    }

    void vaiTroTenMenu() {
        NhanVien nv = new NhanVien();
        try {
            if (Auth.user.getMaNV() != null) {
                try {
                    String sql = "select TENNV, VAITRO, HINH from NHANVIEN where MANV = '" + Auth.user.getMaNV() + "'";
                    ResultSet rs = JdbcHelper.query(sql);
                    while (rs.next()) {
                        String tenNV = rs.getString(1);
                        int vaiTro = rs.getInt(2);
                        String Hinh = rs.getString(3);
                        nv.setHinh(Hinh);
                        lblAvt.setToolTipText(nv.getHinh());
                        lblAvt.setIcon(XImage.read(nv.getHinh()));
                        Icon i = lblAvt.getIcon();
                        ImageIcon icon = (ImageIcon) i;
                        Image image = icon.getImage().getScaledInstance(lblAvt.getWidth(), lblAvt.getHeight(), Image.SCALE_SMOOTH);
                        lblAvt.setIcon(new ImageIcon(image));
                        lblTenNV.setText(tenNV);
                        if (vaiTro == 1) {
                            lblVaiTro.setText("Quản lý");
                        } else if (vaiTro == 0) {
                            lblVaiTro.setText("Nhân viên");
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Lỗi không xác định");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chưa đăng nhập");
        }
    }

    void moPn(JPanel panel) {
        childPanel = panel;
        pnMain.removeAll();
        pnMain.add(childPanel);
        pnMain.validate();
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
        pnMenu = new javax.swing.JPanel();
        lblSanPham = new javax.swing.JLabel();
        lblPhieuNhap = new javax.swing.JLabel();
        lblNhaCungCap = new javax.swing.JLabel();
        lblHoaDon = new javax.swing.JLabel();
        lblThongKe = new javax.swing.JLabel();
        lblAvt = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        lblDoiMatKhau = new javax.swing.JLabel();
        lblThongTinUpdate = new javax.swing.JLabel();
        lblDangXuat = new javax.swing.JLabel();
        pnTC = new javax.swing.JPanel();
        lblTrangChu = new javax.swing.JLabel();
        pnNV = new javax.swing.JPanel();
        lblNhanVien = new javax.swing.JLabel();
        pnKH = new javax.swing.JPanel();
        lblKhachHang = new javax.swing.JLabel();
        pnMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        pnMenu.setBackground(new java.awt.Color(106, 139, 255));
        pnMenu.setLayout(null);

        lblSanPham.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lblSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-partner-24.png"))); // NOI18N
        lblSanPham.setText("Sản phẩm");
        lblSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSanPhamMouseClicked(evt);
            }
        });
        pnMenu.add(lblSanPham);
        lblSanPham.setBounds(70, 390, 143, 32);

        lblPhieuNhap.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        lblPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-rules-24.png"))); // NOI18N
        lblPhieuNhap.setText("Phiếu nhập");
        lblPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnMenu.add(lblPhieuNhap);
        lblPhieuNhap.setBounds(70, 510, 159, 32);

        lblNhaCungCap.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblNhaCungCap.setForeground(new java.awt.Color(255, 255, 255));
        lblNhaCungCap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-partner-24.png"))); // NOI18N
        lblNhaCungCap.setText("Nhà cung cấp");
        lblNhaCungCap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnMenu.add(lblNhaCungCap);
        lblNhaCungCap.setBounds(70, 570, 186, 32);

        lblHoaDon.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-list-24.png"))); // NOI18N
        lblHoaDon.setText("Hoá đơn");
        lblHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnMenu.add(lblHoaDon);
        lblHoaDon.setBounds(70, 630, 127, 32);

        lblThongKe.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-circle-chart-24.png"))); // NOI18N
        lblThongKe.setText("Thống kê");
        lblThongKe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongKeMouseClicked(evt);
            }
        });
        pnMenu.add(lblThongKe);
        lblThongKe.setBounds(70, 690, 136, 32);

        lblAvt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnMenu.add(lblAvt);
        lblAvt.setBounds(110, 50, 90, 120);

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenNV.setForeground(new java.awt.Color(255, 255, 255));
        lblTenNV.setText("                        ");
        pnMenu.add(lblTenNV);
        lblTenNV.setBounds(100, 170, 130, 20);

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblVaiTro.setForeground(new java.awt.Color(255, 255, 255));
        lblVaiTro.setText("            ");
        pnMenu.add(lblVaiTro);
        lblVaiTro.setBounds(130, 190, 48, 20);

        lblDoiMatKhau.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        lblDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-change-24.png"))); // NOI18N
        lblDoiMatKhau.setText("Đổi mật khẩu");
        lblDoiMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDoiMatKhauMouseClicked(evt);
            }
        });
        pnMenu.add(lblDoiMatKhau);
        lblDoiMatKhau.setBounds(70, 750, 179, 32);

        lblThongTinUpdate.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblThongTinUpdate.setForeground(new java.awt.Color(255, 255, 255));
        lblThongTinUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-info-24.png"))); // NOI18N
        lblThongTinUpdate.setText("Thông tin update");
        lblThongTinUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblThongTinUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongTinUpdateMouseClicked(evt);
            }
        });
        pnMenu.add(lblThongTinUpdate);
        lblThongTinUpdate.setBounds(70, 870, 226, 32);

        lblDangXuat.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        lblDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-open-pane-24.png"))); // NOI18N
        lblDangXuat.setText("Đăng xuất");
        lblDangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseClicked(evt);
            }
        });
        pnMenu.add(lblDangXuat);
        lblDangXuat.setBounds(70, 810, 144, 32);

        pnTC.setBackground(new java.awt.Color(153, 204, 255));

        lblTrangChu.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lblTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-home-24.png"))); // NOI18N
        lblTrangChu.setText("Trang chủ");
        lblTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblTrangChuMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnTCLayout = new javax.swing.GroupLayout(pnTC);
        pnTC.setLayout(pnTCLayout);
        pnTCLayout.setHorizontalGroup(
            pnTCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTCLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(lblTrangChu)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        pnTCLayout.setVerticalGroup(
            pnTCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTrangChu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnMenu.add(pnTC);
        pnTC.setBounds(0, 260, 300, 50);

        pnNV.setBackground(new java.awt.Color(106, 139, 255));

        lblNhanVien.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-user-account-24.png"))); // NOI18N
        lblNhanVien.setText("Nhân viên");
        lblNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNhanVienMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnNVLayout = new javax.swing.GroupLayout(pnNV);
        pnNV.setLayout(pnNVLayout);
        pnNVLayout.setHorizontalGroup(
            pnNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnNVLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(lblNhanVien)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        pnNVLayout.setVerticalGroup(
            pnNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnNVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNhanVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnMenu.add(pnNV);
        pnNV.setBounds(0, 320, 300, 58);

        pnKH.setBackground(new java.awt.Color(106, 139, 255));

        lblKhachHang.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        lblKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-user-24.png"))); // NOI18N
        lblKhachHang.setText("Khách hàng");
        lblKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKhachHangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnKHLayout = new javax.swing.GroupLayout(pnKH);
        pnKH.setLayout(pnKHLayout);
        pnKHLayout.setHorizontalGroup(
            pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnKHLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblKhachHang)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pnKHLayout.setVerticalGroup(
            pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnKHLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblKhachHang)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pnMenu.add(pnKH);
        pnKH.setBounds(0, 440, 300, 50);

        getContentPane().add(pnMenu);
        pnMenu.setBounds(0, 0, 0, 1080);

        pnMain.setBackground(new java.awt.Color(255, 255, 255));
        pnMain.setMaximumSize(new java.awt.Dimension(1620, 1080));
        pnMain.setPreferredSize(new java.awt.Dimension(1620, 1080));
        getContentPane().add(pnMain);
        pnMain.setBounds(300, 0, 1620, 1080);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseClicked
        moPn(new pnTrangChu());
        pnTC.setBackground(new Color(153, 204, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
    }//GEN-LAST:event_lblTrangChuMouseClicked

    private void lblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanVienMouseClicked
        moPn(new pnNhanVien());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(153, 204, 255));
        pnKH.setBackground(new Color(106, 139, 255));
    }//GEN-LAST:event_lblNhanVienMouseClicked

    private void lblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhachHangMouseClicked
        moPn(new pnKhachHang());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(153, 204, 255));
    }//GEN-LAST:event_lblKhachHangMouseClicked

    private void lblDoiMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseClicked
        moPn(new pnDoiMatKhau());
    }//GEN-LAST:event_lblDoiMatKhauMouseClicked

    private void lblThongTinUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongTinUpdateMouseClicked
        moPn(new pnThongTinUpdate());
    }//GEN-LAST:event_lblThongTinUpdateMouseClicked

    private void lblTrangChuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMousePressed

    }//GEN-LAST:event_lblTrangChuMousePressed

    private void lblDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseClicked
        int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất không");
        if (confirmResponse == JOptionPane.YES_OPTION) {
            new DangNhap().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_lblDangXuatMouseClicked

    private void lblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPhamMouseClicked
        moPn(new pnSanPham());
    }//GEN-LAST:event_lblSanPhamMouseClicked

    private void lblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseClicked
        moPn(new pnthongke());
    }//GEN-LAST:event_lblThongKeMouseClicked
    //BatDauNhanVien

    //KetThucNhanvien
    //BatDauKhachHang
    //KetThucKhachHang
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAvt;
    private javax.swing.JLabel lblDangXuat;
    private javax.swing.JLabel lblDoiMatKhau;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblNhaCungCap;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblPhieuNhap;
    private javax.swing.JLabel lblSanPham;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblThongTinUpdate;
    private javax.swing.JLabel lblTrangChu;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JPanel pnKH;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnMenu;
    private javax.swing.JPanel pnNV;
    private javax.swing.JPanel pnTC;
    private javax.swing.ButtonGroup rdoGioiTinh;
    private javax.swing.ButtonGroup rdoVaiTro;
    // End of variables declaration//GEN-END:variables
}
