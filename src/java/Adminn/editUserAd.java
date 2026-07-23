/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

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
@WebServlet(name = "editUserAd", urlPatterns = {"/editUserAd"})
public class editUserAd extends HttpServlet {

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
            out.println("<title>Servlet editUserAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editUserAd at " + request.getContextPath() + "</h1>");
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
        
        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        // 2. Nếu chưa có thì tạo người dùng rỗng
        if (nd == null) {
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }

        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);
        int maND = Integer.parseInt(request.getParameter("id"));
        NguoiDungDAO ngdao = new NguoiDungDAO();
        NguoiDung ng = ngdao.getById(maND);
        request.setAttribute("nguoidungg", ng);
        request.getRequestDispatcher("/Admin/editUser.jsp").forward(request, response);
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
        NguoiDung admin = (NguoiDung) session.getAttribute("user");

        // 1️⃣ Kiểm tra đăng nhập và quyền admin
        if (admin == null || !"admin".equals(admin.getQuyen())) {
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }

        int maND = Integer.parseInt(request.getParameter("maND"));
        String tenND = request.getParameter("tenND");
        String matkhau = request.getParameter("matkhau").trim();
        String quyen = request.getParameter("quyen");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String diachi = request.getParameter("diachi");
        
        NguoiDungDAO nddao = new NguoiDungDAO();
        NguoiDung ng = nddao.getById(maND);
        NguoiDung l = new NguoiDung();
        l.setMaND(maND);
        l.setTenND(tenND);
        if(matkhau == null || matkhau.isEmpty()){
            matkhau = ng.getMatkhau();
        }
        l.setMatkhau(matkhau);
        l.setQuyen(quyen);
        l.setEmail(email);
        l.setSdt(sdt);
        l.setDiachi(diachi);
        nddao.update(l);
        if("admin".equals(quyen)){
            NguoiDung ngg = nddao.getById(maND);
            request.setAttribute("nguoidung", ngg);
            session.setAttribute("user", ngg);
        }
        response.sendRedirect(request.getContextPath() + "/manageUserAd");
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
