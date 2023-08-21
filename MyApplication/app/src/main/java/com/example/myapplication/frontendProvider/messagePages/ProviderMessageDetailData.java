package com.example.myapplication.frontendProvider.messagePages;

public class ProviderMessageDetailData {
    private String sender;
    private String content;
    private String time;

    public ProviderMessageDetailData(String sender, String content, String time) {
        this.sender = sender;
        this.content = content;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
