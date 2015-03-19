package tabishfayyaz.apps.samples.diystarter.rest.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

import tabishfayyaz.apps.samples.diystarter.rest.response.DIYAuthorize;

/**
 * Created by tabish on 3/17/15.
 */
public class DIYAuthorizeDeserializer implements JsonDeserializer<DIYAuthorize>
{
    @Override
    public DIYAuthorize deserialize(JsonElement element, Type type, JsonDeserializationContext context)
    {
        JsonElement nestedElement = element.getAsJsonObject().get("response");

        DIYAuthorize authorize = new DIYAuthorize();
        authorize.setToken(nestedElement.getAsJsonObject().get("token").getAsString());
        authorize.setUrl(nestedElement.getAsJsonObject().get("url").getAsString());
        return authorize;
    }
}
