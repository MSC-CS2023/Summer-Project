package com.example.myapplication.Bean;

public class Service {
    /**
     * The short description of a service.
     */
    private String description;
    /**
     * The Long detail information of a service.
     */
    private String detail;
    /**
     * The fee of the service.
     */
    private Double fee;
    /**
     * The unique id of a service.
     */
    private Long id;
    /**
     * The id of the provider of the service.
     */
    private Long providerId;
    /**
     * The last time when the service data is updated.
     */
    private Long timestamp;
    /**
     * The title of a service.
     */
    private String title;

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getDetail() { return detail; }
    public void setDetail(String value) { this.detail = value; }

    public Double getFee() { return fee; }
    public void setFee(Double value) { this.fee = value; }

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public Long getProviderId() { return providerId; }
    public void setProviderId(Long value) { this.providerId = value; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long value) { this.timestamp = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }
}