package aocutils;

import java.io.IOException;

public class Day {
    protected String name;
    protected String dir;

    protected Day(int year, int number) {
        this.name = String.format("(%d) Day %d", year, number);
        this.dir = String.format("%d/day%02d", year, number);
    }

    public void run(int mode) throws IOException {};
}
