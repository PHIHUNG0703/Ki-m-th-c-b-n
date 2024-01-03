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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author cutr0
 */
public class formTrangChu extends javax.swing.JFrame {

    private JPanel childPanel;
    boolean moMenu = true;
    int height = 120;
    int soTrang = 1;
    int width = 300;

    /**
     * Creates new form TrangChu
     */
    public formTrangChu() {
        initComponents();
        setExtendedState(formTrangChu.MAXIMIZED_BOTH);
        setIconImage(XImage.getAppIcon());
        vaiTroTenMenu();
        moPn(new pnBanHang());
    }

    void moTrangChu() {
        soTrang = 1;
        moPn(new pnTrangChu());
        pnTC.setBackground(new Color(153, 204, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));
    }

    void moNhanVien() {
        soTrang = 2;
        moPn(new pnNhanVien());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(153, 204, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));
    }

    void moKhachHang() {
        soTrang = 3;
        moPn(new pnKhachHang());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(153, 204, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));
    }

    void moDoiMatKhau() {
        soTrang = 4;
        moPn(new pnDoiMatKhau());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(153, 204, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));
    }

    void moSanPham() {
        soTrang = 5;
        moPn(new pnSanPham());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(153, 204, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));
    }

    void moNhaCungCap() {
        soTrang = 6;
        moPn(new pnNhaCungCap());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(153, 204, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));
    }

    void moPhieuNhap() {
        soTrang = 7;
        moPn(new pnPhieuNhap());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(153, 204, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));

    }

    void moHoaDon() {
        soTrang = 8;
        moPn(new pnBanHang());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(153, 204, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));
    }

    void moThongKe() {
        soTrang = 9;
        moPn(new pnThongKe(this, moMenu));
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(153, 204, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(106, 139, 255));

    }

    void moLoaiSanPham() {
        soTrang = 10;
        moPn(new pnLoaiSanPham());
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(106, 139, 255));
        pnLSP.setBackground(new Color(153, 204, 255));
    }

    void openMenu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 50; i < height; i++) {
                    pnMini.setSize(width, i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(formTrangChu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    void closeMenu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = height; i > 50; i--) {
                    pnMini.setSize(width, i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(formTrangChu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    void vaiTroTenMenu() {
        NhanVien nv = new NhanVien();
        try {
            if (Auth.user.getMaNV() != null) {
                try {
                    String sql = "select TENNV, VAITRO, HINH from NHANVIEN WHERE MANV = '" + Auth.user.getMaNV() + "'";
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
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chưa đăng nhập");
            System.exit(0);
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
        lblAvt = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        pnTC = new javax.swing.JPanel();
        lblTrangChu = new javax.swing.JLabel();
        pnNV = new javax.swing.JPanel();
        lblNhanVien = new javax.swing.JLabel();
        pnTK = new javax.swing.JPanel();
        lblThongKe = new javax.swing.JLabel();
        pnDX = new javax.swing.JPanel();
        lblDangXuat = new javax.swing.JLabel();
        pnDMK = new javax.swing.JPanel();
        lblDoiMatKhau = new javax.swing.JLabel();
        pnNCC = new javax.swing.JPanel();
        lblNhaCungCap = new javax.swing.JLabel();
        pnPN = new javax.swing.JPanel();
        lblPhieuNhap = new javax.swing.JLabel();
        pnMini = new javax.swing.JPanel();
        pnSP = new javax.swing.JPanel();
        lblMoMenu = new javax.swing.JLabel();
        lblSanPham = new javax.swing.JLabel();
        pnLSP = new javax.swing.JPanel();
        lblLoaiSP = new javax.swing.JLabel();
        pnKH = new javax.swing.JPanel();
        lblKhachHang = new javax.swing.JLabel();
        pnHD = new javax.swing.JPanel();
        lblHoaDon = new javax.swing.JLabel();
        lblDangXuat1 = new javax.swing.JLabel();
        pnMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnMenu.setBackground(new java.awt.Color(106, 139, 255));
        pnMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAvt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        pnMenu.add(lblAvt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 90, 120));

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenNV.setForeground(new java.awt.Color(255, 255, 255));
        lblTenNV.setText("                        ");
        pnMenu.add(lblTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 130, -1));

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblVaiTro.setForeground(new java.awt.Color(255, 255, 255));
        lblVaiTro.setText("            ");
        pnMenu.add(lblVaiTro, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, -1, -1));

        pnTC.setBackground(new java.awt.Color(153, 204, 255));
        pnTC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTrangChu.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lblTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-home-24.png"))); // NOI18N
        lblTrangChu.setText("Trang chủ");
        lblTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseClicked(evt);
            }
        });
        pnTC.add(lblTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnTC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 300, 50));

        pnNV.setBackground(new java.awt.Color(106, 139, 255));
        pnNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnNV.add(lblNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 300, 50));

        pnTK.setBackground(new java.awt.Color(106, 139, 255));
        pnTK.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnTK.add(lblThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 300, 50));

        pnDX.setBackground(new java.awt.Color(106, 139, 255));
        pnDX.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnDX.add(lblDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnDX, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 800, 300, 50));

        pnDMK.setBackground(new java.awt.Color(106, 139, 255));
        pnDMK.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnDMK.add(lblDoiMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnDMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 740, 300, 50));

        pnNCC.setBackground(new java.awt.Color(106, 139, 255));
        pnNCC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNhaCungCap.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblNhaCungCap.setForeground(new java.awt.Color(255, 255, 255));
        lblNhaCungCap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-partner-24.png"))); // NOI18N
        lblNhaCungCap.setText("Nhà cung cấp");
        lblNhaCungCap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNhaCungCapMouseClicked(evt);
            }
        });
        pnNCC.add(lblNhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 300, 50));

        pnPN.setBackground(new java.awt.Color(106, 139, 255));
        pnPN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPhieuNhap.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        lblPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-rules-24.png"))); // NOI18N
        lblPhieuNhap.setText("Phiếu nhập");
        lblPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPhieuNhapMouseClicked(evt);
            }
        });
        pnPN.add(lblPhieuNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 300, 50));

        pnMini.setBackground(new java.awt.Color(106, 139, 255));
        pnMini.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSP.setBackground(new java.awt.Color(106, 139, 255));
        pnSP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonXuong.png"))); // NOI18N
        lblMoMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMoMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMoMenuMouseClicked(evt);
            }
        });
        pnSP.add(lblMoMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 15, -1, -1));

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
        pnSP.add(lblSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMini.add(pnSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 50));

        pnLSP.setBackground(new java.awt.Color(106, 139, 255));
        pnLSP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLoaiSP.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblLoaiSP.setForeground(new java.awt.Color(255, 255, 255));
        lblLoaiSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/loaisanpham.png"))); // NOI18N
        lblLoaiSP.setText("Loại sản phẩm");
        lblLoaiSP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLoaiSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoaiSPMouseClicked(evt);
            }
        });
        pnLSP.add(lblLoaiSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMini.add(pnLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 300, 50));

        pnMenu.add(pnMini, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 300, 50));

        pnKH.setBackground(new java.awt.Color(106, 139, 255));
        pnKH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnKH.add(lblKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, 300, 50));

        pnHD.setBackground(new java.awt.Color(106, 139, 255));
        pnHD.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHoaDon.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-list-24.png"))); // NOI18N
        lblHoaDon.setText("Hoá đơn");
        lblHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHoaDonMouseClicked(evt);
            }
        });
        pnHD.add(lblHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        pnMenu.add(pnHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 300, 50));

        lblDangXuat1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblDangXuat1.setForeground(new java.awt.Color(255, 255, 255));
        lblDangXuat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-open-pane-24.png"))); // NOI18N
        lblDangXuat1.setText("Thoát");
        lblDangXuat1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDangXuat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangXuat1MouseClicked(evt);
            }
        });
        pnMenu.add(lblDangXuat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 870, -1, -1));

        getContentPane().add(pnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 1080));

        pnMain.setBackground(new java.awt.Color(255, 255, 255));
        pnMain.setMaximumSize(new java.awt.Dimension(1620, 1080));
        pnMain.setMinimumSize(new java.awt.Dimension(1620, 1080));
        pnMain.setPreferredSize(new java.awt.Dimension(1620, 1080));
        getContentPane().add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1620, 1080));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseClicked
        moTrangChu();
    }//GEN-LAST:event_lblTrangChuMouseClicked

    private void lblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanVienMouseClicked
        moNhanVien();
    }//GEN-LAST:event_lblNhanVienMouseClicked

    private void lblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhachHangMouseClicked
        moKhachHang();
    }//GEN-LAST:event_lblKhachHangMouseClicked

    private void lblDoiMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseClicked
        moDoiMatKhau();
    }//GEN-LAST:event_lblDoiMatKhauMouseClicked

    private void lblDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseClicked
        pnTC.setBackground(new Color(106, 139, 255));
        pnNV.setBackground(new Color(106, 139, 255));
        pnNCC.setBackground(new Color(106, 139, 255));
        pnPN.setBackground(new Color(106, 139, 255));
        pnKH.setBackground(new Color(106, 139, 255));
        pnSP.setBackground(new Color(106, 139, 255));
        pnHD.setBackground(new Color(106, 139, 255));
        pnTK.setBackground(new Color(106, 139, 255));
        pnDMK.setBackground(new Color(106, 139, 255));
        pnDX.setBackground(new Color(153, 204, 255));
        int confirmResponse = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất không");
        if (confirmResponse == JOptionPane.YES_OPTION) {
            new formDangNhap().setVisible(true);
            this.dispose();
        } else {
            pnDX.setBackground(new Color(106, 139, 255));
            switch (soTrang) {
                case 1 ->
                    moTrangChu();
                case 2 ->
                    moNhanVien();
                case 3 ->
                    moNhaCungCap();
                case 4 ->
                    moDoiMatKhau();
                case 5 ->
                    moSanPham();
                case 6 ->
                    moNhaCungCap();  // Handling case 6 and 10
                case 7 ->
                    moPhieuNhap();
                case 8 ->
                    moHoaDon();
                case 9 ->
                    moThongKe();
                default ->
                    moLoaiSanPham();
            }
        }
    }//GEN-LAST:event_lblDangXuatMouseClicked

    private void lblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPhamMouseClicked
        moSanPham();
    }//GEN-LAST:event_lblSanPhamMouseClicked

    private void lblNhaCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhaCungCapMouseClicked
        moNhaCungCap();
    }//GEN-LAST:event_lblNhaCungCapMouseClicked

    private void lblPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPhieuNhapMouseClicked
        moPhieuNhap();
    }//GEN-LAST:event_lblPhieuNhapMouseClicked

    private void lblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoaDonMouseClicked
        moHoaDon();
    }//GEN-LAST:event_lblHoaDonMouseClicked

    private void lblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseClicked
        if (Auth.user.isVaiTro() == false) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xem thống kê");
        } else {
            moThongKe();
        }
    }//GEN-LAST:event_lblThongKeMouseClicked

    private void lblMoMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMoMenuMouseClicked
        if (moMenu == true) {
            openMenu();
            lblMoMenu.setIcon(new ImageIcon("src\\com\\edusys\\icons\\0buttonLen.png"));
            moMenu = false;
        } else if (moMenu == false) {
            lblMoMenu.setIcon(new ImageIcon("src\\com\\edusys\\icons\\0buttonXuong.png"));
            closeMenu();
            moMenu = true;
        }
    }//GEN-LAST:event_lblMoMenuMouseClicked

    private void lblLoaiSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoaiSPMouseClicked
        moLoaiSanPham();
    }//GEN-LAST:event_lblLoaiSPMouseClicked

    private void lblDangXuat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuat1MouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblDangXuat1MouseClicked

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
            java.util.logging.Logger.getLogger(formTrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formTrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formTrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formTrangChu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formTrangChu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAvt;
    private javax.swing.JLabel lblDangXuat;
    private javax.swing.JLabel lblDangXuat1;
    private javax.swing.JLabel lblDoiMatKhau;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblLoaiSP;
    private javax.swing.JLabel lblMoMenu;
    private javax.swing.JLabel lblNhaCungCap;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblPhieuNhap;
    private javax.swing.JLabel lblSanPham;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblTrangChu;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JPanel pnDMK;
    private javax.swing.JPanel pnDX;
    private javax.swing.JPanel pnHD;
    private javax.swing.JPanel pnKH;
    private javax.swing.JPanel pnLSP;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnMenu;
    private javax.swing.JPanel pnMini;
    private javax.swing.JPanel pnNCC;
    private javax.swing.JPanel pnNV;
    private javax.swing.JPanel pnPN;
    private javax.swing.JPanel pnSP;
    private javax.swing.JPanel pnTC;
    private javax.swing.JPanel pnTK;
    private javax.swing.ButtonGroup rdoGioiTinh;
    private javax.swing.ButtonGroup rdoVaiTro;
    // End of variables declaration//GEN-END:variables
}
