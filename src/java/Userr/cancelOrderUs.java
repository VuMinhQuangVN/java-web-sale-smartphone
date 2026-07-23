/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.BienTheDienThoai;
import Models.BienTheDienThoaiDAO;
import Models.ChiTietHoaDon;
import Models.ChiTietHoaDonDAO;
import Models.DonHang;
import Models.DonHangDAO;
import Models.NguoiDung;
import Models.RefundService;
import Models.dbConnect;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
@WebServlet(name = "cancelOrderUs", urlPatterns = {"/cancelOrderUs"})
public class cancelOrderUs extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet cancelOrderUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet cancelOrderUs at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        if (nd == null) {
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }
        request.setAttribute("nguoidung", nd);
        boolean needsRefund = false;
        try {
            int maDH = Integer.parseInt(request.getParameter("maDH"));
            DonHangDAO dhdao = new DonHangDAO();

            DonHang dh = dhdao.getById(maDH);

            if (dh != null && dh.getMaND() == nd.getMaND() && ("Chờ xác nhận".equals(dh.getTrangThai()) || "Đã giao".equals(dh.getTrangThai()))) {

                // === BẮT ĐẦU TRANSACTION ĐỂ HỦY ĐƠN HÀNG ===
                Connection conn = null;
                try {
                    conn = new dbConnect().getConnection();
                    conn.setAutoCommit(false);

                    // Cập nhật trạng thái đơn hàng thành "Đã hủy"
                    dh.setTrangThai("Hủy");
                    if ("Đã thanh toán".equals(dh.getTrangThaiTT())){
                        needsRefund = true;
                        dh.setTrangThaiTT("Hoàn tiền");
                    }
                    dhdao.update(conn, dh);

                    // (TÙY CHỌN - NÂNG CAO) Hoàn trả số lượng sản phẩm về kho
                    List<Map<String, Object>> listChiTiet = new ArrayList<>();
                    ChiTietHoaDonDAO cthddao = new ChiTietHoaDonDAO();
                    listChiTiet = cthddao.getUpdateSL(conn, maDH);

                    BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
                    for (Map<String, Object> item : listChiTiet) {
                        BienTheDienThoai bt = (BienTheDienThoai) item.get("bienthe");
                        ChiTietHoaDon cthd = (ChiTietHoaDon) item.get("chitietdonhang");
                        int soLuongCon = bt.getSoLuong();
                        int soLuongMua = cthd.getSoLuong();
                        int soLuongUpdate = soLuongCon + soLuongMua;
                        btdao.updateSL(conn, bt.getMaBienthe(), soLuongUpdate);
                    }

                    //Hoàn tiền cho đơn đã thanh toán
                    if (needsRefund) {
                        RefundService.processRefund(nd, dh.getTongTien(), "Hủy hàng - hoàn tiền!");
                    }

                    conn.commit();
                    session.setAttribute("message", "Đã hủy đơn hàng #" + maDH + " thành công.");
                    session.setAttribute("type", "success");

                } catch (SQLException exx) {
                    if (conn != null) try {
                        conn.rollback();
                    } catch (SQLException exxx) {
                        exxx.printStackTrace();
                    }
                    exx.printStackTrace();
                    session.setAttribute("message", "Hủy đơn hàng thất bại do lỗi hệ thống.");
                    session.setAttribute("type", "error");
                } finally {
                    if (conn != null) try {
                        conn.setAutoCommit(true);
                        conn.close();
                    } catch (SQLException exxxx) {
                        exxxx.printStackTrace();
                    }
                }

            } else {
                // Đơn hàng không hợp lệ để hủy
                session.setAttribute("message", "Không thể hủy đơn hàng này.");
                session.setAttribute("type", "error");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Yêu cầu không hợp lệ(MADH).");
            session.setAttribute("type", "error");
        } catch (Exception e_main) {
            e_main.printStackTrace();
            session.setAttribute("message", "Đã có lỗi không mong muốn xảy ra.");
            session.setAttribute("type", "error");
        }

        // Sau khi xử lý, quay trở lại trang lịch sử đơn hàng
        response.sendRedirect(request.getContextPath() + "/lichSuDonHangUs");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
