package com.lnu.auth.model;

public class Test {
    private int type;
    private String x;
    private String y;
    private String time;
    private int button;
    private int correct;

    public Test(int type, String x, String y, String time) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public Test(int type, String x, String y, String time, int button, int correct) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.time = time;
        this.button = button;
        this.correct = correct;
    }

    public int getType() {
        return type;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getTime() {
        return time;
    }

    public int getButton() {
        return button;
    }

    public int getCorrect() {
        return correct;
    }
}
