package com.example.myapplication.Bean.Httpdata;

public class Picture {
    /**
     * The image id.
     */
    private Long id;
    /**
     * The time when the image uploaded.
     */
    private String timestamp;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String value) { this.timestamp = value; }
}
