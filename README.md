
## 1. Mô tả ứng dụng
Ứng dụng **TaskManager** là nền tảng quản trị được thiết kế nhằm giúp các doanh nghiệp và đội ngũ tối ưu hóa quy trình theo dõi tiến độ công việc. Hệ thống cho phép khởi tạo dự án, phân công nhiệm vụ cụ thể cho từng thành viên, đồng thời cập nhật trạng thái và trao đổi trực tiếp thông qua luồng bình luận thời gian thực. Nhờ đó, người quản lý dễ dàng kiểm soát thời hạn (deadline) và mức độ ưu tiên của mọi hạng mục, giúp nâng cao hiệu suất làm việc nhóm.

---

## 2. Công nghệ sử dụng
Dự án được xây dựng trên mô hình Full-Stack phổ biến với các công nghệ hiện đại:
* **Backend:** Java 17, Spring Boot 3.x, Spring Security (Quản lý phân quyền tài khoản).
* **Database:** MySQL / Spring Data JPA (Quản lý và tương tác với cơ sở dữ liệu).
* **Frontend:** HTML5, CSS3, Thymeleaf (Engine kết xuất giao diện), Bootstrap 5 (Giao diện đáp ứng - Responsive), Bootstrap Icons.
* **Công cụ quản lý thư viện:** Gradle hoặc Maven.

---

## 3. Các chức năng chính
* **Đăng nhập & Phân quyền (Authentication & Authorization):** Hệ thống phân chia rõ ràng hai vai trò người dùng:
    * `ADMIN`: Có toàn quyền hệ thống bao gồm Tạo dự án, Thêm công việc, Chỉnh sửa thông tin nâng cao và Xóa dữ liệu.
    * `USER`: Chỉ có quyền xem danh sách dự án, cập nhật tiến độ công việc được giao và tham gia thảo luận.
* **Quản lý Dự án (Project Management):** Khởi tạo, chỉnh sửa thông tin dự án, hiển thị danh sách trực quan cùng thông tin người chịu trách nhiệm.
* **Quản lý Công việc (Task Management):** Phân công công việc đi kèm các trường thông tin chi tiết: Mức độ ưu tiên (`HIGH`, `MEDIUM`, `LOW`), Hạn chót (Deadline), Trạng thái (`Open`, `In Progress`, `Done`).
* **Thảo luận nhóm (Comment System):** Cho phép các thành viên ghi chú tiến độ, gửi bình luận trao đổi trực tiếp bên trong trang chi tiết của từng đầu việc dưới danh nghĩa tài khoản đang đăng nhập.

---

## 4. Hình ảnh giao diện hệ thống
*Dưới đây là các hình ảnh minh họa giao diện, bạn có thể bổ sung các file ảnh này vào thư mục `Images/` trong kho lưu trữ của mình.*

* **Trang đăng nhập hệ thống:**
    ![Giao diện Đăng nhập](Images/login_screen.png)
* **Danh sách quản lý dự án (Dành cho Admin):**
    ![Danh sách dự án](Images/project_list.png)
* **Danh sách công việc & Tiến độ:**
    ![Danh sách công việc](Images/task_list.png)
* **Chi tiết công việc & Khung thảo luận:**
    ![Chi tiết công việc và Bình luận](Images/task_detail.png)

---

## 5. Sơ đồ kiến trúc hệ thống
Dự án áp dụng mô hình kiến trúc chuẩn **MVC (Model-View-Controller)** kết hợp tầng **Service** và **Repository** nhằm tách biệt hoàn toàn mã nguồn xử lý logic và hiển thị:
