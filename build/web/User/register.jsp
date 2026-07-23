<%-- 
    Document   : register
    Created on : Oct 12, 2025, 7:41:19 AM
    Author     : ACER
--%>

<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    // Nhận redirect để quay lại đúng trang sau khi đăng ký
    String redirect = request.getParameter("redirect");
    if (redirect == null) {
        redirect = "indexKhachHang";
    }
    String err = (String) request.getAttribute("error");
    String emailPrefill = request.getParameter("email") != null ? request.getParameter("email") : "";
%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký - WebPhone</title>
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <style>
            /* ===== Layout tổng thể ===== */
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
                max-width: 480px;
            }

            .card-auth {
                border-radius: 14px;
                box-shadow: 0 6px 18px rgba(0,0,0,0.1);
                border: none;
                background: #fff;
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
        <!-- HEADER -->
        <header class="auth-header">
            <div class="d-flex align-items-center gap-2">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" alt="logo" style="height:44px;">
                <span class="brand">WEBPHONE</span>
            </div>
            <div>
                <span class="form-note">Gọi mua hàng: <b>1800.1060</b></span>
            </div>
        </header>

        <!-- MAIN CONTENT -->
        <main class="auth-card">
            <div class="auth-wrapper">
                <div class="card card-auth p-4">
                    <div class="card-body">
                        <h4 class="card-title mb-2">Đăng ký tài khoản</h4>
                        <p class="form-note mb-3">Tạo tài khoản mới để bắt đầu mua sắm tại WebPhone.</p>

                        <% if (err != null) {%>
                        <div class="alert alert-danger"><%= err%></div>
                        <% }%>

                        <form method="post" action="<%= request.getContextPath()%>/registerUs">
                            <input type="hidden" name="redirect" value="<%= redirect%>">

                            <div class="mb-3">
                                <label class="form-label">Họ tên</label>
                                <input type="text" name="tenND" class="form-control" required placeholder="Nguyễn Văn A">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" required value="<%= emailPrefill%>" placeholder="abc@gmail.com">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Mật khẩu</label>
                                <input type="password" name="matkhau" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Nhập lại mật khẩu</label>
                                <input type="password" name="repassword" class="form-control" required>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">Đăng ký</button>
                            </div>
                        </form>

                        <hr class="my-3">

                        <div class="text-center form-note">
                            Đã có tài khoản?
                            <a href="<%=request.getContextPath()%>/User/login.jsp?redirect=<%= URLEncoder.encode(redirect, "UTF-8")%>">Đăng nhập ngay</a>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- FOOTER -->
        <footer>
            © 2025 WebPhone. All rights reserved.
        </footer>
    </body>
</html>

