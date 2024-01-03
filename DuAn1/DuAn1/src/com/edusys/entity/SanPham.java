package com.edusys.entity;

public class SanPham {

    private String maSP;
    private String tenSP;
    private String maLoai;
    private String tenLoai;
    private int soLuong;
    private float gia;
    private String dvTinh;
    private String maNCC;
    private String tenNCC;
    private String ghiChu;
    private String hinh;
    private float ptGiamGia;
    private float tienGiamGia;
    private float tongTien;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String maLoai, String tenLoai, int soLuong, float gia, String dvTinh, String maNCC, String tenNCC, String ghiChu, String hinh, float ptGiamGia, float tienGiamGia, float tongTien) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
        this.gia = gia;
        this.dvTinh = dvTinh;
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.ghiChu = ghiChu;
        this.hinh = hinh;
        this.ptGiamGia = ptGiamGia;
        this.tienGiamGia = tienGiamGia;
        this.tongTien = tongTien;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public String getDvTinh() {
        return dvTinh;
    }

    public void setDvTinh(String dvTinh) {
        this.dvTinh = dvTinh;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public float getPtGiamGia() {
        return ptGiamGia;
    }

    public void setPtGiamGia(float ptGiamGia) {
        this.ptGiamGia = ptGiamGia;
    }

    public float getTienGiamGia() {
        return tienGiamGia;
    }

    public void setTienGiamGia(float tienGiamGia) {
        this.tienGiamGia = tienGiamGia;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return this.tenLoai;
    }

}
