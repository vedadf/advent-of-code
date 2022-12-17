package org.example.TaskTwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PartOne {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_two/input");
            Scanner myReader = new Scanner(myObj);

            Map<String, String> opponentMap = new HashMap<>();
            opponentMap.put("ROCK", "A");
            opponentMap.put("PAPER", "B");
            opponentMap.put("SCISSORS", "C");

            Map<String, String> playerMap = new HashMap<>();
            playerMap.put("ROCK", "X");
            playerMap.put("PAPER", "Y");
            playerMap.put("SCISSORS", "Z");

            Map<String, Integer> playerPoints = new HashMap<>();
            playerPoints.put("X", 1); // rock
            playerPoints.put("Y", 2); // paper
            playerPoints.put("Z", 3); // scissors

            //opponent:
            // A - rock
            // B - paper
            // C - scissors

            int totalPoints = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                List<String> list = Arrays.stream(data.split(" ")).collect(Collectors.toList());
                String opponent = list.get(0);
                String player = list.get(1);

                int choicePoints = playerPoints.get(player);

                int resultPoints = 0;
                if (player.equals(playerMap.get("ROCK"))) {
                    if (opponent.equals(opponentMap.get("ROCK"))) {
                        resultPoints = 3; // draw
                    } else if (opponent.equals(opponentMap.get("SCISSORS"))) {
                        resultPoints = 6; // win
                    }
                } else if (player.equals(playerMap.get("PAPER"))) {
                    if (opponent.equals(opponentMap.get("PAPER"))) {
                        resultPoints = 3; // draw
                    } else if (opponent.equals(opponentMap.get("ROCK"))) {
                        resultPoints = 6; // win
                    }
                } else if (player.equals(playerMap.get("SCISSORS"))) {
                    if (opponent.equals(opponentMap.get("SCISSORS"))) {
                        resultPoints = 3; // draw
                    } else if (opponent.equals(opponentMap.get("PAPER"))) {
                        resultPoints = 6; // win
                    }
                }

                int totalRoundPoints = choicePoints + resultPoints;
                totalPoints += totalRoundPoints;

            }

            System.out.println("TOTAL: " + totalPoints);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}