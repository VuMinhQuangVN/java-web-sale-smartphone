/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.ChiTietHoaDonDAO;
import Models.DonHang;
import Models.DonHangDAO;
import Models.NguoiDung;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
@WebServlet(name = "chiTietDonHangAd", urlPatterns = {"/chiTietDonHangAd"})
public class chiTietDonHangAd extends HttpServlet {

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
            out.println("<title>Servlet chiTietDonHangAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet chiTietDonHangAd at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        NguoiDung admin = (NguoiDung) session.getAttribute("user");

        // 1️⃣ Kiểm tra đăng nhập và quyền admin
        if (admin == null || !"admin".equals(admin.getQuyen())) {
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/manageOrderAd");
            return;
        }

        try {
            int maDH = Integer.parseInt(idStr);
            DonHangDAO dhdao = new DonHangDAO();

            DonHang dh = dhdao.getById(maDH);
            if (dh == null) {
                request.setAttribute("message", "Không tìm thấy đơn hàng #" + maDH);
                request.getRequestDispatcher("/Admin/manageOrder.jsp").forward(request, response);
                return;
            }

            // Lấy danh sách chi tiết đơn hàng (list map)
            ChiTietHoaDonDAO cthddao = new ChiTietHoaDonDAO();
            List<Map<String, Object>> listChiTiet = cthddao.get(maDH);

            // Gửi dữ liệu sang JSP
            request.setAttribute("nguoidung", admin);
            request.setAttribute("donhang", dh);
            request.setAttribute("listChiTiet", listChiTiet);

            request.getRequestDispatcher("/Admin/chiTietDonHang.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/manageOrderAd");
        }
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
        processRequest(request, response);
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
