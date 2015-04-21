package networklib;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.MediaType;

import java.io.IOException;
import java.util.List;

import networklib.model.MCategory;
import networklib.model.MModel;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static rx.Observable.OnSubscribe;

/**
 * Created by nick on 4/4/15.
 */
public class BBCommerceApi {
    private String baseUrl = "https://gncapi.uat.bbhosted.com";
    private OkHttpClient mClient;
    private JsonParser mJsonParser;
    
    interface HttpDecorator {
        void decorate(Request.Builder req);
    }

    public Observable<String> httpVerb(final String route, final HttpDecorator decorator) {
        return Observable.create(new OnSubscribe<String>() {
            public void call(final Subscriber<? super String> subscriber) {
                Request.Builder requestBuilder = new Request.Builder()
                    .url(baseUrl+route);
                decorator.decorate(requestBuilder);
                Request request = requestBuilder.build();

                mClient.newCall(request).enqueue(new Callback() {
                    public void onFailure(Request request, IOException ex) {
                        subscriber.onError(ex);
                    }

                    public void onResponse(Response resp) throws IOException {
                        subscriber.onNext(resp.body().string());
                    }
                });
            }
        });
    }

    protected <T> Func1<String, T> makeJsonConverter(final Class<T> ofClass) {
        return new Func1<String, T>() {
            public T call(String x) {
                return GsonUtils.getInstance().getGson().fromJson(x, ofClass);
            }
        };
    }

    public <T> Observable<T> GET(final String route, Func1<String, T> converter) {
        return httpVerb(route, new HttpDecorator() {
            public void decorate(Request.Builder builder) {
                builder.get();
            }
        }).map(converter);
    }

    public <T> Observable<T> GET(final String route, final Class<T> ofClass) {
        return GET(route, makeJsonConverter(ofClass));
    }

    public <T> Observable<T> POST(final String route, final String payload, final String contentType, final Class<T> ofClass) {
        return httpVerb(route, new HttpDecorator() {
            public void decorate(Request.Builder builder) {
                MediaType type = MediaType.parse(contentType);
                RequestBody body = RequestBody.create(type, payload);
                builder
                    .post(body);

            }
        }).map(makeJsonConverter(ofClass));
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
    }
}
