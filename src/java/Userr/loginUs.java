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
@WebServlet(name = "loginUs", urlPatterns = {"/loginUs"})
public class loginUs extends HttpServlet {

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
            out.println("<title>Servlet loginUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginUs at " + request.getContextPath() + "</h1>");
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

        String redirect = request.getParameter("redirect");
        String idString = request.getParameter("id");
        if(idString != null){
            redirect = redirect + "?id=" + idString;
        }
        request.setAttribute("redirect", redirect);
        request.getRequestDispatcher("/User/login.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String err = null;

        NguoiDung ng = null;
        NguoiDungDAO nddao = new NguoiDungDAO();
        ng = nddao.getByND(email);
        if (ng == null) {
            err = "Email chưa chính xác!";
            request.setAttribute("error", err);
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
        } else if (ng.getMatkhau().equals(password)) {
            if (ng.getQuyen().equals("user")) {
                HttpSession session = request.getSession();
                session.setAttribute("user", ng);
                response.sendRedirect(request.getContextPath() + "/" + redirect);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", ng);
                response.sendRedirect(request.getContextPath() + "/DasboardAd");
            }
        } else {
            err = "Mật khẩu chưa chính xác!";
            request.setAttribute("error", err);
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
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
