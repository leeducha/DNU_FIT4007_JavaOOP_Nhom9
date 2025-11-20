package model;

public class Movie {
    private String id;
    private String title;
    private double basePrice;

    public Movie(String id, String title, double basePrice) {
        this.id = id;
        this.title = title;
        this.basePrice = basePrice;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getBasePrice() { return basePrice; }
    public void setTitle(String title) { this.title = title; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    @Override
    public String toString() { return String.format("[%s] %-25s | %.0f VND", id, title, basePrice); }
    public String toCSV() { return id + "," + title + "," + basePrice; }
}