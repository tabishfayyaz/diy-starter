package tabishfayyaz.apps.samples.diystarter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import tabishfayyaz.apps.samples.diystarter.R;
import tabishfayyaz.apps.samples.diystarter.manager.events.ApiFailEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.AuthorizeUserEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.LoadProjectsEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.UserAuthorizedEvent;
import tabishfayyaz.apps.samples.diystarter.util.Util;


/**
 *
 * SelectionActivity is the first activity of the app and lets the user choose between seeing his
 * projects or his followers projects
 *
 */
public class SelectionActivity extends Activity
{
    private final String TAG = Util.tag(getClass().getSimpleName());

    private Button mMyProjectsBtn;
    private Button mFriendsProjectsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        EventBus.getDefault().register(this);

        //the username and password could come from input fields
        EventBus.getDefault().post(new AuthorizeUserEvent("hiveworking", "hiveworking"));

        mMyProjectsBtn = (Button)findViewById(R.id.selection_activity_myprojects_btn);
        mFriendsProjectsBtn = (Button)findViewById(R.id.selection_activity_friends_btn);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onMyProjectsClick(View view)
    {
        LoadProjectsEvent.msgType = LoadProjectsEvent.MY_PROJECTS;

        Intent intent = new Intent(this, ProjectsActivity.class);
        startActivity(intent);
    }

    public void onFriendsProjectsClick(View view)
    {
        LoadProjectsEvent.msgType = LoadProjectsEvent.FRIENDS_PROJECTS;

        Intent intent = new Intent(this, ProjectsActivity.class);
        startActivity(intent);
    }

    public void onEvent(UserAuthorizedEvent event)
    {
        mMyProjectsBtn.setEnabled(true);
        mFriendsProjectsBtn.setEnabled(true);
    }

    public void onEvent(ApiFailEvent event)
    {
        Toast.makeText(this, getResources().getString(R.string.api_failure), Toast.LENGTH_SHORT).show();
    }
}
