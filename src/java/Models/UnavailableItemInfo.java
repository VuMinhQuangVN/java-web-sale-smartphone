/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER
 */
public class UnavailableItemInfo {

    private int maBienThe;
    private String tenSanPham;
    private String thongTinBienThe;
    private String lyDo;

    public UnavailableItemInfo(int maBienThe, String tenSanPham, String thongTinBienThe, String lyDo) {
        this.maBienThe = maBienThe;
        this.tenSanPham = tenSanPham;
        this.thongTinBienThe = thongTinBienThe;
        this.lyDo = lyDo;
    }

    public int getMaBienThe() {
        return maBienThe;
    }

    public void setMaBienThe(int maBienThe) {
        this.maBienThe = maBienThe;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getThongTinBienThe() {
        return thongTinBienThe;
    }

    public void setThongTinBienThe(String thongTinBienThe) {
        this.thongTinBienThe = thongTinBienThe;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }
    
    
}
