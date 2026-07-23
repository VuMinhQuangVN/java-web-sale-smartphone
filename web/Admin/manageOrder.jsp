<%-- 
    Document   : manageOrder
    Created on : Oct 25, 2025, 11:51:21 AM
    Author     : ACER
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="Models.DonHang"%>
<%@page import="java.util.List"%>
<%@page import="Models.NguoiDung"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Admin Dashboard</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <link href="<%= request.getContextPath()%>/Admin/adminStyles.css" rel="stylesheet">
    </head>

    <body>
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


        <!-- LAYOUT -->
        <div class="admin-container">
            <!-- SIDEBAR -->
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

            <!-- CONTENT -->
            <main class="content">
                <!-- ==================== KHU VỰC TÌM KIẾM VÀ LỌC ==================== -->
                <h4 class="mb-4">Quản lý Đơn hàng</h4>

                <%
                    // Lấy các tham số lọc hiện tại từ request
                    String keyword = (String) request.getAttribute("keyword");
                    String trangThai = (String) request.getAttribute("trangThai");
                    String fromDate = (String) request.getAttribute("fromDate");
                    String toDate = (String) request.getAttribute("toDate");

                    if (keyword == null) {
                        keyword = "";
                    }
                    if (trangThai == null) {
                        trangThai = "Tất cả";
                    }
                    if (fromDate == null) {
                        fromDate = "";
                    }
                    if (toDate == null)
                        toDate = "";
                %>

                <!-- FORM LỌC NÂNG CẤP -->
                <form action="<%= request.getContextPath()%>/manageOrderAd" method="GET" class="mb-4 p-3 border rounded bg-light shadow-sm">
                    <div class="row g-3">
                        <!-- Hàng 1: Từ khóa & Trạng thái -->
                        <div class="col-md-4">
                            <label class="form-label fw-bold">Tìm kiếm</label>
                            <input type="text" class="form-control" id="keywordInput" name="keyword" 
                                   placeholder="Mã ĐH, tên, SĐT..." value="<%= keyword%>">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Trạng thái</label>
                            <select id="statusInput" name="trangThai" class="form-select">
                                <option value="Tất cả" <%= "Tất cả".equals(trangThai) ? "selected" : ""%>>Tất cả</option>
                                <option value="Chờ xác nhận" <%= "Chờ xác nhận".equals(trangThai) ? "selected" : ""%>>Chờ xác nhận</option>
                                <option value="Đang giao" <%= "Đang giao".equals(trangThai) ? "selected" : ""%>>Đang giao</option>
                                <option value="Đã giao" <%= "Đã giao".equals(trangThai) ? "selected" : ""%>>Đã giao</option>
                                <option value="Hoàn tất" <%= "Hoàn tất".equals(trangThai) ? "selected" : ""%>>Hoàn tất</option>
                                <option value="Hủy" <%= "Hủy".equals(trangThai) ? "selected" : ""%>>Đã hủy</option>
                            </select>
                        </div>

                        <!-- Hàng 2: Từ ngày - Đến ngày -->
                        <div class="col-md-2">
                            <label class="form-label fw-bold">Từ ngày</label>
                            <input type="date" id="fromDateInput" name="fromDate" class="form-control" value="<%= fromDate%>">
                        </div>
                        <div class="col-md-2">
                            <label class="form-label fw-bold">Đến ngày</label>
                            <input type="date" id="toDateInput" name="toDate" class="form-control" value="<%= toDate%>">
                        </div>

                        <!-- Hàng 3: Nút bấm -->
                        <div class="col-md-1 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100" title="Lọc dữ liệu"><i class="fas fa-filter"></i></button>
                        </div>
                    </div>

                    <!-- Dòng nút chức năng phụ -->
                    <div class="mt-3 d-flex justify-content-between">
                        <a href="<%= request.getContextPath()%>/manageOrderAd" class="btn btn-outline-secondary btn-sm">
                            <i class="fas fa-sync-alt"></i> Xóa bộ lọc
                        </a>

                        <!-- NÚT XUẤT EXCEL THẦN THÁNH -->
                        <button type="button" onclick="exportExcel()" class="btn btn-success">
                            <i class="fas fa-file-excel"></i> Xuất Excel (Theo lọc)
                        </button>
                    </div>
                </form>

                <!-- ==================== CÁC TAB TRẠNG THÁI (dùng như bộ lọc nhanh) ==================== -->
                <!-- Sửa lại các tab -->
                <ul class="nav nav-tabs mb-3">
                    <li class="nav-item">
                        <a class="nav-link <%= "Tất cả".equals(trangThai) ? "active" : ""%>" 
                           href="<%= request.getContextPath()%>/manageOrderAd?trangThai=Tất cả&keyword=<%= keyword%>">Tất cả</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= "Chờ xác nhận".equals(trangThai) ? "active" : ""%>" 
                           href="<%= request.getContextPath()%>/manageOrderAd?trangThai=Chờ xác nhận&keyword=<%= keyword%>">Chờ xác nhận <span class="badge bg-danger">...</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= "Đang giao".equals(trangThai) ? "active" : ""%>" 
                           href="<%= request.getContextPath()%>/manageOrderAd?trangThai=Đang giao&keyword=<%= keyword%>">Đang giao</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <%= "Hoàn tất".equals(trangThai) ? "active" : ""%>" 
                           href="<%= request.getContextPath()%>/manageOrderAd?trangThai=Hoàn tất&keyword=<%= keyword%>">Hoàn tất</a>
                    </li>
                </ul>

                <!-- ==================== KHU VỰC HÀNH ĐỘNG HÀNG LOẠT (sẽ được JS điều khiển) ==================== -->
                <div id="bulk-action-bar" class="alert alert-info d-none mb-3 d-flex justify-content-between align-items-center">
                    <div>
                        <span id="selected-count">0</span> đơn hàng đã được chọn.
                    </div>
                    <div id="bulk-action-buttons">
                        <%-- Các nút này sẽ được JS điều khiển --%>
                        <button class="btn btn-success btn-sm d-none" data-action="confirm_selected">Xác nhận</button>
                        <!--                        <button class="btn btn-primary btn-sm d-none" data-action="ship_selected">Giao hàng</button>-->
                        <button class="btn btn-info btn-sm d-none" data-action="deliver_selected">Đã giao</button>
                        <!--                        <button class="btn btn-danger btn-sm d-none" data-action="cancel_selected">Hủy</button>-->
                    </div>
                </div>

                <!-- ==================== BẢNG LIỆT KÊ ĐƠN HÀNG ==================== -->
                <form id="bulk-action-form" action="<%= request.getContextPath()%>/manageOrderAd" method="POST">
                    <input type="hidden" name="action" id="bulk-action-input">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th><input class="form-check-input" type="checkbox" id="select-all"></th>
                                <th>Mã ĐH</th>
                                <th>Khách hàng</th>
                                <th>Ngày đặt</th>
                                <th class="text-end">Tổng tiền</th>
                                <th>Trạng thái</th>
                                <th class="text-center">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<DonHang> listDonHang = (List<DonHang>) request.getAttribute("listDonHang");
                                NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                                if (listDonHang != null && !listDonHang.isEmpty()) {
                                    for (DonHang dh : listDonHang) {
                            %>
                            <%-- SỬA 1: Di chuyển data-status từ <td> lên <tr> --%>
                            <tr data-status="<%= dh.getTrangThai()%>">
                                <td>
                                    <%-- SỬA 2: Sửa "Đã hủy" thành "Hủy" cho khớp với CSDL --%>
                                    <% if ("Hoàn tất".equals(dh.getTrangThai()) || "Hủy".equals(dh.getTrangThai()) || "Đã giao".equals(dh.getTrangThai())) { %>
                                    <input class="form-check-input" type="checkbox" disabled title="Không thể chọn đơn hàng ở trạng thái này">
                                    <% } else {%>
                                    <input class="form-check-input order-checkbox" type="checkbox" name="orderIds" value="<%= dh.getMaDH()%>">
                                    <% }%>
                                </td>

                                <td><a href="<%= request.getContextPath()%>/chiTietDonHangAdmin?id=<%= dh.getMaDH()%>" class="fw-bold text-decoration-none">#<%= dh.getMaDH()%></a></td>

                                <td>
                                    <div><%= dh.getTenND()%></div>
                                </td>

                                <td><%= sdf.format(dh.getNgayDH())%></td>

                                <td class="text-end fw-bold text-danger"><%= nf.format(dh.getTongTien())%> ₫</td>

                                <td>
                                    <%
                                        String statusClass = "bg-secondary"; // Mặc định
                                        if ("Chờ xác nhận".equals(dh.getTrangThai()))
                                            statusClass = "bg-warning text-dark";
                                        else if ("Đang giao".equals(dh.getTrangThai()))
                                            statusClass = "bg-primary";
                                        else if ("Đã giao".equals(dh.getTrangThai()))
                                            statusClass = "bg-info";
                                        else if ("Hoàn tất".equals(dh.getTrangThai()))
                                            statusClass = "bg-success";
                                        else if ("Hủy".equals(dh.getTrangThai()))
                                            statusClass = "bg-danger";
                                    %>
                                    <span class="badge <%= statusClass%>"><%= dh.getTrangThai()%></span>
                                </td>

                                <td class="text-center">
                                    <% if ("Chờ xác nhận".equals(dh.getTrangThai())) {%>
                                    <a href="#" class="btn btn-success btn-sm action-btn" data-action="confirm_order" data-id="<%= dh.getMaDH()%>" title="Xác nhận & Giao hàng"><i class="fas fa-check"></i></a>
                                    <a href="#" class="btn btn-danger btn-sm action-btn" data-action="cancel_order" data-id="<%= dh.getMaDH()%>" title="Hủy đơn hàng"><i class="fas fa-times"></i></a>
                                        <% } else if ("Đang giao".equals(dh.getTrangThai())) {%>
                                    <a href="#" class="btn btn-info btn-sm action-btn" data-action="deliver_order" data-id="<%= dh.getMaDH()%>" title="Xác nhận đã giao thành công"><i class="fas fa-user-check"></i></a>
                                        <% } else {%>
                                    <a href="<%= request.getContextPath()%>/chiTietDonHangAd?id=<%= dh.getMaDH()%>" class="btn btn-outline-secondary btn-sm" title="Xem chi tiết"><i class="fas fa-eye"></i></a>
                                        <% } %>
                                </td>
                            </tr>
                            <%
                                } // end for
                            %>

                            <%
                            } else {
                            %>
                            <tr>
                                <td colspan="7" class="text-center p-5">
                                    <i class="fas fa-search fs-2 text-muted"></i>
                                    <h5 class="mt-3 text-muted">Không tìm thấy đơn hàng nào phù hợp.</h5>
                                </td>
                            </tr>
                            <%
                                } // end else
                            %>

                        </tbody>
                    </table>
                    <ul class="pagination justify-content-center">
                        <%
                            int currentPage = (int) request.getAttribute("currentPage");
                            int totalPages = (int) request.getAttribute("totalPages");

                            // Tạo base URL chứa đủ tham số
                            String baseUrl = request.getContextPath() + "/manageOrderAd?";
                            if (!keyword.isEmpty()) {
                                baseUrl += "keyword=" + keyword + "&";
                            }
                            if (!"Tất cả".equals(trangThai)) {
                                baseUrl += "trangThai=" + trangThai + "&";
                            }
                            if (!fromDate.isEmpty()) {
                                baseUrl += "fromDate=" + fromDate + "&";
                            }
                            if (!toDate.isEmpty())
                                baseUrl += "toDate=" + toDate + "&";
                        %>
                        <!-- ... Logic Loop Page giữ nguyên, chỉ thay href bằng baseUrl ... -->
                        <% for (int i = 1; i <= totalPages; i++) {%>
                        <li class="page-item <%= (i == currentPage ? "active" : "")%>">
                            <a class="page-link" href="<%= baseUrl%>page=<%= i%>"><%= i%></a>
                        </li>
                        <% }%>
                    </ul>
                </form>
            </main>
        </div>

        <!-- FOOTER -->
        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
        <%
            String msg = (String) request.getAttribute("message");
            String type = (String) request.getAttribute("type");
        %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // === KHAI BÁO CÁC BIẾN ===
                const selectAllCheckbox = document.getElementById('select-all');
                const orderCheckboxes = document.querySelectorAll('.order-checkbox');
                const bulkActionBar = document.getElementById('bulk-action-bar');
                const selectedCountSpan = document.getElementById('selected-count');
                const bulkActionForm = document.getElementById('bulk-action-form');
                const bulkActionInput = document.getElementById('bulk-action-input');
                const bulkActionButtonsContainer = document.getElementById('bulk-action-buttons');
                const allBulkButtons = bulkActionButtonsContainer.querySelectorAll('button');
                const singleActionButtons = document.querySelectorAll('.action-btn');
                const allOrderRows = document.querySelectorAll('tbody tr[data-status]');

                // === HÀM CẬP NHẬT GIAO DIỆN HÀNH ĐỘNG HÀNG LOẠT ===
                function updateBulkActionBar() {
                    const selectedCheckboxes = document.querySelectorAll('.order-checkbox:checked');
                    const count = selectedCheckboxes.length;

                    // Cập nhật số lượng và ẩn/hiện thanh hành động
                    selectedCountSpan.textContent = count;
                    bulkActionBar.classList.toggle('d-none', count === 0);

                    // --- Logic "thông minh" ---
                    allBulkButtons.forEach(btn => btn.classList.add('d-none'));

                    // --- LOGIC VÔ HIỆU HÓA CHECKBOX ĐỘNG ---
                    // 1. Kích hoạt lại tất cả checkbox có thể chọn lúc đầu
                    document.querySelectorAll('.order-checkbox:disabled').forEach(cb => cb.disabled = false);

                    if (count > 0) {
                        // 2. Lấy trạng thái của checkbox ĐẦU TIÊN được chọn
                        const firstSelectedStatus = selectedCheckboxes[0].closest('tr').dataset.status;

                        // 3. Vô hiệu hóa tất cả các dòng có trạng thái KHÁC với trạng thái đầu tiên
                        allOrderRows.forEach(row => {
                            const checkbox = row.querySelector('.order-checkbox');
                            if (checkbox && row.dataset.status !== firstSelectedStatus) {
                                checkbox.disabled = true;
                            }
                        });

                        // 4. Hiển thị các nút phù hợp dựa trên trạng thái chung đó
                        if (firstSelectedStatus === 'Chờ xác nhận') {
                            bulkActionButtonsContainer.querySelector('[data-action="confirm_selected"]').classList.remove('d-none');
                            bulkActionButtonsContainer.querySelector('[data-action="cancel_selected"]').classList.remove('d-none');
                        } else if (firstSelectedStatus === 'Đang giao') {
                            bulkActionButtonsContainer.querySelector('[data-action="deliver_selected"]').classList.remove('d-none');
                        }
                        // Thêm các điều kiện khác ở đây nếu cần...

                    } else {
                        // Nếu không có checkbox nào được chọn, hãy đảm bảo tất cả các checkbox có thể chọn đều được kích hoạt
                        document.querySelectorAll('.order-checkbox').forEach(cb => {
                            const status = cb.closest('tr').dataset.status;
                            if (status !== 'Hoàn tất' && status !== 'Hủy') {
                                cb.disabled = false;
                            }
                        });
                    }

                    // Cập nhật trạng thái của checkbox "Chọn tất cả"
                    const allCheckableCheckboxes = document.querySelectorAll('.order-checkbox:not(:disabled)');
                    if (allCheckableCheckboxes.length > 0) {
                        selectAllCheckbox.checked = (count > 0 && count === allCheckableCheckboxes.length);
                        selectAllCheckbox.indeterminate = (count > 0 && count < allCheckableCheckboxes.length);
                    } else {
                        selectAllCheckbox.checked = false;
                        selectAllCheckbox.indeterminate = false;
                    }
                }

                // === GẮN CÁC SỰ KIỆN ===

                // Sự kiện cho checkbox "Chọn tất cả"
                if (selectAllCheckbox) {
                    selectAllCheckbox.addEventListener('change', function () {
                        // Chỉ chọn các checkbox không bị vô hiệu hóa
                        document.querySelectorAll('.order-checkbox:not(:disabled)').forEach(checkbox => {
                            checkbox.checked = this.checked;
                        });
                        updateBulkActionBar();
                    });
                }

                // Sự kiện cho từng checkbox của đơn hàng
                orderCheckboxes.forEach(checkbox => {
                    checkbox.addEventListener('change', updateBulkActionBar);
                });

                // Sự kiện cho các nút hành động hàng loạt (trong thanh màu xanh)
                allBulkButtons.forEach(button => {
                    button.addEventListener('click', function (e) {
                        e.preventDefault();
                        const action = this.dataset.action;
                        const selectedIdsCount = document.querySelectorAll('.order-checkbox:checked').length;
                        if (selectedIdsCount === 0) {
                            alert('Vui lòng chọn ít nhất một đơn hàng.');
                            return;
                        }
                        if (confirm('Bạn có chắc muốn thực hiện hành động này cho ' + selectedIdsCount + ' đơn hàng đã chọn không?')) {
                            bulkActionInput.value = action;
                            bulkActionForm.submit();
                        }
                    });
                });

                // Sự kiện cho các nút hành động riêng lẻ (trên mỗi dòng)
                if (singleActionButtons.length > 0) {
                    const singleActionForm = document.createElement('form');
                    singleActionForm.method = 'POST';
                    singleActionForm.action = '<%= request.getContextPath()%>/manageOrderAd';
                    singleActionForm.style.display = 'none';
                    document.body.appendChild(singleActionForm);

                    singleActionButtons.forEach(button => {
                        button.addEventListener('click', function (e) {
                            e.preventDefault();
                            const action = this.dataset.action;
                            const orderId = this.dataset.id;
                            const confirmationMessage = this.title || 'thực hiện hành động này';

                            if (confirm('Bạn có chắc muốn ' + confirmationMessage + ' cho đơn hàng #' + orderId + ' không?')) {
                                singleActionForm.innerHTML =
                                        '<input type="hidden" name="action" value="' + action + '">' +
                                        '<input type="hidden" name="orderId" value="' + orderId + '">';
                                singleActionForm.submit();
                            }
                        });
                    });
                }

                // Khởi tạo giao diện lần đầu khi trang tải
                updateBulkActionBar();
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

            function exportExcel() {
                // 1. Lấy giá trị hiện tại trong các ô input
                var k = document.getElementById("keywordInput").value;
                var s = document.getElementById("statusInput").value;
                var f = document.getElementById("fromDateInput").value;
                var t = document.getElementById("toDateInput").value;

                // 2. Tạo link
                var url = "ExportExcelControl?type=orders" +
                        "&keyword=" + encodeURIComponent(k) +
                        "&trangThai=" + encodeURIComponent(s) +
                        "&fromDate=" + encodeURIComponent(f) +
                        "&toDate=" + encodeURIComponent(t);

                // 3. Gọi tải về
                window.location.href = url;
            }
        </script>
    </body>
</html>