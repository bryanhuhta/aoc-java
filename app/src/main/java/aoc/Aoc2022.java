package aoc;

import java.util.Arrays;
import java.util.List;

import aoc2022.*; // Pull in all day implementations.
import aocutils.Day;

public class Aoc2022 {
    private static final List<Day> days = Arrays.asList(
        new Day01(),
        new Day02()
    );

    /**
     * Gets a day (1-indexed) to run. As special case, if zero is passed, the
     * latest day will run.
     *
     * @param day 1-indexed day number.
     * @return A Day implementation.
     */
    public static Day getDayOrLatest(int day) {
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
}
