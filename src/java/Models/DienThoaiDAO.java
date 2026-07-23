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
public class DienThoaiDAO {

    Connection conn = null;
    PreparedStatement ps = null;

    //Trả về danh sách thông tin các điện thoại
    public List<DienThoai> getAll(int offset, int limit) {
        String sql = "SELECT * FROM dienthoai LIMIT ? OFFSET ?";
        List<DienThoai> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    DienThoai d = new DienThoai();
                    d.setMaDT(rs.getInt("maDT"));
                    d.setTenDT(rs.getString("tenDT"));
                    d.setMaLoai(rs.getInt("maLoai"));
                    d.setAnh(rs.getString("anh"));
                    d.setMota(rs.getString("mota"));
                    d.setHangSx(rs.getString("hangSx"));
                    d.setManHinh(rs.getString("manHinh"));
                    d.setPin(rs.getString("pin"));
                    d.setChip(rs.getString("Chip"));
                    d.setCamera(rs.getString("camera"));
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM dienthoai";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Thêm 1 điện thoại
    public int insert(DienThoai d) throws SQLException {
        int maDT = -1;
        String sql = "INSERT INTO dienthoai(tenDT, maLoai, anh, mota, hangSx, manHinh, pin, Chip, camera) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, d.getTenDT());
            ps.setInt(2, d.getMaLoai());
            ps.setString(3, d.getAnh());
            ps.setString(4, d.getMota());
            ps.setString(5, d.getHangSx());
            ps.setString(6, d.getManHinh());
            ps.setString(7, d.getPin());
            ps.setString(8, d.getChip());
            ps.setString(9, d.getCamera());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    maDT = rs.getInt(1);
                }
            }
            return maDT;
        }
    }

    // Lấy thông tin điện thoại theo mã
    public DienThoai getById(int maDT) {
        String sql = "SELECT * FROM dienthoai WHERE maDT = ?";
        DienThoai d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new DienThoai();
                    d.setMaDT(rs.getInt("maDT"));
                    d.setTenDT(rs.getString("tenDT"));
                    d.setMaLoai(rs.getInt("maLoai"));
                    d.setAnh(rs.getString("anh"));
                    d.setMota(rs.getString("mota"));
                    d.setHangSx(rs.getString("hangSx"));
                    d.setManHinh(rs.getString("manHinh"));
                    d.setPin(rs.getString("pin"));
                    d.setChip(rs.getString("Chip"));
                    d.setCamera(rs.getString("camera"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }

    // Lấy thông tin điện thoại theo ten
    public List<DienThoai> search(String tenDT, int offset, int limit) {
        String sql = "SELECT * FROM dienthoai WHERE tenDT LIKE ? LIMIT ? OFFSET ?";
        List<DienThoai> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + tenDT + "%");
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DienThoai d = new DienThoai();
                    d.setMaDT(rs.getInt("maDT"));
                    d.setTenDT(rs.getString("tenDT"));
                    d.setMaLoai(rs.getInt("maLoai"));
                    d.setAnh(rs.getString("anh"));
                    d.setMota(rs.getString("mota"));
                    d.setHangSx(rs.getString("hangSx"));
                    d.setManHinh(rs.getString("manHinh"));
                    d.setPin(rs.getString("pin"));
                    d.setChip(rs.getString("Chip"));
                    d.setCamera(rs.getString("camera"));
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy thông tin điện thoại theo hang
    public List<DienThoai> searchHang(String hangSx, int offset, int limit) {
        String sql = "SELECT * FROM dienthoai WHERE hangSx LIKE ? LIMIT ? OFFSET ?";
        List<DienThoai> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + hangSx + "%");
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DienThoai d = new DienThoai();
                    d.setMaDT(rs.getInt("maDT"));
                    d.setTenDT(rs.getString("tenDT"));
                    d.setMaLoai(rs.getInt("maLoai"));
                    d.setAnh(rs.getString("anh"));
                    d.setMota(rs.getString("mota"));
                    d.setHangSx(rs.getString("hangSx"));
                    d.setManHinh(rs.getString("manHinh"));
                    d.setPin(rs.getString("pin"));
                    d.setChip(rs.getString("Chip"));
                    d.setCamera(rs.getString("camera"));
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countSearch(String keyword) {
        String sql = "SELECT COUNT(*) FROM dienthoai WHERE tenDT LIKE ?";
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

    public int countSearchHang(String keyword) {
        String sql = "SELECT COUNT(*) FROM dienthoai WHERE hangSx LIKE ?";
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

    // Cập nhật điện thoại
    public void update(DienThoai d) throws SQLException {
        String sql = "UPDATE dienthoai SET tenDT=?, maLoai=?, anh=?, mota=?, hangSx=?, "
                + "manHinh=?, pin=?, Chip=?, camera=? WHERE maDT=?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getTenDT());
            ps.setInt(2, d.getMaLoai());
            ps.setString(3, d.getAnh());
            ps.setString(4, d.getMota());
            ps.setString(5, d.getHangSx());
            ps.setString(6, d.getManHinh());
            ps.setString(7, d.getPin());
            ps.setString(8, d.getChip());
            ps.setString(9, d.getCamera());

            ps.setInt(10, d.getMaDT());

            ps.executeUpdate();

        }
    }

    // Xóa điện thoại theo mã
    public void delete(int maDT) throws SQLException {
        String sql = "DELETE FROM dienthoai WHERE maDT = ?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDT);
            ps.executeUpdate();

        }
    }

    public List<DienThoai> searchAndFilter(String tenDT, int maLoai, String hangSx, int offset, int limit) {
        // Sử dụng StringBuilder để xây dựng câu lệnh SQL động
        StringBuilder sql = new StringBuilder("SELECT * FROM dienthoai WHERE 1=1");
        List<Object> params = new ArrayList<>(); // Danh sách để lưu các tham số

        // 1. Thêm điều kiện tìm theo tên nếu có
        if (tenDT != null && !tenDT.trim().isEmpty()) {
            sql.append(" AND tenDT LIKE ?");
            params.add("%" + tenDT + "%");
        }

        if (hangSx != null && !hangSx.trim().isEmpty()) { // THÊM ĐIỀU KIỆN LỌC HÃNG
            sql.append(" AND hangSx = ?");
            params.add(hangSx);
        }

        // 2. Thêm điều kiện lọc theo loại nếu có
        if (maLoai > 0) {
            sql.append(" AND maLoai = ?");
            params.add(maLoai);
        }

        // 3. Thêm phần phân trang
        sql.append(" LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<DienThoai> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Set các tham số vào PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DienThoai d = new DienThoai();
                    d.setMaDT(rs.getInt("maDT"));
                    d.setTenDT(rs.getString("tenDT"));
                    d.setMaLoai(rs.getInt("maLoai"));
                    d.setAnh(rs.getString("anh"));
                    d.setMota(rs.getString("mota"));
                    d.setHangSx(rs.getString("hangSx"));
                    d.setManHinh(rs.getString("manHinh"));
                    d.setPin(rs.getString("pin"));
                    d.setChip(rs.getString("Chip"));
                    d.setCamera(rs.getString("camera"));
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countAfterSearchAndFilter(String tenDT, int maLoai, String hangSx) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM dienthoai WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (tenDT != null && !tenDT.trim().isEmpty()) {
            sql.append(" AND tenDT LIKE ?");
            params.add("%" + tenDT + "%");
        }

        if (hangSx != null && !hangSx.trim().isEmpty()) {
            sql.append(" AND hangSx = ?");
            params.add(hangSx);
        }

        if (maLoai > 0) {
            sql.append(" AND maLoai = ?");
            params.add(maLoai);
        }

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Map<String, Object>> getBaoCaoTonKho(String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();

        // SQL: Lấy thông tin máy + biến thể, sắp xếp Tồn kho TĂNG DẦN (để thấy hàng sắp hết trước)
        StringBuilder sql = new StringBuilder("""
        SELECT dt.maDT, dt.tenDT, dt.hangSx, 
               bt.mauSac, bt.RAM, bt.ROM, bt.gia, bt.soLuong
        FROM dienthoai dt
        JOIN bienthedienthoai bt ON dt.maDT = bt.maDT
        WHERE 1=1
    """);

        // Nếu có tìm kiếm thì lọc theo tên máy
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND dt.tenDT LIKE ? ");
        }

        // Sắp xếp tồn kho tăng dần (ASC) -> Cái nào còn ít hiện lên đầu để nhập hàng
        sql.append(" ORDER BY bt.soLuong ASC, dt.tenDT ASC");

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("maDT", rs.getInt("maDT"));
                map.put("tenDT", rs.getString("tenDT"));
                map.put("hangSx", rs.getString("hangSx"));

                // Tạo chuỗi biến thể: VD "8GB - 256GB - Titan Tự Nhiên"
                String bienThe = rs.getString("RAM") + " - " + rs.getString("ROM") + " - " + rs.getString("mauSac");
                map.put("bienThe", bienThe);

                map.put("gia", rs.getDouble("gia"));
                map.put("soLuong", rs.getInt("soLuong"));

                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
