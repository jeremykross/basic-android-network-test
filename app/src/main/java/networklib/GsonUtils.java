package networklib;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

/**
 * Created by Brad on 4/16/2015.
 */
public class GsonUtils {

    private Gson mGson;
    protected GsonFireBuilder mBuilder = new GsonFireBuilder();

    protected static GsonUtils mInstance;
    protected GsonUtils(){}

    public static GsonUtils getInstance() {
        if (mInstance == null) {
            mInstance = new GsonUtils();
        }
        return mInstance;
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = mBuilder.createGson();
        }
        return mGson;
    }

    public <T, C extends T> void registerTypeSelector(Class<T> baseClass, final Class<C> subClass) {
        mBuilder.registerTypeSelector(baseClass, new TypeSelector<T>() {
            @Override
            public Class<? extends T> getClassForElement(JsonElement readElement) {
                return subClass;
            }
        });
    }
}
