<%-- 
    Document   : verifyotp
    Created on : Nov 21, 2025
    Updated UI : Based on Login template
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    // Lấy thông tin từ request
    String email = (String) request.getAttribute("email");
    // Nếu email bị null (trường hợp truy cập trực tiếp), gán chuỗi rỗng để tránh lỗi null pointer
    if (email == null) email = "";
    
    String err = (String) request.getAttribute("error");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nhập mã OTP - WebPhone</title>
        
        <!-- CSS Bootstrap & FontAwesome -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        
        <style>
            /* ===== Layout (Giống các trang trước) ===== */
            html, body {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
                background: #f6f7fb;
                font-family: "Segoe UI", Arial, sans-serif;
            }

            /* Header */
            .auth-header {
                background: #ffd900;
                padding: 12px 28px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            }
            .auth-header .brand { font-weight: 700; font-size: 18px; letter-spacing: 0.5px; }

            /* Main Card */
            main.auth-card {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 60px 15px;
            }
            .card-auth {
                border-radius: 14px;
                box-shadow: 0 6px 18px rgba(0,0,0,0.1);
                border: none;
                background: #fff;
                width: 100%;
                max-width: 460px;
            }
            .card-title { font-weight: 600; color: #333; }
            .form-note { font-size: 0.9rem; color: #6b7280; }
            .small-link { font-size: 0.9rem; text-decoration: none; }
            .small-link:hover { text-decoration: underline; }
            
            .btn-primary { background-color: #007bff; border: none; }
            .btn-primary:hover { background-color: #0069d9; }

            /* Style riêng cho ô nhập OTP */
            .otp-input {
                letter-spacing: 8px;
                font-weight: bold;
                font-size: 1.5rem;
                text-align: center;
            }

            footer { background: #f5f5f5; color: #333; padding: 40px 0 20px; }
        </style>
    </head>
    <body>
        <!-- Header -->
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
                <div class="card-body text-center">
                    <div class="mb-4">
                        <div style="width: 60px; height: 60px; background: #e0f2fe; color: #0284c7; border-radius: 50%; display: inline-flex; align-items: center; justify-content: center; font-size: 24px;">
                            <i class="fas fa-shield-alt"></i>
                        </div>
                    </div>

                    <h4 class="card-title mb-2">Xác thực OTP</h4>
                    <p class="form-note mb-4">
                        Mã xác thực đã được gửi đến email:<br>
                        <strong class="text-dark"><%= email %></strong>
                    </p>

                    <%-- Hiển thị lỗi --%>
                    <% if (err != null) {%>
                    <div class="alert alert-danger text-start"><i class="fas fa-exclamation-triangle"></i> <%= err%></div>
                    <% }%>

                    <form action="verifyotp" method="post">
                        <!-- Input ẩn chứa email để Servlet biết ai đang nhập -->
                        <input type="hidden" name="email" value="<%= email%>">

                        <div class="mb-4">
                            <label class="form-label d-none">Nhập mã OTP</label>
                            <!-- Ô nhập OTP được style to, căn giữa -->
                            <input type="text" name="otp" class="form-control otp-input" 
                                   required maxlength="6" placeholder="______" autocomplete="off" autofocus>
                        </div>

                        <div class="d-grid mb-3">
                            <button type="submit" class="btn btn-primary btn-lg">Xác nhận</button>
                        </div>
                    </form>

                    <div class="form-note">
                        Không nhận được mã? 
                        <!-- Nút này có thể link lại trang forgotpassword để gửi lại hoặc link tới servlet resend -->
                        <a href="<%= request.getContextPath()%>/User/forgotpassword.jsp?email=<%= email %>" class="small-link fw-bold">Gửi lại</a>
                    </div>
                    <div class="mt-2">
                        <a href="<%= request.getContextPath()%>/User/login.jsp" class="small-link text-muted">
                            <i class="fas fa-arrow-left"></i> Quay lại đăng nhập
                        </a>
                    </div>

                </div>
            </div>
        </main>

        <footer class="text-center mt-4 mb-3 form-note">
            © 2025 WebPhone
        </footer>
        
        <%-- Script SweetAlert (Dùng chung template) --%>
        <%
            String msg = (String) request.getAttribute("message");
            String type = (String) request.getAttribute("type");
        %>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
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