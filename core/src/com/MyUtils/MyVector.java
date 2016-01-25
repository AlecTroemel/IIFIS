package com.MyUtils;

/**
 * Created by alect on 1/24/2016.
 */
public class MyVector {
    public int x;
    public int y;

    public MyVector(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public MyVector(MyVector other) {
        this.x = other.x;
        this.y = other.y;
    }

    public boolean equals(MyVector other) {
        if (other.x == this.x && other.y == this.y) return true;
        else return false;
    }

    public boolean equals(int x, int y) {
        if (x == this.x && y == this.y) return true;
        else return false;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void add(MyVector other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void sub(int x, int y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(MyVector other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public String toString() {
        return "x: " + this.x + " y: " + this.y;
    }
}
