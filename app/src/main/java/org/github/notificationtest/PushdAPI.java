package org.github.notificationtest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by renan on 02/05/16.
 */
public interface PushdAPI {

    @FormUrlEncoded
    @POST("/subscribers")
    Call<ResponseBody> registerAsubscriberID(@Field("token") String token,
                                             @Field("proto") String proto,
                                             @Field("lang") String lang);

    @FormUrlEncoded
    @POST("/event/chat")
    Call<ResponseBody> sendChatEvent(@Field("msg") String msg);

    @FormUrlEncoded
    @POST("/subscriber/{SUBSCRIBER_ID}/subscriptions/{EVENT_NAME}")
    Call<ResponseBody> subscribeToAnEvent(@Path("SUBSCRIBER_ID") String subscriber_id, @Path("EVENT_NAME") String eventName, @Field("ignore_message") int ignoreMessage);

}
