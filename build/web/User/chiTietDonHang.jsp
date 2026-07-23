<%-- 
    Document   : chiTietDonHang
    Updated    : Fix CSS & Null Safety
--%>
<%@page import="Models.*, java.util.*, java.text.NumberFormat, java.util.Locale, java.text.SimpleDateFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết đơn hàng - WebPhone</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <style>
            body {
                background: #f7f9fb;
                font-family: "Segoe UI", Arial, sans-serif;
                color: #333;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
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

            /* ===== MAIN ===== */
            main {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                padding: 40px 20px;
            }
            .account-layout {
                display: flex;
                width: 100%;
                max-width: 1100px;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.1);
                overflow: hidden;
            }
            /* Sidebar */
            .sidebar {
                width: 250px;
                background: #f0f0f0;
                border-right: 1px solid #e0e0e0;
                padding: 25px 15px;
            }
            .sidebar h5 {
                font-weight: 700;
                margin-bottom: 20px;
                text-align: center;
            }
            .sidebar a {
                display: block;
                color: #333;
                text-decoration: none;
                padding: 10px 15px;
                border-radius: 8px;
                margin-bottom: 8px;
                transition: all 0.25s;
            }
            .sidebar a:hover, .sidebar a.active {
                background: #ffd900;
                color: #000;
                font-weight: 600;
            }

            /* Content */
            .content {
                flex: 1;
                padding: 30px 40px;
            }
            .content h4 {
                font-weight: 700;
                margin-bottom: 25px;
                color: #222;
            }
            .order-info-card {
                background: #f8f9fa;
                padding: 15px;
                border-radius: 8px;
                border: 1px solid #e9ecef;
                height: 100%;
            }
            .product-table img {
                width: 80px;
                height: 80px;
                object-fit: contain;
            }

            /* --- BỔ SUNG CSS CÒN THIẾU --- */
            .totals-section {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                border: 1px solid #e9ecef;
                margin-top: 20px;
            }

            /* ===== FOOTER ===== */
            footer {
                background: #f5f5f5;
                color: #333;
                padding: 40px 0 20px;
            }
            footer a {
                color: #555;
                text-decoration: none;
            }
            footer a:hover {
                color: #000;
            }
        </style>
    </head>

    <body>
        <%
            // 1. Kiểm tra đăng nhập
            NguoiDung nd = (NguoiDung) session.getAttribute("user");
            boolean loggedIn = (nd != null && nd.getMaND() > 0);
            if (nd == null) {
                response.sendRedirect(request.getContextPath() + "/loginUs");
                return;
            }

            // 2. Lấy dữ liệu từ Attribute
            DonHang dh = (DonHang) request.getAttribute("donHang");
            List<Map<String, Object>> listChiTiet = (List<Map<String, Object>>) request.getAttribute("listChiTiet");

            if (dh == null || listChiTiet == null) {
                session.setAttribute("message", "Không tìm thấy dữ liệu đơn hàng!");
                session.setAttribute("type", "error");
                response.sendRedirect(request.getContextPath() + "/lichSuDonHangUs");
                return;
            }

            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            int cartSize = (request.getAttribute("cartSize") != null) ? (int) request.getAttribute("cartSize") : 0;

            // --- LOGIC KIỂM TRA HẠN ĐÁNH GIÁ (15 NGÀY) ---
            boolean isReviewTime = false;
            if ("Hoàn tất".equals(dh.getTrangThai()) && dh.getNgayHoantat() != null) {
                long millisInDay = 24 * 60 * 60 * 1000;
                long diff = Math.abs(new java.util.Date().getTime() - dh.getNgayHoantat().getTime());
                if ((diff / millisInDay) <= 15) {
                    isReviewTime = true;
                }
            }
        %>

        <!-- HEADER -->
        <header>
            <div class="d-flex align-items-center gap-2">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" class="logo">
                <span class="brand-name">WEBPHONE</span>
            </div>
            <nav class="d-flex align-items-center gap-4">
                <a href="<%= request.getContextPath()%>/indexKhachHang" class="nav-icon-link" title="Trang chủ"><i class="fas fa-home"></i></a>
                <a href="<%= request.getContextPath()%>/gioHang" class="nav-icon-link position-relative" title="Giỏ hàng">
                    <i class="fas fa-shopping-cart"></i>
                    <% if (cartSize > 0) {%>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"><%= cartSize%></span>
                    <% } %>
                </a>
                <% if (loggedIn) {%>
                <div class="user-profile-link d-flex align-items-center">
                    <a href="<%= request.getContextPath()%>/infomationUs" class="d-flex align-items-center gap-2 text-decoration-none">
                        <i class="fas fa-user-circle"></i> <span><%= nd.getTenND()%></span>
                    </a>
                </div>
                <% } else {%>
                <a href="<%= request.getContextPath()%>/loginUs" class="btn btn-dark btn-sm">Đăng nhập</a>
                <% }%>
            </nav>
        </header>

        <!-- MAIN -->
        <main>
            <div class="account-layout">
                <!-- SIDEBAR -->
                <div class="sidebar">
                    <h5><i class="fas fa-user-circle me-2 text-primary"></i>Tài khoản</h5>
                    <a href="<%=request.getContextPath()%>/infomationUs"><i class="fas fa-id-card me-2"></i> Thông tin cá nhân</a>
                    <a href="<%=request.getContextPath()%>/changePasswordUs"><i class="fas fa-lock me-2"></i> Đổi mật khẩu</a>
                    <a href="<%=request.getContextPath()%>/lichSuGiaoDichUs"><i class="fas fa-shopping-cart me-2"></i> Lịch sử Giao dịch</a>
                    <a href="<%=request.getContextPath()%>/lichSuDonHangUs" class="active"><i class="fas fa-clock me-2"></i> Lịch sử đơn hàng</a>
                    <a href="<%=request.getContextPath()%>/logoutUs"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
                </div>

                <!-- CONTENT -->
                <div class="content">
                    <div class="d-flex align-items-center mb-4">
                        <a href="<%=request.getContextPath()%>/lichSuDonHangUs" class="btn btn-light me-3 shadow-sm"><i class="fas fa-arrow-left"></i></a>
                        <h4 class="mb-0">Chi tiết Đơn hàng <span class="text-danger">#<%= dh.getMaDH()%></span></h4>
                    </div>

                    <div class="row mb-4">
                        <div class="col-md-4">
                            <div class="order-info-card">
                                <h5 class="text-primary"><i class="fas fa-info-circle me-1"></i> Trạng thái</h5>
                                <p><strong>Trạng thái:</strong> <span class="badge bg-info text-dark"><%= dh.getTrangThai()%></span></p>
                                <p><strong>Thanh toán:</strong> <%= "Đã thanh toán".equals(dh.getTrangThaiTT()) ? "<span class='text-success fw-bold'>Đã thanh toán</span>" : "<span class='text-warning fw-bold'>Chưa thanh toán</span>"%></p>
                                <p class="mb-0"><strong>Ngày đặt:</strong> <%= sdf.format(dh.getNgayDH())%></p>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="order-info-card">
                                <h5 class="text-primary"><i class="fas fa-map-marker-alt me-1"></i> Giao tới</h5>
                                <p><strong>Người nhận:</strong> <%= nd.getTenND()%></p>
                                <p><strong>Số điện thoại:</strong> <%= nd.getSdt()%></p>
                                <p class="mb-0"><strong>Địa chỉ:</strong> <%= dh.getDiaChigiao()%></p>
                            </div>
                        </div>
                    </div>

                    <!-- Danh sách sản phẩm -->
                    <h5 class="mb-3 fw-bold">Các sản phẩm trong đơn hàng</h5>
                    <table class="table table-bordered align-middle product-table">
                        <thead class="table-light">
                            <tr>
                                <th colspan="2" class="text-center">Sản phẩm</th>
                                <th class="text-center">Đơn giá</th>
                                <th class="text-center">SL</th>
                                <th class="text-end">Tạm tính</th>
                                    <% if ("Hoàn tất".equals(dh.getTrangThai())) { %>
                                <th class="text-center" style="width: 130px;">Đánh giá</th>
                                    <% } %>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Map<String, Object> item : listChiTiet) {
                                    DienThoai dt = (DienThoai) item.get("dienthoai");
                                    BienTheDienThoai bt = (BienTheDienThoai) item.get("bienthe");
                                    ChiTietHoaDon cthd = (ChiTietHoaDon) item.get("chitietdonhang");

                                    // Cách lấy an toàn để tránh NullPointer
                                    boolean daDanhGia = Boolean.TRUE.equals(item.get("daDanhGia"));
                            %>
                            <tr>
                                <td class="text-center">
                                    <img src="<%= request.getContextPath()%>/<%= dt.getAnh()%>" alt="sp">
                                </td>
                                <td>
                                    <div class="fw-semibold"><%= dt.getTenDT()%></div>
                                    <small class="text-muted"><%= bt.getMauSac()%> | <%= bt.getRAM()%> / <%= bt.getROM()%></small>
                                </td>
                                <td class="text-center"><%= nf.format(cthd.getGia())%> ₫</td>
                                <td class="text-center"><%= cthd.getSoLuong()%></td>
                                <td class="text-end fw-semibold"><%= nf.format(cthd.getThanhTien())%> ₫</td>

                                <%-- Cột nút đánh giá --%>
                                <% if ("Hoàn tất".equals(dh.getTrangThai())) { %>
                                <td class="text-center">
                                    <% if (isReviewTime) { %>
                                    <% if (daDanhGia) { %>
                                    <span class="badge bg-success p-2"><i class="fas fa-check"></i> Đã đánh giá</span>
                                    <% } else {%>
                                    <button type="button" class="btn btn-sm btn-outline-warning text-dark fw-bold" 
                                            data-bs-toggle="modal" 
                                            data-bs-target="#reviewModal"
                                            data-madt="<%= dt.getMaDT()%>"
                                            data-tendt="<%= dt.getTenDT()%>"
                                            data-madh="<%= dh.getMaDH()%>">
                                        <i class="far fa-star"></i> Đánh giá
                                    </button>
                                    <% } %>
                                    <% } else { %>
                                    <span class="badge bg-secondary">Hết hạn</span>
                                    <% } %>
                                </td>
                                <% } %>
                            </tr>
                            <% }%>
                        </tbody>
                    </table>

                    <!-- Phần tổng cộng -->
                    <div class="row justify-content-end">
                        <div class="col-md-5">
                            <div class="totals-section">
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Tạm tính:</span>
                                    <span><%= nf.format(dh.getTongTien())%> ₫</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Phí vận chuyển:</span>
                                    <span>0 ₫</span>
                                </div>
                                <hr>
                                <div class="d-flex justify-content-between fw-bold fs-5">
                                    <span>Tổng cộng:</span>
                                    <span class="text-danger"><%= nf.format(dh.getTongTien())%> ₫</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- MODAL ĐÁNH GIÁ -->
        <div class="modal fade" id="reviewModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="<%= request.getContextPath()%>/themDanhGia" method="POST">
                        <div class="modal-header bg-warning">
                            <h5 class="modal-title fw-bold">Đánh giá sản phẩm</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Hidden inputs -->
                            <input type="hidden" name="maDH" id="reviewMaDH">
                            <input type="hidden" name="maDT" id="reviewMaDT">

                            <p class="fw-bold text-center fs-5 text-primary" id="productNameDisplay"></p>

                            <div class="mb-3 text-center">
                                <label class="form-label fw-semibold">Bạn chấm mấy sao?</label>
                                <div class="d-flex justify-content-center">
                                    <select name="soSao" class="form-select w-50 text-center fw-bold text-warning" style="font-size: 1.1em;">
                                        <option value="5">⭐⭐⭐⭐⭐ (Tuyệt)</option>
                                        <option value="4">⭐⭐⭐⭐ (Tốt)</option>
                                        <option value="3">⭐⭐⭐ (Bình thường)</option>
                                        <option value="2">⭐⭐ (Tệ)</option>
                                        <option value="1">⭐ (Rất tệ)</option>
                                    </select>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Nhận xét của bạn:</label>
                                <textarea class="form-control" name="noiDung" rows="4" placeholder="Chất lượng sản phẩm, thời gian giao hàng..."></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-primary fw-bold">Gửi đánh giá</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>                            

        <!-- FOOTER -->
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

        <!-- XỬ LÝ SWEETALERT (FLASH MESSAGE) -->
        <%
            String msg = (String) session.getAttribute("message");
            String type = (String) session.getAttribute("type");

            // Xóa session ngay sau khi lấy
            if (msg != null) {
                session.removeAttribute("message");
                session.removeAttribute("type");
            }
        %>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            // JS xử lý Modal gán dữ liệu động
            var reviewModal = document.getElementById('reviewModal');
            reviewModal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget;
                var maDT = button.getAttribute('data-madt');
                var maDH = button.getAttribute('data-madh');
                var tenDT = button.getAttribute('data-tendt');

                reviewModal.querySelector('#reviewMaDT').value = maDT;
                reviewModal.querySelector('#reviewMaDH').value = maDH;
                reviewModal.querySelector('#productNameDisplay').textContent = tenDT;
            });

            // Hiển thị thông báo nếu có
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