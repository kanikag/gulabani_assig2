package com.lnu.auth.model;

public class TestSession {
    private int type;
    private String x;
    private String y;
    private String time;
    private String button;
    private String correct;

    public TestSession(int type, String x, String y, String time) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public TestSession(int type, String x, String y, String time, String button, String correct) {
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

    public String getButton() {
        return button;
    }

    public String getCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "TestSession{" +
                "type=" + type +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", time='" + time + '\'' +
                ", button='" + button + '\'' +
                ", correct='" + correct + '\'' +
                '}';
    }
}
