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
public class ThongKeDAO {

    public double getTongHoanTien() {
        String sql = "SELECT SUM(soTien) FROM doanhthu WHERE soTien < 0";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countDonHoanTat() {
        String sql = "SELECT COUNT(*) FROM donhang WHERE trangThai='Hoàn tất'";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countDonDangGiao() {
        String sql = "SELECT COUNT(*) FROM donhang WHERE trangThai='Đang giao'";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countKhachHang() {
        String sql = "SELECT COUNT(*) FROM nguoidung WHERE quyen='user'";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getSanPhamBanChayNhat() {
        String sql = "SELECT d.tenDT FROM chitiethoadon ct "
                + "JOIN bienthedienthoai b ON ct.maBienthe=b.maBienthe "
                + "JOIN dienthoai d ON b.maDT=d.maDT "
                + "GROUP BY d.maDT ORDER BY SUM(ct.soLuong) DESC LIMIT 1";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("tenDT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Chưa có dữ liệu";
    }

    public List<Map<String, Object>> getTop5SanPhamBanChay() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT d.tenDT, d.anh, SUM(ct.soLuong) AS tongSoLuong "
                + "FROM chitiethoadon ct "
                + "JOIN bienthedienthoai b ON ct.maBienthe = b.maBienthe "
                + "JOIN dienthoai d ON b.maDT = d.maDT "
                + "GROUP BY d.maDT "
                + "ORDER BY SUM(ct.soLuong) DESC LIMIT 5";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("tenDT", rs.getString("tenDT"));
                item.put("anh", rs.getString("anh"));
                item.put("tongSoLuong", rs.getInt("tongSoLuong"));
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Map<String, Object>> getTopKhachHang() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
            SELECT n.tenND AS tenKH, COUNT(d.maDH) AS soDon, SUM(d.tongTien) AS tongChi
            FROM donhang d
            JOIN nguoidung n ON d.maND = n.maND
            WHERE d.trangThai LIKE 'Hoàn tất%'
            GROUP BY n.tenND
            ORDER BY tongChi DESC
            LIMIT 5
        """;
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("tenKH", rs.getString("tenKH"));
                map.put("soDon", rs.getInt("soDon"));
                map.put("tongChi", rs.getDouble("tongChi"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Số đơn hàng theo tháng (trong năm hiện tại)
    public List<Map<String, Object>> getDonTheoThang() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
            SELECT MONTH(ngayDH) AS thang, COUNT(*) AS tongDon
            FROM donhang
            WHERE YEAR(ngayDH) = YEAR(CURDATE())
            GROUP BY MONTH(ngayDH)
            ORDER BY thang
        """;
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("thang", rs.getInt("thang"));
                map.put("tongDon", rs.getInt("tongDon"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Object> getTongQuanThang(int thang, int nam) {
        Map<String, Object> data = new HashMap<>();
        double tongDoanhThu = 0;
        double tongHoanTien = 0;
        int soDonThanhCong = 0;

        String sql = """
        SELECT 
            SUM(CASE WHEN loaiGiaodich != 'REFUND' THEN soTien ELSE 0 END) as DoanhThu,
            SUM(CASE WHEN loaiGiaodich = 'REFUND' THEN soTien ELSE 0 END) as HoanTien
        FROM doanhthu 
        WHERE MONTH(ngay) = ? AND YEAR(ngay) = ?
    """;

        String sqlDonHang = "SELECT COUNT(*) FROM donhang WHERE trangThai = 'Hoàn tất' AND MONTH(ngayHoantat) = ? AND YEAR(ngayHoantat) = ?";

        try (Connection conn = new dbConnect().getConnection()) {
            // 1. Tính tiền
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tongDoanhThu = rs.getDouble("DoanhThu");
                tongHoanTien = rs.getDouble("HoanTien"); // Sẽ là số âm
            }

            // 2. Đếm đơn
            ps = conn.prepareStatement(sqlDonHang);
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            rs = ps.executeQuery();
            if (rs.next()) {
                soDonThanhCong = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        data.put("tongDoanhThu", tongDoanhThu);
        data.put("tongHoanTien", Math.abs(tongHoanTien)); // Lấy giá trị tuyệt đối để hiển thị
        data.put("soDonThanhCong", soDonThanhCong);
        return data;
    }

// Top Sản phẩm theo tháng
    public List<Map<String, Object>> getTopSanPhamTheoThang(int thang, int nam) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
        SELECT dt.tenDT, SUM(ct.soLuong) as slBan
        FROM chitiethoadon ct
        JOIN bienthedienthoai bt ON ct.maBienthe = bt.maBienthe
        JOIN dienthoai dt ON bt.maDT = dt.maDT
        JOIN donhang dh ON ct.maDH = dh.maDH
        WHERE MONTH(dh.ngayDH) = ? AND YEAR(dh.ngayDH) = ? AND dh.trangThai != 'Hủy'
        GROUP BY dt.tenDT
        ORDER BY slBan DESC
        LIMIT 5
    """;
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                m.put("tenDT", rs.getString("tenDT"));
                m.put("slBan", rs.getInt("slBan"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// Top Khách hàng theo tháng
    public List<Map<String, Object>> getTopKhachHangTheoThang(int thang, int nam) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
        SELECT nd.tenND, COUNT(dh.maDH) as soDon, SUM(dh.tongTien) as tongChi
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE MONTH(dh.ngayDH) = ? AND YEAR(dh.ngayDH) = ? AND dh.trangThai != 'Hủy'
        GROUP BY nd.tenND
        ORDER BY tongChi DESC
        LIMIT 5
    """;
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                m.put("tenKH", rs.getString("tenND"));
                m.put("soDon", rs.getInt("soDon"));
                m.put("tongChi", rs.getDouble("tongChi"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
