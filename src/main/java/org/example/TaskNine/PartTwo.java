package org.example.TaskNine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PartTwo {

    private static Pair headLocation = new Pair(0, 0);
    private static Pair tailLocation = new Pair(0, 0);
    private static int headIndex = 0;
    private static int tailIndex = 1;
    private static List<Pair> knotTailLocations = new ArrayList<>(Arrays.asList(
        new Pair(0, 0),

        new Pair(0, 0),
        new Pair(0, 0),
        new Pair(0, 0),

        new Pair(0, 0),
        new Pair(0, 0),
        new Pair(0, 0),

        new Pair(0, 0),
        new Pair(0, 0),

        new Pair(0, 0)
    ));
    private static List<Pair> tailVisited = new ArrayList<>(Arrays.asList(new Pair(0, 0)));

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
            //2536
            File myObj = new File("src/main/resources/task_nine/" + "input");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] s = data.split(" ");
                String direction = s[0];
                int paces = Integer.parseInt(s[1]);

                move(paces, direction);

//                switch (direction) {
//                    case "R":
//                        moveRight(paces);
//                        break;
//                    case "L":
//                        moveLeft(paces);
//                        break;
//                    case "U":
//                        moveUp(paces);
//                        break;
//                    case "D":
//                        moveDown(paces);
//                        break;
//                    default:
//                        break;
//                }
            }

            System.out.println(knotTailLocations);
            System.out.println(headLocation);
            System.out.println(tailLocation);

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
            // tailLocation should always correspond to tailIndex being 9
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
        // only add the tail of the rope
        if (tailIndex == 9) {
            addVisited();
        }
    }

    /**
     * Reference:
     * (0, 0) (0, 1) (0, 2)
     * (1, 0) (1, 1) (1, 2)
     * (2, 0) (2, 1) (2, 2)
     */

    private static void move(int paces, String direction) {
        for (int i = 0; i < paces; i++) {
            headIndex = 0;
            tailIndex = 1;
            for (int j = 0; j < knotTailLocations.size() - 1; j++) {
                headLocation = knotTailLocations.get(headIndex);
                tailLocation = knotTailLocations.get(tailIndex);

                if (j == 0) {

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

                    knotTailLocations.set(headIndex, headLocation);
                }

                if (!isTailNearHead()) {
                    moveTailNearHead();
                    knotTailLocations.set(tailIndex, tailLocation);
                }
                headIndex = headIndex + 1;
                tailIndex = tailIndex + 1;
            }
        }
        // reset head and tail
        headIndex = 0;
        tailIndex = 1;
        headLocation = knotTailLocations.get(headIndex);
        tailLocation = knotTailLocations.get(tailIndex);
    }

//    private static void moveRight(int paces) {
//        for (int i = 0; i < paces; i++) {
//            headIndex = 0;
//            tailIndex = 1;
//            for (int j = 0; j < knotTailLocations.size() - 1; j++) {
//                headLocation = knotTailLocations.get(headIndex);
//                tailLocation = knotTailLocations.get(tailIndex);
//
//                if (j == 0) {
//                    headLocation = new Pair(headLocation.x, headLocation.y + 1);
//                    knotTailLocations.set(headIndex, headLocation);
//                }
//
//                if (!isTailNearHead()) {
//                    moveTailNearHead();
//                    knotTailLocations.set(tailIndex, tailLocation);
//                }
//                headIndex = headIndex + 1;
//                tailIndex = tailIndex + 1;
//            }
//        }
//        // reset head and tail
//        headIndex = 0;
//        tailIndex = 1;
//        headLocation = knotTailLocations.get(headIndex);
//        tailLocation = knotTailLocations.get(tailIndex);
//    }
//
//    private static void moveUp(int paces) {
//        for (int i = 0; i < paces; i++) {
//            headIndex = 0;
//            tailIndex = 1;
//            for (int j = 0; j < knotTailLocations.size() - 1; j++) {
//                headLocation = knotTailLocations.get(headIndex);
//                tailLocation = knotTailLocations.get(tailIndex);
//
//                if (j == 0) {
//                    headLocation = new Pair(headLocation.x - 1, headLocation.y);
//                    knotTailLocations.set(headIndex, headLocation);
//                }
//
//                if (!isTailNearHead()) {
//                    moveTailNearHead();
//                    knotTailLocations.set(tailIndex, tailLocation);
//                }
//                headIndex = headIndex + 1;
//                tailIndex = tailIndex + 1;
//            }
//        }
//        // reset head and tail
//        headIndex = 0;
//        tailIndex = 1;
//        headLocation = knotTailLocations.get(headIndex);
//        tailLocation = knotTailLocations.get(tailIndex);
//    }
//
//    private static void moveLeft(int paces) {
//        for (int i = 0; i < paces; i++) {
//            headIndex = 0;
//            tailIndex = 1;
//            for (int j = 0; j < knotTailLocations.size() - 1; j++) {
//                headLocation = knotTailLocations.get(headIndex);
//                tailLocation = knotTailLocations.get(tailIndex);
//
//                if (j == 0) {
//                    headLocation = new Pair(headLocation.x, headLocation.y - 1);
//                    knotTailLocations.set(headIndex, headLocation);
//                }
//
//                if (!isTailNearHead()) {
//                    moveTailNearHead();
//                    knotTailLocations.set(tailIndex, tailLocation);
//                }
//                headIndex = headIndex + 1;
//                tailIndex = tailIndex + 1;
//            }
//        }
//        // reset head and tail
//        headIndex = 0;
//        tailIndex = 1;
//        headLocation = knotTailLocations.get(headIndex);
//        tailLocation = knotTailLocations.get(tailIndex);
//    }
//
//    private static void moveDown(int paces) {
//        for (int i = 0; i < paces; i++) {
//            headIndex = 0;
//            tailIndex = 1;
//            for (int j = 0; j < knotTailLocations.size() - 1; j++) {
//                headLocation = knotTailLocations.get(headIndex);
//                tailLocation = knotTailLocations.get(tailIndex);
//
//                if (j == 0) {
//                    headLocation = new Pair(headLocation.x + 1, headLocation.y);
//                    knotTailLocations.set(headIndex, headLocation);
//                }
//
//                if (!isTailNearHead()) {
//                    moveTailNearHead();
//                    knotTailLocations.set(tailIndex, tailLocation);
//                }
//                headIndex = headIndex + 1;
//                tailIndex = tailIndex + 1;
//            }
//        }
//        // reset head and tail
//        headIndex = 0;
//        tailIndex = 1;
//        headLocation = knotTailLocations.get(headIndex);
//        tailLocation = knotTailLocations.get(tailIndex);
//    }

}