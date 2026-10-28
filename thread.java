import java.util.Scanner;

public class thread implements Runnable {
    private static final String[] QUESTIONS = {
        "Which keyword is used to create a subclass in Java?",
        "Which method is the entry point of a Java application?",
        "Which interface can be implemented to define a task for a thread?"
    };

    private static final String[][] OPTIONS = {
        {"A. this", "B. extends", "C. implements", "D. super"},
        {"A. start()", "B. run()", "C. main()", "D. init()"},
        {"A. Runnable", "B. Serializable", "C. Comparable", "D. Cloneable"}
    };

    private static final char[] ANSWERS = {'B', 'C', 'A'};

    @Override
    public void run() {
        int score = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Online Examination");
            System.out.println("Enter A, B, C, or D for each question.\n");

            for (int i = 0; i < QUESTIONS.length; i++) {
                System.out.println((i + 1) + ". " + QUESTIONS[i]);
                for (String option : OPTIONS[i]) {
                    System.out.println("   " + option);
                }

                char answer;
                while (true) {
                    System.out.print("Your answer: ");
                    String input = scanner.nextLine().trim();
                    if (input.length() == 1
                            && input.toUpperCase().charAt(0) >= 'A'
                            && input.toUpperCase().charAt(0) <= 'D') {
                        answer = input.toUpperCase().charAt(0);
                        break;
                    }
                    System.out.println("Please enter A, B, C, or D.");
                }

                if (answer == ANSWERS[i]) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer is "
                            + ANSWERS[i] + ".\n");
                }
            }
        }

        System.out.println("Examination complete. Your score: "
                + score + " out of " + QUESTIONS.length);
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable examination = new thread();
        Thread examinationThread = new Thread(examination);
        examinationThread.start();
        examinationThread.join();
    }
}
