package tabishfayyaz.apps.samples.diystarter.rest;

/**
 * Created by tabish on 3/17/15.
 */
public enum DIYConstants
{
    BASE_URL("https://api.diy.org");

    private String productionUrl;

    public String url()
    {
        return productionUrl;
    }

    DIYConstants(String url)
    {
        productionUrl = url;
    }
}
