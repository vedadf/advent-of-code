package org.example.TaskNine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PartOne {

    private static Pair headLocation = new Pair(0, 0);
    private static Pair tailLocation = new Pair(0, 0);
    private static List<Pair> tailVisited = new ArrayList<>(Arrays.asList(tailLocation));

    private static class Pair {
        public int x;

        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x +     ", " + y + ")";
        }
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_nine/" + "input");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] s = data.split(" ");
                String direction = s[0];
                int paces = Integer.parseInt(s[1]);

                move(paces, direction);
            }

            System.out.println(tailVisited);
            System.out.println(tailVisited.size());

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static boolean isTailNearHead() {
        int[] cols = new int[]{0, 0, 1, 0, -1, -1, 1, 1, -1};
        int[] rows = new int[]{0, 1, 0, -1, 0, 1, -1, 1, -1};

        for (int i = 0; i < 9; i++) {
            int newCol = cols[i];
            int newRow = rows[i];
            if (tailLocation.x + newRow == headLocation.x && tailLocation.y + newCol == headLocation.y) {
                return true;
            }
        }

        return false;
    }

    private static void addVisited() {
        for (Pair pair : tailVisited) {
            if (pair.x == tailLocation.x && pair.y == tailLocation.y) {
                return;
            }
        }
        tailVisited.add(tailLocation);
    }

    private static void moveTailNearHead() {
        if (headLocation.x == tailLocation.x) { // move vertically
            if (headLocation.y < tailLocation.y) {
                tailLocation = new Pair(tailLocation.x, tailLocation.y - 1);
            } else if (headLocation.y > tailLocation.y) {
                tailLocation = new Pair(tailLocation.x, tailLocation.y + 1);
            }
        } else if (headLocation.y == tailLocation.y) { // move horizontally
            if (headLocation.x < tailLocation.x) {
                tailLocation = new Pair(tailLocation.x - 1, tailLocation.y);
            } else {
                tailLocation = new Pair(tailLocation.x + 1, tailLocation.y);
            }
        } else { // move diagonally
            if (headLocation.x < tailLocation.x) {
                if (headLocation.y > tailLocation.y) {
                    tailLocation = new Pair(tailLocation.x - 1, tailLocation.y + 1);
                } else {
                    tailLocation = new Pair(tailLocation.x - 1, tailLocation.y - 1);
                }
            } else {
                if (headLocation.y < tailLocation.y) {
                    tailLocation = new Pair(tailLocation.x + 1, tailLocation.y - 1);
                } else {
                    tailLocation = new Pair(tailLocation.x + 1, tailLocation.y + 1);
                }
            }
        }
        addVisited();
    }

    /**
     * Reference:
     * (0, 0) (0, 1) (0, 2)
     * (1, 0) (1, 1) (1, 2)
     * (2, 0) (2, 1) (2, 2)
     */

    private static void move(int paces, String direction) {
        for (int i = 0; i < paces; i++) {
            switch (direction) {
                case "R":
                    headLocation = new Pair(headLocation.x, headLocation.y + 1);
                    break;
                case "L":
                    headLocation = new Pair(headLocation.x, headLocation.y - 1);
                    break;
                case "U":
                    headLocation = new Pair(headLocation.x - 1, headLocation.y);
                    break;
                case "D":
                    headLocation = new Pair(headLocation.x + 1, headLocation.y);
                    break;
                default:
                    break;
            }

            if (!isTailNearHead()) {
                moveTailNearHead();
            }
        }
    }

}