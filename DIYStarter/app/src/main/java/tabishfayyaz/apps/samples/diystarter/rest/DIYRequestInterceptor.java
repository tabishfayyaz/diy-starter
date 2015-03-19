package tabishfayyaz.apps.samples.diystarter.rest;

import android.util.Base64;
import android.util.Log;

import retrofit.RequestInterceptor;
import tabishfayyaz.apps.samples.diystarter.util.Util;

/**
 *
 * DIYRequestInterceptor is used to inject headers into every http request that is sent
 *
 * @see tabishfayyaz.apps.samples.diystarter.rest.DIYApi
 *
 * Created by tabish on 3/18/15.
 */
public class DIYRequestInterceptor implements RequestInterceptor
{
    private final String TAG = Util.tag(getClass().getSimpleName());

    private String accessToken;
    private String authorizationValue;

    public void setAuthorizationValue(String value)
    {
        accessToken = null;
        authorizationValue = value;
    }

    public void setAccessToken(String token)
    {
        accessToken = token;
        authorizationValue = null;
    }

    public String getAuthorizationValue()
    {
        return authorizationValue;
    }

    @Override
    public void intercept(RequestFacade request)
    {
        if (accessToken == null)
        {
            request.addHeader("Authorization", authorizationValue);
        }
        else
        {
            request.addHeader("x-diy-api-token", accessToken);
        }
    }
}
