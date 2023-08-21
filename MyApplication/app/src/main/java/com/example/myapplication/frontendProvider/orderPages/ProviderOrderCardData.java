package com.example.myapplication.frontendProvider.orderPages;

public class ProviderOrderCardData {

    private String title;
    private Long orderNum;
    private String price;
    private String imageSrc;
    private String state;
    private String imageLink;
    private String slotTime;

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public ProviderOrderCardData(String title, Long orderNum, String price, String imageSrc, String state, String imageLink
    , String slotTime) {
        this.title = title;
        this.orderNum = orderNum;
        this.price = price;
        this.imageSrc = imageSrc;
        this.state = state;
        this.imageLink = imageLink;
        this.slotTime = slotTime;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
