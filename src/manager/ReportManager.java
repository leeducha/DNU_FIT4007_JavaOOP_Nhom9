package manager;

import model.Ticket;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {
    private BookingManager bookingManager;

    public ReportManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    // ✅ Tính tổng doanh thu
    public double calculateRevenue() {
        double total = 0;
        for (Ticket t : bookingManager.getTickets()) {
            total += t.getPrice();
        }
        return total;
    }

    // ✅ Lấy danh sách top phim bán chạy
    public Map<String, Integer> getTopMovies() {
        Map<String, Integer> stats = new HashMap<>();
        for (Ticket t : bookingManager.getTickets()) {
            String title = t.getMovieTitle();
            stats.put(title, stats.getOrDefault(title, 0) + 1);
        }
        return stats;
    }
}
