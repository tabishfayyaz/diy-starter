package tabishfayyaz.apps.samples.diystarter.manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tabishfayyaz.apps.samples.diystarter.manager.events.NewCommentEvent;
import tabishfayyaz.apps.samples.diystarter.rest.DIYApi;
import tabishfayyaz.apps.samples.diystarter.rest.model.Maker;
import tabishfayyaz.apps.samples.diystarter.rest.model.Project;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYAuthorize;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYComment;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYComments;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYFollowing;
import tabishfayyaz.apps.samples.diystarter.rest.response.DIYProjects;
import tabishfayyaz.apps.samples.diystarter.manager.events.ApiFailEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.AuthorizeUserEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.CommentsLoadedEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.LoadCommentsEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.LoadProjectsEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.PostCommentEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.ProjectsLoadedEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.UserAuthorizedEvent;
import tabishfayyaz.apps.samples.diystarter.util.Util;

/**
 *
 * DataService is the pipe through which data travels back & forth between the UI layer and
 * REST layer. The UI is completely agnostic to who is the data provider meaning you can plug-play any
 * type of UI without changing anything in other packages.
 *
 *  @see tabishfayyaz.apps.samples.diystarter.StarterApp
 *
 * Created by tabish on 3/18/15.
 */
public class DataService
{
    private final String TAG = Util.tag(getClass().getSimpleName());

    public void onEvent(AuthorizeUserEvent event)
    {
        DIYApi.DIYService diyService = DIYApi.createDIYService(event.getUsername(), event.getPassword());

        diyService.authenticateUser(new Callback<DIYAuthorize>() {
            @Override
            public void success(DIYAuthorize diyAuthorize, Response response) {

                try
                {
                    String publicToken = Util.hmacSha1(diyAuthorize.getUrl(), diyAuthorize.getToken());
                    DIYApi.setAccessToken(publicToken);

                    Log.d(TAG, "User Authorized");
                    EventBus.getDefault().post(new UserAuthorizedEvent());
                }
                catch (Exception ex)
                {
                    EventBus.getDefault().post(new ApiFailEvent());
                }


            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new ApiFailEvent());
            }
        });
    }

    public void onEvent(LoadProjectsEvent event)
    {
        DIYApi.DIYService diyService = DIYApi.getDIYService();

        Callback<DIYProjects> callback = new Callback<DIYProjects>() {
            @Override
            public void success(DIYProjects diyProjects, Response response) {

                EventBus.getDefault().post(new ProjectsLoadedEvent(diyProjects.getAll()));
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new ApiFailEvent());
            }
        };

        if (LoadProjectsEvent.msgType == LoadProjectsEvent.MY_PROJECTS)
        {
            diyService.listProjects(DIYApi.getUsername(), callback);
        }
        else if (LoadProjectsEvent.msgType == LoadProjectsEvent.FRIENDS_PROJECTS)
        {
            diyService.listFollowing(DIYApi.getUsername(), new Callback<DIYFollowing>() {
                @Override
                public void success(DIYFollowing diyFollowing, Response response) {

                    loadFriendsProjects(diyFollowing.getAll());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    public void onEvent(LoadCommentsEvent event)
    {
        DIYApi.DIYService diyService = DIYApi.getDIYService();

        Callback<DIYComments> callback = new Callback<DIYComments>() {
            @Override
            public void success(DIYComments diyComments, Response response) {
                EventBus.getDefault().post(new CommentsLoadedEvent(diyComments.getAll()));
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new ApiFailEvent());
            }
        };

        diyService.listComments(LoadCommentsEvent.project.getMaker().getUrl(), LoadCommentsEvent.project.getId(), callback);
    }

    public void onEvent(PostCommentEvent event)
    {
        DIYApi.DIYService diyService = DIYApi.getDIYService();

        diyService.postComment(event.getProject().getMaker().getUrl(), event.getProject().getId(), event.getComment(), new Callback<DIYComment>() {
            @Override
            public void success(DIYComment diyComment, Response response) {
                Log.d(TAG, diyComment.getComment().getRaw());
                EventBus.getDefault().post(new NewCommentEvent(diyComment.getComment()));
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new ApiFailEvent());
            }
        });
    }

    private void loadFriendsProjects(final List<Maker> makersList)
    {
        DIYApi.DIYService diyService = DIYApi.getDIYService();

        Callback<DIYProjects> callback = new Callback<DIYProjects>() {

            List<Project> allFriendsProjects = new ArrayList<Project>();

            @Override
            public void success(DIYProjects diyProjects, Response response) {

                allFriendsProjects.addAll(diyProjects.getAll());

                //send all the data we have till now as single list
                EventBus.getDefault().post(new ProjectsLoadedEvent(allFriendsProjects));
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new ApiFailEvent());
            }
        };

        for (Maker maker : makersList)
        {
            diyService.listProjects(maker.getUrl(), callback);
        }
    }
}
