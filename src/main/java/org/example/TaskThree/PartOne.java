package org.example.TaskThree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PartOne {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_three/input");
            Scanner myReader = new Scanner(myObj);

            int totalSum = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String firstCompartment = data.substring(0, data.length() / 2);
                String secondCompartment = data.substring(data.length() / 2);

                int totalRucksackPriority = 0;

                List<Character> visitedChars = new ArrayList<>();
                for (int i = 0; i < firstCompartment.length(); i++) {
                    char firstCompartmentItem = firstCompartment.charAt(i);
                    for (int j = 0; j < secondCompartment.length(); j++) {
                        char secondCompartmentItem = secondCompartment.charAt(j);
                        if (firstCompartmentItem == secondCompartmentItem && !visitedChars.contains(firstCompartmentItem)) {
                            visitedChars.add(firstCompartmentItem);
                            if (Character.isUpperCase(firstCompartmentItem)) {
                                int asciiToPriority = firstCompartmentItem - 38;
                                totalRucksackPriority += asciiToPriority;
                            } else {
                                int asciiToPriority = firstCompartmentItem - 96;
                                totalRucksackPriority += asciiToPriority;
                            }
                        }
                    }
                }

                totalSum += totalRucksackPriority;

            }

            System.out.println(totalSum);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}