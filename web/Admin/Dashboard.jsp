<%-- 
    Document   : Dashboard
    Created on : Oct 4, 2025, 2:06:36 PM
    Author     : Administrator
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
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
        <style>
            .stat-card {
                border: none;
                border-radius: 15px;
                color: #fff;
                position: relative;
                overflow: hidden;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .stat-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 6px 15px rgba(0,0,0,0.2);
            }
            .stat-icon {
                position: absolute;
                top: 10px;
                right: 20px;
                font-size: 2.5rem;
                opacity: 0.2;
            }
            .fade-in {
                opacity: 0;
                transform: translateY(20px);
                animation: fadeInUp 0.6s forwards;
            }
            @keyframes fadeInUp {
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            .small-product-list .list-group-item {
                padding: 8px 12px;
            }

            .small-product-list img {
                width: 40px;
                height: 40px;
                object-fit: cover;
            }

        </style>
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
                    <li><a href="<%= request.getContextPath()%>/DasboardAd" class="active"><i class="fas fa-chart-line"></i> Dashboard</a></li>
                    <li><a href="<%= request.getContextPath()%>/managerProductAd"><i class="fas fa-mobile-alt"></i> Quản lý sản phẩm</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageCategoryAd"><i class="fas fa-list"></i> Quản lý loại</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageOrderAd"><i class="fas fa-shopping-cart"></i> Quản lý đơn hàng</a></li>
                    <li><a href="<%= request.getContextPath()%>/manageUserAd"><i class="fas fa-users"></i> Quản lý người dùng</a></li>
                    <li><a href="<%= request.getContextPath()%>/DoanhThuAd"><i class="fas fa-coins"></i>Doanh Thu</a></li>
                    <li><a href="<%= request.getContextPath()%>/logoutAd"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a></li>
                </ul>
            </aside>

            <!-- CONTENT -->
            <main class="content">
                <h3 class="fw-bold mb-4">📊 Thống kê tổng quan</h3>

                <!-- Card hàng 1 -->
                <div class="row g-4">
                    <div class="col-md-4 fade-in" style="animation-delay:0.1s;">
                        <div class="card stat-card bg-primary p-4 shadow-sm">
                            <i class="fas fa-dollar-sign stat-icon"></i>
                            <h5 class="fw-semibold">Tổng doanh thu</h5>
                            <h2 class="fw-bold" data-count="<%= request.getAttribute("tongDoanhThu")%>">0</h2>
                        </div>
                    </div>

                    <div class="col-md-4 fade-in" style="animation-delay:0.2s;">
                        <div class="card stat-card bg-danger p-4 shadow-sm">
                            <i class="fas fa-undo-alt stat-icon"></i>
                            <h5 class="fw-semibold">Tổng hoàn tiền</h5>
                            <h2 class="fw-bold" data-count="<%= request.getAttribute("tongHoanTien")%>">0</h2>
                        </div>
                    </div>

                    <div class="col-md-4 fade-in" style="animation-delay:0.3s;">
                        <div class="card stat-card bg-success p-4 shadow-sm">
                            <i class="fas fa-check-circle stat-icon"></i>
                            <h5 class="fw-semibold">Đơn hàng hoàn tất</h5>
                            <h2 class="fw-bold" data-count="<%= request.getAttribute("soDonHoanTat")%>">0</h2>
                        </div>
                    </div>
                </div>

                <!-- Card hàng 2 -->
                <div class="row g-4 mt-3">
                    <div class="col-md-4 fade-in" style="animation-delay:0.4s;">
                        <div class="card stat-card bg-warning p-4 shadow-sm">
                            <i class="fas fa-truck stat-icon"></i>
                            <h5 class="fw-semibold">Đơn hàng đang giao</h5>
                            <h2 class="fw-bold" data-count="<%= request.getAttribute("soDonDangGiao")%>">0</h2>
                        </div>
                    </div>

                    <div class="col-md-4 fade-in" style="animation-delay:0.5s;">
                        <div class="card stat-card bg-secondary p-4 shadow-sm">
                            <i class="fas fa-users stat-icon"></i>
                            <h5 class="fw-semibold">Tổng khách hàng</h5>
                            <h2 class="fw-bold" data-count="<%= request.getAttribute("tongKhachHang")%>">0</h2>
                        </div>
                    </div>

                    <div class="col-md-4 fade-in" style="animation-delay:0.6s;">
                        <div class="card stat-card bg-dark p-4 shadow-sm">
                            <i class="fas fa-fire-alt stat-icon"></i>
                            <h5 class="fw-semibold">Sản phẩm bán chạy nhất</h5>
                            <h4 class="fw-bold mt-2 text-light">
                                <%= request.getAttribute("spBanChay")%>
                            </h4>
                        </div>
                    </div>
                </div>
                <!-- ================== BIỂU ĐỒ THỐNG KÊ ================== -->
                <hr class="my-5">
                <h4 class="fw-bold mb-4"><i class="fas fa-calendar-alt me-2 text-primary"></i>Số đơn hàng theo tháng</h4>

                <%
                    String msgDonThang = (String) request.getAttribute("msgDonThang");
                    List<Map<String, Object>> donTheoThang = (List<Map<String, Object>>) request.getAttribute("donTheoThang");

                    if (msgDonThang != null) {
                %>
                <div class="alert alert-warning text-center"><%= msgDonThang%></div>
                <%
                } else if (donTheoThang != null && !donTheoThang.isEmpty()) {
                %>
                <div class="card shadow-sm p-4">
                    <canvas id="orderByMonthChart" height="150"></canvas>
                </div>
                <%
                    }
                %>

                <hr class="my-5">
                <h4 class="fw-bold mb-4"><i class="fas fa-chart-bar me-2 text-info"></i>Phân tích doanh thu & đơn hàng</h4>

                <div class="card shadow-sm p-4 mb-4">
                    <div class="row">
                        <div class="col-md-6 text-center border-end">
                            <h6 class="text-primary mb-3"><i class="fas fa-chart-line me-2"></i>Doanh thu 7 ngày gần nhất</h6>
                            <canvas id="revenueChart" style="max-height:280px;"></canvas>
                        </div>
                        <div class="col-md-6 text-center">
                            <h6 class="text-success mb-3"><i class="fas fa-pie-chart me-2"></i>Tỷ lệ trạng thái đơn hàng</h6>
                            <canvas id="orderStatusChart" style="max-height:280px;"></canvas>
                        </div>
                    </div>
                </div>


                <!-- Top sản phẩm -->
                <hr class="my-5">
                <h4 class="fw-bold mb-4">
                    <i class="fas fa-fire-alt me-2 text-danger"></i>Top 5 sản phẩm bán chạy nhất
                </h4>

                <div class="row align-items-center">
                    <div class="col-md-6">
                        <canvas id="topProductChart" height="200"></canvas>
                    </div>
                    <div class="col-md-6">
                        <div class="list-group shadow-sm small-product-list">
                            <%
                                List<Map<String, Object>> topSP = (List<Map<String, Object>>) request.getAttribute("topSP");
                                if (topSP != null) {
                                    for (Map<String, Object> sp : topSP) {
                            %>
                            <div class="list-group-item d-flex align-items-center">
                                <img src="<%= request.getContextPath()%>/<%= sp.get("anh")%>" 
                                     alt="Ảnh" class="rounded me-3" style="width:50px;height:50px;object-fit:cover;">
                                <div>
                                    <h6 class="mb-0"><%= sp.get("tenDT")%></h6>
                                    <small class="text-muted">Đã bán: <%= sp.get("tongSoLuong")%> sản phẩm</small>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>
                </div>

                <hr class="my-5">
                <h4 class="fw-bold mb-4">
                    <i class="fas fa-calendar-alt me-2 text-primary"></i>Doanh thu theo tháng (Năm nay)
                </h4>

                <div class="card shadow-sm p-4 mb-4">
                    <canvas id="monthlyRevenueChart" height="120"></canvas>
                </div>

                <hr class="my-5">
                <h4 class="fw-bold mb-4"><i class="fas fa-user-tie me-2 text-warning"></i>Top 5 khách hàng chi tiêu nhiều nhất</h4>

                <%
                    String msgTopKH = (String) request.getAttribute("msgTopKH");
                    List<Map<String, Object>> topKH = (List<Map<String, Object>>) request.getAttribute("topKH");

                    if (msgTopKH != null) {
                %>
                <div class="alert alert-warning text-center"><%= msgTopKH%></div>
                <%
                } else if (topKH != null && !topKH.isEmpty()) {
                %>
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <canvas id="topCustomerChart" height="160"></canvas>
                    </div>
                    <div class="col-md-6">
                        <div class="list-group shadow-sm small-product-list">
                            <% for (Map<String, Object> kh : topKH) {%>
                            <div class="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="mb-0"><%= kh.get("tenKH")%></h6>
                                    <small class="text-muted">
                                        <%= kh.get("soDon")%> đơn - 
                                        <%= String.format("%,.0f", kh.get("tongChi"))%> ₫
                                    </small>
                                </div>
                                <i class="fas fa-crown text-warning"></i>
                            </div>
                            <% } %>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#reportModal">
                    <i class="fas fa-file-pdf"></i> Xuất Báo Cáo Tháng (PDF)
                </button>
            </main>
        </div>

        <!-- FOOTER -->
        <footer>
            © 2025 WebPhone Admin | <a href="#">Chính sách bảo mật</a> | <a href="#">Liên hệ hỗ trợ</a>
        </footer>
        <div class="modal fade" id="reportModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title"><i class="fas fa-file-alt"></i> Chọn kỳ báo cáo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="ExportPdfControl" method="GET" target="_blank"> <!-- target blank để mở tab mới tải -->
                        <div class="modal-body">
                            <input type="hidden" name="type" value="pdf_report">

                            <div class="mb-3">
                                <label class="form-label fw-bold">Tháng:</label>
                                <select name="month" class="form-select">
                                    <% for (int i = 1; i <= 12; i++) {
                                            int currentMonth = java.time.LocalDate.now().getMonthValue();
                                    %>
                                    <option value="<%= i%>" <%= i == currentMonth ? "selected" : ""%>>Tháng <%= i%></option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-bold">Năm:</label>
                                <select name="year" class="form-select">
                                    <option value="2024">2024</option>
                                    <option value="2025" selected>2025</option>
                                    <option value="2026">2026</option>
                                </select>
                            </div>

                            <div class="alert alert-info small">
                                <i class="fas fa-info-circle"></i> Báo cáo sẽ bao gồm Doanh thu, Top sản phẩm bán chạy và Khách hàng VIP.
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-danger">Xuất Báo Cáo</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const counters = document.querySelectorAll("[data-count]");
            counters.forEach(counter => {
                const target = parseFloat(counter.dataset.count || 0);
                const isMoney = target > 1000;
                let count = 0;
                const step = target / 100;

                const updateCount = () => {
                    count += step;
                    if (count < target) {
                        counter.textContent = isMoney
                                ? count.toLocaleString('vi-VN', {maximumFractionDigits: 0}) + " đ"
                                : Math.round(count);
                        requestAnimationFrame(updateCount);
                    } else {
                        counter.textContent = isMoney
                                ? target.toLocaleString('vi-VN', {maximumFractionDigits: 0}) + " đ"
                                : Math.round(target);
                    }
                };
                updateCount();
            });
            const labels = [
        <%
            List<Map<String, Object>> doanhThu7Ngay = (List<Map<String, Object>>) request.getAttribute("doanhThu7Ngay");
            if (doanhThu7Ngay != null) {
                for (int i = doanhThu7Ngay.size() - 1; i >= 0; i--) {
                    Map<String, Object> item = doanhThu7Ngay.get(i);
                    java.sql.Date ngay = (java.sql.Date) item.get("ngay");
        %> "<%= new java.text.SimpleDateFormat("dd/MM").format(ngay)%>",
        <% }
            } %>
            ];
            const data = [
        <%
            if (doanhThu7Ngay != null) {
                for (int i = doanhThu7Ngay.size() - 1; i >= 0; i--) {
                    Map<String, Object> item = doanhThu7Ngay.get(i);
                    double tong = (double) item.get("tong");
        %> <%= tong%>,
        <% }
            }%>
            ];

            new Chart(document.getElementById("revenueChart"), {
                type: 'bar', // có thể đổi thành 'line'
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Doanh thu (VNĐ)',
                            data: data,
                            backgroundColor: 'rgba(54, 162, 235, 0.6)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 2,
                            fill: true,
                            tension: 0.3
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function (value) {
                                    return value.toLocaleString('vi-VN') + ' đ';
                                }
                            }
                        }
                    },
                    plugins: {
                        legend: {display: false},
                        tooltip: {
                            callbacks: {
                                label: ctx => ctx.parsed.y.toLocaleString('vi-VN') + ' đ'
                            }
                        }
                    }
                }
            });
            const labels2 = [
        <%
            List<Map<String, Object>> thongKeTrangThai = (List<Map<String, Object>>) request.getAttribute("thongKeTrangThai");
            if (thongKeTrangThai != null) {
                for (Map<String, Object> item : thongKeTrangThai) {
        %> "<%= item.get("trangThai")%>",
        <% }
            } %>
            ];
            const data2 = [
        <%
            if (thongKeTrangThai != null) {
                for (Map<String, Object> item : thongKeTrangThai) {
        %> <%= item.get("soLuong")%>,
        <% }
            }%>
            ];

            new Chart(document.getElementById("orderStatusChart"), {
                type: 'pie',
                data: {
                    labels: labels2,
                    datasets: [{
                            data: data2,
                            backgroundColor: [
                                '#ffc107', // vàng - Chờ xác nhận
                                '#0d6efd', // xanh dương - Đang giao
                                '#17a2b8', // xanh nhạt - Đã giao
                                '#28a745', // xanh lá - Hoàn tất
                                '#dc3545'  // đỏ - Hủy
                            ],
                            borderColor: '#fff',
                            borderWidth: 2
                        }]
                },
                options: {
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                color: '#333',
                                font: {size: 14}
                            }
                        },
                        tooltip: {
                            callbacks: {
                                label: ctx => ctx.label + ': ' + ctx.parsed + ' đơn'
                            }
                        }
                    }
                }
            });
        <%
            StringBuilder labels = new StringBuilder("[");
            StringBuilder data = new StringBuilder("[");
            if (topSP != null) {
                for (int i = 0; i < topSP.size(); i++) {
                    Map<String, Object> sp = topSP.get(i);
                    labels.append("\"").append(sp.get("tenDT")).append("\"");
                    data.append(sp.get("tongSoLuong"));
                    if (i < topSP.size() - 1) {
                        labels.append(",");
                        data.append(",");
                    }
                }
            }
            labels.append("]");
            data.append("]");
        %>
            // === Biểu đồ Top 5 sản phẩm bán chạy ===
            const ctxTop = document.getElementById('topProductChart').getContext('2d');
            const productLabels = <%= labels.toString()%>;
            const productData = <%= data.toString()%>;

            new Chart(ctxTop, {
                type: 'bar',
                data: {
                    labels: productLabels,
                    datasets: [{
                            label: 'Số lượng bán',
                            data: productData,
                            backgroundColor: ['#007bff', '#28a745', '#ffc107', '#dc3545', '#17a2b8']
                        }]
                },
                options: {
                    indexAxis: 'y',
                    plugins: {legend: {display: false}},
                    scales: {x: {beginAtZero: true}}
                }
            });
            // === Biểu đồ Doanh thu theo tháng (Năm hiện tại) ===
            const labelsMonth = [
        <%
            List<Map<String, Object>> doanhThuThang = (List<Map<String, Object>>) request.getAttribute("doanhThuThang");
            if (doanhThuThang != null) {
                for (Map<String, Object> item : doanhThuThang) {
                    int thang = (int) item.get("thang");
        %> "Tháng <%= thang%>",
        <%  }
            }
        %>
            ];

            const dataMonth = [
        <%
            if (doanhThuThang != null) {
                for (Map<String, Object> item : doanhThuThang) {
                    double tong = (double) item.get("tong");
        %> <%= tong%>,
        <%  }
            }
        %>
            ];

            new Chart(document.getElementById("monthlyRevenueChart"), {
                type: 'bar',
                data: {
                    labels: labelsMonth,
                    datasets: [{
                            label: 'Doanh thu (VNĐ)',
                            data: dataMonth,
                            backgroundColor: 'rgba(255, 206, 86, 0.6)',
                            borderColor: 'rgba(255, 206, 86, 1)',
                            borderWidth: 2
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function (value) {
                                    return value.toLocaleString('vi-VN') + ' đ';
                                }
                            }
                        }
                    },
                    plugins: {
                        legend: {display: false},
                        tooltip: {
                            callbacks: {
                                label: ctx => ctx.parsed.y.toLocaleString('vi-VN') + ' đ'
                            }
                        }
                    }
                }
            });

        <%
            StringBuilder khLabels = new StringBuilder("[");
            StringBuilder khData = new StringBuilder("[");
            if (topKH != null && !topKH.isEmpty()) {
                for (int i = 0; i < topKH.size(); i++) {
                    Map<String, Object> kh = topKH.get(i);
                    khLabels.append("\"").append(kh.get("tenKH")).append("\"");
                    khData.append(kh.get("tongChi"));
                    if (i < topKH.size() - 1) {
                        khLabels.append(",");
                        khData.append(",");
                    }
                }
            }
            khLabels.append("]");
            khData.append("]");
        %>

        <% if (topKH != null && !topKH.isEmpty()) {%>
            new Chart(document.getElementById("topCustomerChart"), {
                type: 'bar',
                data: {
                    labels: <%= khLabels.toString()%>,
                    datasets: [{
                            label: 'Tổng chi tiêu (VNĐ)',
                            data: <%= khData.toString()%>,
                            backgroundColor: [
                                '#ffc107',
                                '#ffb84d',
                                '#ffd966',
                                '#ffe699',
                                '#fff2cc'
                            ],
                            borderColor: '#e0a800',
                            borderWidth: 1.5,
                            borderRadius: 5
                        }]
                },
                options: {
                    indexAxis: 'y',
                    scales: {
                        x: {
                            beginAtZero: true,
                            ticks: {
                                callback: value => value.toLocaleString('vi-VN') + ' ₫'
                            }
                        }
                    },
                    plugins: {
                        legend: {display: false},
                        tooltip: {
                            callbacks: {
                                label: ctx => ctx.parsed.x.toLocaleString('vi-VN') + ' ₫'
                            }
                        }
                    }
                }
            });
        <% } %>
        <%
            // Tạo dữ liệu cho biểu đồ từ List<Map>
            StringBuilder monthLabels = new StringBuilder("[");
            StringBuilder monthData = new StringBuilder("[");
            if (donTheoThang != null && !donTheoThang.isEmpty()) {
                for (int i = 0; i < donTheoThang.size(); i++) {
                    Map<String, Object> item = donTheoThang.get(i);
                    int thang = (int) item.get("thang");
                    int tongDon = (int) item.get("tongDon");
                    monthLabels.append("\"Tháng ").append(thang).append("\"");
                    monthData.append(tongDon);
                    if (i < donTheoThang.size() - 1) {
                        monthLabels.append(",");
                        monthData.append(",");
                    }
                }
            }
            monthLabels.append("]");
            monthData.append("]");
        %>

        <% if (donTheoThang != null && !donTheoThang.isEmpty()) {%>
            new Chart(document.getElementById("orderByMonthChart"), {
                type: 'bar',
                data: {
                    labels: <%= monthLabels.toString()%>,
                    datasets: [{
                            label: 'Số đơn hàng',
                            data: <%= monthData.toString()%>,
                            backgroundColor: 'rgba(255, 193, 7, 0.6)',
                            borderColor: 'rgba(255, 193, 7, 1)',
                            borderWidth: 2
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {precision: 0}
                        }
                    },
                    plugins: {
                        legend: {display: false},
                        tooltip: {
                            callbacks: {
                                label: ctx => ctx.parsed.y + ' đơn hàng'
                            }
                        }
                    }
                }
            });
        <% }%>
        });
    </script>
</html>

