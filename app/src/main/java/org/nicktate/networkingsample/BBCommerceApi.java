package org.nicktate.networkingsample;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nick on 4/4/15.
 */
public class BBCommerceApi {
    private String baseUrl = "https://mockapi.uat.bbhosted.com/";
    private OkHttpClient mClient;

    public Observable getCategory(final String id) {
        Observable<MCategory> categoryObservable = Observable.create(
                new Observable.OnSubscribe<MCategory>() {
                    @Override
                    public void call(final Subscriber<? super MCategory> subscriber) {
                        Request request = new Request.Builder()
                                .url(baseUrl + "categories/" + id)
                                .build();

                        mClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                subscriber.unsubscribe();
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                for(JsonElement je : new JsonParser()
                                        .parse(response.body().string())
                                        .getAsJsonObject()
                                        .getAsJsonArray("categories")) {

                                    MCategory category = new Gson().fromJson(je, MCategory.class);
                                    subscriber.onNext(category);
                                }

                                subscriber.unsubscribe();
                            }
                        });
                    }
                });

        return categoryObservable;
    }

    // singleton
    private static class Holder {
        static final BBCommerceApi INSTANCE = new BBCommerceApi();
    }

    public static BBCommerceApi getInstance() {
        return Holder.INSTANCE;
    }

    private BBCommerceApi() {
        mClient = new OkHttpClient();
    }
}
