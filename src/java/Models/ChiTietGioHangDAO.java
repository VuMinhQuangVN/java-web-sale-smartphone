/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import com.mysql.cj.jdbc.ConnectionImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public class ChiTietGioHangDAO {

    Connection conn = null;
    PreparedStatement ps = null;

    public List<Map<String, Object>> getChiTietGioHang(int maGiohang) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT c.maCTGH, c.soLuong AS soLuongCT, c.gia,"
                + " d.maDT, d.tenDT, d.anh,"
                + " b.maBienthe, b.mauSac, b.RAM, b.ROM, b.soLuong AS soLuongBT"
                + " FROM chitietgiohang c"
                + " JOIN bienthedienthoai b ON c.maBienthe = b.maBienthe"
                + " JOIN dienthoai d ON b.maDT = d.maDT"
                + " WHERE c.maGiohang = ?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maGiohang);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();

                    DienThoai dt = new DienThoai();
                    dt.setMaDT(rs.getInt("maDT"));
                    dt.setTenDT(rs.getString("tenDT"));
                    dt.setAnh(rs.getString("anh"));

                    BienTheDienThoai bt = new BienTheDienThoai();
                    bt.setMaBienthe(rs.getInt("maBienthe"));
                    bt.setMauSac(rs.getString("mauSac"));
                    bt.setRAM(rs.getString("RAM"));
                    bt.setROM(rs.getString("ROM"));
                    bt.setGia(rs.getDouble("gia"));
                    bt.setSoLuong(rs.getInt("soLuongBT"));

                    map.put("maCTGH", rs.getInt("maCTGH"));
                    map.put("soLuong", rs.getInt("soLuongCT"));
                    map.put("dienthoai", dt);
                    map.put("bienthe", bt);

                    list.add(map);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public Map<String, Object> getByMaCTGH(int maCTGH) {
        Map<String, Object> map = new HashMap<>();
        String sql = "SELECT c.maCTGH, c.soLuong AS soLuongCT, c.gia,"
                + " d.maDT, d.tenDT, d.anh,"
                + " b.maBienthe, b.mauSac, b.RAM, b.ROM, b.soLuong AS soLuongBT"
                + " FROM chitietgiohang c"
                + " JOIN bienthedienthoai b ON c.maBienthe = b.maBienthe"
                + " JOIN dienthoai d ON b.maDT = d.maDT"
                + " WHERE c.maCTGH = ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maCTGH);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    DienThoai dt = new DienThoai();
                    dt.setMaDT(rs.getInt("maDT"));
                    dt.setTenDT(rs.getString("tenDT"));
                    dt.setAnh(rs.getString("anh"));

                    BienTheDienThoai bt = new BienTheDienThoai();
                    bt.setMaBienthe(rs.getInt("maBienthe"));
                    bt.setMauSac(rs.getString("mauSac"));
                    bt.setRAM(rs.getString("RAM"));
                    bt.setROM(rs.getString("ROM"));
                    bt.setGia(rs.getDouble("gia"));
                    bt.setSoLuong(rs.getInt("soLuongBT"));

                    map.put("maCTGH", rs.getInt("maCTGH"));
                    map.put("soLuong", rs.getInt("soLuongCT"));
                    map.put("dienthoai", dt);
                    map.put("bienthe", bt);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public ChiTietGioHang getGioHang(int maGiohang, int maBienthe) {
        String sql = "SELECT `maCTGH`, `soLuong`, `gia` FROM `chitietgiohang` WHERE maGiohang =? AND maBienthe =? LIMIT 1";
        ChiTietGioHang d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maGiohang);
            ps.setInt(2, maBienthe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new ChiTietGioHang();
                    d.setMaCTGH(rs.getInt("maCTGH"));
                    d.setSoLuong(rs.getInt("soLuong"));
                    d.setGia(rs.getDouble("gia"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public void updateSoLuong(int maCTGH, int index) {
        String sql = "UPDATE `chitietgiohang` SET `soLuong`= soLuong + ? WHERE `maCTGH`=?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, index);
            ps.setInt(2, maCTGH);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSoLuongg(int maCTGH, int soLuong) {
        String sql = "UPDATE `chitietgiohang` SET `soLuong`= ? WHERE `maCTGH`=?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setInt(2, maCTGH);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertChiTietGiohang(ChiTietGioHang d) {
        int maCTGH = -1;
        String sql = "INSERT INTO `chitietgiohang`(`maGiohang`, `maBienthe`, `soLuong`, `gia`) "
                + "VALUES (?,?,?,?)";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getMaGiohang());
            ps.setInt(2, d.getMaBienthe());
            ps.setInt(3, d.getSoLuong());
            ps.setDouble(4, d.getGia());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    maCTGH = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maCTGH;
    }

    // Xóa chi tiết giỏ hàng theo mã
    public void delete(Connection conn, int maCTGH) throws SQLException {
        String sql = "DELETE FROM chitietgiohang WHERE maCTGH = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCTGH);
            ps.executeUpdate();

        }
    }

    public void delete(int maCTGH) throws SQLException {
        String sql = "DELETE FROM chitietgiohang WHERE maCTGH = ?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCTGH);
            ps.executeUpdate();

        }
    }

    public int getSoLuongBinhThuongTrongGioHang(int maND, int maGioHang) {
        String sql = "SELECT COUNT(*) AS soLuong FROM `chitietgiohang` cg "
                + "JOIN `giohang` g ON cg.maGiohang = g.maGiohang "
                + "WHERE g.maND = ? AND g.maGiohang = ? AND g.trangThai = 'Chưa đặt'"; 
        int soLuong = 0;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maND);  
            ps.setInt(2, maGioHang);  

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    soLuong = rs.getInt("soLuong"); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return soLuong;  
    }

}
