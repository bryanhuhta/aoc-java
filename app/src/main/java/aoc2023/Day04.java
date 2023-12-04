package aoc2023;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aocutils.Day;

public class Day04 extends Day {
    public Day04() {
        super(2023, 4);
    }

    @Override
    protected void _run() throws IOException {
        List<String> lines = getLines();
        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        int result = 0;

        for (String line : lines) {
            Card card = new Card(line);
            result += card.getScore();
        }

        System.out.printf("part 1: %d\n", result);
    }

    private static void part2(List<String> lines) {
        int result = 0;

        List<Card> cards = new ArrayList<>();
        for (String line : lines) {
            Card card = new Card(line);
            cards.add(card);
        }

        Deque<Card> winnings = new ArrayDeque<>(cards);
        Map<Integer, Integer> processedCards = new HashMap<>();

        while (winnings.size() > 0) {
            Card card = winnings.poll();
            processedCards.compute(card.getId(), (k, v) -> {
                return v != null ? v + 1 : 1;
            });

            int matches = card.getMatches();
            for (int i = 0; i < matches; i++) {
                winnings.add(cards.get(card.getId() + i));
            }
        }

        for (Integer count : processedCards.values()) {
            result += count;
        }

        System.out.printf("part 2: %d\n", result);
    }
}

class Card {
    private int id;
    private Set<String> numbers = new HashSet<>();
    private Set<String> winningNumbers = new HashSet<>();

    public Card(String s) {
        String[] parts = s.split(":");
        id = Integer.parseInt(parts[0].trim().split("\\s+")[1]);

        parts = parts[1].split("\\|");
        for (String number : parts[0].trim().split("\\s+")) {
            numbers.add(number);
        }

        for (String number : parts[1].trim().split("\\s+")) {
            winningNumbers.add(number);
        }
    }

    public int getMatches() {
        return (int) numbers.stream()
            .filter(s -> winningNumbers.contains(s))
            .count();
    }

    public int getScore() {
        int count = getMatches();
        return count > 1 ? 1 << (count - 1) : count;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Card [id=" + id + ", numbers=" + numbers + ", winningNumbers=" + winningNumbers + "]";
    }
}
