/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.ChiTietGioHangDAO;
import Models.GioHang;
import Models.GioHangDAO;
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
@WebServlet(name = "deleteCartUs", urlPatterns = {"/deleteCartUs"})
public class deleteCartUs extends HttpServlet {

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
            out.println("<title>Servlet deleteCartUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteCartUs at " + request.getContextPath() + "</h1>");
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
    public void loadCartAndForward(HttpServletRequest request, HttpServletResponse response, NguoiDung nd) throws IOException, ServletException{
        request.setAttribute("nguoidung", nd);
        
        int sl = 0;
        if (nd != null) {
            GioHangDAO ghdao = new GioHangDAO();
            GioHang gh = ghdao.getOrCreateCart(nd.getMaND());
            ChiTietGioHangDAO ctghdao = new ChiTietGioHangDAO();
            sl = ctghdao.getSoLuongBinhThuongTrongGioHang(nd.getMaND(), gh.getMaGiohang());
        }
        request.setAttribute("cartSize", sl);
        
        GioHangDAO ghdao = new GioHangDAO();
        GioHang gh = ghdao.getOrCreateCart(nd.getMaND());
        
        ChiTietGioHangDAO ctdao = new ChiTietGioHangDAO();
        List<Map<String, Object>> list = ctdao.getChiTietGioHang(gh.getMaGiohang());
        
        request.setAttribute("giohang", list);
        request.getRequestDispatcher("/User/gioHangKH.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        // 2. Nếu chưa có thì tạo người dùng rỗng
        if (nd == null) {
            request.getRequestDispatcher("/User/login.jsp?redirect=gioHang").forward(request, response);
            return;
        }

        
        int maCTGH;
        try {
            maCTGH = Integer.parseInt(request.getParameter("maCTGH"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Yêu cầu không hợp lệ!");
            request.setAttribute("type", "error");
            loadCartAndForward(request, response, nd);
            return;
        }
        
        ChiTietGioHangDAO ctdao = new ChiTietGioHangDAO();
        try {
            ctdao.delete(maCTGH);
            
            request.setAttribute("message", "Xóa thành công!");
            request.setAttribute("type", "success");
        } catch (Exception e) {
            request.setAttribute("message", "Xóa thất bại! Vui lòng thử lại.");
            request.setAttribute("type", "error");
        }
        
        loadCartAndForward(request, response, nd);
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
