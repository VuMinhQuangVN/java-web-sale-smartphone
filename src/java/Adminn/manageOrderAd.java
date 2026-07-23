/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.DoanhThu;
import Models.DoanhThuDAO;
import Models.DonHang;
import Models.DonHangDAO;
import Models.NguoiDung;
import Models.dbConnect;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.*;

/**
 *
 * @author ACER
 */
@WebServlet(name = "manageOrderAd", urlPatterns = {"/manageOrderAd"})
public class manageOrderAd extends HttpServlet {

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
            out.println("<title>Servlet manageOrderAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet manageOrderAd at " + request.getContextPath() + "</h1>");
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
        // 1. THIẾT LẬP VÀ KIỂM TRA
        request.setCharacterEncoding("UTF-8");

        int pageSize = 10;

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        if (nd == null) {
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }
        request.setAttribute("nguoidung", nd);

        String keyword = request.getParameter("keyword");
        String trangThai = request.getParameter("trangThai");
        String fromDate = request.getParameter("fromDate"); // Mới
        String toDate = request.getParameter("toDate");     // Mới
        String pageStr = request.getParameter("page");

        if (trangThai == null || trangThai.isEmpty()) {
            trangThai = "Tất cả";
        }

        int currentPage = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid page number format: " + pageStr);
                currentPage = 1;
            }
        }

        // 3. TÍNH TOÁN LOGIC PHÂN TRANG
        DonHangDAO dhdao = new DonHangDAO();

        int totalItems = dhdao.countSearchDonHang(keyword, trangThai, fromDate, toDate);

        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        int offset = (currentPage - 1) * pageSize;

        List<DonHang> listDonHang = dhdao.searchDonHang(keyword, trangThai, fromDate, toDate, offset, pageSize);

        request.setAttribute("listDonHang", listDonHang);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("keyword", keyword);
        request.setAttribute("fromDate", fromDate); // Giữ lại giá trị
        request.setAttribute("toDate", toDate);     // Giữ lại giá trị
        request.setAttribute("trangThai", trangThai);

        request.getRequestDispatcher("/Admin/manageOrder.jsp").forward(request, response);
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
        NguoiDung adminUser = (NguoiDung) session.getAttribute("user");

        if (adminUser == null || !"admin".equals(adminUser.getQuyen())) {
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String[] orderIdsStr = request.getParameterValues("orderIds");

        // Xử lý cả hành động riêng lẻ (gửi lên "orderId")
        if (orderIdsStr == null) {
            String singleOrderId = request.getParameter("orderId");
            if (singleOrderId != null) {
                orderIdsStr = new String[]{singleOrderId};
            }
        }

        if (action == null || orderIdsStr == null || orderIdsStr.length == 0) {
            request.setAttribute("message", "Yêu cầu không hợp lệ.");
            request.setAttribute("type", "error");
            response.sendRedirect(request.getContextPath() + "/manageOrderAd");
            return;
        }

        List<Integer> orderIds = Arrays.stream(orderIdsStr)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Connection conn = null;
        try {
            conn = new dbConnect().getConnection();
            conn.setAutoCommit(false);

            DonHangDAO dhdao = new DonHangDAO();
            DoanhThuDAO dtdao = new DoanhThuDAO();

            // Lặp qua từng đơn hàng để xử lý
            for (int maDH : orderIds) {
                // Lấy thông tin mới nhất của đơn hàng để kiểm tra
                DonHang dh = dhdao.getById(maDH);
                if (dh == null) {
                    continue; // Bỏ qua nếu đơn hàng không tồn tại
                }
                switch (action) {
                    case "confirm_order":
                    case "confirm_selected":
                        if ("Chờ xác nhận".equals(dh.getTrangThai())) {
                            dh.setTrangThai("Đang giao");
                            dhdao.update(conn, dh);
                        }
                        break;

                    case "deliver_order":
                    case "deliver_selected":
                        if ("Đang giao".equals(dh.getTrangThai())) {
                            if ("Chưa thanh toán".equals(dh.getTrangThaiTT())) {
                                // Nếu chưa thanh toán, chuyển sang "Đã giao" và "Đã thanh toán"
                                dh.setTrangThai("Đã giao");
                                dh.setTrangThaiTT("Đã thanh toán");
                                dh.setNgayGiao(new java.util.Date());
                                dhdao.update(conn, dh);

                                // Thêm một bản ghi doanh thu cho đơn COD
                                DoanhThu dt = new DoanhThu();
                                dt.setMaDH(maDH);
                                dt.setMaND(dh.getMaND());
                                dt.setNgay(new java.util.Date());
                                dt.setSoTien(dh.getTongTien());
                                dt.setLoaiGiaodich("SALE_COD");
                                dt.setGhiChu(dh.getTenND() + " đã thanh toán(COD) cho đơn hàng #" + maDH);
                                dtdao.insert(conn, dt);
                            } else if ("Đã thanh toán".equals(dh.getTrangThaiTT())) {
                                dh.setTrangThai("Đã giao");
                                dh.setNgayGiao(new java.util.Date());
                                dhdao.update(conn, dh);
                            }
                        }
                        break;

                    // Không có case "cancel_order" vì đã bỏ chức năng hủy
                }
            }

            conn.commit();
            request.setAttribute("message", "Đã cập nhật " + orderIds.size() + " đơn hàng thành công.");
            request.setAttribute("type", "success");

        } catch (Exception e) {
            if (conn != null) try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            request.setAttribute("message", "Thao tác thất bại do lỗi hệ thống.");
            request.setAttribute("type", "error");
        } finally {
            if (conn != null) try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException exx) {
                exx.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/manageOrderAd");
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
