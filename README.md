# JavaApp
---------Đồ án môn học Ngôn ngữ lập trình Java-------

--------N13 - ỨNG DỤNG HỖ TRỢ TRUNG TÂM ANH NGỮ----------

Nền tảng phát triển: Mobile – Android

Ngôn ngữ: Java – Hệ quản trị cơ sở dữ liệu: MySQL

Sơ lược đồ án:

Ứng dụng được xây dựng nhằm hướng đến môi trường di động – Android với các tính năng tiện ích về cung cấp thông tin, quản lý người dùng, quản lý bài viết thiết kế dành riêng cho một Trung tâm Anh Ngữ cụ thể.
Ứng dụng hộ trợ tài khoản và phiên làm việc thích hợp cho 2 loại nhân viên chính trong Trung tâm là người quản lý và nhân viên, trong đó : 
+ Người quản lý có quyền theo dõi, tạo mới và kiểm soát tài khoản nhân viên trong Trung tâm
+ Nhân viên có quyền viết, đăng và quản lý các bài viết với mục đích quảng bá và tư vấn chọn dịch vụ mà Trung tâm cung cấp đến người dùng khách nằm ngoài Trung tâm
Người dùng khách có thể truy cập vào ứng dụng để theo dõi và nắm bắt các thông tin trên. Họ sẽ không thể nắm giữ tài khoản và không thao tác đến các chức năng nội bộ của quản lý hay nhân viên.

----------------- Cài đặt ---------------

1. Tải và cài đặt Android Studio https://developer.android.com/studio
2. Tải và cài đặt XAMPP https://www.apachefriends.org/download.html
3. Clone respository
4. Giải nén file da1_courseadviser.rar gồm folder da1_courseadviser chứa các web API và file da1_courseadviser.sql
5. Di chuyển folder da1_courseadviser vào vị trí lưu trữ XAMPP/htdocs (không thay đổi tên file hay folder)
6. Mở hệ quản trị cơ sở dữ liệu mySQL qua XAMPP, tạo mới một database mang tên da1_courseadviser và import da1_courseadviser.sql vào database trên
7. Mở cmd chạy lệnh ipconfig và copy địa chỉ IPv4 Address
8. Mở project bằng Android Studio
9. Đến file Project/java/com.example.myapp/utils/ShareIP và gắn địa chỉ IPv4 Address vào chuỗi String ở dòng thứ 4
10. Run app (có thể qua máy ảo trên Android Studio hoặc máy vật lý; đề suất tốt nhất là máy áo Pixel 4XL API 28)

----- Thank you for checking this app. Hope you have a great day! ---------
