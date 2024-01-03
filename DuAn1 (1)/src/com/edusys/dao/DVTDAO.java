/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.DVT;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class DVTDAO extends EduSysDAO<DVT, String> {

    final String INSERT_SQL = "INSERT INTO DVT(MADVT,TENDVT) values (?,?)";
    final String UPDATE_SQL = "UPDATE DVT SET TENDVT= ? WHERE MADVT = ?";
    final String DELETE_SQL = "DELETE FROM DVT WHERE MADVT = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM DVT";
    final String SELECT_BY_ID_SQL = "SELECT * FROM DVT WHERE MADVT = ?";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT * FROM DVT WHERE TENDVT LIKE ?";
    final String SX_THEO_TEN_TANG_DAN = "SELECT * FROM DVT ORDER BY TENDVT ASC";
    final String SX_THEO_TEN_GIAM_DAN = "SELECT * FROM DVT ORDER BY TENDVT DESC";

    @Override

    public void insert(DVT entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaDVT(),
                entity.getTenDVT());
    }

    @Override
    public void update(DVT entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenDVT(),
                entity.getMaDVT());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<DVT> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DVT selectById(String id) {
        List<DVT> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DVT> selectBySql(String sql, Object... args) {
        List<DVT> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                DVT entity = new DVT();
                entity.setMaDVT(rs.getString("MADVT"));
                entity.setTenDVT(rs.getString("TENDVT"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<DVT> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL, "%" + keyWord + "%");
    }

    public List<DVT> sapxeptangdan() {
        return selectBySql(SX_THEO_TEN_TANG_DAN);
    }

    public List<DVT> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAM_DAN);
    }

}
