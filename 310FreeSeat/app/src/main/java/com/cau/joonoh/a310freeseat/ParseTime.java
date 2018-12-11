package com.cau.joonoh.a310freeseat;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import java.io.InputStreamReader;
import java.util.Scanner;

public class ParseTime extends Activity{

    //File timefile;

    InputStreamReader timefile;

    Scanner s ;

    ParseTime(Node[] nodes, Resources resources){
        //timefile =  new File("C:/Users/caucse/Downloads/9~X.csv");

        try {
            timefile = new InputStreamReader(resources.openRawResource(R.raw.roomschedulef));
            s= new Scanner(timefile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("size", ""+nodes.length);
        for(int i = 0; i<72; i++){
            Log.d("node" + i, Integer.toString(i));

            nodes[i].time = new boolean[6][14];
            for(int x = 0 ; x<6; x++) {
                nodes[i].time[x] = new boolean[14];
                for(int y = 0; y<14; y++) {
                   nodes[i].time[x][y] = true;
                }
            }
        }



        int count = 0;

        while(s.hasNextLine()) {

            String line[] = s.nextLine().split(",");


            String name = line[0];
            for(int i = 1; i< line.length; i++) {



                String times[] = line[i].split(":");

                if(line[i].equals("")){
                    continue;
                }


                for(int j = 0 ; j< 14; j++) {
                    for(int k = 0; k<times.length; k++) {

                        if(Integer.parseInt(times[k])==j) {
                            nodes[count].time[i-1][j] = false;
                            break;
                        }
                        else
                            nodes[count].time[i-1][j] = true;
                    }
                }


            }


            count++;

        }
    }


}