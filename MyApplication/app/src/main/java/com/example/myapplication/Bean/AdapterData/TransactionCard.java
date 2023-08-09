package com.example.myapplication.Bean.AdapterData;

public class TransactionCard {

    String name;
    String type;
    String time;
    String amount;

    public TransactionCard(String name, String type, String time, String amount) {
        this.name = name;
        this.type = type;
        this.time = time;
        this.amount = amount;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
