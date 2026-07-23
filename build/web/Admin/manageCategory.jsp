<%-- 
    Document   : manageCategory
    Created on : Oct 7, 2025, 5:03:45 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page import="Models.Loai"%>
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
        <title>Quản lý loại</title>
    </head>
    <body>
        <!-- HEADER -->
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



        <div class="admin-container">
            <aside class="sidebar">
                <h2>📊 Admin Panel</h2>
                <ul>
                    <li><a href="<%= request.getContextPath()%>/DasboardAd"><i class="fas fa-chart-line"></i> Dashboard</a></li>
                    <li><a href="<%= request.getContextPath()%>/managerProductAd"><i class="fas fa-mobile-alt"></i> Quản lý sản phẩm</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageCategoryAd" class="active"><i class="fas fa-list"></i> Quản lý loại</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageOrderAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <div class="content p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3>Danh sách loại sản phẩm</h3>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">+ Thêm loại</button>
                </div>

                <!-- Table -->
                <table class="table table-bordered table-hover align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>Mã loại</th>
                            <th>Tên loại</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Loai> listLoai = (List<Loai>) request.getAttribute("listLoai");
                            if (listLoai != null) {
                                for (Loai l : listLoai) {
                        %>
                        <tr>
                            <td><%= l.getMaLoai()%></td>
                            <td><%= l.getTenLoai()%></td>
                            <td>
                                <button 
                                    class="btn btn-warning btn-sm"
                                    data-bs-toggle="modal"
                                    data-bs-target="#editModal"
                                    data-id="<%= l.getMaLoai()%>"
                                    data-name="<%= l.getTenLoai()%>">
                                    Sửa
                                </button>
                                <a href="/BTL_QlBanDienThoai/deleteCategoryAd?id=<%= l.getMaLoai()%>" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Bạn có chắc muốn xóa loại này không?')">
                                    Xóa
                                </a>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal Thêm -->
        <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form action="/BTL_QlBanDienThoai/addCategoryAd" method="post" class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">Thêm loại sản phẩm</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <label for="tenLoai" class="form-label">Tên loại</label>
                        <input type="text" name="tenLoai" id="tenLoai" class="form-control" required>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Thêm</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal Sửa -->
        <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form action="/BTL_QlBanDienThoai/updateCategoryAd" method="post" class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Sửa loại sản phẩm</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="maLoai" id="editMaLoai">
                        <label for="editTenLoai" class="form-label">Tên loại</label>
                        <input type="text" name="tenLoai" id="editTenLoai" class="form-control" required>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <%
            String msg = (String) session.getAttribute("message");
            String type = (String) session.getAttribute("type");
            // 2. Xóa session ngay sau khi lấy để tránh hiện lại khi F5
            if (msg != null) {
                session.removeAttribute("message");
                session.removeAttribute("type");
            }
        %>
        <!-- Script để tự động đổ dữ liệu vào modal Sửa -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
                                       const editModal = document.getElementById('editModal');
                                       editModal.addEventListener('show.bs.modal', function (event) {
                                           const button = event.relatedTarget;
                                           const id = button.getAttribute('data-id');
                                           const name = button.getAttribute('data-name');

                                           document.getElementById('editMaLoai').value = id;
                                           document.getElementById('editTenLoai').value = name;
                                       });
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

        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
    </body>
</html>



