package com.cau.joonoh.a310freeseat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;



public class LoadingActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new backTask().execute();

    }

    private class backTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            getData();
            return null;
        }
    }

    private void dijk(){

    }


    private void getData(){

        dijk();

        Intent it = new Intent(LoadingActivity.this, ResultActivity.class);

        startActivity(it);
        finish();

    }
}
