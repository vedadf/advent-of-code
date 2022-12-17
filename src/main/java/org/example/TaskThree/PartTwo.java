package org.example.TaskThree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PartTwo {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_three/input");
            Scanner myReader = new Scanner(myObj);

            int totalSum = 0;
            int counter = 0;
            Set<Character> uniqueChars = new HashSet<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                Set<Character> newUniqueChars = new HashSet<>();

                for(int i = 0; i < data.length(); i++) {
                    char c = data.charAt(i);
                    // since looking for unique char across three sets, use first set as ref
                    if (counter == 0) {
                        uniqueChars.add(c);
                    } else {
                        // create a subset based on lines 2 and 3
                        if (uniqueChars.contains(c)) {
                            newUniqueChars.add(c);
                        }
                    }
                }

                if (counter != 0) {
                    uniqueChars = newUniqueChars;
                }

                if (counter == 2) {
                    uniqueChars = new HashSet<>();
                    // final set contains only one element which is the intersection of all three sets
                    Character character = new ArrayList<>(newUniqueChars).get(0);
                    if (Character.isUpperCase(character)) {
                        int asciiToPriority = character - 38;
                        totalSum += asciiToPriority;
                    } else {
                        int asciiToPriority = character - 96;
                        totalSum += asciiToPriority;
                    }
                    counter = 0;
                } else {
                    counter++;
                }

            }

            System.out.println(totalSum);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}