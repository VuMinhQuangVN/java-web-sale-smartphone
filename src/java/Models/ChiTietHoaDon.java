/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER
 */
public class ChiTietHoaDon {
    private int maDH;
    private int maBienthe;
    private int soLuong;
    private double gia;
    private double thanhTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int maDH, int maBienthe, int soLuong, double gia, double thanhTien) {
        this.maDH = maDH;
        this.maBienthe = maBienthe;
        this.soLuong = soLuong;
        this.gia = gia;
        this.thanhTien = thanhTien;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public int getMaBienthe() {
        return maBienthe;
    }

    public void setMaBienthe(int maBienthe) {
        this.maBienthe = maBienthe;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    
}
