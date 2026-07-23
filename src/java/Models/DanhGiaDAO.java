/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ACER
 */
public class DanhGiaDAO {
    // 1. Thêm mới một đánh giá (INSERT)
    public void insert(DanhGia d) throws SQLException {
        // Không cần insert ngayDanhGia vì trong SQL mình đã để DEFAULT current_timestamp
        String sql = "INSERT INTO danhgia (maND, maDT, maDH, soSao, noiDung) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = new dbConnect().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getMaND());
            ps.setInt(2, d.getMaDT());
            ps.setInt(3, d.getMaDH());
            ps.setInt(4, d.getSoSao());
            ps.setString(5, d.getNoiDung());

            ps.executeUpdate();
        }
    }

    // 1. Đếm tổng số đánh giá của 1 sản phẩm (để tính tổng số trang)
    public int countByMaDT(int maDT) throws SQLException {
        String sql = "SELECT COUNT(*) FROM danhgia WHERE maDT = ? AND trangThai = 'Hien'";
        try (Connection conn = new dbConnect().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // 2. Lấy danh sách đánh giá CÓ PHÂN TRANG (Limit, Offset)
    public List<DanhGia> getDanhGiaByMaDT_PhanTrang(int maDT, int offset, int limit) throws SQLException {
        List<DanhGia> list = new ArrayList<>();
        String sql = "SELECT dg.*, nd.tenND " +
                     "FROM danhgia dg " +
                     "JOIN nguoidung nd ON dg.maND = nd.maND " +
                     "WHERE dg.maDT = ? AND dg.trangThai = 'Hien' " +
                     "ORDER BY dg.ngayDanhGia DESC " +
                     "LIMIT ? OFFSET ?"; // Thêm LIMIT và OFFSET

        try (Connection conn = new dbConnect().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDT);
            ps.setInt(2, limit);  // Lấy bao nhiêu dòng
            ps.setInt(3, offset); // Bỏ qua bao nhiêu dòng đầu

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DanhGia d = new DanhGia();
                    d.setMaDanhGia(rs.getInt("maDanhGia"));
                    d.setMaND(rs.getInt("maND"));
                    d.setMaDT(rs.getInt("maDT"));
                    d.setMaDH(rs.getInt("maDH"));
                    d.setSoSao(rs.getInt("soSao"));
                    d.setNoiDung(rs.getString("noiDung"));
                    d.setNgayDanhGia(rs.getTimestamp("ngayDanhGia"));
                    d.setTenND(rs.getString("tenND"));
                    list.add(d);
                }
            }
        }
        return list;
    }
    
    // 2. Lấy danh sách đánh giá theo Mã Điện Thoại (để hiện thị chi tiết SP)
    public List<DanhGia> getDanhGiaByMaDT(int maDT) throws SQLException {
        List<DanhGia> list = new ArrayList<>();
        
        // Join bảng nguoidung để lấy tên người bình luận
        // Chỉ lấy những đánh giá có trạng thái là 'Hien'
        String sql = "SELECT dg.*, nd.tenND " +
                     "FROM danhgia dg " +
                     "JOIN nguoidung nd ON dg.maND = nd.maND " +
                     "WHERE dg.maDT = ? AND dg.trangThai = 'Hien' " +
                     "ORDER BY dg.ngayDanhGia DESC";

        try (Connection conn = new dbConnect().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDT);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DanhGia d = new DanhGia();
                    d.setMaDanhGia(rs.getInt("maDanhGia"));
                    d.setMaND(rs.getInt("maND"));
                    d.setMaDT(rs.getInt("maDT"));
                    d.setMaDH(rs.getInt("maDH"));
                    d.setSoSao(rs.getInt("soSao"));
                    d.setNoiDung(rs.getString("noiDung"));
                    d.setNgayDanhGia(rs.getTimestamp("ngayDanhGia"));
                    
                    // Set tên người dùng lấy từ bảng nguoidung
                    d.setTenND(rs.getString("tenND"));

                    list.add(d);
                }
            }
        }
        return list;
    }

    // 3. (Mở rộng) Kiểm tra xem người dùng đã đánh giá sản phẩm này trong đơn hàng này chưa
    // Dùng để ẩn nút "Đánh giá" nếu họ đã đánh giá rồi.
    public boolean hasReviewed(int maND, int maDT, int maDH) throws SQLException {
        String sql = "SELECT 1 FROM danhgia WHERE maND = ? AND maDT = ? AND maDH = ?";
        
        try (Connection conn = new dbConnect().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maND);
            ps.setInt(2, maDT);
            ps.setInt(3, maDH);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có kết quả trả về true
            }
        }
    }
    
    // 4. (Mở rộng) Lấy số sao trung bình của 1 điện thoại
    public double getAverageRating(int maDT) throws SQLException {
        String sql = "SELECT AVG(soSao) as tbSao FROM danhgia WHERE maDT = ? AND trangThai = 'Hien'";
         try (Connection conn = new dbConnect().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maDT);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getDouble("tbSao");
                }
            }
        }
        return 0;
    }
}
