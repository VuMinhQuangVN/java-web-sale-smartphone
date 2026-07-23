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
public class DanhGia {
    private int maDanhGia;
    private int maND;
    private int maDT;
    private int soSao;
    private String noiDung;
    private Date ngayDanhGia;
    private String trangThai;
    private int maDH;
    private String tenND;

    public DanhGia(int maDanhGia, int maND, int maDT, int soSao, String noiDung, Date ngayDanhGia, String trangThai, int maDH, String tenND) {
        this.maDanhGia = maDanhGia;
        this.maND = maND;
        this.maDT = maDT;
        this.soSao = soSao;
        this.noiDung = noiDung;
        this.ngayDanhGia = ngayDanhGia;
        this.trangThai = trangThai;
        this.maDH = maDH;
        this.tenND = tenND;
    }

    public DanhGia() {
    }
    

    public int getMaDanhGia() {
        return maDanhGia;
    }

    public void setMaDanhGia(int maDanhGia) {
        this.maDanhGia = maDanhGia;
    }

    public int getMaND() {
        return maND;
    }

    public void setMaND(int maND) {
        this.maND = maND;
    }

    public int getMaDT() {
        return maDT;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public int getSoSao() {
        return soSao;
    }

    public void setSoSao(int soSao) {
        this.soSao = soSao;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Date getNgayDanhGia() {
        return ngayDanhGia;
    }

    public void setNgayDanhGia(Date ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public String getTenND() {
        return tenND;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }
    
    
}
