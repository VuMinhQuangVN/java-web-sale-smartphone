/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER
 */
public class DienThoai {
    private int maDT;
    private String tenDT;
    private int maLoai;
    private String anh;
    private String mota;
    private String hangSx;
    private String manHinh;
    private String pin;
    private String Chip;
    private String camera;

    public DienThoai() {
    }

    public DienThoai(int maDT, String tenDT, int maLoai, String anh, String mota, String hangSx, String manHinh, String pin, String Chip, String camera) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.maLoai = maLoai;
        this.anh = anh;
        this.mota = mota;
        this.hangSx = hangSx;
        this.manHinh = manHinh;
        this.pin = pin;
        this.Chip = Chip;
        this.camera = camera;
    }

    public int getMaDT() {
        return maDT;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHangSx() {
        return hangSx;
    }

    public void setHangSx(String hangSx) {
        this.hangSx = hangSx;
    }

    public String getManHinh() {
        return manHinh;
    }

    public void setManHinh(String manHinh) {
        this.manHinh = manHinh;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getChip() {
        return Chip;
    }

    public void setChip(String Chip) {
        this.Chip = Chip;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    
}
