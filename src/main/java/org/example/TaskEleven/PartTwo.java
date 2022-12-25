package org.example.TaskEleven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PartTwo {

    /**
     * Helped: https://www.reddit.com/r/adventofcode/comments/zih7gf/2022_day_11_part_2_what_does_it_mean_find_another/izr79go/?context=3
     */
    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_eleven/" + "input");
            Scanner myReader = new Scanner(myObj);

            int monkeyId = -1;
            List<Long> items = new ArrayList<>();
            String lValue = null;
            String rValue = null;
            char op = ' ';
            int worryDivisible = -1;
            int divisibleFalseMonkey = -1;
            int divisibleTrueMonkey = -1;

            List<Monkey> monkeys = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.length() == 0) {
                    continue;
                }

                if (data.charAt(0) == 'M') {
                    monkeyId = Integer.parseInt(data.split(" ")[1].replace(":", ""));
                } else if (data.charAt(2) == 'S') {
                    String repl = data.replace("  Starting items: ", "");
                    items = Arrays.stream(repl.split(","))
                            .map(item -> item.replace(" ", ""))
                            .map(Long::parseLong)
                            .toList();
                } else if (data.charAt(2) == 'O') {
                    String repl = data.replace("  Operation: new = ", "");
                    String repl2 = repl.replace(" ", "");
                    String splitter = "\\+";
                    op = '+';
                    if (repl2.contains("*")) {
                        splitter = "\\*";
                        op = '*';
                    }
                    lValue = repl2.split(splitter)[0];
                    rValue = repl2.split(splitter)[1];
                } else if (data.charAt(2) == 'T') {
                    String repl = data.replace("  Test: divisible by ", "");
                    worryDivisible = Integer.parseInt(repl);
                } else if (data.contains("true")) {
                    String repl = data.replace("    If true: throw to monkey ", "");
                    divisibleTrueMonkey = Integer.parseInt(repl);
                } else if (data.contains("false")) {
                    String repl = data.replace("    If false: throw to monkey ", "");
                    divisibleFalseMonkey = Integer.parseInt(repl);

                    Monkey monkey = new Monkey(
                            items,
                            monkeyId,
                            lValue,
                            rValue,
                            op,
                            worryDivisible,
                            divisibleFalseMonkey,
                            divisibleTrueMonkey
                    );
                    monkeys.add(monkey);
                }

            }
            myReader.close();

            long divisibleTotal = monkeys.stream()
                    .map(monkey -> monkey.worryDivisible)
                    .reduce(1L, (a, b) -> a * b);

            // 10 000 rounds
            for (int r = 0; r < 10000; r++) {

                for(int i = 0; i < monkeys.size(); i++) {
                    Monkey currentMonkey = monkeys.get(i);

                    List<Long> currentMonkeyItems = new ArrayList<>(currentMonkey.items);
                    for(int j = 0; j < currentMonkeyItems.size(); j++) {
                        Long currentItem = currentMonkey.items.get(j);

                        long worryLevel = currentMonkey.applyOperation(currentItem);
                        worryLevel = worryLevel % divisibleTotal;
                        int targetMonkeyId = currentMonkey.getTargetMonkeyId(worryLevel);

                        for (Monkey monkey : monkeys) {
                            if (monkey.getId() == targetMonkeyId) {
                                List<Long> newItems = new ArrayList<>(monkey.items);
                                newItems.add(worryLevel);
                                monkey.items = newItems;
                                break;
                            }
                        }

                        currentMonkeyItems.remove(j);
                        j--;
                        currentMonkey.inspectedAmount++;
                        currentMonkey.items = currentMonkeyItems;
                    }

                }

            }
            monkeys.sort((o1, o2) -> (int) (o2.getInspectedAmount() - o1.getInspectedAmount()));

            System.out.println(monkeys.get(0).getInspectedAmount() * monkeys.get(1).getInspectedAmount());

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static class Monkey {
        private final int id;
        private final String lValue;
        private final String rValue;
        private final char op;
        private final long worryDivisible;
        private final int divisibleFalseMonkey;
        private final int divisibleTrueMonkey;
        public List<Long> items;
        public long inspectedAmount = 0;

        public Monkey(
                List<Long> items,
                int id,
                String lValue,
                String rValue,
                char op,
                int worryDivisible,
                int divisibleFalseMonkey,
                int divisibleTrueMonkey
        ) {
            this.items = items;
            this.id = id;
            this.lValue = lValue;
            this.rValue = rValue;

            this.op = op;
            this.worryDivisible = worryDivisible;
            this.divisibleFalseMonkey = divisibleFalseMonkey;
            this.divisibleTrueMonkey = divisibleTrueMonkey;
        }

        public long applyOperation(long valueOld) {
            long l = this.lValue.equals("old") ? valueOld : Long.parseLong(this.lValue);
            long r = this.rValue.equals("old") ? valueOld : Long.parseLong(this.rValue);

            return switch (this.op) {
                case '*' -> l * r;
                case '+' -> l + r;
                case '/' -> l / r;
                case '-' -> l - r;
                default -> -1;
            };
        }

        public int getTargetMonkeyId(long playerWorryLevel) {
            return playerWorryLevel % worryDivisible == 0 ? this.divisibleTrueMonkey : this.divisibleFalseMonkey;
        }

        public int getId() {
            return id;
        }

        public long getInspectedAmount() {
            return inspectedAmount;
        }

        @Override
        public String toString() {
            return id + ": " + items + "; Inspected " + inspectedAmount + "\n";
        }

    }

}