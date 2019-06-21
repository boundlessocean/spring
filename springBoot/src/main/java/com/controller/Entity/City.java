package com.controller.Entity;

public class City {

    private String code;
    private String address;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "City{" +
                "code='" + code + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
