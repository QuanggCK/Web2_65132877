-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 01, 2026 at 09:00 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `taskmanager`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`comment_id`, `task_id`, `user_id`, `content`, `created_at`) VALUES
(1, 1, 1, 'xong rồi', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `project_id` int(11) NOT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`project_id`, `project_name`, `description`, `created_by`, `created_at`) VALUES
(1, 'Website Bán Hàng', 'Phát triển website bán hàng online', 1, '2026-05-31 20:54:58'),
(2, 'Hệ Thống Quản Lý Sinh Viên', 'Quản lý sinh viên và điểm số', 1, '2026-05-31 20:54:58'),
(3, 'Ứng Dụng Đặt Lịch Khám', 'Đặt lịch khám bệnh trực tuyến', 1, '2026-05-31 20:54:58'),
(4, 'Website Tin Tức', 'Quản lý và đăng bài viết tin tức', 1, '2026-05-31 20:54:58'),
(5, 'Task Manager', 'Hệ thống quản lý công việc nhóm', 1, '2026-05-31 20:54:58');

-- --------------------------------------------------------

--
-- Table structure for table `project_member`
--

CREATE TABLE `project_member` (
  `id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `join_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `project_member`
--

INSERT INTO `project_member` (`id`, `project_id`, `user_id`, `join_date`) VALUES
(1, 1, 1, '2026-05-31 20:55:11'),
(2, 1, 2, '2026-05-31 20:55:11'),
(3, 1, 3, '2026-05-31 20:55:11'),
(4, 2, 1, '2026-05-31 20:55:11'),
(5, 2, 2, '2026-05-31 20:55:11'),
(6, 3, 1, '2026-05-31 20:55:11'),
(7, 3, 3, '2026-05-31 20:55:11'),
(8, 4, 1, '2026-05-31 20:55:11'),
(9, 4, 2, '2026-05-31 20:55:11'),
(10, 4, 3, '2026-05-31 20:55:11'),
(11, 5, 1, '2026-05-31 20:55:11'),
(12, 5, 2, '2026-05-31 20:55:11'),
(13, 5, 3, '2026-05-31 20:55:11');

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `task_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `assigned_to` int(11) NOT NULL,
  `created_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`task_id`, `title`, `description`, `priority`, `start_date`, `deadline`, `status_id`, `project_id`, `assigned_to`, `created_by`) VALUES
