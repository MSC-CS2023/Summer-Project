package com.example.myapplication.Bean.Httpdata;

public class ServiceShort {
    private String description;
    /**
     * The fee of a service.
     */
    private Double fee;
    /**
     * The unique id of a service.
     */
    private Long id;
    private String pictureId;
    /**
     * The id of its provider.
     */
    private Long providerId;
    /**
     * The title of a service.
     */
    private String title;
    /**
     * The provider's username.
     */
    private String username;

    private double mark;

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public ServiceShort(){

    }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public Double getFee() { return fee; }
    public void setFee(Double value) { this.fee = value; }

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public String getPictureId() { return pictureId; }
    public void setPictureId(String value) { this.pictureId = value; }

    public Long getProviderId() { return providerId; }
    public void setProviderId(Long value) { this.providerId = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }
}
