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

    final String INSERT_SQL = "INSERT INTO SANPHAM(MASP,TENSP,MALOAI,SOLUONG,GIABAN,GIANHAP,MADVT,MANCC,HINH,PTGIAMGIA,TIENGIAM,GHICHU) values (?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE SANPHAM SET TENSP = ?, MALOAI = ?, SOLUONG = ?, GIABAN = ?, GIANHAP = ?, MADVT = ?, MANCC = ?, HINH= ?, PTGIAMGIA = ?, TIENGIAM = ?, GHICHU = ?  WHERE MASP = ?";
    final String DELETE_SQL = "DELETE FROM SANPHAM WHERE MASP = ?";
    final String SELECT_ALL_SQL = "SELECT A.*, B.TENLOAI, C.TENNCC, D.TENDVT\n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "INNER JOIN DVT D ON A.MADVT = D.MADVT\n"
            + "ORDER BY MASP DESC";
    final String SELECT_BY_ID_SQL = "SELECT A.*, B.TENLOAI, C.TENNCC, D.TENDVT\n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "INNER JOIN DVT D ON A.MADVT = D.MADVT\n"
            + "WHERE MASP = ?\n"
            + "ORDER BY MASP DESC";
    final String SELECT_BY_KEY_WORD_SQL = "SELECT A.*, B.TENLOAI, C.TENNCC, D.TENDVT\n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "INNER JOIN DVT D ON A.MADVT = D.MADVT\n"
            + "WHERE TENSP LIKE ?\n"
            + "ORDER BY MASP DESC";
    final String SX_THEO_TEN_TANG_DAN = "SELECT A.*, B.TENLOAI, C.TENNCC, D.TENDVT\n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "INNER JOIN DVT D ON A.MADVT = D.MADVT\n"
            + "ORDER BY TENSP ASC";
    final String SX_THEO_TEN_GIAM_DAN = "SELECT A.*, B.TENLOAI, C.TENNCC, D.TENDVT\n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "INNER JOIN DVT D ON A.MADVT = D.MADVT\n"
            + "ORDER BY TENSP DESC";
    final String LOC_THEO_TEN_NNC = "SELECT A.*, B.TENLOAI, C.TENNCC, D.TENDVT\n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "INNER JOIN DVT D ON A.MADVT = D.MADVT\n"
            + "WHERE C.TENNCC LIKE ?";
    final String LOC_THEO_LOAI_SP = "SELECT A.*, B.TENLOAI, C.TENNCC, D.TENDVT\n"
            + "FROM SANPHAM A\n"
            + "INNER JOIN LOAISANPHAM B ON A.MALOAI = B.MALOAI\n"
            + "INNER JOIN NHACUNGCAP C ON A.MANCC = C.MANCC\n"
            + "INNER JOIN DVT D ON A.MADVT = D.MADVT\n"
            + "WHERE B.TENLOAI LIKE ?";

    @Override
    public void insert(SanPham entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaSP(),
                entity.getTenSP(),
                entity.getMaLoai(),
                entity.getSoLuong(),
                entity.getGiaBan(),
                entity.getGiaNhap(),
                entity.getMaDVT(),
                entity.getMaNCC(),
                entity.getHinh(),
                entity.getPtGiamGia(),
                entity.getTienGiam(),
                entity.getGhiChu());
    }

    @Override
    public void update(SanPham entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenSP(),
                entity.getMaLoai(),
                entity.getSoLuong(),
                entity.getGiaBan(),
                entity.getGiaNhap(),
                entity.getMaDVT(),
                entity.getMaNCC(),
                entity.getHinh(),
                entity.getPtGiamGia(),
                entity.getTienGiam(),
                entity.getGhiChu(),
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
                entity.setGiaBan(rs.getFloat("GIABAN"));
                entity.setGiaNhap(rs.getFloat("GIANHAP"));
                entity.setMaDVT(rs.getString("MADVT"));
                entity.setTenDVT(rs.getString("TENDVT"));
                entity.setMaNCC(rs.getString("MANCC"));
                entity.setTenNCC(rs.getString("TENNCC"));
                entity.setHinh(rs.getString("HINH"));
                entity.setPtGiamGia(rs.getFloat("PTGIAMGIA"));
                entity.setTienGiam(rs.getFloat("TIENGIAM"));
                entity.setGhiChu(rs.getString("GHICHU"));
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

    public List<SanPham> locTheoLoai(String keyWord) {
        return selectBySql(LOC_THEO_LOAI_SP, "%" + keyWord + "%");
    }

    public List<SanPham> locTheoNhaCC(String keyWord) {
        return selectBySql(LOC_THEO_TEN_NNC, "%" + keyWord + "%");
    }

    public List<SanPham> sapXepTangDan() {
        return selectBySql(SX_THEO_TEN_TANG_DAN);
    }

    public List<SanPham> sapXepGiamDan() {
        return selectBySql(SX_THEO_TEN_GIAM_DAN);
    }

}