(1, 'Thiết kế giao diện', 'Thiết kế trang chủ', 'LOW', '2026-06-01', '2026-06-05', 1, 1, 2, 1),
(2, 'Xây dựng đăng nhập', 'Đăng nhập và đăng ký', 'Cao', '2026-06-01', '2026-06-06', 2, 1, 3, 1),
(3, 'Quản lý sản phẩm', 'CRUD sản phẩm', 'Trung bình', '2026-06-02', '2026-06-08', 1, 1, 2, 1),
(4, 'Giỏ hàng', 'Chức năng thêm giỏ hàng', 'Cao', '2026-06-03', '2026-06-09', 3, 1, 3, 1),
(5, 'Thanh toán', 'Thanh toán đơn hàng', 'Cao', '2026-06-04', '2026-06-10', 4, 1, 2, 1),
(6, 'Thiết kế CSDL', 'Tạo bảng sinh viên', 'Cao', '2026-06-01', '2026-06-03', 4, 2, 2, 1),
(7, 'CRUD Sinh Viên', 'Thêm sửa xóa sinh viên', 'Cao', '2026-06-02', '2026-06-06', 2, 2, 2, 1),
(8, 'Quản lý lớp', 'Quản lý lớp học', 'Trung bình', '2026-06-03', '2026-06-08', 1, 2, 2, 1),
(9, 'Quản lý điểm', 'Nhập điểm sinh viên', 'Cao', '2026-06-04', '2026-06-10', 1, 2, 2, 1),
(10, 'Báo cáo thống kê', 'Thống kê kết quả', 'Thấp', '2026-06-05', '2026-06-12', 3, 2, 2, 1),
(11, 'Thiết kế giao diện', 'UI đặt lịch khám', 'Trung bình', '2026-06-01', '2026-06-04', 2, 3, 3, 1),
(12, 'Đăng ký bệnh nhân', 'Quản lý tài khoản bệnh nhân', 'Cao', '2026-06-02', '2026-06-06', 1, 3, 3, 1),
(13, 'Đặt lịch', 'Chọn bác sĩ và lịch khám', 'Cao', '2026-06-03', '2026-06-07', 2, 3, 3, 1),
(14, 'Thông báo', 'Gửi email nhắc lịch', 'Thấp', '2026-06-04', '2026-06-09', 3, 3, 3, 1),
(15, 'Báo cáo', 'Thống kê lượt khám', 'Thấp', '2026-06-05', '2026-06-10', 4, 3, 3, 1),
(16, 'Quản lý bài viết', 'CRUD bài viết', 'Cao', '2026-06-01', '2026-06-05', 4, 4, 2, 1),
(17, 'Danh mục tin', 'CRUD danh mục', 'Trung bình', '2026-06-02', '2026-06-06', 2, 4, 3, 1),
(18, 'Tìm kiếm', 'Tìm kiếm bài viết', 'Thấp', '2026-06-03', '2026-06-08', 1, 4, 2, 1),
(19, 'Bình luận', 'Chức năng bình luận', 'Trung bình', '2026-06-04', '2026-06-09', 3, 4, 3, 1),
(20, 'Trang chủ', 'Hiển thị tin nổi bật', 'Cao', '2026-06-05', '2026-06-10', 4, 4, 2, 1),
(21, 'Đăng nhập', 'Spring Security Login', 'Cao', '2026-06-01', '2026-06-03', 4, 5, 2, 1),
(22, 'Quản lý Project', 'CRUD Project', 'Cao', '2026-06-02', '2026-06-05', 4, 5, 3, 1),
(23, 'Quản lý Task', 'CRUD Task', 'Cao', '2026-06-03', '2026-06-07', 2, 5, 2, 1),
(24, 'Phân quyền', 'ADMIN và USER', 'Trung bình', '2026-06-04', '2026-06-08', 1, 5, 3, 1),
(25, 'Dashboard', 'Thống kê tiến độ', 'Thấp', '2026-06-05', '2026-06-10', 3, 5, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `task_status`
--

CREATE TABLE `task_status` (
  `status_id` int(11) NOT NULL,
  `status_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `task_status`
--

INSERT INTO `task_status` (`status_id`, `status_name`) VALUES
(1, 'Cần làm'),
(2, 'Đang làm'),
(3, 'Kiểm tra'),
(4, 'Hoàn thành');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `full_name`, `email`, `role`, `created_at`) VALUES
(1, 'truongnhom', '{noop}admin123', 'Truong Nhom', 'admin@gmail.com', 'ADMIN', '2026-05-31 16:48:16'),
(2, 'thanhvien', '{noop}user123', 'Thanh Vien', 'user@gmail.com', 'USER', '2026-05-31 16:48:16'),
(3, 'thanhvien2', '{noop}user456', 'Thanh Vien 2', 'user2@gmail.com', 'USER', '2026-05-31 20:54:45');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `fk_comment_task` (`task_id`),
  ADD KEY `fk_comment_user` (`user_id`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`project_id`),
  ADD KEY `fk_project_user` (`created_by`);

--
-- Indexes for table `project_member`
--
ALTER TABLE `project_member`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pm_project` (`project_id`),
  ADD KEY `fk_pm_user` (`user_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`task_id`),
  ADD KEY `fk_task_status` (`status_id`),
  ADD KEY `fk_task_project` (`project_id`),
  ADD KEY `fk_task_assigned` (`assigned_to`),
  ADD KEY `fk_task_creator` (`created_by`);

--
-- Indexes for table `task_status`
--
ALTER TABLE `task_status`
  ADD PRIMARY KEY (`status_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `project_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `project_member`
--
ALTER TABLE `project_member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `task_status`
--
ALTER TABLE `task_status`
  MODIFY `status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `fk_comment_task` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `fk_project_user` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `project_member`
--
ALTER TABLE `project_member`
  ADD CONSTRAINT `fk_pm_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_pm_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `fk_task_assigned` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `fk_task_creator` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `fk_task_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_task_status` FOREIGN KEY (`status_id`) REFERENCES `task_status` (`status_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
