/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      12 October 2025
  @version   3

    Double or Nothing Quiz Mini Project

    The rules of this quiz state that you start off with £500.
    With each correct answer you input, you double the pot of money.
    With each incorrect answer, you halve the pot of money.
   ****************************************/

// Level 4 and 5:
//      Let 4 people to answer the question in turn.
//      Let each person get the question right, or choose to skip - controlled by an inner loop
//      Each question and their correct answer

// Import all packages
import java.util.*;
import java.io.*;

// Initialise ADT for quiz questions
class Question {
    private String answers;
    private String category;
    private String questionText;
    private boolean used;

    // Create a static method to build a question with attributes
    public static Question createQuestions(String answers, String category, String questionText) {
        Question question = new Question();
        question.answers = answers;
        question.category = category;
        question.questionText = questionText;
        question.used = false; // Set it to false because all questions are initially unused
        return question;
    }

    // Modifier and accessor methods for the attributes of each question
    public String getAnswer() {return answers;}
    public String getCategory() {return category;}
    public String getQuestionText() {return questionText;}
    public boolean isUsed() {return used;}
    public void setUsed (boolean used) {this.used = true;}
}

// Initialise ADT for players
class Player {
    private int number;
    private int money;

    //
    // Create a static method that creates each player
    //
    public static Player createPlayers(int playerNum, int maxMoney) {
        Player player = new Player();
        player.number = playerNum; // Set the number of the player to allocate each one their own money
        player.money = maxMoney; // Set the player their level of money
        return player; // Return the instance of the player
    }

    //
    // Modifier and accessor methods that access attributes of a player
    //
    public int getNumber() {return number;}
    public int getMoney() {return money;}
    public void setMoney(int newMoney) {money = newMoney;}
}

public class DoubleOrNothing_MiniProject {
    public static void main(String[] args) { // START main
        // Initialise program constants
        Scanner scanner = new Scanner(System.in);
        final int MAX_NUMBER_OF_PLAYERS = 4;
        final int MAX_NUMBER_OF_QUESTIONS = 5;
        final int STARTING_WINNINGS = 500;
        final String FILE_PATH = "/Users/andy/Desktop/Coding/Java files/MINI PROJECT/Categories/";

        // Create variables beforehand, to be visible by scope
        String category_menu;
        Question[] quiz_game_questions = null;

        // Call the method which prints the rules of the game
        gameRules();

        // START
        // Picking the category of the quiz
        //
        System.out.println("To pick a category or make a new one:\n1) Choose categories \n2) New \nEnter: ");


        do { // Whilst the options do not align with 1 or 2 entered, keep asking the user to enter either option correctly
            category_menu = scanner.nextLine();
            if (category_menu.equals("1")) {
                quiz_game_questions = gatherQuestions(showCategories(FILE_PATH, scanner), MAX_NUMBER_OF_QUESTIONS);
            }
            else if (category_menu.equals("2")){
                createQuestions(MAX_NUMBER_OF_QUESTIONS, scanner);
            }
            else {
                System.out.println("Enter a valid option 1 or 2.");
            }
        } while (!category_menu.equals("1") && !category_menu.equals("2")); // Condition keeps performing above code until 1 or 2 is entered
        // END


        // Create the players for the game
        Player[] quiz_game_players = new Player[MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            quiz_game_players[i] = Player.createPlayers(i + 1, STARTING_WINNINGS);
        }

        quizGame(quiz_game_players, quiz_game_questions);
    } // END main

    public static void gameRules() {
        final String RULES =
                """
                        Welcome to the Double or Nothing Quiz Game!
                        
                        The rules of this quiz state that you start off with £500.
                        With each correct answer you input, you double the pot of money.
                        With each incorrect answer, you halve the pot of money.
                        """;
        System.out.println(RULES); // Print out the rules of the game
    }

    // START
    // Implement the questions - showing categories, retrieving them from the text files and creating them as instances to be used in game
    //
    public static String showCategories(String FILE_PATH, Scanner scanner) { // Show all the categories in the 'Categories' folder, where all the categories of questions are
        File categoriesFolder = new File(FILE_PATH);
        String[] categoriesList = categoriesFolder.list();
        if (categoriesFolder.exists() && categoriesFolder.isDirectory()) {
            for (String category : categoriesList) { // For each loop of each category in file list of categories
                System.out.println(category);
            }
        }
        String categoryInputted =  scanner.nextLine();
        return categoryInputted;
    } // END showCategories

    public static void createQuestions(int MAX_NUMBER_OF_QUESTIONS, Scanner scanner) {
        String[] new_category_questions = new String[MAX_NUMBER_OF_QUESTIONS];
        String[] new_category_answers = new String[MAX_NUMBER_OF_QUESTIONS];

        System.out.println("Please enter the name of the category you would like to enter: ");
        String new_category = scanner.nextLine();

        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
            System.out.format("Enter question %d: ", i);
            new_category_questions[i] = scanner.nextLine();
            System.out.format("Please enter the answer of question %d: ", i);
            new_category_answers[i] = scanner.nextLine();
        }


    } // END createCategories

    public static Question[] gatherQuestions(String categoryInputted, int MAX_NUMBER_OF_QUESTIONS) {
        final File QUESTIONS_FILE = new File(String.format("/Users/andy/Desktop/Coding/Java files/MINI PROJECT/Categories/%s.txt", categoryInputted));
        String[] texts = new String[5];
        String[] answers = new String[5];
        Question[] questionsArray = new Question[5]; // Create a list of question records

        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTIONS_FILE))) {
            for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
                String temp = reader.readLine(); // Create a temporary placeholder to read each line of the categorised questions
                texts[i] = temp.split(":")[0]; // In the files, question texts are to the left of the colon
                answers[i] = temp.split(":")[1]; // Whereas answers are to the right of it
                questionsArray[i] = Question.createQuestions(answers[i], categoryInputted, texts[i]); // Add the text, its answer and the category to the current question record
            }
            // Remove the file extension of the file to retrieve the category
            System.out.println(categoryInputted.replaceFirst("[.][^.]+$", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionsArray;
    }

    public static void quizGame(Player[] players, Question[] quiz_questions) {
        System.out.println("Do nothing");
    }

    public static boolean isQuestionCorrect(String question, String answer) {
        return (question.equals(answer)); // Return true if the question's answer and the user's answer match
    } // Else return false
}
