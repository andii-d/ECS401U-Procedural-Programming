/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      20 October 2025
  @version   3

    Double or Nothing Quiz Mini Project

    The rules of this quiz state that you start off with £500.
    With each correct answer you input, you double the pot of money.
    With each incorrect answer, you halve the pot of money.

Each previous version has been level 1 and 2.

Current version is the final program.
   ****************************************/

// Import all packages
import java.util.*;
import java.io.*;

// Initialise ADT for quiz questions
class Question {
    Random random = new Random();
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
    public boolean isUsed() {return used;}
    public String getCategory() {return category;}
    public String getAnswer() {return answers.toLowerCase();}
    public String getQuestionText() {return questionText;}
    public void setUsed (boolean used) {this.used = used;}
}

// Initialise ADT for players
class Player {
    private int number;
    private int money;

    // Create a static method that creates each player
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
    public void winMoney() {setMoney(money * 2);}
    public void loseMoney() {setMoney(money/ 2);}
}

public class DoubleOrNothing_MiniProject {
    public static void main(String[] args) { // START main
        // Initialise program constants
        Scanner scanner = new Scanner(System.in);
        final int MAX_NUMBER_OF_PLAYERS = 4;
        final int MAX_NUMBER_OF_QUESTIONS = 5;
        final int STARTING_WINNINGS = 500;
        final String FILE_PATH = "/Users/andy/Desktop/Coding/Java files/MINI PROJECT/Categories/"; // Change for local dependency

        // Initialise variables beforehand, to be visible by scope
        int category_menu = 0;
        Question[] quiz_game_questions = null;
        Player[] quiz_game_players = createPlayers(MAX_NUMBER_OF_PLAYERS, STARTING_WINNINGS);

        // Call the method which prints the rules of the game
        gameRules();

        // START
        // Picking the category of the quiz or making questions
        //
        while (category_menu != 1) {
            System.out.println("To pick a category or make a new one:\n1) Choose categories \n2) New \nEnter: ");
            category_menu = Integer.parseInt(scanner.nextLine());
            if (category_menu == 1) {
                quiz_game_questions = gatherQuestions(showCategories(FILE_PATH, scanner), MAX_NUMBER_OF_QUESTIONS, FILE_PATH);
            } else if (category_menu == 2) {
                createQuestions(MAX_NUMBER_OF_QUESTIONS, scanner, FILE_PATH);
                System.out.println("Your category will now be shown in game!");
            } else {
                System.out.println("\nEnter a valid option 1 or 2.\n");
            }
        }
        // END

        quizGame(quiz_game_players, quiz_game_questions, scanner, MAX_NUMBER_OF_QUESTIONS); // Start the game
    } // END main

    public static void gameRules() { // Print out the rules of the game
        final String RULES =
                """
                        Welcome to the Double or Nothing Quiz Game!
                        
                        The rules of this quiz state that you start off with £500.
                        With each correct answer you input, you double the pot of money.
                        With each incorrect answer, you halve the pot of money.
                        """;
        System.out.println(RULES);
    }

    // START showCategories
    // Implement the questions - showing categories, retrieving them from the text files and creating them as instances to be used in game
    public static String showCategories(String FILE_PATH, Scanner scanner) { // Show all the categories in the 'Categories' folder, where all the categories of questions are
        File categories_folder = new File(FILE_PATH);
        String[] categories_list = categories_folder.list();
        String category_inputted;
        int display_number = 0; // To display the correct category number, in order to avoid ghost files in the below for loop

        if (categories_folder.exists() && categories_folder.isDirectory()) {
            for (int i = 0; i < categories_list.length; i++) {
                if (!categories_list[i].startsWith(".") && categories_list[i].endsWith(".txt")) { // Ignore ghost files and/or non .txt files
                    display_number++;
                    categories_list[i] = categories_list[i].replaceFirst("[.][^.]+$", "");
                    System.out.printf("%d) %s\n", display_number,  categories_list[i]);
                }
            }
        }
        System.out.println("Pick a category: ");
        category_inputted = scanner.nextLine();
        while (!categoryIsInArray(category_inputted, categories_list)) {
            System.out.println("Enter an existing category please.");
            category_inputted = scanner.nextLine();
        }
        return category_inputted;
    } // END showCategories

    // START categoryIsInArray
    public static boolean categoryIsInArray(String category_inputted, String[] list_of_categories) {
        // Linearly search the entire list of categories given, and return whether the category inputted exists or not
        for (String category : list_of_categories) {
            if (category.equals(category_inputted)) {
                return true;
            }
        }
        return false;
    } // END categoryIsInArray

    // START createPlayers
    public static Player[] createPlayers(int MAX_NUMBER_OF_PLAYERS, int STARTING_WINNINGS) {
        // Create the player instances
        Player[] quiz_game_players = new Player[MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            quiz_game_players[i] = Player.createPlayers(i + 1, STARTING_WINNINGS);
        }
        return quiz_game_players;
    } // END createPlayers

