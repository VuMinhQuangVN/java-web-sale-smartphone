<%-- 
    Document   : manageVariant
    Created on : Oct 10, 2025, 9:57:36 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page import="Models.BienTheDienThoai"%>
<%@page import="java.util.List"%>
<%@page import="Models.DienThoai"%>
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
        <title>Quản lý các biến thể</title>
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
            <aside class="sidebar">
                <h2>📊 Admin Panel</h2>
                <ul>
                    <li><a href="<%= request.getContextPath()%>/DasboardAd"><i class="fas fa-chart-line"></i> Dashboard</a></li>
                    <li><a href="<%= request.getContextPath()%>/managerProductAd" class="active"><i class="fas fa-mobile-alt"></i> Quản lý sản phẩm</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageCategoryAd"><i class="fas fa-list"></i> Quản lý loại</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageOrderAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <div class="content">
                <%
                    DienThoai dt = (DienThoai) request.getAttribute("dt");
                    List<BienTheDienThoai> listBT = (List<BienTheDienThoai>) request.getAttribute("listBienThe");
                %>

                    <div class="container mt-4">

                        <h3 class="mb-4">
                            ⚙️ Quản lý biến thể – <%= (dt != null ? dt.getTenDT() : "")%>
                        </h3>

                        <div class="mb-3">
                            <a href="<%= request.getContextPath()%>/addBienTheAd?maDT=<%= dt.getMaDT()%>&redirect=manage" class="btn btn-success">➕ Thêm biến thể</a>
                            <a href="<%= request.getContextPath()%>/managerProductAd" class="btn btn-secondary">⬅️ Quay lại danh sách</a>
                        </div>

                        <table class="table table-bordered table-hover align-middle">
                            <thead class="table-dark text-center">
                                <tr>
                                    <th>Mã biến thể</th>
                                    <th>Màu sắc</th>
                                    <th>RAM</th>
                                    <th>ROM</th>
                                    <th>Giá (VNĐ)</th>
                                    <th>Số lượng</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>

                            <tbody class="text-center">
                                <%
                                    if (listBT != null && !listBT.isEmpty()) {
                                        for (BienTheDienThoai bt : listBT) {
                                %>
                                <tr>
                                    <td><%= bt.getMaBienthe()%></td>
                                    <td><%= bt.getMauSac()%></td>
                                    <td><%= bt.getRAM()%></td>
                                    <td><%= bt.getROM()%></td>
                                    <td><%= String.format("%,.0f", bt.getGia())%></td>
                                    <td><%= bt.getSoLuong()%></td>
                                    <td>
                                        <a href="<%= request.getContextPath()%>/editBienTheAd?id=<%= bt.getMaBienthe()%>&maDT=<%= dt.getMaDT()%>&redirect=manage" class="btn btn-warning btn-sm">✏️ Sửa</a>
                                        <a href="<%= request.getContextPath()%>/deleteBienTheAd?id=<%= bt.getMaBienthe()%>&maDT=<%= dt.getMaDT()%>&redirect=manage" class="btn btn-danger btn-sm" onclick="return confirm('Xóa biến thể này?')">🗑️ Xóa</a>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="7" class="text-muted">Chưa có biến thể nào cho sản phẩm này.</td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>

                    </div>
            </div>
        </div>



        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
    </body>
</html>
<%
    String msg = (String) request.getAttribute("message");
    String type = (String) request.getAttribute("type");
    // 2. Xóa session ngay sau khi lấy để tránh hiện lại khi F5
    if (msg != null) {
        session.removeAttribute("message");
        session.removeAttribute("type");
    }
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
