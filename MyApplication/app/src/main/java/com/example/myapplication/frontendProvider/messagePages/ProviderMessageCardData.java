package com.example.myapplication.frontendProvider.messagePages;

public class ProviderMessageCardData {
    private String imageSrc;
    private String username;
    private String latestMessage;

    public ProviderMessageCardData(String imageSrc, String username, String latestMessage) {
        this.imageSrc = imageSrc;
        this.username = username;
        this.latestMessage = latestMessage;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }
}
