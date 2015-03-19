package tabishfayyaz.apps.samples.diystarter.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tabishfayyaz.apps.samples.diystarter.rest.model.Project;

/**
 * Created by tabish on 3/17/15.
 */
public class DIYProjects extends DIYObject
{
    @SerializedName("response")
    private List<Project> all;

    public List<Project> getAll() {
        return all;
    }
}
