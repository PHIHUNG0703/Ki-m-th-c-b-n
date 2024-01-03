/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.HoaDonCT;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class HoaDonCTDAO extends EduSysDAO<HoaDonCT, String> {

    final String INSERT_SQL = "INSERT INTO HOADONCT(MASP,MALOAI,GIABAN,SOLUONG,DVTINH,PTGIAMGIA,TIENGIAMGIA,TONGTIEN,MAHD) values (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE HOADONCT SET MASP = ?, MALOAI =?, GIABAN = ?, SOLUONG = ?, DVTINH = ?, PTGIAMGIA = ?, TIENGIAMGIA = ?, TONGTIEN = ?, MAHD = ? WHERE MAHDCT = ?";
    final String DELETE_SQL = "DELETE FROM HOADONCT WHERE MAHDCT = ?";
    final String SELECT_ALL_SQL = "SELECT A.MAHDCT, A.MASP, B.TENSP, A.MALOAI, C.TENLOAI, A.GIABAN, A.SOLUONG, A.DVTINH, A.PTGIAMGIA, A.TIENGIAMGIA, A.TONGTIEN, MAHD\n"
            + "FROM HOADONCT A\n"
            + "INNER JOIN SANPHAM B ON A.MASP = B.MASP\n"
            + "INNER JOIN LOAISANPHAM C ON A.MALOAI = C.MALOAI";
    final String SELECT_BY_ID_SQL = "SELECT A.MAHDCT, A.MASP, B.TENSP, A.MALOAI, C.TENLOAI, A.GIABAN, A.SOLUONG, A.DVTINH, A.PTGIAMGIA, A.TIENGIAMGIA, A.TONGTIEN, MAHD\n"
            + "FROM HOADONCT A\n"
            + "INNER JOIN SANPHAM B ON A.MASP = B.MASP\n"
            + "INNER JOIN LOAISANPHAM C ON A.MALOAI = C.MALOAI WHERE MAHDCT = ?";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT A.MAHDCT, A.MASP, B.TENSP, A.MALOAI, C.TENLOAI, A.GIABAN, A.SOLUONG, A.DVTINH, A.PTGIAMGIA, A.TIENGIAMGIA, A.TONGTIEN, MAHD\n"
            + "FROM HOADONCT A\n"
            + "INNER JOIN SANPHAM B ON A.MASP = B.MASP\n"
            + "INNER JOIN LOAISANPHAM C ON A.MALOAI = C.MALOAI\n"
            + "WHERE A.MAHDCT like ?";
    final String SX_THEO_TEN = "SELECT * FROM HOADONCT ORDER BY MAHDCT ASC";
    final String SX_THEO_TEN_GIAMDAN = "SELECT * FROM HOADONCT ORDER BY MAHDCT DESC";
    final String HD_CT = "SELECT A.MAHDCT, A.MASP, B.TENSP, A.MALOAI, C.TENLOAI, A.GIABAN, A.SOLUONG, A.DVTINH, A.PTGIAMGIA, A.TIENGIAMGIA, A.TONGTIEN, MAHD\n"
            + "FROM HOADONCT A\n"
            + "INNER JOIN SANPHAM B ON A.MASP = B.MASP\n"
            + "INNER JOIN LOAISANPHAM C ON A.MALOAI = C.MALOAI WHERE MAHD LIKE ?";
    final String XOA_HD = "DELETE FROM HOADONCT WHERE MAHD = ?";

    @Override
    public void insert(HoaDonCT entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaSP(),
                entity.getMaLoai(),
                entity.getGiaBan(),
                entity.getSoLuong(),
                entity.getDvTinh(),
                entity.getPtGiamGia(),
                entity.getTienGiamGia(),
                entity.getTongTien(),
                entity.getMaHD());
    }

    @Override
    public void update(HoaDonCT entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getMaSP(),
                entity.getMaLoai(),
                entity.getGiaBan(),
                entity.getSoLuong(),
                entity.getDvTinh(),
                entity.getPtGiamGia(),
                entity.getTienGiamGia(),
                entity.getTongTien(),
                entity.getMaHD(),
                entity.getMaHDCT());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDonCT> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDonCT selectById(String id) {
        List<HoaDonCT> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDonCT> selectBySql(String sql, Object... args) {
        List<HoaDonCT> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDonCT entity = new HoaDonCT();
                entity.setMaHDCT(rs.getString("MAHDCT"));
                entity.setMaSP(rs.getString("MASP"));
                entity.setTenSP(rs.getString("TENSP"));
                entity.setMaLoai(rs.getString("MALOAI"));
                entity.setTenLoai(rs.getString("TENLOAI"));
                entity.setGiaBan(rs.getFloat("GIABAN"));
                entity.setSoLuong(rs.getInt("SOLUONG"));
                entity.setDvTinh(rs.getString("DVTINH"));
                entity.setPtGiamGia(rs.getFloat("PTGIAMGIA"));
                entity.setTienGiamGia(rs.getFloat("TIENGIAMGIA"));
                entity.setTongTien(rs.getFloat("TONGTIEN"));
                entity.setMaHD(rs.getString("MAHD"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<HoaDonCT> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL, "%" + keyWord + "%");
    }

    public List<HoaDonCT> sapxep() {
        return selectBySql(SX_THEO_TEN);
    }

    public List<HoaDonCT> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAMDAN);
    }

    public List<HoaDonCT> chonhoadon(String keyWord) {
        return selectBySql(HD_CT, "%" + keyWord + "%");
    }

    public List<HoaDonCT> xoaHoaDonCT(String keyWord) {
        return selectBySql(XOA_HD, "%" + keyWord + "%");
    }

}
