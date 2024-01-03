/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.SanPham;
import com.edusys.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cutr0
 */
public class SanPhamDAO extends EduSysDAO<SanPham, String> {

    final String INSERT_SQL = "INSERT INTO SANPHAM(MASP,TENSP,MALOAI,SOLUONG,GIABAN,DVTINH,MANCC,GHICHU,HINH) values (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NHANVIEN SET TENSP = ?, MALOAI =?, SOLUONG = ?, GIABAN = ?, DVTINH = ?, MANCC = ?, GHICHU = ?, HINH= ?  WHERE MASP = ?";
    final String DELETE_SQL = "DELETE FROM SANPHAM WHERE MASP = ?";
    final String SELECT_ALL_SQL = "SELECT MASP, TENSP, A.MALOAI ,B.TENLOAI, SOLUONG, GIABAN, DVTINH, A.MANCC ,C.TENNCC, GHICHU, HINH \n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC";
    final String SELECT_BY_ID_SQL = "SELECT MASP, TENSP, A.MALOAI, B.TENLOAI, SOLUONG, GIABAN, DVTINH, A.MANCC, C.TENNCC, GHICHU, HINH \n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "WHERE MASP = ?";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT MASP ,TENSP, A.MALOAI, B.TENLOAI, SOLUONG, GIABAN, DVTINH, A.MANCC, C.TENNCC, GHICHU ,HINH \n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "WHERE TENSP LIKE ?";
    final String SX_THEO_TEN = "SELECT MASP ,TENSP, A.MALOAI, B.TENLOAI, SOLUONG, GIABAN, DVTINH, A.MANCC, C.TENNCC, GHICHU ,HINH \n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "ORDER BY TENSP ASC";
    final String SX_THEO_TEN_GIAMDAN = "SELECT MASP ,TENSP, A.MALOAI, B.TENLOAI, SOLUONG, GIABAN, DVTINH, A.MANCC, C.TENNCC, GHICHU ,HINH \n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "ORDER BY TENSP DESC";
    final String LOC_SP_THEO_LOAI = "SELECT * FROM LOAISANPHAM";
    final String LOC_THEO_TENNNC = "SELECT MASP ,TENSP, A.MALOAI, B.TENLOAI, SOLUONG, GIABAN, DVTINH, A.MANCC, C.TENNCC, GHICHU ,HINH \n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "WHERE C.TENNCC LIKE ?";
    final String LOC_THEO_SL = "SELECT MASP ,TENSP, A.MALOAI, B.TENLOAI, SOLUONG, GIABAN, DVTINH, A.MANCC, C.TENNCC, GHICHU ,HINH \n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "WHERE GIABAN ? ?";

    ;

    ;

    @Override
    public void insert(SanPham entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaSP(),
                entity.getTenSP(),
                entity.getMaLoai(),
                entity.getTenLoai(),
                entity.getSoLuong(),
                entity.getGia(),
                entity.getDvTinh(),
                entity.getMaNCC(),
                entity.getTenNCC(),
                entity.getGhiChu(),
                entity.getHinh());
    }

    @Override
    public void update(SanPham entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenSP(),
                entity.getMaLoai(),
                entity.getTenLoai(),
                entity.getSoLuong(),
                entity.getGia(),
                entity.getDvTinh(),
                entity.getMaNCC(),
                entity.getTenNCC(),
                entity.getGhiChu(),
                entity.getHinh(),
                entity.getMaSP());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<SanPham> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSP(rs.getString("MASP"));
                entity.setTenSP(rs.getString("TENSP"));
                entity.setMaLoai(rs.getString("MALOAI"));
                entity.setTenLoai(rs.getString("TENLOAI"));
                entity.setSoLuong(rs.getInt("SOLUONG"));
                entity.setGia(rs.getFloat("GIABAN"));
                entity.setDvTinh(rs.getString("DVTINH"));
                entity.setMaNCC(rs.getString("MANCC"));
                entity.setTenNCC(rs.getString("TENNCC"));
                entity.setGhiChu(rs.getString("GHICHU"));
                entity.setHinh(rs.getString("HINH"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<SanPham> selectByKeyword(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD_SQL, "%" + keyWord + "%");
    }

    public List<SanPham> selectByKeywordncc(String keyWord) {
        return selectBySql(LOC_THEO_TENNNC, "%" + keyWord + "%");
    }

    public List<SanPham> loctheogia(String keyWord1, String keyWord2) {
        return selectBySql(LOC_THEO_TENNNC, "" + keyWord1 + "" + keyWord2 + "");
    }

    public List<SanPham> sapxep() {
        return selectBySql(SX_THEO_TEN);
    }

    public List<SanPham> sapxepgiamdan() {
        return selectBySql(SX_THEO_TEN_GIAMDAN);
    }

    public List<SanPham> timkiemtheotenloai() {
        return selectBySql(SX_THEO_TEN_GIAMDAN);
    }

    public List<SanPham> hientenloai() {
        return selectBySql(LOC_SP_THEO_LOAI);
    }
}
