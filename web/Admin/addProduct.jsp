<%-- 
    Document   : addProduct
    Created on : Oct 7, 2025, 2:17:55 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page import="java.util.List"%>
<%@page import="Models.Loai"%>
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
        <title>Thêm sản phẩm</title>
    </head>
    <body class="bg-light">
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
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <div class="content">
                <div class="container mt-4">
                    <h3 class="mb-4">➕ Thêm điện thoại mới</h3>

                    <form action="addProductAd" method="post" enctype="multipart/form-data">

                        <!-- THÔNG TIN CHUNG -->
                        <h5 class="text-primary mt-3">Thông tin chung</h5>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Tên điện thoại</label>
                                <input type="text" name="tenDT" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Loại</label>
                                <select name="maLoai" class="form-select" required>
                                    <%
                                        List<Loai> listLoai = (List<Loai>) request.getAttribute("listLoai");
                                        if (listLoai != null) {
                                            for (Loai l : listLoai) {
                                    %>
                                    <option value="<%= l.getMaLoai()%>"><%= l.getTenLoai()%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label class="form-label">Hãng sản xuất</label>
                                <input type="text" name="hangSx" class="form-control" required>
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Màn hình</label>
                                <input type="text" name="manHinh" class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Pin</label>
                                <input type="text" name="pin" class="form-control">
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Chip</label>
                                <input type="text" name="Chip" class="form-control">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Camera</label>
                                <input type="text" name="camera" class="form-control" placeholder="VD: Chính 200MP, Phụ 12MP">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Ảnh sản phẩm</label>
                            <input type="file" name="anh" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mô tả</label>
                            <textarea name="mota" rows="4" class="form-control"></textarea>
                        </div>

                        <!-- THÔNG TIN BIẾN THỂ -->
                        <hr>
                        <h5 class="text-primary mt-3">Biến thể đầu tiên</h5>
                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label class="form-label">Màu sắc</label>
                                <input type="text" name="mauSac" class="form-control" placeholder="VD: Đen, Xanh..." required>
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">RAM</label>
                                <input type="text" name="RAM" class="form-control" placeholder="VD: 8GB" required>
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">ROM</label>
                                <input type="text" name="ROM" class="form-control" placeholder="VD: 256GB" required>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Giá (VNĐ)</label>
                                <input type="number" name="gia" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Số lượng</label>
                                <input type="number" name="soLuong" class="form-control" required>
                            </div>
                        </div>

                        <!-- NÚT -->
                        <div class="mt-4">
                            <button type="submit" class="btn btn-success">💾 Lưu</button>
                            <a href="<%= request.getContextPath()%>/managerProductAd" class="btn btn-secondary">↩ Quay lại</a>
                        </div>

                    </form>
                </div>
            </div>

        </div>



        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
    </body>
</html>