    // START createQuestions
    public static void createQuestions(int MAX_NUMBER_OF_QUESTIONS, Scanner scanner, String FILE_PATH) {
        String[] new_category_questions = new String[MAX_NUMBER_OF_QUESTIONS];
        String[] new_category_answers = new String[MAX_NUMBER_OF_QUESTIONS];
        File category_folder = new File(FILE_PATH);
        String[] categories_list = category_folder.list();

        for (int i = 0; i < categories_list.length; i++) {
            categories_list[i] = categories_list[i].replaceFirst("[.][^.]+$", "").toLowerCase();
        }

        System.out.println("Please enter the name of the category you would like to enter: ");
        String new_category = scanner.nextLine().toLowerCase();

        while (categoryIsInArray(new_category, categories_list)) {
            System.out.println("That category already exists. Please enter a new one.");
            new_category = scanner.nextLine().toLowerCase();
        }

        final File NEW_CATEGORY_TEXT_FILE = new File(String.format("%s/%s.txt", FILE_PATH, new_category));

        // Enter the list of questions and their respective answers
        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
            System.out.format("Enter question %d: ", i+1);
            new_category_questions[i] = scanner.nextLine();
            System.out.format("Please enter the answer of question %d: ", i+1);
            new_category_answers[i] = scanner.nextLine();
        }

        try { // Attempt to write to the given file path of the new categorised quiz questions, catching and printing any errors
            BufferedWriter writer = new BufferedWriter(new FileWriter(NEW_CATEGORY_TEXT_FILE));
            for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
                System.out.println(new_category_questions[i] + ":" + new_category_answers[i]);
                writer.write(String.format("%s:%s", new_category_questions[i], new_category_answers[i]));
                writer.newLine();
            }
            writer.close(); // CLose the BufferedWriter in order to save everything
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    } // END createQuestions


    // START gatherQuestions
    public static Question[] gatherQuestions(String categoryInputted, int MAX_NUMBER_OF_QUESTIONS, String FILE_PATH) {
        final File QUESTIONS_FILE = new File(String.format("%s%s.txt", FILE_PATH, categoryInputted));
        String[] texts = new String[MAX_NUMBER_OF_QUESTIONS];
        String[] answers = new String[MAX_NUMBER_OF_QUESTIONS];
        Question[] questionsArray = new Question[MAX_NUMBER_OF_QUESTIONS];

        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTIONS_FILE))) {
            for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
                String temp = reader.readLine(); // Create a temporary placeholder to read each line of the categorised questions
                texts[i] = temp.split(":")[0]; // In the files, question texts are to the left of the colon
                answers[i] = temp.split(":")[1]; // Whereas the answers are to the right of it
                questionsArray[i] = Question.createQuestions(answers[i], categoryInputted, texts[i]); // Add the text, its answer and the category to the current question instance
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionsArray;
    } // END gatherQuestions

    // START isQuestionCorrect
    public static boolean isQuestionCorrect(String question, String answer) {
        return (question.equals(answer)); // Return true if the question's answer and the user's answer match
    } // END isQuestionCorrect

    // START randomiseNumber
    public static int randomiseNumber(int max, Question[] questions) {
        Random random = new Random();
        int random_q_num = random.nextInt(max);

        for (Question question : questions) { // This prevents infinite looping by linearly searching the entire list of questions
            if (!question.isUsed()) { // If there is still a question that's unused, generate a random question number
                while (questions[random_q_num].isUsed()) {
                    random_q_num =  random.nextInt(max);
                }
                return random_q_num;
            }
        }
        return (max+1); // Return unimportant placeholder value which will be replaced
    } // END randomiseNumber

    // START quizGame
    public static void quizGame(Player[] players, Question[] quiz_questions, Scanner scanner, int MAX_NUMBER_OF_QUESTIONS) {
        System.out.printf("You are playing the %s category! Good luck everyone!\n", quiz_questions[0].getCategory());

        String current_input;
        int num_answered = 0;
        int current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_questions);

        for (Player player : players) {
            System.out.printf("Current PLAYER number: %d\n", player.getNumber()); // Let each player know when their turn is
            while (num_answered <= 5 && current_question_number != 6) {
                System.out.println(quiz_questions[current_question_number].getQuestionText());
                current_input = scanner.nextLine().toLowerCase(); // Removes case-sensitivity

                // If the player wants to pass, halve their money and go to the next question
                if (isQuestionCorrect(current_input, "pass")) {
                    num_answered++; // Increment
                    player.loseMoney();
                    System.out.printf("Question passed! Player %d now has £%d.\n", player.getNumber(), player.getMoney());
                    quiz_questions[current_question_number].setUsed(true);
                    current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_questions); // Go to the next question
                }
                else if (isQuestionCorrect(current_input, quiz_questions[current_question_number].getAnswer())) {
                    num_answered++; // Increment
                    player.winMoney();
                    System.out.printf("Correct! Player number %d now has £%d. \n", player.getNumber(), player.getMoney());
                    quiz_questions[current_question_number].setUsed(true);
                    current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_questions);
                }
                // If the answer is incorrect, halve the player's money
                else {
                    player.loseMoney();
                    System.out.printf("Incorrect! Player %d now has £%d.\n", player.getNumber(), player.getMoney());
                }
            }
            // Nested for loop to reset the list of questions to be reused for the next player
            for (Question question : quiz_questions) {
                question.setUsed(false);
            }
            // Reset parameters for the while loop
            num_answered = 0;
            // (Will not return 6 because all questions are reset to false)
            current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_questions);
        }
        // Print game results at the end
        for (Player player : players) {
            System.out.printf("Player %d has finished with £%d. \n", player.getNumber(), player.getMoney());
        }

    } // END quizGame

}
