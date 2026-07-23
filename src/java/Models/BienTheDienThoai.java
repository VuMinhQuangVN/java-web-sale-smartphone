/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER
 */
public class BienTheDienThoai {
    private int maBienthe;
    private int maDT;
    private String mauSac;
    private String RAM;
    private String ROM;
    private double gia;
    private int soLuong;

    public BienTheDienThoai() {
    }

    public BienTheDienThoai(int maBienthe, int maDT, String mauSac, String RAM, String ROM, double gia, int soLuong) {
        this.maBienthe = maBienthe;
        this.maDT = maDT;
        this.mauSac = mauSac;
        this.RAM = RAM;
        this.ROM = ROM;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public int getMaBienthe() {
        return maBienthe;
    }

    public void setMaBienthe(int maBienthe) {
        this.maBienthe = maBienthe;
    }

    public int getMaDT() {
        return maDT;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getROM() {
        return ROM;
    }

    public void setROM(String ROM) {
        this.ROM = ROM;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    
    

}
