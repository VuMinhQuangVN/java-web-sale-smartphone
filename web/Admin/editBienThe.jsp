<%-- 
    Document   : editBienThe
    Created on : Oct 10, 2025, 9:15:39 PM
    Author     : ACER
--%>

<%@page import="Models.NguoiDung"%>
<%@page import="Models.BienTheDienThoai"%>
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
        <title>Chỉnh sửa biến thể điện thoại</title>
    </head>
    <body>
        <%
            NguoiDung nguoidung = (NguoiDung) request.getAttribute("nguoidung");

            if (nguoidung == null || nguoidung.getQuyen().equals("user")) {
                response.sendRedirect(request.getContextPath() + "/User/login.jsp");
                return;
            }
            
            String redirect = (String) request.getAttribute("redirect");
            if("".equals(redirect.trim())){
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
            <%
                BienTheDienThoai bt = (BienTheDienThoai) request.getAttribute("bt");
                DienThoai dt = (DienThoai) request.getAttribute("dt");
            %>
            <div class="content">
                <div class="content">
                    <div class="container mt-4">
                        <h3 class="mb-4">
                            Cập nhật biến thể – <%= (dt != null ? dt.getTenDT() : "")%>
                        </h3>

                        <form action="editBienTheAd" method="post">
                            <input type="hidden" name="maBienThe" value="<%= bt.getMaBienthe()%>">
                            <input type="hidden" name="maDT" value="<%= bt.getMaDT()%>">
                            <input type="hidden" name="redirect" value="<%= redirect%>">

                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label class="form-label">Màu sắc</label>
                                    <input type="text" name="mauSac" value="<%= bt.getMauSac()%>" class="form-control" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">RAM</label>
                                    <input type="text" name="RAM" value="<%= bt.getRAM()%>" class="form-control" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">ROM</label>
                                    <input type="text" name="ROM" value="<%= bt.getROM()%>" class="form-control" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Giá (VNĐ)</label>
                                    <input type="number" name="gia" value="<%= bt.getGia()%>" class="form-control" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Số lượng</label>
                                    <input type="number" name="soLuong" value="<%= bt.getSoLuong()%>" class="form-control" required>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-primary">💾 Lưu thay đổi</button>
                                <%  
                                    if("manage".equals(redirect.trim())){                                       
                                %>
                                <a href="<%=request.getContextPath() %>/manageVariantAd?id=<%= dt.getMaDT()%>" class="btn btn-secondary">⬅️ Quay lại</a>
                                <%  
                                    }else{
                                %>
                                <a href="<%=request.getContextPath() %>/editProductAd?id=<%= dt.getMaDT()%>" class="btn btn-secondary">⬅️ Quay lại</a>
                                <%  
                                    }
                                %>
                            </div>
                        </form>
                    </div>
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
