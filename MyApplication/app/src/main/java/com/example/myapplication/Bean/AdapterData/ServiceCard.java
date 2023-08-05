package com.example.myapplication.Bean.AdapterData;

import com.example.myapplication.R;

public class ServiceCard {
    private String username;
    private String providerAvatarSrc;
    private String serviceTitle;
    private String servicePrice;

    //to add
    private String serviceInfo;
    private int collectionSrcId;
    private int serviceImgSrcId;
    private String state;
    private int avatarSrcId;

    public ServiceCard(String username, String servicePrice, String serviceTitle, String providerAvatarSrc){
        this.username = username;
        this.servicePrice = servicePrice;
        this.serviceTitle = serviceTitle;
        this.providerAvatarSrc = providerAvatarSrc;

        //to add
        this.serviceInfo = "some info balabalabala";
        this.state = "available tomorrow";
        this.serviceImgSrcId = R.drawable.img_sample1;
        this.collectionSrcId = R.drawable.btn_emptyheart;
        this.avatarSrcId = R.drawable.btn_avatar1;
    }

    public ServiceCard() {
    }

    public String getProviderAvatarSrc() {
        return providerAvatarSrc;
    }

    public void setProviderAvatarSrc(String providerAvatarSrc) {
        this.providerAvatarSrc = providerAvatarSrc;
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

    public int getCollectionSrcId() {
        return collectionSrcId;
    }

    public void setCollectionSrcId(int collectionSrcId) {
        this.collectionSrcId = collectionSrcId;
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

    public int getAvatarSrcId() {
        return avatarSrcId;
    }

    public void setAvatarSrcId(int setAvatarSrcId) {
        this.state = state;
    }
}
