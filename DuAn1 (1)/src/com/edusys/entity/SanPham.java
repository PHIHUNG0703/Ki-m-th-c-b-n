package com.edusys.entity;

public class SanPham {

    private String maSP;
    private String tenSP;
    private String maLoai;
    private String tenLoai;
    private int soLuong;
    private float giaBan;
    private float giaNhap;
    private String maDVT;
    private String tenDVT;
    private String maNCC;
    private String tenNCC;
    private String ghiChu;
    private String hinh;
    private float ptGiamGia;
    private float tienGiam;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String maLoai, String tenLoai, int soLuong, float giaBan, float giaNhap, String maDVT, String tenDVT, String maNCC, String tenNCC, String ghiChu, String hinh, float ptGiamGia, float tienGiam) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaNhap = giaNhap;
        this.maDVT = maDVT;
        this.tenDVT = tenDVT;
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.ghiChu = ghiChu;
        this.hinh = hinh;
        this.ptGiamGia = ptGiamGia;
        this.tienGiam = tienGiam;
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

    public float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getMaDVT() {
        return maDVT;
    }

    public void setMaDVT(String maDVT) {
        this.maDVT = maDVT;
    }

    public String getTenDVT() {
        return tenDVT;
    }

    public void setTenDVT(String tenDVT) {
        this.tenDVT = tenDVT;
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

    public float getTienGiam() {
        return tienGiam;
    }

    public void setTienGiam(float tienGiam) {
        this.tienGiam = tienGiam;
    }

    @Override
    public String toString() {
        return this.tenLoai;
    }

}
