package org.github.notificationtest;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renan on 02/05/16.
 */
public class PushdWrapper {
    public static final String TAG = "PushdWrapper";
    public static String BASE_URL = "http://10.100.100.116:50080";    private String CHAT_CHANNEL = "chat";
    private Retrofit retrofit;
    private String registerId;

    public PushdWrapper() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

    }

    public void register(String token) {
        PushdAPI service = retrofit.create(PushdAPI.class);
        Call<ResponseBody> call = service.registerAsubscriberID(token, "gcm", "pt");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.wtf(TAG, "onResponse");
                String location = response.headers().get("Location");
                registerId = location.split("/")[2];
                subscribeToAnEvent(CHAT_CHANNEL);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.wtf(TAG, "onFailure");
                Utils.splitAndLog(TAG, String.valueOf(call.toString()));
                t.printStackTrace();
            }
        });
    }

    public void sendEvent(String msg) {

        PushdAPI service = retrofit.create(PushdAPI.class);
        Call<ResponseBody> call = service.sendChatEvent(msg);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.wtf(TAG, "onResponse");
                Utils.splitAndLog(TAG, String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.wtf(TAG, "onFailure");
                Utils.splitAndLog(TAG, String.valueOf(call.toString()));
                t.printStackTrace();
            }
        });
    }

    public void subscribeToAnEvent(String eventName) {
        PushdAPI service = retrofit.create(PushdAPI.class);
        Call<ResponseBody> call = service.subscribeToAnEvent(registerId, eventName, 0);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.wtf(TAG, "registered ");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.wtf(TAG, "Failure");
            }
        });
    }

}
