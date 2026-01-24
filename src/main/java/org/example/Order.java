package org.example;

public class Order {

    private String orderId;
    private String bookId;
    private String customerName;
    private int quantity;
    private String status;
    private long timestamp;

    public Order() {
    }

    public Order(String orderId, String bookId, String customerName, int quantity, String status, long timestamp) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.customerName = customerName;
        this.quantity = quantity;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
