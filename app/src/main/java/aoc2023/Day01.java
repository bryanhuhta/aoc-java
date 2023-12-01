package aoc2023;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aocutils.Day;

public class Day01 extends Day {
    public static final List<String> digits = Arrays.asList(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine"
    );

    public static final List<String> digitsReversed = digits.stream()
        .map(s -> new StringBuilder(s).reverse().toString())
        .toList();

    public Day01() {
        super(2023, 1);
    }

    @Override
    protected void _run() throws IOException {
        List<String> lines = getLines();
        part1(lines);

        lines = isTestMode() ? getLines(String.format("%s/test2.txt", dir)) : getLines();
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int sum = lines.stream()
            .map(Day01::decodeString)
            .collect(Collectors.summingInt(Integer::intValue));
        System.out.println("part 1: " + sum);
    }

    private static void part2(List<String> lines) {
        int sum = lines.stream()
            .map(Day01::decodeStringV2)
            .collect(Collectors.summingInt(Integer::intValue));
        System.out.println("part 2: " + sum);
    }

    private static int decodeString(String s) {
        StringBuilder numberBuilder = new StringBuilder();
        StringBuilder sb = new StringBuilder(s);

        // Find the first digit.
        sb.toString().codePoints()
            .filter(Character::isDigit)
            .findFirst()
            .ifPresent(c -> numberBuilder.appendCodePoint(c));

        // Find the last digit.
        sb.reverse().toString().codePoints()
            .filter(Character::isDigit)
            .findFirst()
            .ifPresent(c -> numberBuilder.appendCodePoint(c));

        return Integer.parseInt(numberBuilder.toString());
    }

    private static int decodeStringV2(String s) {
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, s.length())
            .filter(i -> {
                char c = s.charAt(i);

                // Check if this is a digit.
                if (Character.isDigit(c)) {
                    return true;
                }

                // Check if this a spelled digit.
                for (String digit : digits) {
                    if (s.startsWith(digit, i)) {
                        return true;
                    }
                }

                return false;
            })
            .findFirst()
            .ifPresent(i -> {
                char c = s.charAt(i);

                if (Character.isDigit(c)) {
                    sb.append(c);
                    return;
                }

                for (int idx = 0; idx < digits.size(); idx++) {
                    String digit = digits.get(idx);
                    if (s.startsWith(digit, i)) {
                        sb.append(idx+1);
                        return;
                    }
                }
            });

        String reversed = new StringBuilder(s).reverse().toString();
        IntStream.range(0, reversed.length())
            .filter(i -> {
                char c = reversed.charAt(i);

                // Check if this is a digit.
                if (Character.isDigit(c)) {
                    return true;
                }

                // Check if this a spelled digit.
                for (String digit : digitsReversed) {
                    if (reversed.startsWith(digit, i)) {
                        return true;
                    }
                }

                return false;
            })
            .findFirst()
            .ifPresent(i -> {
                char c = reversed.charAt(i);

                if (Character.isDigit(c)) {
                    sb.append(c);
                    return;
                }

                for (int idx = 0; idx < digitsReversed.size(); idx++) {
                    String digit = digitsReversed.get(idx);
                    if (reversed.startsWith(digit, i)) {
                        sb.append(idx+1);
                        return;
                    }
                }
            });

        return Integer.parseInt(sb.toString());
    }
}
