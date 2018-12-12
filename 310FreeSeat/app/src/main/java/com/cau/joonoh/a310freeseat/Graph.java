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

class Elevator{
    int floor;
    int e_id;

    Elevator(int floor, int e_id){
        this.floor = floor;
        this.e_id = e_id;
    }
}

public class Graph {

    private int[][] adj;

    public Node[] node;

    private String[] classrooms;

    private String[] classes;

    public ArrayList<Edge> edges;

    public void generateGraph(Resources resources){
        node = new Node[128];


        InputStream inputStream = resources.openRawResource(R.raw.adjacentmatrixf);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String csvLine;

        try{

            csvLine = reader.readLine();
                ArrayList<Elevator> Aelevator = new ArrayList<Elevator>();
                ArrayList<Elevator> Belevator = new ArrayList<Elevator>();
                ArrayList<Elevator> Celevator = new ArrayList<Elevator>();
                String cells[] = csvLine.split(",");

                for(int i = 1; i < cells.length; i++){
                    boolean isRoom = true;
                    Log.d("cellsleng",""+cells.length);
                    if(cells[i].contains("엘베") || cells[i].contains("계단") || cells[i].contains("정문") || cells[i].contains("후문")){
                        isRoom = false;
                    }
                    node[i-1] = new Node(cells[i], i-1, isRoom);

                }
            for(int i = 1; i < cells.length; i++){

                if(cells[i].contains("A엘베") ){
                    int floor = Character.getNumericValue(cells[i].charAt(0));
                    Aelevator.add(new Elevator(floor, i));
                }
                else if(cells[i].contains("B엘베")){
                    int floor = Character.getNumericValue(cells[i].charAt(0));
                    Belevator.add(new Elevator(floor, i));
                }
                else if(cells[i].contains("C엘베")){
                    int floor = Character.getNumericValue(cells[i].charAt(0));
                    Celevator.add(new Elevator(floor, i));
                }
            }


                edges = new ArrayList<Edge>();

            int count = 1;

            while((csvLine = reader.readLine()) != null){

                String w[] = csvLine.split(",");

                Log.d("wwwwlength", w.length + "");
                Log.d("cccc", csvLine);

                Calendar cal2 = Calendar.getInstance();

                int min = cal2.get(Calendar.MINUTE);

                int weight2 = 0;

                if(w[0].contains("A엘베")){
                    int floor2 = Character.getNumericValue(w[0].charAt(0));

                    for(int k = 0; k < Aelevator.size(); k++){

                        if(min >= 50 || min <= 10){
                            weight2 = 140 + Math.abs(floor2 - Aelevator.get(k).floor) * 2;

                        }
                        else{
                            weight2 = 123 + Math.abs(floor2 - Aelevator.get(k).floor) * 2;
                        }
                        w[Aelevator.get(k).e_id] = Integer.toString(Integer.parseInt(w[Aelevator.get(k).e_id]) * weight2);

                    }
                }
                else if(w[0].contains("B엘베")) {
                    int floor2 = Character.getNumericValue(w[0].charAt(0));

                    for(int k = 0; k < Belevator.size(); k++){

                        if(min >= 50 || min <= 10){
                            weight2 = 112 + Math.abs(floor2 - Belevator.get(k).floor) * 2;

                        }
                        else{
                            weight2 = 82 + Math.abs(floor2 - Belevator.get(k).floor) * 2;
                        }
                        w[Belevator.get(k).e_id] = Integer.toString(Integer.parseInt(w[Belevator.get(k).e_id]) * weight2);
                    }
                }
                else if(w[0].contains("C엘베")) {
                    int floor2 = Character.getNumericValue(w[0].charAt(0));

                    for(int k = 0; k < Celevator.size(); k++){

                        weight2 = 63 + Math.abs(floor2 - Celevator.get(k).floor) * 2;

                        w[Celevator.get(k).e_id] = Integer.toString(Integer.parseInt(w[Celevator.get(k).e_id]) * weight2);

                    }
                }

                for(int j = 1; j < w.length; j++){

                    if((Integer.parseInt(w[j]) < 9999) && (Integer.parseInt(w[j]) > 0)) {
                        Edge e = new Edge(Integer.parseInt(w[j]), count-1, j-1);

                        edges.add(e);
                        Log.d("edge",Integer.toString(e.snode) + "  "  + e.dnode +  " " + e.weight);
                    }

                }
                count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ParseTime parset = new ParseTime(node,resources);
        for(int i= 0; i< 128; i++){
            Log.d("node",node[i].name + node[i].isRoom + node[i].key);
        }
    }

    public void generateAdj(Resources resources){
        adj = new int[128][128];

        Log.d("adg start!", "adg start!");


        InputStream inputStream = resources.openRawResource(R.raw.adjacentmatrixf);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String csvLine = null;
            int count = 0;

            csvLine = reader.readLine();

            Log.d("log1", csvLine);

            while((csvLine = reader.readLine()) != null){
                String cells[] = csvLine.split(",");

//                Log.d("log1", csvLine);
                String print = new String();
                for(int i = 1; i < cells.length; i++){

                    adj[count][i-1] = Integer.parseInt(cells[i]);
                    print =  print+Integer.toString(adj[count][i-1]);
                }
                count++;

  //               Log.d("log", print);
  //                Log.d("log", Integer.toString(count));
            }
            //Log.d("log", Integer.toString(count));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] algorithm(int sKey, int time){
        Log.d("key",""+ sKey);
        Distance dist[] = new Distance[128];
        ArrayList<ArrayList<Integer>> path= new ArrayList<ArrayList<Integer>>();

        for(int i = 0;  i < 128; i++){
            dist[i] = new Distance();
            path.add(new ArrayList<Integer>());
        }


        path.get(sKey).add(sKey);



        for(int i = 0; i < 128; i++){
            dist[i].value = 9999;
            dist[i].key = i;
        }

        dist[sKey].value = 0;




        //elevator multiply

        for(int i = 0; i < 128; i++){
            for(int j = 0; j < edges.size(); j++){
//                Log.d("r", "" + dist[edges.get(j).snode].value + edges.get(j).weight);
//                Log.d("l", "" + dist[edges.get(j).dnode].value);
                if(dist[edges.get(j).snode].value + edges.get(j).weight < dist[edges.get(j).dnode].value){

                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.addAll(path.get(edges.get(j).snode));
                    temp.add(edges.get(j).dnode);
                    path.get(edges.get(j).dnode).clear();
                    path.get(edges.get(j).dnode).addAll(temp);
                    Log.d("change node" , node[edges.get(j).dnode].name);
                    dist[edges.get(j).dnode].value = dist[edges.get(j).snode].value + edges.get(j).weight;
                    String result = new String("");
//                    for(int k = 0; k < path.get(i).size(); k++){
//
//                        result = result + node[path.get(edges.get(j).dnode).get(k)].name;
//
//                    }
//                    Log.d("aaa"+node[edges.get(j).dnode].name, result);
                }
            }
        }




        Arrays.sort(dist, new Comparator<Distance>() {
            @Override
            public int compare(Distance o1, Distance o2) {
                if(o1.value > o2.value){
                    return 1;
                }
                else if(o1.value < o2.value){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });

        for(int i = 0; i < 128; i++){
            Log.d("value", dist[i].value + "");
            Log.d("key", dist[i].key + "");
        }

        Calendar cal = Calendar.getInstance();

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;   //요일
        int dayHour = cal.get(Calendar.HOUR_OF_DAY);

        Log.d("day", ""+dayNum);
        Log.d("hour", ""+dayHour);

        String[] dump = new String[2];


        Log.d("key", "" +node[dist[0].key].isRoom);
        Log.d("key", "" +node[dist[0].key].name);
        Log.d("key", "" +node[dist[0].key].time[2][3]);
        Log.d("key", "" +node[dist[0].key].key);
        Log.d("dayNum",""+(dayNum-2));
        Log.d("dayHour",""+(dayHour-8));
        Log.d("time",""+time);
        for(int i = 0; i < 128; i++){
            if(node[dist[i].key].isRoom){
                int count = 0;
                String[] result;
                for(int j = dayHour; j <dayHour+time; j++){
                    if(node[dist[i].key].time[dayNum - 2][j - 8]==true){
                        count++; // 입력받은 시간만큼 활용가능한지 검사
                    }
                }
                if(count == time){//강의실이 이용가능할 때,
                    result = new String[1+path.get(dist[i].key).size()];
                    result[0] = new String(node[dist[i].key].name);

                    Log.d("path size", path.get(i).size() + "");

                    for(int k = 0; k < path.get(dist[i].key).size(); k++){
                        result[k+1] =  node[path.get(dist[i].key).get(k)].name;
                        Log.d("aaaaa", node[path.get(dist[i].key).get(k)].name);
                    }

                    return  result;


                }else{
                    Log.d("else","sdfsdfsdfdsfdsfds");
                    continue;//이용가능하지 않으면 continue
                }
            }
        }


        return dump;

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
