package aoc2022;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import aocutils.Day;
import aocutils.Utils;

public class Day01 extends Day {
    public Day01() {
        super(2022, 1);
    }

    @Override
    public void run(int mode) throws IOException {
        System.out.println(Utils.dayTitle(name, mode));

        String filename = Utils.filenameForDay(dir, mode);
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
