Link Demo: https://youtu.be/AW8ZxwW2V4Y
---
## 1. Mô tả ứng dụng
Ứng dụng **TaskManager** là nền tảng quản trị được thiết kế nhằm giúp các doanh nghiệp và đội ngũ tối ưu hóa quy trình theo dõi tiến độ công việc. Hệ thống cho phép khởi tạo dự án, phân công nhiệm vụ cụ thể cho từng thành viên, đồng thời cập nhật trạng thái và trao đổi trực tiếp thông qua luồng bình luận thời gian thực. Nhờ đó, người quản lý dễ dàng kiểm soát thời hạn (deadline) và mức độ ưu tiên của mọi hạng mục, giúp nâng cao hiệu suất làm việc nhóm.

---

## 2. Công nghệ sử dụng
Dự án được xây dựng trên mô hình Full-Stack phổ biến với các công nghệ hiện đại:
* **Backend:** Java 21, Spring Boot 4.0.6, Spring Security (Quản lý phân quyền tài khoản).
* **Database:** MySQL / Spring Data JPA (Quản lý và tương tác với cơ sở dữ liệu).
* **Frontend:** HTML5, CSS3, Thymeleaf (Engine kết xuất giao diện), Bootstrap 5 (Giao diện đáp ứng - Responsive), Bootstrap Icons.
* **Công cụ quản lý thư viện:** Gradle.

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

*Dưới đây là các hình ảnh minh họa thực tế các chức năng của hệ thống (Ảnh được lưu trữ trực tiếp trong thư mục tài nguyên tĩnh `static` của dự án):*

* **Giao diện Đăng nhập hệ thống:**
  ![Giao diện Đăng nhập](src/main/resources/static/login.png)

* **Danh sách hiển thị tất cả Dự án (Giao diện chung):**
  ![Danh sách tất cả dự án](src/main/resources/static/project.png)

* **Giao diện Thêm mới dự án (Dành cho Admin):**
  ![Thêm mới dự án](src/main/resources/static/addpj.png)

* **Giao diện Cập nhật thông tin dự án:**
  ![Cập nhật dự án](src/main/resources/static/updatepj.png)

* **Giao diện xem danh sách dự án dành riêng cho Nhân viên:**
  ![Trang xem dự án của nhân viên](src/main/resources/static/userpj.png)

* **Danh sách hiển thị tất cả Công việc (Nhiệm vụ):**
  ![Danh sách tất cả nhiệm vụ](src/main/resources/static/task.png)

* **Chi tiết công việc & Khung bình luận thảo luận tương tác:**
  ![Chi tiết công việc và Bình luận](src/main/resources/static/cmt.png)

---
## 5. Sơ đồ kiến trúc hệ thống

Dự án được thiết kế theo mô hình kiến trúc phân tầng tiêu chuẩn, kết hợp giữa mô hình **MVC (Model-View-Controller)** ở tầng giao diện và kiến trúc dịch vụ (**Service-Repository**) ở tầng xử lý nghiệp vụ nhằm đảm bảo tính độc lập, dễ bảo trì và mở rộng dữ liệu.

```text
+-------------------------------------------------------+
       |               Trình duyệt / Người dùng                |
       +------------------------------------------+------------+
                                                  |
                                    HTTP Request  |  HTTP Response
                                    (Form, JSON)  |  (HTML, Thymeleaf)
                                                  v
       +-------------------------------------------------------+
       |             TẦNG GIAO DIỆN (PRESENTATION LAYER)        |
       |  - Thymeleaf Templates (HTML5, Bootstrap 5)           |
       +------------------------------------------+------------+
                                                  |
                                                  v
       +-------------------------------------------------------+
       |              BỘ LỌC BẢO MẬT (SPRING SECURITY)         |
       |  - Xác thực tài khoản (Authentication)                |
       |  - Phân quyền ADMIN / USER (Authorization)            |
       +------------------------------------------+------------+
                                                  |
                                                  v
       +-------------------------------------------------------+
       |             TẦNG ĐIỀU HƯỚNG (CONTROLLER LAYER)        |
       |  - AuthController       - ProjectController           |
       |  - TaskController       - HomeController              |
       +------------------------------------------+------------+
                                                  |
                                    Service Calls | Data Mapping
                                                  v
       +-------------------------------------------------------+
       |              TẦNG NGHIỆP VỤ (SERVICE LAYER)           |
       |  - UserService          - ProjectService              |
       |  - TaskService          - CommentService              |
       +------------------------------------------+------------+
                                                  |
                                    Data Mapping  | Repositories Queries
                                                  v
       +-------------------------------------------------------+
       |             TẦNG TRUY CẬP DỮ LIỆU (REPOSITORY LAYER)  |
       |  - Spring Data JPA Interfaces                         |
       |  - UserRepository, TaskRepository, CommentRepository  |
       +------------------------------------------+------------+
                                                  |
                                     SQL Queries  | JDBC Results
                                                  v
       +-------------------------------------------------------+
       |                 CƠ SỞ DỮ LIỆU (DATABASE)              |
       |  - MySQL Server (Lưu trữ Users, Projects, Tasks,...)  |
       +-------------------------------------------------------+
```
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

