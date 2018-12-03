package com.cau.joonoh.a310freeseat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button searchButton;
    Graph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        searchButton = (Button) findViewById(R.id.search_btn);


        graph = new Graph();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.generateAdj(getResources());
//                startActivity(new Intent(MainActivity.this, LoadingActivity.class));
//                finish();
            }
        });
    }
}
