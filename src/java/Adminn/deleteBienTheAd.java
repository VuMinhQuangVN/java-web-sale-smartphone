/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.BienTheDienThoaiDAO;
import Models.NguoiDung;
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
@WebServlet(name = "deleteBienTheAd", urlPatterns = {"/deleteBienTheAd"})
public class deleteBienTheAd extends HttpServlet {

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
            out.println("<title>Servlet deleteBienTheAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteBienTheAd at " + request.getContextPath() + "</h1>");
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
        NguoiDung admin = (NguoiDung) session.getAttribute("user");

        // 1️⃣ Kiểm tra đăng nhập và quyền admin
        if (admin == null || !"admin".equals(admin.getQuyen())) {
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }
        request.setAttribute("nguoidung", admin);
        
        String redirect = request.getParameter("redirect");
        String redirect1 = request.getParameter("redirect1");
        
        int maBienthe = Integer.parseInt(request.getParameter("id"));
        int maDT = Integer.parseInt(request.getParameter("maDT"));
        
        BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
        
        try {
            btdao.delete(maBienthe);
            session.setAttribute("message", "Xóa thành công!");
            session.setAttribute("type", "success");
        } catch (Exception e) {
            session.setAttribute("message", "Không thể xóa!");
            session.setAttribute("type", "error");
        }
        if(redirect != null){        
            response.sendRedirect(request.getContextPath() + "/manageVariantAd?maDT=" + maDT);
        }else if(redirect1 != null && redirect == null){
            response.sendRedirect(request.getContextPath() + "/editProductAd?id=" + maDT);
        }else{
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
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
