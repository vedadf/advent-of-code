package org.example.TaskTen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PartTwo {

    private static void printMatrix(char[][] matrix) {
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 40; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_ten/" + "input");
            Scanner myReader = new Scanner(myObj);

            int registerValue = 1;

            Queue<Integer> registerValQueue = new LinkedList<>();

            // refers to middle pixel
            int spritePosition = 0;

            char[][] displayMatrix = new char[6][40];

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

            int displayCursor = 0;
            int displayRow = -1;
            while(registerValQueue.size() > 0) {
                // display draw coordinates
                if (displayCursor % 40 == 0) {
                    displayCursor = 0;
                    displayRow++;
                }

                // draw decision
                if (displayCursor >= spritePosition - 1 && displayCursor <= spritePosition + 1) {
                    displayMatrix[displayRow][displayCursor] = '#';
                } else {
                    displayMatrix[displayRow][displayCursor] = '.';
                }

                // increment register and cycle, update sprite position
                int currentValue = registerValQueue.remove();
                registerValue += currentValue;
                spritePosition = registerValue;
                // this is our cycle
                displayCursor++;
            }

            printMatrix(displayMatrix);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}