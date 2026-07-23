/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.BienTheDienThoai;
import Models.BienTheDienThoaiDAO;
import Models.ChiTietGioHangDAO;
import Models.DanhGia;
import Models.DanhGiaDAO;
import Models.DienThoai;
import Models.DienThoaiDAO;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
@WebServlet(name = "chiTietSP", urlPatterns = {"/chiTietSP"})
public class chiTietSP extends HttpServlet {

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
            out.println("<title>Servlet chiTietSP</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet chiTietSP at " + request.getContextPath() + "</h1>");
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

        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);
        
        int maDT = Integer.parseInt(request.getParameter("id"));
        DienThoai dt = new DienThoaiDAO().getById(maDT);

        BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
        List<BienTheDienThoai> listt = btdao.getByDienThoai(maDT);
        
        // --- MỚI: XỬ LÝ ĐÁNH GIÁ CÓ PHÂN TRANG ---
        DanhGiaDAO dgDao = new DanhGiaDAO();
        List<DanhGia> listDanhGia = new ArrayList<>();
        double trungBinhSao = 0;
        int totalReviews = 0;
        int currentPage = 1;
        int reviewsPerPage = 5; // Số đánh giá mỗi trang
        int totalPages = 1;

        try {
            // 1. Lấy trang hiện tại từ URL (VD: chiTietSP?id=1&page=2)
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                try {
                    currentPage = Integer.parseInt(pageStr);
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }
            
            // 2. Lấy tổng số đánh giá & Sao trung bình (Tính trên toàn bộ dữ liệu)
            totalReviews = dgDao.countByMaDT(maDT);
            trungBinhSao = dgDao.getAverageRating(maDT);
            
            // 3. Tính toán phân trang
            if (totalReviews > 0) {
                totalPages = (int) Math.ceil((double) totalReviews / reviewsPerPage);
                // Kiểm tra nếu page > totalPages thì gán lại
                if (currentPage > totalPages) currentPage = totalPages;
                
                int offset = (currentPage - 1) * reviewsPerPage;
                
                // 4. Lấy danh sách đánh giá cho trang hiện tại
                listDanhGia = dgDao.getDanhGiaByMaDT_PhanTrang(maDT, offset, reviewsPerPage);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("listDanhGia", listDanhGia);
        request.setAttribute("trungBinhSao", trungBinhSao);
        request.setAttribute("totalReviews", totalReviews); // Tổng số lượng review
        request.setAttribute("currentPage", currentPage);   // Trang hiện tại
        request.setAttribute("totalPages", totalPages);     // Tổng số trang
        
        
        request.setAttribute("listBienThe", listt);
        request.setAttribute("dienthoai", dt);
        request.getRequestDispatcher("User/detailSp.jsp").forward(request, response);
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
