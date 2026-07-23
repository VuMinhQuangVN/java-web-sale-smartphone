/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
public class BienTheDienThoaiDAO {

    Connection conn = null;
    PreparedStatement ps = null;

    //Trả về danh sách thông tin biến thể điện thoại
    public List<BienTheDienThoai> getAll(int offset, int limit) {
        String sql = "SELECT * FROM bienthedienthoai LIMIT ? OFFSET ?";
        List<BienTheDienThoai> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    BienTheDienThoai d = new BienTheDienThoai();
                    d.setMaBienthe(rs.getInt("maBienthe"));
                    d.setMaDT(rs.getInt("maDT"));
                    d.setMauSac(rs.getString("mauSac"));
                    d.setRAM(rs.getString("RAM"));
                    d.setROM(rs.getString("ROM"));
                    d.setGia(rs.getDouble("gia"));
                    d.setSoLuong(rs.getInt("soLuong"));
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM bienthedienthoai";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Thêm 1 biến thể
    public void insert(BienTheDienThoai d) throws SQLException {
        String sql = "INSERT INTO `bienthedienthoai`(`maDT`, `mauSac`, `RAM`, `ROM`, `gia`, `soLuong`) "
                + "VALUES (?,?,?,?,?,?)";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getMaDT());
            ps.setString(2, d.getMauSac());
            ps.setString(3, d.getRAM());
            ps.setString(4, d.getROM());
            ps.setDouble(5, d.getGia());
            ps.setInt(6, d.getSoLuong());

            ps.executeUpdate();

        }
    }

    // Lấy thông tin biến thể điện thoại theo mã
    public BienTheDienThoai getById(int maBienthe) {
        String sql = "SELECT * FROM bienthedienthoai WHERE maBienthe = ?";
        BienTheDienThoai d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maBienthe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new BienTheDienThoai();
                    d.setMaBienthe(rs.getInt("maBienthe"));
                    d.setMaDT(rs.getInt("maDT"));
                    d.setMauSac(rs.getString("mauSac"));
                    d.setRAM(rs.getString("RAM"));
                    d.setROM(rs.getString("ROM"));
                    d.setGia(rs.getDouble("gia"));
                    d.setSoLuong(rs.getInt("soLuong"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }

    public List<BienTheDienThoai> getByDienThoai(int maDT) {
        List<BienTheDienThoai> list = new ArrayList<>();
        String sql = "SELECT * FROM bienthedienthoai WHERE maDT = ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDT);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BienTheDienThoai bt = new BienTheDienThoai(
                            rs.getInt("maBienThe"),
                            rs.getInt("maDT"),
                            rs.getString("mauSac"),
                            rs.getString("RAM"),
                            rs.getString("ROM"),
                            rs.getDouble("gia"),
                            rs.getInt("soLuong")
                    );
                    list.add(bt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật biến thể điện thoại
    public void update(Connection conn, BienTheDienThoai d) throws SQLException {
        String sql = "UPDATE `bienthedienthoai` SET `maDT`=?,`mauSac`=?,`RAM`=?,`ROM`=?,"
                + "`gia`=?,`soLuong`=? WHERE `maBienthe`=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getMaDT());
            ps.setString(2, d.getMauSac());
            ps.setString(3, d.getRAM());
            ps.setString(4, d.getROM());
            ps.setDouble(5, d.getGia());
            ps.setInt(6, d.getSoLuong());

            ps.setInt(7, d.getMaBienthe());

            ps.executeUpdate();

        }
    }
    
    // Cập nhật biến thể điện thoại
    public void updateSL(Connection conn, int maBienthe, int soLuong) throws SQLException {
        String sql = "UPDATE `bienthedienthoai` SET `soLuong`=? WHERE `maBienthe`=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, soLuong);
            ps.setInt(2, maBienthe);

            ps.executeUpdate();

        }
    }

    // Xóa biến thể điện thoại theo mã
    public void delete(int maBienthe) throws SQLException {
        String sql = "DELETE FROM bienthedienthoai WHERE maBienthe = ?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maBienthe);
            ps.executeUpdate();

        }
    }

    public Map<Integer, BienTheDienThoai> getGiaThapNhatCuaMoiDienThoai() {
        Map<Integer, BienTheDienThoai> map = new HashMap<>();
        String sql = """
        SELECT b1.* FROM bienthedienthoai b1
        INNER JOIN (
            SELECT maDT, MIN(gia) AS minGia FROM bienthedienthoai GROUP BY maDT
        ) b2 ON b1.maDT = b2.maDT AND b1.gia = b2.minGia
    """;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BienTheDienThoai bt = new BienTheDienThoai(
                        rs.getInt("maBienThe"),
                        rs.getInt("maDT"),
                        rs.getString("mauSac"),
                        rs.getString("RAM"),
                        rs.getString("ROM"),
                        rs.getDouble("gia"),
                        rs.getInt("soLuong")
                );
                map.put(bt.getMaDT(), bt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, Object> getDienThoaiByMaBT(int maBienthe, int soLuong) {
        Map<String, Object> map = new HashMap<>();
        String sql = "SELECT d.maDT, d.tenDT, d.anh,"
                + " b.maBienthe, b.mauSac, b.RAM, b.ROM, b.soLuong AS soLuongBT, b.gia"
                + " FROM bienthedienthoai b"
                + " JOIN dienthoai d ON b.maDT = d.maDT"
                + " WHERE b.maBienthe = ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBienthe);
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

                    map.put("maCTGH", null);
                    map.put("soLuong", soLuong);
                    map.put("dienthoai", dt);
                    map.put("bienthe", bt);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // Lấy thông tin biến thể điện thoại + điện thoại theo mã
    public ViewModelDtBtdt getVMById(int maBienthe) throws SQLException {
        String sql = "SELECT b.soLuong, d.tenDT, b.mauSac, b.RAM, b.ROM "
                + "FROM bienthedienthoai b "
                + "JOIN dienthoai d ON b.maDT = d.maDT "
                + "WHERE b.maBienthe = ?";
        ViewModelDtBtdt d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maBienthe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new ViewModelDtBtdt();
                    d.setSoLuong(rs.getInt("soLuong"));
                    d.setTenDT(rs.getString("tenDT"));
                    d.setMauSac(rs.getString("mauSac"));
                    d.setRAM(rs.getString("RAM"));
                    d.setROM(rs.getString("ROM"));
                }
            }

            return d;
        }

    }
    
    public int LockDataByMaBienThe(Connection conn, int maBienthe) throws SQLException{
        String sql = "SELECT soLuong FROM bienthedienthoai WHERE maBienthe = ? FOR UPDATE";
        int sl = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maBienthe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sl =  rs.getInt("soLuong");
                }
            }
            return sl;
        }
    }
}
