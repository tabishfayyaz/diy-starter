package tabishfayyaz.apps.samples.diystarter.rest.response;

import com.google.gson.annotations.SerializedName;

import tabishfayyaz.apps.samples.diystarter.rest.model.Comment;

/**
 * Created by tabish on 3/19/15.
 */
public class DIYComment extends DIYObject
{
    @SerializedName("response")
    private Comment comment;

    public Comment getComment() {
        return comment;
    }
}
