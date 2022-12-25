package org.example.TaskTen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PartOne {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_ten/" + "input");
            Scanner myReader = new Scanner(myObj);

            int registerValue = 1;

            Queue<Integer> registerValQueue = new LinkedList<>();

            int totalSignalStrength = 0;
            int observedCycle = 20;
            int observedCycleIncrement = 40;
            int cycle = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.charAt(0) == 'a') {
                    String[] s = data.split(" ");
                    Integer val = Integer.parseInt(s[1]);
                    // since addx takes 2 cycles to complete, it is simulated by 'artificially' adding a 0 to the queue
                    registerValQueue.add(0);
                    registerValQueue.add(val);
                } else if (data.charAt(0) == 'n') {
                    registerValQueue.add(0);
                }
            }

            while(registerValQueue.size() > 0) {
                if (cycle == observedCycle) {
                    int posRegisterValue = registerValue < 0 ? -registerValue : registerValue;
                    int currentSignalStrength = cycle * posRegisterValue;
                    totalSignalStrength += currentSignalStrength;
                    observedCycle += observedCycleIncrement;
                }
                int currentValue = registerValQueue.remove();
                registerValue += currentValue;
                cycle++;
            }

            System.out.println(totalSignalStrength);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}