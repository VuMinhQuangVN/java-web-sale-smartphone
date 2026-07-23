<%-- 
    Document   : resetpassword
    Created on : Nov 21, 2025
    Updated UI : Based on Login template
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    // Lấy email từ request để truyền vào form ẩn
    String email = (String) request.getAttribute("email");
    if (email == null) email = "";
    
    // Lấy thông báo lỗi nếu có (ví dụ: mật khẩu quá ngắn)
    String err = (String) request.getAttribute("error");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đặt lại mật khẩu - WebPhone</title>
        
        <!-- CSS Bootstrap & FontAwesome -->
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        
        <style>
            /* ===== Layout Chung ===== */
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
            
            .btn-primary { background-color: #007bff; border: none; }
            .btn-primary:hover { background-color: #0069d9; }

            /* Footer */
            footer { background: #f5f5f5; color: #333; padding: 40px 0 20px; }
            
            /* CSS cho nút hiện mật khẩu */
            .input-group-text {
                cursor: pointer;
                background: #fff;
                border-left: none;
            }
            .password-field {
                border-right: none;
            }
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
                <div class="card-body">
                    
                    <div class="text-center mb-3">
                        <div style="width: 60px; height: 60px; background: #ecfdf5; color: #10b981; border-radius: 50%; display: inline-flex; align-items: center; justify-content: center; font-size: 24px;">
                            <i class="fas fa-key"></i>
                        </div>
                    </div>

                    <h4 class="card-title text-center mb-2">Đặt lại mật khẩu</h4>
                    <p class="form-note text-center mb-4">
                        Vui lòng nhập mật khẩu mới cho tài khoản liên kết với email <strong><%= email %></strong>
                    </p>

                    <%-- Hiển thị lỗi nếu có --%>
                    <% if (err != null) {%>
                    <div class="alert alert-danger"><i class="fas fa-exclamation-triangle"></i> <%= err%></div>
                    <% }%>

                    <form action="resetpassword" method="post" id="resetForm">
                        <!-- Input ẩn giữ email -->
                        <input type="hidden" name="email" value="<%= email%>">

                        <div class="mb-3">
                            <label class="form-label">Mật khẩu mới</label>
                            <div class="input-group">
                                <input type="password" name="newpass" id="newpass" class="form-control password-field" 
                                       required placeholder="Nhập mật khẩu mới..." minlength="6">
                                <span class="input-group-text" onclick="togglePassword()">
                                    <i class="far fa-eye" id="toggleIcon"></i>
                                </span>
                            </div>
                            <div class="form-text text-muted" style="font-size: 0.8rem;">
                                Mật khẩu nên có ít nhất 6 ký tự.
                            </div>
                        </div>

                        <div class="d-grid gap-2 mt-4">
                            <button type="submit" class="btn btn-primary btn-lg">Lưu mật khẩu mới</button>
                        </div>
                    </form>

                </div>
            </div>
        </main>

        <footer class="text-center mt-4 mb-3 form-note">
            © 2025 WebPhone
        </footer>
        
        <%-- Script xử lý logic giao diện --%>
        <script>
            // Hàm ẩn/hiện mật khẩu
            function togglePassword() {
                const passwordInput = document.getElementById('newpass');
                const icon = document.getElementById('toggleIcon');
                
                if (passwordInput.type === 'password') {
                    passwordInput.type = 'text';
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    passwordInput.type = 'password';
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            }
        </script>

        <%-- SweetAlert (Dùng chung template) --%>
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