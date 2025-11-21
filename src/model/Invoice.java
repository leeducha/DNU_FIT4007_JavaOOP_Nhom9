package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private String invoiceId;
    private String ticketId;
    private double amount;
    private String paymentTime;

    public Invoice(String invoiceId, String ticketId, double amount) {
        this.invoiceId = invoiceId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.paymentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String toCSV() {
        return invoiceId + "," + ticketId + "," + amount + "," + paymentTime;
    }

    @Override
    public String toString() {
        return String.format("Hóa đơn [%s] - Vé: %s - Tiền: %.0f VND - Ngày: %s",
                invoiceId, ticketId, amount, paymentTime);
    }
}