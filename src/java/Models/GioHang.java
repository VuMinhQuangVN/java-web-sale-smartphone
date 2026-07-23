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
public class GioHang {
    private int maGiohang;
    private int maND;
    private Date ngayTao;
    private String trangThai;

    public GioHang() {
    }

    public GioHang(int maGiohang, int maND, Date ngayTao, String trangThai) {
        this.maGiohang = maGiohang;
        this.maND = maND;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    public int getMaGiohang() {
        return maGiohang;
    }

    public void setMaGiohang(int maGiohang) {
        this.maGiohang = maGiohang;
    }

    public int getMaND() {
        return maND;
    }

    public void setMaND(int maND) {
        this.maND = maND;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
}
