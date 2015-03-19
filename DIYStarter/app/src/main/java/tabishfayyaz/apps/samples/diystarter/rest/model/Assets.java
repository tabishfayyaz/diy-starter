package tabishfayyaz.apps.samples.diystarter.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tabish on 3/18/15.
 */
public class Assets
{
    private Asset original;

    @SerializedName("ios_560")
    private Asset iOS560;

    public Asset getiOS560() {
        return iOS560;
    }


}
