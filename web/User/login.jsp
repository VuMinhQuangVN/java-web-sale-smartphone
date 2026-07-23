<%-- 
    Document   : login
    Created on : Oct 12, 2025, 6:56:55 AM
    Author     : ACER
--%>

<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    // Có thể truyền ?redirect=/chiTietSP?id=123 hoặc ?redirect=indexKhachHang
    String redirect = (String) request.getAttribute("redirect");
    if (redirect == null) {
        redirect = "indexKhachHang";
    }
    String err = (String) request.getAttribute("error");
    String emailPrefill = request.getParameter("email") != null ? request.getParameter("email") : "";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập - WebPhone</title>
        <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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
                padding: 60px 15px;      /* ✅ thêm padding để cân đối khi màn hình lớn */
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
        <!-- header (giữ style site) -->
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
                    <h4 class="card-title mb-2">Đăng nhập</h4>
                    <p class="form-note mb-3">Đăng nhập bằng tài khoản để tiếp tục mua hàng và quản lý giỏ của bạn.</p>

                    <% if (err != null) {%>
                    <div class="alert alert-danger"><%= err%></div>
                    <% }%>

                    <form method="post" action="<%= request.getContextPath()%>/loginUs">
                        <!-- truyền redirect để servlet biết phải quay lại đâu -->
                        <input type="hidden" name="redirect" value="<%= redirect%>">

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required value="<%= emailPrefill%>">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>

                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <div>
                                <input type="checkbox" name="remember" id="remember"> <label for="remember" class="small-link">Ghi nhớ</label>
                            </div>
                            <div>
                               <a href="<%= request.getContextPath()%>/User/forgotpassword.jsp?redirect=<%= URLEncoder.encode(redirect, "UTF-8")%>" 
                               class="small-link">Quên mật khẩu?</a>
                            </div>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">Đăng nhập</button>
                        </div>
                    </form>

                    <hr class="my-3">

                    <div class="text-center form-note">
                        Hoặc
                        <a href="<%= request.getContextPath()%>/User/register.jsp?redirect=<%= URLEncoder.encode(redirect, "UTF-8")%>">Tạo tài khoản mới</a>
                    </div>
                </div>
            </div>
        </main>

        <footer class="text-center mt-4 mb-3 form-note">
            © 2025 WebPhone
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