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
public class DonHang {
    private int maDH;
    private int maND;
    private Date ngayDH;
    private String trangThai;
    private double tongTien;
    private Date ngayGiao;
    private Date ngayHoantat;
    private String phuongThucTT;
    private String trangThaiTT;
    private String diaChigiao;
    private String ghiChu;
    //thêm
    private String tenND;

    public DonHang() {
    }

    public DonHang(int maDH, int maND, Date ngayDH, String trangThai, double tongTien, Date ngayGiao, Date ngayHoantat, String phuongThucTT, String trangThaiTT, String diaChigiao, String ghiChu, String tenND) {
        this.maDH = maDH;
        this.maND = maND;
        this.ngayDH = ngayDH;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.ngayGiao = ngayGiao;
        this.ngayHoantat = ngayHoantat;
        this.phuongThucTT = phuongThucTT;
        this.trangThaiTT = trangThaiTT;
        this.diaChigiao = diaChigiao;
        this.ghiChu = ghiChu;
        this.tenND = tenND;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public int getMaND() {
        return maND;
    }

    public void setMaND(int maND) {
        this.maND = maND;
    }

    public Date getNgayDH() {
        return ngayDH;
    }

    public void setNgayDH(Date ngayDH) {
        this.ngayDH = ngayDH;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(Date ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public Date getNgayHoantat() {
        return ngayHoantat;
    }

    public void setNgayHoantat(Date ngayHoantat) {
        this.ngayHoantat = ngayHoantat;
    }

    public String getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    public String getTrangThaiTT() {
        return trangThaiTT;
    }

    public void setTrangThaiTT(String trangThaiTT) {
        this.trangThaiTT = trangThaiTT;
    }

    public String getDiaChigiao() {
        return diaChigiao;
    }

    public void setDiaChigiao(String diaChigiao) {
        this.diaChigiao = diaChigiao;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTenND() {
        return tenND;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }

}
