package aoc;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import aocutils.Day;

public class App {
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

        Day aocDay = AocDays.getDayOrLatest(year, day);
        aocDay.run(mode);
    }
}
