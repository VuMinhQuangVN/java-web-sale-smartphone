<%-- 
    Document   : editUser
    Created on : Oct 11, 2025, 8:16:52 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <link href="<%= request.getContextPath()%>/Admin/adminStyles.css" rel="stylesheet">
        <title>Sửa thông tin người dùng</title>
        <style>
            .form-container {
                max-width: 750px;
                margin: 0 auto;
                background: #fff;
                padding: 25px 30px;
                border-radius: 12px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }
            .form-container h3 {
                color: #333;
                font-weight: 600;
                margin-bottom: 20px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <%
            NguoiDung nguoidung = (NguoiDung) request.getAttribute("nguoidung");

            if (nguoidung == null || nguoidung.getQuyen().equals("user")) {
                response.sendRedirect(request.getContextPath() + "/User/login.jsp");
                return;
            }
        %>
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
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd" class="active"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <!-- CONTENT -->
            <main class="content">
                <%
                    NguoiDung nd = (NguoiDung) request.getAttribute("nguoidungg");
                %>
                <div class="form-container">
                    <h3>✏️ Cập nhật người dùng</h3>
                    <form action="editUserAd" method="post">
                        <input type="hidden" name="maND" value="<%=nd.getMaND()%>">

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Tên người dùng</label>
                                <input type="text" name="tenND" class="form-control" value="<%=nd.getTenND()%>" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" value="<%=nd.getEmail()%>" required>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Số điện thoại</label>
                                <input type="text" name="sdt" class="form-control" value="<%=nd.getSdt()%>" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Địa chỉ</label>
                                <input type="text" name="diachi" class="form-control" value="<%=nd.getDiachi()%>">
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Mật khẩu mới</label>
                                <input type="password" name="matkhau" class="form-control" placeholder="(Bỏ trống nếu không đổi)">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Quyền</label>
                                <select name="quyen" class="form-select" required>
                                    <option value="user" <%= "user".equals(nd.getQuyen()) ? "selected" : ""%>>User</option>
                                    <option value="admin" <%= "admin".equals(nd.getQuyen()) ? "selected" : ""%>>Admin</option>
                                </select>
                            </div>
                        </div>

                        <div class="text-center mt-4">
                            <button type="submit" class="btn btn-primary px-4">Cập nhật</button>
                            <a href="<%=request.getContextPath()%>/manageUserAd" class="btn btn-secondary px-4">Quay lại</a>
                        </div>
                    </form>
                </div>
            </main>
        </div>

        <!-- FOOTER -->
        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
    </body>
</html>
