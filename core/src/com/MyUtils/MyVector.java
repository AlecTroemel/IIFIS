package com.MyUtils;

/**
 * Custom integer vector class
 *
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
        return other.x == this.x && other.y == this.y;
    }

    public boolean equals(int x, int y) {
        return x == this.x && y == this.y;
    }

    public MyVector add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public MyVector add(MyVector other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public MyVector sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public MyVector sub(MyVector other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(MyVector other) {
        this.x = other.x;
        this.y = other.y;
    }

    public String toString() {
        return "x: " + this.x + " y: " + this.y;
    }
}
