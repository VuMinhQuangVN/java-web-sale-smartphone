<%-- 
    Document   : manageProduct
    Created on : Oct 7, 2025, 1:32:14 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
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
        <title>Quản lý sản phẩm</title>
    </head>
    <body>
        <%
            NguoiDung nguoidung = (NguoiDung) request.getAttribute("nguoidung");

            if (nguoidung == null || nguoidung.getQuyen().equals("user")) {
                response.sendRedirect(request.getContextPath() + "/User/login.jsp");
                return;
            }
            String keyword = (String) request.getAttribute("keyword");
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
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3>Danh sách điện thoại</h3>
                    <div>
                        <button onclick="exportInventory()" class="btn btn-warning ms-2 text-dark">
                            <i class="fas fa-file-excel"></i> Báo cáo Tồn kho
                        </button>
                        <a href="<%= request.getContextPath()%>/addProductAd" class="btn btn-success">➕ Thêm điện thoại</a>
                        <form class="d-inline ms-2" method="get" action="managerProductAd">
                            <input type="text" name="keyword" id="searchProductInput" placeholder="Tìm sản phẩm..." 
                                   class="form-control d-inline-block" style="width: 200px;"
                                   value="<%= (keyword != null ? keyword : "") %>">
                            <button type="submit" class="btn btn-primary">Tìm</button>
                        </form>
                    </div>
                </div>

                <table class="table table-bordered table-hover align-middle">
                    <thead class="table-dark text-center">
                        <tr>
                            <th>Mã</th>
                            <th>Tên điện thoại</th>
                            <th>Ảnh</th>
                            <th>Loại</th>
                            <th>Hãng SX</th>
                            <th>Màn hình</th>
                            <th>Pin</th>
                            <th>Chip</th>
                            <th>Camera</th>
                            <th>Mô tả</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<DienThoai> list = (List<DienThoai>) request.getAttribute("listDienThoai");
                            if (list != null && !list.isEmpty()) {
                                for (DienThoai d : list) {
                        %>
                        <tr>
                            <td><%= d.getMaDT()%></td>
                            <td><%= d.getTenDT()%></td>
                            <td><img src="<%= request.getContextPath()%>/<%= d.getAnh()%>" width="70"></td>
                            <td><%=d.getMaLoai()%></td>
                            <td><%= d.getHangSx()%></td>
                            <td><%= d.getManHinh()%></td>
                            <td><%= d.getPin()%></td>
                            <td><%= d.getChip()%></td>
                            <td><%= d.getCamera()%></td>
                            <td style="max-width:250px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
                                <%= d.getMota()%>
                            </td>
                            <td class="text-center">
                                <a href="<%= request.getContextPath()%>/editProductAd?id=<%= d.getMaDT()%>" class="btn btn-warning btn-sm">✏️</a>
                                <a href="<%= request.getContextPath()%>/deleteProductAd?id=<%= d.getMaDT()%>" class="btn btn-danger btn-sm"
                                   onclick="return confirm('Xóa sản phẩm này?');">🗑️</a>
                                <a href="<%= request.getContextPath()%>/manageVariantAd?maDT=<%= d.getMaDT()%>" class="btn btn-info btn-sm">
                                    ⚙️ Biến thể
                                </a>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr><td colspan="11" class="text-center text-muted">Không có dữ liệu</td></tr>
                        <% } %>
                    </tbody>
                </table>

                <!-- Phân trang -->
                <ul class="pagination justify-content-center">
                    <%
                        int currentPage = (int) request.getAttribute("currentPage");
                        int totalPages = (int) request.getAttribute("totalPages");

                        for (int i = 1; i <= totalPages; i++) {
                    %>
                    <li class="page-item <%= (i == currentPage ? "active" : "")%>">
                        <a class="page-link" 
                           href="<%= request.getContextPath()%>/managerProductAd?page=<%= i%><%= (keyword != null && !keyword.isEmpty() ? "&keyword=" + keyword : "")%>">
                            <%= i%>
                        </a>
                    </li>
                    <% }%>
                </ul>
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
    function exportInventory() {
        // Lấy từ khóa người dùng đang nhập ở ô tìm kiếm (nếu muốn lọc báo cáo theo tên máy)
        // Lưu ý: Cần đặt id="searchProductInput" cho ô input tìm kiếm ở trên
        var keyword = document.getElementById("searchProductInput").value;
        
        // Chuyển hướng tải file
        window.location.href = "ExportExcelControl?type=inventory&keyword=" + encodeURIComponent(keyword);
    }
</script>
