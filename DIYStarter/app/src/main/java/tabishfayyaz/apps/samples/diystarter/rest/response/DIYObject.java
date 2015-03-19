package tabishfayyaz.apps.samples.diystarter.rest.response;

/**
 * Created by tabish on 3/18/15.
 */
public class DIYObject
{
    private DIYHead head;

    public boolean isSuccess()
    {
        return head.getCode() == 200;
    }
}
