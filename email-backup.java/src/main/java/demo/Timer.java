package demo;

import java.util.Date;

final public class Timer {

    final private String name;
    final private long start;

    public Timer(String name) {
        this.name = name;
        this.start = new Date().getTime();
        System.out.println(name + " started");
    }

    public void finish() {
        System.out.println(name + " finished in " + seconds() + " second(s)");
    }

    private int seconds() {
        return ((int) (new Date().getTime() - start)) / 1000;
    }
}
