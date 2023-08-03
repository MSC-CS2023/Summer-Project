package com.example.myapplication.Bean.AdapterData;

public class ServiceCard {


        private int avatarSrcId;
        private String username;
        private String serviceInfo;
        private int colletionSrcId;
        private int serviceImgSrcId;
        private String serviceTitle;
        private String servicePrice;
        private String state;

    public ServiceCard(int avatarSrcId, String username, String serviceInfo, int colletionSrcId, int serviceImgSrcId, String serviceTitle, String servicePrice, String state) {
        this.avatarSrcId = avatarSrcId;
        this.username = username;
        this.serviceInfo = serviceInfo;
        this.colletionSrcId = colletionSrcId;
        this.serviceImgSrcId = serviceImgSrcId;
        this.serviceTitle = serviceTitle;
        this.servicePrice = servicePrice;
        this.state = state;
    }

    public ServiceCard() {
    }

    public int getAvatarSrcId() {
        return avatarSrcId;
    }

    public void setAvatarSrcId(int avatarSrcId) {
        this.avatarSrcId = avatarSrcId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public int getColletionSrcId() {
        return colletionSrcId;
    }

    public void setColletionSrcId(int colletionSrcId) {
        this.colletionSrcId = colletionSrcId;
    }

    public int getServiceImgSrcId() {
        return serviceImgSrcId;
    }

    public void setServiceImgSrcId(int serviceImgSrcId) {
        this.serviceImgSrcId = serviceImgSrcId;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
