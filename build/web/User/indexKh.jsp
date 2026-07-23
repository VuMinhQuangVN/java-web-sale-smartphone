<%@page import="Models.Loai"%>
<%@page import="Models.NguoiDung"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.NumberFormat, java.util.*, Models.DienThoai, Models.BienTheDienThoai" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Trang chủ - WebPhone</title>
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


            /* ----- BANNER ----- */
            /*            .banner {
                            height: 340px;
                            overflow: hidden;
                            margin: 20px auto;
                        }
                        .banner img {
                            width: 100%;
                            height: 100%;
                            object-fit: cover;
                        }*/

            #bannerCarousel, .carousel-inner, .carousel-item {
                height: 400px;
            }

            .carousel-item img {
                height: 100%;
                object-fit: cover;
            }
            .nav-link.active {
                font-weight: bold;
                color: #0d6efd !important;
                border-bottom: 2px solid #0d6efd;
            }
            /* ----- PRODUCT CARD ----- */
            .product-card {
                border-radius: 12px;
                overflow: hidden;
                transition: all 0.3s ease;
            }
            .product-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 15px rgba(0,0,0,0.15);
            }
            .product-card img {
                width: 100%;
                height: 210px;
                object-fit: contain;
                background: #fff;
                padding: 10px;
            }
            .product-name {
                font-size: 15px;
                font-weight: 600;
                color: #333;
                min-height: 38px;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .product-price {
                color: #d32f2f;
                font-weight: bold;
                font-size: 16px;
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
        </style>
    </head>

    <!-- Modal chọn biến thể -->
    <div class="modal fade" id="variantModal" tabindex="-1" aria-labelledby="variantModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold" id="variantModalLabel">Chọn biến thể</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body">
                    <form id="variantForm">
                        <input type="hidden" name="maDT" id="maDTInput">

                        <div class="mb-3">
                            <label class="form-label">Tên điện thoại:</label>
                            <input type="text" id="tenDTInput" class="form-control" readonly>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Chọn biến thể:</label>
                            <select name="maBienThe" id="variantSelect" class="form-select" required>
                                <option value="">-- Đang tải biến thể... --</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="quantity" class="form-label fw-semibold">Số lượng</label>
                            <input type="number" id="quantity" name="soLuong" class="form-control" min="1" value="1" required>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-success">🛒 Thêm vào giỏ hàng</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <body>

        <%
            NguoiDung nd = (NguoiDung) request.getAttribute("nguoidung");
            boolean loggedIn = (nd != null && nd.getMaND() > 0);
            int cartSize = (int) request.getAttribute("cartSize");
        %>
        <!-- HEADER -->
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
                <div class="promo-text">🌟 Ưu đãi lớn - Giảm đến 50% cho phụ kiện!</div>
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

        <!-- ======================= BANNER CAROUSEL ======================= -->
        <div id="bannerCarousel" class="carousel slide" data-bs-ride="carousel">

            <!-- 1. Chỉ báo (Các dấu chấm tròn bên dưới) -->
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#bannerCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>

            <!-- 2. Nội dung các slide (Các ảnh banner) -->
            <div class="carousel-inner">
                <!-- Slide 1 (active: là slide hiển thị đầu tiên) -->
                <div class="carousel-item active" data-bs-interval="3000">
                    <img src="<%= request.getContextPath()%>/Image/image003-1677.jpg" class="d-block w-100" alt="Banner iPhone 17">
                </div>
                <!-- Slide 2 -->
                <div class="carousel-item" data-bs-interval="3000">
                    <img src="<%= request.getContextPath()%>/Image/6.jpg" class="d-block w-100" alt="Banner khuyến mãi Samsung">
                </div>
                <!-- Slide 3 -->
                <div class="carousel-item" data-bs-interval="3000">
                    <img src="<%= request.getContextPath()%>/Image/7.jpg" class="d-block w-100" alt="Banner phụ kiện">
                </div>
            </div>

            <!-- 3. Nút điều khiển (Mũi tên trái/phải) -->
            <button class="carousel-control-prev" type="button" data-bs-target="#bannerCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#bannerCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <!-- =================== KẾT THÚC BANNER CAROUSEL =================== -->

        <%
            // 1. Lấy các giá trị filter hiện tại từ URL (giữ nguyên)
            String currentHang = request.getParameter("hangSx");
            if (currentHang == null) {
                currentHang = "";
            }

            String currentKeyword = request.getParameter("keyword");
            if (currentKeyword == null) {
                currentKeyword = "";
            }

            String currentMaLoai = request.getParameter("maLoai");
            if (currentMaLoai == null) {
                currentMaLoai = "0";
            }

            // 2. Encode keyword để tránh lỗi với ký tự đặc biệt
            String encodedKeyword = java.net.URLEncoder.encode(currentKeyword, "UTF-8");
        %>

        <!-- NAVBAR -->
        <nav class="navbar navbar-expand-lg bg-white shadow-sm">
            <div class="container">
                <a class="navbar-brand fw-bold">CHỌN HÃNG:</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <%-- Link "Tất cả": Bỏ hangSx, giữ lại keyword và maLoai --%>
                            <a class="nav-link <%= "".equals(currentHang) ? "active" : ""%>" 
                               href="<%= request.getContextPath()%>/indexKhachHang?keyword=<%= encodedKeyword%>&maLoai=<%= currentMaLoai%>">
                                Tất cả
                            </a>
                        </li>
                        <li class="nav-item">
                            <%-- Link Hãng cụ thể: Set hangSx=Apple, giữ lại keyword và maLoai --%>
                            <a class="nav-link <%= "Apple".equals(currentHang) ? "active" : ""%>" 
                               href="<%= request.getContextPath()%>/indexKhachHang?hangSx=Apple&keyword=<%= encodedKeyword%>&maLoai=<%= currentMaLoai%>">
                                Apple
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link <%= "Samsung".equals(currentHang) ? "active" : ""%>" 
                               href="<%= request.getContextPath()%>/indexKhachHang?hangSx=Samsung&keyword=<%= encodedKeyword%>&maLoai=<%= currentMaLoai%>">
                                Samsung
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link <%= "Xiaomi".equals(currentHang) ? "active" : ""%>" 
                               href="<%= request.getContextPath()%>/indexKhachHang?hangSx=Xiaomi&keyword=<%= encodedKeyword%>&maLoai=<%= currentMaLoai%>">
                                Xiaomi
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link <%= "Oppo".equals(currentHang) ? "active" : ""%>" 
                               href="<%= request.getContextPath()%>/indexKhachHang?hangSx=Oppo&keyword=<%= encodedKeyword%>&maLoai=<%= currentMaLoai%>">
                                Oppo
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link <%= "Vivo".equals(currentHang) ? "active" : ""%>" 
                               href="<%= request.getContextPath()%>/indexKhachHang?hangSx=Vivo&keyword=<%= encodedKeyword%>&maLoai=<%= currentMaLoai%>">
                                Vivo
                            </a>
                        </li>

                    </ul>

                    <%
                        String selectedMaLoai = request.getParameter("maLoai");
                        if (selectedMaLoai == null) {
                            selectedMaLoai = "0";
                        }
                    %>

                    <form class="d-flex" method="get" action="indexKhachHang">

                        <input type="hidden" name="hangSx" value="<%= currentHang%>">
                        <%-- DROPDOWN TÌM THEO LOẠI MỚI --%>
                        <select name="maLoai" class="form-select me-2" style="width: 150px;">
                            <option value="0" <%= "0".equals(selectedMaLoai) ? "selected" : ""%>>Tất cả loại</option>
                            <%
                                List<Loai> listLoai = (List<Loai>) request.getAttribute("listLoai");
                                if (listLoai != null) {
                                    for (Loai l : listLoai) {
                            %>
                            <option value="<%= l.getMaLoai()%>" <%= String.valueOf(l.getMaLoai()).equals(selectedMaLoai) ? "selected" : ""%>><%= l.getTenLoai()%></option>
                            <%
                                    }
                                }
                            %>
                        </select>

                        <input class="form-control me-2" type="search" name="keyword" 
                               placeholder="Tìm theo tên..." value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>">

                        <button class="btn btn-outline-primary" type="submit">Tìm</button>
                    </form>
                </div>
            </div>
        </nav>

        <!-- =============== DANH SÁCH SẢN PHẨM =============== -->
        <%
            List<DienThoai> list = (List<DienThoai>) request.getAttribute("listDienThoai");
            Map<Integer, BienTheDienThoai> giaThapNhatMap = (Map<Integer, BienTheDienThoai>) request.getAttribute("giaThapNhatMap");
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        %>

        <div class="container mt-5">
            <h3 class="fw-bold text-center mb-4">📱 Danh sách sản phẩm</h3>

            <div class="row g-4">
                <%
                    if (list != null && !list.isEmpty()) {
                        for (DienThoai d : list) {
                            BienTheDienThoai btMin = giaThapNhatMap != null ? giaThapNhatMap.get(d.getMaDT()) : null;
                            String giaHienThi = (btMin != null)
                                    ? nf.format(btMin.getGia()) + " VNĐ"
                                    : "Liên hệ";
                %>
                <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6">
                    <div class="card product-card h-100 shadow-sm">
                        <img src="<%= request.getContextPath()%>/<%= d.getAnh()%>" alt="<%= d.getTenDT()%>">
                        <div class="card-body text-center d-flex flex-column justify-content-between">
                            <p class="product-name"><%= d.getTenDT()%></p>
                            <p class="product-price mb-2"><%= giaHienThi%></p>
                            <div>
                                <a href="<%= request.getContextPath()%>/chiTietSP?id=<%= d.getMaDT()%>" class="btn btn-primary btn-sm me-1">Xem chi tiết</a>
                                <% if (loggedIn) {%>
                                <button type="button" 
                                        class="btn btn-success btn-sm"
                                        data-bs-toggle="modal"
                                        data-bs-target="#variantModal"
                                        data-madt="<%=d.getMaDT()%>"
                                        data-tendt="<%=d.getTenDT()%>">
                                    🛒 Thêm
                                </button>
                                <% } else {%>
                                <a href="<%=request.getContextPath()%>/loginUs?redirect=indexKhachHang" class="btn btn-outline-secondary btn-sm">🛒 Thêm</a>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <div class="text-center text-muted py-5">Không có sản phẩm nào để hiển thị.</div>
                <% } %>
            </div>
        </div>

        <!-- =============== PHÂN TRANG =============== -->
        <%
            int currentPage = (request.getAttribute("currentPage") != null) ? (int) request.getAttribute("currentPage") : 1;
            int totalPages = (request.getAttribute("totalPages") != null) ? (int) request.getAttribute("totalPages") : 1;
        %>

        <nav aria-label="Page navigation" class="mt-5">
            <ul class="pagination justify-content-center">
                <li class="page-item <%= (currentPage <= 1) ? "disabled" : ""%>">
                    <a class="page-link" href="?page=<%= currentPage - 1%>">Trước</a>
                </li>

                <% for (int i = 1; i <= totalPages; i++) {%>
                <li class="page-item <%= (i == currentPage) ? "active" : ""%>">
                    <a class="page-link" href="?page=<%= i%>"><%= i%></a>
                </li>
                <% }%>

                <li class="page-item <%= (currentPage >= totalPages) ? "disabled" : ""%>">
                    <a class="page-link" href="?page=<%= currentPage + 1%>">Sau</a>
                </li>
            </ul>
        </nav>

        <!-- =============== FOOTER =============== -->
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

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const modal = document.getElementById('variantModal');
        const form = document.getElementById('variantForm');
        const variantSelect = document.getElementById('variantSelect');
        const qtyInput = document.getElementById('quantity');
        const maDTInput = document.getElementById('maDTInput');
        const tenDTInput = document.getElementById('tenDTInput');
        let data = [];

        // Khi mở modal -> tải biến thể bằng AJAX
        modal.addEventListener('show.bs.modal', async (event) => {
            const btn = event.relatedTarget;
            const maDT = btn.getAttribute('data-madt');
            const tenDT = btn.getAttribute('data-tendt');

            maDTInput.value = maDT;
            tenDTInput.value = tenDT;
            variantSelect.innerHTML = '<option>-- Đang tải biến thể... --</option>';

            try {
                const res = await fetch('<%=request.getContextPath()%>/getVariantsUs?maDT=' + maDT);
                data = await res.json();

                if (data.length > 0) {
                    variantSelect.innerHTML = data.map(bt =>
                        '<option value="' + bt.maBienthe + '">' +
                                bt.mauSac + ' | ' + bt.RAM + '/' + bt.ROM + ' | ' +
                                Number(bt.gia).toLocaleString('vi-VN') + ' VNĐ' +
                                '</option>'
                    ).join('');
                } else {
                    variantSelect.innerHTML = '<option value="">(Không có biến thể khả dụng)</option>';
                }
            } catch (err) {
                console.error(err);
                variantSelect.innerHTML = '<option value="">Lỗi tải biến thể</option>';
            }
        });

        // Khi chọn biến thể -> giới hạn số lượng
        variantSelect.addEventListener('change', () => {
            const selected = data.find(bt => bt.maBienthe === parseInt(variantSelect.value));
            if (selected) {
                qtyInput.max = selected.soLuong;
                qtyInput.value = 1;
            } else {
                qtyInput.removeAttribute('max');
            }
        });

        // 🧩 Khi submit -> gọi servlet addToCartUs bằng POST, nhận JSON
        form.addEventListener('submit', async (e) => {
            e.preventDefault();

            const maDT = maDTInput.value;
            const maBienThe = variantSelect.value;
            const soLuong = qtyInput.value;

            if (!maBienThe) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Vui lòng chọn biến thể!',
                    timer: 1500,
                    showConfirmButton: false
                });
                return;
            }

            try {
                const res = await fetch('<%=request.getContextPath()%>/addToCartUs', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'maDT=' + maDT + '&maBienThe=' + maBienThe + '&soLuong=' + soLuong
                });

                const json = await res.json();

                if (json.success) {
                    Swal.fire({
                        icon: 'success',
                        title: json.message || 'Đã thêm vào giỏ hàng!',
                        showConfirmButton: false,
                        timer: 1500
                    });

                    // Ẩn modal sau khi thêm thành công
                    const modalInstance = bootstrap.Modal.getInstance(modal);
                    modalInstance.hide();

                    // (tuỳ chọn) cập nhật bộ đếm giỏ hàng
                    if (json.cartSize !== undefined) {
                        const badge = document.querySelector(".fa-shopping-cart + .badge");
                        if (badge) {
                            badge.textContent = json.cartSize;
                        } else {
                            const iconLink = document.querySelector(".fa-shopping-cart").parentElement;
                            const span = document.createElement("span");
                            span.className = "position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger";
                            span.textContent = json.cartSize;
                            iconLink.appendChild(span);
                        }
                    }
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: json.message || 'Không thể thêm vào giỏ hàng!',
                        timer: 2000,
                        showConfirmButton: false
                    });
                }
            } catch (err) {
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi kết nối máy chủ!',
                    showConfirmButton: false,
                    timer: 2000
                });
            }
        });
    });
</script>


