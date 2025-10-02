/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      26 September 2025
  @version   1

This program calculates a pool price varying on conditions such as employment status and age,
giving a discount on said conditions (e.g., old age pensioners and children receive a discount).
****************************************/

import java.util.Scanner;

public class SwimmingPoolCharges {


    public static void main(String[] args) {
        final int actualAge = getAge(); // Call the method to get the user's age
        final boolean employmentStatus = isUnemployed(); // Call the method to get the user's employment status
        priceChecker(actualAge, employmentStatus); // Call the method to calculate and display the pool price
    }

    public static int getAge() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How old are you?");
        int age = scanner.nextInt(); // Read the user's age being inputted, as an integer
        scanner.nextLine(); // Clear the newline so other methods run independently
        return age;
    }

    public static boolean isUnemployed() { // Checks if the user is unemployed
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you unemployed? Y/N: ");
        String response = scanner.nextLine().trim(); // trim spaces
        if (!response.equalsIgnoreCase("N") && !response.equalsIgnoreCase("Y")) {
            System.out.println("You must answer Y for yes or N for no. Please try again.");
            System.exit(0);
        }
        return response.equalsIgnoreCase("Y"); // return true if 'Y', false otherwise
    }


    public static void priceChecker(int actualAge, boolean employmentStatus) { // Calculates and displays the pool price based on age and employment status
        final int BASE_PRICE = 12; // Base pool price
        final int OAP_DISCOUNT = 2; // Pensioner discount
        final int UNEMPLOYMENT_DISCOUNT = 3; // Unemployment discount
        int totalPrice = BASE_PRICE; // Initialize total price with base price

        // Handle age conditions
        if (actualAge < 18) { 
            totalPrice /= 2; // Half price
        } 
        
        else if (actualAge >= 60) { 
            totalPrice -= OAP_DISCOUNT; // Pensioner discount (£2)
        }

        // Handle unemployment discount
        if (employmentStatus) {
            totalPrice -= UNEMPLOYMENT_DISCOUNT; // Unemployment discount (£3)
        }

        System.out.println("The swimming pool charge for you is: £" + totalPrice); // Display the total price
    }
}