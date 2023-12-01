package aocutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class Day {
    protected String name;
    protected String dir;

    protected int year;
    protected int day;

    private int mode;

    protected Day(int year, int number) {
        this.year = year;
        this.day = number;
        this.mode = 0; // Default to test mode.

        this.name = String.format("(%d) Day %d", year, number);
        this.dir = String.format("%d/day%02d", year, number);
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public void run(int mode) throws IOException {
        this.mode = mode;

        System.out.println(getTitle());
        _run();
    }

    protected abstract void _run() throws IOException;

    protected List<String> getLines() throws IOException {
        String filename = getFilenameForDay();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        ArrayList<String> lines = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            lines.add(line);
        }

        return lines;
    }

    protected String getTitle() {
        StringBuilder sb = new StringBuilder(name);
        if (isTestMode()) {
            sb.append(" (test)");
        };
        return sb.toString();
    }

    private boolean isTestMode() {
        return mode == 0;
    }

    private String getFilenameForDay() {
        switch (mode) {
        case 0: return String.format("%s/test.txt", dir);
        case 1: return String.format("%s/data.txt", dir);
        default: throw new IllegalArgumentException("invalid mode: " + mode);
        }
    }
}
