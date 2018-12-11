package com.cau.joonoh.a310freeseat;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class MainActivity extends AppCompatActivity {

    Button searchButton;
    Graph graph;
    Spinner currentPosition;
    Spinner emptytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        graph = new Graph();

        graph.generateGraph(getResources());
        graph.generateAdj(getResources());

        searchButton = (Button) findViewById(R.id.search_btn);

        currentPosition= (Spinner) findViewById(R.id.currentPosition);


        String[] positionSpinner = new String[graph.node.length];

        for(int i = 0; i < graph.node.length; i++){
            positionSpinner[i] = graph.node[i].name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, positionSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentPosition.setAdapter(adapter);


        emptytime = (Spinner)findViewById(R.id.emptytime);


        String[] timeSpinner = {"1", "2", "3", "4"};

        ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, timeSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emptytime.setAdapter(time_adapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = currentPosition.getSelectedItem().toString();
                int time = Integer.parseInt(emptytime.getSelectedItem().toString());
                int key = 0;
                for(int i = 0; i < graph.node.length; i++){
                    if(pos.equals(graph.node[i].name)){
                        key = graph.node[i].key;
                    }
                }

               String[] s = graph.algorithm(key, time);




                Intent it = new Intent(MainActivity.this, ResultActivity.class);

                it.putExtra("result", s);

                startActivity(it);

                //finish();
            }
        });
    }
}
