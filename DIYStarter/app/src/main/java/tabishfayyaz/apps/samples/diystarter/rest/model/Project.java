package tabishfayyaz.apps.samples.diystarter.rest.model;

import java.util.List;

/**
 * Created by tabish on 3/17/15.
 */
public class Project
{
    private String id;
    private Maker maker;
    private String title;
    private List<Clip> clips;

    public Maker getMaker() {
        return maker;
    }

    public List<Clip> getClips() {
        return clips;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }


}
