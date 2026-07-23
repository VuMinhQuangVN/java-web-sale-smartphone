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
public class LoaiDAO {
    Connection conn = null;
    PreparedStatement ps = null;

    //Trả về danh sách thông tin các loại
    public List<Loai> getAll() {
        String sql = "SELECT * FROM loai";
        List<Loai> list = new ArrayList<>();

        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loai d = new Loai();
                d.setMaLoai(rs.getInt("maLoai"));
                d.setTenLoai(rs.getString("tenLoai"));
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //Thêm 1 loại
    public void insert(Loai l) throws SQLException {
        String sql = "INSERT INTO `loai`(`tenLoai`) VALUES (?)";
        try (Connection conn = new dbConnect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, l.getTenLoai());

            ps.executeUpdate();

        }
    }

//    // Lấy thông tin điện thoại theo mã
//    public Loai getById(int maDT) {
//        String sql = "SELECT * FROM dienthoai WHERE maDT = ?";
//        DienThoai d = null;
//
//        try (Connection conn = new dbConnect().getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, maDT);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    d = new DienThoai();
//                    d.setMaDT(rs.getInt("maDT"));
//                    d.setTenDT(rs.getString("tenDT"));
//                    d.setMaLoai(rs.getInt("maLoai"));
//                    d.setGia(rs.getDouble("gia"));
//                    d.setAnh(rs.getString("anh"));
//                    d.setMota(rs.getString("mota"));
//                    d.setSoluong(rs.getInt("soluong"));
//                    d.setHangSx(rs.getString("hangSx"));
//                    d.setManHinh(rs.getString("manHinh"));
//                    d.setPin(rs.getString("pin"));
//                    d.setChip(rs.getString("Chip"));
//                    d.setRAM(rs.getString("RAM"));
//                    d.setROM(rs.getString("ROM"));
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return d;
//    }
    
    // Cập nhật loại
    public void update(Loai l) throws SQLException {
        String sql = "UPDATE `loai` SET `tenLoai`=? WHERE `maLoai`=?";

        try (Connection conn = new dbConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, l.getTenLoai());
            ps.setInt(2, l.getMaLoai());

            ps.executeUpdate();

        }
    }
    
    // Xóa loại theo mã
    public void delete(int maLoai) throws SQLException {
        String sql = "DELETE FROM `loai` WHERE `maLoai`=?";

        try (Connection conn = new dbConnect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maLoai);
            ps.executeUpdate();

        }
    }
}

