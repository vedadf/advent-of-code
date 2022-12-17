package org.example.TaskTwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PartTwo {

    public static void main(String[] args) {
        try {
            File myObj = new File("src/main/resources/task_two/input");
            Scanner myReader = new Scanner(myObj);

            Map<String, String> opponentMap = new HashMap<>();
            opponentMap.put("ROCK", "A");
            opponentMap.put("PAPER", "B");
            opponentMap.put("SCISSORS", "C");

            Map<String, String> playerMap = new HashMap<>();
            playerMap.put("LOSE", "X");
            playerMap.put("DRAW", "Y");
            playerMap.put("WIN", "Z");

            Map<String, Integer> playerPoints = new HashMap<>();
            playerPoints.put("X", 0); // LOSE
            playerPoints.put("Y", 3); // DRAW
            playerPoints.put("Z", 6); // WIN

            Map<String, Integer> playerChoicePoints = new HashMap<>();
            playerChoicePoints.put("ROCK", 1);
            playerChoicePoints.put("PAPER", 2);
            playerChoicePoints.put("SCISSORS", 3);

            int totalPoints = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                List<String> list = Arrays.stream(data.split(" ")).collect(Collectors.toList());
                String opponent = list.get(0);
                String player = list.get(1);

                int roundOutcomePoints = playerPoints.get(player);

                int resultPoints = 0;
                if (player.equals(playerMap.get("LOSE"))) {
                    if (opponent.equals(opponentMap.get("ROCK"))) {
                        resultPoints = playerChoicePoints.get("SCISSORS");
                    } else if (opponent.equals(opponentMap.get("SCISSORS"))) {
                        resultPoints = playerChoicePoints.get("PAPER");
                    } else if (opponent.equals(opponentMap.get("PAPER"))) {
                        resultPoints = playerChoicePoints.get("ROCK");
                    }
                } else if (player.equals(playerMap.get("DRAW"))) {
                    if (opponent.equals(opponentMap.get("PAPER"))) {
                        resultPoints = playerChoicePoints.get("PAPER");
                    } else if (opponent.equals(opponentMap.get("ROCK"))) {
                        resultPoints = playerChoicePoints.get("ROCK");
                    } else if (opponent.equals(opponentMap.get("SCISSORS"))) {
                        resultPoints = playerChoicePoints.get("SCISSORS");
                    }
                } else if (player.equals(playerMap.get("WIN"))) {
                    if (opponent.equals(opponentMap.get("SCISSORS"))) {
                        resultPoints = playerChoicePoints.get("ROCK");
                    } else if (opponent.equals(opponentMap.get("PAPER"))) {
                        resultPoints = playerChoicePoints.get("SCISSORS");
                    } else if (opponent.equals(opponentMap.get("ROCK"))) {
                        resultPoints = playerChoicePoints.get("PAPER");
                    }
                }

                int totalRoundPoints = roundOutcomePoints + resultPoints;
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