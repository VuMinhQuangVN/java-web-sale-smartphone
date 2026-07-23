<%-- 
    Document   : gioHangKH
    Created on : Oct 4, 2025, 1:39:53 PM
    Author     : Administrator
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.NumberFormat, java.util.Locale" %>
<%@ page import="Models.*" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>🛒 Giỏ hàng của bạn - WebPhone</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <style>
            /* ===== LAYOUT TOÀN TRANG ===== */
            html, body {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
            }
            body {
                background: #f9fafc;
                font-family: "Segoe UI", Arial, sans-serif;
                color: #333;
            }

            /* ===== HEADER ===== */
            header {
                background: #ffd900;
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 12px 32px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                position: sticky;
                top: 0;
                z-index: 100;
            }
            header .logo {
                height: 55px;
            }
            .brand-name {
                font-weight: 700;
                font-size: 22px;
                letter-spacing: 0.5px;
            }
            header a {
                color: #222;
                font-weight: 500;
                text-decoration: none;
                transition: 0.25s;
            }
            header a:hover {
                color: #0056b3;
            }

            /*Main*/
            main {
                flex: 1;
                display: flex;
                flex-direction: column;
                justify-content: flex-start;
            }

            /* ===== CART ITEM ===== */
            .cart-item {
                background: #fff;
                border-radius: 12px;
                padding: 16px 20px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                margin-bottom: 16px;
            }
            .cart-item img {
                width: 100px;
                height: 100px;
                object-fit: contain;
                border-radius: 8px;
                border: 1px solid #eee;
                background: #fafafa;
            }
            .cart-item .title {
                font-weight: 600;
                font-size: 16px;
            }

            .cart-item .text-end {
                display: flex;
                align-items: center;
                justify-content: flex-end;
                gap: 6px; /* khoảng cách giữa các phần tử */
                flex-wrap: wrap;
            }

            .qty-input {
                width: 70px !important;
                height: 32px !important;
                font-size: 14px;
                text-align: center;
                padding: 2px 4px;
                margin-right: 4px;
            }

            .cart-item button.btn-sm {
                height: 32px !important;
                line-height: 1 !important;
                padding: 0 10px !important;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                vertical-align: middle;
            }


            .cart-item .form-control {
                height: 32px !important;
                padding: 4px 8px;
                font-size: 14px;
            }


            /* ===== SUMMARY ===== */
            .cart-summary {
                background: #fff;
                border-radius: 12px;
                padding: 20px;
                margin-top: 40px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            }
            .cart-summary h5 {
                font-weight: 700;
            }
            .btn-checkout {
                background: #e53935;
                border: none;
                color: #fff;
                font-size: 17px;
                width: 100%;
                padding: 10px;
                border-radius: 8px;
                transition: all 0.25s ease;
            }
            .btn-checkout:hover {
                background: #c62828;
            }

            /* ===== FOOTER ===== */
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
        <%
            NguoiDung nd = (NguoiDung) request.getAttribute("nguoidung");
            boolean loggedIn = (nd != null);
            List<Map<String, Object>> listGioHang = (List<Map<String, Object>>) request.getAttribute("giohang");
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            int cartSize = (int) request.getAttribute("cartSize");
        %>

        <!-- ===== HEADER ===== -->
        <header>
            <div class="d-flex align-items-center gap-2">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" class="logo">
                <span class="brand-name">WEBPHONE</span>
            </div>

            <nav class="d-flex align-items-center gap-4">

                <!-- 1. Icon Trang chủ -->
                <a href="<%= request.getContextPath()%>/indexKhachHang" class="nav-icon-link" title="Trang chủ">
                    <i class="fas fa-home"></i>
                </a>

                <!-- 2. Icon Giỏ hàng với Badge số lượng -->
                <a href="<%= request.getContextPath()%>/gioHang" class="nav-icon-link position-relative" title="Giỏ hàng">
                    <i class="fas fa-shopping-cart"></i>
                    <%-- Chỉ hiển thị badge nếu trong giỏ có hàng (cartSize > 0) --%>
                    <% if (cartSize > 0) {%>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                        <%= cartSize%>
                        <span class="visually-hidden">sản phẩm trong giỏ hàng</span>
                    </span>
                    <% } %>
                </a>

                <!-- 3. Thông tin người dùng hoặc Nút Đăng nhập -->
                <% if (loggedIn) {%>
                <div class="user-profile-link d-flex align-items-center">
                    <a href="<%= request.getContextPath()%>/infomationUs" class="d-flex align-items-center gap-2 text-decoration-none">
                        <i class="fas fa-user-circle"></i>
                        <span><%= nd.getTenND()%></span>
                    </a>
                    <%-- Thông báo cập nhật thông tin vẫn giữ nguyên --%>
                    <% if (nd.getSdt() == null || nd.getDiachi() == null || nd.getSdt().trim().equals("") || nd.getDiachi().trim().equals("")) { %>
                    <span class="badge bg-warning text-dark ms-1" style="font-size: 0.7em;">!</span>
                    <% }%>
                </div>
                <% } else {%>
                <a href="<%= request.getContextPath()%>/loginUs?redirect=indexKhachHang" class="btn btn-dark btn-sm">Đăng nhập</a>
                <% }%>
            </nav>
        </header>
        <main>
            <!-- ===== BODY ===== -->
            <!-- GIỎ HÀNG -->
            <div class="container my-5">
                <h3 class="fw-bold mb-4 text-center">🛒 Giỏ hàng của bạn</h3>

                <div class="d-flex align-items-start gap-4 flex-wrap">
                    <!-- DANH SÁCH SẢN PHẨM -->
                    <div class="flex-grow-1">
                        <div class="d-flex align-items-center mb-3">
                            <input type="checkbox" id="selectAll" class="form-check-input me-2">
                            <label for="selectAll" class="fw-semibold">Chọn tất cả</label>
                        </div>

                        <%
                            if (listGioHang != null && !listGioHang.isEmpty()) {
                                for (Map<String, Object> item : listGioHang) {
                                    DienThoai dt = (DienThoai) item.get("dienthoai");
                                    BienTheDienThoai bt = (BienTheDienThoai) item.get("bienthe");
                                    int soLuong = (int) item.get("soLuong");
                        %>

                        <div class="cart-item d-flex justify-content-between align-items-center">
                            <!-- Thông tin sản phẩm -->
                            <div class="d-flex align-items-center flex-grow-1">
                                <input type="checkbox" name="selectedItems"
                                       value="<%= item.get("maCTGH")%>"
                                       class="form-check-input me-3 select-item"
                                       form="checkoutForm"
                                       <%-- Vô hiệu hóa checkbox nếu sản phẩm đã hết hàng --%>
                                       <%= (bt.getSoLuong() == 0) ? "disabled" : ""%>>

                                <img src="<%= request.getContextPath()%>/<%= dt.getAnh()%>"
                                     alt="<%= dt.getTenDT()%>" class="rounded border" style="width:90px;height:90px;object-fit:contain;">

                                <div class="ms-3">
                                    <div class="title fw-semibold"><%= dt.getTenDT()%></div>
                                    <small class="text-muted">
                                        <%= bt.getMauSac()%> | <%= bt.getRAM()%> / <%= bt.getROM()%>
                                    </small><br>
                                    <span class="text-danger fw-bold"><%= nf.format(bt.getGia())%> ₫</span><br>

                                    <%-- Hiển thị "Hết hàng" nếu số lượng là 0, ngược lại hiển thị số lượng còn lại --%>
                                    <% if (bt.getSoLuong() > 0) {%>
                                    <small class="text-success">Còn lại: <%= bt.getSoLuong()%> sản phẩm</small>
                                    <% } else { %>
                                    <small class="text-danger fw-bold">Hết hàng</small>
                                    <% }%>
                                </div>
                            </div>

                            <!-- Cập nhật giỏ hàng -->
                            <div class="text-end">
                                <form action="<%= request.getContextPath()%>/updateCartUs" method="post" class="d-inline">
                                    <input type="hidden" name="maCTGH" value="<%= item.get("maCTGH")%>">
                                    <input type="number" name="soLuong" value="<%= soLuong%>" min="1"
                                           max="<%= bt.getSoLuong()%>"
                                           class="form-control d-inline form-control-sm text-center qty-input">
                                    <button class="btn btn-outline-success btn-sm">Cập nhật</button>
                                </form>
                                <form action="<%= request.getContextPath()%>/deleteCartUs" method="post" class="d-inline ms-2">
                                    <input type="hidden" name="maCTGH" value="<%= item.get("maCTGH")%>">
                                    <button class="btn btn-outline-danger btn-sm">Xóa</button>
                                </form>
                            </div>
                        </div>
                        <% }%>

                    </div>

                    <!-- TỔNG TIỀN -->
                    <div style="width:350px;">
                        <div class="cart-summary">
                            <h5 class="fw-bold mb-3">Tóm tắt đơn hàng</h5>
                            <p class="text-muted small">Chọn sản phẩm muốn mua rồi bấm “Thanh toán”.</p>
                            <form id="checkoutForm" action="<%= request.getContextPath()%>/checkoutUs" method="get">
                                <div class="d-flex justify-content-between mb-3">
                                    <span>Sản phẩm đã chọn:</span>
                                    <strong id="selectedCount">0</strong>
                                </div>
                                <div class="d-flex justify-content-between mb-3">
                                    <span>Tổng tạm tính:</span>
                                    <strong id="selectedTotal">0 ₫</strong>
                                </div>
                                <button type="button" class="btn-checkout" id="checkoutButton">Tiến hành thanh toán</button>
                            </form>
                        </div>
                    </div>
                </div>

                <% } else {%>
                <div class="text-center py-5 bg-white rounded shadow-sm">
                    <h5 class="text-muted mb-3">Giỏ hàng của bạn đang trống 😢</h5>
                    <a href="<%= request.getContextPath()%>/indexKhachHang" class="btn btn-primary">Tiếp tục mua sắm</a>
                </div>
                <% }%>
            </div>

        </main>
        <!-- ===== FOOTER ===== -->
        <footer>
            <div class="container text-center">
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <h6>Về chúng tôi</h6>
                        <p>WebPhone cung cấp điện thoại & phụ kiện chính hãng với giá tốt nhất.</p>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Hỗ trợ</h6>
                        <a href="#">Liên hệ</a><br>
                        <a href="#">Chính sách đổi trả</a><br>
                        <a href="#">Hướng dẫn mua hàng</a>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Chính sách</h6>
                        <a href="#">Bảo hành</a><br>
                        <a href="#">Bảo mật</a><br>
                        <a href="#">Vận chuyển</a>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Kết nối</h6>
                        <a href="#">Facebook</a> |
                        <a href="#">Zalo</a> |
                        <a href="#">Instagram</a>
                    </div>
                </div>
                <hr class="border-secondary">
                <p class="mb-0 pb-2">© 2025 WebPhone. All rights reserved.</p>
            </div>
        </footer>

        <%
            String msg = (String) request.getAttribute("message");
            String type = (String) request.getAttribute("type");
        %>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            document.addEventListener('DOMContentLoaded', () => {
                const checkoutButton = document.getElementById('checkoutButton');
                const checkboxes = document.querySelectorAll('.select-item');
                const selectAll = document.getElementById('selectAll');
                const totalText = document.getElementById('selectedTotal');
                const countText = document.getElementById('selectedCount');

                selectAll.addEventListener('change', () => {
                    checkboxes.forEach(cb => cb.checked = selectAll.checked);
                    updateTotal();
                });


                checkboxes.forEach(cb => cb.addEventListener('change', () => {
                        const allChecked = [...checkboxes].every(cb => cb.checked);
                        selectAll.checked = allChecked;
                        updateTotal();
                    }));

                function updateTotal() {
                    let total = 0, count = 0;
                    checkboxes.forEach(cb => {
                        if (cb.checked) {
                            count++;
                            const parent = cb.closest('.cart-item');
                            const price = parent.querySelector('.text-danger').textContent.replace(/[^\d]/g, '');
                            const qty = parent.querySelector('input[name="soLuong"]').value;
                            total += parseInt(price) * parseInt(qty);
                        }
                    });
                    countText.textContent = count;
                    totalText.textContent = total.toLocaleString('vi-VN') + ' ₫';
                }

                document.getElementById("checkoutButton").addEventListener("click", function (e) {
                    const hasPhone = "<%= (nd.getSdt() != null && !nd.getSdt().trim().isEmpty())%>" === "true";
                    const hasAddress = "<%= (nd.getDiachi() != null && !nd.getDiachi().trim().isEmpty())%>" === "true";

                    if (!hasPhone || !hasAddress) {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Vui lòng cập nhật thông tin cá nhân trước khi mua!',
                            confirmButtonText: 'Cập nhật ngay'
                        }).then(result => {
                            if (result.isConfirmed) {
                                window.location.href = "<%= request.getContextPath()%>/infomationUs";
                            }
                        });
                    } else {
                        // Nếu đủ thông tin thì mới submit
                        document.getElementById("checkoutForm").submit();
                    }
                });
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
    </body>
</html>


