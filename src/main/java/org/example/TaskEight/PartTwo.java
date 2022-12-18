package org.example.TaskEight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PartTwo {

    public static void main(String[] args) {
        try {
            int numCols = 5;
            String file = "input";

            if (file.equals("input")) {
                numCols = 99;
            } else if (file.equals("inputTest")) {
                numCols = 5;
            } else {
                throw new RuntimeException("File not supported");
            }

            File myObj = new File("src/main/resources/task_eight/" + file);
            Scanner myReader = new Scanner(myObj);

            // grid is a square
            int[][] map = new int[numCols][numCols];

            int rowCounter = 0;
            // load data
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for(int i = 0; i < data.length(); i++) {
                    int treeSize = Integer.parseInt(Character.toString(data.charAt(i)));
                    map[rowCounter][i] = treeSize;
                }
                rowCounter++;
            }

            System.out.println(getHighestScenicScore(map));

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int getHighestScenicScore(int[][] map) {
        int res = -1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int treeScenicScore = getScenicScore(map, i, j);
                if (treeScenicScore > res) {
                    res = treeScenicScore;
                }
            }
        }
        return res;
    }

    private static int getScenicScore(int[][] map, int originI, int originJ) {
        //                                  up, down, left, right
        List<Integer> viewingDistances = Arrays.asList(0, 0, 0, 0);

        // looking up:
        for (int i = originI - 1; i >= 0; i--) {
            int treeSize = map[i][originJ];
            viewingDistances.set(0, viewingDistances.get(0) + 1);
            if (treeSize >= map[originI][originJ]) {
                break;
            }
        }

        // looking down:
        for (int i = originI + 1; i < map.length; i++) {
            int treeSize = map[i][originJ];
            viewingDistances.set(1, viewingDistances.get(1) + 1);
            if (treeSize >= map[originI][originJ]) {
                break;
            }
        }

        // looking left:
        for (int i = originJ - 1; i >= 0; i--) {
            int treeSize = map[originI][i];
            viewingDistances.set(2, viewingDistances.get(2) + 1);
            if (treeSize >= map[originI][originJ]) {
                break;
            }
        }

        // looking right:
        for (int i = originJ + 1; i < map[originI].length; i++) {
            int treeSize = map[originI][i];
            viewingDistances.set(3, viewingDistances.get(3) + 1);
            if (treeSize >= map[originI][originJ]) {
                break;
            }
        }

        return viewingDistances.stream().reduce(1, (a, b) -> a * b);
    }

}