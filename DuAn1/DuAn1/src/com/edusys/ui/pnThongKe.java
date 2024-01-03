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
        addItemToComboboxes();
    }

    public void hienThiSL() {
        updateLabelFromQuery("SELECT COUNT(*) FROM NHANVIEN", lblSLNV);
        updateLabelFromQuery("SELECT COUNT(*) FROM KHACHHANG", lblSLKH);
        updateLabelFromQuery("SELECT COUNT(*) FROM SANPHAM", lblSLMH);
        updateLabelFromQuery("SELECT COUNT(*) FROM HOADON", lblSLHD);
        updateLabelFromQuery("SELECT COUNT(*) FROM PHIEUNHAP", lblSLPN);
        updateLabelFromQueryWithFormat("SELECT SUM(TONGTIEN) FROM HOADONCT", lblDoanhThu);
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

    void addItemToComboboxes() {
        addItemToCombobox(cboNgay, 1, 31);
        addItemToCombobox(cboNgay1, 1, 31);

        addItemToCombobox(cboThang, 1, 12);
        addItemToCombobox(cboThang1, 1, 12);

        addItemToCombobox(cboNam, 2000, 9999);
        addItemToCombobox(cboNam1, 2000, 9999);
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
            String sql = "SELECT C.TENSP,B.NGAYBAN,A.GIABAN,A.SOLUONG,A.PTGIAMGIA,A.TIENGIAMGIA,A.TONGTIEN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP ORDER BY B.NGAYBAN DESC";
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TENSP"),
                    XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                    new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                    rs.getInt("SOLUONG"),
                    new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                    new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAMGIA")) + " VNĐ",
                    new DecimalFormat("###,###.##").format(rs.getDouble("TONGTIEN")) + " VNĐ"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tuNgayDenNgay() {
        int nam = Integer.parseInt((String) cboNam.getSelectedItem());
        int thang = Integer.parseInt((String) cboThang.getSelectedItem());
        int ngay = Integer.parseInt((String) cboNgay.getSelectedItem());
        int nam1 = Integer.parseInt((String) cboNam1.getSelectedItem());
        int thang1 = Integer.parseInt((String) cboThang1.getSelectedItem());
        int ngay1 = Integer.parseInt((String) cboNgay1.getSelectedItem());
        try {
            DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
            model.setRowCount(0);
            String sql = "SELECT C.TENSP,B.NGAYBAN,A.GIABAN,A.SOLUONG,A.PTGIAMGIA,A.TIENGIAMGIA,A.TONGTIEN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP WHERE B.NGAYBAN >= '" + nam + "-" + thang + "-" + ngay + "' AND B.NGAYBAN <= '" + nam1 + "-" + thang1 + "-" + ngay1 + "'";
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TENSP"),
                    XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                    new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                    rs.getInt("SOLUONG"),
                    new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                    new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAMGIA")) + " VNĐ",
                    new DecimalFormat("###,###.##").format(rs.getDouble("TONGTIEN")) + " VNĐ"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tuDTDenDT() {
        if (txtTu1.getText().isEmpty() && txtDen1.getText().isEmpty()) {
            fillDanhSPBR();
        } else {
            try {
                DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
                model.setRowCount(0);
                String sql = "SELECT C.TENSP,B.NGAYBAN,A.GIABAN,A.SOLUONG,A.PTGIAMGIA,A.TIENGIAMGIA,A.TONGTIEN FROM HOADONCT A INNER JOIN HOADON B ON A.MAHD = B.MAHD INNER JOIN SANPHAM C ON A.MASP = C.MASP WHERE A.TONGTIEN >= '" + txtTu1.getText() + "' AND A.TONGTIEN <= '" + txtDen1.getText() + "'";
                ResultSet rs = JdbcHelper.query(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("TENSP"),
                        XDate.toString(rs.getDate("NGAYBAN"), "dd/MM/yyyy"),
                        new DecimalFormat("###,###.##").format(rs.getFloat("GIABAN")) + " VNĐ",
                        rs.getInt("SOLUONG"),
                        new DecimalFormat("###,###.##").format(rs.getDouble("PTGIAMGIA")) + " %",
                        new DecimalFormat("###,###.##").format(rs.getDouble("TIENGIAMGIA")) + " VNĐ",
                        new DecimalFormat("###,###.##").format(rs.getDouble("TONGTIEN")) + " VNĐ"});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        txtTu1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDen1 = new javax.swing.JTextField();
        cboThang = new javax.swing.JComboBox<>();
        cboNam = new javax.swing.JComboBox<>();
        cboNgay = new javax.swing.JComboBox<>();
        cboNam1 = new javax.swing.JComboBox<>();
        cboThang1 = new javax.swing.JComboBox<>();
        cboNgay1 = new javax.swing.JComboBox<>();
        lblThongBaoLoi = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
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
        jLabel21.setText("DOANH THU");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-parcel-96.png"))); // NOI18N
        jPanel8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        jPanel8.add(lblDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 280, 30));

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 210, 400, 120));

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

        tblDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
        jScrollPane3.setViewportView(tblDoanhThu);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 1560, 530));

        jLabel1.setText("đến ngày");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 405, -1, -1));

        jLabel2.setText("Từ ngày");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 405, -1, -1));

        jButton2.setText("Xuất file Excel");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 990, 200, 40));

        jLabel9.setText("Doanh thu từ");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 405, -1, -1));

        txtTu1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTu1CaretUpdate(evt);
            }
        });
        jPanel6.add(txtTu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 400, 100, 30));

        jLabel10.setText("đến ");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1460, 405, -1, -1));

        txtDen1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDen1CaretUpdate(evt);
            }
        });
        jPanel6.add(txtDen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 400, 100, 30));

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn tháng" }));
        jPanel6.add(cboThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 100, 30));

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn năm" }));
        jPanel6.add(cboNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 100, 30));

        cboNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn ngày" }));
        jPanel6.add(cboNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 100, 30));

        cboNam1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn năm" }));
        jPanel6.add(cboNam1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, 100, 30));

        cboThang1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn tháng" }));
        jPanel6.add(cboThang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 400, 100, 30));

        cboNgay1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn ngày" }));
        jPanel6.add(cboNgay1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 400, 100, 30));

        lblThongBaoLoi.setForeground(new java.awt.Color(255, 0, 51));
        jPanel6.add(lblThongBaoLoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 410, -1, -1));

        jButton1.setBackground(new java.awt.Color(106, 139, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Lọc");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, -1, 30));

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

    private void txtDen1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDen1CaretUpdate
        try {
            tuDTDenDT();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtDen1CaretUpdate

    private void txtTu1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTu1CaretUpdate
        try {
            tuDTDenDT();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtTu1CaretUpdate

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        XExcel.writeToExcel(tblDoanhThu, "Sản phẩm bán ra");
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        tuNgayDenNgay();
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNam1;
    private javax.swing.JComboBox<String> cboNgay;
    private javax.swing.JComboBox<String> cboNgay1;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JComboBox<String> cboThang1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JTextField txtDen1;
    private javax.swing.JTextField txtTu1;
    // End of variables declaration//GEN-END:variables
}
