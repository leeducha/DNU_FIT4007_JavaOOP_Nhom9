package model;

public class Ticket {
    private String ticketId;
    private String movieTitle;
    private String seatId;
    private double finalPrice;

    public Ticket(String ticketId, String movieTitle, String seatId, double finalPrice) {
        this.ticketId = ticketId;
        this.movieTitle = movieTitle;
        this.seatId = seatId;
        this.finalPrice = finalPrice;
    }

    // Getter
    public String getTicketId() { return ticketId; }
    public String getSeatId() { return seatId; }
    public String getMovieTitle() { return movieTitle; }
    public double getPrice() { return finalPrice; }

    // Xuất dữ liệu dạng CSV
    public String toCSV() {
        return ticketId + "," + movieTitle + "," + seatId + "," + finalPrice;
    }

    @Override
    public String toString() {
        return String.format("Vé [%s] - Phim: %s - Ghế: %s - Giá: %.0f VND",
                ticketId, movieTitle, seatId, finalPrice);
    }
}