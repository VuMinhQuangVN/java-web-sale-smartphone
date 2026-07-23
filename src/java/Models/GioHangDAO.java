/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class GioHangDAO {

    Connection conn = null;
    PreparedStatement ps = null;

    public GioHang getGioHang(int maND) {
        String sql = "SELECT `maGiohang`, `maND`, `ngayTao`, `trangThai` FROM `giohang` WHERE maND =? AND trangThai = 'Chưa đặt' LIMIT 1";
        GioHang d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maND);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new GioHang();
                    d.setMaGiohang(rs.getInt("maGiohang"));
                    d.setMaND(rs.getInt("maND"));
                    java.sql.Date date = rs.getDate("ngayTao");
                    d.setNgayTao(new Date(date.getTime()));
                    d.setTrangThai(rs.getString("trangThai"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public int insertGiohang(GioHang d) {
        int maGiohang = -1;
        String sql = "INSERT INTO `giohang`(`maND`, `ngayTao`, `trangThai`) "
                + "VALUES (?,?,?)";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getMaND());
            java.sql.Date dataSql = new java.sql.Date(d.getNgayTao().getTime());
            ps.setDate(2, dataSql);
            ps.setString(3, d.getTrangThai());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    maGiohang = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maGiohang;
    }

    public GioHang getOrCreateCart(int maND) {
        GioHang gh = getGioHang(maND);
        if (gh == null) {
            gh = new GioHang();
            gh.setMaND(maND);
            gh.setNgayTao(new java.util.Date());
            gh.setTrangThai("Chưa đặt");
            int maGioHang = insertGiohang(gh);
            gh.setMaGiohang(maGioHang);
        }
        return gh;
    }
    
    public void updateTrangthai(Connection conn, int maGiohang, String trangThai) throws SQLException {
        String sql = "UPDATE `giohang` SET `trangThai`= ? WHERE `maGiohang`=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);//Đã đặt
            ps.setInt(2, maGiohang);

            ps.executeUpdate();
        }
    }
    
}
