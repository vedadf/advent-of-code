package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskOne {
    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_one/input");
            Scanner myReader = new Scanner(myObj);

            float currentTotal = 0f;

            List<Float> maxes = new ArrayList<>(Arrays.asList(0f, 0f, 0f));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.equals("")) {

                    for (int i = 0; i < maxes.size(); i++) {
                        float currMax = maxes.get(i);
                        if (currentTotal > currMax) {
                            maxes.set(i, currentTotal);
                            break;
                        }
                    }

                    //shifting lowest to index 0
                    float indexZero = maxes.get(0);
                    for (int i = 1; i < maxes.size(); i++) {
                        float currMax = maxes.get(i);
                        if (currMax < indexZero) {
                            maxes.set(0, currMax);
                            maxes.set(i, indexZero);
                            break;
                        }
                    }

                    currentTotal = 0f;
                } else {
                    float current = Float.parseFloat(data);
                    currentTotal += current;
                }

            }

            for (int i = 0; i < maxes.size(); i++) {
                float currMax = maxes.get(i);
                if (currentTotal > currMax) {
                    maxes.set(i, currentTotal);
                    break;
                }
            }

            Double res = maxes.stream().mapToDouble(Float::doubleValue).sum();
            System.out.println(res);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}