package com.example.myapplication.frontendProvider.homePages;

public class ProviderServiceCardData {
    private Long serviceId;
    private String title;
    private String description;
    private String price;
    private String imageSrc;

    public ProviderServiceCardData(String title, String description, String price, String imageSrc, Long serviceId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageSrc = imageSrc;
        this.serviceId = serviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

}
