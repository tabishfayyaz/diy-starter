package tabishfayyaz.apps.samples.diystarter.rest.response;

/**
 * Created by tabish on 3/18/15.
 */
public class DIYAuthorize extends DIYObject
{
    private String token;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
