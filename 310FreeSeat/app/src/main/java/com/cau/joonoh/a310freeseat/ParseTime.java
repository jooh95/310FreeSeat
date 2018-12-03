package com.cau.joonoh.a310freeseat;

import android.app.Activity;

import java.io.InputStreamReader;
import java.util.Scanner;

public class ParseTime extends Activity{

    //File timefile;

    InputStreamReader timefile;

    Scanner s ;

    ParseTime(){
        //timefile =  new File("C:/Users/caucse/Downloads/9~X.csv");

        try {
            timefile = new InputStreamReader(getResources().openRawResource(R.raw.room_schedule));
            s= new Scanner(timefile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        boolean timearray[][] = new boolean[6][14];
        for(int x = 0 ; x<6; x++) {
            for(int y = 0; y<14; y++) {
                timearray[x][y] = true;
            }
        }

        while(s.hasNextLine()) {
            String line[] = s.nextLine().split(",");


            String name = line[0];
            for(int i = 1; i< line.length; i++) {



                String time[] = line[i].split(":");


                for(int j = 0 ; j< 14; j++) {
                    for(int k = 0; k<time.length; k++) {

                        if(Integer.parseInt(time[k])==j) {
                            timearray[i-1][j] = true;
                            break;
                        }
                        else
                            timearray[i-1][j] = false;
                    }
                }


            }
            System.out.println(name);
            System.out.println("time table");
            for(int i = 0; i < line.length-1; i++) {
                System.out.println("");
                for(int j = 0; j<14; j++) {
                    System.out.print(timearray[i][j]+" ");
                }
            }
            System.out.println("");

        }
    }


}