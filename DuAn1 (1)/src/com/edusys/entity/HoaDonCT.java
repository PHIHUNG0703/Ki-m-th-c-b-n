/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author cutr0
 */
public class HoaDonCT {

    private String MaHDCT;
    private String maSP;
    private String tenSP;
    private String maLoai;
    private String tenLoai;
    private float giaBan;
    private int soLuong;
    private String maDvt;
    private String tenDvt;
    private float ptGiamGia;
    private float thanhTien;
    private String maHD;

    public HoaDonCT() {
    }

    public HoaDonCT(String MaHDCT, String maSP, String tenSP, String maLoai, String tenLoai, float giaBan, int soLuong, String maDvt, String tenDvt, float ptGiamGia, float thanhTien, String maHD) {
        this.MaHDCT = MaHDCT;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.maDvt = maDvt;
        this.tenDvt = tenDvt;
        this.ptGiamGia = ptGiamGia;
        this.thanhTien = thanhTien;
        this.maHD = maHD;
    }

    public String getMaHDCT() {
        return MaHDCT;
    }

    public void setMaHDCT(String MaHDCT) {
        this.MaHDCT = MaHDCT;
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

    public float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaDvt() {
        return maDvt;
    }

    public void setMaDvt(String maDvt) {
        this.maDvt = maDvt;
    }

    public String getTenDvt() {
        return tenDvt;
    }

    public void setTenDvt(String tenDvt) {
        this.tenDvt = tenDvt;
    }

    public float getPtGiamGia() {
        return ptGiamGia;
    }

    public void setPtGiamGia(float ptGiamGia) {
        this.ptGiamGia = ptGiamGia;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    @Override
    public String toString() {
        return this.MaHDCT;
    }

}
