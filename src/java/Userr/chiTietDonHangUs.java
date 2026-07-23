/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.ChiTietGioHangDAO;
import Models.ChiTietHoaDonDAO;
import Models.DanhGiaDAO;
import Models.DienThoai;
import Models.DonHang;
import Models.DonHangDAO;
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
import java.util.Map;

/**
 *
 * @author ACER
 */
@WebServlet(name = "chiTietDonHangUs", urlPatterns = {"/chiTietDonHangUs"})
public class chiTietDonHangUs extends HttpServlet {

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
            out.println("<title>Servlet chiTietDonHangUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet chiTietDonHangUs at " + request.getContextPath() + "</h1>");
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
        
        if (nd == null) {
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }
        request.setAttribute("nguoidung", nd);

        String idString = request.getParameter("id");
        int id = 0;
        try {
            id = (int) Integer.parseInt(idString);
            List<Map<String, Object>> listChiTiet = new ArrayList<>();
            ChiTietHoaDonDAO cthddao = new ChiTietHoaDonDAO();
            listChiTiet = cthddao.get(id);
            

            DonHangDAO dhdao = new DonHangDAO();
            DonHang dh = new DonHang();
            dh = dhdao.getById(id);                 
            request.setAttribute("donHang", dh);
            
             // 3. --- MỚI: Kiểm tra trạng thái đánh giá cho từng sản phẩm ---
            DanhGiaDAO dgDao = new DanhGiaDAO();
            for (Map<String, Object> item : listChiTiet) {
                DienThoai dt = (DienThoai) item.get("dienthoai");
                // Gọi hàm hasReviewed (bạn đã tạo ở bước trước)
                boolean daDanhGia = dgDao.hasReviewed(nd.getMaND(), dt.getMaDT(), id);
                // Đẩy trạng thái này vào map để JSP dùng
                item.put("daDanhGia", daDanhGia);
            }
            
            request.setAttribute("listChiTiet", listChiTiet);
            
            request.getRequestDispatcher("/User/chiTietDonHang.jsp").forward(request, response);
        } catch (Exception e) {
            int page = 1;
            int limit = 2;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (Exception ex) {
                    page = 1;
                }
            }
            int offset = (page - 1) * limit;
            int totalRecords;

            List<DonHang> listDonHang = new ArrayList<>();
            DonHangDAO dhdao = new DonHangDAO();
            listDonHang = dhdao.getByIdND(nd.getMaND(), offset, limit);
            totalRecords = dhdao.countSearchND(nd.getMaND());
            int totalPages = (int) Math.ceil((double) totalRecords / limit);

            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("listDonHang", listDonHang);

            request.setAttribute("message", "Đã xảy ra lỗi, vui lòng thử lại!");
            request.setAttribute("type", "error");
            request.getRequestDispatcher("/User/lichSuDonHang.jsp").forward(request, response);
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
