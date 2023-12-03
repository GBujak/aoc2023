package com.gbujak.day1;

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {

    public static void main(String[] args) {
        part2();
    }

    private static void part1() {
        var lines = Day1Input.input.lines();
        System.out.println(
            lines
                .map(Day1::getNumValue)
                .reduce(0, Integer::sum)
        );
    }

    private static int getNumValue(String str) {
        var chars = str.chars().mapToObj(c -> (char) c).toList();

        var left = chars
            .stream()
            .flatMap(it -> parseInt(it).stream())
            .findFirst()
            .orElseThrow();

        var right = chars.reversed()
            .stream()
            .flatMap(it -> parseInt(it).stream())
            .findFirst()
            .orElseThrow();

        return Integer.parseInt(left.toString() + right.toString());
    }

    private static Optional<Integer> parseInt(Character c) {
        try {
            return Optional.of(Integer.parseInt(c.toString()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static void part2() {
        var lines = Day1Input.input.lines().toList();
        System.out.println(new Solution2().solve(lines));
    }

    private static class Solution2 {

        record Item(String value, int digit) {}

        List<Item> items = List.of(
            new Item("one", 1),
            new Item("two", 2),
            new Item("three", 3),
            new Item("four", 4),
            new Item("five", 5),
            new Item("six", 6),
            new Item("seven", 7),
            new Item("eight", 8),
            new Item("nine", 9)
        );

        int solve(List<String> lines) {
            var sum = 0;

            for (var line : lines) {
                var numbers = readNumbers(line);

                System.out.println("first: " + numbers.getFirst() + " last: " + numbers.getLast());

                sum += Integer.parseInt(
                    numbers.getFirst().toString() +
                        numbers.getLast().toString()
                );
            }

            return sum;
        }

        List<Integer> readNumbers(String line) {
            var results = new ArrayList<Integer>();

            for (int i = 0; i < line.length(); i++) {
                parseInt(line.charAt(i)).ifPresent(results::add);
                parseWordDigid(line.substring(i)).ifPresent(results::add);
            }

            return Collections.unmodifiableList(results);
        }

        private Optional<Integer> parseWordDigid(String val) {
            for (var i : items) {
                if (val.indexOf(i.value()) == 0) {
                    return Optional.of(i.digit());
                }
            }
            return Optional.empty();
        }
    }
}
