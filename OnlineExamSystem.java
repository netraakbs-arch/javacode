import java.util.Scanner;

class InvalidOptionException extends Exception {
    public InvalidOptionException(String message) {
        super(message);
    }
}

class IncorrectAnswerException extends Exception {
    public IncorrectAnswerException(String message) {
        super(message);
    }
}

public class OnlineExamSystem {

    private static void validateOption(String answer)
            throws InvalidOptionException {
        if (!answer.matches("[ABCD]")) {
            throw new InvalidOptionException(
                    "Please enter one of the displayed options: A, B, C, or D.");
        }
    }

    private static void verifyAnswer(String answer, String correctAnswer)
            throws IncorrectAnswerException {
        if (!answer.equals(correctAnswer)) {
            throw new IncorrectAnswerException("That answer is incorrect.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        String[] questions = {
            "1. Which keyword is used to create a class in Java?",
            "2. Which method is the entry point of a Java program?",
            "3. Which type stores true or false values?"
        };

        String[][] options = {
            {"A. function", "B. class", "C. define", "D. object"},
            {"A. start()", "B. run()", "C. main()", "D. execute()"},
            {"A. int", "B. String", "C. char", "D. boolean"}
        };

        String[] correctAnswers = {"B", "C", "D"};

        System.out.println("=== Online Examination ===");

        for (int i = 0; i < questions.length; i++) {
            System.out.println("\n" + questions[i]);

            for (String option : options[i]) {
                System.out.println(option);
            }

            while (true) {
                System.out.print("Your answer (A/B/C/D): ");
                String answer = scanner.nextLine().trim().toUpperCase();

                try {
                    validateOption(answer);
                    verifyAnswer(answer, correctAnswers[i]);
                    System.out.println("Correct!");
                    score++;
                    break;
                } catch (InvalidOptionException e) {
                    System.out.println("Invalid option: " + e.getMessage());
                } catch (IncorrectAnswerException e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
        }

        System.out.println("\n=== Exam Result ===");
        System.out.println("Your score: " + score + " out of " + questions.length);

        scanner.close();
    }
}