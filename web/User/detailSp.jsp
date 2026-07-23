<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Models.NguoiDung"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="Models.BienTheDienThoai"%>
<%@page import="java.util.List"%>
<%@page import="Models.DienThoai"%>
<%@page import="Models.DanhGia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết sản phẩm</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <style>
            /* ===== HEADER ===== */
            .headercss {
                display: flex;
                align-items: center;
                justify-content: space-between;
                background: #ffd900;
                padding: 12px 32px;
                font-family: "Segoe UI", Arial, sans-serif;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                position: sticky;
                top: 0;
                z-index: 100;
            }

            .headercss .logo {
                height: 55px;
                width: auto;
            }

            .headercss .brand-name {
                font-size: 20px;
                font-weight: 700;
                color: #000;
                letter-spacing: 0.5px;
            }

            .hotline-box {
                font-size: 15px;
                line-height: 1.4;
            }

            .hotline-box .promo-text {
                font-weight: 600;
                color: #0056b3;
            }

            .hotline-box .small-text {
                color: #444;
                font-size: 13px;
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

            .product-img {
                width: 100%;
                max-height: 400px;
                object-fit: contain;
                border-radius: 8px;
                background: #fff;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .variant-card {
                cursor: pointer;
                border: 2px solid transparent;
                border-radius: 6px;
                /*padding: 8px;*/
                transition: all 0.2s ease;
                background: #fff;
            }
            .variant-card:hover {
                border-color: #0d6efd;
                background-color: #f8faff;
            }
            .variant-card.active {
                border-color: #0d6efd;
                background-color: #e8f0ff;
            }

            .variant-card.out-of-stock {
                cursor: not-allowed;
                background-color: #f8f9fa;
                opacity: 0.6;
            }
            .variant-card.out-of-stock:hover {
                border-color: transparent; /* Không đổi màu viền khi hover */
            }
            .price-display {
                font-size: 22px;
                font-weight: bold;
                color: #d32f2f;
            }
            .old-price {
                color: #888;
                text-decoration: line-through;
                font-size: 14px;
                margin-left: 10px;
            }
            .spec-table th {
                width: 150px;
                background: #f8f9fa;
            }
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

            .review-section {
                background: #fff;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.05);
                margin-top: 40px;
            }
            .rating-overview {
                background: #fffbea;
                border: 1px solid #ffe58f;
                padding: 20px;
                border-radius: 8px;
                text-align: center;
                margin-bottom: 30px;
            }
            .big-star {
                font-size: 32px;
                color: #ffc107;
                font-weight: bold;
            }
            .review-item {
                border-bottom: 1px solid #eee;
                padding: 15px 0;
            }
            .review-item:last-child {
                border-bottom: none;
            }
            .user-avatar {
                width: 40px;
                height: 40px;
                background: #ddd;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: bold;
                color: #555;
                margin-right: 15px;
            }
            .star-yellow {
                color: #ffc107;
            }
            .star-gray {
                color: #e4e5e9;
            }
        </style>
    </head>

    <body>
        <%
            DienThoai dt = (DienThoai) request.getAttribute("dienthoai");
            List<BienTheDienThoai> listBienThe = (List<BienTheDienThoai>) request.getAttribute("listBienThe");
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            NguoiDung nd = (NguoiDung) request.getAttribute("nguoidung");
            boolean loggedIn = (nd != null && nd.getMaND() > 0);
            int cartSize = (int) request.getAttribute("cartSize");
        %>
        <!-- Header -->
        <header class="headercss shadow-sm">
            <div class="d-flex align-items-center gap-2">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" alt="Logo" class="logo">
                <strong class="brand-name">WEBPHONE</strong>
            </div>

            <div class="text-center hotline-box">
                <div>📞 Gọi mua hàng: 
                    <a href="tel:1800-1060" class="text-danger text-decoration-none fw-bold">1800.1060</a> 
                    <span class="small-text">(Miễn phí)</span>
                </div>
                <div class="promo-text">🌟 Ưu đãi lớn - Giảm đến 50%!</div>
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


        <div class="container my-5">
            <div class="row g-4">
                <!-- ẢNH SẢN PHẨM -->
                <div class="col-md-5 text-center">
                    <img src="<%= request.getContextPath()%>/<%= dt.getAnh()%>" alt="Sản phẩm" class="product-img mb-3">
                    <div>
                        <small class="text-muted">*Hình ảnh chỉ mang tính minh họa</small>
                    </div>
                </div>

                <!-- THÔNG TIN SẢN PHẨM -->
                <div class="col-md-7">
                    <h3 class="fw-bold mb-2"><%= dt.getTenDT()%></h3>
                    <p class="text-muted mb-1">Hãng sản xuất: <%= dt.getHangSx()%></p>

                    <!-- Biến thể -->
                    <% if (listBienThe != null && !listBienThe.isEmpty()) { %>
                    <div class="mt-3">
                        <label class="fw-semibold d-block mb-2">Chọn biến thể:</label>
                        <div class="row row-cols-2 row-cols-md-3 g-2" id="variantContainer">
                            <% for (BienTheDienThoai bt : listBienThe) {%>
                            <div class="col">
                                <div class="variant-card text-center p-2 <%= (bt.getSoLuong() <= 0) ? "out-of-stock" : ""%>"
                                     data-mabienthe="<%= bt.getMaBienthe()%>"
                                     data-gia="<%= bt.getGia()%>"
                                     data-ram="<%= bt.getRAM()%>" 
                                     data-rom="<%= bt.getROM()%>"
                                     data-soluong="<%= bt.getSoLuong()%>">
                                    <small class="d-block fw-semibold"><%= bt.getMauSac()%></small>
                                    <small class="text-muted"><%= bt.getRAM()%> / <%= bt.getROM()%></small>
                                    <div class="text-danger fw-bold mt-1"><%= nf.format(bt.getGia())%> ₫</div>
                                    <%-- Hiển thị thông báo nếu hết hàng --%>
                                    <% if (bt.getSoLuong() <= 0) { %>
                                    <small class="text-danger d-block fw-bold" style="font-size: 12px;">Hết hàng</small>
                                    <% } %>
                                </div>
                            </div>
                            <% } %>
                        </div>
                    </div>
                    <% } %>

                    <!-- Giá -->
                    <div class="mt-4">
                        <span class="price-display" id="selected-price">
                            <%
                                if (listBienThe != null && !listBienThe.isEmpty()) {
                                    BienTheDienThoai first = listBienThe.get(0);
                                    out.print(nf.format(first.getGia()) + " VNĐ");
                                } else {
                                    out.print("Liên hệ");
                                }
                            %>
                        </span>
                        <span class="old-price">Giá niêm yết: 32.990.000₫</span>
                    </div>

                    <p class="mt-3 text-secondary"><%= dt.getMota() != null ? dt.getMota() : "Chưa có mô tả."%></p>

                    <!-- Nút hành động -->
                    <div class="mt-4">
                        <form id="cartForm" class="d-inline">
                            <input type="hidden" name="maDT" value="<%= dt.getMaDT()%>">
                            <input type="hidden" name="maBienThe" id="selectedVariantId">

                            <div class="d-flex align-items-center">
                                <input type="number" name="soLuong" value="1" min="1" class="form-control me-3" style="width:100px;">

                                <% if (loggedIn) {%>
                                <button type="button" id="btnAddCart" class="btn btn-success btn-lg">
                                    🛒 Thêm vào giỏ
                                </button>
                                <% } else {%>
                                <a href="<%=request.getContextPath()%>/loginUs?redirect=chiTietSP&id=<%=dt.getMaDT()%>" class="btn btn-outline-secondary btn-sm">🛒 Thêm</a>
                                <% } %>
                                <% if (loggedIn) {%>
                                <button type="button" id="btnBuyNow" class="btn btn-danger btn-lg ms-2">
                                    🔥 Mua ngay
                                </button>
                                <% } else {%>
                                <a href="<%=request.getContextPath()%>/loginUs?redirect=chiTietSP&id=<%=dt.getMaDT()%>" class="btn btn-outline-secondary btn-sm">🔥 Mua ngay</a>
                                <% }%>
                            </div>
                        </form>
                    </div>

                </div>
            </div>

            <!-- Thông số kỹ thuật -->
            <div class="mt-5">
                <h4 class="fw-bold mb-3">📋 Thông số kỹ thuật</h4>
                <table class="table table-bordered spec-table">
                    <tbody>
                        <tr><th>Màn hình</th><td><%= dt.getManHinh()%></td></tr>
                        <tr><th>Camera</th><td><%= dt.getCamera() != null ? dt.getCamera() : "Đang cập nhật"%></td></tr>
                        <tr><th>Chip</th><td><%= dt.getChip()%></td></tr>
                        <tr><th>RAM / Bộ nhớ</th>
                            <td id="ram-rom-display">
                                <% if (listBienThe != null && !listBienThe.isEmpty()) {%>
                                <%= listBienThe.get(0).getRAM()%> / <%= listBienThe.get(0).getROM()%>
                                <% } else { %>
                                <span>Đang cập nhật</span>
                                <% }%>
                            </td>
                        </tr>
                        <tr><th>Pin</th><td><%= dt.getPin()%></td></tr>
                    </tbody>
                </table>
            </div>
            <!-- ================= PHẦN ĐÁNH GIÁ SẢN PHẨM ================= -->
            <%
                List<DanhGia> listDG = (List<DanhGia>) request.getAttribute("listDanhGia");
                double tbSao = (request.getAttribute("trungBinhSao") != null) ? (Double) request.getAttribute("trungBinhSao") : 0;
                int totalReview = (request.getAttribute("totalReviews") != null) ? (Integer) request.getAttribute("totalReviews") : 0;

                // Lấy thông tin phân trang
                int currentPage = (request.getAttribute("currentPage") != null) ? (Integer) request.getAttribute("currentPage") : 1;
                int totalPages = (request.getAttribute("totalPages") != null) ? (Integer) request.getAttribute("totalPages") : 1;
            %>

            <div class="review-section" id="phan-danh-gia"> <!-- Thêm ID để khi chuyển trang nó nhảy xuống đây -->
                <h4 class="fw-bold mb-4">⭐ Đánh giá & Nhận xét (<%= totalReview%>)</h4>

                <!-- Tổng quan số sao (Giữ nguyên) -->
                <div class="rating-overview row align-items-center">
                    <div class="col-md-4 border-end">
                        <div class="big-star"><%= String.format("%.1f", tbSao)%>/5</div>
                        <div class="text-warning">
                            <%
                                int fullStars = (int) Math.round(tbSao);
                                for (int i = 1; i <= 5; i++) {
                                    if (i <= fullStars) { %><i class="fas fa-star"></i><% } else { %><i class="far fa-star"></i><% }
                                        }
                                %>
                        </div>
                        <div class="text-muted small mt-1">Dựa trên <%= totalReview%> nhận xét</div>
                    </div>
                    <div class="col-md-8 text-start ps-4">
                        <p class="mb-0 text-muted">Tất cả đánh giá đều đến từ khách hàng đã mua sản phẩm tại WebPhone.</p>
                        <% if (totalReview == 0) { %>
                        <p class="mt-2 text-primary">Chưa có đánh giá nào. Hãy mua hàng để là người đầu tiên đánh giá!</p>
                        <% } %>
                    </div>
                </div>

                <!-- Danh sách bình luận -->
                <div class="review-list">
                    <% if (listDG != null && !listDG.isEmpty()) {
                            for (DanhGia dg : listDG) {
                    %>
                    <div class="review-item d-flex">
                        <div class="user-avatar">
                            <%= (dg.getTenND() != null && !dg.getTenND().isEmpty()) ? dg.getTenND().substring(0, 1).toUpperCase() : "U"%>
                        </div>
                        <div class="flex-grow-1">
                            <div class="d-flex justify-content-between align-items-center">
                                <strong class="text-dark"><%= dg.getTenND()%></strong>
                                <span class="text-muted small"><i class="far fa-clock"></i> <%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dg.getNgayDanhGia())%></span>
                            </div>
                            <div class="mb-2 mt-1" style="font-size: 14px;">
                                <% for (int i = 1; i <= 5; i++) { %>
                                <% if (i <= dg.getSoSao()) { %>
                                <i class="fas fa-star star-yellow"></i>
                                <% } else { %>
                                <i class="fas fa-star star-gray"></i>
                                <% } %>
                                <% }%>
                                <span class="fw-bold ms-2 text-success" style="font-size: 12px;"><i class="fas fa-check-circle"></i> Đã mua hàng</span>
                            </div>
                            <p class="mb-0 text-secondary" style="white-space: pre-line;"><%= dg.getNoiDung()%></p>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>

                <!-- THANH PHÂN TRANG (Chỉ hiện nếu có trên 1 trang) -->
                <% if (totalPages > 1) {%>
                <div class="mt-4">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <!-- Nút Trước -->
                            <li class="page-item <%= (currentPage <= 1) ? "disabled" : ""%>">
                                <a class="page-link" href="?id=<%= dt.getMaDT()%>&page=<%= currentPage - 1%>#phan-danh-gia" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>

                            <!-- Các số trang -->
                            <% for (int i = 1; i <= totalPages; i++) {%>
                            <li class="page-item <%= (i == currentPage) ? "active" : ""%>">
                                <a class="page-link" href="?id=<%= dt.getMaDT()%>&page=<%= i%>#phan-danh-gia"><%= i%></a>
                            </li>
                            <% }%>

                            <!-- Nút Sau -->
                            <li class="page-item <%= (currentPage >= totalPages) ? "disabled" : ""%>">
                                <a class="page-link" href="?id=<%= dt.getMaDT()%>&page=<%= currentPage + 1%>#phan-danh-gia" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <% } %>

            </div>
            <!-- ================= HẾT PHẦN ĐÁNH GIÁ ================= -->

        </div>

        <!-- FOOTER -->
        <footer>
            <div class="container text-center">
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <h6>Về chúng tôi</h6>
                        <p>Web bán điện thoại & phụ kiện chính hãng, giá tốt nhất Việt Nam.</p>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Hỗ trợ khách hàng</h6>
                        <a href="#">Liên hệ</a><br>
                        <a href="#">Hướng dẫn mua hàng</a><br>
                        <a href="#">Câu hỏi thường gặp</a>
                    </div>
                    <div class="col-md-3 mb-3">
                        <h6>Chính sách</h6>
                        <a href="#">Bảo hành</a><br>
                        <a href="#">Đổi trả</a><br>
                        <a href="#">Bảo mật</a>
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

    </body>
</html>
<%
    String msg = (String) request.getAttribute("message");
    String type = (String) request.getAttribute("type");
%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const variantCards = document.querySelectorAll(".variant-card");
        const selectedPrice = document.getElementById("selected-price");
        const hiddenVariantId = document.getElementById("selectedVariantId");
        const ramRomDisplay = document.getElementById("ram-rom-display");
        const btnAddCart = document.getElementById("btnAddCart");
        const btnBuyNow = document.getElementById("btnBuyNow");
        const soLuongInput = document.querySelector('input[name="soLuong"]');

        // Hàm để cập nhật trạng thái các nút hành động (Thêm giỏ, Mua ngay)
        function updateActionButtons(quantity) {
            const stock = parseInt(quantity, 10);
            const loggedIn = <%= loggedIn%>; // Lấy trạng thái đăng nhập từ JSP

            if (!loggedIn)
                return; // Nếu chưa đăng nhập thì không cần làm gì

            if (stock > 0) {
                btnAddCart.disabled = false;
                btnBuyNow.disabled = false;
                btnAddCart.innerText = "🛒 Thêm vào giỏ";
                soLuongInput.max = stock; // Cập nhật số lượng tối đa có thể mua
            } else {
                btnAddCart.disabled = true;
                btnBuyNow.disabled = true;
                btnAddCart.innerText = "Hết hàng";
            }
        }

        // ========== CHỌN BIẾN THỂ ==========
        variantCards.forEach(card => {
            card.addEventListener("click", () => {
                // Nếu biến thể đã hết hàng, không cho phép chọn
                if (card.classList.contains('out-of-stock')) {
                    return;
                }

                variantCards.forEach(c => c.classList.remove("active"));
                card.classList.add("active");

                ramRomDisplay.innerText = card.dataset.ram + " / " + card.dataset.rom;
                const price = Number(card.dataset.gia).toLocaleString("vi-VN") + " VNĐ";
                selectedPrice.textContent = price;
                hiddenVariantId.value = card.dataset.mabienthe;

                // Cập nhật trạng thái các nút dựa trên số lượng tồn kho
                updateActionButtons(card.dataset.soluong);
            });
        });

        // ========== THIẾT LẬP TRẠNG THÁI BAN ĐẦU KHI TẢI TRANG ==========
        // Tìm biến thể đầu tiên còn hàng để chọn làm mặc định
        const firstAvailableVariant = document.querySelector(".variant-card:not(.out-of-stock)");

        if (firstAvailableVariant) {
            firstAvailableVariant.click(); // Tự động "nhấp" vào biến thể đầu tiên còn hàng
        } else {
            // Nếu tất cả biến thể đều hết hàng
            const firstVariant = variantCards[0];
            if (firstVariant) {
                // Hiển thị thông tin của biến thể đầu tiên nhưng vô hiệu hóa nút
                ramRomDisplay.innerText = firstVariant.dataset.ram + " / " + firstVariant.dataset.rom;
                selectedPrice.textContent = Number(firstVariant.dataset.gia).toLocaleString("vi-VN") + " VNĐ";
                hiddenVariantId.value = firstVariant.dataset.mabienthe;
                updateActionButtons(0); // Gọi với số lượng 0 để vô hiệu hóa nút
            }
        }

        // ========== NÚT THÊM VÀO GIỎ ==========
        if (btnAddCart) {
            btnAddCart.addEventListener("click", function () {
                const maDT = document.querySelector('input[name="maDT"]').value;
                const maBienThe = hiddenVariantId.value;
                const soLuong = soLuongInput.value;

                if (!maBienThe) {
                    Swal.fire({icon: 'warning', title: 'Vui lòng chọn một biến thể!', timer: 2000, showConfirmButton: false});
                    return;
                }

                fetch('<%= request.getContextPath()%>/addToCartUs', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'maDT=' + maDT + '&maBienThe=' + maBienThe + '&soLuong=' + soLuong
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.success) {
                                Swal.fire({icon: 'success', title: data.message || 'Đã thêm vào giỏ hàng!', showConfirmButton: false, timer: 1500});
                                if (data.cartSize !== undefined) {
                                    const badge = document.querySelector(".fa-shopping-cart + .badge");
                                    if (badge) {
                                        badge.textContent = data.cartSize; // Cập nhật số lượng
                                    } else {
                                        const iconLink = document.querySelector(".fa-shopping-cart").parentElement;
                                        const span = document.createElement("span");
                                        span.className = "position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger";
                                        span.textContent = data.cartSize; // Thêm mới badge nếu chưa có
                                        iconLink.appendChild(span);
                                    }
                                }
                            } else {
                                Swal.fire({icon: 'error', title: data.message || 'Thêm vào giỏ hàng thất bại!', timer: 2000, showConfirmButton: false});
                            }
                        })
                        .catch(() => Swal.fire({icon: 'error', title: 'Lỗi kết nối máy chủ!', timer: 2000, showConfirmButton: false}));
            });
        }

        // ========== NÚT MUA NGAY ==========
        if (btnBuyNow) {
            btnBuyNow.addEventListener("click", function () {
                const maBienThe = hiddenVariantId.value;
                const soLuong = soLuongInput.value;
                const maDT = document.querySelector('input[name="maDT"]').value;

                // Kiểm tra nếu thiếu thông tin người dùng (số điện thoại và địa chỉ)
    <% if (nd.getSdt() == null || nd.getDiachi() == null || nd.getSdt().trim().equals("") || nd.getDiachi().trim().equals("")) {%>
                // Nếu thiếu thông tin, hiển thị thông báo và chuyển hướng người dùng đến trang cập nhật thông tin
                Swal.fire({
                    icon: 'warning',
                    title: 'Vui lòng cập nhật thông tin cá nhân trước khi mua!',
                    showConfirmButton: true,
                    confirmButtonText: 'Cập nhật thông tin',
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "<%= request.getContextPath()%>/infomationUs";
                    }
                });
                return; // Dừng lại nếu thiếu thông tin
    <% }%>

                if (!maBienThe) {
                    Swal.fire({icon: 'warning', title: 'Vui lòng chọn biến thể trước khi mua!', timer: 2000, showConfirmButton: false});
                    return;
                }

                // Chuyển hướng đến trang thanh toán
                window.location.href = '<%= request.getContextPath()%>/checkoutUs?maBienThe=' + maBienThe + '&soLuong=' + soLuong + '&maDT=' + maDT;
            });
        }
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

