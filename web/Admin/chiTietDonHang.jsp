<%-- 
    Document   : chiTietDonHang
    Created on : Oct 31, 2025, 4:03:30 PM
    Author     : ACER
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="Models.ChiTietHoaDon"%>
<%@page import="Models.DienThoai"%>
<%@page import="Models.BienTheDienThoai"%>
<%@page import="Models.DonHang"%>
<%@page import="Models.NguoiDung"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    NguoiDung admin = (NguoiDung) request.getAttribute("nguoidung");
    DonHang dh = (DonHang) request.getAttribute("donhang");
    List<Map<String, Object>> listCT = (List<Map<String, Object>>) request.getAttribute("listChiTiet");
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết đơn hàng #<%= dh.getMaDH()%></title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <link href="<%= request.getContextPath()%>/Admin/adminStyles.css" rel="stylesheet">
    </head>

    <body>
        <!-- HEADER -->
        <header class="headercss">
            <section class="logo-section d-flex align-items-center">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" alt="Logo">
                <span class="brand-name">WebPhone Admin</span>
            </section>
            <section class="admin-name text-end">
                Xin chào, <%= admin.getTenND()%>
            </section>
        </header>

        <div class="admin-container">
            <aside class="sidebar">
                <h2>📊 Admin Panel</h2>
                <ul>
                    <li><a href="<%= request.getContextPath()%>/DasboardAd"><i class="fas fa-chart-line"></i> Dashboard</a></li>
                    <li><a href="<%= request.getContextPath()%>/managerProductAd"><i class="fas fa-mobile-alt"></i> Quản lý sản phẩm</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageCategoryAd"><i class="fas fa-list"></i> Quản lý loại</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageOrderAd" class="active"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <main class="content">
                <h3 class="fw-bold mb-3">Chi tiết đơn hàng #<%= dh.getMaDH()%></h3>
                <p><b>Khách hàng:</b> <%= dh.getTenND()%></p>
                <p><b>Ngày đặt:</b> <%= dh.getNgayDH()%></p>
                <p><b>Trạng thái:</b> <span class="badge bg-info"><%= dh.getTrangThai()%></span></p>
                <p><b>Tổng tiền:</b> <span class="text-danger fw-bold"><%= String.format("%,.0f", dh.getTongTien())%> ₫</span></p>

                <hr>

                <h5>📱 Danh sách sản phẩm trong đơn hàng</h5>
                <table class="table table-bordered table-hover align-middle mt-3">
                    <thead class="table-light">
                        <tr>
                            <th>Ảnh</th>
                            <th>Tên điện thoại</th>
                            <th>Phiên bản</th>
                            <th>Số lượng</th>
                            <th>Giá</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (listCT != null && !listCT.isEmpty()) {
                                for (Map<String, Object> map : listCT) {
                                    Models.ChiTietHoaDon cthd = (Models.ChiTietHoaDon) map.get("chitietdonhang");
                                    Models.DienThoai dt = (Models.DienThoai) map.get("dienthoai");
                                    Models.BienTheDienThoai bt = (Models.BienTheDienThoai) map.get("bienthe");
                        %>
                        <tr>
                            <td style="width: 90px;">
                                <img src="<%= request.getContextPath()%>/<%= dt.getAnh()%>" alt="Ảnh SP" class="img-fluid rounded" style="max-height:70px;">
                            </td>
                            <td><%= dt.getTenDT()%></td>
                            <td><%= bt.getRAM()%> / <%= bt.getROM()%> / <%= bt.getMauSac()%></td>
                            <td><%= cthd.getSoLuong()%></td>
                            <td><%= String.format("%,.0f", cthd.getGia())%> ₫</td>
                            <td class="text-danger fw-bold"><%= String.format("%,.0f", cthd.getThanhTien())%> ₫</td>
                        </tr>
                        <% }
                    } else { %>
                        <tr>
                            <td colspan="6" class="text-center text-muted py-4">
                                Không có sản phẩm nào trong đơn hàng này.
                            </td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>

                <a href="<%= request.getContextPath()%>/manageOrderAd" class="btn btn-secondary mt-3">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
            </main>
        </div>

        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
    </body>
</html>

