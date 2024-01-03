/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.HoaDon;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class HoaDonDAO extends EduSysDAO<HoaDon, String> {

    final String INSERT_SQL = "INSERT INTO HOADON(MAHD,MANV,MAKH,THANHTIEN) values (?,?,?,?)";
    final String UPDATE_SQL = "UPDATE HOADON SET MANV = ?, MAKH =?, THANHTIEN = ?  WHERE MAHD = ?";
    final String DELETE_SQL = "DELETE FROM HOADON WHERE MAHD = ?";
    final String SELECT_ALL_SQL = "SELECT A.MAHD ,A.MANV, C.TENNV, A.MAKH, D.TENKH, A.NGAYBAN, A.THANHTIEN "
            + "FROM HOADON A "
            + "INNER JOIN NHANVIEN C ON A.MANV = C.MANV "
            + "INNER JOIN KHACHHANG D ON A.MAKH = D.MAKH "
            + "ORDER BY A.MAHD DESC";
    final String SELECT_BY_ID_SQL = "SELECT A.MAHD ,A.MANV, C.TENNV, A.MAKH, D.TENKH, NGAYBAN,A.THANHTIEN\n"
            + "FROM HOADON A \n"
            + "INNER JOIN NHANVIEN C ON A.MANV = C.MANV\n"
            + "INNER JOIN KHACHHANG D ON A.MAKH = D.MAKH\n"
            + "WHERE MAHD LIKE ? "
            + "ORDER BY A.MAHD DESC";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT A.MAHD ,A.MANV, C.TENNV, A.MAKH, D.TENKH, NGAYBAN,A.THANHTIEN\n"
            + "FROM HOADON A \n"
            + "INNER JOIN NHANVIEN C ON A.MANV = C.MANV\n"
            + "INNER JOIN KHACHHANG D ON A.MAKH = D.MAKH\n"
            + "WHERE MAHD like ?"
            + "ORDER BY A.MAHD DESC";
    final String SX_THEO_TEN = "SELECT * FROM HOADON ORDER BY MAHD ASC";
    final String SX_THEO_TEN_GIAMDAN = "SELECT * FROM HOADON ORDER BY MAHD DESC";
    final String SHOW_ALL = "SELECT A.MAHD ,A.MANV, C.TENNV, A.MAKH, D.TENKH ,A.THANHTIEN\n"
            + "FROM HOADON A \n"
            + "INNER JOIN NHANVIEN C ON A.MANV = C.MANV\n"
            + "INNER JOIN KHACHHANG D ON A.MAKH = D.MAKH "
            + "ORDER BY A.MAHD DESC";

    @Override
    public void insert(HoaDon entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaHD(),
                entity.getMaNV(),
                entity.getMaKH(),
                entity.getTongTien());
    }

    @Override
    public void update(HoaDon entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getMaNV(),
                entity.getMaKH(),
                entity.getTongTien(),
                entity.getMaHD());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getString("MAHD"));
                entity.setMaNV(rs.getString("MANV"));
                entity.setTenNV(rs.getString("TENNV"));
                entity.setMaKH(rs.getString("MAKH"));
                entity.setTenKH(rs.getString("TENKH"));
                entity.setNgayBan(rs.getDate("NGAYBAN"));
                entity.setTongTien(rs.getFloat("THANHTIEN"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<HoaDon> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL, "%" + keyWord + "%");
    }

    public List<HoaDon> sapxep() {
        return selectBySql(SX_THEO_TEN);
    }

    public List<HoaDon> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAMDAN);
    }

    public List<HoaDon> showall() {
        return selectBySql(SHOW_ALL);
    }
}
