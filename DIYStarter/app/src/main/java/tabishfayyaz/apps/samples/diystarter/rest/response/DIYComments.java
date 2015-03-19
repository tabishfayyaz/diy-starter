package tabishfayyaz.apps.samples.diystarter.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tabishfayyaz.apps.samples.diystarter.rest.model.Comment;

/**
 * Created by tabish on 3/18/15.
 */
public class DIYComments extends DIYObject
{
    @SerializedName("response")
    private List<Comment> all;

    public List<Comment> getAll() {
        return all;
    }
}
