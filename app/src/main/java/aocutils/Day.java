package aocutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Day {
    private static final Map<Integer, List<Day>> allDays = new HashMap<>();

    protected String name;
    protected String dir;

    protected int year;
    protected int day;

    private Mode mode = Mode.NORMAL;

    protected Day(int year, int number) {
        this.year = year;
        this.day = number;

        this.name = String.format("(%d) Day %d", year, number);
        this.dir = String.format("%d/day%02d", year, number);

        insertDay(this);
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public void run(Mode mode) throws IOException {
        this.mode = mode;

        System.out.println(getTitle());
        _run();
    }

    public static Day getDayOrLatest(int year, int day) {
        if (year == 0) {
            Optional<Integer> possibleYear = allDays.keySet().stream().max(Comparator.naturalOrder());
            if (possibleYear.isEmpty()) {
                throw new IllegalStateException("could not find default year (are there any days registered?)");
            }
            year = possibleYear.get();
        }

        List<Day> days = allDays.get(year);
        if (days == null || days.isEmpty()) {
            throw new IllegalStateException(String.format("could not find default day for year %d (are there any days registered?)", year));
        }

        if (day == 0) {
            day = days.size();
        }

        if (day < 1 || day > days.size()) {
            throw new IllegalArgumentException(String.format("day must be between [1, %d), got: %d", days.size(), day));
        }

        // day is 1-index, convert to 0-index.
        day--;
        return days.get(day);
    }

    protected abstract void _run() throws IOException;

    protected List<String> getLines(String filename) throws IOException {
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

    protected List<String> getLines() throws IOException {
        return getLines(getFilenameForDay());
    }

    protected String getTitle() {
        StringBuilder sb = new StringBuilder(name);
        if (isTestMode()) {
            sb.append(" (test)");
        };
        return sb.toString();
    }

    protected boolean isTestMode() {
        return mode == Mode.TEST;
    }

    private String getFilenameForDay() {
        switch (mode) {
        case TEST: return String.format("%s/test.txt", dir);
        case NORMAL: return String.format("%s/data.txt", dir);
        default: throw new IllegalArgumentException("invalid mode: " + mode);
        }
    }

    private void insertDay(Day day) {
        List<Day> daysForYear = allDays.getOrDefault(day.getYear(), new ArrayList<>());
        daysForYear.add(day);
        allDays.put(day.getYear(), daysForYear);
    }
}
