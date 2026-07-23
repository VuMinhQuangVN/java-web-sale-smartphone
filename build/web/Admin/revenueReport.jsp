<%-- 
    Document   : revenueReport
    Created on : Nov 21, 2025, 7:40:59 AM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="Models.DoanhThu"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Báo cáo doanh thu</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <link href="<%= request.getContextPath()%>/Admin/adminStyles.css" rel="stylesheet">
    </head>
    <body>
        <%
            NguoiDung nguoidung = (NguoiDung) request.getAttribute("nguoidung");

            if (nguoidung == null || nguoidung.getQuyen().equals("user")) {
                response.sendRedirect(request.getContextPath() + "/User/login.jsp");
                return;
            }
            // 1. Lấy dữ liệu từ Servlet truyền sang
            List<DoanhThu> listDT = (List<DoanhThu>) request.getAttribute("listDT");
            Double tongTien = (Double) request.getAttribute("tongTien");
            if (tongTien == null) {
                tongTien = 0.0;
            }

            // Lấy các tham số đã chọn để giữ lại trạng thái (selected)
            String selectedYear = (String) request.getAttribute("selectedYear");
            String selectedMonth = (String) request.getAttribute("selectedMonth");
            String selectedQuarter = (String) request.getAttribute("selectedQuarter");

            // Xử lý null để tránh lỗi khi so sánh chuỗi
            if (selectedYear == null) {
                selectedYear = "";
            }
            if (selectedMonth == null) {
                selectedMonth = "";
            }
            if (selectedQuarter == null) {
                selectedQuarter = "";
            }

            // Tạo đối tượng format tiền tệ Việt Nam
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        %>


        <!-- HEADER -->
        <header class="headercss">
            <section class="logo-section d-flex align-items-center">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" alt="Logo">
                <span class="brand-name">WebPhone Admin</span>
            </section>

            <section class="admin-name text-end">
                Xin chào, <%= nguoidung.getTenND()%>
            </section>
        </header>


        <!-- LAYOUT -->
        <div class="admin-container">
            <!-- SIDEBAR -->
            <aside class="sidebar">
                <h2>📊 Admin Panel</h2>
                <ul>
                    <li><a href="<%= request.getContextPath()%>/DasboardAd"><i class="fas fa-chart-line"></i> Dashboard</a></li>
                    <li><a href="<%= request.getContextPath()%>/managerProductAd"><i class="fas fa-mobile-alt"></i> Quản lý sản phẩm</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageCategoryAd"><i class="fas fa-list"></i> Quản lý loại</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageOrderAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd" class="active"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <!-- CONTENT -->
            <main class="content">
                <div class="container mt-4">
                    <h2 class="text-center mb-4">Chi tiết Doanh Thu & Hoàn Tiền</h2>

                    <!-- FORM LỌC -->
                    <form action="DoanhThuAd" method="GET" class="row g-3 mb-4 bg-light p-3 rounded shadow-sm">
                        <div class="col-md-3">
                            <label>Năm:</label>
                            <select name="year" class="form-select">
                                <%
                                    int currentYear = java.time.Year.now().getValue();
                                    for (int y = 2023; y <= 2030; y++) {
                                        String isSelected = String.valueOf(y).equals(selectedYear) ? "selected" : "";
                                %>
                                <option value="<%= y%>" <%= isSelected%>><%= y%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>

                        <div class="col-md-3">
                            <label>Quý (Chọn 0 nếu tìm theo tháng):</label>
                            <select name="quarter" class="form-select">
                                <option value="0">-- Tất cả --</option>
                                <option value="1" <%= "1".equals(selectedQuarter) ? "selected" : ""%>>Quý 1</option>
                                <option value="2" <%= "2".equals(selectedQuarter) ? "selected" : ""%>>Quý 2</option>
                                <option value="3" <%= "3".equals(selectedQuarter) ? "selected" : ""%>>Quý 3</option>
                                <option value="4" <%= "4".equals(selectedQuarter) ? "selected" : ""%>>Quý 4</option>
                            </select>
                        </div>

                        <div class="col-md-3">
                            <label>Tháng (Ưu tiên hơn Quý):</label>
                            <select name="month" class="form-select">
                                <option value="0">-- Tất cả --</option>
                                <%
                                    for (int m = 1; m <= 12; m++) {
                                        String isSelected = String.valueOf(m).equals(selectedMonth) ? "selected" : "";
                                %>
                                <option value="<%= m%>" <%= isSelected%>>Tháng <%= m%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>

                        <div class="col-md-3 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100"><i class="fas fa-filter"></i> Xem dữ liệu</button>
                        </div>
                    </form>

                    <!-- NÚT XUẤT EXCEL -->
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <h4>Tổng kết: <span class="text-danger"><%= nf.format(tongTien)%> ₫</span></h4>
                        <a href="ExportExcelControl?type=revenue&year=<%= selectedYear%>&month=<%= selectedMonth%>&quarter=<%= selectedQuarter%>" 
                           class="btn btn-success">
                            <i class="fas fa-file-excel"></i> Xuất file Excel
                        </a>
                    </div>

                    <!-- BẢNG DỮ LIỆU -->
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Mã GD</th>
                                <th>Ngày</th>
                                <th>Loại GD</th>
                                <th>Mã ĐH</th>
                                <th>Ghi chú</th>
                                <th>Số tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (listDT != null && !listDT.isEmpty()) {
                                    for (DoanhThu o : listDT) {
                                        // Xử lý style cho badge
                                        String badgeClass = "REFUND".equals(o.getLoaiGiaodich()) ? "bg-danger" : "bg-success";
                                        // Xử lý màu text tiền
                                        String textClass = o.getSoTien() < 0 ? "text-danger" : "text-primary";
                                        // Xử lý hiển thị mã đơn hàng (nếu = 0 thì hiện gạch ngang)
                                        String hienThiMaDH = (o.getMaDH() == 0) ? "-" : String.valueOf(o.getMaDH());
                            %>
                            <tr>
                                <td><%= o.getMaDTu()%></td>
                                <td><%= o.getNgay()%></td> <!-- Bạn có thể format ngày tháng ở đây nếu cần -->
                                <td>
                                    <span class="badge <%= badgeClass%>">
                                        <%= o.getLoaiGiaodich()%>
                                    </span>
                                </td>
                                <td><%= hienThiMaDH%></td>
                                <td><%= o.getGhiChu()%></td>
                                <td class="text-end fw-bold <%= textClass%>">
                                    <%= nf.format(o.getSoTien())%>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="6" class="text-center">Không có dữ liệu nào phù hợp.</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
                <%  
                    int totalPages = (int) request.getAttribute("totalPages");
                    int currentPage = (int) request.getAttribute("currentPage");
                %>
                <nav>
                    <ul class="pagination justify-content-center">
                        <%
                            for (int i = 1; i <= totalPages; i++) {
                                String active = (i == currentPage) ? "active" : "";
                        %>
                        <li class="page-item <%= active%>">
                            <a class="page-link"
                               href="DoanhThuAd?page=<%= i%>&year=<%= selectedYear%>&month=<%= selectedMonth%>&quarter=<%= selectedQuarter%>">
                                <%= i%>
                            </a>
                        </li>
                        <% }%>
                    </ul>
                </nav>
            </main>
        </div>

        <!-- FOOTER -->
        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
    </body>
</html>