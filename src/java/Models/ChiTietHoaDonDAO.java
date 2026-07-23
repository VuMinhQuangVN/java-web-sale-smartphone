/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;
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
public class ChiTietHoaDonDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    
    
    //Thêm 1 chi tiết hóa đơn
    public int insert(Connection conn, ChiTietHoaDon d) throws SQLException {
        int maCTHD = -1;
        String sql = "INSERT INTO `chitiethoadon`(`maDH`, `maBienthe`, `soLuong`, `gia`, `thanhTien`) "
                + "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getMaDH());
            ps.setInt(2, d.getMaBienthe());
            ps.setInt(3, d.getSoLuong());
            ps.setDouble(4, d.getGia());
            ps.setDouble(5, d.getThanhTien());
            
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    maCTHD = rs.getInt(1); 
                }
            }
            return maCTHD;
        } 
    }
    
    
    // Lấy danh sách sản phẩm trong hóa đơn 
    public List<ChiTietHoaDon> getById(int maDH) {
        String sql = "SELECT * FROM chitiethoadon WHERE maDH = ?";
        List<ChiTietHoaDon> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                    ChiTietHoaDon d = new ChiTietHoaDon();
                    d.setMaDH(rs.getInt("maDH"));
                    d.setMaBienthe(rs.getInt("maBienthe"));
                    d.setSoLuong(rs.getInt("soLuong"));
                    d.setGia(rs.getDouble("gia"));
                    d.setThanhTien(rs.getDouble("thanhTien"));
                   list.add(d);
                    
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Map<String, Object>> get(int maDH){
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT ct.gia, ct.soLuong AS soLuongCT, ct.thanhTien,"
                + " d.maDT, d.tenDT, d.anh,"
                + " b.maBienthe, b.mauSac, b.RAM, b.ROM, b.soLuong AS soLuongBT"
                + " FROM chitiethoadon ct"
                + " JOIN bienthedienthoai b ON ct.maBienthe = b.maBienthe"
                + " JOIN dienthoai d ON b.maDT = d.maDT"
                + " WHERE ct.maDH = ?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDH);
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
                    bt.setSoLuong(rs.getInt("soLuongBT"));

                    ChiTietHoaDon cthd = new ChiTietHoaDon();
                    cthd.setGia(rs.getDouble("gia"));
                    cthd.setSoLuong(rs.getInt("soLuongCT"));
                    cthd.setThanhTien(rs.getDouble("thanhTien"));
                    
                    map.put("chitietdonhang", cthd);
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
    
    public List<Map<String, Object>> getUpdateSL(Connection conn, int maDH) throws SQLException{
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT ct.gia, ct.soLuong AS soLuongCT, ct.thanhTien,"
                + " b.maBienthe, b.mauSac, b.RAM, b.ROM, b.soLuong AS soLuongBT"
                + " FROM chitiethoadon ct"
                + " JOIN bienthedienthoai b ON ct.maBienthe = b.maBienthe"
                + " WHERE ct.maDH = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDH);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();

                    BienTheDienThoai bt = new BienTheDienThoai();
                    bt.setMaBienthe(rs.getInt("maBienthe"));
                    bt.setMauSac(rs.getString("mauSac"));
                    bt.setRAM(rs.getString("RAM"));
                    bt.setROM(rs.getString("ROM"));
                    bt.setSoLuong(rs.getInt("soLuongBT"));

                    ChiTietHoaDon cthd = new ChiTietHoaDon();
                    cthd.setGia(rs.getDouble("gia"));
                    cthd.setSoLuong(rs.getInt("soLuongCT"));
                    cthd.setThanhTien(rs.getDouble("thanhTien"));
                    
                    map.put("chitietdonhang", cthd);
                    map.put("bienthe", bt);

                    list.add(map);
                }
            }
            return list;
        }
    }
}
