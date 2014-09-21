package io.bxbxbai.androiddemos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import io.bxbxbai.androiddemos.activity.BaseActivity;
import io.bxbxbai.androiddemos.activity.FeedListActivity;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
