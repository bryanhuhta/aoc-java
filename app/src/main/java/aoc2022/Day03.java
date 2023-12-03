package aoc2022;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aocutils.Day;

public class Day03 extends Day {
    public Day03() {
        super(2022, 3);
    }

    @Override
    protected void _run() throws IOException {
        List<String> lines = getLines();
        part1(lines);

        lines = isTestMode() ? getLines(String.format("%s/test2.txt", dir)) : lines;
        part2(lines);
    }

    private static void part1(List<String> lines) {
        Set<Character> compartmentA = new HashSet<>();
        Set<Character> compartmentB = new HashSet<>();
        int score = 0;

        for (String line : lines) {
            line.substring(0, line.length() / 2).chars()
                .forEach(c -> compartmentA.add((char) c));
            line.substring(line.length() / 2).chars()
                .forEach(c -> compartmentB.add((char) c));

            // Intersect.
            compartmentA.retainAll(compartmentB);
            score += score(compartmentA);

            // Reset A and B.
            compartmentA.clear();
            compartmentB.clear();
        }

        System.out.println("part 1: " + score);
    }

    private static void part2(List<String> lines) {
        int score = 0;
        System.out.println("part 2: " + score);
    }

    private static int score(Set<Character> set) {
        int score = 0;
        for (Character c : set) {
            // a-z ==  1 - 26
            // A-Z == 27 - 52
            int offset = (int) c >= (int) 'a' ? (int) 'a' : (int) 'A' - 26;
            score += (int) c - offset + 1;
        }
        return score;
    }
}
