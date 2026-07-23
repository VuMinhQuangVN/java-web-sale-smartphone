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
public class DonHangDAO {

    Connection conn = null;
    PreparedStatement ps = null;

    // ✅ Lấy danh sách đơn hàng kèm tên người dùng
    public List<DonHang> getAll(int offset, int limit) {
        String sql = """
        SELECT dh.*, nd.tenND
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        ORDER BY dh.maDH DESC
        LIMIT ? OFFSET ?
    """;

        List<DonHang> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DonHang d = new DonHang();
                    d.setMaDH(rs.getInt("maDH"));
                    d.setMaND(rs.getInt("maND"));
                    d.setNgayDH(rs.getTimestamp("ngayDH"));
                    d.setTrangThai(rs.getString("trangThai"));
                    d.setTongTien(rs.getDouble("tongTien"));
                    d.setNgayGiao(rs.getTimestamp("ngayGiao"));
                    d.setNgayHoantat(rs.getTimestamp("ngayHoantat"));
                    d.setPhuongThucTT(rs.getString("phuongThucTT"));
                    d.setTrangThaiTT(rs.getString("trangThaiTT"));
                    d.setDiaChigiao(rs.getString("diaChigiao"));
                    d.setGhiChu(rs.getString("ghiChu"));
                    d.setTenND(rs.getString("tenND")); // 👈 lấy tên người dùng
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

// ✅ Đếm tổng số đơn hàng (có JOIN cho chắc cú, phòng sau thêm điều kiện)
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM donhang dh JOIN nguoidung nd ON dh.maND = nd.maND";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Thêm 1 đơn hàng
    public int insert(Connection conn, DonHang d) throws SQLException {
        int maDH = -1;
        String sql = """
        INSERT INTO donhang
        (maND, ngayDH, trangThai, tongTien, ngayGiao, ngayHoantat,
         phuongThucTT, trangThaiTT, diaChigiao, ghiChu)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getMaND());

            if (d.getNgayDH() != null) {
                ps.setTimestamp(2, new java.sql.Timestamp(d.getNgayDH().getTime()));
            } else {
                ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis())); // Mặc định là ngày hiện tại
            }

            ps.setString(3, d.getTrangThai());

            ps.setDouble(4, d.getTongTien());

            if (d.getNgayGiao() != null) {
                ps.setTimestamp(5, new java.sql.Timestamp(d.getNgayGiao().getTime()));
            } else {
                ps.setNull(5, java.sql.Types.TIMESTAMP);
            }

            if (d.getNgayHoantat() != null) {
                ps.setTimestamp(6, new java.sql.Timestamp(d.getNgayHoantat().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.TIMESTAMP);
            }

            ps.setString(7, d.getPhuongThucTT());

            ps.setString(8, d.getTrangThaiTT());

            ps.setString(9, d.getDiaChigiao());

            ps.setString(10, d.getGhiChu());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    maDH = rs.getInt(1);
                }
            }

            return maDH;
        }
    }

    //Thêm 1 đơn hàng
    public int insert(DonHang d) throws SQLException {
        int maDH = -1;
        String sql = """
        INSERT INTO donhang
        (maND, ngayDH, trangThai, tongTien, ngayGiao, ngayHoantat,
         phuongThucTT, trangThaiTT, diaChigiao, ghiChu)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getMaND());

            if (d.getNgayDH() != null) {
                ps.setTimestamp(2, new java.sql.Timestamp(d.getNgayDH().getTime()));
            } else {
                ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis())); // Mặc định là ngày hiện tại
            }

            ps.setString(3, d.getTrangThai());

            ps.setDouble(4, d.getTongTien());

            if (d.getNgayGiao() != null) {
                ps.setTimestamp(5, new java.sql.Timestamp(d.getNgayGiao().getTime()));
            } else {
                ps.setNull(5, java.sql.Types.TIMESTAMP);
            }

            if (d.getNgayHoantat() != null) {
                ps.setTimestamp(6, new java.sql.Timestamp(d.getNgayHoantat().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.TIMESTAMP);
            }

            ps.setString(7, d.getPhuongThucTT());

            ps.setString(8, d.getTrangThaiTT());

            ps.setString(9, d.getDiaChigiao());

            ps.setString(10, d.getGhiChu());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    maDH = rs.getInt(1);
                }
            }

            return maDH;
        }
    }

    public DonHang getById(int maDH) {
        String sql = """
        SELECT dh.*, nd.tenND
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE dh.maDH = ?
    """;
        DonHang d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDH);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new DonHang();
                    d.setMaDH(rs.getInt("maDH"));
                    d.setMaND(rs.getInt("maND"));
                    d.setNgayDH(rs.getTimestamp("ngayDH"));
                    d.setTrangThai(rs.getString("trangThai"));
                    d.setTongTien(rs.getDouble("tongTien"));
                    d.setNgayGiao(rs.getTimestamp("ngayGiao"));
                    d.setNgayHoantat(rs.getTimestamp("ngayHoantat"));
                    d.setPhuongThucTT(rs.getString("phuongThucTT"));
                    d.setTrangThaiTT(rs.getString("trangThaiTT"));
                    d.setDiaChigiao(rs.getString("diaChigiao"));
                    d.setGhiChu(rs.getString("ghiChu"));
                    d.setTenND(rs.getString("tenND")); // 👈 lấy luôn tên người dùng
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }

    // ✅ Tìm kiếm theo người dùng (kèm tên người dùng)
    public List<DonHang> getByIdND(int maND, int offset, int limit) {
        String sql = """
        SELECT dh.*, nd.tenND
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE dh.maND = ?
        ORDER BY dh.ngayDH DESC
        LIMIT ? OFFSET ?
    """;
        List<DonHang> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maND);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DonHang d = new DonHang();
                    d.setMaDH(rs.getInt("maDH"));
                    d.setMaND(rs.getInt("maND"));
                    d.setNgayDH(rs.getTimestamp("ngayDH"));
                    d.setTrangThai(rs.getString("trangThai"));
                    d.setTongTien(rs.getDouble("tongTien"));
                    d.setNgayGiao(rs.getTimestamp("ngayGiao"));
                    d.setNgayHoantat(rs.getTimestamp("ngayHoantat"));
                    d.setPhuongThucTT(rs.getString("phuongThucTT"));
                    d.setTrangThaiTT(rs.getString("trangThaiTT"));
                    d.setDiaChigiao(rs.getString("diaChigiao"));
                    d.setGhiChu(rs.getString("ghiChu"));
                    d.setTenND(rs.getString("tenND")); // 👈 Lấy luôn tên khách hàng
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

