package model.repository; // Cập nhật theo cấu trúc lồng nhau

import model.*; // Import các class từ thư mục cha (model)
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    // Tạo thư mục data ngay tại thư mục gốc dự án
    private static final String DATA_DIR = "data/";
    private static final String MOVIE_FILE = DATA_DIR + "movies.csv";
    private static final String SEAT_FILE = DATA_DIR + "seats.csv";
    private static final String TICKET_FILE = DATA_DIR + "tickets.csv";

    public DataRepository() {
        new File(DATA_DIR).mkdirs();
        createFileIfNotExists(MOVIE_FILE);
        createFileIfNotExists(SEAT_FILE);
        createFileIfNotExists(TICKET_FILE);
    }

    private void createFileIfNotExists(String path) {
        try {
            File file = new File(path);
            file.createNewFile();
        } catch (IOException e) {
            System.err.println("Lỗi tạo file: " + e.getMessage());
        }
    }

    // --- MOVIE ---
    public List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    movies.add(new Movie(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return movies;
    }

    public void saveAllMovies(List<Movie> movies) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIE_FILE))) {
            for (Movie m : movies) {
                bw.write(m.toCSV());
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- SEAT ---
    public List<Seat> loadSeats() {
        List<Seat> seats = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SEAT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    // SỬA Ở ĐÂY: Chuyển đổi từ text/boolean sang Enum SeatStatus
                    SeatStatus status;
                    if (parts[2].equalsIgnoreCase("true") || parts[2].equalsIgnoreCase("BOOKED")) {
                        status = SeatStatus.BOOKED;
                    } else {
                        status = SeatStatus.AVAILABLE;
                    }

                    // Truyền 'status' (kiểu Enum) vào thay vì 'isBooked' (kiểu boolean)
                    if (parts[1].equals("VIP")) {
                        seats.add(new VipSeat(parts[0], status));
                    } else {
                        seats.add(new StandardSeat(parts[0], status));
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }

        // Tạo dữ liệu mẫu nếu file trống (CŨNG CẦN SỬA LẠI CHO ĐÚNG KIỂU ENUM)
        if (seats.isEmpty()) {
            for(int i=1; i<=5; i++) seats.add(new StandardSeat("A"+i, SeatStatus.AVAILABLE));
            for(int i=1; i<=5; i++) seats.add(new VipSeat("B"+i, SeatStatus.AVAILABLE));
            saveAllSeats(seats);
        }
        return seats;
    }

    public void saveAllSeats(List<Seat> seats) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SEAT_FILE))) {
            for (Seat s : seats) {
                bw.write(s.toCSV());
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- TICKET ---
    public void appendTicket(Ticket ticket) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TICKET_FILE, true))) {
            bw.write(ticket.toCSV());
            bw.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void saveAllTickets(List<String> ticketLines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TICKET_FILE))) {
            for (String line : ticketLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<String> loadTicketLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) { e.printStackTrace(); }
        return lines;
    }
}