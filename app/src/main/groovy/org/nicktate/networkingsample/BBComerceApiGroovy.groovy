package org.nicktate.networkingsample;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

import groovy.json.JsonSlurper;

public class BBCommerceApiGroovy {
    private String baseUrl = "https://mockapi.uat.bbhosted.com"
    private OkHttpClient mClient;
    private static BBCommerceApiGroovy sInstance;

    private BBCommerceApiGroovy() {
        mClient = new OkHttpClient();
    }

    public Observable<Object> get(final String endpoint) {
        return Observable.create( { subscriber ->
            Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
                .build(); 

            mClient.newCall(request).enqueue(new Callback() {
                public void onFailure(Request req, IOException e) {
                    subscriber.onError(e);    
                }

                public void onResponse(Response res) {
                    subscriber.onNext(new JsonSlurper().parseText(res.body().string()));
                    subscriber.onCompleted();
                }
            });


        } as OnSubscribe<Object>);
    }

    public Observable<List> getCategories(final String catId) {
        return get("/categories/" + catId);
    }

    public static BBCommerceApiGroovy getInstance() {
        if(sInstance == null) sInstance = new BBCommerceApiGroovy();
        return sInstance;
    }
}
