/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author cutr0
 */
public class HoaDonCT2 {

    private String tenSP;
    private String giaBan;
    private String soLuong;
    private static Float thanhTien;

    public HoaDonCT2() {
    }

    public HoaDonCT2(String tenSP, String giaBan, String soLuong) {
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public static Float getThanhTien() {
        return thanhTien;
    }

    public static void setThanhTien(Float thanhTien) {
        HoaDonCT2.thanhTien = thanhTien;
    }

}
