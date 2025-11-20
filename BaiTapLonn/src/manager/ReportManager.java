package manager;

import model.Ticket;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {
    private BookingManager bookingManager;

    public ReportManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    public double calculateRevenue() {
        double total = 0;
        for (Ticket t : bookingManager.getTickets()) {
            // Lấy giá từ việc parse lại CSV hoặc lưu thuộc tính giá (ở đây ta lấy từ file CSV parse ra)
            // Do constructor Ticket đã lưu giá, ta cần getter. Đã thêm getter ở class Ticket.
            // Logic tạm thời: lấy giá vé từ chuỗi CSV nếu cần, hoặc từ object nếu lưu
            String[] parts = t.toCSV().split(",");
            total += Double.parseDouble(parts[3]);
        }
        return total;
    }

    public Map<String, Integer> getTopMovies() {
        Map<String, Integer> stats = new HashMap<>();
        for (Ticket t : bookingManager.getTickets()) {
            String title = t.getMovieTitle();
            stats.put(title, stats.getOrDefault(title, 0) + 1);
        }
        return stats;
    }
}