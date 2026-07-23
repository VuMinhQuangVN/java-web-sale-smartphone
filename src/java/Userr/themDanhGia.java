/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.DanhGia;
import Models.DanhGiaDAO;
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
@WebServlet(name = "themDanhGia", urlPatterns = {"/themDanhGia"})
public class themDanhGia extends HttpServlet {

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
            out.println("<title>Servlet themDanhGia</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet themDanhGia at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Cấu hình tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        // 2. Kiểm tra đăng nhập (Bảo mật)
        if (nd == null) {
            response.sendRedirect(request.getContextPath() + "/loginUs");
            return;
        }

        // 3. Lấy dữ liệu từ Form Modal
        String maDHStr = request.getParameter("maDH");
        String maDTStr = request.getParameter("maDT");
        String soSaoStr = request.getParameter("soSao");
        String noiDung = request.getParameter("noiDung");

        // URL để quay lại trang chi tiết đơn hàng
        String redirectUrl = request.getContextPath() + "/chiTietDonHangUs?id=" + maDHStr;

        try {
            int maDH = Integer.parseInt(maDHStr);
            int maDT = Integer.parseInt(maDTStr);
            int soSao = Integer.parseInt(soSaoStr);

            DanhGiaDAO dao = new DanhGiaDAO();

            // 4. Kiểm tra xem đã đánh giá chưa (Tránh F5 spam hoặc hack form)
            if (dao.hasReviewed(nd.getMaND(), maDT, maDH)) {
                session.setAttribute("message", "Bạn đã đánh giá sản phẩm này rồi!");
                session.setAttribute("type", "error");
            } else {
                // 5. Tạo đối tượng và Insert
                DanhGia dg = new DanhGia();
                dg.setMaND(nd.getMaND());
                dg.setMaDT(maDT);
                dg.setMaDH(maDH);
                dg.setSoSao(soSao);
                dg.setNoiDung(noiDung);

                // insert vào DB
                dao.insert(dg);

                session.setAttribute("message", "Cảm ơn bạn đã đánh giá sản phẩm!");
                session.setAttribute("type", "success");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("message", "Dữ liệu không hợp lệ!");
            session.setAttribute("type", "error");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            session.setAttribute("type", "error");
        }

        // 6. Quay lại trang chi tiết đơn hàng
        response.sendRedirect(redirectUrl);
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
