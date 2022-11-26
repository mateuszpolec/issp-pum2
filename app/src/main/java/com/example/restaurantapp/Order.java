package com.example.restaurantapp;

import java.util.UUID;

public class Order {

    // TODO: Dodać status opłacenia.

    enum Status
    {
        ORDERED,
        IN_PROGRESS,
        IN_DELIVERY,
        DELIVERED,
        CANCELLED
    }

    private UUID mId;
    private UUID mCustomerID;
    private String mDishNames;
    private String mComments;
    private String mAddress;
    private int mTelephone;
    private int mTotalPrice;
    private int mTableNumber;
    private Order.Status mStatus;

    public Order()
    {
        this.mId = UUID.randomUUID();
    }

    public Order(UUID id, UUID customerID, String dishNames, String comments, String address, int telephone, int totalPrice, int tableNumber, Order.Status status)
    {
        this.mId = id;
        this.mCustomerID = customerID;
        this.mDishNames = dishNames;
        this.mComments = comments;
        this.mAddress = address;
        this.mTelephone = telephone;
        this.mTotalPrice = totalPrice;
        this.mTableNumber = tableNumber;
        this.mStatus = status;
    }

    public void setCustomerID(UUID customerID) { this.mCustomerID = customerID; }
    public UUID getCustomerID() { return this.mCustomerID; }

    public void setDishNames(String dishNames) { this.mDishNames = dishNames; }
    public String getDishNames() { return this.mDishNames; }

    public void setComments(String comments) { this.mComments = comments; }
    public String getComments() { return this.mComments; }

    public void setAddress(String address) { this.mAddress = address; }
    public String getAddress() { return this.mAddress; }

    public void setTelephone(int telephone) { this.mTelephone = telephone; }
    public int getTelephone() { return this.mTelephone; }

    public void setTotalPrice(int totalPrice) { this.mTotalPrice = totalPrice; }
    public int getTotalPrice() { return this.mTotalPrice; }

    public void setTableNumber(int tableNumber) { this.mTableNumber = tableNumber; }
    public int getTableNumber() { return this.mTableNumber; }

    public void setStatus(Order.Status status) { this.mStatus = status; }
    public Order.Status getStatus() { return this.mStatus; }

    public UUID getId() { return this.mId; }

}
