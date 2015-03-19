package tabishfayyaz.apps.samples.diystarter.manager.events;

import tabishfayyaz.apps.samples.diystarter.rest.model.Comment;

/**
 * Created by tabish on 3/19/15.
 */
public class NewCommentEvent
{
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public NewCommentEvent(Comment comment)
    {
        this.comment = comment;

    }
}
