package com.example.myapplication.Bean.Httpdata;

public class Session {
    /**
     * Session id.
     */
    private Long id;
    /**
     * The content of the message.
     */
    private String message;
    /**
     * Recipient's user id.
     */
    private Long recipientId;
    /**
     * Recipient's user type.
     */
    private String recipientType;
    /**
     * Sender's user id.
     */
    private Long senderId;
    /**
     * Sender's user type.
     */
    private String senderType;
    /**
     * The time when the message sent.
     */
    private Long timestamp;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public Long getRecipientId() { return recipientId; }
    public void setRecipientId(Long value) { this.recipientId = value; }

    public String getRecipientType() { return recipientType; }
    public void setRecipientType(String value) { this.recipientType = value; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long value) { this.senderId = value; }

    public String getSenderType() { return senderType; }
    public void setSenderType(String value) { this.senderType = value; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long value) { this.timestamp = value; }
}
