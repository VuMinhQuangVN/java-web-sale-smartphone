# Java Web Sale Smartphone

Website bán điện thoại (smartphone) xây dựng bằng **Java Servlet/JSP** theo mô hình MVC, dùng MySQL làm cơ sở dữ liệu. Project được tổ chức theo cấu trúc NetBeans (Ant-based web application), gồm 2 phân hệ: **User** (khách hàng mua hàng) và **Admin** (quản trị viên quản lý shop).

## Tính năng chính

### Phía người dùng (User)
- Đăng ký / đăng nhập, quên mật khẩu & khôi phục qua OTP gửi email (`EmailService`, `verifyotp`, `resetpassword`)
- Xem danh sách sản phẩm, tìm kiếm, xem chi tiết sản phẩm theo biến thể (dung lượng, màu...)
- Giỏ hàng: thêm / sửa / xóa sản phẩm trong giỏ
- Đặt hàng, thanh toán (`checkoutUs`, `SimulatePayServlet`, `payment_status`)
- Kiểm tra tồn kho realtime qua API (`ApiCheckStockServlet`)
- Xem lịch sử đơn hàng, chi tiết đơn hàng, hủy đơn hàng
- Lịch sử giao dịch, đánh giá sản phẩm (`themDanhGia`)
- Quản lý thông tin cá nhân, đổi mật khẩu

### Phía quản trị (Admin)
- Dashboard tổng quan, thống kê doanh thu (`DasboardAd`, `DoanhThuAd`, `ThongKeDAO`)
- Quản lý sản phẩm và biến thể sản phẩm (thêm/sửa/xóa)
- Quản lý danh mục (category)
- Quản lý người dùng
- Quản lý đơn hàng, xem chi tiết đơn hàng
- Xuất báo cáo ra Excel (Apache POI) và PDF (iText) — `ExportExcelControl`, `ExportPdfControl`
- Xử lý hoàn tiền (`RefundService`)
- Tự động cập nhật trạng thái đơn hàng (`AutoOrderUpdater`)

## Kiến trúc

Project theo mô hình MVC cổ điển với Servlet làm Controller:

```
src/java/
├── Models/     # Entity + DAO (tầng truy xuất dữ liệu MySQL)
├── Userr/      # Servlet xử lý nghiệp vụ phía khách hàng
└── Adminn/     # Servlet xử lý nghiệp vụ phía quản trị

web/
├── User/       # Giao diện JSP phía khách hàng
├── Admin/      # Giao diện JSP phía quản trị
├── css/ js/    # Tài nguyên tĩnh
└── Image/      # Hình ảnh sản phẩm
```

Kết nối DB được quản lý tập trung trong `Models/dbConnect.java` (JDBC + MySQL Connector/J).

## Công nghệ sử dụng

- **Java 17**, Servlet/JSP (Jakarta EE), chạy trên **Apache Tomcat**
- **MySQL** (JDBC qua `mysql-connector-java`)
- **Apache POI** – xuất file Excel
- **iText 5** – xuất file PDF
- **Jakarta Mail** – gửi email OTP
- **Gson** – xử lý JSON cho các API (check tồn kho, biến thể...)
- Build bằng **Apache Ant** (NetBeans project)

## Yêu cầu môi trường

- JDK 17
- Apache Tomcat (tương thích Servlet 5+/Jakarta EE)
- MySQL Server
- NetBeans IDE (khuyến nghị, vì project đi kèm cấu hình `nbproject/`) hoặc Ant CLI

## Cài đặt & chạy

1. **Tạo database MySQL** tên `qlbandienthoai` (theo cấu hình mặc định trong `dbConnect.java`) và import schema/dữ liệu tương ứng.
2. **Cấu hình kết nối DB** trong `src/java/Models/dbConnect.java` nếu username/password MySQL của bạn khác mặc định (`root` / không mật khẩu):
   ```java
   DriverManager.getConnection("jdbc:mysql://localhost:3306/qlbandienthoai", "root", "");
   ```
3. **Cấu hình gửi mail OTP** trong `src/java/Models/EmailService.java` — thay bằng tài khoản Gmail + App Password của riêng bạn (xem lưu ý bảo mật bên dưới).
4. Mở project bằng NetBeans, cấu hình Tomcat server, sau đó **Run Project** — hoặc build bằng Ant:
   ```bash
   ant clean
   ant build
   ant run
   ```
5. Truy cập trang qua trình duyệt tại `http://localhost:8080/<context-path>/`.

## ⚠️ Lưu ý bảo mật (quan trọng)

Repo hiện đang **hardcode thông tin nhạy cảm trực tiếp trong source code**:
- `Models/EmailService.java` chứa sẵn địa chỉ Gmail và **App Password** thật dùng để gửi OTP.
- `Models/dbConnect.java` chứa thông tin kết nối MySQL (mặc định `root` không mật khẩu, ít rủi ro hơn nhưng vẫn nên tách ra).

Vì repo là **public**, các thông tin này đã bị lộ. Khuyến nghị xử lý:
1. Vào Google Account → Security → App Passwords, **thu hồi (revoke)** App Password đang bị lộ và tạo cái mới.
2. Chuyển các giá trị nhạy cảm (email, app password, thông tin DB) ra file cấu hình riêng (ví dụ `config.properties`) hoặc biến môi trường, **không commit** file này lên Git.
3. Thêm các đường dẫn chứa thông tin nhạy cảm vào `.gitignore`.
4. Cân nhắc purge lịch sử Git nếu muốn xóa hẳn thông tin cũ khỏi repo (dùng `git filter-repo` hoặc BFG Repo-Cleaner), vì chỉ xóa file ở commit mới không loại bỏ được nó khỏi history.

## Giấy phép

Chưa có file LICENSE — nếu bạn muốn public dùng lại được, nên bổ sung license phù hợp (MIT, Apache-2.0...).
