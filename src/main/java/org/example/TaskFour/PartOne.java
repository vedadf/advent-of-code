package org.example.TaskFour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PartOne {

    private static boolean isRangeIncluded(int start1, int end1, int start2, int end2) {
        return start2 >= start1 && end2 <= end1;
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_four/input");
            Scanner myReader = new Scanner(myObj);

            int totalSum = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] ranges = data.split(",");
                String[] firstElf = ranges[0].split("-");
                String[] secondElf = ranges[1].split("-");

                List<Integer> firstElfPair = Arrays.asList(Integer.parseInt(firstElf[0]), Integer.parseInt(firstElf[1]));
                List<Integer> secondElfPair = Arrays.asList(Integer.parseInt(secondElf[0]), Integer.parseInt(secondElf[1]));

                if (isRangeIncluded(firstElfPair.get(0), firstElfPair.get(1), secondElfPair.get(0), secondElfPair.get(1)) ||
                        isRangeIncluded(secondElfPair.get(0), secondElfPair.get(1), firstElfPair.get(0), firstElfPair.get(1))) {
                    totalSum++;
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