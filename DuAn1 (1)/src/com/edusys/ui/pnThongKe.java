/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.edusys.ui;

import com.edusys.utils.JdbcHelper;
import com.edusys.utils.XDate;
import com.edusys.utils.XExcel;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cutr0
 */
public class pnThongKe extends javax.swing.JPanel {

    /**
     * Creates new form pnThongKe
     */
    public pnThongKe(java.awt.Frame parent, boolean modal) {
        initComponents();
        hienThiSL();
        selectTab(0);
        fillDanhSPBR();
    }

    public void hienThiSL() {
        updateLabelFromQuery("SELECT COUNT(*) FROM NHANVIEN", lblSLNV);
        updateLabelFromQuery("SELECT COUNT(*) FROM KHACHHANG", lblSLKH);
        updateLabelFromQuery("SELECT COUNT(*) FROM SANPHAM", lblSLMH);
        updateLabelFromQuery("SELECT COUNT(*) FROM HOADON", lblSLHD);
        updateLabelFromQuery("SELECT COUNT(*) FROM PHIEUNHAP", lblSLPN);
        updateLabelFromQueryWithFormat("SELECT SUM(THANHTIEN) FROM HOADONCT", lblDoanhThu);
    }

    private void updateLabelFromQuery(String sql, JLabel label) {
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                label.setText(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLabelFromQueryWithFormat(String sql, JLabel label) {
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                double value = rs.getDouble(1);
                DecimalFormat decimalFormat = new DecimalFormat("###,###.## VNĐ");
                label.setText(decimalFormat.format(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addItemToCombobox(JComboBox<String> comboBox, int start, int end) {
        for (int i = start; i <= end; i++) {
            comboBox.addItem(String.valueOf(i));
        }
    }

    public void selectTab(int index) {
        tabs.setSelectedIndex(index);
    }

    void fillDanhSPBR() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
            model.setRowCount(0);
            String sql = "SELECT *, C.TENSP,B.NGAYBAN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP ORDER BY B.NGAYBAN DESC";
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TENSP"),
                    XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                    new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                    rs.getInt("SOLUONG"),
                    new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                    new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAM")) + " VNĐ",
                    new DecimalFormat("###,###.##").format(rs.getDouble("THANHTIEN")) + " VNĐ"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tuNgayDenNgay() {
        try {
            String tuNgay = txtTuNgay.getText();
            String tuThang = txtTuThang.getText();
            String tuNam = txtTuNam.getText();
            String denNgay = txtDenNgay.getText();
            String denThang = txtDenThang.getText();
            String denNam = txtDenNam.getText();
            if (tuNgay.isEmpty() || tuThang.isEmpty() || tuNam.isEmpty()
                    || denNgay.isEmpty() || denThang.isEmpty() || denNam.isEmpty()
                    || tuNgay.equalsIgnoreCase("") || tuThang.equalsIgnoreCase("")
                    || tuNam.equalsIgnoreCase("") || denNgay.equalsIgnoreCase("")
                    || denThang.equalsIgnoreCase("") || denNam.equalsIgnoreCase("")) {
                fillDanhSPBR();
            } else {
                try {
                    DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
                    model.setRowCount(0);
                    String sql = "SELECT C.TENSP,B.NGAYBAN,A.GIABAN,A.SOLUONG,A.PTGIAMGIA,A.TIENGIAMGIA,A.THANHTIEN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP WHERE B.NGAYBAN >= '" + tuNam + "-" + tuThang + "-" + tuNgay + "' AND B.NGAYBAN <= '" + denNam + "-" + denThang + "-" + denNgay + "' ORDER BY B.NGAYBAN DESC";
                    ResultSet rs = JdbcHelper.query(sql);
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("TENSP"),
                            XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                            new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                            rs.getInt("SOLUONG"),
                            new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                            new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAMGIA")) + " VNĐ",
                            new DecimalFormat("###,###.##").format(rs.getDouble("THANHTIEN")) + " VNĐ"});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tuDTDenDT() {
        String tu = txtDTtu.getText();
        String den = txtDTden.getText();
        if (tu.isEmpty() && den.isEmpty()) {
            fillDanhSPBR();
        } else {
            try {
                DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
                model.setRowCount(0);
                String sql = "SELECT C.TENSP,B.NGAYBAN,A.GIABAN,A.SOLUONG,A.PTGIAMGIA,A.TIENGIAMGIA,A.THANHTIEN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP WHERE A.THANHTIEN >= '" + tu + "' AND A.THANHTIEN <= '" + den + "' ORDER BY B.NGAYBAN DESC";
                ResultSet rs = JdbcHelper.query(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("TENSP"),
                        XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                        new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                        rs.getInt("SOLUONG"),
                        new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                        new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAMGIA")) + " VNĐ",
                        new DecimalFormat("###,###.##").format(rs.getDouble("THANHTIEN")) + " VNĐ"});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void banChay() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
            model.setRowCount(0);
            String sql = "SELECT C.TENSP,B.NGAYBAN,A.GIABAN,A.SOLUONG,A.PTGIAMGIA,A.TIENGIAMGIA,A.THANHTIEN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP ORDER BY A.SOLUONG DESC";
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TENSP"),
                    XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                    new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                    rs.getInt("SOLUONG"),
                    new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                    new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAMGIA")) + " VNĐ",
                    new DecimalFormat("###,###.##").format(rs.getDouble("THANHTIEN")) + " VNĐ"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void doanhThu() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
            model.setRowCount(0);
            String sql = "SELECT C.TENSP,B.NGAYBAN,A.GIABAN,A.SOLUONG,A.PTGIAMGIA,A.TIENGIAMGIA,A.THANHTIEN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP ORDER BY A.THANHTIEN DESC";
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TENSP"),
                    XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                    new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                    rs.getInt("SOLUONG"),
                    new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                    new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAMGIA")) + " VNĐ",
                    new DecimalFormat("###,###.##").format(rs.getDouble("THANHTIEN")) + " VNĐ"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cot() {
        new BieuDoCot(null, true).setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblSLKH = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblDoanhThu = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblSLNV = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblSLPN = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblSLHD = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblSLMH = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDoanhThu = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtDTtu = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDTden = new javax.swing.JTextField();
        lblThongBaoLoi = new javax.swing.JLabel();
        txtTuNgay = new javax.swing.JTextField();
        txtTuThang = new javax.swing.JTextField();
        txtTuNam = new javax.swing.JTextField();
        txtDenNgay = new javax.swing.JTextField();
        txtDenThang = new javax.swing.JTextField();
        txtDenNam = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1620, 1080));
        setMinimumSize(new java.awt.Dimension(1620, 1080));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tabs.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(106, 139, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("SỐ LƯỢNG KHÁCH HÀNG");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-user-96.png"))); // NOI18N
        jPanel7.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblSLKH.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSLKH.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.add(lblSLKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 30));

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 400, 120));

        jPanel8.setBackground(new java.awt.Color(106, 139, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("TỔNG DOANH THU");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-parcel-96.png"))); // NOI18N
        jPanel8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        jPanel8.add(lblDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 280, 30));

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 220, 400, 120));

        jPanel9.setBackground(new java.awt.Color(106, 139, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SỐ LƯỢNG NHÂN VIÊN");
        jPanel9.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-user-account-96.png"))); // NOI18N
        jPanel9.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblSLNV.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSLNV.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(lblSLNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 30));

        jPanel6.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 400, 120));

        jPanel10.setBackground(new java.awt.Color(106, 139, 255));
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SỐ LƯỢNG PHIẾU NHẬP");
        jPanel10.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-user-account-96.png"))); // NOI18N
        jPanel10.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblSLPN.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSLPN.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.add(lblSLPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 30));

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 220, 400, 120));

        jPanel11.setBackground(new java.awt.Color(106, 139, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("SỐ LƯỢNG HÓA ĐƠN");
        jPanel11.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-user-account-96.png"))); // NOI18N
        jPanel11.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblSLHD.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSLHD.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(lblSLHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 30));

        jPanel6.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 400, 120));

