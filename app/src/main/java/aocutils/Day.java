package aocutils;

import java.io.IOException;

public class Day {
    protected String name;
    protected String dir;

    protected int year;
    protected int day;

    protected Day(int year, int number) {
        this.year = year;
        this.day = number;

        this.name = String.format("(%d) Day %d", year, number);
        this.dir = String.format("%d/day%02d", year, number);
    }

    public void run(int mode) throws IOException {};

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }
}
