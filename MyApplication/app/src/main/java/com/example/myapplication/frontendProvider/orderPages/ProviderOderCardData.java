package com.example.myapplication.frontendProvider.orderPages;

public class ProviderOderCardData {

    private String title;
    private Long orderNum;
    private Double price;
    private String imageSrc;
    private String state;

    public ProviderOderCardData(String title, Long orderNum, Double price, String imageSrc, String state) {
        this.title = title;
        this.orderNum = orderNum;
        this.price = price;
        this.imageSrc = imageSrc;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
