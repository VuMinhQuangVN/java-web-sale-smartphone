/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class DoanhThu {
    private int maDTu;
    private Date ngay;
    private double soTien;
    private String ghiChu;
    private Integer maDH;
    private int maND;
    private String loaiGiaodich;

    public DoanhThu() {
    }

    public DoanhThu(int maDTu, Date ngay, double soTien, String ghiChu, Integer maDH, int maND, String loaiGiaodich) {
        this.maDTu = maDTu;
        this.ngay = ngay;
        this.soTien = soTien;
        this.ghiChu = ghiChu;
        this.maDH = maDH;
        this.maND = maND;
        this.loaiGiaodich = loaiGiaodich;
    }

    public int getMaDTu() {
        return maDTu;
    }

    public void setMaDTu(int maDTu) {
        this.maDTu = maDTu;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getMaDH() {
        return maDH;
    }

    public void setMaDH(Integer maDH) {
        this.maDH = maDH;
    }

    public int getMaND() {
        return maND;
    }

    public void setMaND(int maND) {
        this.maND = maND;
    }

    public String getLoaiGiaodich() {
        return loaiGiaodich;
    }

    public void setLoaiGiaodich(String loaiGiaodich) {
        this.loaiGiaodich = loaiGiaodich;
    }   
    
}
