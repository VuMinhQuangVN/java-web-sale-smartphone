/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.BienTheDienThoai;
import java.sql.*;
import Models.BienTheDienThoaiDAO;
import Models.ChiTietGioHangDAO;
import Models.ChiTietHoaDon;
import Models.ChiTietHoaDonDAO;
import Models.DienThoai;
import Models.DienThoaiDAO;
import Models.DoanhThu;
import Models.DoanhThuDAO;
import Models.DonHang;
import Models.DonHangDAO;
import Models.GioHang;
import Models.GioHangDAO;
import Models.NguoiDung;
import Models.RefundService;
import Models.dbConnect;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
@WebServlet(name = "checkoutUs", urlPatterns = {"/checkoutUs"})
public class checkoutUs extends HttpServlet {

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
            out.println("<title>Servlet checkoutUs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet checkoutUs at " + request.getContextPath() + "</h1>");
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
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }

        // 🔹 Kiểm tra xem người dùng đã có đủ thông tin chưa (số điện thoại và địa chỉ)
        if (nd.getSdt() == null || nd.getDiachi() == null
                || nd.getSdt().trim().isEmpty() || nd.getDiachi().trim().isEmpty()) {
            // Gửi thông báo yêu cầu cập nhật thông tin
            request.setAttribute("message", "Vui lòng cập nhật thông tin cá nhân trước khi thanh toán!");
            request.setAttribute("type", "error");
            request.getRequestDispatcher("/User/infomationUs.jsp").forward(request, response);
            return;
        }
        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);

        ChiTietGioHangDAO ctdao = new ChiTietGioHangDAO();
        List<Map<String, Object>> listSelectedItems = new ArrayList<>();

        String maBienTheStr = request.getParameter("maBienThe");
        String soLuongStr = request.getParameter("soLuong");

        if (maBienTheStr == null || soLuongStr == null) {

            request.setAttribute("destination", "cart");
            String[] selectedItems = request.getParameterValues("selectedItems");

            GioHangDAO ghdao = new GioHangDAO();
            GioHang gh = ghdao.getOrCreateCart(nd.getMaND());

            List<Map<String, Object>> list = ctdao.getChiTietGioHang(gh.getMaGiohang());

            if (selectedItems == null || selectedItems.length == 0) {
                // Không có sản phẩm nào được chọn
                request.setAttribute("giohang", list);
                request.setAttribute("message", "Vui lòng chọn ít nhất 1 sản phẩm để thanh toán!");
                request.setAttribute("type", "error");
                request.getRequestDispatcher("/User/gioHangKH.jsp").forward(request, response);
                return;
            }

            int[] maCTGHArray = Arrays.stream(selectedItems)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            //        Nếu chọn tất cả
            if (selectedItems.length == list.size()) {
                request.setAttribute("selectedAll", "true");
            } else {
                request.setAttribute("selectedAll", "false");
            }

            for (int item : maCTGHArray) {
                Map<String, Object> map = new HashMap<>();
                map = ctdao.getByMaCTGH(item);
                listSelectedItems.add(map);
            }

        } else {
            request.setAttribute("destination", "detail");
            request.setAttribute("selectedAll", "false");
            int maBienThe = Integer.parseInt(maBienTheStr);
            int soLuong = Integer.parseInt(soLuongStr);
            BienTheDienThoaiDAO btdtdao = new BienTheDienThoaiDAO();
            Map<String, Object> map = new HashMap<>();
            map = btdtdao.getDienThoaiByMaBT(maBienThe, soLuong);
            listSelectedItems.add(map);
        }

        session.setAttribute("selectedItemsForCheckout", listSelectedItems);//Có tất cả
        request.setAttribute("selectedItems", listSelectedItems);
        request.getRequestDispatcher("/User/checkout.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param maDTT
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void requestDetal(HttpServletRequest request, HttpServletResponse response, int maDTT) throws ServletException, IOException {
        int maDT = maDTT;
        DienThoai dt = new DienThoaiDAO().getById(maDT);

        BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
        List<BienTheDienThoai> listt = btdao.getByDienThoai(maDT);

        request.setAttribute("listBienThe", listt);
        request.setAttribute("dienthoai", dt);

    }

    public void requestCart(HttpServletRequest request, HttpServletResponse response, int maND) throws ServletException, IOException {
        GioHangDAO ghdao = new GioHangDAO();
        GioHang gh = ghdao.getOrCreateCart(maND);

        ChiTietGioHangDAO ctdao = new ChiTietGioHangDAO();
        List<Map<String, Object>> list = ctdao.getChiTietGioHang(gh.getMaGiohang());

        request.setAttribute("giohang", list);

    }

    private void handleErrorAndForward(HttpServletRequest request, HttpServletResponse response,
            String errorMessage, String destination, NguoiDung nd, int maDT)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("selectedItemsForCheckout");

        // 2. Thiết lập thông báo lỗi
        request.setAttribute("message", errorMessage);
        request.setAttribute("type", "error");
        request.setAttribute("user", nd);//ấy lại y hịt cái login 

        // 3. Tải lại dữ liệu cần thiết cho trang đích
        if ("cart".equals(destination)) {
            requestCart(request, response, nd.getMaND());
            request.getRequestDispatcher("/User/gioHangKH.jsp").forward(request, response);
        } else if ("detail".equals(destination)) {
            try {
                requestDetal(request, response, maDT);
                request.getRequestDispatcher("User/detailSp.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // Nếu maDTFromDetail không hợp lệ, trả về giỏ hàng cho an toàn
                requestCart(request, response, nd.getMaND());
                request.getRequestDispatcher("/User/gioHangKH.jsp").forward(request, response);
            }
        } else {

            requestCart(request, response, nd.getMaND());
            request.getRequestDispatcher("/User/gioHangKH.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        String selectedAll = request.getParameter("selectedAll");
        int maDT = 0;
        String destination = (String) request.getParameter("destination");
        boolean paymentSuccessful = false;

        double tongTien = 0.0;
        try {
            tongTien = Double.parseDouble(request.getParameter("tongTien"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String phuongThucTT = request.getParameter("phuongThucTT");

        //Xác nhận đã thanh toán
        if (phuongThucTT.equals("QR")) {
            paymentSuccessful = true;
        }

        int maDH = 0;
        boolean transactionSuccess = false;
        Connection conn = null;
        try {
            conn = new dbConnect().getConnection();
            conn.setAutoCommit(false);

            BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
            List<Map<String, Object>> selectedItems = (List<Map<String, Object>>) request.getSession().getAttribute("selectedItemsForCheckout");
            for (Map<String, Object> item : selectedItems) {
                BienTheDienThoai bt = (BienTheDienThoai) item.get("bienthe");
                DienThoai dt = (DienThoai) item.get("dienthoai");
                int soLuongMua = (int) item.get("soLuong");
                int maBienThe = bt.getMaBienthe();

                int soLuongTon = btdao.LockDataByMaBienThe(conn, maBienThe);
                if (soLuongTon < soLuongMua) {
                    throw new SQLException("Sản phẩm '" + ((DienThoai) item.get("dienthoai")).getTenDT() + "' không đủ số lượng.");

                }

            }

            //Thêm đơn hàng
            DonHang dh = new DonHang();
            dh.setMaND(nd.getMaND());
            dh.setNgayDH(new java.util.Date());
            dh.setTrangThai("Chờ xác nhận");
            dh.setTongTien(tongTien);
            dh.setPhuongThucTT(phuongThucTT);
            if (phuongThucTT.equals("QR")) {
                dh.setTrangThaiTT("Đã thanh toán");
            } else {
                dh.setTrangThaiTT("Chưa thanh toán");
            }
            dh.setDiaChigiao(nd.getDiachi());
            dh.setGhiChu(null);

            DonHangDAO dhdao = new DonHangDAO();

            maDH = (int) dhdao.insert(conn, dh);

            //Cập nhập lại số lượng & Thêm hóa đơn chi tiết
            BienTheDienThoaiDAO btdtdao = new BienTheDienThoaiDAO();
            ChiTietHoaDonDAO cthddao = new ChiTietHoaDonDAO();

            int i = 1;
            for (Map<String, Object> item : selectedItems) {
                //Cập nhập lại số lượng 
                BienTheDienThoai bt = (BienTheDienThoai) item.get("bienthe");
                DienThoai dt = (DienThoai) item.get("dienthoai");
                int soLuongMua = (int) item.get("soLuong");
                int soLuongConLai = bt.getSoLuong() - soLuongMua;

                btdtdao.updateSL(conn, bt.getMaBienthe(), soLuongConLai);

                //Thêm hóa đơn chi tiết
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setMaDH(maDH);
                cthd.setMaBienthe(bt.getMaBienthe());
                cthd.setSoLuong(soLuongMua);
                cthd.setGia(bt.getGia());
                cthd.setThanhTien((double) bt.getGia() * soLuongMua);
                cthddao.insert(conn, cthd);

                if ("cart".equals(destination)) {
                    if ("true".equals(selectedAll) && i == 1) {
                        GioHangDAO ghdao = new GioHangDAO();
                        GioHang gh = ghdao.getOrCreateCart(nd.getMaND());
                        ghdao.updateTrangthai(conn, gh.getMaGiohang(), "Đã đặt");
                        i++;
                    } else if ("false".equals(selectedAll)) {
                        int maCTGH = (int) item.get("maCTGH");
                        ChiTietGioHangDAO ctghdao = new ChiTietGioHangDAO();
                        ctghdao.delete(conn, maCTGH);
                    }
                }

                //Mua ỏ detail chỉ có 1 điện thoại:
                maDT = dt.getMaDT();
            }
            //Thêm doanh thu
            if (paymentSuccessful) {
                DoanhThuDAO dtdao = new DoanhThuDAO();
                DoanhThu dtt = new DoanhThu();
                dtt.setNgay(new java.util.Date());
                dtt.setSoTien(tongTien);
                dtt.setGhiChu("Thanh toán cho đơn hàng #" + maDH);
                dtt.setMaDH(maDH);
                dtt.setMaND(nd.getMaND());
                dtt.setLoaiGiaodich("SALE");
                dtdao.insert(conn, dtt);
            }

            conn.commit();
            transactionSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            if (paymentSuccessful) {
                RefundService.processRefund(nd, tongTien, "Hoàn tiền do lỗi: " + e.getMessage());
                String msg = "Rất tiếc, đã có lỗi xảy ra. Giao dịch của bạn đã được thanh toán và chúng tôi sẽ tiến hành hoàn tiền trong 24h.";
                handleErrorAndForward(request, response, msg, destination, nd, maDT);
            } else {
                String msg = e.getMessage() != null ? e.getMessage() : "Đã có lỗi xảy ra, vui lòng thử lại.";
                handleErrorAndForward(request, response, msg, destination, nd, maDT);
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (transactionSuccess) {
            sl = 0;
            if (nd != null) {
                GioHangDAO ghdao = new GioHangDAO();
                GioHang gh = ghdao.getOrCreateCart(nd.getMaND());
                ChiTietGioHangDAO ctghdao = new ChiTietGioHangDAO();
                sl = ctghdao.getSoLuongBinhThuongTrongGioHang(nd.getMaND(), gh.getMaGiohang());
            }
            session.removeAttribute("selectedItemsForCheckout");
            response.sendRedirect(request.getContextPath() + "/User/orderSuccess.jsp?maDH=" + maDH + "&cartSize=" + sl);
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
