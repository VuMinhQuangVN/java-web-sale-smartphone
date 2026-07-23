<%-- 
    Document   : lichSuDonHang
    Created on : Oct 18, 2025
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
            List<DonHang> listDonHang = (List<DonHang>) request.getAttribute("listDonHang");

            // Định dạng tiền tệ và ngày tháng
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            int cartSize = (int) request.getAttribute("cartSize");
            String currentStatus = (String) request.getAttribute("currentStatus");
            if (currentStatus == null) {
                currentStatus = "Tất cả";
            }
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
                    <a href="<%=request.getContextPath()%>/lichSuGiaoDichUs"><i class="fas fa-shopping-cart me-2"></i> Lịch sử Giao dịch</a>
                    <a href="<%=request.getContextPath()%>/lichSuDonHangUs" class="active"><i class="fas fa-clock me-2"></i> Lịch sử đơn hàng</a>
                    <a href="<%=request.getContextPath()%>/logoutUs"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
                </div>

                <!-- CONTENT -->
                <div class="content">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 class="mb-0">Lịch sử đơn hàng</h4>
                        <!-- (Tùy chọn) Thêm bộ lọc đơn hàng -->
                        <div class="btn-group">
                            <a href="<%=request.getContextPath()%>/lichSuDonHangUs?status=Tất cả" 
                               class="btn btn-outline-secondary btn-sm <%= "Tất cả".equals(currentStatus) ? "active" : ""%>">Tất cả</a>

                            <a href="<%=request.getContextPath()%>/lichSuDonHangUs?status=Chờ xác nhận" 
                               class="btn btn-outline-secondary btn-sm <%= "Chờ xác nhận".equals(currentStatus) ? "active" : ""%>">Chờ xác nhận</a>

                            <a href="<%=request.getContextPath()%>/lichSuDonHangUs?status=Đang giao" 
                               class="btn btn-outline-secondary btn-sm <%= "Đang giao".equals(currentStatus) ? "active" : ""%>">Đang giao</a>

                            <a href="<%=request.getContextPath()%>/lichSuDonHangUs?status=Đã giao" 
                               class="btn btn-outline-secondary btn-sm <%= "Đã giao".equals(currentStatus) ? "active" : ""%>">Đã giao</a>

                            <a href="<%=request.getContextPath()%>/lichSuDonHangUs?status=Hoàn tất" 
                               class="btn btn-outline-secondary btn-sm <%= "Hoàn tất".equals(currentStatus) ? "active" : ""%>">Hoàn tất</a>

                            <a href="<%=request.getContextPath()%>/lichSuDonHangUs?status=Hủy" 
                               class="btn btn-outline-secondary btn-sm <%= "Hủy".equals(currentStatus) ? "active" : ""%>">Hủy</a>
                        </div>
                    </div>

                    <% if (listDonHang == null || listDonHang.isEmpty()) {%>
                    <div class="text-center p-5 border rounded bg-light mt-4">
                        <i class="fas fa-box-open fs-1 text-muted mb-3"></i>
                        <h5 class="text-muted">Bạn chưa có đơn hàng nào.</h5>
                        <a href="<%=request.getContextPath()%>/indexKhachHang" class="btn btn-primary mt-3">Bắt đầu mua sắm</a>
                    </div>
                    <% } else {
                        boolean firstItem = true;
                        for (DonHang dh : listDonHang) {
                            if (!firstItem) {
                    %>
                    <hr class="my-4"> 
                    <%
                        }
                        firstItem = false;
                    %>
                    <div class="d-flex justify-content-between align-items-center">
                        <!-- Cột thông tin bên trái -->
                        <div>
                            <div class="mb-2">
                                <strong class="me-2">Đơn hàng: <span class="text-danger">#<%= dh.getMaDH()%></span></strong>
                                <small class="text-muted">Ngày đặt: <%= sdf.format(dh.getNgayDH())%></small>
                            </div>
                            <%
                                String statusClass = "bg-secondary";
                                if ("Đang giao".equals(dh.getTrangThai()))
                                    statusClass = "bg-primary";
                                else if ("Hoàn tất".equals(dh.getTrangThai()))
                                    statusClass = "bg-success";
                                else if ("Hủy".equals(dh.getTrangThai()))
                                    statusClass = "bg-danger";
                            %>
                            <div><span class="badge text-white <%= statusClass%>"><%= dh.getTrangThai()%></span></div>
                            <div class="mt-2">
                                <strong>Tổng tiền:</strong> 
                                <span class="fw-bold fs-5 text-danger"><%= nf.format(dh.getTongTien())%> ₫</span>
                            </div>
                            <div class="text-muted small">
                                <strong>Thanh toán:</strong> <%= dh.getPhuongThucTT()%> 
                                (
                                <%-- Sử dụng toán tử ba ngôi lồng nhau --%>
                                <%= "Đã thanh toán".equals(dh.getTrangThaiTT())
                                        ? "<span class='text-success fw-semibold'>Đã thanh toán</span>"
                                        : ("Hoàn tiền".equals(dh.getTrangThaiTT())
                                        ? "<span class='text-info fw-semibold'>Hoàn tiền</span>"
                                        : "<span class='text-warning fw-semibold'>Chưa thanh toán</span>")%>
                                )
                            </div>
                        </div>
                        <!-- Cột nút bấm bên phải -->
                        <div class="text-end">
                            <a href="<%=request.getContextPath()%>/chiTietDonHangUs?id=<%= dh.getMaDH()%>" class="btn btn-outline-primary">Xem chi tiết</a>
                            <% 
                                // LOGIC HIỂN THỊ NÚT ĐÁNH GIÁ (Trong vòng 15 ngày kể từ ngày hoàn tất)
                                if ("Hoàn tất".equals(dh.getTrangThai()) && dh.getNgayHoantat() != null) {
                                    long millisInDay = 24 * 60 * 60 * 1000; // Số mili-giây trong 1 ngày
                                    java.util.Date now = new java.util.Date(); // Ngày hiện tại
                                    java.util.Date completeDate = dh.getNgayHoantat(); // Ngày hoàn tất đơn hàng
                                    
                                    // Tính khoảng cách thời gian (mili-giây)
                                    long diffDetails = Math.abs(now.getTime() - completeDate.getTime());
                                    // Quy đổi ra ngày
                                    long diffDays = diffDetails / millisInDay;
                                    
                                    // Nếu nhỏ hơn hoặc bằng 15 ngày thì hiện nút
                                    if (diffDays <= 15) {
                            %>
                                <a href="<%=request.getContextPath()%>/chiTietDonHangUs?id=<%= dh.getMaDH()%>" class="btn btn-warning text-dark ms-2" title="Bạn còn <%= 15 - diffDays %> ngày để đánh giá">
                                    <i class="fas fa-star text-white"></i> Đánh giá
                                </a>
                            <% 
                                    } 
                                } 
                            %>
                            <%-- (Tùy chọn) Thêm nút Hủy đơn hàng nếu trạng thái là "Chờ xác nhận" --%>
                            <% if ("Chờ xác nhận".equals(dh.getTrangThai()) || "Đã giao".equals(dh.getTrangThai())) {%>
                            <form action="<%= request.getContextPath()%>/cancelOrderUs" method="POST" class="d-inline ms-2" onsubmit="return confirm('Bạn có chắc chắn muốn hủy đơn hàng này không?');">
                                <input type="hidden" name="maDH" value="<%= dh.getMaDH()%>">
                                <button type="submit" class="btn btn-outline-danger">Hủy đơn hàng</button>
                            </form>
                            <% } else { %>
                            <%-- (Tùy chọn) Hiển thị nút bị vô hiệu hóa cho các trạng thái khác --%>
                            <button class="btn btn-outline-danger disabled ms-2" title="Không thể hủy đơn hàng ở trạng thái này">Hủy đơn hàng</button>
                            <% } %>
                        </div>
                    </div>
                    <%
                            } // end for
                        } // end else
                    %>
                    <!-- =============== PHÂN TRANG =============== -->
                    <%
                        int currentPage = (request.getAttribute("currentPage") != null) ? (int) request.getAttribute("currentPage") : 1;
                        int totalPages = (request.getAttribute("totalPages") != null) ? (int) request.getAttribute("totalPages") : 1;
                    %>

                    <nav aria-label="Page navigation" class="mt-5">
                        <ul class="pagination justify-content-center">
                            <li class="page-item <%= (currentPage <= 1) ? "disabled" : ""%>">
                                <a class="page-link" href="?status=<%= currentStatus%>&page=<%= currentPage - 1%>">Trước</a>
                            </li>

                            <% for (int i = 1; i <= totalPages; i++) {%>
                            <li class="page-item <%= (i == currentPage) ? "active" : ""%>">
                                <a class="page-link" href="?status=<%= currentStatus%>&page=<%= i%>"><%= i%></a>
                            </li>
                            <% }%>

                            <li class="page-item <%= (currentPage >= totalPages) ? "disabled" : ""%>">
                                <a class="page-link" href="?status=<%= currentStatus%>&page=<%= currentPage + 1%>">Sau</a>
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
<%
    String msg = (String) request.getAttribute("message");
    String type = (String) request.getAttribute("type");
%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
                                // ========== Hiển thị thông báo SweetAlert ==========
    <% if (msg != null) {%>
                                Swal.fire({
                                    icon: '<%= "success".equals(type) ? "success" : "error"%>',
                                    title: '<%= msg%>',
                                    showConfirmButton: false,
                                    timer: 2000,
                                    timerProgressBar: true,
                                    toast: true,
                                    position: 'top-end'
                                });
    <% }%>
</script>
