package io.bxbxbai.androiddemos;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

import io.bxbxbai.androiddemos.activity.FeedListActivity;
import io.bxbxbai.androiddemos.adapter.FeedListAdapter;
import io.bxbxbai.androiddemos.data.FeedItem;
import io.bxbxbai.androiddemos.data.FeedResult;
import io.bxbxbai.androiddemos.utils.GsonRequest;
import io.bxbxbai.androiddemos.utils.ViewFinder;


public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActionBar mActionbar;
    private ShimmerTextView mActionbarTitle;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionbar();

        ViewFinder finder = new ViewFinder(this);
        finder.onClick(R.id.btn_feed_list_demo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedListActivity.class));
            }
        });

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the ic_launcher)
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

    }

    private void initActionbar() {
        mActionbar = getActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(false);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setIcon(R.drawable.ic_launcher);
        mActionbar.setDisplayShowTitleEnabled(false);
        mActionbar.setDisplayShowCustomEnabled(true);

        View view = View.inflate(this, R.layout.layout_actionbar, null);
        mActionbarTitle = (ShimmerTextView) view.findViewById(R.id.tv_action_bar_title);
        new Shimmer().start(mActionbarTitle);
        mActionbarTitle.setText(R.string.app_name);
        mActionbar.setCustomView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
