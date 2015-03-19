package tabishfayyaz.apps.samples.diystarter.manager.events;

import tabishfayyaz.apps.samples.diystarter.rest.model.Comment;
import tabishfayyaz.apps.samples.diystarter.rest.model.Project;

/**
 * Created by tabish on 3/19/15.
 */
public class PostCommentEvent
{
    private Comment comment;
    private Project project;

    public Comment getComment() {
        return comment;
    }

    public Project getProject() {
        return project;
    }

    public PostCommentEvent(String raw, Project project)
    {
        Comment comment = new Comment();
        comment.setRaw(raw);

        this.comment = comment;
        this.project = project;
    }
}
