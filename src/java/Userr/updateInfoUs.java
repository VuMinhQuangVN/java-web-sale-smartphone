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
@WebServlet(name = "updateInfoUs", urlPatterns = {"/updateInfoUs"})
public class updateInfoUs extends HttpServlet {

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
            out.println("<title>Servlet updateInfoUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateInfoUs at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }

        // 3. Gửi về JSP

        int maND = nd.getMaND();
        String tenND = request.getParameter("tenND");
        String matkhau = null;
        String quyen = nd.getQuyen();
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String diachi = request.getParameter("diachi");

        NguoiDungDAO nddao = new NguoiDungDAO();
        NguoiDung l = new NguoiDung();
        l.setMaND(maND);
        l.setTenND(tenND);
        if (matkhau == null || matkhau.isEmpty()) {
            matkhau = nd.getMatkhau();
        }
        l.setMatkhau(matkhau);
        l.setQuyen(quyen);
        l.setEmail(email);
        l.setSdt(sdt);
        l.setDiachi(diachi);

        try {
            nddao.update(l);
            
            NguoiDung ng = nddao.getById(maND);
            request.setAttribute("nguoidung", ng);
            session.setAttribute("user", ng);
            request.setAttribute("message", "Cập nhật thông tin thành công!");
            request.setAttribute("type", "success");
            
            request.getRequestDispatcher("/User/infomation.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Cập nhật thất bại! Vui lòng thử lại.");
            request.setAttribute("type", "error");
            request.getRequestDispatcher("/User/infomation.jsp").forward(request, response);
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
