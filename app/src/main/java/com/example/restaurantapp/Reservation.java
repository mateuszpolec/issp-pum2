package com.example.restaurantapp;

import java.util.Date;
import java.util.UUID;

public class Reservation {

    private UUID mId;
    private Date mDate;
    private String mComments;
    private int mCount;

    public Reservation()
    {
        this.mId = UUID.randomUUID();
        this.mDate = new Date();
    }

    public Reservation(UUID id, Date date, String comments, int count)
    {
        this.mId = id;
        this.mDate = date;
        this.mComments = comments;
        this.mCount = count;
    }

    public void setDate(Date date) { this.mDate = date; }
    public Date getDate() { return this.mDate; }

    public void setComments(String comments) { this.mComments = comments; }
    public String getComments() { return this.mComments; }

    public void setCount(int count) { this.mCount = count; }
    public int getCount() { return this.mCount; }

    public UUID getId() { return this.mId; }

}
