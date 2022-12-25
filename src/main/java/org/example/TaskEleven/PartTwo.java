package org.example.TaskEleven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PartTwo {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_eleven/" + "inputTest");
            Scanner myReader = new Scanner(myObj);

            int monkeyId = -1;
            List<Integer> items = new ArrayList<>();
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
                            .map(Integer::parseInt)
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

            // 20 rounds
            for (int r = 0; r < 20; r++) {

                for(int i = 0; i < monkeys.size(); i++) {
                    Monkey currentMonkey = monkeys.get(i);

                    List<Integer> currentMonkeyItems = new ArrayList<>(currentMonkey.items);
                    for(int j = 0; j < currentMonkeyItems.size(); j++) {
                        Integer currentItem = currentMonkey.items.get(j);

                        int worryLevel = currentMonkey.applyOperation(currentItem);
                        int monkeyBoredWorryLevel = worryLevel / 3;
                        int targetMonkeyId = currentMonkey.getTargetMonkeyId(monkeyBoredWorryLevel);

                        for (Monkey monkey : monkeys) {
                            if (monkey.getId() == targetMonkeyId) {
                                List<Integer> newItems = new ArrayList<>(monkey.items);
                                newItems.add(monkeyBoredWorryLevel);
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
                System.out.println(monkeys);
            }

            monkeys.sort((o1, o2) -> o2.getInspectedAmount() - o1.getInspectedAmount());
            System.out.println(monkeys);
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
        private final Integer worryDivisible;
        private final int divisibleFalseMonkey;
        private final int divisibleTrueMonkey;
        public List<Integer> items;
        public int inspectedAmount = 0;

        public Monkey(
                List<Integer> items,
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

        public int applyOperation(int valueOld) {
            int l = this.lValue.equals("old") ? valueOld : Integer.parseInt(this.lValue);
            int r = this.rValue.equals("old") ? valueOld : Integer.parseInt(this.rValue);

            return switch (this.op) {
                case '*' -> l * r;
                case '+' -> l + r;
                case '/' -> l / r;
                case '-' -> l - r;
                default -> -1;
            };
        }

        public int getTargetMonkeyId(int playerWorryLevel) {
            return playerWorryLevel % this.worryDivisible == 0 ? this.divisibleTrueMonkey : this.divisibleFalseMonkey;
        }

        public int getId() {
            return id;
        }

        public int getInspectedAmount() {
            return inspectedAmount;
        }

        @Override
        public String toString() {
            return id + ": " + items + "; Inspected " + inspectedAmount + "\n";
        }

    }

}