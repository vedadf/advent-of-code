package org.example.TaskEight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PartOne {

    public static void main(String[] args) {
        try {
            int numCols = 5;
            String file = "inputTest";

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

            System.out.println(getNumOfVisibleTrees(map));

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int getNumOfVisibleTrees(int[][] map) {
        // trees on the edge are always visible
        int res = getNumOfEdgeTrees(map.length);

        int colCoord = 1;
        int rowCoord = 1;
        for (int i = colCoord; i < map.length - 1; i++) {
            for (int j = rowCoord; j < map[i].length - 1; j++) {
                if (isTreeVisible(map, i, j)) {
                    res++;
                }
            }
        }
        return res;
    }

    private static boolean isTreeVisible(int[][] map, int originI, int originJ) {
        //                                              up, down, left, right
        List<Boolean> isVisibleDirections = Arrays.asList(true, true, true, true);

        // rows:
        for (int i = 0; i < map.length; i++) {
            int treeSize = map[i][originJ];
            if (treeSize >= map[originI][originJ]) {
                if (i < originI) { // upwards
                    isVisibleDirections.set(0, false);
                } else if (i > originI) { // downwards
                    isVisibleDirections.set(1, false);
                }
            }
        }

        // cols:
        for (int i = 0; i < map.length; i++) {
            int treeSize = map[originI][i];
            if (treeSize >= map[originI][originJ]) {
                if (i < originJ) { // left
                    isVisibleDirections.set(2, false);
                } else if (i > originJ) { // right
                    isVisibleDirections.set(3, false);
                }
            }
        }

        return isVisibleDirections.contains(true);
    }

    private static int getNumOfEdgeTrees(int colNum) {
        // grid is a square
        return (colNum * 4) - 4;
    }

}