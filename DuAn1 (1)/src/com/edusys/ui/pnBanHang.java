/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.HoaDonCTDAO;
import com.edusys.dao.HoaDonDAO;
import com.edusys.entity.HoaDon;
import com.edusys.entity.HoaDonCT;
import com.edusys.entity.HoaDonCT2;
import com.edusys.utils.Auth;
import com.edusys.utils.JdbcHelper;
import com.edusys.utils.XDate;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 *
 * @author cutr0
 */
public class pnBanHang extends javax.swing.JPanel implements Printable {

    HoaDonCTDAO hdctDao = new HoaDonCTDAO();
    HoaDonDAO hdDao = new HoaDonDAO();
    int row = 0;
    int image_number;
    int MaTS;

    /**
     * Creates new form pnHoaDon
     */
    public pnBanHang() {
        initComponents();
        init();
        ngayBan();
        txtTenNV.setText(Auth.user.getHoTen());
        txtTongTien.setText(String.valueOf(0));
    }

    void init() {
        row = 0;
        filltableHD();
    }

    private void ngayBan() {
        txtNgayBan.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }

    void tongTien() {
        String sql = "SELECT SUM(THANHTIEN) FROM HOADONCT WHERE MAHD like '" + txtMaHD.getText() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                txtTongTien.setText(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void edit() {
        try {
            String maHD = (String) tblHoaDon.getValueAt(row, 1);
            HoaDon hd = hdDao.selectById(maHD);
            if (hd != null) {
                filltableHDCT();
                setForm(hd);
//                LayDuLieu();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void editCT() {
        try {
            String maHDCT = (String) tblSanPham.getValueAt(row, 1);
            HoaDonCT hdct = hdctDao.selectById(maHDCT);
            if (hdct != null) {
                setFormCT(hdct);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void filltableHDCT() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyWord = String.valueOf(tblHoaDon.getValueAt(row, 1));
            List<HoaDonCT> list = hdctDao.chonhoadon(keyWord);
            for (int i = 0; i < list.size(); i++) {
                HoaDonCT hdct = list.get(i);
                Object[] row = {
                    i + 1,
                    hdct.getMaHDCT(),
                    hdct.getTenSP(),
                    hdct.getTenLoai(),
                    hdct.getGiaBan(),
                    hdct.getSoLuong(),
                    hdct.getTenDvt(),
                    hdct.getPtGiamGia(),
                    hdct.getThanhTien()};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }

    void filltableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = hdDao.selectAll();
            for (int i = 0; i < list.size(); i++) {
                HoaDon hd = list.get(i);
                Object[] row = {
                    i + 1,
                    hd.getMaHD(),
                    hd.getTenNV(),
                    hd.getTenKH(),
                    XDate.toString(hd.getNgayBan(), "dd/MM/yyyy"),
                    hd.getNgayBan()};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }

    void LayDuLieuSP() {
        String sql = "SELECT TENSP, TIENGIAM FROM SANPHAM WHERE MASP LIKE '" + txtMaSP.getText() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                txtTenSP.setText(rs.getString(1));
                txtGiaBan.setText(rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void maKH() {
        String sql = "SELECT MAKH FROM KHACHHANG WHERE SDT LIKE '" + txtSdt.getText() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                lblMaKH.setText(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    HoaDon getForm() {
        maKH();
        HoaDon hd = new HoaDon();
        hd.setMaHD(txtMaHD.getText());
        hd.setMaNV(Auth.user.getMaNV());
        hd.setMaKH(lblMaKH.getText());
        hd.setTongTien(Float.parseFloat(txtTongTien.getText()));
        return hd;
    }

    void setForm(HoaDon hd) {
        txtMaHD.setText(hd.getMaHD());
        txtTenNV.setText((String) tblHoaDon.getValueAt(row, 2));
        txtTenKH.setText((String) tblHoaDon.getValueAt(row, 3));
        txtNgayBan.setText(new SimpleDateFormat("dd/MM/yyyy").format(hd.getNgayBan()));
        txtTongTien.setText(String.valueOf(hd.getTongTien()));
        sdt();
    }

    void sdt() {
        String sql = "SELECT SDT FROM KHACHHANG WHERE TENKH LIKE N'" + txtTenKH.getText() + "'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                txtSdt.setText(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    HoaDonCT getFormCT() {
        HoaDonCT hdct = new HoaDonCT();
        String sql = "SELECT A.MALOAI,B.TENLOAI,C.MADVT,C.TENDVT,A.PTGIAMGIA "
                + "FROM SANPHAM A "
                + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI "
                + "INNER JOIN DVT C ON A.MADVT = C.MADVT  "
                + "WHERE MASP LIKE N'%" + txtMaSP.getText() + "%'";
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                hdct.setMaHDCT(lblMaHDCT.getText());
                hdct.setMaHD(txtMaHD.getText());
                hdct.setMaSP(txtMaSP.getText());
                hdct.setTenSP(txtTenSP.getText());
                hdct.setMaLoai(rs.getString("MALOAI"));
                hdct.setTenLoai(rs.getString("TENLOAI"));
                hdct.setGiaBan(Float.parseFloat(txtGiaBan.getText()));
                hdct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                hdct.setMaDvt(rs.getString("MADVT"));
                hdct.setTenDvt(rs.getString("TENDVT"));
                hdct.setPtGiamGia(rs.getFloat("PTGIAMGIA"));
                hdct.setThanhTien(Float.parseFloat(txtThanhTien.getText()));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return hdct;
    }

    void setFormCT(HoaDonCT hdct) {
        txtMaSP.setText(hdct.getMaSP());
        txtTenSP.setText(hdct.getTenSP());
        txtSoLuong.setText(Integer.toString(hdct.getSoLuong()));
        txtGiaBan.setText(Float.toString(hdct.getGiaBan()));
        txtThanhTien.setText(Float.toString(hdct.getThanhTien()));
    }

    void clearForm() {
        row = 0;
        HoaDon hd = new HoaDon();
        setForm(hd);
    }

    void clearFormCT() {
        HoaDonCT hdct = new HoaDonCT();
        setFormCT(hdct);
    }

    void insert() {
        try {
            HoaDon hd = getForm();
            hdDao.insert(hd);
            filltableHD();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
//            tblHoaDon.getSelectionModel().setSelectionInterval(0, 0);
//            edit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            System.out.println(e);
        }
    }

    void insertCT() {
        try {
            HoaDonCT hdct = getFormCT();
            hdctDao.insert(hdct);
            filltableHDCT();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }

    boolean batLoi() {
        if (txtMaSP.getText().isEmpty()) {
            return false;
        }
        if (txtTenSP.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void GoiYTenKH() {
        if (txtTenKH.getText().isEmpty()) {
            cboTenKH.hidePopup();
            cboTenKH.removeAllItems();
        } else {
            String sql1 = "SELECT TENKH FROM KHACHHANG WHERE TENKH LIKE N'%" + txtTenKH.getText() + "%' ORDER BY TENKH DESC";
            try {
                cboTenKH.removeAllItems();
                ResultSet rs = JdbcHelper.query(sql1);
                while (rs.next()) {
                    cboTenKH.addItem(rs.getString(1));
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
            cboTenKH.showPopup();
        }
    }

    void QR() {
        try {
            InputStream barcodeInputStream = new FileInputStream("D:\\DuAn1NetBean\\DuAn1\\Anh\\" + image_number + ".png");
            BufferedImage barBufferedImage = ImageIO.read(barcodeInputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(barBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Reader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);
            txtMaSP.setText(result.getText());
            String sql = "SELECT TENSP, TIENGIAM FROM SANPHAM WHERE MASP LIKE '" + result.getText() + "'";
            try {
                ResultSet rs = JdbcHelper.query(sql);
                while (rs.next()) {
                    txtTenSP.setText(rs.getString(1));
                    txtGiaBan.setText(rs.getString(2));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            e.getStackTrace();
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
        jLabel1 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        btnThen = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtNgayBan = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        lblTongTien123 = new javax.swing.JLabel();
        cboTenKH = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtTienThoi = new javax.swing.JTextField();
        txtThanhToan = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtThanhTien = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnThemSP = new javax.swing.JButton();
        txtMaSP = new javax.swing.JTextField();
        btnQR = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        lblTienGiamGia = new javax.swing.JLabel();
        lblDisplay = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        lblMaHDCT = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin khách hàng"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Tên khách hàng");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        txtTenKH.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTenKH.setToolTipText("");
        txtTenKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTenKHCaretUpdate(evt);
            }
        });
        jPanel1.add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 420, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Số điện thoại");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 140, -1));

        txtSdt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtSdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 440, 40));

        btnThen.setBackground(new java.awt.Color(106, 139, 255));
        btnThen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThen.setForeground(new java.awt.Color(255, 255, 255));
        btnThen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonThem.png"))); // NOI18N
        btnThen.setText("Thêm");
        btnThen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThenMouseClicked(evt);
            }
        });
        jPanel1.add(btnThen, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 120, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setText("Ngày bán");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 110, -1, -1));

        txtNgayBan.setEditable(false);
        txtNgayBan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtNgayBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 130, 200, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setText("Mã hóa đơn");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, -1, -1));

        txtMaHD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 200, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel17.setText("Tên nhân viên");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        txtTenNV.setEditable(false);
        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel1.add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 420, 40));

        lblTongTien123.setFont(new java.awt.Font("Segoe UI", 0, 1)); // NOI18N
        lblTongTien123.setText("0");
        jPanel1.add(lblTongTien123, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, -1, -1));

        cboTenKH.setMaximumRowCount(200);
        cboTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenKHActionPerformed(evt);
            }
        });
        jPanel1.add(cboTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 420, 40));

        jButton6.setBackground(new java.awt.Color(106, 139, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonCapNhat.png"))); // NOI18N
        jButton6.setText("Cập nhật");
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 120, 40));

        jButton7.setBackground(new java.awt.Color(106, 139, 255));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonXoa.png"))); // NOI18N
        jButton7.setText("Xóa");
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 120, 40));

