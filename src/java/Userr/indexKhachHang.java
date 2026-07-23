/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.BienTheDienThoai;
import Models.BienTheDienThoaiDAO;
import Models.ChiTietGioHangDAO;
import Models.DienThoai;
import Models.DienThoaiDAO;
import Models.GioHang;
import Models.GioHangDAO;
import Models.Loai;
import Models.LoaiDAO;
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
@WebServlet(name = "indexKhachHang", urlPatterns = {"/indexKhachHang"})
public class indexKhachHang extends HttpServlet {

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
            out.println("<title>Servlet indexKhachHang</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet indexKhachHang at " + request.getContextPath() + "</h1>");
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
            nd = new NguoiDung(); // đối tượng rỗng để JSP không bị null
            nd.setMaND(0);
            nd.setTenND("Khách vãng lai");
        }

        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);

        String keyword = request.getParameter("keyword");
        String maLoaiParam = request.getParameter("maLoai");
        String hangSx = request.getParameter("hangSx");
        int maLoai = 0;
        if (maLoaiParam != null && !maLoaiParam.isEmpty()) {
            try {
                maLoai = Integer.parseInt(maLoaiParam);
            } catch (NumberFormatException e) {
                maLoai = 0;
            }
        }

        DienThoaiDAO dao = new DienThoaiDAO();
        List<DienThoai> list;

        int page = 1;
        int limit = 8;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (Exception e) {
                page = 1;
            }
        }

        int offset = (page - 1) * limit;
        int totalRecords;

        if ((keyword == null || keyword.trim().isEmpty())
                && (hangSx == null || hangSx.trim().isEmpty())
                && maLoai <= 0) {
            list = dao.getAll(offset, limit);
            totalRecords = dao.countAll();
        } else {
            list = dao.searchAndFilter(keyword, maLoai, hangSx, offset, limit);
            totalRecords = dao.countAfterSearchAndFilter(keyword, maLoai, hangSx);
        }

        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        BienTheDienThoaiDAO bienTheDAO = new BienTheDienThoaiDAO();
        Map<Integer, BienTheDienThoai> giaThapNhatMap = bienTheDAO.getGiaThapNhatCuaMoiDienThoai();

        LoaiDAO loaiDAO = new LoaiDAO();
        List<Loai> listLoai = loaiDAO.getAll();

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("maLoai", maLoai);
        request.setAttribute("listDienThoai", list);
        request.setAttribute("giaThapNhatMap", giaThapNhatMap);
        request.setAttribute("listLoai", listLoai);

        request.getRequestDispatcher("/User/indexKh.jsp").forward(request, response);
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
