<%-- 
    Document   : changePassword
    Created on : Oct 15, 2025, 10:26:09 AM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thông tin tài khoản - WebPhone</title>
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

            /* Form */
            .form-label {
                font-weight: 600;
            }
            .btn-custom {
                background: #ffd900;
                border: none;
                font-weight: 600;
                color: #000;
                transition: all 0.25s;
            }
            .btn-custom:hover {
                background: #ffcc00;
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
            NguoiDung nd = (NguoiDung) request.getAttribute("nguoidung");
            boolean loggedIn = (nd != null && nd.getMaND() > 0);
            if (nd == null || nd.getMaND() == 0) {
                response.sendRedirect(request.getContextPath() + "/User/login.jsp?redirect=infomationUs");
                return;
            }
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
                    <a href="<%=request.getContextPath()%>/changePasswordUs" class="active"><i class="fas fa-lock me-2"></i> Đổi mật khẩu</a>
                    <a href="<%=request.getContextPath()%>/lichSuGiaoDichUs"><i class="fas fa-shopping-cart me-2"></i> Lịch sử Giao dịch</a>
                    <a href="<%=request.getContextPath()%>/lichSuDonHangUs"><i class="fas fa-clock me-2"></i> Lịch sử đơn hàng</a>
                    <a href="<%=request.getContextPath()%>/logoutUs"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
                </div>

                <!-- CONTENT -->
                <div class="content tab-content">
                    <!-- Đổi mật khẩu -->
                    <div class="tab-pane fade show active" id="password">
                        <h4>Đổi mật khẩu</h4>
                        <form method="post" action="<%=request.getContextPath()%>/changePasswordUs">
                            <div class="mb-3">
                                <label class="form-label">Mật khẩu hiện tại</label>
                                <input type="password" name="oldPassword" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Mật khẩu mới</label>
                                <input type="password" name="newPassword" class="form-control" minlength="6" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Xác nhận mật khẩu mới</label>
                                <input type="password" name="confirmPassword" class="form-control" minlength="6" required>
                            </div>
                            <div class="text-end">
                                <button type="submit" class="btn btn-custom px-4">Đổi mật khẩu</button>
                            </div>
                        </form>
                    </div>
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
        <%
            String msg = (String) request.getAttribute("message");
            String type = (String) request.getAttribute("type");
        %>

    </body>

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
</html>

