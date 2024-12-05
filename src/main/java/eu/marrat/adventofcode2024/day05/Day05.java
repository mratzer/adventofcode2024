package eu.marrat.adventofcode2024.day05;

import eu.marrat.adventofcode2024.util.Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day05 {

    public static void main(String[] args) {

        List<Rule> orderingRules = getLines("day05/input.txt")
                .takeWhile(StringUtils::isNotEmpty)
                .map(Rule::fromString)
                .toList();

        int result = getLines("day05/input.txt")
                .dropWhile(StringUtils::isNotEmpty)
                .filter(StringUtils::isNotEmpty)
                .map(pages -> Utils.toIntArray(StringUtils.split(pages, ",")))
                .filter(e -> isAlreadyInCorrectOrder(e, orderingRules))
                .mapToInt(sortedPages -> sortedPages[sortedPages.length / 2])
                .sum();

        System.out.println(result);
    }

    private static boolean isAlreadyInCorrectOrder(int[] pages, List<Rule> allRules) {
        int[] sortedPages = sortManualPages(pages, allRules);

        return Arrays.equals(pages, sortedPages);
    }

    private static int[] sortManualPages(int[] pages, List<Rule> allRules) {
        Set<Rule> applicableRulesForManual = new HashSet<>();

        for (int page : pages) {
            applicableRulesForManual.addAll(allRules.stream()
                    .filter(rule -> rule.isApplicable(page))
                    .collect(Collectors.toSet()));
        }

        int[] sortedPages = IntStream.of(pages)
                .boxed()
                .sorted(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {

                        Set<Rule> applicableRulesForCurrentPages = applicableRulesForManual.stream()
                                .filter(e -> e.isApplicable(o1, o2))
                                .collect(Collectors.toSet());

                        for (Rule rule : applicableRulesForCurrentPages) {
                            int result = rule.apply(o2, o1);

                            if (result != 0) {
                                return result;
                            }
                        }

                        return 0;
                    }
                })
                .mapToInt(e -> e)
                .toArray();

        System.out.println(ArrayUtils.toString(pages) + " -> " + ArrayUtils.toString(sortedPages));

        return sortedPages;
    }

    record Rule(int before, int after) {

        private static final Pattern PATTERN = Pattern.compile("^(\\d+)\\|(\\d+)$");

        public boolean isApplicable(int page) {
            return before == page || after == page;
        }

        public boolean isApplicable(int page1, int page2) {
            return (before == page1 || after == page1) && (before == page2 || after == page2);
        }

        public int apply(int page1, int page2) {
            if (page1 == before && page2 == after) {
                return 1;
            } else if (page1 == after && page2 == before) {
                return -1;
            } else {
                return 0;
            }
        }

        static Rule fromString(String string) {
            Matcher matcher = PATTERN.matcher(string);

            if (matcher.matches()) {
                return new Rule(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2))
                );
            } else {
                throw new IllegalArgumentException();
            }
        }

    }

}
