package tabishfayyaz.apps.samples.diystarter.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tabishfayyaz.apps.samples.diystarter.rest.model.Maker;

/**
 * Created by tabish on 3/18/15.
 */
public class DIYFollowing extends DIYObject
{
    @SerializedName("response")
    private List<Maker> all;

    public List<Maker> getAll() {
        return all;
    }
}
