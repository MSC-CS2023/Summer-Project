package com.example.myapplication.Bean.Httpdata;

public class ServiceShort {
    /**
     * The fee of a service.
     */
    private Double fee;
    /**
     * The unique id of a service.
     */
    private Long id;
    /**
     * The id of its provider.
     */
    private Long providerId;
    /**
     * The title of a service.
     */
    private String title;

    public Double getFee() { return fee; }
    public void setFee(Double value) { this.fee = value; }

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public Long getProviderId() { return providerId; }
    public void setProviderId(Long value) { this.providerId = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }
}
