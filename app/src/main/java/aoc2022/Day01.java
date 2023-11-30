package aoc2022;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day01 {
    private final static String DAY_NAME = "Day 1";

    public static void run(int mode) throws IOException {
        StringBuilder sb = new StringBuilder("Day 1");
        String filename;
        switch (mode) {
        case 0:
            filename = "day01.test.txt";
            sb.append(" (test)");
            break;
        case 1: filename = "day01.txt"; break;
        default: throw new IllegalArgumentException("(" + DAY_NAME + ") invalid mode: " + mode);
        }
        System.out.println(sb.toString());

        List<String> lines = ResourceUtils.lines(filename);


        int max = max(lines);
        System.out.println("part 1: " + max);

        int[] topN = topNMax(3, lines);
        int topNSum = 0;
        for (int v : topN) {
            topNSum += v;
        }
        System.out.println("part 2: " + topNSum);
    }

    private static int max(List<String> lines) {
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

        return max;
    }

    private static int[] topNMax(int n, List<String> lines) {
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

        return topN;
    }
}
