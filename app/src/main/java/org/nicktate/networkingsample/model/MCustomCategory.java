package org.nicktate.networkingsample.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by abhijitnukalapati on 4/14/15.
 */
public class MCustomCategory extends MCategory {
    public String href;

    public static class Deserializer implements JsonDeserializer<MCategory> {
        @Override
        public MCategory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            MCustomCategory category = new MCustomCategory();

            if (json.isJsonObject()) {
                JsonObject jo = json.getAsJsonObject();

                if (jo.has("href") && jo.get("href").isJsonPrimitive()) {
                    category.href = jo.get("href").getAsString();
                }

            }

            return category;
        }
    }
}
