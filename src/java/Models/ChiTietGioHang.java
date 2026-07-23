/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER
 */
public class ChiTietGioHang {
    private int maCTGH;
    private int maGiohang;
    private int maBienthe;
    private int soLuong;
    private double gia;

    public ChiTietGioHang() {
    }

    public ChiTietGioHang(int maCTGH, int maGiohang, int maBienthe, int soLuong, double gia) {
        this.maCTGH = maCTGH;
        this.maGiohang = maGiohang;
        this.maBienthe = maBienthe;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public int getMaCTGH() {
        return maCTGH;
    }

    public void setMaCTGH(int maCTGH) {
        this.maCTGH = maCTGH;
    }

    public int getMaGiohang() {
        return maGiohang;
    }

    public void setMaGiohang(int maGiohang) {
        this.maGiohang = maGiohang;
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
    
    
}
