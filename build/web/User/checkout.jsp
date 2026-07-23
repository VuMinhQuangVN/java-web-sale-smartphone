<%-- 
    Document   : checkout
    Created on : Oct 15, 2025, 7:37:17 PM
    Author     : ACER
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.NumberFormat, java.util.Locale, Models.*" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán - WebPhone</title>
        <link href="<%= request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <script src="<%= request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a2e0c9a7a2.js" crossorigin="anonymous"></script>
        <style>
            body {
                background: #f9fafc;
                font-family: "Segoe UI", Arial, sans-serif;
            }

            /* HEADER đồng bộ với các trang khác */
            header {
                background: #ffd900;
                padding: 12px 32px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                position: sticky;
                top: 0;
                z-index: 10;
            }

            header a {
                color: #222;
                text-decoration: none;
                font-weight: 500;
            }

            header a:hover {
                color: #0056b3;
            }

            .checkout-container {
                max-width: 1100px;
                margin: 50px auto;
            }

            .info-card {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                padding: 24px;
                margin-bottom: 30px;
            }

            .summary-box {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                padding: 24px;
            }

            .btn-confirm {
                background: #e53935;
                color: #fff;
                border: none;
                padding: 12px;
                border-radius: 8px;
                width: 100%;
                font-size: 16px;
            }

            .btn-confirm:hover {
                background: #c62828;
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
    <body>
        <%
            NguoiDung nd = (NguoiDung) session.getAttribute("user");
            boolean loggedIn = (nd != null && nd.getMaND() > 0);
            List<Map<String, Object>> selectedItems = (List<Map<String, Object>>) request.getAttribute("selectedItems");
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            String selectedAll = (String) request.getAttribute("selectedAll");
            String destination = (String) request.getAttribute("destination");
int cartSize = (int) request.getAttribute("cartSize");
        %>

        <!-- HEADER -->
        <header>
            <div class="d-flex align-items-center gap-2">
                <img src="<%= request.getContextPath()%>/Image/aaaaa.jpg" class="logo" style="height:55px;">
                <strong>WEBPHONE</strong>
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

        <!-- MAIN -->
        <div class="checkout-container container">
            <h3 class="fw-bold mb-4 text-center">🧾 Xác nhận đơn hàng</h3>

            <div class="info-card">
                <h5 class="fw-bold mb-3">Thông tin người nhận</h5>

                <form action="<%= request.getContextPath()%>/checkoutUs" method="post">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Họ tên người nhận</label>
                            <input type="text" name="tenNguoiNhan" value="<%= nd != null ? nd.getTenND() : ""%>" class="form-control" readonly>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" name="sdt" value="<%= nd != null ? nd.getSdt() : ""%>" class="form-control" readonly>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Địa chỉ giao hàng</label>
                        <input type="text" name="diaChi" value="<%= nd != null ? nd.getDiachi() : ""%>" class="form-control" readonly>
                    </div>

                    <!-- 🧾 TÓM TẮT ĐƠN HÀNG -->
                    <div class="summary-box mt-4">
                        <h5 class="fw-bold mb-3">Đơn hàng của bạn</h5>
                        <table class="table table-bordered align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>Hình ảnh</th>
                                    <th>Sản phẩm</th>
                                    <th>Biến thể</th>
                                    <th class="text-center">SL</th>
                                    <th class="text-end">Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    double tong = 0;
                                    if (selectedItems != null && !selectedItems.isEmpty()) {
                                        for (Map<String, Object> item : selectedItems) {
                                            DienThoai dt = (DienThoai) item.get("dienthoai");
                                            BienTheDienThoai bt = (BienTheDienThoai) item.get("bienthe");
                                            int soLuong = (int) item.get("soLuong");
                                            double thanhTien = bt.getGia() * soLuong;
                                            tong += thanhTien;
                                %>
                                <tr>
                                    <td><img src="<%= request.getContextPath()%>/<%= dt.getAnh()%>" 
                                             style="width:80px;height:80px;object-fit:contain" class="border rounded"></td>
                                    <td><%= dt.getTenDT()%></td>
                                    <td><%= bt.getMauSac()%> | <%= bt.getRAM()%>/<%= bt.getROM()%></td>
                                    <td class="text-center"><%= soLuong%></td>
                                    <td class="text-end text-danger fw-bold"><%= nf.format(thanhTien)%> ₫</td>
                                </tr>
                                <% }
                                    }%>
                            </tbody>
                        </table>

                        <div class="d-flex justify-content-between mt-3">
                            <strong>Tổng cộng:</strong>
                            <strong class="text-danger fs-5"><%= nf.format(tong)%> ₫</strong>
                        </div>
                    </div>

                    <!-- 💳 CHỌN PHƯƠNG THỨC THANH TOÁN -->
                    <div class="mt-4">
                        <h5 class="fw-bold mb-3">Phương thức thanh toán</h5>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="phuongThucTT" id="cod" value="COD" checked>
                            <label class="form-check-label" for="cod">💵 Thanh toán khi nhận hàng (COD)</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="phuongThucTT" id="qr" value="QR">
                            <label class="form-check-label" for="qr">🏧 Chuyển khoản qua mã QR</label>
                        </div>
                    </div>

                <input type="hidden" name="tongTien" value="<%= tong%>">
                <input type="hidden" name="selectedAll" value="<%= selectedAll != null ? selectedAll : "false" %>">
                <input type="hidden" name="destination" value="<%= destination != null ? destination : "cart" %>">
                    <div class="text-center">
                        <button type="submit" class="btn btn-success mt-4 px-4 py-2 fw-bold">Xác nhận đặt hàng</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- 💳 Modal hiển thị mã QR -->
        <div class="modal fade" id="qrModal" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" aria-labelledby="qrLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content text-center">
                    <div class="modal-header">
                        <h5 class="modal-title fw-bold" id="qrLabel">Thanh toán qua mã QR</h5>
                        <!-- Bỏ nút đóng để ép người dùng chờ hoặc hết giờ -->
                    </div>
                    <div class="modal-body">
                        <p>Vui lòng quét mã để thanh toán: <strong class="text-danger"><%= nf.format(tong)%> ₫</strong></p>

                        <img src="<%= request.getContextPath()%>/Image/qr_thanh_toan.jpg" style="width:200px;height:200px;" class="mb-3">

                        <!-- Hiển thị thời gian đếm ngược -->
                        <div class="alert alert-warning">
                            Đơn hàng sẽ hết hạn sau: <strong id="countdownTimer" style="font-size: 1.2em">05:00</strong>
                        </div>

                        <div class="d-flex justify-content-center align-items-center mt-3">
                            <div class="spinner-border text-primary me-2" role="status" style="width: 1.5rem; height: 1.5rem;"></div>
                            <span>Đang chờ xác nhận thanh toán từ ngân hàng...</span>
                        </div>

                        <!-- PHẦN NÀY ĐỂ DEMO: Giả lập quét mã -->
                        <!-- Khi bảo vệ đồ án, bạn mở link này ở tab ẩn danh hoặc điện thoại -->
                        <div class="mt-4 p-2 bg-light border" style="font-size: 0.8em;">
                            <strong>Dành cho Demo (Giả lập điện thoại quét):</strong><br>
                            <a id="simulationLink" href="#" target="_blank">Click vào đây để giả lập đã Thanh toán</a>
                        </div>
                    </div>
                    <div class="modal-footer justify-content-center">
                        <button type="button" class="btn btn-outline-secondary" id="btnCancelPayment">Hủy thanh toán</button>
                    </div>
                </div>
            </div>
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

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const form = document.querySelector("form[action*='checkoutUs']");
                const modalElement = document.getElementById('qrModal');
                const modal = new bootstrap.Modal(modalElement);
                const timerElement = document.getElementById("countdownTimer");
                const simulationLink = document.getElementById("simulationLink");
                const contextPath = '<%= request.getContextPath()%>';

                let checkInterval;
                let countdownInterval;
                let timeRemaining = 300;

                form.addEventListener("submit", (e) => {
                    const qrSelected = document.getElementById("qr").checked;

                    if (qrSelected) {
                        e.preventDefault();

                        const transId = 'TRANS_' + Date.now();

                        // SỬA: Đổi từ template literal sang nối chuỗi bằng '+'
                        simulationLink.href = contextPath + '/simulate-pay?transId=' + transId;

                        modal.show();
                        startCountdown();
                        startPolling(transId);
                    }
                });

                // --- Hàm xử lý đếm ngược ---
                function startCountdown() {
                    timeRemaining = 300;
                    updateTimerDisplay(timeRemaining);

                    countdownInterval = setInterval(() => {
                        timeRemaining--;
                        updateTimerDisplay(timeRemaining);

                        if (timeRemaining <= 0) {
                            handleTimeout();
                        }
                    }, 1000);
                }

                function updateTimerDisplay(seconds) {
                    const m = Math.floor(seconds / 60).toString().padStart(2, '0');
                    const s = (seconds % 60).toString().padStart(2, '0');
                    // SỬA: Đổi từ template literal sang nối chuỗi bằng '+'
                    timerElement.textContent = m + ':' + s;
                }

                // --- Hàm xử lý hết giờ ---
                function handleTimeout() {
                    clearIntervals();
                    modal.hide();
                    alert("Hết thời gian thanh toán. Vui lòng thực hiện lại.");
                    window.location.reload();
                }

                // --- Nút hủy thanh toán ---
                document.getElementById("btnCancelPayment").addEventListener("click", () => {
                    if (confirm("Bạn muốn hủy giao dịch này?")) {
                        handleTimeout();
                    }
                });

                // --- Hàm AJAX Polling (Quan trọng nhất) ---
                function startPolling(transId) {
                    checkInterval = setInterval(() => {
                        // SỬA: Đổi từ template literal sang nối chuỗi bằng '+'
                        fetch(contextPath + '/api/payment-status?transId=' + transId)
                                .then(response => response.json())
                                .then(data => {
                                    console.log("Trạng thái thanh toán:", data.status);

                                    if (data.status === 'PAID') {
                                        clearIntervals();

                                        // GIỮ LẠI BACKTICK (``) Ở ĐÂY LÀ AN TOÀN VÀ TỐT NHẤT
                                        modalElement.querySelector('.modal-body').innerHTML = `
                                    <div class="text-success py-4">
                                        <i class="fa fa-check-circle fa-5x mb-3"></i>
                                        <h3>Thanh toán thành công!</h3>
                                        <p>Đang chuyển hướng...</p>
                                    </div>
                                `;

                                        setTimeout(() => {
                                            modal.hide();
                                            form.submit();
                                        }, 2000);
                                    }
                                })
                                .catch(error => console.error('Lỗi kiểm tra trạng thái:', error));
                    }, 2000);
                }

                // Hàm dọn dẹp các interval
                function clearIntervals() {
                    clearInterval(checkInterval);
                    clearInterval(countdownInterval);
                }
                const itemsToCheck = [];

                // Đoạn mã JSP này sẽ chạy trên MÁY CHỦ.
                // Nó lặp qua danh sách sản phẩm bạn đã chọn và "viết" ra mã JavaScript.
            <%
                if (selectedItems != null && !selectedItems.isEmpty()) {
                    for (Map<String, Object> item : selectedItems) {
                        BienTheDienThoai bt = (BienTheDienThoai) item.get("bienthe");
                        int soLuong = (int) item.get("soLuong");
            %>
                // Với mỗi sản phẩm, nó thêm một mục vào "danh sách kiểm tra".
                itemsToCheck.push({maBienThe: <%= bt.getMaBienthe()%>, soLuong: <%= soLuong%>});
            <%
                    }
                }
            %>

                // 2. GỬI YÊU CẦU ĐẾN MÁY CHỦ (NẾU CÓ SẢN PHẨM CẦN KIỂM TRA)
                if (itemsToCheck.length > 0) {
                    // fetch là cách JavaScript hiện đại để giao tiếp với máy chủ mà không cần tải lại trang.
                    fetch('<%= request.getContextPath()%>/api/check-stock', {
                        method: 'POST', // Phương thức: Gửi dữ liệu đi.
                        headers: {'Content-Type': 'application/json'}, // "Thư giới thiệu": Báo cho máy chủ biết tôi đang gửi dữ liệu dạng JSON.
                        body: JSON.stringify(itemsToCheck) // Chuyển "danh sách kiểm tra" thành một chuỗi JSON để gửi đi.
                    })
                            // 3. XỬ LÝ KHI NHẬN ĐƯỢC PHẢN HỒI
                            .then(response => {
                                // BƯỚC 1: KIỂM TRA TRẠNG THÁI HTTP
                                // Nếu response.ok là false (ví dụ: status là 404 hoặc 500),
                                // thì chúng ta biết đây là một lỗi từ server.
                                if (!response.ok) {
                                    return response.json().then(errorData => {
                                        throw new Error(errorData.error || 'Lỗi không xác định từ máy chủ');
                                    });
                                }
                                return response.json();
                            }) // Chuyển đổi chuỗi JSON phản hồi từ máy chủ thành một đối tượng JavaScript.
                            .then(data => {

                                if (!data.allAvailable) {
                                    // ... thì thực hiện các hành động cảnh báo:

                                    // a. Vô hiệu hóa nút "Xác nhận đặt hàng" để người dùng không bấm được nữa.
                                    document.querySelector("button[type='submit']").disabled = true;

                                    // b. Tạo một đoạn mã HTML chứa thông báo lỗi.
                                    let errorHtml = '<div class="alert alert-danger"><strong>Cảnh báo:</strong> Một số sản phẩm đã thay đổi trạng thái:<ul>';

                                    // c. Lặp qua danh sách các sản phẩm không có sẵn trong báo cáo.
                                    data.unavailableItems.forEach(item => {
                                        errorHtml += `<li>${item.tenSanPham} - ${item.thongTinBienThe}: <strong>vừa hết hàng</strong> hoặc không đủ số lượng.</li>`;
                                    });

                                    errorHtml += '</ul>Vui lòng <a href="<%= request.getContextPath()%>/gioHang">quay lại giỏ hàng</a> để cập nhật.</div>';

                                    // d. Tìm nút "Xác nhận đặt hàng" và chèn thông báo lỗi ngay phía trên nó.
                                    const submitButton = document.querySelector("button[type='submit']");
                                    submitButton.insertAdjacentHTML('beforebegin', errorHtml);
                                }
                            })
                            // 4. XỬ LÝ NẾU CÓ LỖI MẠNG
                            .catch(error => console.error('Lỗi khi kiểm tra kho hàng:', error)); // Nếu máy chủ bị lỗi hoặc mất mạng, in lỗi ra console.
                }
            });
        </script>
    </body>
</html>

