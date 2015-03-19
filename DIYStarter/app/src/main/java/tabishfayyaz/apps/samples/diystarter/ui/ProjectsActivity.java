package tabishfayyaz.apps.samples.diystarter.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import tabishfayyaz.apps.samples.diystarter.R;
import tabishfayyaz.apps.samples.diystarter.rest.model.Assets;
import tabishfayyaz.apps.samples.diystarter.rest.model.Clip;
import tabishfayyaz.apps.samples.diystarter.rest.model.Project;
import tabishfayyaz.apps.samples.diystarter.manager.events.ApiFailEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.LoadCommentsEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.LoadProjectsEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.ProjectsLoadedEvent;
import tabishfayyaz.apps.samples.diystarter.util.Util;

/**
 *
 * ProjectsActivity is an independent component that shows projects available in its data_source.
 * All communication with data and REST server happens through DataService
 *
 * It will be very helpful for you to understand EventBus: https://github.com/greenrobot/EventBus
 *
 * * @see tabishfayyaz.apps.samples.diystarter.manager.DataService
 */
public class ProjectsActivity extends Activity
{
    private final String TAG = Util.tag(getClass().getSimpleName());

    private ListView mListView;
    private ProjectsAdapter mAdapter;
    private List<Project> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        mListView = (ListView)findViewById(R.id.activity_projects_listview);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Project project = mAdapter.getItem(position);
                LoadCommentsEvent.project = project;

                Intent intent = new Intent(ProjectsActivity.this, CommentsActivity.class);
                startActivity(intent);
            }
        });

        //initialize with an empty data source
        dataSource = new ArrayList<Project>();
        mAdapter = new ProjectsAdapter(this, 0, dataSource);
        mListView.setAdapter(mAdapter);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //  if the activity went away or was destroyed you retrieve latest data again
        EventBus.getDefault().post(new LoadProjectsEvent());
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(ProjectsLoadedEvent event)
    {
        //  for simplicity I am replacing all the data in the list, a more optimized approach
        //  can be to only add (new Projects) and replace (modified projects) but I don't think its needed
        //  unless we start seeing a bottle neck
        dataSource.clear();
        dataSource.addAll(event.getProjectList());

        mAdapter.notifyDataSetChanged();
    }

    public void onEvent(ApiFailEvent event)
    {
        Toast.makeText(this, getResources().getString(R.string.api_failure), Toast.LENGTH_SHORT).show();
    }

    private class ProjectsAdapter extends ArrayAdapter<Project>
    {
        private final String TAG = Util.tag(getClass().getSimpleName());

        public ProjectsAdapter(Context context, int textViewResourceId, List<Project> objects)
        {
            super(context, textViewResourceId, objects);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            ProjectItemViewHolder holder = null;

            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listitem_project, null);
                holder = new ProjectItemViewHolder();

                //this is the classic View Holder pattern for creating efficient list views
                holder.titleText = (TextView)view.findViewById(R.id.listitem_project_title);
                holder.primaryImage = (ImageView)view.findViewById(R.id.listitem_project_image);

                view.setTag(holder);
            }
            else
            {
                holder = (ProjectItemViewHolder)view.getTag();
            }

            Project project = getItem(position);
            holder.titleText.setText(project.getTitle());

            //  TODO - check for existence of atleast one clip/image, assuming for sake of demo that there will be at-least 1 asset/clip
            Clip clip = project.getClips().get(0);
            Assets assets = clip.getAssets();

            Picasso.with(getContext()).load(assets.getiOS560().getUrl()).into(holder.primaryImage);

            return view;
        }
    }

    private class ProjectItemViewHolder
    {
        public TextView titleText;
        public ImageView primaryImage;
    }
}
