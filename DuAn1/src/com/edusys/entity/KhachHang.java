/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author cutr0
 */
public class KhachHang {

    private String MaKH;
    private String TenKH;
    private String Sdt;
    private String diaChi;
    private String ghiChu;

    public KhachHang() {
    }

    public KhachHang(String MaKH, String TenKH, String Sdt, String diaChi, String ghiChu) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.Sdt = Sdt;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String Sdt) {
        this.Sdt = Sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    

    @Override
    public String toString() {
        return this.TenKH;
    }

}