        jPanel12.setBackground(new java.awt.Color(106, 139, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("SỐ LƯỢNG MẶT HÀNG");
        jPanel12.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-parcel-96.png"))); // NOI18N
        jPanel12.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblSLMH.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSLMH.setForeground(new java.awt.Color(255, 255, 255));
        jPanel12.add(lblSLMH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 30));

        jPanel6.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 30, 400, 120));

        tblDoanhThu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sản phẩm", "Ngày bán", "Giá bán", "Số lượng", "% Giảm giá", "Tiền giảm giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoanhThu.setRowHeight(30);
        jScrollPane3.setViewportView(tblDoanhThu);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 1560, 550));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("đến ngày");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 405, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Từ ngày");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 405, -1, -1));

        jButton2.setBackground(new java.awt.Color(106, 139, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Xuất file Excel");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 1000, 200, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Doanh thu từ");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 400, -1, -1));

        txtDTtu.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDTtuCaretUpdate(evt);
            }
        });
        jPanel6.add(txtDTtu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 400, 100, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("đến ");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1450, 405, -1, -1));

        txtDTden.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDTdenCaretUpdate(evt);
            }
        });
        jPanel6.add(txtDTden, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 400, 100, 30));

        lblThongBaoLoi.setForeground(new java.awt.Color(255, 0, 51));
        jPanel6.add(lblThongBaoLoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 410, -1, -1));
        jPanel6.add(txtTuNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, 50, 30));
        jPanel6.add(txtTuThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 50, 30));
        jPanel6.add(txtTuNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 50, 30));
        jPanel6.add(txtDenNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, 50, 30));
        jPanel6.add(txtDenThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 400, 50, 30));
        jPanel6.add(txtDenNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 400, 50, 30));

        jButton1.setBackground(new java.awt.Color(106, 139, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/0buttonLoc.png"))); // NOI18N
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 400, 40, 30));

        jButton3.setBackground(new java.awt.Color(106, 139, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Bán chạy ");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel6.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, 160, 30));

        jButton4.setBackground(new java.awt.Color(106, 139, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Doanh thu cao ");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel6.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 400, 160, 30));

        tabs.addTab("Tổng quát", jPanel6);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1620, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1045, Short.MAX_VALUE)
        );

        tabs.addTab("Biểu đồ", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1620, 1080));
    }// </editor-fold>//GEN-END:initComponents

    private void txtDTdenCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDTdenCaretUpdate
        try {
            tuDTDenDT();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtDTdenCaretUpdate

    private void txtDTtuCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDTtuCaretUpdate
        try {
            tuDTDenDT();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtDTtuCaretUpdate

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        XExcel.writeToExcel(tblDoanhThu, "Sản phẩm bán ra");
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        try {
            tuNgayDenNgay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        banChay();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        doanhThu();
    }//GEN-LAST:event_jButton4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDoanhThu;
    private javax.swing.JLabel lblSLHD;
    private javax.swing.JLabel lblSLKH;
    private javax.swing.JLabel lblSLMH;
    private javax.swing.JLabel lblSLNV;
    private javax.swing.JLabel lblSLPN;
    private javax.swing.JLabel lblThongBaoLoi;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDoanhThu;
    private javax.swing.JTextField txtDTden;
    private javax.swing.JTextField txtDTtu;
    private javax.swing.JTextField txtDenNam;
    private javax.swing.JTextField txtDenNgay;
    private javax.swing.JTextField txtDenThang;
    private javax.swing.JTextField txtTuNam;
    private javax.swing.JTextField txtTuNgay;
    private javax.swing.JTextField txtTuThang;
    // End of variables declaration//GEN-END:variables
}
