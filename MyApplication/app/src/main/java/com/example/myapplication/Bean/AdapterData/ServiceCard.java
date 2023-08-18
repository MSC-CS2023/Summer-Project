package com.example.myapplication.Bean.AdapterData;

import com.example.myapplication.R;

public class ServiceCard {
    private Long serviceId;

    private String username;
    private String providerAvatarSrc;
    private String serviceTitle;
    private String servicePrice;
    private String serviceInfo;
    private String servicePictureSrc;

    //to add
//    private int collectionSrcId;
    private int serviceImgSrcId;
//    private String state;
    private double mark;

    private int avatarSrcId;

//    public ServiceCard(String username, String servicePrice, String serviceTitle,
//                       String providerAvatarSrc, String serviceInfo, String servicePictureSrc, Long serviceId){
//        this.username = username;
//        this.servicePrice = servicePrice;
//        this.serviceTitle = serviceTitle;
//        this.providerAvatarSrc = providerAvatarSrc;
//        this.serviceInfo = serviceInfo;
//        this.servicePictureSrc = servicePictureSrc;
//        this.serviceId = serviceId;
//
//        //to add
//        this.mark = 4.0;
//        this.serviceImgSrcId = R.drawable.img_sample1;
//
//    }


    public ServiceCard(String username, String servicePrice, String serviceTitle,
                      String providerAvatarSrc, String serviceInfo, String servicePictureSrc, Long serviceId, Double mark) {
        this.serviceId = serviceId;
        this.username = username;
        this.providerAvatarSrc = providerAvatarSrc;
        this.serviceTitle = serviceTitle;
        this.servicePrice = servicePrice;
        this.serviceInfo = serviceInfo;
        this.servicePictureSrc = servicePictureSrc;
        this.serviceImgSrcId = R.drawable.img_sample1;
//        this.avatarSrcId = R.drawable.btn_avatar1;
        this.mark = mark;

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

//    public int getCollectionSrcId() {
//        return collectionSrcId;
//    }
//
//    public void setCollectionSrcId(int collectionSrcId) {
//        this.collectionSrcId = collectionSrcId;
//    }

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

//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }


    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getAvatarSrcId() {
        return avatarSrcId;
    }

//    public void setAvatarSrcId(int setAvatarSrcId) {
//        this.state = state;
//    }
//
//    public String getServicePictureSrc() {
//        return servicePictureSrc;
//    }


    public void setAvatarSrcId(int avatarSrcId) {
        this.avatarSrcId = avatarSrcId;
    }

    public void setServicePictureSrc(String servicePictureSrc) {
        this.servicePictureSrc = servicePictureSrc;
    }

    public String getServicePictureSrc() {
        return servicePictureSrc;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}

