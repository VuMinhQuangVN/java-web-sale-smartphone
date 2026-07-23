<%-- 
    Document   : manageUser
    Created on : Oct 11, 2025, 2:42:38 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page import="java.util.List"%>
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
        <title>Quản lý người dùng</title>
        <style>
            .user-table th {
                background-color: #ffd900;
                color: #000;
            }

            .user-table td, .user-table th {
                vertical-align: middle;
                text-align: center;
            }

            .user-action-btn {
                padding: 4px 10px;
                border-radius: 6px;
                font-size: 14px;
            }

            .btn-edit {
                background-color: #3498db;
                color: white;
            }

            .btn-delete {
                background-color: #e74c3c;
                color: white;
            }

            .btn-add {
                background-color: #2ecc71;
                color: white;
            }

            .btn-edit:hover {
                background-color: #2980b9;
            }
            .btn-delete:hover {
                background-color: #c0392b;
            }
            .btn-add:hover {
                background-color: #27ae60;
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
        <!-- HEADER -->
        <header class="headercss">
            <section class="logo-section d-flex align-items-center">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" alt="Logo">
                <span class="brand-name">WebPhone Admin</span>
            </section>

            <section class="admin-name text-end">
                Xin chào, <%= nguoidung.getTenND()%>
            </section>
        </header>

        <div class="admin-container">
            <!-- SIDEBAR -->
            <aside class="sidebar">
                <h2>📊 Admin Panel</h2>
                <ul>
                    <li><a href="<%= request.getContextPath()%>/DasboardAd"><i class="fas fa-chart-line"></i> Dashboard</a></li>
                    <li><a href="<%= request.getContextPath()%>/managerProductAd"><i class="fas fa-mobile-alt"></i> Quản lý sản phẩm</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageCategoryAd"><i class="fas fa-list"></i> Quản lý loại</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageOrderAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd" class="active"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <!-- CONTENT -->

            <main class="content">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h3 class="fw-bold">👤 Quản lý người dùng</h3>

                    <div style="display: flex; align-items: center; gap: 10px;">

                        <!-- Nút thêm -->
                        <a href="<%=request.getContextPath()%>/addUserAd" 
                           class="btn btn-add" 
                           style="height: 40px; display: flex; align-items: center;">
                            + Thêm người dùng
                        </a>

                        <!-- Form tìm kiếm -->
                        <form method="get" action="<%=request.getContextPath()%>/manageUserAd" 
                              class="d-flex" 
                              style="margin: 0;">

                            <input type="text" name="keyword" 
                                   class="form-control me-2" 
                                   placeholder="Nhập email cần tìm"
                                   style="height: 40px;">

                            <button type="submit" 
                                    class="btn btn-primary"
                                    style="height: 40px;">
                                Tìm
                            </button>

                        </form>
                    </div>
                </div>

                <%
                    List<NguoiDung> listND = (List<NguoiDung>) request.getAttribute("listNguoiDung");
                    if (listND != null && !listND.isEmpty()) {
                %>
                <table class="table table-bordered user-table align-middle shadow-sm">
                    <thead>
                        <tr>
                            <th style="width: 5%;">Mã</th>
                            <th style="width: 15%;">Tên người dùng</th>
                            <th style="width: 15%;">Email</th>
                            <th style="width: 10%;">SĐT</th>
                            <th style="width: 25%;">Địa chỉ</th>
                            <th style="width: 10%;">Quyền</th>
                            <th style="width: 20%;">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (NguoiDung nd : listND) {%>
                        <tr>
                            <td><%= nd.getMaND()%></td>
                            <td><%= nd.getTenND()%></td>
                            <td><%= nd.getEmail()%></td>
                            <td><%= nd.getSdt()%></td>
                            <td><%= nd.getDiachi()%></td>
                            <td>
                                <span class="badge <%= "admin".equalsIgnoreCase(nd.getQuyen()) ? "bg-danger" : "bg-secondary"%>">
                                    <%= nd.getQuyen()%>
                                </span>
                            </td>
                            <td>
                                <a href="<%=request.getContextPath()%>/editUserAd?id=<%=nd.getMaND()%>" class="btn user-action-btn btn-edit me-2">Sửa</a>                              
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <ul class="pagination justify-content-center">
                    <%
                        String keyword = (String) request.getAttribute("keyword");
                        int currentPage = (int) request.getAttribute("currentPage");
                        int totalPages = (int) request.getAttribute("totalPages");

                        for (int i = 1; i <= totalPages; i++) {
                    %>
                    <li class="page-item <%= (i == currentPage ? "active" : "")%>">
                        <a class="page-link" 
                           href="<%= request.getContextPath()%>/manageUserAd?page=<%= i%><%= (keyword != null && !keyword.isEmpty() ? "&keyword=" + keyword : "")%>">
                            <%= i%>
                        </a>
                    </li>
                    <% }%>
                </ul>
                <% } else { %>
                <div class="alert alert-info text-center">Chưa có người dùng nào trong hệ thống.</div>
                <% }%>

            </main>
        </div>

        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
    </body>
</html>
