/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.LoaiSanPham;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class LoaiSanPhamDAO extends EduSysDAO<LoaiSanPham, String> {

    final String INSERT_SQL = "INSERT INTO LOAISANPHAM(MALOAI,TENLOAI) values (?,?)";
    final String UPDATE_SQL = "UPDATE LOAISANPHAM SET TENLOAI= ? WHERE MALOAI = ?";
    final String DELETE_SQL = "DELETE FROM LOAISANPHAM WHERE MALOAI = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM LOAISANPHAM";
    final String SELECT_BY_ID_SQL = "SELECT * FROM LOAISANPHAM WHERE MALOAI = ?";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT * FROM LOAISANPHAM WHERE MALOAI like ?";

    @Override

    public void insert(LoaiSanPham entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaLoai(),
                entity.getTenLoai());
    }

    @Override
    public void update(LoaiSanPham entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenLoai(),
                entity.getMaLoai());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<LoaiSanPham> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LoaiSanPham selectById(String id) {
        List<LoaiSanPham> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiSanPham> selectBySql(String sql, Object... args) {
        List<LoaiSanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                LoaiSanPham entity = new LoaiSanPham();
                entity.setMaLoai(rs.getString("MALOAI"));
                entity.setTenLoai(rs.getString("TENLOAI"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<LoaiSanPham> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL, "%" + keyWord + "%");
    }

}
