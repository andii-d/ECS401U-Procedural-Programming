/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      01 October 2025
  @version   1

    This program calculates the average grade based of a series of grade inputs for the whole module.
   ****************************************/

import java.util.*;

public class ModuleGradeCalculation {
    public static void main(String[] args) {
        final String StudentGrade = moduleGradeInput(); // Call the moduleGradeInput method to get the student's overall grade and finalise the variable to prevent future modification
        System.out.println("Your portfolio earned a " + StudentGrade + " grade overall.");
    }

    public static String moduleGradeInput() {
        // Implementation for module grade input
        final String[] GRADES = {"A+", "A", "B", "C", "D", "F", "G"}; // Finalise the set of grades available
        int[] studentsGrades = new int[GRADES.length]; // Create an array to store the number of grades the student has
        int[] betterGrades = new int [GRADES.length]; // Create an array to store the number of better/equal grades the student has

        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input

        for (int i = 0; i < GRADES.length; i++) {
            System.out.println("How many " + GRADES[i] + " grades have you? ");
            studentsGrades[i] = scanner.nextInt(); // Store the number of each grade the student has
            for (int j = i; j < GRADES.length; j++) { // For each grade inputted, add that number to all the better or equal grades
                betterGrades[j] += studentsGrades[i];
            }
        }

        scanner.close(); // Prevent resource leaks

        String finalGrade = "Q"; // Initalise the final grade as Q (will be returned if no other condition is met)

        // Determine final grade based on the counts of each grade
        // The following conditios check for the final grade based on the exact number of A+ and A grades, hence the use of studentsGrades array
        if (studentsGrades[0] == 6 && studentsGrades[1] == 2) { // A*, must have 6 A+ and 2 As
            finalGrade = "A*";
        }
        else if (studentsGrades[0] == 5 && studentsGrades[1] == 3) { // A++, must have 5 A+ and 3 As
            finalGrade = "A++";
        }
        else if (studentsGrades[0] == 4 && studentsGrades[1] == 4) { // A+, must have 4 A+ and 4 As
            finalGrade = "A+";
        }
        // The following conditions check for the final grade based on the number of better or equal grades, hence the use of betterGrades array
        else if (betterGrades[1] >= 6 && betterGrades[2] == 8) { // A, must have 6 As or better and 8 Bs or better
            finalGrade = "A";
        }
        else if (betterGrades[2] >= 6 && betterGrades[3] == 8) { // B, must have 6 Bs or better and 8 Cs or better
            finalGrade = "B";
        }
        else if (betterGrades[3] >= 6 && betterGrades[4] == 8) { // C, must have 6 Cs or better and 8 Ds or better
            finalGrade = "C";
        }
        else if (betterGrades[4] >= 6 && betterGrades[5] == 8) { // D, must have 6 Ds or better and 8 Fs or better
            finalGrade = "D";
        }
        else if (betterGrades[5] >= 6 && betterGrades[6] == 8) { // F, must have 6 Ds or better and 8 Gs or better
            finalGrade = "F";
        }
        else if (betterGrades[6] == 8) { // G, must have 8 Gs
            finalGrade = "G";
        }
        
        return finalGrade; // Return the final grade
    }
}