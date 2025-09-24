import java.util.Scanner;

public class FitnessApp {
    public static void main(String[] args) {
        displayFitnessResults(); // Call the method to display the fitness results
    }

    public static int actualAgeInput() {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input recording their actual age
        System.out.println("Enter your actual age: "); // Ask the user to input their actual age
        int actualAge = scanner.nextInt(); // Record said input as an integer
        return actualAge;
    }

    public static double exerciseInput1() {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input recording their score for exercise 1
        System.out.println("Enter your score for exercise 1 (out of 40): "); // Ask the user to input their score for exercise 1
        double exerciseScore1 = scanner.nextDouble(); // Record said input as an integer
        if (exerciseScore1 < 0.0 || exerciseScore1 > 40.0) { // Check if the input is valid (between 0 and 40) using an or condition (||)
            System.out.println("Invalid input. Please enter a score between 0 and 40.");
            exerciseInput1(); // Recursively call the input method until a valid score is entered    
        }
        return exerciseScore1;
    } 

    public static double exerciseInput2() {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input recording their score for exercise 2
        System.out.println("Enter your score for exercise 2 (out of 40): "); // Ask the user to input their score for exercise 2
        double exerciseScore2 = scanner.nextDouble(); // Record said input as an integer
        if (exerciseScore2 < 0.0 || exerciseScore2 > 40.0) { // Check if the input is valid (between 0 and 40) using an or condition (||)
            System.out.println("Invalid input. Please enter a score between 0 and 40.");
            exerciseInput2(); // Recursively call the input method until a valid score is entered    
        }
        scanner.close(); // Close the scanner to prevent resource leaks (exerciseInput2 is the last method to use the scanner)
        return exerciseScore2;
    }

    public static int exerciseAvgScore(int exerciseScore1, int exersciseScore2) {
        double avgFitnessScore = (exerciseScore1 + exersciseScore2) / 2; // Calculate the mean score from the two exercises above
        int roundedAvgFitnessScore = (int) Math.floor(avgFitnessScore); // Multiply by 1.6, add 10 to the result, and round up the score to the nearest integer
        return roundedAvgFitnessScore;
    }

    public static int PCFitAge(int avgScore) {
        final double modifier = 1.6; // 
        int fitnessAge = (int) Math.ceil((avgScore * modifier) + 10);  // Calculate the fitness age using the formula provided
        return fitnessAge;  
    }

    public static int exerciseAgeDifference(int actualAge, int fitnessAge) {
        int ageDifference = actualAge - fitnessAge; // Calculate the age difference between the fitness and actual age
        return ageDifference;
    }

    public static void displayFitnessResults() {
        // Call all the above methods and store their return values in variables
        int actualAge = actualAgeInput();
        double exerciseScore1 = exerciseInput1();
        double exerciseScore2 = exerciseInput2();
        int avgScore = exerciseAvgScore((int) exerciseScore1, (int) exerciseScore2);
        int fitnessAge = PCFitAge(avgScore);
        int ageDifference = exerciseAgeDifference(actualAge, fitnessAge);
        System.out.println("Your average score is " + avgScore + "."); // Display the average score to the user
        System.out.println("Your fitness age is " + fitnessAge + "."); // Display the fitness age to the user
        System.out.println("You are " + ageDifference + " years younger than your actual age."); // Display the age difference to the user
    }

}