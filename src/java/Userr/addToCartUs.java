/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.BienTheDienThoai;
import Models.BienTheDienThoaiDAO;
import Models.ChiTietGioHang;
import Models.ChiTietGioHangDAO;
import Models.DienThoai;
import Models.DienThoaiDAO;
import Models.GioHang;
import Models.GioHangDAO;
import Models.NguoiDung;
import com.google.gson.JsonObject;
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
@WebServlet(name = "addToCartUs", urlPatterns = {"/addToCartUs"})
public class addToCartUs extends HttpServlet {

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
            out.println("<title>Servlet addToCartUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addToCartUs at " + request.getContextPath() + "</h1>");
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
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();
        JsonObject json = new JsonObject();

        try {
            HttpSession session = request.getSession();
            NguoiDung nd = (NguoiDung) session.getAttribute("user");

            if (nd == null) {
                json.addProperty("success", false);
                json.addProperty("message", "Bạn cần đăng nhập để thêm sản phẩm vào giỏ.");
                out.print(json.toString());
                return;
            }

            GioHangDAO ghdao = new GioHangDAO();
            GioHang gh = ghdao.getOrCreateCart(nd.getMaND());

            BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
            int maBienThe = Integer.parseInt(request.getParameter("maBienThe"));
            BienTheDienThoai bt = btdao.getById(maBienThe);

            ChiTietGioHangDAO ctghdao = new ChiTietGioHangDAO();
            ChiTietGioHang ctgh = ctghdao.getGioHang(gh.getMaGiohang(), maBienThe);

            int soLuong = Integer.parseInt(request.getParameter("soLuong"));

            // ⚠️ 2. Kiểm tra biến thể
            if (bt == null) {
                json.addProperty("success", false);
                json.addProperty("message", "Biến thể không tồn tại.");
            } // ⚠️ 3. Thêm mới chi tiết giỏ hàng
            else if (ctgh == null) {
                if (soLuong > bt.getSoLuong()) {
                    json.addProperty("success", false);
                    json.addProperty("message", "Số lượng yêu cầu vượt quá tồn kho (" + bt.getSoLuong() + ").");
                } else {
                    ChiTietGioHang ct = new ChiTietGioHang();
                    ct.setMaGiohang(gh.getMaGiohang());
                    ct.setMaBienthe(maBienThe);
                    ct.setSoLuong(soLuong);
                    ct.setGia(bt.getGia());
                    ctghdao.insertChiTietGiohang(ct);

                    json.addProperty("success", true);
                    json.addProperty("message", "Đã thêm vào giỏ hàng!");
                }
            } // ⚠️ 4. Cập nhật số lượng nếu sản phẩm đã tồn tại
            else {
                if (ctgh.getSoLuong() + soLuong > bt.getSoLuong()) {
                    json.addProperty("success", false);
                    json.addProperty("message", "Số lượng yêu cầu vượt quá tồn kho (" + bt.getSoLuong() + ").");
                } else {
                    ctghdao.updateSoLuong(ctgh.getMaCTGH(), ctgh.getSoLuong() + soLuong);
                    json.addProperty("success", true);
                    json.addProperty("message", "Đã cập nhật số lượng trong giỏ hàng!");
                }
            }
            
            if (json.get("success").getAsBoolean()) {
            int newCartSize = ctghdao.getSoLuongBinhThuongTrongGioHang(nd.getMaND() ,gh.getMaGiohang()); 
            json.addProperty("cartSize", newCartSize);

            session.setAttribute("cartSize", newCartSize);
        }

        } catch (Exception e) {
             e.printStackTrace();
            json.addProperty("success", false);
            json.addProperty("message", "Thêm thất bại, vui lòng thử lại!");
        }

        out.print(json.toString());
        out.flush();

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
