package aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aocutils.Day;

public class Day02 extends Day {
    private enum Color {
        RED(0),
        GREEN(1),
        BLUE(2);

        private final int value;

        private Color(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static int max() {
            return BLUE.value;
        }

        public static Color parseColor(String s) {
            switch (s) {
            case "red": return RED;
            case "green": return GREEN;
            case "blue": return BLUE;
            default: throw new IllegalArgumentException(String.format("invalid color string: %s", s));
            }
        }
    }

    private static final Pattern gamePattern = Pattern.compile("Game (?<id>\\d+):");
    private static final Pattern drawPattern = Pattern.compile("(?<n>\\d+) (?<color>red|blue|green)");

    public Day02() {
        super(2023, 2);
    }

    @Override
    protected void _run() throws IOException {
        List<String> lines = getLines();
        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            Matcher matcher = gamePattern.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException(String.format("line missing game id: %s", line));
            }

            int id = Integer.parseInt(matcher.group("id"));
            if (isGamePossible(line)) {
                sum += id;
            }
        }

        System.out.printf("part 1: %d\n", sum);
    }

    private static void part2(List<String> lines) {
        int sum = 0;

        for (String line : lines) {
            int[] maxCounts = maxCounts(line);
            sum += power(maxCounts);
        }

        System.out.printf("part 2: %d\n", sum);
    }

    private static boolean isGamePossible(String game) {
        int[] maxCubes = new int[] { 12, 13, 14 };

        String[] rounds = game.split(";");
        for (String round : rounds) {
            int[] counts = colorCounts(round);
            for (int i = 0; i < maxCubes.length; i++) {
                if (counts[i] > maxCubes[i]) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int[] maxCounts(String game) {
        int[] maxCounts = new int[Color.max()+1];

        String[] rounds = game.split(";");
        for (String round : rounds) {
            int[] counts = colorCounts(round);
            for (int i = 0; i < maxCounts.length; i++) {
                maxCounts[i] = Math.max(maxCounts[i], counts[i]);
            }
        }

        return maxCounts;
    }

    private static int[] colorCounts(String round) {
        int[] colors = new int[Color.max()+1];

        Matcher matcher = drawPattern.matcher(round);
        while (matcher.find()) {
            Color color = Color.parseColor(matcher.group("color"));
            int count = Integer.parseInt(matcher.group("n"));

            colors[color.getValue()] += count;
        }

        return colors;
    }

    private static int power(int[] counts) {
        return Arrays.stream(counts).reduce(1, (p, count) -> p * count);
    }
}
