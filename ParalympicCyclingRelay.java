/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      02 October 2025
  @version   1

    This program implements a card game scoring system.
   ****************************************/

import java.util.*;

class ParalympicRelayTeam {
    String countryName;
    int maxPointsPerRace;
    int[] disabilityRatings = new int[3];
}

public class ParalympicCyclingRelay {
    public static void main(String[] args) {
        // Implementation for Paralympic cycling relay menu
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the point limit for the relay? ");
        int givenPointLimit = scanner.nextInt();
        scanner.nextLine();

        System.out.println("What is the name of the country? ");
        String givenCountryName = scanner.nextLine();

        ParalympicRelayTeam team = createTeam(givenCountryName, givenPointLimit);

        int[] givenRatings = new int[3];

        for (int i = 0; i < 3; i++) {
            System.out.println(String.format("What is the disability rating of cyclyst %d? D ", i+1));
            givenRatings[i] = scanner.nextInt();
        }

        setTeamRatings(team, givenPointLimit, givenPointLimit, givenPointLimit);

        String finalRating = printTeamRatings(team);

        System.out.println(finalRating);

    }    

    public static ParalympicRelayTeam createTeam(String country, int maxPoints) {
        ParalympicRelayTeam team = new ParalympicRelayTeam();
        team.countryName = country;
        team.maxPointsPerRace = maxPoints;
    
        for (int i = 0; i < 3; i++) {
            team.disabilityRatings[i] = maxPoints;
        }    
        return team;
    }

    public static void setTeamRatings(ParalympicRelayTeam team, int rating1, int rating2, int rating3) {
        if (rating1 < 1 || rating1 > 5 || rating2 < 1 || rating2 > 5 || rating3 < 1 || rating3 > 5) {
            System.out.println("Invalid ratings were entered!");
        }
        else { 
            team.disabilityRatings[0] = rating1;
            team.disabilityRatings[1] = rating2;
            team.disabilityRatings[2] = rating3;
        }
    }

    public static String printTeamRatings(ParalympicRelayTeam team) {
        int disabilityRatingsTotal =  team.disabilityRatings[0] +  team.disabilityRatings[1] +  team.disabilityRatings[2];
        boolean legalRating = disabilityRatingsTotal <= team.maxPointsPerRace;
        
        String isLegal = legalRating ? "legal" : "NOT legal"; // Ternary operator in order to decide whether the rating for the team inputted is legal
        // ? comes after true, : comes after false

        return (String.format("%s has D%d, D%d and D%d cyclists. That is %s for a %d point race.", team.countryName, team.disabilityRatings[0], team.disabilityRatings[1], team.disabilityRatings[2], isLegal, team.maxPointsPerRace));
    }



}

