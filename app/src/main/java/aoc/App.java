package aoc;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import aocutils.Day;

public class App {
    static {
        // 2023
        new aoc2023.Day01();
        new aoc2023.Day02();

        // 2022
        new aoc2022.Day01();
        new aoc2022.Day02();
        new aoc2022.Day03();
        new aoc2022.Day04();
    }

    public static void main(String[] args) throws IOException, ParseException {
        Options options = new Options()
            .addOption(
                "m",
                "mode",
                true,
                "the mode to run the aoc challenge (default: 0 (test mode))"
            )
            .addOption(
                "y",
                "year",
                true,
                "the year of AoC day"
            );

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        args = cmd.getArgs();

        int mode = Integer.parseInt(cmd.getOptionValue("m", "0"));
        int year = Integer.parseInt(cmd.getOptionValue("y", "0"));
        int day = args.length >= 1 ? Integer.parseInt(args[0]) : 0;

        Day.getDayOrLatest(year, day).run(mode);
    }
}
