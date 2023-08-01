package com.example.myapplication.Bean;

public class Favourite {
    /**
     * The customer id.
     */
    private long customerId;
    /**
     * The favourite id.
     */
    private long id;
    /**
     * The service id.
     */
    private long serviceId;
    /**
     * A short service of the id.
     */
    private ServiceShort serviceShort;
    /**
     * The time when it is added to the favourite.
     */
    private long timestamp;

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long value) { this.customerId = value; }

    public long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public long getServiceId() { return serviceId; }
    public void setServiceId(long value) { this.serviceId = value; }

    public ServiceShort getServiceShort() { return serviceShort; }
    public void setServiceShort(ServiceShort value) { this.serviceShort = value; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long value) { this.timestamp = value; }
}