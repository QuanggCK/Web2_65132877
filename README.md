
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

Dự án được thiết kế theo mô hình kiến trúc phân tầng tiêu chuẩn, kết hợp giữa mô hình **MVC (Model-View-Controller)** ở tầng giao diện và kiến trúc dịch vụ (**Service-Repository**) ở tầng xử lý nghiệp vụ nhằm đảm bảo tính độc lập, dễ bảo trì và mở rộng dữ liệu.

```mermaid
graph TD
    %% Định nghĩa các Client/User
    User([Người dùng / Trình duyệt]) <--> |HTTP Request / Response| View
    
    %% Tầng Giao diện (Presentation Layer)
    subgraph Giao diện (Presentation Layer)
        View[Thymeleaf Templates <br> HTML5 / Bootstrap 5]
    end

    %% Tầng Điều hướng và Bảo mật
    View <--> |Controller Mapping| Controller
    subgraph Điều phối & Bảo mật (Security & Controller)
        Security[Spring Security <br> Filter Chain]
        Controller[Spring Controllers <br> Task / Project / Auth]
        Security -.-> |Xác thực & Phân quyền| Controller
    end

    %% Tầng Nghiệp vụ (Business Layer)
    Controller <--> |Service Calls| Service
    subgraph Nghiệp vụ (Business Service Layer)
        Service[Service Components <br> TaskService / UserService /... ]
    end

    %% Tầng Dữ liệu (Data Access Layer)
    Service <--> |Data Mapping| Repo
    subgraph Truy cập dữ liệu (Data Access Layer)
        Repo[Spring Data JPA <br> Repositories]
    end

    %% Tầng Cơ sở dữ liệu (Database Layer)
    Repo <--> |SQL Queries / JDBC| DB[(MySQL Database)]

    %% Định nghĩa phong cách màu sắc cho các khối
    classDef client fill:#f9f,stroke:#333,stroke-width:2px;
    classDef view fill:#d1e7dd,stroke:#0f5132,stroke-width:1px;
    classDef control fill:#cff4fc,stroke:#055160,stroke-width:1px;
    classDef service fill:#fff3cd,stroke:#664d03,stroke-width:1px;
    classDef repo fill:#f8d7da,stroke:#842029,stroke-width:1px;
    classDef db fill:#e2e3e5,stroke:#383d41,stroke-width:2px;

    class User client;
    class View view;
    class Security,Controller control;
    class Service service;
    class Repo repo;
    class DB db;


---

## 6. Cấu trúc chi tiết thư mục dự án
```text
src/
├── main/
│   ├── java/
│   │   └── clc65/
│   │       └── quanggck/
│   │           ├── TaskManagerApplication.java  # File khởi chạy chính của ứng dụng Spring Boot
│   │           │
│   │           ├── config/                      # Cấu hình hệ thống & Bảo mật
│   │           │   └── SecurityConfig.java      # Cấu hình Spring Security (Phân quyền ADMIN/USER, Login/Logout)
│   │           │
│   │           ├── controllers/                 # Tầng tiếp nhận Request từ trình duyệt và điều hướng View
│   │           │   ├── HomeController.java      # Điều hướng trang chủ, bảng điều khiển chung
│   │           │   ├── AuthController.java      # Xử lý Đăng nhập, Đăng ký tài khoản
│   │           │   ├── ProjectController.java   # Quản lý định tuyến Dự án (Xem, thêm, sửa, xóa dự án)
│   │           │   └── TaskController.java      # Quản lý định tuyến Công việc & Bình luận (Xem, thêm, sửa, xóa, comment)
│   │           │
│   │           ├── models/                      # Tầng chứa các Entity định nghĩa cấu trúc bảng Database
│   │           │   ├── User.java                # Thông tin tài khoản, vai trò (Role)
│   │           │   ├── Project.java             # Thông tin dự án
│   │           │   ├── Task.java                # Thông tin chi tiết công việc, mức độ ưu tiên, hạn chót
│   │           │   ├── TaskStatus.java          # Định nghĩa trạng thái công việc (Mới, Đang làm, Hoàn thành)
│   │           │   └── Comment.java             # Thông tin nội dung thảo luận, thời gian tạo bình luận
│   │           │
│   │           ├── repos/                       # Tầng tương tác trực tiếp, truy vấn dữ liệu từ MySQL (JPA)
│   │           │   ├── UserRepository.java      # Tìm kiếm user, kiểm tra trùng lặp email/username
│   │           │   ├── ProjectRepository.java   # Truy vấn danh sách dự án
│   │           │   ├── TaskRepository.java      # Tìm kiếm, lọc danh sách công việc
│   │           │   ├── TaskStatusRepository.java# Truy vấn danh mục trạng thái
│   │           │   └── CommentRepository.java   # Lấy danh sách bình luận theo Task ID
│   │           │
│   │           └── services/                    # Tầng xử lý logic nghiệp vụ xử lý dữ liệu trung gian
│   │               ├── UserService.java         # Logic xử lý thông tin người dùng, lấy user đăng nhập hiện tại
│   │               ├── ProjectService.java      # Logic tính toán, xử lý thông tin dự án
│   │               ├── TaskService.java         # Logic phân công công việc, kiểm tra hạn chót
│   │               ├── TaskStatusService.java   # Cung cấp danh mục trạng thái tiến độ
│   │               └── CommentService.java      # Logic kiểm tra, lưu trữ các bình luận hợp lệ
│   │
│   └── resources/
│       ├── application.properties               # File cấu hình cấu hình Port, chuỗi kết nối MySQL DB, mã hóa
│       │
│       ├── static/                              # Chứa tài nguyên tĩnh của hệ thống (Trình duyệt tải trực tiếp)
│       │     
│       └── templates/                           # Thư mục chứa toàn bộ giao diện HTML của hệ thống (Thymeleaf Engine)
│           ├── login.html                       # Giao diện form Đăng nhập tài khoản
│           │
│           ├── fragments/                       # Các thành phần giao diện dùng chung được tái sử dụng
│           │   ├── header.html                  # Thanh điều hướng phía trên cùng (Navbar, Thông tin User, Đăng xuất)
│           │   └── sidebar.html                 # Thanh trình đơn bên trái (Menu chuyển tab Dự án, Công việc, Tài khoản)
│           │
│           ├── project/                         # Thư mục chứa bộ giao diện quản trị Dự án
│           │   ├── list.html                    # Trang hiển thị danh sách toàn bộ dự án hiện có
│           │   ├── add.html                     # Trang chứa form tạo dự án mới (Chỉ Admin nhìn thấy)
│           │   └── edit.html                    # Trang chứa form cập nhật thông tin dự án
│           │
│           └── task/                            # Thư mục chứa bộ giao diện quản trị Công việc
│               ├── list.html                    # Trang hiển thị danh sách công việc (Có hiển thị huy hiệu ưu tiên, trạng thái)
│               ├── add.html                     # Trang chứa form tạo và phân công công việc mới
│               ├── edit.html                    # Trang chứa form cập nhật tiến độ, sửa đổi công việc
│               └── detail.html                  # Trang chi tiết công việc (Hiển thị đầy đủ mô tả, bảng thông tin và khung chat thảo luận)
