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
    private String dvTinh;
    private float ptGiamGia;
    private float tienGiamGia;
    private float tongTien;
    private String maHD;

    public HoaDonCT() {
    }

    public HoaDonCT(String MaHDCT, String maSP, String tenSP, String maLoai, String tenLoai, float giaBan, int soLuong, String dvTinh, float ptGiamGia, float tienGiamGia, float tongTien, String maHD) {
        this.MaHDCT = MaHDCT;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.dvTinh = dvTinh;
        this.ptGiamGia = ptGiamGia;
        this.tienGiamGia = tienGiamGia;
        this.tongTien = tongTien;
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

    public String getDvTinh() {
        return dvTinh;
    }

    public void setDvTinh(String dvTinh) {
        this.dvTinh = dvTinh;
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
        this.tongTien = giaBan * soLuong;
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
