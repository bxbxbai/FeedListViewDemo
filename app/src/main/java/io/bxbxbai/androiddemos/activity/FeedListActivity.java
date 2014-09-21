package io.bxbxbai.androiddemos.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.bxbxbai.androiddemos.AppController;
import io.bxbxbai.androiddemos.R;
import io.bxbxbai.androiddemos.adapter.FeedListAdapter;
import io.bxbxbai.androiddemos.data.FeedItem;
import io.bxbxbai.androiddemos.data.FeedResult;
import io.bxbxbai.androiddemos.utils.GsonRequest;

/**
 * Created by baia on 14-9-21.
 */
public class FeedListActivity extends Activity {
    private static final String TAG = "FeedListActivity";

    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

    private ActionBar mActionbar;
    private ShimmerTextView mActionbarTitle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        initActionbar();
        listView = (ListView) findViewById(R.id.feed_list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the ic_launcher)
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // making fresh volley request and getting json
        GsonRequest<FeedResult> gsonRequest = new GsonRequest<FeedResult>(URL_FEED, FeedResult.class,
                new Response.Listener<FeedResult>() {
                    @Override
                    public void onResponse(FeedResult response) {
                        feedItems = response.getFeedItems();
                        listAdapter.setData(feedItems);
                        listAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                    }
                });

        // Adding request to volley request queue
        AppController.getInstance().addRequest(gsonRequest, TAG);
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImageUrl(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initActionbar() {
        mActionbar = getActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setIcon(R.drawable.ic_launcher);
        mActionbar.setDisplayShowTitleEnabled(false);
        mActionbar.setDisplayShowCustomEnabled(true);

        View view = View.inflate(this, R.layout.layout_actionbar, null);
        mActionbarTitle = (ShimmerTextView) view.findViewById(R.id.tv_action_bar_title);
        new Shimmer().start(mActionbarTitle);
        mActionbarTitle.setText(R.string.feed_list_demo);
        mActionbar.setCustomView(view);
    }

}
