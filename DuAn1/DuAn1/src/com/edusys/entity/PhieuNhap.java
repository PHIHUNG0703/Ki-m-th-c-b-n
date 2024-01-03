/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

import java.util.Date;

/**
 *
 * @author cutr0
 */
public class PhieuNhap {

    private String maPNCT;
    private String maPN;
    private Date ngayNhap;
    private String maNCC;
    private String tenNCC;
    private String maNV;
    private String tenNV;
    private String maSP;
    private String tenSP;
    private String maLoai;
    private String tenLoai;
    private Float donGiaNhap;
    private int soLuong;
    private String dvTinh;
    private Float tongTien;

    public PhieuNhap() {
    }

    public PhieuNhap(String maPNCT, String maPN, Date ngayNhap, String maNCC, String tenNCC, String maNV, String tenNV, String maSP, String tenSP, String maLoai, String tenLoai, Float donGiaNhap, int soLuong, String dvTinh, Float tongTien) {
        this.maPNCT = maPNCT;
        this.maPN = maPN;
        this.ngayNhap = ngayNhap;
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.donGiaNhap = donGiaNhap;
        this.soLuong = soLuong;
        this.dvTinh = dvTinh;
        this.tongTien = tongTien;
    }

    public String getMaPNCT() {
        return maPNCT;
    }

    public void setMaPNCT(String maPNCT) {
        this.maPNCT = maPNCT;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
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

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
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

    public Float getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(Float donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
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

    public Float getTongTien() {
        return tongTien;
    }

    public void setTongTien(Float tongTien) {
        this.tongTien = tongTien;
    }

    
    
    @Override
    public String toString() {
        return this.maPNCT;
    }

}
