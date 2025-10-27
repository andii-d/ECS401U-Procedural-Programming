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
    String answers;
    String category;
    String questionText;
    boolean used;
}

// Initialise ADT for players
class Player {
    int number;
    int money;
}

public class DoubleOrNothing_MiniProject {
    public static void main(String[] args) throws IOException { // START main
        // Initialise program constants
        Scanner scanner = new Scanner(System.in);
        final int MAX_NUMBER_OF_PLAYERS = 4;
        final int MAX_NUMBER_OF_QUESTIONS = 5;
        final int STARTING_WINNINGS = 500;
        final String FILE_PATH = "/Users/andy/Desktop/Coding/Java files/MINI PROJECT/Categories/"; // Change for local dependency

        // Initialise variables before starting the game
        Question[] quiz_game_questions = null;
        Player[] quiz_game_players = createPlayers(MAX_NUMBER_OF_PLAYERS, STARTING_WINNINGS);
        String current_input;
        int category_menu = 0;
        int num_answered = 0;

        // Call the method which prints the rules of the game
        gameRules();

        // START
        // Picking the category of the quiz or making questions
        while (category_menu != 1) {
            System.out.println("To pick a category or make a new one:\n1) Choose categories \n2) New \nEnter: ");
            category_menu = Integer.parseInt(scanner.nextLine());
            if (category_menu == 1) {
                quiz_game_questions = gatherQuestions(showCategories(FILE_PATH, scanner), MAX_NUMBER_OF_QUESTIONS, FILE_PATH);
            } else if (category_menu == 2) {
                createNewQuestions(MAX_NUMBER_OF_QUESTIONS, scanner, FILE_PATH);
                System.out.println("Your category will now be shown in game!");
            } else {
                System.out.println("\nEnter a valid option 1 or 2.\n");
            }
        }
        // END

        System.out.printf("You are playing the %s category! Good luck everyone!\n", getCategory(quiz_game_questions[0]).toUpperCase());
        int current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_game_questions);


