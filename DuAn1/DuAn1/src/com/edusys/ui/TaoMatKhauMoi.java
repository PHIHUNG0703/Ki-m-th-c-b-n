/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.NhanVienDAO;
import com.edusys.utils.Auth;
import javax.swing.JOptionPane;

/**
 *
 * @author cutr0
 */
public class TaoMatKhauMoi extends javax.swing.JFrame {

    /**
     * Creates new form TaoMatKhauMoi
     */
    public TaoMatKhauMoi() {
        initComponents();
        setExtendedState(TaoMatKhauMoi.MAXIMIZED_BOTH);
    }
    boolean hienMK = true;
    NhanVienDAO dao = new NhanVienDAO();

    private void doiMatKhau() {
        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String matKhauMoi2 = new String(txtNhapLaiMatKhauMoi.getPassword());

        if (!matKhauMoi.equals(matKhauMoi2)) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu không đúng!");
        } else if (Auth.user.getMatKhau().equalsIgnoreCase(matKhauMoi)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu bị trùng với mật khẩu cũ!");
        } else {
            try {
                Auth.user.setMatKhau(matKhauMoi);
                dao.update(Auth.user);
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                this.dispose();
                new DangNhap().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu không thành công!");
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

        pnDangNhap = new javax.swing.JPanel();
        pnKhungDangNhap = new javax.swing.JPanel();
        lblHienMk2 = new javax.swing.JLabel();
        lblHienMk1 = new javax.swing.JLabel();
        lblDangNhap = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        btnDangNhap = new javax.swing.JButton();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        lblMatKhau1 = new javax.swing.JLabel();
        txtNhapLaiMatKhauMoi = new javax.swing.JPasswordField();
        lblTest = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnDangNhap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnKhungDangNhap.setBackground(new java.awt.Color(255, 255, 255));
        pnKhungDangNhap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHienMk2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-eye-24 (1).png"))); // NOI18N
        lblHienMk2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHienMk2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHienMk2MouseClicked(evt);
            }
        });
        pnKhungDangNhap.add(lblHienMk2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, -1, -1));

        lblHienMk1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-eye-24 (1).png"))); // NOI18N
        lblHienMk1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHienMk1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHienMk1MouseClicked(evt);
            }
        });
        pnKhungDangNhap.add(lblHienMk1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, -1, -1));

        lblDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblDangNhap.setText("Tạo Mật Khẩu Mới");
        lblDangNhap.setPreferredSize(new java.awt.Dimension(300, 64));
        pnKhungDangNhap.add(lblDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 320, -1));

        lblMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMatKhau.setText("Mật khẩu mới");
        pnKhungDangNhap.add(lblMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, -1, -1));

        btnDangNhap.setBackground(new java.awt.Color(106, 139, 255));
        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setText("Xác nhận đổi");
        btnDangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDangNhapMouseClicked(evt);
            }
        });
        pnKhungDangNhap.add(btnDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 300, 30));
        pnKhungDangNhap.add(txtMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 300, 40));

        lblMatKhau1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMatKhau1.setText("Nhập lại mật khẩu mới");
        pnKhungDangNhap.add(lblMatKhau1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, -1, -1));
        pnKhungDangNhap.add(txtNhapLaiMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 300, 40));

        lblTest.setText("              ");
        pnKhungDangNhap.add(lblTest, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, -1, -1));

        pnDangNhap.add(pnKhungDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 700, 500));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/hinhnen2.jpg"))); // NOI18N
        pnDangNhap.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDangNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangNhapMouseClicked
        doiMatKhau();
    }//GEN-LAST:event_btnDangNhapMouseClicked

    private void lblHienMk1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHienMk1MouseClicked
        if (hienMK == true) {
            txtMatKhauMoi.setEchoChar((char) 0);
            hienMK = false;
        } else {
            txtMatKhauMoi.setEchoChar('*');
            hienMK = true;
        }
    }//GEN-LAST:event_lblHienMk1MouseClicked

    private void lblHienMk2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHienMk2MouseClicked
        if (hienMK == true) {
            txtNhapLaiMatKhauMoi.setEchoChar((char) 0);
            hienMK = false;
        } else {
            txtNhapLaiMatKhauMoi.setEchoChar('*');
            hienMK = true;
        }
    }//GEN-LAST:event_lblHienMk2MouseClicked

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
            java.util.logging.Logger.getLogger(TaoMatKhauMoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaoMatKhauMoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaoMatKhauMoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaoMatKhauMoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TaoMatKhauMoi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDangNhap;
    private javax.swing.JLabel lblHienMk1;
    private javax.swing.JLabel lblHienMk2;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblMatKhau1;
    private javax.swing.JLabel lblTest;
    private javax.swing.JPanel pnDangNhap;
    private javax.swing.JPanel pnKhungDangNhap;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JPasswordField txtNhapLaiMatKhauMoi;
    // End of variables declaration//GEN-END:variables
}
