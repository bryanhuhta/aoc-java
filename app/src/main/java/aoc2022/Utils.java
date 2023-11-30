package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Gets the lines of a file.
     *
     * @param filename the file name.
     * @return Each line of the file.
     * @throws IOException
     */
    public static List<String> lines(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        ArrayList<String> lines = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            lines.add(line);
        }

        return lines;
    }

    public static String filenameForDay(String day, int mode) {
        String filename;
        switch (mode) {
        case 0: filename = day + "/test.txt"; break;
        case 1: filename = day + "/data.txt"; break;
        default: throw new IllegalArgumentException("invalid mode: " + mode);
        }

        return filename;
    }

    public static boolean isTestMode(int mode) {
        return mode == 0;
    }

    public static String dayTitle(String day, int mode) {
        StringBuilder sb = new StringBuilder(day);
        if (Utils.isTestMode(mode)) {
            sb.append(" (test)");
        };
        return sb.toString();
    }
}
