<%-- 
    Document   : forgotpassword
    Created on : Nov 21, 2025
    Updated UI : Based on Login template
--%>

<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    // Lấy thông báo lỗi từ Servlet (nếu có)
    String err = (String) request.getAttribute("error");
    // Lấy email người dùng đã nhập trước đó để điền lại (nếu có)
    String emailPrefill = request.getParameter("email") != null ? request.getParameter("email") : "";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quên mật khẩu - WebPhone</title>
        <!-- Nhúng CSS giống trang Login -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <style>
            /* ===== Layout tổng thể (Giống trang Login) ===== */
            html, body {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
                background: #f6f7fb;
                font-family: "Segoe UI", Arial, sans-serif;
            }

            /* ===== Header ===== */
            .auth-header {
                background: #ffd900;
                padding: 12px 28px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            }
            .auth-header .brand {
                font-weight: 700;
                font-size: 18px;
                letter-spacing: 0.5px;
            }

            /* ===== Khu vực chính ===== */
            main.auth-card {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 60px 15px;
            }

            .auth-wrapper {
                width: 100%;
                max-width: 460px;
            }

            .card-auth {
                border-radius: 14px;
                box-shadow: 0 6px 18px rgba(0,0,0,0.1);
                border: none;
                background: #fff;
                width: 100%; /* Đảm bảo card rộng full trong wrapper */
                max-width: 460px; /* Giới hạn chiều rộng card */
            }

            .card-auth .card-title {
                font-weight: 600;
                color: #333;
            }

            .form-note {
                font-size: 0.9rem;
                color: #6b7280;
            }

            .small-link {
                font-size: 0.9rem;
                text-decoration: none;
            }
            .small-link:hover {
                text-decoration: underline;
            }

            .btn-primary {
                background-color: #007bff;
                border: none;
            }

            .btn-primary:hover {
                background-color: #0069d9;
            }

            /* ===== Footer ===== */
            footer {
                background: #f5f5f5;
                color: #333;
                padding: 40px 0 20px;
            }
        </style>
    </head>
    <body>
        <!-- Header (Giữ nguyên style site) -->
        <header class="auth-header">
            <div class="d-flex align-items-center gap-2">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" alt="logo" style="height:44px;">
                <span class="brand">WEBPHONE</span>
            </div>
            <div>
                <span class="form-note">Gọi mua hàng: <b>1800.1060</b></span>
            </div>
        </header>

        <main class="auth-card">
            <div class="card card-auth p-4">
                <div class="card-body">
                    <h4 class="card-title mb-2">Quên mật khẩu</h4>
                    <p class="form-note mb-3">Nhập địa chỉ email liên kết với tài khoản của bạn để nhận mã xác thực (OTP).</p>

                    <%-- Hiển thị lỗi nếu có (Style Bootstrap) --%>
                    <% if (err != null) {%>
                    <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> <%= err%></div>
                    <% }%>

                    <!-- Form gửi OTP -->
                    <form action="forgotpassword" method="post">
                        
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" placeholder="Ví dụ: email@domain.com" required value="<%= emailPrefill%>">
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-lg">Gửi OTP</button>
                        </div>
                        
                        <div class="mt-3 text-center">
                            <a href="<%= request.getContextPath()%>/User/login.jsp" class="small-link">
                                <i class="fas fa-arrow-left"></i> Quay lại đăng nhập
                            </a>
                        </div>
                    </form>

                </div>
            </div>
        </main>

        <footer class="text-center mt-4 mb-3 form-note">
            © 2025 WebPhone
        </footer>

        <%-- Phần Script thông báo SweetAlert (nếu Servlet trả về message) --%>
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
    </body>
</html>