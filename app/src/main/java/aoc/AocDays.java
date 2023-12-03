package aoc;

import java.util.Arrays;
import java.util.List;
import aocutils.Day;

public class AocDays {
    private static final List<Day> allDays = Arrays.asList(
        // 2023
        new aoc2023.Day01(),
        new aoc2023.Day02(),

        // 2022
        new aoc2022.Day01(),
        new aoc2022.Day02(),
        new aoc2022.Day03()
    );

    /**
     * Gets a day (1-indexed) to run. As special case, if zero is passed, the
     * latest day will run.
     *
     * @param day 1-indexed day number.
     * @return A Day implementation.
     */
    public static Day getDayOrLatest(int year, int day) {
        if (year == 0) {
            year = getYears().getLast();
        }

        List<Day> days = getDaysForYear(year);
        if (days.size() == 0) {
            throw new IllegalArgumentException(String.format("no days for year: %d", year));
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

    private static List<Day> getDaysForYear(int year) {
        return allDays.stream()
            .filter(d -> d.getYear() == year)
            .toList();
    }

    private static List<Integer> getYears() {
        return allDays.stream()
            .map(d -> d.getYear())
            .distinct()
            .sorted()
            .toList();
    }
}
