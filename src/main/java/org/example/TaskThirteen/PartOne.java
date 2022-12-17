package org.example.TaskThirteen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PartOne {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_five/inputTest");
            Scanner myReader = new Scanner(myObj);

            int totalSum = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

            }

            System.out.println(totalSum);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}