// ✅ Đếm số lượng kết quả theo người dùng
    public int countSearchND(int maND) {
        String sql = """
        SELECT COUNT(*) 
        FROM donhang
        WHERE maND = ?
    """;

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

    // ✅ Tìm kiếm theo trạng thái (kèm tên người dùng)
    public List<DonHang> getByTrangThai(String trangThai, int offset, int limit) {
        String sql = """
        SELECT dh.*, nd.tenND
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE dh.trangThai LIKE ?
        ORDER BY dh.ngayDH DESC
        LIMIT ? OFFSET ?
    """;
        List<DonHang> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + trangThai + "%");
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DonHang d = new DonHang();
                    d.setMaDH(rs.getInt("maDH"));
                    d.setMaND(rs.getInt("maND"));
                    d.setNgayDH(rs.getTimestamp("ngayDH"));
                    d.setTrangThai(rs.getString("trangThai"));
                    d.setTongTien(rs.getDouble("tongTien"));
                    d.setNgayGiao(rs.getTimestamp("ngayGiao"));
                    d.setNgayHoantat(rs.getTimestamp("ngayHoantat"));
                    d.setPhuongThucTT(rs.getString("phuongThucTT"));
                    d.setTrangThaiTT(rs.getString("trangThaiTT"));
                    d.setDiaChigiao(rs.getString("diaChigiao"));
                    d.setGhiChu(rs.getString("ghiChu"));
                    d.setTenND(rs.getString("tenND")); // 👈 Lấy luôn tên khách hàng
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

// ✅ Đếm số lượng kết quả theo trạng thái
    public int countSearchTT(String keyword) {
        String sql = """
        SELECT COUNT(*) 
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE dh.trangThai LIKE ?
    """;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
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

    // 🔍 Tìm kiếm theo phương thức thanh toán
    public List<DonHang> getByPTTT(String phuongThucTT, int offset, int limit) {
        String sql = """
        SELECT dh.*, nd.tenND
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE dh.phuongThucTT LIKE ?
        ORDER BY dh.ngayDH DESC
        LIMIT ? OFFSET ?
    """;

        List<DonHang> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + phuongThucTT + "%");
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DonHang d = new DonHang();
                    d.setMaDH(rs.getInt("maDH"));
                    d.setMaND(rs.getInt("maND"));
                    d.setTenND(rs.getString("tenND")); // ✅ lấy luôn tên người dùng
                    d.setNgayDH(rs.getTimestamp("ngayDH"));
                    d.setTrangThai(rs.getString("trangThai"));
                    d.setTongTien(rs.getDouble("tongTien"));

                    java.sql.Date ngayGiao = rs.getDate("ngayGiao");
                    if (ngayGiao != null) {
                        d.setNgayGiao(rs.getTimestamp("ngayGiao"));
                    }

                    java.sql.Date ngayHoantat = rs.getDate("ngayHoantat");
                    if (ngayHoantat != null) {
                        d.setNgayHoantat(rs.getTimestamp("ngayHoantat"));
                    }

                    d.setPhuongThucTT(rs.getString("phuongThucTT"));
                    d.setTrangThaiTT(rs.getString("trangThaiTT"));
                    d.setDiaChigiao(rs.getString("diaChigiao"));
                    d.setGhiChu(rs.getString("ghiChu"));
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
// 🔢 Đếm số kết quả theo phương thức thanh toán

    public int countSearchPTTT(String keyword) {
        String sql = """
        SELECT COUNT(*)
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE dh.phuongThucTT LIKE ?
    """;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ✅ Tìm kiếm đơn hàng (tên, sdt, email, trạng thái)
    public List<DonHang> searchDonHang(String keyword, String trangThai, String fromDate, String toDate, int offset, int limit) {
        StringBuilder sql = new StringBuilder("""
        SELECT dh.*, nd.tenND, nd.sdt, nd.email
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE 1=1
    """);

        // Danh sách tham số động
        List<Object> params = new ArrayList<>();

        // 🔍 Tìm theo tên, sđt, email
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (nd.tenND LIKE ? OR nd.sdt LIKE ? OR nd.email LIKE ? OR dh.maDH LIKE ?) ");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
            params.add(like);
        }

        // 🧩 Lọc theo trạng thái (nếu có)
        if (trangThai != null && !trangThai.equalsIgnoreCase("Tất cả") && !trangThai.isEmpty()) {
            sql.append(" AND dh.trangThai = ? ");
            params.add(trangThai);
        }

        // 3. Lọc theo NGÀY (Mới thêm)
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND dh.ngayDH >= ? ");
            params.add(fromDate + " 00:00:00"); // Bắt đầu ngày
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND dh.ngayDH <= ? ");
            params.add(toDate + " 23:59:59"); // Kết thúc ngày
        }

        // 📅 Sắp xếp và phân trang
        sql.append(" ORDER BY dh.ngayDH DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<DonHang> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Gán tham số
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DonHang d = new DonHang();
                d.setMaDH(rs.getInt("maDH"));
                d.setMaND(rs.getInt("maND"));
                d.setNgayDH(rs.getTimestamp("ngayDH"));
                d.setTongTien(rs.getDouble("tongTien"));
                d.setTrangThai(rs.getString("trangThai"));
                d.setPhuongThucTT(rs.getString("phuongThucTT"));
                d.setTrangThaiTT(rs.getString("trangThaiTT"));
                d.setDiaChigiao(rs.getString("diaChigiao"));
                d.setGhiChu(rs.getString("ghiChu"));
                d.setTenND(rs.getString("tenND")); // ✅ Gán thêm tên người dùng
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countSearchDonHang(String keyword, String trangThai, String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*)
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (nd.tenND LIKE ? OR nd.sdt LIKE ? OR nd.email LIKE ? OR dh.maDH LIKE ?) ");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
            params.add(like);
        }

        if (trangThai != null && !trangThai.equalsIgnoreCase("Tất cả") && !trangThai.isEmpty()) {
            sql.append(" AND dh.trangThai = ? ");
            params.add(trangThai);
        }

        // Thêm Date cho Count
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND dh.ngayDH >= ? ");
            params.add(fromDate + " 00:00:00");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND dh.ngayDH <= ? ");
            params.add(toDate + " 23:59:59");
        }

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

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

    // Cập nhật đơn hàng
    public void update(Connection conn, DonHang d) throws SQLException {
        String sql = "UPDATE `donhang` SET `trangThai`=?,trangThaiTT=?,"
                + "`ngayGiao`=?,`ngayHoantat`=?,"
                + "`ghiChu`=? WHERE `maDH`=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getTrangThai());
            ps.setString(2, d.getTrangThaiTT());
            if (d.getNgayGiao() != null) {
                ps.setTimestamp(3, new java.sql.Timestamp(d.getNgayGiao().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.TIMESTAMP);
            }

            if (d.getNgayHoantat() != null) {
                ps.setTimestamp(4, new java.sql.Timestamp(d.getNgayHoantat().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.TIMESTAMP);
            }

            ps.setString(5, d.getGhiChu());
            ps.setInt(6, d.getMaDH());

            ps.executeUpdate();
        }
    }

    //Xóa 1 đơn hàng
    public void delete(int maDH) {
        String sql = "DELETE FROM donhang WHERE maDH = ?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDH);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getThongKeTrangThai() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT trangThai, COUNT(*) AS soLuong FROM donhang GROUP BY trangThai";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("trangThai", rs.getString("trangThai"));
                map.put("soLuong", rs.getInt("soLuong"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DonHang> getDonHangByNguoiDung(int maND, String trangThai, int offset, int limit) {
        StringBuilder sql = new StringBuilder("""
        SELECT dh.*, nd.tenND
        FROM donhang dh
        JOIN nguoidung nd ON dh.maND = nd.maND
        WHERE dh.maND = ?
    """);

        List<Object> params = new ArrayList<>();
        params.add(maND);

        // Nếu có trạng thái và không phải là "Tất cả" thì thêm điều kiện lọc
        if (trangThai != null && !trangThai.equalsIgnoreCase("Tất cả") && !trangThai.isEmpty()) {
            sql.append(" AND dh.trangThai = ? ");
            params.add(trangThai);
        }

        sql.append(" ORDER BY dh.ngayDH DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<DonHang> list = new ArrayList<>();
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DonHang d = new DonHang();
                d.setMaDH(rs.getInt("maDH"));
                d.setMaND(rs.getInt("maND"));
                d.setNgayDH(rs.getTimestamp("ngayDH"));
                d.setTrangThai(rs.getString("trangThai"));
                d.setTongTien(rs.getDouble("tongTien"));
                d.setPhuongThucTT(rs.getString("phuongThucTT"));
                d.setTrangThaiTT(rs.getString("trangThaiTT"));
                d.setDiaChigiao(rs.getString("diaChigiao"));
                d.setNgayGiao(rs.getTimestamp("ngayGiao"));     // Lấy ngày giao
                d.setNgayHoantat(rs.getTimestamp("ngayHoantat")); // Lấy ngày hoàn tất
                d.setTenND(rs.getString("tenND"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countDonHangByNguoiDung(int maND, String trangThai) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*)
        FROM donhang
        WHERE maND = ?
    """);

        List<Object> params = new ArrayList<>();
        params.add(maND);

        // Nếu có trạng thái và không phải là "Tất cả" thì thêm điều kiện lọc
        if (trangThai != null && !trangThai.equalsIgnoreCase("Tất cả") && !trangThai.isEmpty()) {
            sql.append(" AND trangThai = ? ");
            params.add(trangThai);
        }

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

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

    public List<Map<String, Object>> getDonHangForExport(String keyword, String trangThai, String fromDate, String toDate) {
        List<Map<String, Object>> list = new ArrayList<>();

        // Câu lệnh SQL chuẩn bị sẵn các trường cần thiết cho báo cáo
        StringBuilder sql = new StringBuilder("""
        SELECT d.maDH, n.tenND, n.sdt, d.ngayDH, d.tongTien, 
               d.trangThai, d.phuongThucTT, d.trangThaiTT, d.diaChigiao, d.ghiChu 
        FROM donhang d 
        JOIN nguoidung n ON d.maND = n.maND 
        WHERE 1=1 
    """);

        List<Object> params = new ArrayList<>();

        // 1. Lọc theo từ khóa (Mã ĐH, Tên, SĐT)
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (n.tenND LIKE ? OR n.sdt LIKE ? OR d.maDH LIKE ?) ");
            String like = "%" + keyword.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }

        // 2. Lọc theo trạng thái
        if (trangThai != null && !trangThai.equalsIgnoreCase("Tất cả") && !trangThai.isEmpty()) {
            sql.append(" AND d.trangThai = ? ");
            params.add(trangThai);
        }

        // 3. Lọc theo khoảng thời gian
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND d.ngayDH >= ? ");
            params.add(fromDate + " 00:00:00"); // Bắt đầu ngày
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND d.ngayDH <= ? ");
            params.add(toDate + " 23:59:59"); // Kết thúc ngày
        }

        // Sắp xếp mới nhất lên đầu
        sql.append(" ORDER BY d.ngayDH DESC");

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Gán giá trị cho các dấu ?
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("maDH", rs.getInt("maDH"));
                map.put("tenND", rs.getString("tenND"));
                map.put("sdt", rs.getString("sdt"));
                map.put("ngayDH", rs.getTimestamp("ngayDH"));
                map.put("tongTien", rs.getDouble("tongTien"));
                map.put("trangThai", rs.getString("trangThai"));
                map.put("phuongThucTT", rs.getString("phuongThucTT"));
                map.put("trangThaiTT", rs.getString("trangThaiTT"));
                map.put("diaChigiao", rs.getString("diaChigiao"));
                map.put("ghiChu", rs.getString("ghiChu"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
