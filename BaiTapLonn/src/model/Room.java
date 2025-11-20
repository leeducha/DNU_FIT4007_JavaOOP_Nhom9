package model;

public class Room {
    private String roomId;
    private String roomName;
    private int totalSeats;

    public Room(String roomId, String roomName, int totalSeats) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.totalSeats = totalSeats;
    }

    public String getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }

    public String toCSV() {
        return roomId + "," + roomName + "," + totalSeats;
    }

    @Override
    public String toString() {
        return String.format("Phòng: %s (%s) - %d ghế", roomId, roomName, totalSeats);
    }
}