/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ExportPdfControl", urlPatterns = {"/ExportPdfControl"})
public class ExportPdfControl extends HttpServlet {

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
            out.println("<title>Servlet ExportPdfControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportPdfControl at " + request.getContextPath() + "</h1>");
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

        if ("pdf_report".equals(type)) { try {
            // Gọi hàm này
            exportMonthlyReportPDF(request, response);
            } catch (DocumentException ex) {
                System.getLogger(ExportPdfControl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } else {
            response.getWriter().println("Tham số không hợp lệ hoặc thiếu type!");
        }
    }

    private void exportMonthlyReportPDF(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        // 1. Lấy tháng năm từ tham số (nếu null thì lấy hiện tại)
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");

        int month = (monthStr == null || monthStr.isEmpty()) ? LocalDate.now().getMonthValue() : Integer.parseInt(monthStr);
        int year = (yearStr == null || yearStr.isEmpty()) ? LocalDate.now().getYear() : Integer.parseInt(yearStr);

        // 2. Lấy dữ liệu từ Database
        Models.ThongKeDAO dao = new Models.ThongKeDAO();
        Map<String, Object> tongQuan = dao.getTongQuanThang(month, year);
        List<Map<String, Object>> topSP = dao.getTopSanPhamTheoThang(month, year);
        List<Map<String, Object>> topKH = dao.getTopKhachHangTheoThang(month, year);

        // 3. Thiết lập Header phản hồi
        response.setContentType("application/pdf");
        String fileName = "BaoCao_Thang" + month + "_" + year + "_" + System.currentTimeMillis() + ".pdf";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try {
            // 4. Khởi tạo Document khổ A4, lề 50
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // --- CẤU HÌNH FONT TIẾNG VIỆT (QUAN TRỌNG) ---
            // Lấy đường dẫn thực tế tới file arial.ttf trong folder 'fonts'
            String fontPath = getServletContext().getRealPath("/fonts/arial.ttf");

            // Kiểm tra xem file có tồn tại không để tránh lỗi crash
            if (fontPath == null || !new File(fontPath).exists()) {
                document.add(new Paragraph("LỖI: Không tìm thấy file font tại /fonts/arial.ttf"));
                document.close();
                return;
            }

            // Tạo BaseFont hỗ trợ tiếng Việt (Identity-H)
            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // Định nghĩa các kiểu chữ
            Font fontTitle = new Font(bf, 18, Font.BOLD, BaseColor.BLUE);
            Font fontHeader = new Font(bf, 11, Font.BOLD, BaseColor.WHITE); // Chữ trắng cho header bảng
            Font fontBoldBlack = new Font(bf, 11, Font.BOLD, BaseColor.BLACK);
            Font fontNormal = new Font(bf, 11, Font.NORMAL, BaseColor.BLACK);
            Font fontItalic = new Font(bf, 10, Font.ITALIC, BaseColor.GRAY);

            // --- BẮT ĐẦU VẼ NỘI DUNG ---
            // 1. Tiêu đề
            Paragraph title = new Paragraph("BÁO CÁO KẾT QUẢ KINH DOANH", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subTitle = new Paragraph("Tháng " + month + " năm " + year, fontBoldBlack);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            subTitle.setSpacingAfter(10);
            document.add(subTitle);

            Paragraph dateCreate = new Paragraph("Ngày xuất: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), fontItalic);
            dateCreate.setAlignment(Element.ALIGN_CENTER);
            dateCreate.setSpacingAfter(20);
            document.add(dateCreate);

            // 2. Phần Tổng quan (Dùng bảng không viền)
            document.add(new Paragraph("I. TỔNG QUAN TÀI CHÍNH", fontBoldBlack));
            document.add(Chunk.NEWLINE);

            PdfPTable tableOverview = new PdfPTable(2);
            tableOverview.setWidthPercentage(100);
            tableOverview.setSpacingAfter(20);

            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

            addTextRow(tableOverview, "Tổng doanh thu bán hàng:", nf.format(tongQuan.get("tongDoanhThu")) + " đ", fontNormal, fontBoldBlack);
            addTextRow(tableOverview, "Tổng tiền hoàn lại (Hủy đơn):", nf.format(tongQuan.get("tongHoanTien")) + " đ", fontNormal, fontBoldBlack);
            addTextRow(tableOverview, "Số đơn hàng thành công:", tongQuan.get("soDonThanhCong") + " đơn", fontNormal, fontBoldBlack);

            document.add(tableOverview);

            // 3. Phần Top Sản phẩm
            document.add(new Paragraph("II. TOP 5 SẢN PHẨM BÁN CHẠY", fontBoldBlack));
            document.add(Chunk.NEWLINE);

            // Bảng 3 cột: STT (1 phần), Tên (4 phần), Số lượng (2 phần)
            PdfPTable tableProduct = new PdfPTable(new float[]{1, 4, 2});
            tableProduct.setWidthPercentage(100);
            tableProduct.setSpacingAfter(20);

            addTableHeader(tableProduct, fontHeader, "STT", "Tên Sản Phẩm", "Số Lượng Bán");

            int stt = 1;
            for (Map<String, Object> sp : topSP) {
                tableProduct.addCell(createCell(String.valueOf(stt++), fontNormal, Element.ALIGN_CENTER));
                tableProduct.addCell(createCell((String) sp.get("tenDT"), fontNormal, Element.ALIGN_LEFT));
                tableProduct.addCell(createCell(String.valueOf(sp.get("slBan")), fontNormal, Element.ALIGN_CENTER));
            }

            if (topSP.isEmpty()) {
                PdfPCell cellEmpty = new PdfPCell(new Phrase("Chưa có dữ liệu bán hàng", fontItalic));
                cellEmpty.setColspan(3);
                cellEmpty.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellEmpty.setPadding(10);
                tableProduct.addCell(cellEmpty);
            }

            document.add(tableProduct);

            // 4. Phần Top Khách hàng
            document.add(new Paragraph("III. TOP 5 KHÁCH HÀNG VIP", fontBoldBlack));
            document.add(Chunk.NEWLINE);

            // Bảng 4 cột
            PdfPTable tableCustomer = new PdfPTable(new float[]{1, 3, 2, 3});
            tableCustomer.setWidthPercentage(100);

            addTableHeader(tableCustomer, fontHeader, "STT", "Tên Khách Hàng", "Số Đơn", "Tổng Chi Tiêu");

            stt = 1;
            for (Map<String, Object> kh : topKH) {
                tableCustomer.addCell(createCell(String.valueOf(stt++), fontNormal, Element.ALIGN_CENTER));
                tableCustomer.addCell(createCell((String) kh.get("tenKH"), fontNormal, Element.ALIGN_LEFT));
                tableCustomer.addCell(createCell(String.valueOf(kh.get("soDon")), fontNormal, Element.ALIGN_CENTER));
                tableCustomer.addCell(createCell(nf.format(kh.get("tongChi")) + " đ", fontNormal, Element.ALIGN_RIGHT));
            }

            if (topKH.isEmpty()) {
                PdfPCell cellEmpty = new PdfPCell(new Phrase("Chưa có dữ liệu khách hàng", fontItalic));
                cellEmpty.setColspan(4);
                cellEmpty.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellEmpty.setPadding(10);
                tableCustomer.addCell(cellEmpty);
            }

            document.add(tableCustomer);

            // 5. Footer (Chữ ký)
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            PdfPTable footerTable = new PdfPTable(2);
            footerTable.setWidthPercentage(100);

            PdfPCell cellSignLeft = new PdfPCell(new Phrase("Người lập biểu", fontNormal));
            cellSignLeft.setBorder(Rectangle.NO_BORDER);
            cellSignLeft.setHorizontalAlignment(Element.ALIGN_CENTER);
            footerTable.addCell(cellSignLeft);

            PdfPCell cellSignRight = new PdfPCell(new Phrase("Giám đốc xác nhận", fontNormal));
            cellSignRight.setBorder(Rectangle.NO_BORDER);
            cellSignRight.setHorizontalAlignment(Element.ALIGN_CENTER);
            footerTable.addCell(cellSignRight);

            // Khoảng trống để ký
            PdfPCell cellSpace = new PdfPCell(new Phrase("\n\n\n", fontNormal));
            cellSpace.setBorder(Rectangle.NO_BORDER);
            footerTable.addCell(cellSpace);
            footerTable.addCell(cellSpace);

            document.add(footerTable);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    // --- CÁC HÀM HỖ TRỢ (Helper Methods) ---
    // 1. Hàm thêm dòng text 2 cột (Label : Value) cho phần tổng quan
    private void addTextRow(PdfPTable table, String label, String value, Font fontLabel, Font fontValue) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, fontLabel));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setPaddingBottom(6);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(value, fontValue));
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setPaddingBottom(6);
        table.addCell(cell2);
    }

    // 2. Hàm tạo Header cho bảng (Nền tối, chữ trắng)
    private void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.DARK_GRAY); // Màu nền header
            cell.setPaddingTop(5);
            cell.setPaddingBottom(7);
            table.addCell(cell);
        }
    }

    // 3. Hàm tạo ô dữ liệu bình thường
    private PdfPCell createCell(String content, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6); // Padding cho thoáng
        return cell;
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
