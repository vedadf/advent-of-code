package org.example.TaskFive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PartTwo {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_five/input");
            Scanner myReader = new Scanner(myObj);

            // stack that contains temporary keys
            Map<Integer, List<Character>> tempStackList = new HashMap<>();
            // tempStackList converted to stack that contains keys based on input (safeguard if stack names are not incremental integers)
            Map<Integer, Stack<Character>> realStackList = new HashMap<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.length() > 0 && data.charAt(0) == ' ' && data.charAt(1) == '1') { // for even more edge-case coverage, check if data.charAt(1) is number
                    // align stack map keys according to stack labels from input
                    int counter = 1;
                    for (int i = 1; i < data.length(); i+=4) {
                        int realStackIndex = Integer.parseInt(Character.toString(data.charAt(i)));
                        Stack<Character> stack = new Stack<>();
                        // fill stack
                        for (int j = tempStackList.get(counter).size() - 1; j >= 0; j--) {
                            stack.push(tempStackList.get(counter).get(j));
                        }
                        realStackList.put(realStackIndex, stack);
                        counter++;
                    }
                } else if (data.length() > 0 && data.charAt(0) == 'm') {
                    // rearrangement
                    String dataNoMove = data.replace("move ", "");
                    String[] numOriginPair = dataNoMove.split(" from ");
                    String[] originTargetPair = numOriginPair[1].split(" to ");

                    int numToMove = Integer.parseInt(numOriginPair[0]);
                    int origin = Integer.parseInt(originTargetPair[0]);
                    int target = Integer.parseInt(originTargetPair[1]);

                    // pop items from the origin stack and save to temp list
                    List<Character> itemsToMove = new ArrayList<>();

                    for (int i = 0; i < numToMove; i++) {
                        itemsToMove.add(realStackList.get(origin).pop());
                    }

                    // moving all boxes at once
                    if (numToMove > 1) {
                        Collections.reverse(itemsToMove);
                    }

                    // add popped items to the target stack
                    itemsToMove.forEach(item -> {
                        realStackList.get(target).push(item);
                    });
                } else if (data.length() > 0 && data.charAt(0) != 'm') {
                    // load data
                    for(int i = 1; i < data.length(); i+=4) {
                        char c = data.charAt(i);

                        // to which stack the char belongs
                        int stackIndex = i / 4;

                        // + 1 to align rearrangement procedure labels
                        stackIndex++;

                        // if stack does not exist, create it
                        if (!tempStackList.containsKey(stackIndex)) {
                            List<Character> list = new ArrayList<>();
                            if (c != ' ') {
                                list.add(c);
                            }
                            tempStackList.put(stackIndex, list);
                        } else {
                            if (c != ' ') {
                                tempStackList.get(stackIndex).add(c);
                            }
                        }

                    }
                }

            }

            String res = realStackList.values().stream()
                    .map(Stack::pop)
                    .map(item -> Character.toString(item))
                    .collect(Collectors.joining());

            System.out.println(res);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}