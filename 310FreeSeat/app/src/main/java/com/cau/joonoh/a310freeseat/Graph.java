package com.cau.joonoh.a310freeseat;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Graph {

    private int[][] adj;

    public Node[] node;

    private String[] classrooms;

    private String[] classes;

    public ArrayList<Edge> edges;

    public void generateGraph(Resources resources){
        node = new Node[135];

        InputStream inputStream = resources.openRawResource(R.raw.room_schedule);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String csvLine;

        try{

            csvLine = reader.readLine();

                String cells[] = csvLine.split(",");

                for(int i = 1; i < cells.length; i++){
                    boolean isRoom = true;

                    if(cells[i].contains("엘베") || cells[i].contains("계단") || cells[i].contains("정문") || cells[i].contains("후문")){
                        isRoom = false;
                    }

                    node[i-1] = new Node(cells[i], i-1, isRoom);
                }

                edges = new ArrayList<Edge>();

            while((csvLine = reader.readLine()) != null){

                String w[] = csvLine.split(",");

                int count = 1;
                for(int j = 1; j < w.length; j++){
                    if(Integer.parseInt(w[j]) != 9999 || Integer.parseInt(w[j]) != 0) {
                        Edge e = new Edge(Integer.parseInt(w[j]), count-1, j-1);

                        edges.add(e);
                    }
                }
                count++;
            }
        }
        catch (Exception e){

        }
    }

    public void generateAdj(Resources resources){
        adj = new int[135][135];

        InputStream inputStream = resources.openRawResource(R.raw.adjacent_matrix);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-16LE"));

            String csvLine = null;
            int count = 0;

            csvLine = reader.readLine();


            while((csvLine = reader.readLine()) != null){
                String cells[] = csvLine.split(",");

                //Log.d("log1", csvLine);

                for(int i = 1; i < cells.length; i++){

                    adj[count][i-1] = Integer.parseInt(cells[i]);
                }
                count++;

//                Log.d("log", reader.readLine());

                //Log.d("log", Integer.toString(count));
            }
            //Log.d("log", Integer.toString(count));

            //Log.d("end", reader.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void algorithm(int sKey, int time){
        Distance dist[] = new Distance[135];

        for(int i = 0; i < 135; i++){
            dist[i].value = 9999;
            dist[i].key = i;
        }

        dist[sKey].value = 0;

        //elevator multiply

        for(int i = 0; i < 135; i++){
            for(int j = 0; j < edges.size(); j++){
                if(dist[edges.get(j).snode].value + edges.get(j).weight < dist[edges.get(j).dnode].value){

                    dist[edges.get(j).dnode].value = dist[edges.get(j).snode].value + edges.get(j).weight;
                }
            }
        }


        Arrays.sort(dist, new Comparator<Distance>() {
            @Override
            public int compare(Distance o1, Distance o2) {
                if(o1.value > o2.value){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });

        for(int i = 0; i < 135; i++){
            if(node[dist[i].key].isRoom){

            }
        }

        Date dt = new Date();
        SimpleDateFormat current_time = new SimpleDateFormat("hh");

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(dt);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;   //요일

        Log.d("DATE",current_time.format(dt).toString());


    }

//
//    public void parseData(Resources resources){
//        InputStream inputStream = resources.openRawResource(R.raw.course_schedule);
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        int idx = 0;
//        try{
//            String csvLine;
//
//            while((csvLine = reader.readLine()) != null){
//
//                String[] items  = csvLine.split(",");
//
//                Log.d("column", items[idx]);
//                idx++;
//            }
//
//        }
//        catch (Exception e){
//
//        }
//
//    }



    public String[] getClassroom(){
        return classrooms;
    }

}
