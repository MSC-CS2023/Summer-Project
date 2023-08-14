package com.example.myapplication.frontendProvider.profilePages;

public class ProviderTransactionCardData {
    private String name;
    private String type;
    private String time;
    private String price;

    public ProviderTransactionCardData(String name, String type, String time, String price) {
        this.name = name;
        this.type = type;
        this.time = time;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
