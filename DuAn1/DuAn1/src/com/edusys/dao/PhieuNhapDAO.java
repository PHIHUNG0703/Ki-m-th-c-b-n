/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NhanVien;
import com.edusys.entity.PhieuNhap;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class PhieuNhapDAO extends EduSysDAO<PhieuNhap, String> {

    final String INSERT_SQL = "INSERT INTO NHANVIEN(MAPNCT,MAPN,SDT,EMAIL,MATKHAU,DIACHI,GIOITINH,VAITRO,HINH) values (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NHANVIEN SET TENNV = ?, SDT =?, EMAIL = ?, MATKHAU = ?, DIACHI = ?, GIOITINH = ?, VAITRO = ?, HINH= ?  WHERE MANV = ?";
    final String DELETE_SQL = "DELETE FROM PHIEUNHAPCT WHERE MAPNCT = ?";
    final String SELECT_ALL_SQL = "SELECT A.MAPNCT, B.MAPN, B.NGAYNHAP, B.MANCC, C.TENNCC, B.MANV, D.TENNV, A.MASP, E.TENSP, A.MALOAI, F.TENLOAI, DONGIANHAP,A.SOLUONG,A.DVTINH,A.TONGTIEN\n"
            + "FROM PHIEUNHAPCT A\n"
            + "INNER JOIN PHIEUNHAP B ON A.MAPN = B.MAPN\n"
            + "INNER JOIN NHACUNGCAP C ON B.MANCC = C.MANCC\n"
            + "INNER JOIN NHANVIEN D ON B.MANV = D.MANV\n"
            + "INNER JOIN SANPHAM E ON A.MASP = E.MASP\n";
    final String SELECT_BY_ID_SQL = "SELECT A.MAPNCT, B.MAPN, B.NGAYNHAP, B.MANCC, C.TENNCC, B.MANV, D.TENNV, A.MASP, E.TENSP, A.MALOAI, F.TENLOAI, DONGIANHAP,A.SOLUONG,A.DVTINH,A.TONGTIEN\n"
            + "FROM PHIEUNHAPCT A\n"
            + "INNER JOIN PHIEUNHAP B ON A.MAPN = B.MAPN\n"
            + "INNER JOIN NHACUNGCAP C ON B.MANCC = C.MANCC\n"
            + "INNER JOIN NHANVIEN D ON B.MANV = D.MANV\n"
            + "INNER JOIN SANPHAM E ON A.MASP = E.MASP\n"
            + "INNER JOIN LOAISANPHAM F ON E.MALOAI = F.MALOAI WHERE MAPNCT = ?";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT A.MAPNCT, B.MAPN, B.NGAYNHAP, B.MANCC, C.TENNCC, B.MANV, D.TENNV, A.MASP, E.TENSP, A.MALOAI, F.TENLOAI, DONGIANHAP,A.SOLUONG,A.DVTINH,A.TONGTIEN\n"
            + "FROM PHIEUNHAPCT A\n"
            + "INNER JOIN PHIEUNHAP B ON A.MAPN = B.MAPN\n"
            + "INNER JOIN NHACUNGCAP C ON B.MANCC = C.MANCC\n"
            + "INNER JOIN NHANVIEN D ON B.MANV = D.MANV\n"
            + "INNER JOIN SANPHAM E ON A.MASP = E.MASP\n"
            + "INNER JOIN LOAISANPHAM F ON E.MALOAI = F.MALOAI";
    final String SX_THEO_TEN = "SELECT * FROM PHIEUNHAPCT ORDER BY TENNV ASC";
    final String SX_THEO_TEN_GIAMDAN = "SELECT * FROM PHIEUNHAPCT ORDER BY TENNV DESC";

    @Override

    public void insert(PhieuNhap entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaPN(),
                entity.getMaPNCT(),
                entity.getNgayNhap(),
                entity.getTenNCC(),
                entity.getTenNV(),
                entity.getTenSP(),
                entity.getTenLoai(),
                entity.getDonGiaNhap(),
                entity.getSoLuong(),
                entity.getDvTinh(),
                entity.getTongTien());
    }

    @Override
    public void update(PhieuNhap entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getMaPNCT(),
                entity.getNgayNhap(),
                entity.getTenNCC(),
                entity.getTenNV(),
                entity.getTenSP(),
                entity.getTenLoai(),
                entity.getDonGiaNhap(),
                entity.getSoLuong(),
                entity.getDvTinh(),
                entity.getTongTien(),
                entity.getMaPN());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<PhieuNhap> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhieuNhap selectById(String id) {
        List<PhieuNhap> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuNhap> selectBySql(String sql, Object... args) {
        List<PhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                PhieuNhap entity = new PhieuNhap();
                entity.setMaPNCT(rs.getString("MAPNCT"));
                entity.setNgayNhap(rs.getDate("NGAYNHAP"));
                entity.setTenNCC(rs.getString("TENNCC"));
                entity.setTenNV(rs.getString("TENNV"));
                entity.setTenSP(rs.getString("TENSP"));
                entity.setTenLoai(rs.getString("TENLOAI"));
                entity.setDonGiaNhap(rs.getFloat("DONGIANHAP"));
                entity.setSoLuong(rs.getInt("SOLUONG"));
                entity.setDvTinh(rs.getString("DVTINH"));
                entity.setTongTien(rs.getFloat("TONGTIEN"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<PhieuNhap> selectByKeyword() {
        return selectBySql(SELECT_BY_KEY_WORD_SQL);
    }

    public List<PhieuNhap> sapxep() {
        return selectBySql(SX_THEO_TEN);
    }

    public List<PhieuNhap> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAMDAN);
    }

}
