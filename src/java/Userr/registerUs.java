/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.NguoiDung;
import Models.NguoiDungDAO;
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
@WebServlet(name = "registerUs", urlPatterns = {"/registerUs"})
public class registerUs extends HttpServlet {

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
            out.println("<title>Servlet registerUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registerUs at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF8");

        String redirect = request.getParameter("redirect");

        String tenND = request.getParameter("tenND");
        String matkhau = request.getParameter("matkhau").trim();
        String quyen = request.getParameter("quyen");
        String email = request.getParameter("email");
        String repassword = request.getParameter("repassword");

        NguoiDung ng = null;
        NguoiDungDAO nddao = new NguoiDungDAO();
        ng = nddao.getByND(email);
        String err = null;
        if (ng == null) {
            if (matkhau.equals(repassword)) {
                NguoiDung l = new NguoiDung();
                l.setTenND(tenND);
                l.setMatkhau(matkhau);
                l.setQuyen("user");
                l.setEmail(email);
                l.setSdt("");
                l.setDiachi("");

                nddao.insert(l);
                
                request.setAttribute("message", "Đăng ký thành công thành công!");
                request.setAttribute("type", "success");
                request.getRequestDispatcher("/User/login.jsp?redirect=" + redirect).forward(request, response);
                return;
            } else {
                err = "Mật khẩu chưa khớp!";
                request.setAttribute("email", email);
                request.setAttribute("error", err);
                request.getRequestDispatcher("/User/register.jsp?redirect=" + redirect).forward(request, response);
            }
        } else {
            err = "Tài khoản email đã tồn tại";
            request.setAttribute("email", email);
            request.setAttribute("error", err);
            request.getRequestDispatcher("/User/register.jsp?redirect=" + redirect).forward(request, response);
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
