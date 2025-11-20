package model;

public abstract class Person {
    protected String id;
    protected String name;
    protected String email;
    protected String phone;

    public Person(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    // Phương thức abstract để ép các lớp con phải có cách lưu CSV riêng
    public abstract String toCSV();

    @Override
    public String toString() {
        return String.format("ID: %s | Tên: %s | SĐT: %s", id, name, phone);
    }
}