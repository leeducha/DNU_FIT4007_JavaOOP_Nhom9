package model;

public abstract class Seat {
    protected String seatId;
    protected SeatStatus status;

    public Seat(String seatId, SeatStatus status) {
        this.seatId = seatId;
        this.status = status;
    }

    public abstract double getSurcharge();

    public String getSeatId() { return seatId; }
    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }

    public abstract String toCSV();
}