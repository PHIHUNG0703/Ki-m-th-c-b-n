/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class ThongKeDAO {

    public static ResultSet rs = null; // Trả về kết quả truy vấn

    public static String PieChart_sql = "SELECT YEAR(NGAYDK) NAM , COUNT(*) SOLUONG FROM NGUOIHOC GROUP BY YEAR(NGAYDK)";
    public static String BarChart_sql = "SELECT YEAR(NGAYKG) NAM , SUM( HOCPHI) HocPhiTB FROM KHOAHOC GROUP BY YEAR(NGAYKG)";
    public static String Cot_sql = "select top 5 A.MASP SUM(C.TONGTIEN) as \"TONGTIEN\"\n"
            + "from SANPHAM A \n"
            + "INNER JOIN HOADONCT B ON A.MASP = B.MASP\n"
            + "INNER JOIN HOADON C ON B.MAHD = C.MAHD\n"
            + "group by A.MASP\n"
            + "order by 'TONGTIEN' desc";

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
//    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
//        try {
//            List<Object[]> list = new ArrayList<>();
//            ResultSet rs = JdbcHelper.query(sql, args);
//            while (rs.next()) {
//                Object[] vals = new Object[cols.length];
//                for (int i = 0; i < cols.length; i++) {
//                    vals[i] = rs.getObject(cols[i]);
//                }
//                list.add(vals);
//            }
//            rs.getStatement().getConnection().close();
//            return list;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public List<Object[]> getBangDiem(Integer makh) {
//        String sql = "{CALL sp_BangDiem(?)}";
//        String[] cols = {"MaNH", "HoTen", "Diem"};
//        return getListOfArray(sql, cols, makh);
//    }
    public List<Object[]> getsoLuongBanRa() {
        String sql = "{CALL sp_soLuongBanRa}";
        String[] cols = {"tenSP", "ngayBan", "giaBan", "soLuong", "ptGiamGia", "tienGiamGia", "tongTien"};
        return getListOfArray(sql, cols);
    }

    public List<Object[]> getPie_Chart() {
        String[] cols = {"Nam", "SoLuong"};
        return this.getListOfArray(PieChart_sql, cols);
    }

    public List<Object[]> getBar_chart() {
        String[] cols = {"Nam", "HocPhiTB"};
        return this.getListOfArray(BarChart_sql, cols);
    }
    
    public List<Object[]> getBieuDo_chart() {
        String[] cols = {"MASP","TONGTIEN"};
        return  this.getListOfArray(Cot_sql, cols);
    }

//    public List<Object[]> getDiemChuyenDe() {
//        String sql = "{CALL sp_DiemChuyenDe}";
//        String[] cols = {"ChuyenDe", "SoHV", "ThapNhat", "CaoNhat", "TrungBinh"};
//        return getListOfArray(sql, cols);
//    }
//
//    public List<Object[]> getDoanhThu(Integer nam) {
//        String sql = "{CALL sp_DoanhThu(?)}";
//        String[] cols = {"ChuyenDe","SoKH", "SoHV", "DoanhThu", "ThapNhat", "CaoNhat", "TrungBinh"};
//        return getListOfArray(sql, cols,nam);
//    }
}
