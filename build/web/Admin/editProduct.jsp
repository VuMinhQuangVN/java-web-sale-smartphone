<%-- 
    Document   : editProduct
    Created on : Oct 8, 2025, 10:35:56 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page import="Models.BienTheDienThoai"%>
<%@page import="Models.DienThoai"%>
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
        <title>Cập nhập sản phẩm</title>
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
                    <li><a href="<%= request.getContextPath()%>/manageOrderAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>
            <% DienThoai dt = (DienThoai) request.getAttribute("dienthoai");%>

            <div class="content">
                <div class="container mt-4">
                    <h3 class="mb-4">Cập nhật sản phẩm</h3>
                    <form action="<%=request.getContextPath() %>/editProductAd" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="maDT" value="<%= dt.getMaDT()%>">

                        <!-- Thông tin chung -->
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Tên điện thoại</label>
                                <input type="text" name="tenDT" value="<%= dt.getTenDT()%>" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Loại</label>
                                <select name="maLoai" class="form-select" required>
                                    <%
                                        List<Loai> listLoai = (List<Loai>) request.getAttribute("listLoai");
                                        if (listLoai != null) {
                                            for (Loai l : listLoai) {
                                                String selected = (l.getMaLoai() == dt.getMaLoai()) ? "selected" : "";
                                    %>
                                    <option value="<%= l.getMaLoai()%>" <%= selected%>><%= l.getTenLoai()%></option>
                                    <% }
                }%>
                                </select>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label class="form-label">Hãng SX</label>
                                <input type="text" name="hangSx" value="<%= dt.getHangSx()%>" class="form-control" required>
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Màn hình</label>
                                <input type="text" name="manHinh" value="<%= dt.getManHinh()%>" class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Pin</label>
                                <input type="text" name="pin" value="<%= dt.getPin()%>" class="form-control">
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label class="form-label">Chip</label>
                                <input type="text" name="Chip" value="<%= dt.getChip()%>" class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Camera</label>
                                <input type="text" name="camera" value="<%= dt.getCamera()%>" class="form-control">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Ảnh sản phẩm</label>
                            <input type="file" name="anh" class="form-control">
                            <% if (dt.getAnh() != null && !dt.getAnh().isEmpty()) {%>
                            <div class="mt-2">
                                <p>Ảnh hiện tại:</p>
                                <img src="<%= request.getContextPath()%>/<%= dt.getAnh()%>" width="120">
                            </div>
                            <% }%>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mô tả</label>
                            <textarea name="mota" rows="4" class="form-control"><%= dt.getMota()%></textarea>
                        </div>

                        <hr>
                        <h5>Danh sách biến thể</h5>
                        <table class="table table-bordered">
                            <thead>
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
                            <tbody>
                                <%
                                    List<BienTheDienThoai> listBT = (List<BienTheDienThoai>) request.getAttribute("listBienThe");
                                    if (listBT != null) {
                                        for (BienTheDienThoai bt : listBT) {
                                %>
                                <tr>
                                    <td><%= bt.getMaBienthe()%></td>
                                    <td><%= bt.getMauSac()%></td>
                                    <td><%= bt.getRAM()%></td>
                                    <td><%= bt.getROM()%></td>
                                    <td><%= bt.getGia()%></td>
                                    <td><%= bt.getSoLuong()%></td>
                                    <td>
                                        <a href="<%= request.getContextPath()%>/editBienTheAd?id=<%= bt.getMaBienthe()%>&maDT=<%= dt.getMaDT()%>&redirect1=edit" class="btn btn-warning btn-sm">Sửa</a>
                                        <a href="<%= request.getContextPath()%>/deleteBienTheAd?id=<%= bt.getMaBienthe()%>&maDT=<%= dt.getMaDT()%>&redirect1=edit" class="btn btn-danger btn-sm" onclick="return confirm('Xóa biến thể này?')">Xóa</a>
                                    </td>
                                </tr>
                                <% }
              }%>
                            </tbody>
                        </table>

                        <a href="<%= request.getContextPath()%>/addBienTheAd?maDT=<%= dt.getMaDT()%>&redirect1=edit" class="btn btn-success">➕ Thêm biến thể</a>

                        <hr>
                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        <a href="<%= request.getContextPath()%>/managerProductAd" class="btn btn-secondary">Quay lại</a>
                    </form>
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
