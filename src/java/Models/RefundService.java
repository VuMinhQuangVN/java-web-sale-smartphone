/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ACER
 */
public class RefundService {
    
    
    public static void processRefund(NguoiDung nd, double amount, String reason) {
        
        DoanhThu refundRecord = new DoanhThu();
        
        refundRecord.setMaND(nd.getMaND());
        refundRecord.setMaDH(null);         
        refundRecord.setNgay(new java.util.Date());
        refundRecord.setSoTien(-amount);    
        refundRecord.setLoaiGiaodich("REFUND");
        
        String ghiChu = String.format("HOÀN TIỀN cho người dùng: %s (ID: %d). Lý do: %s", 
                                      nd.getTenND(), nd.getMaND(), reason);
        refundRecord.setGhiChu(ghiChu);
        
        try {
            DoanhThuDAO dtdao = new DoanhThuDAO();
            
            dtdao.insert(refundRecord); 

            System.out.println("GHI NHẬN HOÀN TIỀN THÀNH CÔNG.");

        } catch (Exception e) {
            System.err.println("!!! LỖI NGHIÊM TRỌNG: KHÔNG THỂ GHI NHẬN GIAO DỊCH HOÀN TIỀN !!!");
            System.err.println("CHI TIẾT: " + ghiChu);
            e.printStackTrace();
        }
    }
}
