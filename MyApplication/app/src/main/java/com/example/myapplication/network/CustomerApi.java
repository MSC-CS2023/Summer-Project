package com.example.myapplication.network;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.BalanceData;
import com.example.myapplication.Bean.Httpdata.data.FavouriteData;
import com.example.myapplication.Bean.Httpdata.data.FavouriteListData;
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
import com.google.api.Http;

import java.util.List;
import java.util.function.BooleanSupplier;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.flow.Flow;
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

public interface CustomerApi {

    //SESSIONS
    @GET("customer/session/get")
    Flowable<HttpBaseBean<GetMessageData>> getMessage(
            @Header("Authorization") String authorization, @Query("after") Long after, @Query("until") Long until);

    @PUT("customer/session/send")
    @Multipart
    Flowable<HttpBaseBean<SendMessageData>> sendMessage(
            @Header("Authorization") String authorization, @Part("recipient_id") Long recipientId,
            @Part("recipient_type") String recipientType, @Part("message") String message);

    //FAVOURITE
    @GET("customer/favourite/get")
    Flowable<HttpBaseBean<FavouriteListData>> getCustomerFavourites(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("customer/favourite/check")
    Flowable<HttpBaseBean<Object>> checkIfInFavourite(
            @Header("Authorization") String authorization, @Query("id") Long serviceId);

    @PUT("customer/favourite/add")
    @Multipart
    Flowable<HttpBaseBean<FavouriteData>> addToFavourite(
            @Header("Authorization") String authorization, @Part("id") Long serviceId);

    @DELETE("customer/favourite/delete")
    Flowable<HttpBaseBean<Object>> removeFromFavourite(
            @Header("Authorization") String authorization, @Query("id") Long serviceId);


    //BOOKING ORDER
    @GET("customer/booking_order/get")
    Flowable<HttpBaseBean<OrderListData>> getCustomerOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("customer/booking_order/get_one")
    Flowable<HttpBaseBean<OrderData>> getCertainCustomerOrder(
            @Header("Authorization") String authorization, @Query("id") Long orderId);

    @GET("customer/booking_order/advanced")
    Flowable<HttpBaseBean<OrderListData>> searchCustomerOrder(
            @Header("Authorization") String authorization, @Query("keys") String keyword,
            @Query("or") Boolean isOr, @Query("start") Integer start, @Query("num") Integer number,
            @Query("not") Boolean isNot);

    @POST("customer/booking_order/cancel")
    @Multipart
    Flowable<HttpBaseBean<OrderData>> cancelCustomerOrder(
            @Header("Authorization") String authorization, @Part("id") Long orderId);

    @POST("customer/booking_order/finish")
    @Multipart
    Flowable<HttpBaseBean<OrderData>> finishCustomerOrder(
            @Header("Authorization") String authorization, @Part("id") Long orderId);

    @POST("customer/booking_order/mark")
    @Multipart
    Flowable<HttpBaseBean<OrderData>> markOrder(
            @Header("Authorization") String authorization, @Part("id") Long orderId,
            @Part("mark") Integer orderMark);

    @PUT("customer/booking_order/create")
    @Multipart
    Flowable<HttpBaseBean<OrderData>> createCustomerOrder(
            @Header("Authorization") String authorization, @Part("service_id") Long serviceId,
            @Part("start_timestamp") Long startTimestamp, @Part("end_timestamp") Long endTimestamp);

    @GET("customer/booking_order/get_unconfirmed")
    Flowable<HttpBaseBean<OrderListData>> getCustomerUnconfirmedOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("customer/booking_order/get_rejected")
    Flowable<HttpBaseBean<OrderListData>> getCustomerRejectedOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("customer/booking_order/get_canceled")
    Flowable<HttpBaseBean<OrderListData>> getCustomerCanceledOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("customer/booking_order/get_finished")
    Flowable<HttpBaseBean<OrderListData>> getCustomerFinishedOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);

    @GET("customer/booking_order/get_processing")
    Flowable<HttpBaseBean<OrderListData>> getCustomerProcessingOrders(
            @Header("Authorization") String authorization,
            @Query("start") Integer start, @Query("num") Integer number);


    //NORMAL
    @GET("customer/self_details")
    Flowable<HttpBaseBean<SelfDetailData>> getCustomerDetail(@Header("Authorization") String authorization);

    @GET("customer/get_avatar")
    Call<ResponseBody> getCustomerAvatar(@Header("Authorization") String authorizations);

    @GET("customer/get_avatar_timestamp")
    Flowable<HttpBaseBean<TimeStampData>> getCustomerAvatarTimeStamp(@Header("Authorization") String authorization);

    @GET("customer/get_service")
    Flowable<HttpBaseBean<ServiceDetailData>> customerGetServiceDetail(
            @Header("Authorization") String authorization, @Query("id") Long serviceId);

    @GET("customer/get_rec")
    Flowable<HttpBaseBean<ServiceShortListData>> randomlyRecommend(
            @Header("Authorization") String authorization, @Query("num") Integer recommendNum,
            @Query("list") String[] serviceList);

    @GET("customer/renew_token")
    Flowable<HttpBaseBean<LoginData>> customerReLogin(@Header("Authorization") String authorization);

    @POST("customer/modify_detail")
    @Multipart
    Flowable<HttpBaseBean<ModifyDetailData>> modifyCustomerDetail(@Header("Authorization") String authorization
            , @Part("key") String modifyItem, @Part("value") String ModifyContent);

    @POST("customer/reset_password")
    @Multipart
    Flowable<HttpBaseBean<Object>> resetCustomerPassword(@Header("Authorization") String authorization
            , @Part("old_value") String oldPassword, @Part("new_value") String newPassword);

    @POST("customer/update_avatar")
    @Multipart
    Flowable<HttpBaseBean<TimeStampData>> updateCustomerAvatar(@Header("Authorization") String authorization
            , @Part MultipartBody.Part part);

    @DELETE("customer/delete_account")
    Flowable<HttpBaseBean<Object>> deleteCustomerAccount(@Header("Authorization") String authorization);


    //NEW
    @GET("customer/booking_order/get_by_date")
    Flowable<HttpBaseBean<OrderListData>> getCustomerOrdersByDate(
            @Header("Authorization") String authorization, @Query("timestamp") Long timestamp);

    //NEW BALANCE
    @GET("customer/balance/get")
    Flowable<HttpBaseBean<BalanceData>> getCustomerBalance(@Header("Authorization") String authorization);

    @POST("customer/balance/deposit")
    @Multipart
    Flowable<HttpBaseBean<BalanceData>> customerDeposit(
            @Header("Authorization") String authorization, @Part("money") Double money);

    @POST("customer/balance/witihdraw")
    @Multipart
    Flowable<HttpBaseBean<BalanceData>> customerWithdraw(
            @Header("Authorization") String authorization, @Part("money") Double money);

}
