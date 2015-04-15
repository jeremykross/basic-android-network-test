package org.nicktate.networkingsample;

import org.nicktate.networkingsample.model.MCategory;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func1;

import groovy.json.JsonSlurper;

public class BBCommerceApiGroovy {
    private String baseUrl = "https://mockapi.uat.bbhosted.com"
    private OkHttpClient mClient;
    private static BBCommerceApiGroovy sInstance;

    private BBCommerceApiGroovy() {
        mClient = new OkHttpClient();
    }

    public Observable get(final String endpoint) {
        return Observable.create({ subscriber ->
            Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
                .build(); 

            mClient.newCall(request).enqueue(new Callback() {
                public void onFailure(Request req, IOException e) {
                    subscriber.onError(e);    
                }

                public void onResponse(Response res) {
                    subscriber.onNext(
                        new JsonSlurper().parseText(res.body().string()));
                    subscriber.onCompleted();
                }
            });

        });
    }

    /*
    def getCategoryList(final String catId) {
        return get("/categories/" + catId);
    }
    */

    /*
    public Observable< List<MCategory> > getCategoryList(final String catId) {
        return get("/categories/" + catId)
            .map({ x->
                return x.categories.collect { y ->
                    MCategory category = new MCategory();
                    category.title = y.title;
                    category.id = y.id;
                    category.href = y.href;
                    return category;
                }
            });
    }

    public Observable<MCategory> getCategories(final String catId) {
        return getCategoryList(catId)
            .flatMap({ x->
                Observable.from(x);
            });
    } 
    */

    public static BBCommerceApiGroovy getInstance() {
        if(sInstance == null) sInstance = new BBCommerceApiGroovy();
        return sInstance;
    }
}
