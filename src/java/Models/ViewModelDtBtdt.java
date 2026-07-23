/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER
 */
public class ViewModelDtBtdt {

    private int SoLuong;
    private String tenDT;
    private String MauSac;
    private String RAM;
    private String ROM;

    public ViewModelDtBtdt() {
    }

    
    public ViewModelDtBtdt(int SoLuong, String tenDT, String MauSac, String RAM, String ROM) {
        this.SoLuong = SoLuong;
        this.tenDT = tenDT;
        this.MauSac = MauSac;
        this.RAM = RAM;
        this.ROM = ROM;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public String getMauSac() {
        return MauSac;
    }

    public void setMauSac(String MauSac) {
        this.MauSac = MauSac;
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
    
    
}
