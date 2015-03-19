package tabishfayyaz.apps.samples.diystarter.rest;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import tabishfayyaz.apps.samples.diystarter.rest.deserializer.DIYAuthorizeDeserializer;
import tabishfayyaz.apps.samples.diystarter.rest.model.Comment;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYAuthorize;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYComment;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYComments;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYFollowing;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYProjects;

/**
 *
 * DIYApi communicates with the DIY's REST API utilizing RetroFit: http://square.github.io/retrofit/
 * and OkHttp: http://square.github.io/okhttp/ as the HTTP client
 *
 * Created by tabish on 3/17/15.
 */
public class DIYApi
{
    //create once use forever
    private static DIYService diyService;
    private static DIYRequestInterceptor requestInterceptor = new DIYRequestInterceptor();
    private static String username;

    public static void setAccessToken(String accessToken)
    {
        requestInterceptor.setAccessToken(accessToken);
    }

    public static String getAuthorizationValue()
    {
        return requestInterceptor.getAuthorizationValue();
    }

    public static String getUsername()
    {
        return username;
    }

    public static DIYService createDIYService(final String username, final String password)
    {
        DIYApi.username = username;
        final String credentials = username + ":" + password;
        String authorizationValue = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        requestInterceptor.setAuthorizationValue(authorizationValue);

        return getDIYService();
    }

    public static DIYService getDIYService()
    {
        if (diyService == null)
        {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(DIYAuthorize.class, new DIYAuthorizeDeserializer())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(DIYConstants.BASE_URL.url())
                    .setClient(new OkClient())  //set OKHttp as the HTTP client
                    .setRequestInterceptor(requestInterceptor)
                    .setConverter(new GsonConverter(gson))
//                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            diyService = restAdapter.create(DIYService.class);
        }

        return diyService;
    }

    public interface DIYService
    {
        @GET ("/authorize")
        void authenticateUser(Callback<DIYAuthorize> callback);

        @GET("/makers/{id}/projects")
        void listProjects(@Path("id") String id, Callback<DIYProjects> callback);

        @GET("/makers/{id}/projects/{project_id}/comments")
        void listComments(@Path("id") String id, @Path("project_id") String projectId, Callback<DIYComments> callback);

        @GET("/makers/{id}/following")
        void listFollowing(@Path("id") String id, Callback<DIYFollowing> callback);

        @POST("/makers/{id}/projects/{project_id}/comments")
        void postComment(@Path("id") String id, @Path("project_id") String projectId, @Body Comment comment, Callback<DIYComment> callback);
    }
}