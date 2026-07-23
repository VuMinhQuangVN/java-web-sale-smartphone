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
import java.sql.*;
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
@WebServlet(name = "addBienTheAd", urlPatterns = {"/addBienTheAd"})
public class addBienTheAd extends HttpServlet {

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
            out.println("<title>Servlet addBienTheAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addBienTheAd at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("nguoidung", admin);
        int maDT = Integer.parseInt(request.getParameter("maDT"));
        
        DienThoaiDAO dtdao = new DienThoaiDAO();
        DienThoai dt = dtdao.getById(maDT);
        request.setAttribute("dt", dt);
        
        request.getRequestDispatcher("/Admin/addBienThe.jsp").forward(request, response);
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
        int maDT = Integer.parseInt(request.getParameter("maDT"));
        String mauSac = request.getParameter("mauSac");
        String ram = request.getParameter("RAM");
        String rom = request.getParameter("ROM");
        double gia = Double.parseDouble(request.getParameter("gia").trim());
        int soluong = Integer.parseInt(request.getParameter("soLuong"));
        
        String redirect = request.getParameter("redirect");
        
        BienTheDienThoai bt = new BienTheDienThoai();
        bt.setMaDT(maDT);
        bt.setMauSac(mauSac);
        bt.setRAM(ram);
        bt.setROM(rom);
        bt.setGia(gia);
        bt.setSoLuong(soluong);
        
        BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();

        try {
            btdao.insert(bt); 
            session.setAttribute("message", "Thêm thành công.");
            session.setAttribute("type", "success");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Thêm thất bại do lỗi hệ thống!");
            session.setAttribute("type", "error");
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
