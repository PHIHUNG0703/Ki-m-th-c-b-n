/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NhaCungCap;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class NhaCungCapDAO extends EduSysDAO<NhaCungCap, String> {

    final String INSERT_SQL = "INSERT INTO NHACUNGCAP(MANCC,TENNCC,DIACHI,SDT,EMAIL) values (?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NHACUNGCAP SET TENNCC = ?, DIACHI =?, SDT = ?, EMAIL = ?  WHERE MANCC = ?";
    final String DELETE_SQL = "DELETE FROM NHACUNGCAP WHERE MANCC = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM NHACUNGCAP ORDER BY MANCC DESC";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NHACUNGCAP WHERE MANCC = ? ORDER BY MANCC DESC";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT * FROM NHACUNGCAP WHERE TENNCC LIKE ? ORDER BY MANCC DESC";
    final String SX_THEO_TEN_TANG_DAN = "SELECT * FROM NHACUNGCAP ORDER BY TENNCC ASC";
    final String SX_THEO_TEN_GIAM_DAN = "SELECT * FROM NHACUNGCAP ORDER BY TENNCC DESC";

    @Override

    public void insert(NhaCungCap entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaNCC(),
                entity.getTenNCC(),
                entity.getDiaChi(),
                entity.getSDT(),
                entity.getEmail());
    }

    @Override
    public void update(NhaCungCap entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenNCC(),
                entity.getDiaChi(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getMaNCC());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhaCungCap> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhaCungCap selectById(String id) {
        List<NhaCungCap> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhaCungCap> selectBySql(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhaCungCap entity = new NhaCungCap();
                entity.setMaNCC(rs.getString("MANCC"));
                entity.setTenNCC(rs.getString("TENNCC"));
                entity.setDiaChi(rs.getString("DIACHI"));
                entity.setSDT(rs.getString("SDT"));
                entity.setEmail(rs.getString("EMAIL"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<NhaCungCap> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL, "%" + keyWord + "%");
    }

    public List<NhaCungCap> sapxep() {
        return selectBySql(SX_THEO_TEN_TANG_DAN);
    }

    public List<NhaCungCap> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAM_DAN);
    }
}
