package com.gift.gemift.Network;

import com.gift.gemift.Model.AddFriendModel;
import com.gift.gemift.Model.ContributorApprovalInputModel;
import com.gift.gemift.Model.FriendCircleAddInterestResponse;
import com.gift.gemift.Model.FriendCircleMatch;
import com.gift.gemift.Model.FriendCircleResponse;
import com.gift.gemift.Model.FriendDataModel;
import com.gift.gemift.Model.FriendSearchModel;
import com.gift.gemift.Model.GetNewCategoryResponseItem;
import com.gift.gemift.Model.GetOccasionResponse;
import com.gift.gemift.Model.ImageUploadResponse;
import com.gift.gemift.Model.InitialFundRequestModel;
import com.gift.gemift.Model.InitiateFundModel;
import com.gift.gemift.Model.InsertCategory;
import com.gift.gemift.Model.InsertCategoryPersonel;
import com.gift.gemift.Model.InsertSubCategoryPersonnel;
import com.gift.gemift.Model.InsertSubCategoryResponse;
import com.gift.gemift.Model.InsertVoteOccasion;
import com.gift.gemift.Model.InsertVoteProduct;
import com.gift.gemift.Model.InterestList;
import com.gift.gemift.Model.LoginResponse;
import com.gift.gemift.Model.MyCategory;
import com.gift.gemift.Model.MyInterestAdded;
import com.gift.gemift.Model.MyOccasionResponse;
import com.gift.gemift.Model.MySubCategory;
import com.gift.gemift.Model.NotificationCount;
import com.gift.gemift.Model.NotificationResponse;
import com.gift.gemift.Model.OTPSuccessResponse;
import com.gift.gemift.Model.OccasionInsertResponse;
import com.gift.gemift.Model.OccasionModel;
import com.gift.gemift.Model.Optin;
import com.gift.gemift.Model.ProductRoot;
import com.gift.gemift.Model.ProductVote;
import com.gift.gemift.Model.Root;
import com.gift.gemift.Model.StatsModel;
import com.gift.gemift.Model.SecretFriendAgeResponse;
import com.gift.gemift.Model.SignUpOTPresponse;
import com.gift.gemift.Model.SignUpResponse;
import com.gift.gemift.Model.StandardOccasion;
import com.gift.gemift.Model.SubcategoryResponse;
import com.gift.gemift.Model.SubcategoryResponseNew;
import com.gift.gemift.Model.TransactionConfirmInput;
import com.gift.gemift.Model.UnapprovedOccasion;
import com.gift.gemift.Model.UpdateFriendAgeResponse;
import com.gift.gemift.Model.UpdateImagePathResponse;
import com.gift.gemift.Model.UpdateOccasion;
import com.gift.gemift.Model.VonageOTPModel;
import com.gift.gemift.Model.VotedRoot;
import com.gift.gemift.Model.Wallet.TransactionDetailResponse;
import com.gift.gemift.Model.Wallet.WalletResponse;
import com.gift.gemift.Model.Wallet.WalletViewNotificationInput;
import com.gift.gemift.Model.WalletDetails;
import com.gift.gemift.Model.getCategoryResponse;
import com.gift.gemift.Model.product.ProductCategoryResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("login")
    Observable<Response<LoginResponse>> getLoginDetails(
            @Body LoginResponse loginResponse
    );

    @POST("auth/signup")
    Observable<Response<ResponseBody>> insertSingUpDetails(
            @Body SignUpResponse loginResponse
    );

    @POST("friend/circle")
    Observable<Response<AddFriendModel>> addFriend(
            @Body AddFriendModel addFriendModel
    );

    @POST("friend/circle")
    Observable<Response<ResponseBody>> addFriendCircle(
            @Body AddFriendModel addFriendModel
    );


    @POST("friend/circle")
    Observable<Response<ResponseBody>> contributorApproval(@Body ContributorApprovalInputModel contributorApprovalInputModel);

    @POST("phone/login")
    Observable<Response<LoginResponse>> getLoginDetailsByNumber(
            @Body LoginResponse loginResponse
    );

    @POST("otp")
    Observable<Response<VonageOTPModel>> getOTP(
            @Body VonageOTPModel vonageOTPModel
    );

    @POST("otp")
    Observable<Response<ResponseBody>> verifyOTP(
            @Body VonageOTPModel vonageOTPModel
    );

    @POST("otp")
    Observable<Response<SignUpOTPresponse>> getSignUpOTP(
            @Body VonageOTPModel vonageOTPModel
    );


    @Headers("Content-Type: application/json")
    @GET("user/search")
    Observable<Response<FriendSearchModel>> getFriendDetailSearch(@Header("Authorization") String sessionId,
                                                                  @Query("text") String name);

    @GET("friend/circle")
    Observable<Response<FriendDataModel>> getFriendCircleList(@Query("user_id") String userId,
                                                              @Query("request_id") int requestId);

    @GET("friend/circle")
    Observable<Response<FriendCircleResponse>> getFriendCircleDetail(@Query("friend_circle_id") String circleId,
                                                                     @Query("request_id") int requestId);


    @GET("{phoneNumber}/{custom_otp}/{template}")
    Observable<Response<OTPSuccessResponse>> getIndianOTP(@Path("phoneNumber") String phoneNumber, @Path("custom_otp") String custom_otp, @Path("template") String template);

    @GET("VERIFY/{session_id}/{otp_entered_by_user}")
    Observable<Response<OTPSuccessResponse>> VerifyIndianOTP(@Path("session_id") String session_id, @Path("otp_entered_by_user") String otp_entered_by_user);

    @POST("imageUpload.php")
    Observable<Response<ImageUploadResponse>> uploadImage(@Body ImageUploadResponse imageUploadResponse);

    @POST("attr")
    Observable<Response<ResponseBody>> updateImagePath(@Body UpdateImagePathResponse updateImagePathResponse);

    @GET("category")
    Observable<Response<ArrayList<InterestList>>> getInterest(@Query("request_id") int requestId,
                                                              @Query("friend_circle_id") String circleId);

    @GET("interest")
    Observable<Response<SubcategoryResponse>> getSubInterest(@Query("request_id") int requestId,
                                                             @Query("friend_circle_id") String circleId,
                                                             @Query("age") int age);

    @POST("interest")
    Observable<Response<ResponseBody>> addCategoryUser(@Body InsertCategory insertCategory);

    @POST("interest")
    Observable<Response<ResponseBody>> addSubCategoryUser(@Body InsertSubCategoryResponse insertCategory);

    @GET("attr")
    Observable<Response<SecretFriendAgeResponse>> getFriendCircleAge(@Query("request_id") int requestId,
                                                                     @Query("friend_circle_id") String friendCircleId);

    @POST("attr")
    Observable<Response<ResponseBody>> updateFriendAge(@Body UpdateFriendAgeResponse updateFriendAgeResponse);


    @GET("user/occasion")
    Observable<Response<OccasionModel>> getFrequencyList(@Query("request_id") int requestId,
                                                         @Query("friend_circle_id") String friendCircleId,
                                                         @Query("user_id") String userid);

    @GET("user/occasion")
    Observable<Response<GetOccasionResponse>> getOccasionListByCircleId(@Query("request_id") int requestId,
                                                                        @Query("friend_circle_id") String friendCircleId,
                                                                        @Query("user_id") String userid);


    @POST("user/occasion")
    Observable<Response<ResponseBody>> insertOccasion(@Body OccasionInsertResponse occasionInsertResponse);

    @POST("user/occasion")
    Observable<Response<ResponseBody>> insertStandardOccasion(@Body StandardOccasion standardOccasion);

    @POST("user/occasion")
    Observable<Response<VonageOTPModel>> insertVoteOccasion(@Body InsertVoteOccasion insertVoteOccasion);

    @GET("notify")
    Observable<Response<ArrayList<Root>>> getInvites(@Query("request_id") int requestId,
                                                     @Query("user_id") String friendCircleId,
                                                     @Query("phone_number") String phoneNumber);


    @GET("notify")
    Observable<Response<NotificationResponse>> getNotification(@Query("request_id") int requestId,
                                                               @Query("user_id") String friendCircleId);


    @GET("notify")
    Observable<Response<MyOccasionResponse>> getUpComingOccasions(@Query("request_id") int requestId,
                                                                  @Query("user_id") String friendCircleId,
                                                                  @Query("phone_number") String phoneNumber,
                                                                  @Query("country_code") String countryCode);

    @GET("interest")
    Observable<Response<getCategoryResponse>> getCategoryInserted(@Query("request_id") int requestId,
                                                                  @Query("friend_circle_id") String friendCircleId);


    @POST("friend/circle")
    Observable<Response<ResponseBody>> insert_joingroup(
            @Body AddFriendModel addFriendModel
    );

    @POST("user/occasion")
    Observable<Response<ResponseBody>> approve_occasion(
            @Body UnapprovedOccasion unapprovedOccasion
    );

    @GET("prod/search")
    Observable<Response<ProductRoot>> getProducts(@Query("request_id") int requestId,
                                                  @Query("sort_order") String sortOrder,
                                                  @Query("occasion_list") String occasionId,
                                                  @Query("friend_circle_id") String friendCircleId,
                                                  @Query("page_number") String page_number,
                                                  @Query("page_size") String page_size);

    @POST("prod/search")
    Observable<Response<ResponseBody>> insertProductVote(@Body InsertVoteProduct insertVoteProduct);

    @GET("prod/search")
    Observable<Response<VotedRoot>> getVotedProducts(@Query("request_id") int requestId,
                                                     @Query("friend_circle_id") String friendCircleId,
                                                     @Query("occasion_name") String occName,
                                                     @Query("occasion_year") String occYear);

    @POST("gmm/txn")
    Observable<Response<TransactionDetailResponse>> gmm_initiate_team_buy(@Body InitialFundRequestModel initiateFundModel);


    // new Api


    @GET("personal")
    Observable<Response<MyInterestAdded>> getSubInterest(@Query("request_type") String requestType,
                                                         @Query("user_id") String user_id);

    @GET("category")
    Observable<Response<List<GetNewCategoryResponseItem>>> getNewCategoryInserted(@Query("request_id") int requestId);

    @GET("interest")
    Observable<Response<SubcategoryResponseNew>> getNewSubCategoryInserted(@Query("request_id") int requestId,
                                                                           @Query("user_id") String user_id,
                                                                           @Query("friend_circle_id") String friend_circle_id,
                                                                           @Query("age") String age,
                                                                           @Query("gender") String gender,
                                                                           @Query("page_number") String page_number,
                                                                           @Query("page_size") String page_size);

    @GET("interest")
    Observable<Response<FriendCircleAddInterestResponse>> getNewInterestInserted(@Query("request_id") int requestId,
                                                                                 @Query("user_id") String user_id,
                                                                                 @Query("friend_circle_id") String friend_circle_id,
                                                                                 @Query("age") String age,
                                                                                 @Query("gender") String gender,
                                                                                 @Query("page_number") String page_number,
                                                                                 @Query("page_size") String page_size);

    @GET("interest")
    Observable<Response<FriendCircleAddInterestResponse>> getInterestInsertedFriendCircle(@Query("request_id") int requestId,
                                                                                          @Query("friend_circle_id") String user_id);

    @GET("personal")
    Observable<Response<FriendCircleMatch>> getFriendCircleMatch(@Query("request_type") String requestType,
                                                                 @Query("user_id") String user_id);


    @POST("personal")
    Observable<Response<ResponseBody>> addCategoryUserPersonnel(@Body InsertCategoryPersonel insertCategory);

    @POST("personal")
    Observable<Response<ResponseBody>> addSubCategoryUserPersonnel(@Body InsertSubCategoryPersonnel insertCategory);

    @GET("personal")
    Observable<Response<StatsModel>> getSats(@Query("request_type") String type);


    @GET("notify")
    Observable<Response<NotificationCount>> getNotificationCount(@Query("request_id") String type, @Query("user_id") String user_id);

    @GET("notify")
    Observable<Response<ResponseBody>> updateNotification(@Query("request_id") String type, @Query("user_id") String user_id, @Query("message_id") String message_id);

    @POST("gmm/txn")
    Observable<Response<ResponseBody>> updateWalletNotification(@Body WalletViewNotificationInput walletViewNotificationInput);


    @GET("gmm/txn")
    Observable<Response<WalletResponse>> getTransaction(@Query("request_type") String requestType,
                                                        @Query("user_id") String user_id);


    @GET("gmm/txn")
    Observable<Response<TransactionDetailResponse>> getTransactionDetails(@Query("request_type") String requestType,
                                                                          @Query("transaction_id") String transaction_id);

    @POST("gmm/txn")
    Observable<Response<ResponseBody>> opt(@Body Optin optin);



    @POST("gmm/txn")
    Observable<Response<ResponseBody>> pay_amount(@Body Optin optin);

    @POST("gmm/txn")
    Observable<Response<ResponseBody>> confirm(@Body TransactionConfirmInput optin);


    @POST("user/occasion")
    Observable<Response<ResponseBody>> updateOccasion(@Body UpdateOccasion occasion);


    @POST("prod/search")
    Observable<Response<ResponseBody>> productVote(@Body ProductVote vote);



    @GET("prod/search")
    Observable<Response<ProductCategoryResponse>> getProductCategories(@Query("request_id") String request_id,
                                                                       @Query("occasion_list") String occasion_list,
                                                                       @Query("gender_list") String gender_list,
                                                                       @Query("time_zone") String time_zone,
                                                                       @Query("api_call_time") String api_call_time);



}