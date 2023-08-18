package com.example.myapplication.network;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.Bean.Httpdata.data.MarkData;
import com.example.myapplication.Bean.Httpdata.data.PictureListData;
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PublicMethodApi {

    //PUBLIC USER INFORMATION
    //CUSTOMER
    @GET("public/customer/avatar")
    Call<ResponseBody> getCustomerAvatar(@Query("id") Long customerId);

    //PROVIDER
    @GET("public/service_provider/avatar")
    Call<ResponseBody> getProviderAvatar(@Query("id") Long providerId);

    @GET("public/service_provider/services")
    Flowable<HttpBaseBean<ServiceShortListData>> getProviderServices(
            @Query("id") Long providerId,
            @Query("start") Integer start, @Query("num") Integer number);

    //NORMAL
    @GET("get_service")
    Flowable<HttpBaseBean<ServiceDetailData>> getServiceDetail(@Query("id") Long serviceId);

    @GET("get_pics_menu")
    Flowable<HttpBaseBean<PictureListData>> getPictureMenu(@Query("id") Long serviceId);

    @GET("get_pic")
    Call<ResponseBody> getPicture(@Query("id") Long pictureId);

    @GET("search")
    Flowable<HttpBaseBean<ServiceShortListData>> search(
            @Query("keywords") String searchKeyword, @Query("sort_by") String sortType,
            @Query("descending") Boolean isDescending, @Query("start") Integer startPosition,
            @Query("num") Integer displayNumber, @Query("or") Boolean isOr);

    @GET("get_random")
    Flowable<HttpBaseBean<ServiceShortListData>> recommendServicesRandomly(@Query("num") Integer recommendNumber);

    @POST("customer_login")
    @Multipart
    Flowable<HttpBaseBean<LoginData>> customerLogin(@Part("email") String email , @Part("password") String password);

    @POST("service_provider_login")
    @Multipart
    Flowable<HttpBaseBean<LoginData>> providerLogin(@Part("email") String email , @Part("password") String password);

    @PUT("customer_register")
    @Multipart
    Flowable<HttpBaseBean<LoginData>> customerRegister(
            @Part("email") String email, @Part("username") String username,
            @Part("password") String password, @Part("address") String address,
            @Part("tel") String tel);

    @PUT("service_provider_register")
    @Multipart
    Flowable<HttpBaseBean<LoginData>> providerRegister(
            @Part("email") String email, @Part("username") String username,
            @Part("password") String password, @Part("address") String address,
            @Part("tel") String tel);

    //New
    @GET("get_mark")
    Flowable<HttpBaseBean<MarkData>> getMark(@Query("id") Long providerId);

    @GET("get_by_tag")
    Flowable<HttpBaseBean<ServiceShortListData>> getServiceByTag(
            @Query("tag") String tag, @Query("start") Integer startPosition,
            @Query("num") Integer displayNumber);
}
