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
            );

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        int mode = Integer.parseInt(cmd.getOptionValue("m", "0"));
        int dayNumber = 0;

        args = cmd.getArgs();
        if (args.length >= 1) {
            dayNumber = Integer.parseInt(args[0]);
        }

        Day day = Aoc2022.getDayOrLatest(dayNumber);
        day.run(mode);
    }
}
