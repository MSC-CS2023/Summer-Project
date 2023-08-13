package com.example.myapplication.Bean.Httpdata;

public class Favourite {
    /**
     * The customer id.
     */
    private Long customerId;
    /**
     * The favourite id.
     */
    private Long id;
    /**
     * The service id.
     */
    private Long serviceId;
    /**
     * A short service of the id.
     */
    private ServiceShort serviceShort;
    /**
     * The time when it is added to the favourite.
     */
    private Long timestamp;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long value) { this.customerId = value; }

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long value) { this.serviceId = value; }

    public ServiceShort getServiceShort() { return serviceShort; }
    public void setServiceShort(ServiceShort value) { this.serviceShort = value; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long value) { this.timestamp = value; }
}