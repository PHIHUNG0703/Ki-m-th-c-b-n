/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.NhanVienDAO;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Auth;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.JOptionPane;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author cutr0
 */
public class QuenMatKhau extends javax.swing.JFrame {

    String OTP;
    String Gmail;
    int nhapSai = 3;

    /**
     * Creates new form QuenMatKhau
     */
    public QuenMatKhau() {
        initComponents();
        setExtendedState(QuenMatKhau.MAXIMIZED_BOTH);
        captcha();
    }

    boolean batLoiCaptCha() {
        if (txtNhapGmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập Gmail");
            return false;
        }

        if (txtNhapCaptCha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã Captcha");
            return false;
        }

        try {
            String sql = "select count(*) from NHANVIEN where EMAIL = '" + lblOTP.getText() + "'";
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                int SoLuong = rs.getInt(1);
                if (SoLuong == 0) {
                    JOptionPane.showMessageDialog(this, "Gmail không tồn tại");
                    txtNhapGmail.setText("");
                    return false;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gmail không hợp lệ");
            return false;
        }
        return true;
    }

    void noiGmail() {
        Gmail = txtNhapGmail.getText();
        lblOTP.setText(Gmail + "@fpt.edu.vn");
    }

    void captcha() {
        String upper = "ABCDEFGHIKLMNOPRQSTUVWXYZ";
        String lower = "abcdefghiklmnoprqstuvwxyz";
        String number = "0123456789";
        String combination = upper + lower + number;
        int len = 5;
        char[] password = new char[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            password[i] = combination.charAt(r.nextInt(combination.length()));
        }
        txtHienCaptCha.setText(new String(password));
    }

    void guiOTP() {
        String combination = "0123456789";
        int len = 6;
        char[] maOTP = new char[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            maOTP[i] = combination.charAt(r.nextInt(combination.length()));
        }
        OTP = String.valueOf(maOTP);
        System.out.println(maOTP);
        final String username = "cutr0ll007@gmail.com";
        final String password = "dcnowuxhrynltprl";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cutr0ll007@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(lblOTP.getText()));
            message.setSubject("Mã xác nhận Gmail");
            message.setText("Mã OTP của bạn là: " + new String(maOTP) + ".");

            Transport.send(message);

            JOptionPane.showMessageDialog(this, "Gữi thành công vui lòng kiểm tra Gmail");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    void xacNhapCaptCha() {
        if (batLoiCaptCha() == true) {
            if (txtNhapCaptCha.getText().equalsIgnoreCase(txtHienCaptCha.getText())) {
                JOptionPane.showMessageDialog(this, "Nhập Captcha thành công đang gữi mã OTP");
                guiOTP();
                captcha();
            } else {
                JOptionPane.showMessageDialog(this, "Nhập sai mã CaptCha vui lòng nhập lại");
                txtNhapCaptCha.setText("");
                captcha();
            }
        }
    }

    void xacNhanOTP() {
        if (txtNhapOTP.getText().equalsIgnoreCase(OTP)) {
            JOptionPane.showMessageDialog(this, "Xác nhận thành công");
            maNV();
            this.dispose();
            new TaoMatKhauMoi().setVisible(true);
        } else {
            if (nhapSai == 1) {
                OTP = "";
                JOptionPane.showMessageDialog(this, "Bạn đã nhập sai mã OTP quá 3 lần mã OTP của bạn đã hết hiệu lực");
                txtNhapOTP.setText("");
            } else {
                nhapSai -= 1;
                JOptionPane.showMessageDialog(this, "Nhập sai mã OTP bạn còn " + nhapSai + " lần nhập");

            }
        }
    }

    void maNV() {
        NhanVienDAO dao = new NhanVienDAO();
        try {
            String sql = "select MANV from NHANVIEN where EMAIL = '" + lblOTP.getText() + "'";
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                String manv = rs.getString(1);
                NhanVien nv = dao.selectById(manv);
                Auth.user = nv;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không hợp lệ");
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnXacNhan = new javax.swing.JButton();
        txtHienCaptCha = new javax.swing.JTextField();
        btnGuiMa = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtNhapOTP = new javax.swing.JTextField();
        btnDoiMa = new javax.swing.JButton();
        lblQuayLai = new javax.swing.JLabel();
        txtNhapCaptCha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblOTP = new javax.swing.JLabel();
        txtNhapGmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("QUÊN MẬT KHẨU");
        jLabel2.setPreferredSize(new java.awt.Dimension(300, 64));
        jPanel2.add(jLabel2);
        jLabel2.setBounds(200, 30, 310, 64);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Gmail");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(200, 150, 33, 16);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Mã Captcha");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(200, 220, 67, 16);

        btnXacNhan.setBackground(new java.awt.Color(106, 139, 255));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanMouseClicked(evt);
            }
        });
        jPanel2.add(btnXacNhan);
        btnXacNhan.setBounds(200, 370, 300, 40);

        txtHienCaptCha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtHienCaptCha.setEnabled(false);
        jPanel2.add(txtHienCaptCha);
        txtHienCaptCha.setBounds(360, 240, 60, 40);

        btnGuiMa.setBackground(new java.awt.Color(153, 255, 153));
        btnGuiMa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuiMa.setText("Gữi mã");
        btnGuiMa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuiMa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuiMaMouseClicked(evt);
            }
        });
        jPanel2.add(btnGuiMa);
        btnGuiMa.setBounds(430, 310, 73, 40);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Mã OTP");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(200, 290, 45, 16);
        jPanel2.add(txtNhapOTP);
        txtNhapOTP.setBounds(200, 310, 220, 40);

        btnDoiMa.setBackground(new java.awt.Color(153, 255, 153));
        btnDoiMa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDoiMa.setText("Đổi mã");
        btnDoiMa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDoiMa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDoiMaMouseClicked(evt);
            }
        });
        jPanel2.add(btnDoiMa);
        btnDoiMa.setBounds(430, 240, 73, 40);

        lblQuayLai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblQuayLai.setText("Quay lại đăng nhập");
        lblQuayLai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblQuayLai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuayLaiMouseClicked(evt);
            }
        });
        jPanel2.add(lblQuayLai);
        lblQuayLai.setBounds(300, 420, 111, 16);
        jPanel2.add(txtNhapCaptCha);
        txtNhapCaptCha.setBounds(200, 240, 150, 40);
        jPanel2.add(jLabel6);
        jLabel6.setBounds(90, 100, 0, 0);

        jLabel7.setText("@fpt.edu.vn");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(435, 180, 65, 16);

        lblOTP.setBackground(new java.awt.Color(0, 0, 0));
        lblOTP.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblOTP);
        lblOTP.setBounds(310, 100, 60, 40);

        txtNhapGmail.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNhapGmailCaretUpdate(evt);
            }
        });
        jPanel2.add(txtNhapGmail);
        txtNhapGmail.setBounds(200, 170, 230, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(610, 290, 700, 500);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/hinhnen2.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 1920, 1080);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoiMaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoiMaMouseClicked
        captcha();
    }//GEN-LAST:event_btnDoiMaMouseClicked

    private void btnGuiMaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuiMaMouseClicked
        xacNhapCaptCha();
    }//GEN-LAST:event_btnGuiMaMouseClicked

    private void btnXacNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanMouseClicked
        xacNhanOTP();
    }//GEN-LAST:event_btnXacNhanMouseClicked

    private void lblQuayLaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuayLaiMouseClicked
        new DangNhap().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblQuayLaiMouseClicked

    private void txtNhapGmailCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNhapGmailCaretUpdate
        noiGmail();
    }//GEN-LAST:event_txtNhapGmailCaretUpdate

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
            java.util.logging.Logger.getLogger(QuenMatKhau.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuenMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMa;
    private javax.swing.JButton btnGuiMa;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblOTP;
    private javax.swing.JLabel lblQuayLai;
    private javax.swing.JTextField txtHienCaptCha;
    private javax.swing.JTextField txtNhapCaptCha;
    private javax.swing.JTextField txtNhapGmail;
    private javax.swing.JTextField txtNhapOTP;
    // End of variables declaration//GEN-END:variables
}
