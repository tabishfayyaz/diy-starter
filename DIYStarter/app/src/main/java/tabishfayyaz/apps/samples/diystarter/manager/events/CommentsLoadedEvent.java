package tabishfayyaz.apps.samples.diystarter.manager.events;

import java.util.List;

import tabishfayyaz.apps.samples.diystarter.rest.model.Comment;

/**
 * Created by tabish on 3/19/15.
 */
public class CommentsLoadedEvent
{
    public List<Comment> getCommentsList() {
        return commentsList;
    }

    private List<Comment> commentsList;

    public CommentsLoadedEvent(List<Comment> commentsList)
    {
        this.commentsList = commentsList;
    }
}
