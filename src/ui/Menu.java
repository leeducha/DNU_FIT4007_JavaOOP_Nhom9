package ui;

import manager.*;
import model.*;
import exceptions.SeatAlreadyBookedException;

import java.util.List;
import java.util.Scanner;

public class Menu {
    // Khai báo các Manager để xử lý nghiệp vụ
    private MovieManager movieMgr;
    private BookingManager bookingMgr;
    private ReportManager reportMgr;
    private Scanner scanner;

    public Menu() {
        // Khởi tạo các đối tượng khi Menu được tạo
        this.movieMgr = new MovieManager();
        this.bookingMgr = new BookingManager();
        this.reportMgr = new ReportManager(bookingMgr);
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   HỆ THỐNG QUẢN LÝ RẠP PHIM ");
            System.out.println("========================================");
            System.out.println("1. Xem danh sách phim");
            System.out.println("2. Thêm phim mới");
            System.out.println("3. Sửa thông tin phim");
            System.out.println("4. Xóa phim");
            System.out.println("5. Tìm kiếm phim");
            System.out.println("6. Xem trạng thái ghế");
            System.out.println("7. Đặt vé xem phim");
            System.out.println("8. Hủy vé");
            System.out.println("9. Tra cứu vé (theo Mã)");
            System.out.println("10. Xem lịch sử vé đã bán");
            System.out.println("11. Thống kê doanh thu");
            System.out.println("12. Top phim bán chạy");
            System.out.println("0. Thoát");
            System.out.print("=> Chọn chức năng: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1: showMovies(); break;
                case 2: addMovie(); break;
                case 3: editMovie(); break;
                case 4: deleteMovie(); break;
                case 5: searchMovie(); break;

                case 6: showSeats(); break;
                case 7: bookTicket(); break;
                case 8: cancelTicket(); break;
                case 9: findTicket(); break;

                case 10: showHistory(); break;
                case 11:
                    System.out.printf(">>> TỔNG DOANH THU: %,.0f VNĐ\n", reportMgr.calculateRevenue());
                    break;
                case 12:
                    System.out.println("--- TOP PHIM ---");
                    reportMgr.getTopMovies().forEach((k, v) -> System.out.println("- " + k + ": " + v + " vé"));
                    break;

                case 0:
                    System.out.println("Tạm biệt!");
                    return; // Thoát khỏi vòng lặp và kết thúc hàm display
                default: System.out.println("Chức năng không hợp lệ!");
            }
        }
    }

    // --- CÁC HÀM HỖ TRỢ GIAO DIỆN (Private) ---

    private void showMovies() {
        System.out.println("\n--- DANH SÁCH PHIM ---");
        if (movieMgr.getMovies().isEmpty()) System.out.println("(Trống)");
        else movieMgr.getMovies().forEach(System.out::println);
    }

    private void addMovie() {
        System.out.print("Nhập tên phim: "); String t = scanner.nextLine();
        System.out.print("Nhập giá vé: ");
        try {
            double p = Double.parseDouble(scanner.nextLine());
            movieMgr.addMovie(t, p);
            System.out.println("Thêm thành công!");
        } catch (Exception e) { System.out.println("Lỗi nhập giá!"); }
    }

    private void editMovie() {
        System.out.print("Nhập Mã phim cần sửa: "); String id = scanner.nextLine();
        System.out.print("Tên mới: "); String t = scanner.nextLine();
        System.out.print("Giá mới: "); double p = Double.parseDouble(scanner.nextLine());
        if (movieMgr.editMovie(id, t, p)) System.out.println("Cập nhật thành công!");
        else System.out.println("Không tìm thấy mã phim.");
    }

    private void deleteMovie() {
        System.out.print("Nhập Mã phim cần xóa: ");
        if (movieMgr.deleteMovie(scanner.nextLine())) System.out.println("Đã xóa!");
        else System.out.println("Không tìm thấy.");
    }

    private void searchMovie() {
        System.out.print("Nhập từ khóa: ");
        movieMgr.searchMovie(scanner.nextLine()).forEach(System.out::println);
    }

    private void showSeats() {
        System.out.println("\n--- SƠ ĐỒ GHẾ ---");
        for (Seat s : bookingMgr.getSeats()) {
            String status = (s.getStatus() == SeatStatus.BOOKED) ? "[X]" : "[_]";
            System.out.print(s.getSeatId() + status + "  ");
            if (s.getSeatId().endsWith("5")) System.out.println();
        }
        System.out.println("\nChú thích: [_] Trống, [X] Đã đặt (A: Thường, B: VIP)");
    }

    private void bookTicket() {
        showMovies();
        System.out.print("Chọn số thứ tự phim (1...n): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= movieMgr.getMovies().size()) {
                System.out.println("Sai số thứ tự!"); return;
            }

            showSeats();
            System.out.print("Nhập mã ghế (VD: A1): ");
            String seatId = scanner.nextLine();

            Ticket t = bookingMgr.bookTicket(movieMgr.getMovies().get(idx), seatId);
            System.out.println(">>> ĐẶT VÉ THÀNH CÔNG: " + t);
        } catch (SeatAlreadyBookedException e) {
            System.out.println("LỖI: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void cancelTicket() {
        System.out.print("Nhập mã vé cần hủy: ");
        if (bookingMgr.cancelTicket(scanner.nextLine())) System.out.println("Đã hủy vé thành công.");
        else System.out.println("Không tìm thấy vé.");
    }

    private void findTicket() {
        System.out.print("Nhập mã vé: ");
        Ticket t = bookingMgr.findTicket(scanner.nextLine());
        System.out.println(t != null ? t : "Không tìm thấy vé.");
    }

    private void showHistory() {
        System.out.println("\n--- LỊCH SỬ VÉ ---");
        bookingMgr.getTickets().forEach(System.out::println);
    }
}