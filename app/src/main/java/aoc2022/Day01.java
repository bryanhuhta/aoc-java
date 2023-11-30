package aoc2022;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day01 {
    private final static String DAY_NAME = "Day 1";
    private final static String DAY_DIR = "day01";

    public static void run(int mode) throws IOException {
        System.out.println(Utils.dayTitle(DAY_NAME, mode));

        String filename = Utils.filenameForDay(DAY_DIR, mode);
        List<String> lines = Utils.lines(filename);

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int max = 0;
        int sum = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                max = Math.max(max, sum);
                sum = 0;
                continue;
            }

            sum += Integer.parseInt(line);
        }

        if (sum != 0) {
            max = Math.max(max, sum);
        }

        System.out.println("part 1: " + max);
    }

    private static void part2(List<String> lines) {
        int[] topN = {0, 0, 0};
        int sum = 0;

        for (String line : lines) {
            if (line.isEmpty()) {
                if (topN[0] < sum) {
                    topN[0] = sum;
                }
                Arrays.sort(topN);
                sum = 0;

                continue;
            }

            sum += Integer.parseInt(line);
        }

        if (sum != 0 && topN[0] < sum) {
            topN[0] = sum;
        }

        int topNSum = 0;
        for (int v : topN) {
            topNSum += v;
        }
        System.out.println("part 2: " + topNSum);
    }

}
