package com.boundless.model;

import java.util.Date;

public class CustomDate {

    private Date date;

    public CustomDate(Date date){
        setDate(date);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.date.toString();
    }
}