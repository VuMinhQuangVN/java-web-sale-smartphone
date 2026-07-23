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
public class DoanhThuDAO {

    Connection conn = null;
    PreparedStatement ps = null;

    public List<DoanhThu> getAll() {
        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT * FROM doanhthu ORDER BY ngay DESC";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DoanhThu d = new DoanhThu();
                d.setMaDTu(rs.getInt("maDTu"));
                d.setNgay(rs.getTimestamp("ngay"));
                d.setSoTien(rs.getDouble("soTien"));
                d.setGhiChu(rs.getString("ghiChu"));
                int maDH_val = rs.getInt("maDH");
                if (rs.wasNull()) {
                    d.setMaDH(null);
                } else {
                    d.setMaDH(maDH_val);
                }
                d.setMaND(rs.getInt("maND"));
                d.setLoaiGiaodich(rs.getString("loaiGiaodich"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DoanhThu> getByND(int maND, int offset, int limit) {
        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT * FROM doanhthu WHERE maND = ? ORDER BY ngay DESC LIMIT ? OFFSET ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maND);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DoanhThu d = new DoanhThu();
                    d.setMaDTu(rs.getInt("maDTu"));
                    d.setNgay(rs.getTimestamp("ngay"));
                    d.setSoTien(rs.getDouble("soTien"));
                    d.setGhiChu(rs.getString("ghiChu"));
                    int maDH_val = rs.getInt("maDH");
                    if (rs.wasNull()) {
                        d.setMaDH(null);
                    } else {
                        d.setMaDH(maDH_val);
                    }
                    d.setMaND(rs.getInt("maND"));
                    d.setLoaiGiaodich(rs.getString("loaiGiaodich"));
                    list.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countDTByND(int maND) {
        String sql = "SELECT COUNT(*) FROM doanhthu WHERE maND = ?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maND);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insert(DoanhThu d) {
        int id = -1;
        String sql = "INSERT INTO doanhthu (ngay, soTien, ghiChu, maDH, maND, loaiGiaodich) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if (d.getNgay() != null) {
                ps.setTimestamp(1, new java.sql.Timestamp(d.getNgay().getTime()));
            } else {
                ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            }

            ps.setDouble(2, d.getSoTien());
            ps.setString(3, d.getGhiChu());

            // Xử lý trường hợp maDH có thể null
            if (d.getMaDH() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, d.getMaDH());
            }

            ps.setInt(5, d.getMaND());
            ps.setString(6, d.getLoaiGiaodich());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public int insert(Connection conn, DoanhThu d) throws SQLException {
        int id = -1;
        String sql = "INSERT INTO doanhthu (ngay, soTien, ghiChu, maDH, maND, loaiGiaodich) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if (d.getNgay() != null) {
                ps.setTimestamp(1, new java.sql.Timestamp(d.getNgay().getTime()));
            } else {
                ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            }

            ps.setDouble(2, d.getSoTien());
            ps.setString(3, d.getGhiChu());

            // Xử lý trường hợp maDH có thể null
            if (d.getMaDH() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, d.getMaDH());
            }

            ps.setInt(5, d.getMaND());
            ps.setString(6, d.getLoaiGiaodich());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
            return id;
        }

    }

    // ✅ Tính tổng doanh thu (tự động trừ các giao dịch loại 'Trừ')
    public double getTongDoanhThu() {
        String sql = "SELECT SUM(soTien) AS tong FROM doanhthu";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("tong");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Map<String, Object>> getDoanhThu7Ngay() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
        SELECT DATE(ngay) AS ngay, SUM(soTien) AS tong
        FROM doanhthu
        WHERE loaiGiaodich LIKE 'SALE%'
        GROUP BY DATE(ngay)
        ORDER BY ngay DESC
        LIMIT 7
    """;
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("ngay", rs.getDate("ngay"));
                map.put("tong", rs.getDouble("tong"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Map<String, Object>> getDoanhThuTheoThang() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
        SELECT MONTH(ngay) AS thang, SUM(soTien) AS tong
        FROM doanhthu
        WHERE YEAR(ngay) = YEAR(CURDATE()) 
          AND loaiGiaodich LIKE 'SALE%'
        GROUP BY MONTH(ngay)
        ORDER BY thang
    """;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("thang", rs.getInt("thang"));
                map.put("tong", rs.getDouble("tong"));
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DoanhThu> getDoanhThuByFilter(String year, String month, String quarter) {
        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT * FROM doanhthu WHERE 1=1"; // Kỹ thuật 1=1 để dễ nối chuỗi

        if (year != null && !year.isEmpty()) {
            sql += " AND YEAR(ngay) = " + year;
        }

        if (month != null && !month.isEmpty() && !month.equals("0")) {
            sql += " AND MONTH(ngay) = " + month;
        } else if (quarter != null && !quarter.isEmpty() && !quarter.equals("0")) {
            // Xử lý quý
            int q = Integer.parseInt(quarter);
            if (q == 1) {
                sql += " AND MONTH(ngay) BETWEEN 1 AND 3";
            }
            if (q == 2) {
                sql += " AND MONTH(ngay) BETWEEN 4 AND 6";
            }
            if (q == 3) {
                sql += " AND MONTH(ngay) BETWEEN 7 AND 9";
            }
            if (q == 4) {
                sql += " AND MONTH(ngay) BETWEEN 10 AND 12";
            }
        }

        sql += " ORDER BY ngay DESC";

        try {
            conn = new dbConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs = ps.executeQuery();
            while (rs.next()) {
                // Map dữ liệu từ ResultSet vào Object DoanhThu
                // Giả sử bạn đã có constructor đầy đủ
                DoanhThu dt = new DoanhThu(
                        rs.getInt("maDTu"),
                        rs.getTimestamp("ngay"),
                        rs.getDouble("soTien"),
                        rs.getString("ghiChu"),
                        rs.getInt("maDH"),
                        rs.getInt("maND"),
                        rs.getString("loaiGiaodich")
                );
                list.add(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DoanhThu> getDoanhThuByFilterr(
            String year, String month, String quarter,
            int limit, int offset) {

        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT * FROM doanhthu WHERE 1=1";

        if (year != null && !year.isEmpty()) {
            sql += " AND YEAR(ngay) = " + year;
        }

        if (month != null && !month.isEmpty() && !month.equals("0")) {
            sql += " AND MONTH(ngay) = " + month;
        } else if (quarter != null && !quarter.isEmpty() && !quarter.equals("0")) {
            int q = Integer.parseInt(quarter);
            sql += switch (q) {
                case 1 ->
                    " AND MONTH(ngay) BETWEEN 1 AND 3";
                case 2 ->
                    " AND MONTH(ngay) BETWEEN 4 AND 6";
                case 3 ->
                    " AND MONTH(ngay) BETWEEN 7 AND 9";
                case 4 ->
                    " AND MONTH(ngay) BETWEEN 10 AND 12";
                default ->
                    "";
            };
        }

        sql += " ORDER BY ngay DESC LIMIT ? OFFSET ?";

        try {
            conn = new dbConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new DoanhThu(
                        rs.getInt("maDTu"),
                        rs.getTimestamp("ngay"),
                        rs.getDouble("soTien"),
                        rs.getString("ghiChu"),
                        rs.getInt("maDH"),
                        rs.getInt("maND"),
                        rs.getString("loaiGiaodich")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countDoanhThu(String year, String month, String quarter) {
        String sql = "SELECT COUNT(*) FROM doanhthu WHERE 1=1";

        if (year != null && !year.isEmpty()) {
            sql += " AND YEAR(ngay) = " + year;
        }

        if (month != null && !month.isEmpty() && !month.equals("0")) {
            sql += " AND MONTH(ngay) = " + month;
        } else if (quarter != null && !quarter.isEmpty() && !quarter.equals("0")) {
            int q = Integer.parseInt(quarter);
            sql += switch (q) {
                case 1 ->
                    " AND MONTH(ngay) BETWEEN 1 AND 3";
                case 2 ->
                    " AND MONTH(ngay) BETWEEN 4 AND 6";
                case 3 ->
                    " AND MONTH(ngay) BETWEEN 7 AND 9";
                case 4 ->
                    " AND MONTH(ngay) BETWEEN 10 AND 12";
                default ->
                    "";
            };
        }

        try {
            conn = new dbConnect().getConnection();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
