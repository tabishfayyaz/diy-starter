package tabishfayyaz.apps.samples.diystarter.manager.events;

/**
 * Created by tabish on 3/18/15.
 */
public class AuthorizeUserEvent
{
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public AuthorizeUserEvent(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
