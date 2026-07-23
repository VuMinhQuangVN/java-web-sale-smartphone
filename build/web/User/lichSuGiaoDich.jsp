<%-- 
    Document   : lichSuGiaoDich
    Created on : Oct 24, 2025, 12:44:59 PM
    Author     : ACER
--%>


<%@page import="Models.*, java.util.*, java.text.NumberFormat, java.util.Locale, java.text.SimpleDateFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch sử đơn hàng - WebPhone</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <style>
            body {
                background: #f7f9fb;
                font-family: "Segoe UI", Arial, sans-serif;
                color: #333;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            /* ===== HEADER ===== */
            header {
                background: #ffd900;
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 12px 32px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                position: sticky;
                top: 0;
                z-index: 100;
            }
            header .logo {
                height: 55px;
            }
            .brand-name {
                font-weight: 700;
                font-size: 22px;
                letter-spacing: 0.5px;
            }
            header a {
                color: #222;
                font-weight: 500;
                text-decoration: none;
                transition: 0.25s;
            }
            header a:hover {
                color: #0056b3;
            }

            /* ===== MAIN ===== */
            main {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                padding: 40px 20px;
            }
            .account-layout {
                display: flex;
                width: 100%;
                max-width: 1100px;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            /* Sidebar trái */
            .sidebar {
                width: 250px;
                background: #f0f0f0;
                border-right: 1px solid #e0e0e0;
                padding: 25px 15px;
            }
            .sidebar h5 {
                font-weight: 700;
                margin-bottom: 20px;
                text-align: center;
            }
            .sidebar a {
                display: block;
                color: #333;
                text-decoration: none;
                padding: 10px 15px;
                border-radius: 8px;
                margin-bottom: 8px;
                transition: all 0.25s;
            }
            .sidebar a:hover,
            .sidebar a.active {
                background: #ffd900;
                color: #000;
                font-weight: 600;
            }

            /* Nội dung phải */
            .content {
                flex: 1;
                padding: 30px 40px;
            }
            .content h4 {
                font-weight: 700;
                margin-bottom: 25px;
                color: #222;
            }

            /* Custom Accordion Style */
            .accordion-button {
                font-weight: 600;
            }
            .accordion-button:not(.collapsed) {
                background-color: #fff9e6;
                box-shadow: none;
            }
            .order-details-table img {
                width: 60px;
                height: 60px;
                object-fit: contain;
            }

            .transaction-table th {
                background-color: #f8f9fa;
                font-weight: 600;
            }
            .amount-positive {
                color: #198754 !important;
                font-weight: 600;
            }
            .amount-negative {
                color: #dc3545 !important;
                font-weight: 600;
            }

            .transaction-table .col-amount {
                white-space: nowrap; /* Ngăn không cho nội dung xuống dòng */
                width: 1%; /* Mẹo để cột co lại vừa với nội dung nhưng vẫn ưu tiên không xuống dòng */
                min-width: 150px; /* Đặt độ rộng tối thiểu để đảm bảo đủ chỗ */
            }

            /* ===== FOOTER ===== */
            footer {
                background: #f5f5f5;
                color: #333;
                padding: 40px 0 20px;
            }
            footer h6 {
                color: #000;
            }
            footer a {
                color: #555;
            }
            footer a:hover {
                color: #000;
            }
        </style>
    </head>

    <body>
        <%
            // Lấy dữ liệu đã được servlet chuẩn bị
            NguoiDung nd = (NguoiDung) session.getAttribute("user");
            boolean loggedIn = (nd != null && nd.getMaND() > 0);
            if (nd == null) {
                response.sendRedirect(request.getContextPath() + "/User/login.jsp?redirect=lichSuDonHang");
                return;
            }
            List<DoanhThu> listGiaoDich = (List<DoanhThu>) request.getAttribute("listGiaoDich");

            // Định dạng tiền tệ và ngày tháng
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            int cartSize = (int) request.getAttribute("cartSize");
        %>

        <!-- HEADER -->
        <header>
            <div class="d-flex align-items-center gap-2">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" class="logo">
                <span class="brand-name">WEBPHONE</span>
            </div>
            <nav class="d-flex align-items-center gap-4">

                <!-- 1. Icon Trang chủ -->
                <a href="<%= request.getContextPath()%>/indexKhachHang" class="nav-icon-link" title="Trang chủ">
                    <i class="fas fa-home"></i>
                </a>

                <!-- 2. Icon Giỏ hàng với Badge số lượng -->
                <a href="<%= request.getContextPath()%>/gioHang" class="nav-icon-link position-relative" title="Giỏ hàng">
                    <i class="fas fa-shopping-cart"></i>
                    <%-- Chỉ hiển thị badge nếu trong giỏ có hàng (cartSize > 0) --%>
                    <% if (cartSize > 0) {%>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                        <%= cartSize%>
                        <span class="visually-hidden">sản phẩm trong giỏ hàng</span>
                    </span>
                    <% } %>
                </a>

                <!-- 3. Thông tin người dùng hoặc Nút Đăng nhập -->
                <% if (loggedIn) {%>
                <div class="user-profile-link d-flex align-items-center">
                    <a href="<%= request.getContextPath()%>/infomationUs" class="d-flex align-items-center gap-2 text-decoration-none">
                        <i class="fas fa-user-circle"></i>
                        <span><%= nd.getTenND()%></span>
                    </a>
                    <%-- Thông báo cập nhật thông tin vẫn giữ nguyên --%>
                    <% if (nd.getSdt() == null || nd.getDiachi() == null || nd.getSdt().trim().equals("") || nd.getDiachi().trim().equals("")) { %>
                    <span class="badge bg-warning text-dark ms-1" style="font-size: 0.7em;">!</span>
                    <% }%>
                </div>
                <% } else {%>
                <a href="<%= request.getContextPath()%>/loginUs?redirect=indexKhachHang" class="btn btn-dark btn-sm">Đăng nhập</a>
                <% }%>
            </nav>
        </header>

        <!-- MAIN -->
        <main>
            <div class="account-layout">
                <!-- SIDEBAR -->
                <div class="sidebar">
                    <h5><i class="fas fa-user-circle me-2 text-primary"></i>Tài khoản</h5>
                    <a href="<%=request.getContextPath()%>/infomationUs"><i class="fas fa-id-card me-2"></i> Thông tin cá nhân</a>
                    <a href="<%=request.getContextPath()%>/changePasswordUs"><i class="fas fa-lock me-2"></i> Đổi mật khẩu</a>
                    <a href="<%=request.getContextPath()%>/lichSuGiaoDichUs" class="active"><i class="fas fa-shopping-cart me-2"></i> Lịch sử Giao dịch</a>
                    <a href="<%=request.getContextPath()%>/lichSuDonHangUs"><i class="fas fa-clock me-2"></i> Lịch sử đơn hàng</a>
                    <a href="<%=request.getContextPath()%>/logoutUs"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
                </div>

                <!-- CONTENT -->
                <div class="content">
                    <h4>Lịch sử Giao dịch</h4>

                    <% if (listGiaoDich == null || listGiaoDich.isEmpty()) { %>
                    <div class="text-center p-5 border rounded bg-light">
                        <i class="fas fa-receipt fs-1 text-muted mb-3"></i>
                        <h5 class="text-muted">Bạn chưa có giao dịch nào.</h5>
                    </div>
                    <% } else { %>
                    <table class="table table-hover align-middle transaction-table">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Ngày Giao Dịch</th>
                                <th scope="col">Loại Giao Dịch</th>
                                <th scope="col">Số Tiền</th>
                                <th scope="col">Ghi Chú</th>
                                <th scope="col">Đơn Hàng Liên Quan</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (DoanhThu gd : listGiaoDich) {
                            %>
                            <tr>
                                <th scope="row"><%= gd.getMaDTu()%></th>
                                <td><%= sdf.format(gd.getNgay())%></td>
                                <td>
                                    <% if ("SALE".equals(gd.getLoaiGiaodich())) { %>
                                    <span class="badge bg-success">Thanh toán</span>
                                    <% } else if ("REFUND".equals(gd.getLoaiGiaodich())) { %>
                                    <span class="badge bg-danger">Hoàn tiền</span>
                                    <% } else {%>
                                    <span class="badge bg-secondary"><%= gd.getLoaiGiaodich()%></span>
                                    <% }%>
                                </td>
                                <%
                                    // Khai báo các biến để hiển thị
                                    String cssClass = "";
                                    String prefix = "";
                                    double displayAmount = Math.abs(gd.getSoTien());

                                    // Logic đảo ngược
                                    if (gd.getSoTien() >= 0) {
                                        cssClass = "amount-negative";
                                        prefix = "-";
                                    } else {
                                        cssClass = "amount-positive";
                                        prefix = "+";
                                    }
                                %>
                                <td class="<%= cssClass%> col-amount">
                                    <%= prefix%><%= nf.format(displayAmount)%> ₫
                                </td>
                                <td class="text-muted small"><%= gd.getGhiChu()%></td>
                                <td>
                                    <% if (gd.getMaDH() != null && gd.getMaDH() > 0) {%>
                                    <a href="<%=request.getContextPath()%>/chiTietDonHangUs?id=<%= gd.getMaDH()%>">#<%= gd.getMaDH()%></a>
                                    <% } else { %>
                                    <span class="text-muted">N/A</span>
                                    <% } %>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                    <% }%>
                    <!-- =============== PHÂN TRANG =============== -->
                    <%
                        int currentPage = (request.getAttribute("currentPage") != null) ? (int) request.getAttribute("currentPage") : 1;
                        int totalPages = (request.getAttribute("totalPages") != null) ? (int) request.getAttribute("totalPages") : 1;
                    %>

                    <nav aria-label="Page navigation" class="mt-5">
                        <ul class="pagination justify-content-center">
                            <li class="page-item <%= (currentPage <= 1) ? "disabled" : ""%>">
                                <a class="page-link" href="?page=<%= currentPage - 1%>">Trước</a>
                            </li>

                            <% for (int i = 1; i <= totalPages; i++) {%>
                            <li class="page-item <%= (i == currentPage) ? "active" : ""%>">
                                <a class="page-link" href="?page=<%= i%>"><%= i%></a>
                            </li>
                            <% }%>

                            <li class="page-item <%= (currentPage >= totalPages) ? "disabled" : ""%>">
                                <a class="page-link" href="?page=<%= currentPage + 1%>">Sau</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </main>

        <!-- FOOTER -->
        <footer>
            <div class="container text-center">
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <h6>Về chúng tôi</h6>
                        <p>WebPhone cung cấp điện thoại & phụ kiện chính hãng với giá tốt nhất.</p>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Hỗ trợ</h6>
                        <a href="#">Liên hệ</a><br>
                        <a href="#">Chính sách đổi trả</a><br>
                        <a href="#">Hướng dẫn mua hàng</a>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Chính sách</h6>
                        <a href="#">Bảo hành</a><br>
                        <a href="#">Bảo mật</a><br>
                        <a href="#">Vận chuyển</a>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Kết nối</h6>
                        <a href="#">Facebook</a> |
                        <a href="#">Zalo</a> |
                        <a href="#">Instagram</a>
                    </div>
                </div>
                <hr class="border-secondary">
                <p class="mb-0 pb-2">© 2025 WebPhone. All rights reserved.</p>
            </div>
        </footer>
    </body>
</html>
