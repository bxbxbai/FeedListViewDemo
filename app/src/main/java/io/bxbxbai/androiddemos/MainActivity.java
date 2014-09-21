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

import io.bxbxbai.androiddemos.activity.BaseActivity;
import io.bxbxbai.androiddemos.activity.FeedListActivity;
import io.bxbxbai.androiddemos.adapter.FeedListAdapter;
import io.bxbxbai.androiddemos.data.FeedItem;
import io.bxbxbai.androiddemos.data.FeedResult;
import io.bxbxbai.androiddemos.utils.GsonRequest;
import io.bxbxbai.androiddemos.utils.ViewFinder;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        ViewFinder finder = new ViewFinder(this);
        finder.onClick(R.id.btn_feed_list_demo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedListActivity.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
