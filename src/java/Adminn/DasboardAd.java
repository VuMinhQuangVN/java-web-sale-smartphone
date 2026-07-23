/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.DoanhThuDAO;
import Models.DonHangDAO;
import Models.NguoiDung;
import Models.ThongKeDAO;
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
@WebServlet(name = "DasboardAd", urlPatterns = {"/DasboardAd"})
public class DasboardAd extends HttpServlet {

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
            out.println("<title>Servlet DasboardAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DasboardAd at " + request.getContextPath() + "</h1>");
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
    public void init() throws ServletException {
        Models.AutoOrderUpdater.startScheduler();
    }

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

        DoanhThuDAO dtDao = new DoanhThuDAO();
        ThongKeDAO tkDao = new ThongKeDAO();

        double tongDoanhThu = dtDao.getTongDoanhThu();
        double tongHoanTien = tkDao.getTongHoanTien(); // âm nên hiển thị giá trị tuyệt đối
        int soDonHoanTat = tkDao.countDonHoanTat();
        int soDonDangGiao = tkDao.countDonDangGiao();
        int tongKhachHang = tkDao.countKhachHang();
        String spBanChay = tkDao.getSanPhamBanChayNhat();

        request.setAttribute("tongDoanhThu", tongDoanhThu);
        request.setAttribute("tongHoanTien", Math.abs(tongHoanTien));
        request.setAttribute("soDonHoanTat", soDonHoanTat);
        request.setAttribute("soDonDangGiao", soDonDangGiao);
        request.setAttribute("tongKhachHang", tongKhachHang);
        request.setAttribute("spBanChay", spBanChay);

        DoanhThuDAO dtdao = new DoanhThuDAO();
        List<Map<String, Object>> doanhThu7Ngay = dtdao.getDoanhThu7Ngay();
        request.setAttribute("doanhThu7Ngay", doanhThu7Ngay);

        DonHangDAO dhdao = new DonHangDAO();
        List<Map<String, Object>> thongKeTrangThai = dhdao.getThongKeTrangThai();
        request.setAttribute("thongKeTrangThai", thongKeTrangThai);

        ThongKeDAO thongKeDAO = new ThongKeDAO();
        List<Map<String, Object>> topSP = thongKeDAO.getTop5SanPhamBanChay();
        request.setAttribute("topSP", topSP);

        DoanhThuDAO doanhThuDAO = new DoanhThuDAO();
        List<Map<String, Object>> doanhThuThang = doanhThuDAO.getDoanhThuTheoThang();
        request.setAttribute("doanhThuThang", doanhThuThang);

        List<Map<String, Object>> topKH = tkDao.getTopKhachHang();
        List<Map<String, Object>> donTheoThang = tkDao.getDonTheoThang();
        if (topKH == null || topKH.isEmpty()) {
            request.setAttribute("msgTopKH", "⚠️ Chưa có dữ liệu khách hàng chi tiêu.");
        } else if (topKH.size() < 5) {
            request.setAttribute("msgTopKH", "⚠️ Hiện mới có " + topKH.size() + " khách hàng — chưa đủ 5 để thống kê đầy đủ.");
        }
        request.setAttribute("topKH", topKH);
        if (donTheoThang == null || donTheoThang.isEmpty()) {
            request.setAttribute("msgDonThang", "⚠️ Chưa có dữ liệu đơn hàng theo tháng.");
        }
        request.setAttribute("donTheoThang", donTheoThang);

        request.getRequestDispatcher("/Admin/Dashboard.jsp").forward(request, response);
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
