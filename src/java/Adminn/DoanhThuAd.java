/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.DoanhThu;
import Models.DoanhThuDAO;
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
@WebServlet(name = "DoanhThuAd", urlPatterns = {"/DoanhThuAd"})
public class DoanhThuAd extends HttpServlet {

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
            out.println("<title>Servlet DoanhThuAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoanhThuAd at " + request.getContextPath() + "</h1>");
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
        if (nd == null || !"admin".equals(nd.getQuyen())) {
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }

        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);

        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String quarter = request.getParameter("quarter");

        // Mặc định nếu không chọn gì thì lấy năm hiện tại
        if (year == null) {
            year = String.valueOf(java.time.Year.now().getValue());
        }

        DoanhThuDAO dao = new DoanhThuDAO();

        // Giữ lại lựa chọn để hiển thị trên Form
        request.setAttribute("selectedYear", year);
        request.setAttribute("selectedMonth", month);
        request.setAttribute("selectedQuarter", quarter);
        int page = 1;
        int recordsPerPage = 10;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int offset = (page - 1) * recordsPerPage;

        List<DoanhThu> list = dao.getDoanhThuByFilterr(year, month, quarter, recordsPerPage, offset);

        int totalRecords = dao.countDoanhThu(year, month, quarter);
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        
        // Tính tổng doanh thu của danh sách lọc được
        double tongTien = 0;
        for (DoanhThu dt : list) {
            tongTien += dt.getSoTien();
        }

        request.setAttribute("listDT", list);
        request.setAttribute("tongTien", tongTien);
        
        request.getRequestDispatcher("/Admin/revenueReport.jsp").forward(request, response);
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
