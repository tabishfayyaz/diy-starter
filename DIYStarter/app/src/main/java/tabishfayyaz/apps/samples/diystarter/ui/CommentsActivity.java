package tabishfayyaz.apps.samples.diystarter.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import tabishfayyaz.apps.samples.diystarter.R;
import tabishfayyaz.apps.samples.diystarter.manager.events.NewCommentEvent;
import tabishfayyaz.apps.samples.diystarter.rest.model.Comment;
import tabishfayyaz.apps.samples.diystarter.rest.model.Project;
import tabishfayyaz.apps.samples.diystarter.manager.events.ApiFailEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.CommentsLoadedEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.LoadCommentsEvent;
import tabishfayyaz.apps.samples.diystarter.manager.events.PostCommentEvent;
import tabishfayyaz.apps.samples.diystarter.util.Util;

/**
 *
 * CommentsActivity is an independent component that shows comments available in its data_source
 * It also gives the user the option to post a new comment. All communication with data and REST
 * server happens through DataService
 *
 * It will be very helpful for you to understand EventBus: https://github.com/greenrobot/EventBus
 *
 * @see tabishfayyaz.apps.samples.diystarter.manager.DataService
 */

public class CommentsActivity extends Activity
{
    private final String TAG = Util.tag(getClass().getSimpleName());

    private ListView mListView;
    private CommentsAdapter mAdapter;
    private List<Comment> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        mListView = (ListView)findViewById(R.id.activity_comments_listview);

        //initialize with an empty data source
        dataSource = new ArrayList<Comment>();
        mAdapter = new CommentsAdapter(this, 0, dataSource);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        EventBus.getDefault().register(this);

        //  if the activity went away or was destroyed you retrieve latest data again
        EventBus.getDefault().post(new LoadCommentsEvent());
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        //  activity is going out of visibility so no point receiving data events
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(CommentsLoadedEvent event)
    {
        //  for simplicity I am replacing all the data in the list, a more optimized approach
        //  can be to only add (new Projects) and replace (modified projects) but I don't think its needed
        //  unless we start seeing a bottle neck
        dataSource.clear();
        dataSource.addAll(event.getCommentsList());

        mAdapter.notifyDataSetChanged();
    }

    public void onEvent(NewCommentEvent event)
    {
        mAdapter.add(event.getComment());

        EditText commentInputField = (EditText)findViewById(R.id.activity_comments_inputbox);
        commentInputField.setText("");
    }

    public void onEvent(ApiFailEvent event)
    {
        Toast.makeText(this, getResources().getString(R.string.api_failure), Toast.LENGTH_SHORT).show();
    }

    public void onPostBtnClick(View view)
    {
        Project selectedProject = LoadCommentsEvent.project;
        EditText commentInputField = (EditText)findViewById(R.id.activity_comments_inputbox);

        EventBus.getDefault().post(new PostCommentEvent(commentInputField.getText().toString(), selectedProject));
    }

    private class CommentsAdapter extends ArrayAdapter<Comment>
    {
        private final String TAG = Util.tag(getClass().getSimpleName());

        public CommentsAdapter(Context context, int textViewResourceId, List<Comment> objects)
        {
            super(context, textViewResourceId, objects);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            CommentItemViewHolder holder = null;

            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listitem_comment, null);
                holder = new CommentItemViewHolder();

                //this is the classic View Holder pattern for creating efficient list views
                holder.commentText = (TextView)view.findViewById(R.id.listitem_comment_text);

                view.setTag(holder);
            }
            else
            {
                holder = (CommentItemViewHolder)view.getTag();
            }

            Comment comment = getItem(position);
            holder.commentText.setText(comment.getRaw());

            return view;
        }
    }

    private class CommentItemViewHolder
    {
        public TextView commentText;
    }

}
