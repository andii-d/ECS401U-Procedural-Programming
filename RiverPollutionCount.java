/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      28 September 2025
  @version   1

    This program calculates the water quality of a river based on user input of five water samples.
   ****************************************/

import java.util.Scanner;

class RiverPollutionCount {
    public static void main(String[] args) {
        String waterQualityToday = waterQualityChecker(pollutionQualityReport()); // Gathers the report of the worst quality and stores the result
        System.out.println(waterQualityToday); // Prints the water quality result
    }

    public static int pollutionQualityReport() {
        int worstQuality = 0; // Initializes the worst quality variable
        int averageQuality = 0; // Initializes the average quality variable

        for (int i = 0; i < 5; i++) { // Loops five times to gather five water samples
            Scanner scanner = new Scanner(System.in);
            System.out.print("Water sample " + (i+1) + ": Give the water quality measurement in CFU/100mL? ");
            int currQuality = scanner.nextInt();
            if (currQuality < 0) {
                System.out.println("That sample is invalid. Start again.");
                System.exit(0);
            }
            averageQuality += currQuality;
            worstQuality = Math.max(worstQuality, currQuality); // Keeps track of the worst quality found (highest number entered so far)
        }
        averageQuality /= 5; // Calculates the average quality
        System.out.println("Average water quality today: " + averageQuality + " CFU/100mL"); // State the average quality
        return worstQuality; // Returns the worst quality found
    }

    public static String waterQualityChecker(final int worstQuality) { // Use final in the parameter to prevent modification of the argument
        System.out.println("Worst water quality today: " + worstQuality + " CFU/100mL"); // Prints the worst quality found

        // Determines the water quality based on the worst quality measurement
        if (worstQuality <= 150) {
            return "The water quality is GOOD today.";
        }
        else if (worstQuality <= 350) {
            return "The water quality is SUFFICIENT today.";
        }
        else {
            return "The water quality is POOR today.";
        }
    }

}