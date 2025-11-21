package model;

public class VipSeat extends Seat {
    public VipSeat(String seatId, SeatStatus status) {
        super(seatId, status);
    }
    @Override
    public double getSurcharge() { return 20000; } // Phụ thu 20k
    @Override
    public String toCSV() { return seatId + ",VIP," + status; }
}