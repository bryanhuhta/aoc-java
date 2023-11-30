package aoc2022;

import java.io.IOException;
import java.util.List;
import java.util.Map;

enum Shape {
    ROCK,
    PAPER,
    SCISSORS
}

enum Outcome {
    WIN,
    LOSE,
    DRAW
}

public class Day02 {
    private final static String DAY_NAME = "Day 2";
    private final static String DAY_DIR = "day02";

    private final static Map<String, Shape> shapes = Map.of(
        "A", Shape.ROCK,
        "X", Shape.ROCK,

        "B", Shape.PAPER,
        "Y", Shape.PAPER,

        "C", Shape.SCISSORS,
        "Z", Shape.SCISSORS
    );

    private final static Map<String, Outcome> outcomes = Map.of(
        "X", Outcome.LOSE,
        "Y", Outcome.DRAW,
        "Z", Outcome.WIN
    );

    public static void run(int mode) throws IOException {
        System.out.println(Utils.dayTitle(DAY_NAME, mode));

        String filename = Utils.filenameForDay(DAY_DIR, mode);
        List<String> lines = Utils.lines(filename);

        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int score = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");

            Shape opponentShape = shapes.get(parts[0]);
            Shape playerShape = shapes.get(parts[1]);
            Outcome outcome = playRound(opponentShape, playerShape);

            score += score(playerShape, outcome);
        }

        System.out.println("part 1: " + score);
    }

    private static void part2(List<String> lines) {
        int score = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");

            Shape opponentShape = shapes.get(parts[0]);
            Outcome outcome = outcomes.get(parts[1]);
            Shape playerShape = playRound(opponentShape, outcome);

            score += score(playerShape, outcome);
        }

        System.out.println("part 2: " + score);
    }

    private static int score(Shape shape, Outcome outcome) {
        int score = 0;

        switch (shape) {
        case ROCK: score += 1; break;
        case PAPER: score += 2; break;
        case SCISSORS: score += 3; break;
        }

        switch (outcome) {
        case WIN: score += 6; break;
        case LOSE: score += 0; break;
        case DRAW: score += 3; break;
        }

        return score;
    }

    private static Outcome playRound(Shape opponent, Shape player) {
        if (opponent == player) {
            return Outcome.DRAW;
        }

        switch (opponent) {
        case ROCK: return player == Shape.PAPER ? Outcome.WIN : Outcome.LOSE;
        case PAPER: return player == Shape.SCISSORS ? Outcome.WIN : Outcome.LOSE;
        case SCISSORS: return player == Shape.ROCK ? Outcome.WIN : Outcome.LOSE;
        default: throw new IllegalArgumentException("unknown opponent hand: " + opponent);
        }
    }

    private static Shape playRound(Shape opponent, Outcome outcome) {
        if (outcome == Outcome.DRAW) {
            return opponent;
        }

        switch (opponent) {
        case ROCK: return outcome == Outcome.WIN ? Shape.PAPER : Shape.SCISSORS;
        case PAPER: return outcome == Outcome.WIN ? Shape.SCISSORS : Shape.ROCK;
        case SCISSORS: return outcome == Outcome.WIN ? Shape.ROCK : Shape.PAPER;
        default: throw new IllegalArgumentException("unknown opponent hand: " + opponent);
        }
    }
}
