package model;

public class Showtime {
    private String showtimeId;
    private String movieId;
    private String roomId;
    private String startTime; // Ví dụ: "19:00 20/11/2023"

    public Showtime(String showtimeId, String movieId, String roomId, String startTime) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.roomId = roomId;
        this.startTime = startTime;
    }

    public String getShowtimeId() { return showtimeId; }

    public String toCSV() {
        return showtimeId + "," + movieId + "," + roomId + "," + startTime;
    }

    @Override
    public String toString() {
        return String.format("Suất chiếu [%s]: Phim %s tại %s lúc %s", showtimeId, movieId, roomId, startTime);
    }
}