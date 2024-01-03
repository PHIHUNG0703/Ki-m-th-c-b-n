/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import javax.swing.JOptionPane;
import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Auth;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author cutr0
 */
public class DangNhap extends javax.swing.JFrame {

    boolean hienMK = true;
    String manv = "a";
    String nhoPas1 = "b";
    String nhoPas2;
    String MK;

    /**
     * Creates new form DangNhap
     */
    public DangNhap() {
        initComponents();
        DocMaNV();
        setExtendedState(DangNhap.MAXIMIZED_BOTH);
    }

    void Luu() {
        String a = txtTenDangNhap.getText();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\NhoPass\\MaNV.dat"))) {
            dos.writeUTF(a);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String b = txtMatKhau.getText();
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\NhoPass\\MK.dat"))) {
            dos.writeUTF(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (chkNhoMatKhau.isSelected()) {
            nhoPas1 = "a";
        } else if (!chkNhoMatKhau.isSelected()) {
            nhoPas1 = "b";
        }
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\NhoPass\\Nho.dat"))) {
            dos.writeUTF(nhoPas1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void DocMaNV() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\NhoPass\\MaNV.dat"))) {
            manv = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean batLoi() {
        if (txtTenDangNhap.getText().isEmpty() && txtMatKhau.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được bỏ trống");
            return false;
        }
        if (txtTenDangNhap.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được bỏ trống");
            return false;
        }
        if (txtTenDangNhap.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được bỏ trống");
            return false;
        }
        return true;
    }

    void dangNhap() {
        if (batLoi() == true) {
            NhanVienDAO dao = new NhanVienDAO();
            String manv = txtTenDangNhap.getText();
            String matkhau = new String(txtMatKhau.getPassword());
            NhanVien nv = dao.selectById(manv);
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập!");
            } else {
                if (!nv.getMatKhau().equals(matkhau)) {
                    JOptionPane.showMessageDialog(this, "Sai mật khẩu!");
                } else {
                    Auth.user = nv;
//                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                    new TrangChu().setVisible(true);
                    this.dispose();
                }
            }
        }
    }

    void hienPass() {
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

    void nhoPass() {
        try {
            try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\NhoPass\\Nho.dat"))) {
                nhoPas2 = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\NhoPass\\MK.dat"))) {
                MK = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nhoPas2.equalsIgnoreCase("a")) {
                if (txtTenDangNhap.getText().equalsIgnoreCase(manv)) {
                    chkNhoMatKhau.setSelected(true);
                    txtMatKhau.setEchoChar('*');
                    txtMatKhau.setText(MK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        lblDangNhap = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        lblTenDangNhap = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        btnDangNhap = new javax.swing.JButton();
        lblQuenMatKhau = new javax.swing.JLabel();
        chkNhoMatKhau = new javax.swing.JCheckBox();
        lblHienMK = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        lblTest = new javax.swing.JLabel();
        lblAnhNen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setResizable(false);

        pnDangNhap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnKhungDangNhap.setBackground(new java.awt.Color(255, 255, 255));
        pnKhungDangNhap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblDangNhap.setText("ĐĂNG NHẬP");
        lblDangNhap.setPreferredSize(new java.awt.Dimension(300, 64));
        pnKhungDangNhap.add(lblDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 220, -1));

        txtTenDangNhap.setText("nv01");
        txtTenDangNhap.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTenDangNhapCaretUpdate(evt);
            }
        });
        pnKhungDangNhap.add(txtTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 300, 40));

        lblTenDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTenDangNhap.setText("Tên đăng nhập");
        pnKhungDangNhap.add(lblTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, -1, -1));

        lblMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMatKhau.setText("Mật khẩu");
        pnKhungDangNhap.add(lblMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, -1, 20));

        btnDangNhap.setBackground(new java.awt.Color(106, 139, 255));
        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDangNhapMouseClicked(evt);
            }
        });
        pnKhungDangNhap.add(btnDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 300, 40));

        lblQuenMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblQuenMatKhau.setText("Quên mật khẩu?");
        lblQuenMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuenMatKhauMouseClicked(evt);
            }
        });
        pnKhungDangNhap.add(lblQuenMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, -1, -1));

        chkNhoMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkNhoMatKhau.setText("Nhớ mật khẩu");
        pnKhungDangNhap.add(chkNhoMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 300, -1, -1));

        lblHienMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-closed-eye-24.png"))); // NOI18N
        lblHienMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHienMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHienMKMouseClicked(evt);
            }
        });
        pnKhungDangNhap.add(lblHienMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, -1, -1));

        txtMatKhau.setText("12345678");
        pnKhungDangNhap.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 300, 40));
        pnKhungDangNhap.add(lblTest, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 30, 30));

        pnDangNhap.add(pnKhungDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 700, 500));

        lblAnhNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/hinhnen2.jpg"))); // NOI18N
        pnDangNhap.add(lblAnhNen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

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
        Luu();
        dangNhap();
    }//GEN-LAST:event_btnDangNhapMouseClicked

    private void lblHienMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHienMKMouseClicked
        hienPass();
    }//GEN-LAST:event_lblHienMKMouseClicked

    private void lblQuenMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMatKhauMouseClicked
        new QuenMatKhau().setVisible(true);
    }//GEN-LAST:event_lblQuenMatKhauMouseClicked

    private void txtTenDangNhapCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTenDangNhapCaretUpdate
        nhoPass();
    }//GEN-LAST:event_txtTenDangNhapCaretUpdate

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
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JCheckBox chkNhoMatKhau;
    private javax.swing.JLabel lblAnhNen;
    private javax.swing.JLabel lblDangNhap;
    private javax.swing.JLabel lblHienMK;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblQuenMatKhau;
    private javax.swing.JLabel lblTenDangNhap;
    private javax.swing.JLabel lblTest;
    private javax.swing.JPanel pnDangNhap;
    private javax.swing.JPanel pnKhungDangNhap;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
