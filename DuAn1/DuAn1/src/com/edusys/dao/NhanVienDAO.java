/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NhanVien;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String> {
    
    final String INSERT_SQL = "INSERT INTO NHANVIEN(MANV,TENNV,SDT,EMAIL,MATKHAU,DIACHI,NGAYSINH,GIOITINH,VAITRO,HINH) values (?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NHANVIEN SET TENNV = ?, SDT =?, EMAIL = ?, MATKHAU = ?, DIACHI = ?, NGAYSINH = ?, GIOITINH = ?, VAITRO = ?, HINH= ?  WHERE MANV = ?";
    final String DELETE_SQL = "DELETE FROM NHANVIEN WHERE MAPNCT = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM NHANVIEN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE MANV = ?";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT * FROM NHANVIEN WHERE TENNV like ?";
    final String SX_THEO_TEN = "SELECT * FROM NHANVIEN ORDER BY TENNV ASC";
    final String SX_THEO_TEN_GIAMDAN = "SELECT * FROM NHANVIEN ORDER BY TENNV DESC";
    
    @Override
    
    public void insert(NhanVien entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaNV(),
                entity.getHoTen(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getMatKhau(),
                entity.getDiaChi(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.isVaiTro(),
                entity.getHinh());
    }
    
    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getHoTen(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getMatKhau(),
                entity.getDiaChi(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.isVaiTro(),
                entity.getHinh(),
                entity.getMaNV());
    }
    
    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }
    
    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    
    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MANV"));
                entity.setHoTen(rs.getString("TENNV"));
                entity.setSdt(rs.getString("SDT"));
                entity.setEmail(rs.getString("EMAIL"));
                entity.setMatKhau(rs.getString("MATKHAU"));
                entity.setDiaChi(rs.getString("DIACHI"));
                entity.setNgaySinh(rs.getDate("NGAYSINH"));
                entity.setGioiTinh(rs.getBoolean("GIOITINH"));
                entity.setVaiTro(rs.getBoolean("VAITRO"));
                entity.setHinh(rs.getString("HINH"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<NhanVien> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL, "%" + keyWord + "%");
    }
    
    public List<NhanVien> sapxep() {
        return selectBySql(SX_THEO_TEN);
    }
    
    public List<NhanVien> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAMDAN);
    }
    
}
