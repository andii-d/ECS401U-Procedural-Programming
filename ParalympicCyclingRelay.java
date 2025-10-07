/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      06 October 2025
  @version   1

    Write a program that checks the
    legality of relay teams given the country 
    and the disability rating of the entrant for each relay leg.
   ****************************************/

import java.util.*; // Import all packages

class ParalympicRelayTeam { // Create a new record for the relay team's attributes, allowing accessor methods to be used
    String countryName;
    int maxPointsPerRace;
    int[] disabilityRatings = new int[3];
}

public class ParalympicCyclingRelay {
    public static void main(String[] args) {
        // Implementation for Paralympic cycling relay menu
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the point limit for the relay? ");
        int givenPointLimit = scanner.nextInt(); // Stores the user's input of the next integer
        scanner.nextLine(); // Prevents the next input from being a string input and not an integer input by consuming the \n
        while (givenPointLimit < 3) { // Keep re-entering the point limit if it is below 3, otherwise exit the loop
            System.out.println("That is not a valid input. Enter a value >=3");
            System.out.println("What is the point limit for the relay? ");
            givenPointLimit = scanner.nextInt(); // Stores the user's input of the next integer
            scanner.nextLine(); // Prevents the next input from being a string input and not an integer input by consuming the \n
        }

        System.out.println("What is the name of the country? ");
        String givenCountryName = scanner.nextLine(); // Stores the user's input of the country name 
        while (!givenCountryName.matches("[A-Za-z]+")){ // Use the regular expresison of any alphabetical character one or more times for the user input of a country
            System.out.println("That is not a valid input. Enter a name.");
            givenCountryName = scanner.nextLine(); // Stores the user's input of the country name 
        }

        ParalympicRelayTeam team = createTeam(givenCountryName, givenPointLimit);

        int[] givenRatings = new int[3]; // Initalise a list of size 3 for the ratings of the 3 players, hence 'given'

        for (int i = 0; i < 3; i++) { // Use a for loop to actually set the rating for each cyclist in the team
            System.out.println(String.format("What is the disability rating of cyclyst %d? D ", i+1)); // Use i+1 because i is intialised at 0
            int currRating = scanner.nextInt();

            while (currRating < 1 || currRating > 5) { 
                System.out.println("That is not a valid input. Enter a value >=1 & <=5");
                System.out.println(String.format("What is the disability rating of cyclyst %d? D ", i+1)); // Use i+1 because i is intialised at 0
                currRating = scanner.nextInt();
            }
            givenRatings[i] = currRating; // Takes the next user input of an integer
        }

        scanner.close(); // Prevent resource leaks

        setTeamRatings(team, givenRatings[0], givenRatings[1], givenRatings[2]); // Sets the team ratings using an accessor method and inserts the user-inputted ratings as arguments

        final String finalRating = printTeamRatings(team); // Keep the final rating given in a final string variable so it cannot be modified

        System.out.println(finalRating); // Ultimately, print the final rating
    }    

    public static ParalympicRelayTeam createTeam(String country, int maxPoints) { // Create a new method that accesses the ParalympicRelayTeam method
        ParalympicRelayTeam team = new ParalympicRelayTeam(); // Create a new team object of the record
        team.countryName = country;
        team.maxPointsPerRace = maxPoints;
    
        for (int i = 0; i < 3; i++) {
            team.disabilityRatings[i] = maxPoints; // Placeholder values for the team's disability rating per cyclist
        }    
        return team; // Return the entire object 
    }

    public static void setTeamRatings(ParalympicRelayTeam team, int rating1, int rating2, int rating3) { // Set the team's ratings via an accessor method
        if (rating1 < 1 || rating1 > 5 || rating2 < 1 || rating2 > 5 || rating3 < 1 || rating3 > 5) {
        }
        else { 
            team.disabilityRatings[0] = rating1;
            team.disabilityRatings[1] = rating2;
            team.disabilityRatings[2] = rating3;
        }
    }

    public static String printTeamRatings(ParalympicRelayTeam team) { // Final accessor method that just collates the disability ratings and the legality of the team (depending on their max points and ratings)
        final int DISABILITY_RATINGS_TOTAL =  team.disabilityRatings[0] +  team.disabilityRatings[1] +  team.disabilityRatings[2];
        final boolean LEGAL_RATING = DISABILITY_RATINGS_TOTAL <= team.maxPointsPerRace; // The rating of the race is legal is the total of the disability ratings is less than the max points of the race
        
        final String IS_LEGAL = LEGAL_RATING ? "legal" : "NOT legal"; // Ternary operator in order to decide whether the rating for the team inputted is legal
        // ? comes after true, : comes after false

        return (String.format("%s has D%d, D%d and D%d cyclists. That is %s for a %d point race.", team.countryName, team.disabilityRatings[0], team.disabilityRatings[1], team.disabilityRatings[2], IS_LEGAL, team.maxPointsPerRace));
    }
}
