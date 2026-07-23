/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.DoanhThu;
import Models.DoanhThuDAO;
import Models.DonHangDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ExportExcelControl", urlPatterns = {"/ExportExcelControl"})
public class ExportExcelControl extends HttpServlet {

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
            out.println("<title>Servlet ExportExcelControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportExcelControl at " + request.getContextPath() + "</h1>");
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
        String type = request.getParameter("type");

        if ("revenue".equals(type)) {
            exportRevenuee(request, response);
        } else if ("orders".equals(type)) {
            exportOrders(request, response);
        } else if ("inventory".equals(type)) {
            exportInventory(request, response); // <--- Thêm dòng này
        } else {
            response.getWriter().println("Tham số không hợp lệ hoặc thiếu type!");
        }
    }

    private void exportRevenuee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Lấy tham số lọc (giống hệt bên DoanhThuAd)
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String quarter = request.getParameter("quarter");

        DoanhThuDAO dao = new DoanhThuDAO();
        List<DoanhThu> list = dao.getDoanhThuByFilter(year, month, quarter);

        // 2. Tạo file Excel
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Doanh Thu");

            // Tạo Header
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Mã GD", "Ngày Giao Dịch", "Loại GD", "Mã Đơn Hàng", "Khách Hàng (ID)", "Ghi Chú", "Số Tiền"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // 3. Đổ dữ liệu
            int rowNum = 1;
            double totalAmount = 0;

            // Format ngày tháng và số tiền trong Excel
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/mm/yyyy hh:mm:ss"));

            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));

            for (DoanhThu dt : list) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(dt.getMaDTu());

                Cell dateCell = row.createCell(1);
                dateCell.setCellValue(dt.getNgay());
                dateCell.setCellStyle(dateStyle);

                row.createCell(2).setCellValue(dt.getLoaiGiaodich());

                if (dt.getMaDH() != 0) {
                    row.createCell(3).setCellValue(dt.getMaDH());
                } else {
                    row.createCell(3).setCellValue("-");
                }

                row.createCell(4).setCellValue(dt.getMaND());
                row.createCell(5).setCellValue(dt.getGhiChu());

                Cell moneyCell = row.createCell(6);
                moneyCell.setCellValue(dt.getSoTien());
                moneyCell.setCellStyle(currencyStyle);

                totalAmount += dt.getSoTien();
            }

            // Dòng tổng cộng
            Row totalRow = sheet.createRow(rowNum);
            totalRow.createCell(5).setCellValue("TỔNG CỘNG:");
            Cell totalCell = totalRow.createCell(6);
            totalCell.setCellValue(totalAmount);
            totalCell.setCellStyle(currencyStyle);

            // Tự động giãn cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 4. Xuất file ra trình duyệt
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=BaoCaoDoanhThu_" + System.currentTimeMillis() + ".xlsx");

            workbook.write(response.getOutputStream());
        }
    }

    private void exportOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Lấy tham số lọc từ URL
        String keyword = request.getParameter("keyword");
        String trangThai = request.getParameter("trangThai");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        // 2. Gọi DAO lấy dữ liệu
        DonHangDAO dao = new DonHangDAO();
        List<Map<String, Object>> list = dao.getDonHangForExport(keyword, trangThai, fromDate, toDate);

        // 3. Tạo file Excel với Apache POI
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh Sách Đơn Hàng");

            // --- Header ---
            Row headerRow = sheet.createRow(0);
            String[] columns = {
                "Mã ĐH", "Khách Hàng", "SĐT", "Ngày Đặt",
                "Địa Chỉ Giao", "PTTT", "TT Thanh Toán",
                "Trạng Thái ĐH", "Ghi Chú", "Tổng Tiền"
            };

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); // Màu nền vàng cho header
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- Data ---
            int rowNum = 1;

            // Format Ngày tháng
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/mm/yyyy HH:mm"));

            // Format Tiền tệ
            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0 ₫"));

            for (Map<String, Object> map : list) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue((Integer) map.get("maDH"));
                row.createCell(1).setCellValue((String) map.get("tenND"));
                row.createCell(2).setCellValue((String) map.get("sdt"));

                Cell dateCell = row.createCell(3);
                dateCell.setCellValue((java.util.Date) map.get("ngayDH"));
                dateCell.setCellStyle(dateStyle);

                row.createCell(4).setCellValue((String) map.get("diaChigiao"));
                row.createCell(5).setCellValue((String) map.get("phuongThucTT"));
                row.createCell(6).setCellValue((String) map.get("trangThaiTT"));
                row.createCell(7).setCellValue((String) map.get("trangThai"));

                String ghiChu = (String) map.get("ghiChu");
                row.createCell(8).setCellValue(ghiChu == null ? "" : ghiChu);

                Cell moneyCell = row.createCell(9);
                moneyCell.setCellValue((Double) map.get("tongTien"));
                moneyCell.setCellStyle(currencyStyle);
            }

            // Auto resize cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 4. Thiết lập Header HTTP để tải về
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // Tạo tên file động theo thời gian
            String fileName = "DonHang_" + System.currentTimeMillis() + ".xlsx";
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // Ghi ra luồng output
            workbook.write(response.getOutputStream());
        }
    }

    private void exportInventory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword"); // Lấy từ khóa tìm kiếm nếu có

        Models.DienThoaiDAO dao = new Models.DienThoaiDAO();
        List<java.util.Map<String, Object>> list = dao.getBaoCaoTonKho(keyword);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Báo Cáo Tồn Kho");

            // --- HEADER ---
            Row headerRow = sheet.createRow(0);
            String[] columns = {"STT", "Tên Điện Thoại", "Hãng SX", "Biến Thể (RAM-ROM-Màu)", "Giá Bán", "Tồn Kho"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Màu xanh lá nhạt cho kho
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- DATA ---
            int rowNum = 1;
            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0 ₫"));

            // Style cảnh báo hàng sắp hết (Chữ đỏ đậm)
            CellStyle lowStockStyle = workbook.createCellStyle();
            Font lowStockFont = workbook.createFont();
            lowStockFont.setColor(IndexedColors.RED.getIndex());
            lowStockFont.setBold(true);
            lowStockStyle.setFont(lowStockFont);

            int stt = 1;
            for (java.util.Map<String, Object> map : list) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(stt++);
                row.createCell(1).setCellValue((String) map.get("tenDT"));
                row.createCell(2).setCellValue((String) map.get("hangSx"));
                row.createCell(3).setCellValue((String) map.get("bienThe"));

                Cell priceCell = row.createCell(4);
                priceCell.setCellValue((Double) map.get("gia"));
                priceCell.setCellStyle(currencyStyle);

                // Xử lý cột Tồn kho
                int soLuong = (Integer) map.get("soLuong");
                Cell stockCell = row.createCell(5);
                stockCell.setCellValue(soLuong);

                // Nếu tồn kho < 10 thì bôi đỏ để cảnh báo nhập hàng
                if (soLuong < 10) {
                    stockCell.setCellStyle(lowStockStyle);
                }
            }

            // Auto size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Xuất file
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=TonKho_" + System.currentTimeMillis() + ".xlsx");
            workbook.write(response.getOutputStream());
        }
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
