package model;

public class StandardSeat extends Seat {
    public StandardSeat(String seatId, SeatStatus status) {
        super(seatId, status);
    }
    @Override
    public double getSurcharge() { return 0; }
    @Override
    public String toCSV() { return seatId + ",STANDARD," + status; }
}