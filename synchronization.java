import java.util.Locale;
import java.util.Scanner;

public class synchronization {
    private static final String[] QUESTIONS = {
        "What is the capital of France?",
        "What is 5 x 3?",
        "Which is the largest planet in our solar system?"
    };

    private static final String[][] OPTIONS = {
        {"A. Paris", "B. Rome", "C. Madrid", "D. Berlin"},
        {"A. 8", "B. 15", "C. 10", "D. 20"},
        {"A. Earth", "B. Mars", "C. Jupiter", "D. Venus"}
    };

    private static final String[] CORRECT_ANSWERS = {"A", "B", "C"};

    private int score;

    public static void main(String[] args) throws InterruptedException {
        String[] userAnswers = new String[QUESTIONS.length];

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Online Examination - enter A, B, C, or D.");

            for (int i = 0; i < QUESTIONS.length; i++) {
                System.out.println("\n" + (i + 1) + ". " + QUESTIONS[i]);
                for (String option : OPTIONS[i]) {
                    System.out.println(option);
                }

                userAnswers[i] = readAnswer(scanner);
            }
        }

        synchronization exam = new synchronization();
        Thread[] checkerThreads = new Thread[QUESTIONS.length];

        System.out.println("\nChecking your answers...");
        for (int i = 0; i < checkerThreads.length; i++) {
            final int questionIndex = i;

            checkerThreads[i] = new Thread(
                () -> exam.checkAnswer(
                    questionIndex,
                    userAnswers[questionIndex]
                ),
                "QuestionChecker-" + (i + 1)
            );

            checkerThreads[i].start();
        }

        for (Thread checkerThread : checkerThreads) {
            checkerThread.join();
        }

        System.out.println("\nExam complete. Your score: "
            + exam.getScore() + " out of " + QUESTIONS.length + ".");
    }

    private static String readAnswer(Scanner scanner) {
        while (true) {
            System.out.print("Your answer: ");
            String answer = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

            if (answer.matches("[A-D]")) {
                return answer;
            }

            System.out.println("Please enter A, B, C, or D.");
        }
    }

    private synchronized void checkAnswer(int questionIndex, String answer) {
        boolean correct = CORRECT_ANSWERS[questionIndex].equals(answer);

        if (correct) {
            score++;
        }

        System.out.println(Thread.currentThread().getName()
            + ": Question " + (questionIndex + 1) + " is "
            + (correct ? "correct." : "incorrect.")
            + " Correct answer: " + CORRECT_ANSWERS[questionIndex] + ".");
    }

    private synchronized int getScore() {
        return score;
    }
}