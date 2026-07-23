<%-- 
    Document   : orderSuccess
    Created on : Oct 24, 2025, 9:40:47 AM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đặt hàng thành công - WebPhone</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <style>
            html, body {
                height: 100%;
            }
            body {
                background: #f7f9fb;
                font-family: "Segoe UI", Arial, sans-serif;
                color: #333;
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
            header .logo { height: 55px; }
            .brand-name { font-weight: 700; font-size: 22px; letter-spacing: 0.5px; }
            header a { color: #222; font-weight: 500; text-decoration: none; transition: 0.25s; }
            header a:hover { color: #0056b3; }
            
            /* ===== MAIN CONTENT ===== */
            main {
                flex: 1; /* Đảm bảo main chiếm hết không gian còn lại */
                display: flex;
                align-items: center; /* Căn giữa theo chiều dọc */
                justify-content: center; /* Căn giữa theo chiều ngang */
                padding: 40px 20px;
            }
            
            .success-container {
                background: #fff;
                padding: 50px 60px;
                border-radius: 12px;
                box-shadow: 0 5px 25px rgba(0,0,0,0.1);
                text-align: center;
                max-width: 600px;
                width: 100%;
            }

            .success-icon {
                font-size: 80px;
                color: #28a745; /* Màu xanh lá cây của success */
            }
            
            .order-code {
                background-color: #e9f5ff;
                border: 1px dashed #007bff;
                padding: 10px 20px;
                border-radius: 8px;
                font-size: 1.2rem;
                font-weight: bold;
                display: inline-block; /* Để box co lại vừa với nội dung */
            }

            /* ===== FOOTER ===== */
            footer { background: #f5f5f5; color: #333; padding: 40px 0 20px; }
            footer h6 { color: #000; }
            footer a { color: #555; }
            footer a:hover { color: #000; }
        </style>
    </head>

    <body>
        <%
            NguoiDung nd = (NguoiDung) session.getAttribute("user");
            boolean loggedIn = (nd != null && nd.getMaND() > 0);
            if (nd == null) {
                response.sendRedirect(request.getContextPath() + "/User/login.jsp");
                return;
            }

            // Lấy mã đơn hàng từ URL
            String maDH = request.getParameter("maDH");
            if (maDH == null || maDH.trim().isEmpty() || maDH.equals("0")) {
                response.sendRedirect(request.getContextPath() + "/indexKhachHang");
                return;
            }
            String cartSizeString = request.getParameter("cartSize");
            int cartSize = Integer.parseInt(cartSizeString);
        %>

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

        <!-- MAIN (Thiết kế mới) -->
        <main>
            <div class="success-container">
                <i class="fas fa-check-circle success-icon mb-3"></i>
                <h1 class="fw-bold">Đặt hàng thành công!</h1>
                <p class="lead text-muted mb-4">
                    Cảm ơn bạn đã tin tưởng và mua sắm tại WebPhone.
                </p>
                
                <p>Mã đơn hàng của bạn là:</p>
                <div class="order-code my-3">
                    #<%= maDH %>
                </div>
                
                <p class="mt-4">
                    Một email xác nhận đã được gửi đến địa chỉ <strong><%= nd.getEmail() %></strong>.
                    <br>
                    Vui lòng kiểm tra hộp thư của bạn.
                </p>
                
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center mt-5">
                    <a href="<%=request.getContextPath()%>/chiTietDonHangUs?id=<%= maDH %>" class="btn btn-primary btn-lg px-4 gap-3">Xem chi tiết đơn hàng</a>
                    <a href="<%=request.getContextPath()%>/indexKhachHang" class="btn btn-outline-secondary btn-lg px-4">Tiếp tục mua sắm</a>
                </div>
            </div>
        </main>

        <!-- FOOTER (Giữ nguyên) -->
        <footer class="mt-auto"> <!-- Thêm class mt-auto để footer luôn ở dưới -->
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
