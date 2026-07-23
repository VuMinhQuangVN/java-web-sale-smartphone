/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.BienTheDienThoai;
import Models.BienTheDienThoaiDAO;
import Models.DienThoai;
import Models.DienThoaiDAO;
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
import java.sql.*;

/**
 *
 * @author ACER
 */
@WebServlet(name = "editBienTheAd", urlPatterns = {"/editBienTheAd"})
public class editBienTheAd extends HttpServlet {

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
            out.println("<title>Servlet editBienTheAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editBienTheAd at " + request.getContextPath() + "</h1>");
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

        String redirect = request.getParameter("redirect");
        String redirect1 = request.getParameter("redirect1");
        if(redirect != null){        
            request.setAttribute("redirect", redirect);
        }else if(redirect1 != null && redirect == null){
            request.setAttribute("redirect", redirect1);
        }else{
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }
        
        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);
        int maBienthe = Integer.parseInt(request.getParameter("id"));
        int maDT = Integer.parseInt(request.getParameter("maDT"));

        BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
        BienTheDienThoai bt = btdao.getById(maBienthe);
        request.setAttribute("bt", bt);

        DienThoaiDAO dtdao = new DienThoaiDAO();
        DienThoai dt = dtdao.getById(maDT);
        request.setAttribute("dt", dt);

        request.getRequestDispatcher("/Admin/editBienThe.jsp").forward(request, response);
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
        
        String redirect = request.getParameter("redirect");
        
        Connection con = null;
        int maBienThe, maDT, soluong;
        double gia;
        try {
            maBienThe = Integer.parseInt(request.getParameter("maBienThe"));
            maDT = Integer.parseInt(request.getParameter("maDT"));
            soluong = Integer.parseInt(request.getParameter("soLuong"));
            gia = Double.parseDouble(request.getParameter("gia").trim());
        } catch (NumberFormatException e) {

            session.setAttribute("message", "Dữ liệu nhập vào không hợp lệ!");
            session.setAttribute("type", "error");
            response.sendRedirect(request.getContextPath() + "/editProductAd?id=" + request.getParameter("maDT"));
            return;
        }

        String mauSac = request.getParameter("mauSac");
        String ram = request.getParameter("RAM");
        String rom = request.getParameter("ROM");
        try {
            con = new dbConnect().getConnection();
            con.setAutoCommit(false);

            BienTheDienThoai bt = new BienTheDienThoai(maBienThe, maDT, mauSac, ram, rom, gia, soluong);
            BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
            btdao.update(con, bt);

            con.commit();
            
            session.setAttribute("message", "Cập nhật thành công!");
            session.setAttribute("type", "success");
        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            session.setAttribute("message", "Cập nhật thất bại! Đã có lỗi xảy ra với cơ sở dữ liệu.");
            session.setAttribute("type", "error");
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if("manage".equals(redirect.trim())){
            response.sendRedirect(request.getContextPath() + "/manageVariantAd?maDT=" + maDT);
        }else{
            response.sendRedirect(request.getContextPath() + "/editProductAd?id=" + maDT);
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