        // MAIN GAME LOGIC
        for (Player player : quiz_game_players) { // For each player in the list of players
            System.out.printf("Current PLAYER number: %d\n", getNumber(player)); // Let each player know when their turn is
            while (num_answered <= 5 && current_question_number != 6) {
                System.out.println(getQuestionText(quiz_game_questions[current_question_number])); // Print the question
                current_input = scanner.nextLine().toLowerCase(); // Removes case-sensitivity

                // If the player wants to pass, halve their money and go to the next question
                if (isQuestionCorrect(current_input, "pass")) {
                    num_answered++; // Increment
                    loseMoney(player);
                    System.out.printf("Question passed! Player %d now has £%d.\n", getNumber(player), getMoney(player));
                    setUsed(true, quiz_game_questions[current_question_number]);
                    current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_game_questions); // Go to the next question
                }
                else if (isQuestionCorrect(current_input, getAnswer(quiz_game_questions[current_question_number]))) {
                    num_answered++; // Increment
                    winMoney(player);
                    System.out.printf("Correct! Player number %d now has £%d. \n", getNumber(player), getMoney(player));
                    setUsed(true, quiz_game_questions[current_question_number]);
                    current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_game_questions);
                }
                // If the answer is incorrect, halve the player's money
                else {
                    loseMoney(player);
                    System.out.printf("Incorrect! Player %d now has £%d.\n", getNumber(player), getMoney(player));
                }
            }
            // Nested for loop to reset the list of questions to be reused for the next player
            for (Question question : quiz_game_questions) {
                setUsed(false, question);
            }
            // Reset parameters for the while loop
            num_answered = 0;
            // (Will not return 6 because all questions are reset to false)
            current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_game_questions);
        }
        // Print game results at the end
        for (Player player : quiz_game_players) {
            System.out.printf("Player %d has finished with £%d. \n", getNumber(player), getMoney(player));
        }
    } // END main


    // START gameRules
    public static void gameRules() { // Print out the rules of the game
        final String RULES =
                """
                        Welcome to the Double or Nothing Quiz Game!
                        
                        The rules of this quiz state that you start off with £500.
                        With each correct answer you input, you double the pot of money.
                        With each incorrect answer, you halve the pot of money.
                        """;
        System.out.println(RULES);
    } // END gameRules


    // START isQuestionCorrect
    public static boolean isQuestionCorrect(String question, String answer) {
        return (question.equals(answer)); // Return true if the question's answer and the user's answer match
    } // END isQuestionCorrect


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


    // START randomiseNumber
    public static int randomiseNumber(int max, Question[] questions) {
        Random random = new Random();
        int random_q_num = random.nextInt(max);

        for (Question question : questions) { // This prevents infinite looping by linearly searching the entire list of questions
            if (!isUsed(question)){ // If there is still a question that's unused, generate a random question number
                while (isUsed(questions[random_q_num])) {
                    random_q_num =  random.nextInt(max);
                }
                return random_q_num;
            }
        }
        return (max+1); // Return unimportant placeholder value which will be replaced
    } // END randomiseNumber


    // START createQuestionsAttributes
    public static Question createQuestionsAttributes(String answers, String category, String questionText) {
        Question question = new Question();
        question.answers = answers;
        question.category = category;
        question.questionText = questionText;
        question.used = false; // Set it to false because all questions are initially unused
        return question;
    } // END createQuestionsAttributes


    // START createPlayerAttributes
    // Initialises each player
    public static Player createPlayerAttributes(int playerNum, int maxMoney) {
        Player player = new Player();
        player.number = playerNum; // Set the number of the player to allocate each one their own money
        player.money = maxMoney; // Set the player their level of money
        return player; // Return the instance of the player
    } // END createPlayerAttributes


    // START createPlayers
    public static Player[] createPlayers(int MAX_NUMBER_OF_PLAYERS, int STARTING_WINNINGS) {
        // Create the list of player instances
        Player[] quiz_game_players = new Player[MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            quiz_game_players[i] = createPlayerAttributes(i + 1, STARTING_WINNINGS);
        }
        return quiz_game_players;
    } // END createPlayers


    // START createNewQuestions
    public static void createNewQuestions(int MAX_NUMBER_OF_QUESTIONS, Scanner scanner, String FILE_PATH) throws IOException {
        String[] new_category_questions = new String[MAX_NUMBER_OF_QUESTIONS];
        String[] new_category_answers = new String[MAX_NUMBER_OF_QUESTIONS];
        File category_folder = new File(FILE_PATH);
        String[] categories_list = category_folder.list(); // List of files in the FILE_PATH directory

        for (int i = 0; i < categories_list.length; i++) {
            categories_list[i] = categories_list[i].replaceFirst("[.][^.]+$", "").toLowerCase();
        } // Removes the file extension from each file in the list

        System.out.println("Please enter the name of the category you would like to enter: ");
        String new_category = scanner.nextLine().toLowerCase();

        while (categoryIsInArray(new_category, categories_list)) {
            System.out.println("That category already exists. Please enter a new one.");
            new_category = scanner.nextLine().toLowerCase();
        }

        final File NEW_CATEGORY_TEXT_FILE = new File(String.format("%s/%s.txt", FILE_PATH, new_category)); // Finalises the new categorised text file

        // Enter the list of questions and their respective answers
        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
            System.out.format("Enter question %d: ", i+1);
            new_category_questions[i] = scanner.nextLine();
            System.out.format("Please enter the answer of question %d: ", i+1);
            new_category_answers[i] = scanner.nextLine();
        }

        // Attempt to write to the given file path of the new categorised quiz questions, catching and printing any errors
        BufferedWriter writer = new BufferedWriter(new FileWriter(NEW_CATEGORY_TEXT_FILE));
        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
            System.out.println(new_category_questions[i] + ":" + new_category_answers[i]);
            writer.write(String.format("%s:%s", new_category_questions[i], new_category_answers[i]));
            writer.newLine();
        }
        writer.close(); // CLose the BufferedWriter in order to save everything


    } // END createNewQuestions


    // START gatherQuestions
    public static Question[] gatherQuestions(String categoryInputted, int MAX_NUMBER_OF_QUESTIONS, String FILE_PATH) throws IOException {
        final File QUESTIONS_FILE = new File(String.format("%s%s.txt", FILE_PATH, categoryInputted));
        String[] texts = new String[MAX_NUMBER_OF_QUESTIONS];
        String[] answers = new String[MAX_NUMBER_OF_QUESTIONS];
        Question[] questionsArray = new Question[MAX_NUMBER_OF_QUESTIONS];

        BufferedReader reader = new BufferedReader(new FileReader(QUESTIONS_FILE));
        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) {
            String current_line = reader.readLine(); // Create a temporary variable to hold each line of the categorised questions
            texts[i] = current_line.split(":")[0]; // In the files, question texts are to the left of the colon, stored in texts array
            answers[i] = current_line.split(":")[1]; // Whereas store the answers are to the right of it, in the answers array
            questionsArray[i] = createQuestionsAttributes(answers[i], categoryInputted, texts[i]); // Add the text, its answer and the category to the current question instance
        }
        return questionsArray;
    } // END gatherQuestions

    // START showCategories
    public static String showCategories(String FILE_PATH, Scanner scanner) {
        File categories_folder = new File(FILE_PATH); // Makes a new file variable instance of the current file path
        String[] categories_list = categories_folder.list(); // Lists ALL files in given file path
        String category_inputted;
        int display_number = 0; // To display the correct category number, in order to avoid ghost files in the below for loop

        if (categories_folder.exists() && categories_folder.isDirectory()) {
            for (int i = 0; i < categories_list.length; i++) {
                if (!categories_list[i].startsWith(".") && categories_list[i].endsWith(".txt")) { // Ignore ghost files and/or non .txt files
                    display_number++;
                    categories_list[i] = categories_list[i].replaceFirst("[.][^.]+$", "");
                    System.out.printf("%d) %s\n", display_number,  categories_list[i]);
                }
            } // Do not increment the display_number if the condition prior was not met
        }
        System.out.println("Pick a category: ");
        category_inputted = scanner.nextLine();
        while (!categoryIsInArray(category_inputted, categories_list)) {
            System.out.println("Enter an existing category please.");
            category_inputted = scanner.nextLine();
        }
        return category_inputted;
    } // END showCategories


    // Modifier and accessor methods for the attributes of each question
    public static boolean isUsed(Question question) {return question.used;}
    public static String getCategory(Question question) {return question.category;}
    public static String getAnswer(Question question) {return question.answers.toLowerCase();}
    public static String getQuestionText(Question question) {return question.questionText;}
    public static void setUsed (boolean used, Question question) {question.used = used;}


    // Modifier and accessor methods that access attributes of a player
    public static int getNumber(Player player) {return player.number;}
    public static int getMoney(Player player) {return player.money;}
    public static void setMoney(int newMoney, Player player) {player.money = newMoney;}
    public static void winMoney(Player player) {setMoney((player.money * 2), player);}
    public static void loseMoney(Player player) {setMoney((player.money/ 2), player);}

}
