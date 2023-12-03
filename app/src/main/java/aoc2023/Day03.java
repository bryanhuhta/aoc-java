package aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aocutils.Day;

public class Day03 extends Day {
    public Day03() {
        super(2023, 3);
    }

    @Override
    protected void _run() throws IOException {
        List<String> lines = getLines();

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String cur = lines.get(i);
            String prev = i - 1 >= 0 ? lines.get(i-1) : ".".repeat(cur.length());
            String next = i + 1 < lines.size() ? lines.get(i+1) : ".".repeat(cur.length());

            List<Integer> partNumbers = findPartNumbers(prev, cur, next);
            for (int n : partNumbers) {
                sum += n;
            }
        }

        System.out.printf("part 1: %d\n", sum);
    }

    private static void part2(List<String> lines) {
        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String cur = lines.get(i);
            String prev = i - 1 >= 0 ? lines.get(i-1) : ".".repeat(cur.length());
            String next = i + 1 < lines.size() ? lines.get(i+1) : ".".repeat(cur.length());

            List<Integer[]> gearPairs = findGears(prev, cur, next);
            for (Integer[] gears : gearPairs) {
                sum += gears[0] * gears[1];
            }
        }

        System.out.printf("part 2: %d\n", sum);
    }

    private static List<Integer> findPartNumbers(String prev, String cur, String next) {
        List<Integer> partNumbers = new ArrayList<>();

        int i = 0;
        StringBuilder digits = new StringBuilder();
        while (i < cur.length()) {
            char c = cur.charAt(i);
            if (!Character.isDigit(c)) {
                // Hit a non-digit character without finding a symbol. Reset the
                // digits string builder.

                digits.setLength(0);
                i++;
                continue;
            }

            boolean hasSymbol = checkNeighborsForSymbol(i, prev, cur, next);
            if (hasSymbol) {
                // Consume the remaining characters.
                for ( ; i < cur.length(); i++) {
                    c = cur.charAt(i);
                    if (!Character.isDigit(c)) {
                        break;
                    }
                    digits.append(c);
                }

                partNumbers.add(Integer.parseInt(digits.toString()));
                digits.setLength(0);
                continue;
            }

            // Save this digit in case a future digits has a symbol.
            digits.append(c);
            i++;
        }

        return partNumbers;
    }

    private static boolean checkNeighborsForSymbol(int i, String prev, String cur, String next) {
        // Possible symbol locations:
        //  1 2 3
        //  4 X 5
        //  6 7 8

        // prev, cur, and next form strings in this fashion:
        //
        //    ... p p p ...
        //    ... c X c ...
        //    ... n n n ...
        //
        // Where X is the target position we want to check if it has neighbors
        // which are a symbol.
        //
        // This is achieved by taking the relevant substrings of prev, cur and
        // next.
        //
        //    sub_p = [ p p p ]
        //    sub_c = [ c X c ]
        //    sub_n = [ n n n ]
        //
        // These can be appended together and treated as a single string. This
        // string can be searched and if a symbol appears, we know X is has at
        // least one symbol neighbor. We need not worry of X being a symbol,
        // though it will be checked. An invariant of this function is X can
        // never be a symbol.

        int start = Math.max(i - 1, 0);
        int end = Math.min(i + 1, cur.length() - 1) + 1;
        return String.join("", prev.substring(start, end), cur.substring(start, end), next.substring(start, end))
            .chars()
            .filter(c -> isSymbol((char) c))
            .findFirst()
            .isPresent();
    }

    private static boolean isSymbol(char c) {
        return c != '.' && !Character.isDigit(c);
    }

    private static List<Integer[]> findGears(String prev, String cur, String next) {
        List<Integer[]> gearPairs = new ArrayList<>();

        for (int i = 0; i < cur.length(); i++) {
            char c = cur.charAt(i);
            if (c != '*') {
                continue;
            }

            List<Integer> neighbors = getNeighborNumbers(i, prev, cur, next);
            if (neighbors.size() == 2) {
                gearPairs.add(new Integer[] { neighbors.get(0), neighbors.get(1) });
            }
        }

        return gearPairs;
    }

    private static List<Integer> getNeighborNumbers(int i, String prev, String cur, String next) {
        List<Integer> neighbors = new ArrayList<>();

        List<Integer[]> subsequences = new ArrayList<>();
        int[] offsets = new int[] {
            Math.max(i - 1, 0),
            i,
            Math.min(i + 1, cur.length() - 1)
        };

        // Find all digit sequences in prev.
        for (int offset : offsets) {
            if (!Character.isDigit(prev.charAt(offset))) {
                continue;
            }

            int[] bounds = getNumberBounds(offset, prev);
            boolean exists = subsequences.stream()
                .filter(seq -> seq[0] == -1 && seq[1] == bounds[0] && seq[2] == bounds[1])
                .findFirst()
                .isPresent();
            if (exists) {
                // We've already found this number, don't add it again.
                continue;
            }

            subsequences.add(new Integer[] {
                -1, // To indicate prev.
                bounds[0],
                bounds[1],
            });
        }

        // Find all digit sequences in cur.
        for (int offset : offsets) {
            if (!Character.isDigit(cur.charAt(offset))) {
                continue;
            }

            int[] bounds = getNumberBounds(offset, cur);
            boolean exists = subsequences.stream()
                .filter(seq -> seq[0] == 0 && seq[1] == bounds[0] && seq[2] == bounds[1])
                .findFirst()
                .isPresent();
            if (exists) {
                // We've already found this number, don't add it again.
                continue;
            }

            subsequences.add(new Integer[] {
                0, // To indicate cur.
                bounds[0],
                bounds[1],
            });
        }

        // Find all digit sequences in next.
        for (int offset : offsets) {
            if (!Character.isDigit(next.charAt(offset))) {
                continue;
            }

            int[] bounds = getNumberBounds(offset, next);
            boolean exists = subsequences.stream()
                .filter(seq -> seq[0] == 1 && seq[1] == bounds[0] && seq[2] == bounds[1])
                .findFirst()
                .isPresent();
            if (exists) {
                // We've already found this number, don't add it again.
                continue;
            }

            subsequences.add(new Integer[] {
                1, // To indicate next.
                bounds[0],
                bounds[1],
            });
        }

        for (Integer[] seq : subsequences) {
            switch (seq[0]) {
            case -1: neighbors.add(Integer.parseInt(prev.substring(seq[1], seq[2]))); break;
            case 0: neighbors.add(Integer.parseInt(cur.substring(seq[1], seq[2]))); break;
            case 1: neighbors.add(Integer.parseInt(next.substring(seq[1], seq[2]))); break;
            }
        }

        return neighbors;
    }

    private static int[] getNumberBounds(int i, String s) {
        int[] bounds = new int[2];

        // Search left.
        bounds[0] = i;
        for (int j = bounds[0]-1; j >= 0; j--) {
            if (!Character.isDigit(s.charAt(j))) {
                break;
            }
            bounds[0] = j;
        }

        // Search right.
        bounds[1] = i+1;
        for (int j = bounds[1]; j < s.length(); j++) {
            if (!Character.isDigit(s.charAt(j))) {
                break;
            }
            bounds[1] = j+1;
        }

        return bounds;
    }
}
