package com.example.myapplication.network;

import com.example.myapplication.Bean.HttpBaseBean;
import com.example.myapplication.Bean.Service;
import com.example.myapplication.Bean.ServiceShort;
import com.example.myapplication.Bean.data.GetMessageData;
import com.example.myapplication.Bean.data.ModifyDetailData;
import com.example.myapplication.Bean.data.OrderData;
import com.example.myapplication.Bean.data.OrderListData;
import com.example.myapplication.Bean.data.SelfDetailData;
import com.example.myapplication.Bean.data.SendMessageData;
import com.example.myapplication.Bean.data.ServiceDetailData;
import com.example.myapplication.Bean.data.ServiceShortListData;
import com.example.myapplication.Bean.data.TimeStampData;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ProviderApi {

    //SESSIONS
    @GET("service_provider/session/get")
    Flowable<HttpBaseBean<GetMessageData>> getMessage(
            @Header("Authorization") String authorization, @Query("after") Long after, @Query("until") Long until);

    @PUT("service_provider/session/send")
    @Multipart
    Flowable<HttpBaseBean<SendMessageData>> sendMessage(
            @Header("Authorization") String authorization, @Part("recipient_id") Long recipientId,
            @Part("recipient_type") String recipientType, @Part("message") String message);

    //SERVICE
    @GET("service_provider/services/get")
    Flowable<HttpBaseBean<ServiceShortListData>> getProviderServices(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @POST("service_provider/service/modify")
    @Multipart
    Flowable<HttpBaseBean<ServiceDetailData>> modifyService(
            @Header("Authorization") String authorization, @Part("service_id") Long serviceId,
            @Part("key") String modifyItem, @Part("value") String ModifyConte);

    @POST("service_provider/service/add_pic")
    @Multipart
    Flowable<HttpBaseBean<Object>> addPictureToService(
            @Header("Authorization") String authorization, @Part("service_id") Long serviceId,
            @Part MultipartBody.Part part);

    @POST("service_provider/service/add")
    @Multipart
    Flowable<HttpBaseBean<ServiceDetailData>> addService(
            @Header("Authorization") String authorization, @Part("title") String serviceTitle,
            @Part("description") String serviceDescription, @Part("detail") String serviceDetail,
            @Part("fee") Double serviceFee);

    @DELETE("service_provider/service/delete")
    @Multipart
    Flowable<HttpBaseBean<Object>> deleteService(
            @Header("Authorization") String authorization, @Part("service_id") Long serviceId);

    @DELETE("service_provider/service/delete_pic")
    @Multipart
    Flowable<HttpBaseBean<Object>> deleteServicePicture(
            @Header("Authorization") String authorization, @Part("id") Long pictureId);

    //ORDER
    @GET("service_provider/booking_order/get")
    Flowable<HttpBaseBean<OrderListData>> getProviderOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("service_provider/booking_order/get_one")
    Flowable<HttpBaseBean<OrderData>> getCertainProviderOrder(
            @Header("Authorization") String authorization, @Query("id") Long orderId);

    @GET("service_provider/booking_order/advanced")
    Flowable<HttpBaseBean<OrderListData>> searchProviderOrder(
            @Header("Authorization") String authorization, @Query("keys") String keyword,
            @Query("or") Boolean isOr, @Query("start") Integer start, @Query("num") Integer number,
            @Query("not") Boolean isNot);

    @POST("service_provider/booking_order/confirm")
    @Multipart
    Flowable<HttpBaseBean<OrderData>> confirmProviderOrder(
            @Header("Authorization") String authorization, @Part("id") Long orderId);

    @POST("service_provider/booking_order/reject")
    @Multipart
    Flowable<HttpBaseBean<OrderData>> rejectProviderOrder(
            @Header("Authorization") String authorization, @Part("id") Long orderId);

    //NORMAL
    @GET("service_provider/self_details")
    Flowable<HttpBaseBean<SelfDetailData>> getProviderDetail(@Header("Authorization") String authorization);

    @GET("service_provider/get_avatar")
    Call<ResponseBody> getProviderAvatar(@Header("Authorization") String authorizations);

    @GET("service_provider/get_avatar_timestamp")
    Flowable<HttpBaseBean<TimeStampData>> getProviderAvatarTimeStamp(@Header("Authorization") String authorization);

    @POST("service_provider/modify_detail")
    @Multipart
    Flowable<HttpBaseBean<ModifyDetailData>> modifyProviderDetail(
            @Header("Authorization") String authorization,
            @Part("key") String modifyItem, @Part("value") String ModifyContent);

    @POST("service_provider/update_avatar")
    @Multipart
    Flowable<HttpBaseBean<TimeStampData>> updateProviderAvatar(
            @Header("Authorization") String authorization, @Part MultipartBody.Part part);

    @POST("service_provider/reset_password")
    @Multipart
    Flowable<HttpBaseBean<Object>> resetProviderPassword(
            @Header("Authorization") String authorization,
            @Part("old_value") String oldPassword, @Part("new_value") String newPassword);

    @DELETE("service_provider/delete_account")
    Flowable<HttpBaseBean<Object>> deleteProviderAccount(@Header("Authorization") String authorization);

}