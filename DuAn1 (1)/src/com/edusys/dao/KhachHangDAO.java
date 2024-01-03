/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.KhachHang;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class KhachHangDAO extends EduSysDAO<KhachHang, String> {

    final String INSERT_SQL = "INSERT INTO KHACHHANG(MAKH,TENKH,SDT,GHICHU) values (?,?,?,?)";
    final String UPDATE_SQL = "UPDATE KHACHHANG SET TENKH = ?, SDT =?, GHICHU = ? WHERE MAKH = ?";
    final String DELETE_SQL = "DELETE FROM KHACHHANG WHERE MAKH = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM KHACHHANG ORDER BY MAKH DESC";
    final String SELECT_BY_ID_SQL = "SELECT * FROM KHACHHANG WHERE MAKH = ? ORDER BY MAKH DESC";
    final String SELECT_BY_KEY_WORD_SQL_KH = "SELECT * FROM KHACHHANG WHERE TENKH LIKE ? ORDER BY MAKH DESC";
    final String SX_THEO_TEN_TANG_DAN = "SELECT *, PARSENAME(REPLACE(TENKH, ' ', '.'), 1) AS TenCuoiCung FROM KHACHHANG ORDER BY TenCuoiCung ASC";
    final String SX_THEO_TEN_GIAM_DAN = "SELECT *, PARSENAME(REPLACE(TENKH, ' ', '.'), 1) AS TenCuoiCung FROM KHACHHANG ORDER BY TenCuoiCung DESC";

    @Override
    public void insert(KhachHang entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaKH(),
                entity.getTenKH(),
                entity.getSdt(),
                entity.getGhiChu());
    }

    @Override
    public void update(KhachHang entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenKH(),
                entity.getSdt(),
                entity.getGhiChu(),
                entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MAKH"));
                entity.setTenKH(rs.getString("TENKH"));
                entity.setSdt(rs.getString("SDT"));
                entity.setGhiChu(rs.getString("GHICHU"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<KhachHang> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL_KH, "%" + keyWord + "%");
    }

    public List<KhachHang> sapxep() {
        return selectBySql(SX_THEO_TEN_TANG_DAN);
    }

    public List<KhachHang> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAM_DAN);
    }

}
