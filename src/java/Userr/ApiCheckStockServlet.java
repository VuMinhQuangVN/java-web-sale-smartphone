/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Userr;

import Models.BienTheDienThoaiDAO;
import Models.StockCheckRequestItem;
import Models.UnavailableItemInfo;
import Models.ViewModelDtBtdt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ApiCheckStockServlet", urlPatterns = {"/api/check-stock"})
public class ApiCheckStockServlet extends HttpServlet {

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
            out.println("<title>Servlet ApiCheckStockServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApiCheckStockServlet at " + request.getContextPath() + "</h1>");
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
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            // --- 1. Đọc và Parse JSON từ request ---
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            // Định nghĩa kiểu dữ liệu cho Gson để biết cách parse mảng JSON
            Type listType = new TypeToken<ArrayList<StockCheckRequestItem>>() {
            }.getType();
            List<StockCheckRequestItem> requestedItems = gson.fromJson(sb.toString(), listType);

            // --- 2. Chuẩn bị dữ liệu để phản hồi ---
            List<UnavailableItemInfo> unavailableItems = new ArrayList<>();
            boolean allItemsAvailable = true;
            ViewModelDtBtdt vm = null;

            BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
            for (StockCheckRequestItem item : requestedItems) {
                vm = new ViewModelDtBtdt();
                vm = btdao.getVMById(item.getMaBienThe());
                if (vm != null) {
                    if (vm.getSoLuong() < item.getSoLuong()) {
                        allItemsAvailable = false;
                        String thongTinBT = String.format("%s | %s / %s", vm.getMauSac(), vm.getRAM(), vm.getROM());
                        String lyDo = (vm.getSoLuong() == 0) ? "Sản phẩm đã hết hàng" : "Không đủ số lượng (chỉ còn " + vm.getSoLuong() + ")";

                        unavailableItems.add(new UnavailableItemInfo(item.getMaBienThe(), vm.getTenDT(), thongTinBT, lyDo));
                    }
                } else {
                    allItemsAvailable = false;
                    // Bạn cần một cách để lấy Tên sản phẩm nếu biến thể đã bị xóa, hoặc trả về thông tin chung
                    unavailableItems.add(new UnavailableItemInfo(item.getMaBienThe(), "Sản phẩm không xác định", "", "Biến thể này không còn tồn tại"));
                }
            }

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("allAvailable", allItemsAvailable);
            responseData.put("unavailableItems", unavailableItems);

            String jsonResponse = gson.toJson(responseData);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại lỗi đầy đủ vào log của server để debug

            // Gửi mã lỗi 500 (Internal Server Error) về cho client
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // Set content type là JSON để client biết cách đọc
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Gửi về một đối tượng JSON chứa thông báo lỗi
            response.getWriter().write("{\"error\":\"Lỗi máy chủ khi kiểm tra kho hàng. Vui lòng thử lại sau.\"}");
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
