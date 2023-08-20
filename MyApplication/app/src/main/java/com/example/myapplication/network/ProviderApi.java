package com.example.myapplication.network;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.BalanceData;
import com.example.myapplication.Bean.Httpdata.data.GetMessageData;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.Bean.Httpdata.data.ModifyDetailData;
import com.example.myapplication.Bean.Httpdata.data.OrderData;
import com.example.myapplication.Bean.Httpdata.data.OrderListData;
import com.example.myapplication.Bean.Httpdata.data.SelfDetailData;
import com.example.myapplication.Bean.Httpdata.data.SendMessageData;
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;
import com.example.myapplication.Bean.Httpdata.data.TimeStampData;

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
    @GET("service_provider/service/get")
    Flowable<HttpBaseBean<ServiceShortListData>> getProviderServices(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @POST("service_provider/service/modify")
    @Multipart
    Flowable<HttpBaseBean<ServiceDetailData>> modifyService(
            @Header("Authorization") String authorization, @Part("service_id") Long serviceId,
            @Part("key") String modifyItem, @Part("value") String modifyContent);

    @PUT("service_provider/service/add_pic")
    @Multipart
    Flowable<HttpBaseBean<Object>> addPictureToService(
            @Header("Authorization") String authorization, @Part("service_id") Long serviceId,
            @Part MultipartBody.Part part);

    @PUT("service_provider/service/add")
    @Multipart
    Flowable<HttpBaseBean<ServiceDetailData>> addService(
            @Header("Authorization") String authorization, @Part("title") String serviceTitle,
            @Part("description") String serviceDescription, @Part("fee") Double serviceFee, @Part("tag") String tag);

    @DELETE("service_provider/service/delete")
    Flowable<HttpBaseBean<Object>> deleteService(
            @Header("Authorization") String authorization, @Query("service_id") Long serviceId);

    @DELETE("service_provider/service/delete_pic")
    Flowable<HttpBaseBean<Object>> deleteServicePicture(
            @Header("Authorization") String authorization, @Query("id") Long pictureId);

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

    @GET("service_provider/booking_order/get_unconfirmed")
    Flowable<HttpBaseBean<OrderListData>> getProviderUnconfirmedOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("service_provider/booking_order/get_rejected")
    Flowable<HttpBaseBean<OrderListData>> getProviderRejectedOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("service_provider/booking_order/get_canceled")
    Flowable<HttpBaseBean<OrderListData>> getProviderCanceledOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("service_provider/booking_order/get_finished")
    Flowable<HttpBaseBean<OrderListData>> getProviderFinishedOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("service_provider/booking_order/get_processing")
    Flowable<HttpBaseBean<OrderListData>> getProviderProcessingOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    //NORMAL
    @GET("service_provider/self_details")
    Flowable<HttpBaseBean<SelfDetailData>> getProviderDetail(@Header("Authorization") String authorization);

    @GET("service_provider/get_avatar")
    Flowable<ResponseBody> getProviderAvatar(@Header("Authorization") String authorizations);

    @GET("service_provider/get_avatar_timestamp")
    Flowable<HttpBaseBean<TimeStampData>> getProviderAvatarTimeStamp(@Header("Authorization") String authorization);

    @GET("service_provider/renew_token")
    Flowable<HttpBaseBean<LoginData>> providerReLogin(@Header("Authorization") String authorization);

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

    //NEW
    @GET("service_provider/booking_order/get_by_date")
    Flowable<HttpBaseBean<OrderListData>> getProviderOrdersByDate(
            @Header("Authorization") String authorization,
            @Query("day") Integer day, @Query("month") Integer month, @Query("year") Integer year);

    //NEW BALANCE
    @GET("service_provider/balance/get")
    Flowable<HttpBaseBean<BalanceData>> getProviderBalance(@Header("Authorization") String authorization);

    @POST("service_provider/balance/deposit")
    @Multipart
    Flowable<HttpBaseBean<BalanceData>> providerDeposit(
            @Header("Authorization") String authorization, @Part("money") Double money);


    @POST("service_provider/balance/withdraw")
    @Multipart
    Flowable<HttpBaseBean<BalanceData>> providerWithdraw(
            @Header("Authorization") String authorization, @Part("money") Double money);

}
