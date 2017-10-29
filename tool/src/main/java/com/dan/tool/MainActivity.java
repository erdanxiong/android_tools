package com.dan.tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.dan.tool.activity.SwipeContentActivity;

public class MainActivity extends Activity {
    private Button swiprefreshlayouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swiprefreshlayouts = (Button) findViewById(R.id.swiprefreshlayouts);
        swiprefreshlayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplication(), SwipeContentActivity.class);
                startActivity(intent);
            }
        });
    }
}
