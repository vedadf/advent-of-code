package org.example.TaskSix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PartTwo {

    private static final int WINDOW_LENGTH = 14;

    private static boolean listHasAllUniqueChars(List<Character> list) {
        Set<Character> set = new HashSet<>(list);
        return set.size() == list.size();
    }

    private static int startOfPacketMarker(String data) {
        int res = 0;
        List<Character> windowList = new ArrayList<>();
        for (int i = 0; i < WINDOW_LENGTH; i++) {
            windowList.add(data.charAt(i));
        }

        for (int i = 1; i < data.length(); i++) {
            if (listHasAllUniqueChars(windowList)) {
                res = i + WINDOW_LENGTH - 1;
                break;
            }

            windowList = new ArrayList<>();
            for (int j = i; j < i + WINDOW_LENGTH; j++) {
                windowList.add(data.charAt(j));
            }

        }
        return res;
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_six/input");
            Scanner myReader = new Scanner(myObj);

            int res = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.length() < WINDOW_LENGTH) {
                    return;
                }

                res = startOfPacketMarker(data);

            }

            System.out.println(res);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}