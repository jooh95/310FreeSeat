package com.cau.joonoh.a310freeseat;

public class Node {

    public String name;
    public boolean[][] time;
    public int key;

    boolean isRoom;

    Node(String name, int key, boolean isRoom){
        this.name = name;
        this.key = key;
        this.isRoom = isRoom;

    }

}
