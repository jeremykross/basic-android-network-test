package org.nicktate.networkingsample;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.nicktate.networkingsample.model.MCategory;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

import static rx.Observable.OnSubscribe;

/**
 * Created by nick on 4/4/15.
 */
public class BBCommerceApi {
    private String baseUrl = "https://mockapi.uat.bbhosted.com/";
    private OkHttpClient mClient;
    private JsonParser mJsonParser;
    private Gson mGson;

    /**
     *
     * @param catId the category id as a String
     * @return
     */
    public Observable<List<MCategory>> getCategories(final String catId) {
        return Observable.create(new OnSubscribe<List<MCategory>>() {
                    @Override
                    public void call(final Subscriber<? super List<MCategory>> subscriber) {

                        // build network request with the given category id
                        Request request = new Request.Builder()
                                .url(baseUrl + "categories/" + catId)
                                .get() // GET request
                                .build();

                        // post the request
                        mClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                // we cascade the error to the subscriber
                                // in order to let it handle the exception
                                subscriber.onError(e);

                                // XXX: shouldn't we let the
                                // subscriber perform this operation
                                // based on the lifecycle of the fragment/activity
                                // subscriber.unsubscribe();
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {

                                JsonArray categoryJsonArray = mJsonParser.parse(response.body().string())
                                                                .getAsJsonObject()
                                                                .getAsJsonArray("categories");

                                List<MCategory> categories =  mGson.fromJson(categoryJsonArray, new TypeToken<List<MCategory>>(){}.getType());

                                // inform the observer that there
                                // there is data available for consumption
                                subscriber.onNext(categories);

                                // inform the observer that we are
                                // have finished feeding it data
                                subscriber.onCompleted();

                                // XXX: shouldn't we let the
                                // subscriber perform this operation
                                // based on the lifecycle of the fragment/activity
                                // subscriber.unsubscribe();
                            }
                        });
                    }


        });
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
        mJsonParser = new JsonParser();
        mGson = new Gson();
    }
}
