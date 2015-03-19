package tabishfayyaz.apps.samples.diystarter.manager.events;

import java.util.List;

import tabishfayyaz.apps.samples.diystarter.rest.model.Project;

/**
 * Created by tabish on 3/18/15.
 */
public class ProjectsLoadedEvent
{
    private List<Project> projectList;

    public List<Project> getProjectList() {
        return projectList;
    }

    public ProjectsLoadedEvent(List<Project> projectList)
    {
        this.projectList = projectList;
    }
}
