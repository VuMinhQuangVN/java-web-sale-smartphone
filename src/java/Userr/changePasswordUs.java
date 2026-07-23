/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.ChiTietGioHangDAO;
import Models.GioHang;
import Models.GioHangDAO;
import Models.NguoiDung;
import Models.NguoiDungDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ACER
 */
@WebServlet(name = "changePasswordUs", urlPatterns = {"/changePasswordUs"})
public class changePasswordUs extends HttpServlet {

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
            out.println("<title>Servlet changePasswordUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changePasswordUs at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF8");
        
         // 1. Lấy người dùng đang đăng nhập (nếu có)
        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        int sl = 0;
        if (nd != null) {
            GioHangDAO ghdao = new GioHangDAO();
            GioHang gh = ghdao.getOrCreateCart(nd.getMaND());
            ChiTietGioHangDAO ctghdao = new ChiTietGioHangDAO();
            sl = ctghdao.getSoLuongBinhThuongTrongGioHang(nd.getMaND(), gh.getMaGiohang());
        }
        request.setAttribute("cartSize", sl);
        // 2. Nếu chưa có thì tạo người dùng rỗng
        if (nd == null) {
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }

        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);
        
        request.getRequestDispatcher("/User/changePassword.jsp").forward(request, response);
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
        // 1. Lấy người dùng đang đăng nhập (nếu có)
        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        int sl = 0;
        if (nd != null) {
            GioHangDAO ghdao = new GioHangDAO();
            GioHang gh = ghdao.getOrCreateCart(nd.getMaND());
            ChiTietGioHangDAO ctghdao = new ChiTietGioHangDAO();
            sl = ctghdao.getSoLuongBinhThuongTrongGioHang(nd.getMaND(), gh.getMaGiohang());
        }
        request.setAttribute("cartSize", sl);
        // 2. Nếu chưa có thì tạo người dùng rỗng
        if (nd == null) {
            nd = new NguoiDung(); // đối tượng rỗng để JSP không bị null
            nd.setMaND(0);
            nd.setTenND("Khách vãng lai");
        }

        String oldPassword = request.getParameter("oldPassword").trim();
        String newPassword = request.getParameter("newPassword").trim();
        String confirmPassword = request.getParameter("confirmPassword").trim();

        if (oldPassword.equals(nd.getMatkhau()) && newPassword.equals(confirmPassword)) {
            NguoiDungDAO nddao = new NguoiDungDAO();
            NguoiDung l = new NguoiDung();
            l.setMaND(nd.getMaND());
            l.setTenND(nd.getTenND());
            l.setMatkhau(newPassword);
            l.setQuyen(nd.getQuyen());
            l.setEmail(nd.getEmail());
            l.setSdt(nd.getSdt());
            l.setDiachi(nd.getDiachi());
            nddao.update(l);
            NguoiDung ng = nddao.getById(nd.getMaND());
            request.setAttribute("nguoidung", ng);
            session.setAttribute("user", ng);
            request.setAttribute("message", "Đổi mật khẩu thành công!");
            request.setAttribute("type", "success");
            request.setAttribute("openTab", "password");
            request.getRequestDispatcher("/User/changePassword.jsp").forward(request, response);
        } else {
            request.setAttribute("openTab", "password");
            request.setAttribute("nguoidung", nd);
            if (!oldPassword.equals(nd.getMatkhau())) {
                request.setAttribute("message", "Mật khẩu cũ không chính xác!");
                request.setAttribute("type", "error");
            }
            else if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("message", "Xác nhận mật khẩu mới chưa chính xác!");
                request.setAttribute("type", "error");
            }
            request.getRequestDispatcher("/User/changePassword.jsp").forward(request, response);
        }

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
