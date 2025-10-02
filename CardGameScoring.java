/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      01 October 2025
  @version   1

    This program implements a card game scoring system.
   ****************************************/

import java.util.*;

class CardGameScoring {
    public static void main(String[] args) {
        // Implementation for card game menu
        System.out.println("Hand scores");
        System.out.println("-----------------------------------------------");
        System.out.println("pair = 2 | run = 5 | triple = 8 | fifteen = 3 |");
        System.out.println("-----------------------------------------------");

        // Initial score is 0
        int currentPoints = 0;
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input
        String userInput = "";

        while (!userInput.equals("FINISH")) {
            System.out.println("What is in your hand (pair, run, triple, fifteen or FINISH)?  ");
            userInput = scanner.nextLine(); // Get user input and convert to lowercase to standardise

            currentPoints += cardGameInput(userInput); // Update the score based on user input
            if (userInput.equals("FINISH")) {
                break; // Exit the loop if the user inputs "FINISH"
            }
            cardGameMenu(currentPoints); // Display the current score
        }
        
        System.out.printf("Total score = %d points.%n", currentPoints);
        scanner.close(); // Prevent resource leaks
        System.exit(0); // Exit the program

    }

    public static void cardGameMenu(int currentPoints) {
        System.out.printf("Total score so far =  %d points.%n", currentPoints);
    }

    public static int cardGameInput(String userInput) {
        // Implementation for card game input
       
        if (userInput.equals("pair")) {
            return 2;
        } else if (userInput.equals("run")) {
            return 5;
        } else if (userInput.equals("triple")) {
            return 8;
        } else if (userInput.equals("fifteen")) {
            return 3;
        } else if (userInput.equals("FINISH")) {
            return 0; // No points added when finishing
        }
        else {
            System.out.println("That is an invalid option. Try again.");
        }
        return 0; // Placeholder return value
    }
}