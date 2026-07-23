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
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author ACER
 */
@WebServlet(name = "manageVariantAd", urlPatterns = {"/manageVariantAd"})
public class manageVariantAd extends HttpServlet {

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
            out.println("<title>Servlet manageVariantAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet manageVariantAd at " + request.getContextPath() + "</h1>");
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
        int maDT = Integer.parseInt(request.getParameter("maDT"));

        BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
        List<BienTheDienThoai> list = btdao.getByDienThoai(maDT);
        request.setAttribute("listBienThe", list);

        DienThoaiDAO dtdao = new DienThoaiDAO();
        DienThoai dt = dtdao.getById(maDT);
        request.setAttribute("dt", dt);

        String msg = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        
        if (msg != null) {
            // 1. Đẩy vào request để JSP hiển thị được
            request.setAttribute("message", msg);
            request.setAttribute("type", type);
            
            // 2. Xóa ngay khỏi session để F5 không hiện lại
            session.removeAttribute("message");
            session.removeAttribute("type");
        }
        
        request.getRequestDispatcher("/Admin/manageVariant.jsp").forward(request, response);
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