```

# 🚀7. Hướng dẫn chạy dự án

## 🛠️ Yêu cầu chuẩn bị trước khi cài đặt

Trước khi chạy dự án, hãy đảm bảo máy tính của bạn đã cài đặt:

- **Java Development Kit (JDK) 17** hoặc phiên bản cao hơn.
- **MySQL Server** đang hoạt động.
- Một trong hai môi trường phát triển:
  - Eclipse IDE (Eclipse IDE for Enterprise Java Developers)
  - Visual Studio Code (VS Code)

---

## 🗄️ Bước 1: Cấu hình cơ sở dữ liệu (Database)

### 1. Tạo Database

Mở MySQL Workbench, dBeaver hoặc Navicat và thực thi lệnh:

```sql
CREATE DATABASE taskmanager_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

### 2. Cấu hình kết nối MySQL

Mở file:

```text
src/main/resources/application.properties
```

Cập nhật thông tin kết nối MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password

# Tự động đồng bộ cấu trúc bảng
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Thay `root` và `your_password` bằng tài khoản MySQL của bạn.

---

## 💻 Bước 2: Khởi chạy dự án

Chọn một trong hai cách dưới đây.

### A. Chạy trên Eclipse IDE

#### 1. Import dự án

- Mở Eclipse.
- Chọn **File → Import...**

Nếu dự án sử dụng:

- **Gradle** → Chọn **Existing Gradle Project**
- **Maven** → Chọn **Existing Maven Projects**

Nhấn **Next**.

#### 2. Chọn thư mục dự án

Tại mục **Project root directory**:

- Nhấn **Browse...**
- Chọn thư mục chứa dự án (có file `build.gradle` hoặc `pom.xml`)

Nhấn **Finish** và chờ Eclipse tải các thư viện cần thiết.

#### 3. Cấu hình Compiler

Để tránh lỗi liên quan đến tham số khi điều hướng:

- Vào:

```text
Window → Preferences → Java → Compiler
```

- Tích chọn:

```text
Store information about method parameters (usable via reflection)
```

- Nhấn **Apply and Close**
- Chuột phải vào dự án → **Refresh (F5)**

#### 4. Chạy ứng dụng

Mở file:

```text
src/main/java/clc65/quanggck/TaskManagerApplication.java
```

Chuột phải vào file:

```text
Run As → Java Application
```

---

### B. Chạy trên Visual Studio Code (VS Code)

#### 1. Mở dự án

- Mở VS Code.
- Chọn:

```text
File → Open Folder...
```

- Chọn thư mục dự án.

#### 2. Cài đặt Extension

Vào **Extensions (Ctrl + Shift + X)** và cài:

- Extension Pack for Java
- Spring Boot Extension Pack

Đợi VS Code hoàn tất quá trình tải cấu hình Java và hiển thị:

```text
Java: Ready
```

#### 3. Khởi chạy dự án

##### Cách 1: Qua giao diện

- Mở **SPRING BOOT DASHBOARD**
- Tìm dự án `taskmanager`
- Nhấn nút ▶ Run

##### Cách 2: Qua Terminal

Mở Terminal (`Ctrl + ~`) và chạy:

**Gradle**

```bash
./gradlew bootRun
```

Windows:

```bash
.\gradlew.bat bootRun
```

**Maven**

```bash
./mvnw spring-boot:run
```

Windows:

```bash
.\mvnw.cmd spring-boot:run
```

---

## 🌐 Bước 3: Kiểm tra ứng dụng

Sau khi khởi động thành công, màn hình Console sẽ hiển thị:

```text
Started TaskManagerApplication in ... seconds
```

Mở trình duyệt và truy cập:

```text
http://localhost:8080
```

Hệ thống sẽ tự động chuyển hướng đến trang:

```text
/login
```

Bạn có thể:

- Đăng nhập bằng tài khoản đã có trong Database.
- Đăng ký tài khoản mới.
- Trải nghiệm đầy đủ các chức năng quản lý công việc của hệ thống.

---

## ✅ Hoàn tất

Nếu mọi bước đều thực hiện thành công, hệ thống Task Manager sẽ hoạt động tại:

```text
http://localhost:8080
```

