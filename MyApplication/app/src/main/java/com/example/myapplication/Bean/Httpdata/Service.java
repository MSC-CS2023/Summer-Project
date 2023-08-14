package com.example.myapplication.Bean.Httpdata;

public class Service {
    private String address;
    /**
     * The short description of a service.
     */
    private String description;
    /**
     * The fee of the service.
     */
    private Double fee;
    /**
     * The unique id of a service.
     */
    private Long id;
    private String pictureId;
    /**
     * The id of the provider of the service.
     */
    private Long providerId;
    private String tag;
    /**
     * The last time when the service data is updated.
     */
    private Long timestamp;
    /**
     * The title of a service.
     */
    private String title;
    private String username;

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

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

    public String getTag() { return tag; }
    public void setTag(String value) { this.tag = value; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long value) { this.timestamp = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }
}