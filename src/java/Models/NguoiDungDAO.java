/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ACER
 */
public class NguoiDungDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    
    //Trả về danh sách thông tin các người dùng
    public List<NguoiDung> getAll(int offset, int limit) {
        String sql = "SELECT * FROM nguoidung LIMIT ? OFFSET ?";
        List<NguoiDung> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    NguoiDung d = new NguoiDung();
                    d.setMaND(rs.getInt("maND"));
                    d.setTenND(rs.getString("tenND"));
                    d.setMatkhau(rs.getString("matkhau"));
                    d.setQuyen(rs.getString("quyen"));
                    d.setEmail(rs.getString("email"));
                    d.setSdt(rs.getString("sdt"));
                    d.setDiachi(rs.getString("diachi"));
                    list.add(d);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM nguoidung";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int insert(NguoiDung d) {
        int maND = -1;
        String sql = "INSERT INTO `nguoidung`(`tenND`, `matkhau`, `quyen`, `email`, `sdt`, `diachi`) "
                + "VALUES (?,?,?,?,?,?)";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, d.getTenND());
            ps.setString(2, d.getMatkhau());
            ps.setString(3, d.getQuyen());
            ps.setString(4, d.getEmail());
            ps.setString(5, d.getSdt());
            ps.setString(6, d.getDiachi());

            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    maND = rs.getInt(1); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maND; 
    }
    
    // Cập nhật người dùng
    public void update(NguoiDung d) {
        String sql = "UPDATE `nguoidung` SET `tenND`=?,"
                + "`matkhau`=?,`quyen`=?,`email`=?,`sdt`=?,`diachi`=? WHERE `maND`=?";

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getTenND());
            ps.setString(2, d.getMatkhau());
            ps.setString(3, d.getQuyen());
            ps.setString(4, d.getEmail());
            ps.setString(5, d.getSdt());
            ps.setString(6, d.getDiachi());
            ps.setInt(7, d.getMaND());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Lấy thông tin nguoi theo mã
    public NguoiDung getById(int maND) {
        String sql = "SELECT * FROM nguoidung WHERE maND = ?";
        NguoiDung d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maND);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new NguoiDung();
                    d.setMaND(rs.getInt("maND"));
                    d.setTenND(rs.getString("tenND"));
                    d.setMatkhau(rs.getString("matkhau"));
                    d.setQuyen(rs.getString("quyen"));
                    d.setEmail(rs.getString("email"));
                    d.setSdt(rs.getString("sdt"));
                    d.setDiachi(rs.getString("diachi"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
    
    // Lấy thông tin nguoi theo emai
    public NguoiDung getByND(String email) {
        String sql = "SELECT * FROM nguoidung WHERE email = ?";
        NguoiDung d = null;

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new NguoiDung();
                    d.setMaND(rs.getInt("maND"));
                    d.setTenND(rs.getString("tenND"));
                    d.setMatkhau(rs.getString("matkhau"));
                    d.setQuyen(rs.getString("quyen"));
                    d.setEmail(rs.getString("email"));
                    d.setSdt(rs.getString("sdt"));
                    d.setDiachi(rs.getString("diachi"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
    
    //Mr Quyen
    public boolean saveOtp(String email, String otp, LocalDateTime expiry) {
        String sql = "UPDATE nguoidung SET otp = ?, otp_expiry = ? WHERE email = ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, otp);
            ps.setTimestamp(2, Timestamp.valueOf(expiry));
            ps.setString(3, email);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getOtpByEmail(String email) {
        String sql = "SELECT otp FROM nguoidung WHERE email = ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("otp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalDateTime getOtpExpiry(String email) {
        String sql = "SELECT otp_expiry FROM nguoidung WHERE email = ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("otp_expiry");
                return ts != null ? ts.toLocalDateTime() : null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean resetPassword(String email, String newPass) {
        String sql = "UPDATE nguoidung SET matkhau = ? WHERE email = ?";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPass);
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
