package model;

public class Customer extends Person {

    public Customer(String id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + email + "," + phone;
    }

    @Override
    public String toString() {
        return "[Khách Hàng] " + super.toString();
    }
}