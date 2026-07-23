/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

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
@WebServlet(name = "managerProductAd", urlPatterns = {"/managerProductAd"})
public class managerProductAd extends HttpServlet {

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
            out.println("<title>Servlet managerProductAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet managerProductAd at " + request.getContextPath() + "</h1>");
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
        if (session.getAttribute("message") != null) {
            request.setAttribute("message", session.getAttribute("message"));
            request.setAttribute("type", session.getAttribute("type"));

            session.removeAttribute("message");
            session.removeAttribute("type");
        }
        
        String keyword = request.getParameter("keyword");
        DienThoaiDAO dao = new DienThoaiDAO();
        List<DienThoai> list;

        int page = 1;
        int limit = 7;

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
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = dao.search(keyword, offset, limit);
            totalRecords = dao.countSearch(keyword);
        } else {
            list = dao.getAll(offset, limit);
            totalRecords = dao.countAll();
        }
        int totalPages = (int) Math.ceil((double) totalRecords / limit);

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("listDienThoai", list);

        request.getRequestDispatcher("/Admin/manageProduct.jsp").forward(request, response);
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
