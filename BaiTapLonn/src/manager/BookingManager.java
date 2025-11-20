package manager;

import exceptions.SeatAlreadyBookedException;
import model.*;
import java.io.*;
import java.util.*;

public class BookingManager implements Persistable {
    private List<Seat> seats = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private final String SEAT_FILE = "data/seats.csv";
    private final String TICKET_FILE = "data/tickets.csv";

    public BookingManager() {
        new File("data").mkdirs();
        loadData();
        if (seats.isEmpty()) initSeats();
    }

    private void initSeats() {
        for (int i = 1; i <= 5; i++) seats.add(new StandardSeat("A" + i, SeatStatus.AVAILABLE));
        for (int i = 1; i <= 5; i++) seats.add(new VipSeat("B" + i, SeatStatus.AVAILABLE));
        saveData();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Ticket bookTicket(Movie movie, String seatId) throws SeatAlreadyBookedException {
        for (Seat seat : seats) {
            if (seat.getSeatId().equalsIgnoreCase(seatId)) {
                if (seat.getStatus() == SeatStatus.BOOKED) {
                    throw new SeatAlreadyBookedException("Ghế " + seatId + " đã được đặt!");
                }
                seat.setStatus(SeatStatus.BOOKED);

                double price = movie.getBasePrice() + seat.getSurcharge();
                String tId = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
                Ticket ticket = new Ticket(tId, movie.getTitle(), seat.getSeatId(), price);

                tickets.add(ticket);
                saveData();
                return ticket;
            }
        }
        throw new IllegalArgumentException("Mã ghế không tồn tại!");
    }

    public boolean cancelTicket(String ticketId) {
        Ticket target = null;
        for (Ticket t : tickets) {
            if (t.getTicketId().equalsIgnoreCase(ticketId)) {
                target = t;
                break;
            }
        }

        if (target != null) {
            tickets.remove(target);
            for (Seat s : seats) {
                if (s.getSeatId().equalsIgnoreCase(target.getSeatId())) {
                    s.setStatus(SeatStatus.AVAILABLE);
                    break;
                }
            }
            saveData();
            return true;
        }
        return false;
    }

    public Ticket findTicket(String ticketId) {
        for (Ticket t : tickets) {
            if (t.getTicketId().equalsIgnoreCase(ticketId)) return t;
        }
        return null;
    }

    @Override
    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SEAT_FILE))) {
            for (Seat s : seats) {
                bw.write(s.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TICKET_FILE))) {
            for (Ticket t : tickets) {
                bw.write(t.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        File sFile = new File(SEAT_FILE);
        if (sFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(sFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");

                    // --- BẮT ĐẦU ĐOẠN SỬA ---
                    SeatStatus status;
                    // Kiểm tra nếu file cũ lưu là "true"/"false" hoặc file mới lưu "BOOKED"
                    if (parts[2].equalsIgnoreCase("true") || parts[2].equalsIgnoreCase("BOOKED")) {
                        status = SeatStatus.BOOKED;
                    } else {
                        status = SeatStatus.AVAILABLE;
                    }
                    // --- KẾT THÚC ĐOẠN SỬA ---

                    if (parts[1].equals("VIP")) seats.add(new VipSeat(parts[0], status));
                    else seats.add(new StandardSeat(parts[0], status));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // --- PHẦN 2: LOAD VÉ (Giữ nguyên) ---
        File tFile = new File(TICKET_FILE);
        if (tFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(tFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        tickets.add(new Ticket(parts[0], parts[1], parts[2], Double.parseDouble(parts[3])));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}