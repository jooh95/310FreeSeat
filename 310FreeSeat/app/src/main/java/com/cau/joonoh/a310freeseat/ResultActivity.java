package com.cau.joonoh.a310freeseat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView t_classroom, t_path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);


        t_classroom = (TextView) findViewById(R.id.classroom);
        t_path = (TextView) findViewById(R.id.path);

        String result[] = getIntent().getStringArrayExtra("result");



        t_classroom.setText(result[0]);

        String path = new String("");
        for(int i = 1; i < result.length; i++){
            path = path + result[i] + "\n\n";
        }
        t_path.setText(path);


    }

}
