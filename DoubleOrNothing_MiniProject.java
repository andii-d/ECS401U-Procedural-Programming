/* ***************************************
  @author    Andy Dishnica
  @SID       250334126
  @date      20 October 2025
  @version   4

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
    public String getAnswer() {return answers;}
    public String getCategory() {return category;}
    public boolean isUsed() {return used;}
    public void setUsed (boolean used) {this.used = true;}
    public String getQuestionText() {return questionText;}
    public void resetQuestions(Question question) {question.setUsed(false);}

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
        final String FILE_PATH = "/Users/andy/Desktop/Coding/Java files/MINI PROJECT/Categories/";

        // Create variables beforehand, to be visible by scope
        int category_menu;
        Question[] quiz_game_questions = null;

        // Call the method which prints the rules of the game
        gameRules();

        // START
        // Picking the category of the quiz`
        //
        System.out.println("To pick a category or make a new one:\n1) Choose categories \n2) New \nEnter: ");


        do { // Whilst the options do not align with 1 or 2 entered, keep asking the user to enter either option correctly
            category_menu = Integer.parseInt(scanner.nextLine());
            if (category_menu == 1) {
                quiz_game_questions = gatherQuestions(showCategories(FILE_PATH, scanner), MAX_NUMBER_OF_QUESTIONS);
            }
            else if (category_menu == 2){
                createQuestions(MAX_NUMBER_OF_QUESTIONS, scanner, FILE_PATH);
            }
            else {
                System.out.println("Enter a valid option 1 or 2.");
            }
        } while (category_menu != 1 && category_menu != 2); // Condition keeps performing above code until 1 or 2 is entered
        // END

        Player[] quiz_game_players = createPlayers(MAX_NUMBER_OF_PLAYERS, STARTING_WINNINGS);

        quizGame(quiz_game_players, quiz_game_questions, scanner, MAX_NUMBER_OF_QUESTIONS); // Start the game
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
        File categories_folder = new File(FILE_PATH);
        String[] categories_list = categories_folder.list();
        String category_inputted;

        if (categories_folder.exists() && categories_folder.isDirectory()) {
            for (int i = 0; i < categories_list.length; i++) {
                if (!categories_list[i].startsWith(".") && categories_list[i].endsWith(".txt")) { // Ignore ghost files and/or non .txt files
                    categories_list[i] = categories_list[i].replaceFirst("[.][^.]+$", "");
                    System.out.println(categories_list[i]);
                }
            }
        }
        System.out.println("Pick a category: ");
        category_inputted = scanner.nextLine();
        for (String category : categories_list) {
            System.out.println(category.equals(category_inputted));
        }
        while (!categoryIsInArray(category_inputted, categories_list)) {
            System.out.println("Enter an existing category please.");
            category_inputted = scanner.nextLine();
            System.out.println("Debugging 1");
        }
        System.out.println("Debugging 2");
        return category_inputted;
    } // END showCategories

    public static boolean categoryIsInArray(String category_inputted, String[] list_of_categories) {
        // Linearly search the entire list of categories given, and return whether the category inputted exists or not
        for (String category : list_of_categories) {
            if (category.equals(category_inputted)) {
                return true;
            }
        }
        return false;
    }

    public static Player[] createPlayers(int MAX_NUMBER_OF_PLAYERS, int STARTING_WINNINGS) {
        // Create the players for the game
        Player[] quiz_game_players = new Player[MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            quiz_game_players[i] = Player.createPlayers(i + 1, STARTING_WINNINGS);
        }
        return quiz_game_players;
    }

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

        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS; i++) { // Enter the list of questions and their respective answers
            System.out.format("Enter question %d: ", i+1);
            new_category_questions[i] = scanner.nextLine();
            System.out.format("Please enter the answer of question %d: ", i+1);
            new_category_answers[i] = scanner.nextLine();
        }

        try {
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

    } // END createCategories

    public static Question[] gatherQuestions(String categoryInputted, int MAX_NUMBER_OF_QUESTIONS) {
        final File QUESTIONS_FILE = new File(String.format("/Users/andy/Desktop/Coding/Java files/MINI PROJECT/Categories/%s.txt", categoryInputted));
        String[] texts = new String[MAX_NUMBER_OF_QUESTIONS];
        String[] answers = new String[MAX_NUMBER_OF_QUESTIONS];
        Question[] questionsArray = new Question[MAX_NUMBER_OF_QUESTIONS];

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

    public static boolean isQuestionCorrect(String question, String answer) {
        return (question.equals(answer)); // Return true if the question's answer and the user's answer match
    }

    public static int randomiseNumber(int max, Question[] questions) {
        Random random = new Random();
        int random_q_num = random.nextInt(max);

        while (questions[random_q_num].isUsed()) {
            random_q_num =  random.nextInt(max);
        }
        return random_q_num;
    }

    public static void quizGame(Player[] players, Question[] quiz_questions, Scanner scanner, int MAX_NUMBER_OF_QUESTIONS) {
        String current_input = "";
        int winner = 0;
        int current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_questions);

        for (Player player : players) {
            // Nested for loop to reset the list of questions to be reused for the next player
            for (Question question : quiz_questions) {
                question.resetQuestions(question);
            }

            current_input = ""; // Must be updated to run the while loop (do -> while loops are forbade from being used as per style guide)
            // Increment current_question_number inside the while loop
            while (!current_input.equals(quiz_questions[current_question_number].getAnswer()) && !current_input.equals("pass") && !quiz_questions[current_question_number].isUsed()) {
                System.out.println(quiz_questions[current_question_number].getQuestionText());

                current_input = scanner.nextLine();

                // If the player wants to pass, halve their money and go to the next question
                if (current_input.equals("pass")) {
                    player.loseMoney();
                    current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_questions); // Go to the next question
                }

                else if (current_input.equals(quiz_questions[current_question_number].getAnswer())) {
                    player.setMoney(player.getMoney() * 2);
                    System.out.println(player.getMoney() + " money left for player number " + player.getNumber());
                    current_question_number = randomiseNumber(MAX_NUMBER_OF_QUESTIONS, quiz_questions);
                }

                // If the answer is incorrect, halve the player's money
                else {
                    System.out.println("Incorrect!");
                    player.loseMoney();

                }
            }
        }

        for (Player player : players) {
            System.out.printf("Player %d has finished with: £%d\n", player.getNumber(), player.getMoney());
        }
    }
}