        jButton8.setBackground(new java.awt.Color(106, 139, 255));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonLamMoi.png"))); // NOI18N
        jButton8.setText("Làm mới");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 120, 40));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 950, 250));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel21.setText("Tiền thanh toán");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel22.setText("Tiền thối");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        txtTienThoi.setEditable(false);
        txtTienThoi.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel5.add(txtTienThoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 210, 40));

        txtThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhToanActionPerformed(evt);
            }
        });
        jPanel5.add(txtThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 210, 40));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel23.setText("Tổng tiền");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        txtTongTien.setEditable(false);
        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTongTien.setText("0");
        jPanel5.add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 210, 40));

        jButton1.setText("Thanh toán");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, -1, -1));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 410, 230, 650));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HDCT", "Tên sản phẩm", "Tên loại", "Đơn giá", "Số lượng", "Đơn vị tính", "%  Giảm giá", "Thành tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class
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
        tblSanPham.setRowHeight(30);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setMinWidth(40);
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(40);
            tblSanPham.getColumnModel().getColumn(1).setMinWidth(100);
            tblSanPham.getColumnModel().getColumn(1).setMaxWidth(100);
            tblSanPham.getColumnModel().getColumn(2).setMinWidth(200);
            tblSanPham.getColumnModel().getColumn(2).setMaxWidth(200);
            tblSanPham.getColumnModel().getColumn(3).setMinWidth(80);
            tblSanPham.getColumnModel().getColumn(3).setMaxWidth(80);
            tblSanPham.getColumnModel().getColumn(4).setMinWidth(100);
            tblSanPham.getColumnModel().getColumn(4).setMaxWidth(100);
            tblSanPham.getColumnModel().getColumn(5).setMinWidth(60);
            tblSanPham.getColumnModel().getColumn(5).setPreferredWidth(40);
            tblSanPham.getColumnModel().getColumn(5).setMaxWidth(60);
            tblSanPham.getColumnModel().getColumn(7).setMinWidth(80);
            tblSanPham.getColumnModel().getColumn(7).setMaxWidth(80);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 890, 650));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Dữ liệu hóa đơn"));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Tên nhân viên", "Tên khách hàng", "Ngày bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(30);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(32);
            tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(32);
            tblHoaDon.getColumnModel().getColumn(1).setMinWidth(75);
            tblHoaDon.getColumnModel().getColumn(1).setMaxWidth(75);
            tblHoaDon.getColumnModel().getColumn(4).setMinWidth(80);
            tblHoaDon.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 450, 1020));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtThanhTien.setEditable(false);
        txtThanhTien.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel2.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 120, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Mã Sản phẩm");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 20));

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        jPanel2.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 60, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Thành tiền");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, -1, 20));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Tên Sản phẩm");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, 20));

        txtTenSP.setEditable(false);
        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel2.add(txtTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 300, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Số lượng");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, 20));

        txtGiaBan.setEditable(false);
        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jPanel2.add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 100, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Đơn giá");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, -1, 20));

        btnThemSP.setBackground(new java.awt.Color(106, 139, 255));
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonThem.png"))); // NOI18N
        btnThemSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSPMouseClicked(evt);
            }
        });
        jPanel2.add(btnThemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 30, 40, 40));

        txtMaSP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 200, 40));

        btnQR.setBackground(new java.awt.Color(106, 139, 255));
        btnQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/QRCodeTrang.png"))); // NOI18N
        btnQR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQRMouseClicked(evt);
            }
        });
        jPanel2.add(btnQR, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 40, 40));

        jButton2.setBackground(new java.awt.Color(106, 139, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonCapNhat.png"))); // NOI18N
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 30, 40, 40));

        jButton4.setBackground(new java.awt.Color(106, 139, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonXoa.png"))); // NOI18N
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 30, 40, 40));

        jButton5.setBackground(new java.awt.Color(106, 139, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonLamMoi.png"))); // NOI18N
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 30, 40, 40));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 1130, 100));

        lblTienGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 1)); // NOI18N
        lblTienGiamGia.setForeground(new java.awt.Color(255, 255, 255));
        add(lblTienGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 1010, -1, -1));

        lblDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(lblDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(1430, 50, 170, 240));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Quét mã QR hoặc mã vạch");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1430, 30, -1, -1));

        lblMaKH.setFont(new java.awt.Font("Segoe UI", 0, 1)); // NOI18N
        lblMaKH.setForeground(new java.awt.Color(255, 255, 255));
        lblMaKH.setText("jLabel3");
        add(lblMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        lblMaHDCT.setFont(new java.awt.Font("Segoe UI", 0, 1)); // NOI18N
        lblMaHDCT.setForeground(new java.awt.Color(255, 255, 255));
        lblMaHDCT.setText("jLabel3");
        add(lblMaHDCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnThenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThenMouseClicked
        insert();
    }//GEN-LAST:event_btnThenMouseClicked

    private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMousePressed
        if (evt.getClickCount() == 2) {
            row = tblHoaDon.getSelectedRow();
            edit();
            tongTien();
        }
    }//GEN-LAST:event_tblHoaDonMousePressed

    private void btnThemSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPMouseClicked
        if (batLoi() == true) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream("src\\MaTuSinh\\MAHDCT.dat"))) {
                MaTS = dis.readInt();
                MaTS += 1;
                if (MaTS < 10) {
                    lblMaHDCT.setText(String.valueOf("HDCT00" + MaTS));
                } else if (MaTS < 100) {
                    lblMaHDCT.setText(String.valueOf("HDCT0" + MaTS));
                } else if (MaTS < 1000) {
                    lblMaHDCT.setText(String.valueOf("HDCT" + MaTS));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            insertCT();
            tongTien();
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src\\MaTuSinh\\MAHDCT.dat"))) {
                dos.writeInt(MaTS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            clearFormCT();
        }
    }//GEN-LAST:event_btnThemSPMouseClicked

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        if (evt.getClickCount() == 2) {
            row = tblSanPham.getSelectedRow();
            editCT();
            btnThemSP.setIcon(new ImageIcon("src\\com\\edusys\\icons\\capnhat.png"));
        }
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        LayDuLieuSP();
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhToanActionPerformed

    }//GEN-LAST:event_txtThanhToanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Float khachTra = Float.parseFloat(txtThanhToan.getText());
        Float tongTien = Float.parseFloat(txtTongTien.getText());
        txtTienThoi.setText(String.valueOf(khachTra - tongTien));
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable((Printable) this, getPageFormat(pj));
        pj.setPrintable((Printable) this);
        boolean ok = pj.printDialog();
        if (ok) {
            try {
                pj.print();
            } catch (PrinterException ex) {
            }
            JOptionPane.showMessageDialog(this, "In hóa đơn thành công");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTenKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTenKHCaretUpdate
        GoiYTenKH();
    }//GEN-LAST:event_txtTenKHCaretUpdate

    private void cboTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenKHActionPerformed
        txtTenKH.setText((String) cboTenKH.getSelectedItem());
        sdt();
    }//GEN-LAST:event_cboTenKHActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        clearForm();
    }//GEN-LAST:event_jButton5MouseClicked

    private void btnQRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQRMouseClicked
        webSource = new VideoCapture(0);
        myThread = new DaemonThread(lblDisplay);
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();
        int option = JOptionPane.showConfirmDialog(this, "Bạn có muốn quét mã QR hay mã vạch không?");
        if (option == 0) {
            try {
                File file = new File("Anh");
                boolean flag = true;
                if (!file.isDirectory()) {
                    flag = file.mkdir();
                }
                if (!flag) {
                    throw new Exception("Thư mục không tồn tại");
                }
                image_number = 1 + RAND.nextInt(999999999);
                IMAGE_FILE_NAME = file.getAbsolutePath() + "\\" + image_number + ".png";
                Highgui.imwrite(IMAGE_FILE_NAME, frame);
                get_image_file = file;
                webCam(lblDisplay);
                QR();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm");
                dung();
            }
        }
        dung();

    }//GEN-LAST:event_btnQRMouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8MouseClicked

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        int a = Integer.parseInt(txtSoLuong.getText());
        Float b = Float.parseFloat(txtGiaBan.getText());
        txtThanhTien.setText(String.valueOf(a * b));
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private DaemonThread myThread = null;
    private VideoCapture webSource = null;
    private final Mat frame = new Mat(1000, 1000, 1);
    private final MatOfByte mem = new MatOfByte();

    private class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;
        private JLabel lblDisplay;

        public DaemonThread(JLabel displayLabel) {
            this.lblDisplay = displayLabel;
        }

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            Highgui.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

                            BufferedImage buff = (BufferedImage) im;
                            Graphics g = lblDisplay.getGraphics();

                            if (g.drawImage(buff, 1, 1, lblDisplay.getWidth(), lblDisplay.getHeight(), null)) {
                                if (runnable == false) {
                                    this.wait();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    static {
        File file = new File("lib\\opencv_java249.dll");
        System.load(file.getAbsolutePath());
    }

    void dung() {
        if (myThread != null) {
            if (myThread.runnable == true) {
                myThread.runnable = false;
                webSource.release();
            }
        }
    }

    private static File get_image_file;
    private static final SecureRandom RAND = new SecureRandom();
    private static String IMAGE_FILE_NAME = null;

    void webCam(JLabel image) {

        try {
            dung();
            if (get_image_file != null) {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(IMAGE_FILE_NAME).getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
                image.setIcon(imageIcon);
            }
        } catch (Exception e) {
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnThen;
    private javax.swing.JComboBox<String> cboTenKH;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDisplay;
    private javax.swing.JLabel lblMaHDCT;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblTienGiamGia;
    private javax.swing.JLabel lblTongTien123;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNgayBan;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtThanhToan;
    private javax.swing.JTextField txtTienThoi;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
   private DecimalFormat df = new DecimalFormat("###,###");

    public DefaultTableModel tableModel = new DefaultTableModel() {
    };

    private java.util.ArrayList<HoaDonCT2> itemList = new java.util.ArrayList<>();

    private java.util.ArrayList<HoaDonCT2> createItemList() {
        java.util.ArrayList<HoaDonCT2> iL = new java.util.ArrayList<HoaDonCT2>();
        HoaDonCT2 itm = null;
        for (int i = 0; i < tblSanPham.getRowCount(); i++) {
            String tenSP = tblSanPham.getValueAt(i, 2).toString();
            String giaBan = tblSanPham.getValueAt(i, 4).toString();
            String soLuong = tblSanPham.getValueAt(i, 5).toString();
            itm = new HoaDonCT2(tenSP, giaBan, soLuong);
            iL.add(itm);
        }
        return iL;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Float totalAmount = 0.0F;
        Float change = 0.0F;
        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {

            Graphics2D g2d = (Graphics2D) graphics;
            double width = pageFormat.getImageableWidth();
            g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

            FontMetrics metrics = g2d.getFontMetrics(new Font("Arial", Font.BOLD, 7));
            try {
                int y = 15;
                int yShift = 15;
                int headerRectHeight = 15;
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
                double wh = pageFormat.getImageableWidth();
                double ht = pageFormat.getImageableHeight();
                g2d.drawImage(null, 0, 0, (int) wh, (int) ht, null);
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 30));
                y += yShift + 10;
                g2d.drawString("------------------------------------------", 10, y);
                y += yShift + 10;
                g2d.drawString("          CỬA HÀNG BÁCH HÓA HOYO", 10, y);
                y += yShift + 10;
                g2d.drawString("          Địa chỉ Nguyễn Văn Linh", 10, y);
                y += yShift + 10;
                g2d.drawString("            Xuân Khánh, Cần Thơ", 10, y);
                y += 3 * yShift;
                g2d.drawString("  Nhân viên: " + txtTenNV.getText(), 10, y);
                y += yShift + 10;
                g2d.drawString("  Khách hàng: " + txtTenKH.getText(), 10, y);
                y += yShift + 10;
                g2d.drawString("  Mã hóa đơn: " + txtMaHD.getText(), 10, y);
                y += yShift + 10;
                g2d.drawString("  Thời gian: " + txtNgayBan.getText(), 10, y);
                y += 2 * yShift;
                g2d.drawString("------------------------------------------", 10, y);
                y += headerRectHeight;
                y += yShift;
                g2d.drawString("  Số lượng       Giá bán       Thành tiền", 10, y);
                y += yShift + 10;
                g2d.drawString("------------------------------------------", 10, y);
                y += headerRectHeight;

                for (HoaDonCT2 item : createItemList()) {
                    y += yShift;
                    g2d.drawString(" " + item.getTenSP() + "                    ", 10, y);
                    y += 2 * yShift;
                    g2d.drawString("     " + item.getSoLuong() + "            " + String.valueOf(df.format(Float.parseFloat(item.getGiaBan()))), 5, y);
                    float soLuong = Float.parseFloat(item.getSoLuong());
                    float giaBan = Float.parseFloat(item.getGiaBan());
                    float totalPrice = (float) (soLuong * giaBan);
                    g2d.drawString(String.valueOf(df.format(totalPrice)), 600, y);
                    y += 2 * yShift;
                    totalAmount = Float.parseFloat(txtTongTien.getText());

                }
                Float cash = Float.parseFloat(txtThanhToan.getText());

                g2d.drawString("---------------------------------------", 10, y);
                y += 2 * yShift;
                g2d.drawString("  Tổng tiền    :                 " + String.valueOf(df.format(totalAmount)) + "   ", 10, y);
                y += 2 * yShift;
                g2d.drawString("------------------------------------------", 10, y);
                y += 2 * yShift;
                g2d.drawString("  Thanh toán   :                 " + String.valueOf(df.format(cash)) + "   ", 10, y);
                y += 2 * yShift;
                g2d.drawString("------------------------------------------", 10, y);
                y += 2 * yShift;
                g2d.drawString("  Tiền thối lại:                 " + String.valueOf(df.format(Float.parseFloat(txtTienThoi.getText()))) + "   ", 10, y);
                y += 2 * yShift;
                g2d.drawString("******************************************", 10, y);
                y += 2 * yShift;
                g2d.drawString("            Cám ơn đã mua hàng!      ", 10, y);
                y += 2 * yShift;
                g2d.drawString("    Liên hệ: toanvkpc05433@fpt.edu.vn", 10, y);
                y += 2 * yShift;
                g2d.drawString("         Số điện thoại: 0763889837   ", 10, y);
                y += 2 * yShift;
                g2d.drawString("******************************************", 10, y);

            } catch (Exception e) {
                e.printStackTrace();
            }
            result = PAGE_EXISTS;
        }
        return result;
    }

    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double paperWidthCM = 58;
        double width = cm_to_pp(paperWidthCM);
        double paperHeithCM = 200;
        double height = cm_to_pp(paperHeithCM);
//        double height = pf.getImageableHeight();
        paper.setSize(width, height);

        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393701);  // 1 cm = 0.393701 inch
    }

    protected static double toPPI(double inch) {
        return inch * 72d;  // 1 inch = 72 pixels
    }
}
