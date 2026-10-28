import java.util.Scanner;

public class classes {
    private final String[] questions = {
        "Which keyword creates a class in Java?",
        "Which data type stores true or false?",
        "Which method is the entry point of a Java program?"
    };

    private final String[][] options = {
        {"A. function", "B. class", "C. define"},
        {"A. boolean", "B. String", "C. int"},
        {"A. start()", "B. main()", "C. run()"}
    };

    private final char[] correctAnswers = {'B', 'A', 'B'};

    public classes() {
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your name: ");
            String studentName = scanner.nextLine().trim();
            if (studentName.isEmpty()) {
                studentName = "Student";
            }

            System.out.println("\n--- Online Examination ---");
            System.out.println("Student: " + studentName);
            int score = 0;

            for (int i = 0; i < questions.length; i++) {
                System.out.println("\n" + (i + 1) + ". " + questions[i]);
                for (String option : options[i]) {
                    System.out.println("  " + option);
                }

                char answer;
                while (true) {
                    System.out.print("Your answer (A, B, or C): ");
                    String input = scanner.nextLine().trim().toUpperCase();
                    if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'C') {
                        answer = input.charAt(0);
                        break;
                    }
                    System.out.println("Please enter A, B, or C.");
                }

                if (answer == correctAnswers[i]) {
                    score++;
                }
            }

            double percentage = (double) score / questions.length * 100;
            System.out.println("\n--- Exam Results ---");
            System.out.println("Student: " + studentName);
            System.out.println("Score: " + score + "/" + questions.length);
            System.out.printf("Percentage: %.1f%%%n", percentage);
        }
    }

    public static void main(String[] args) {
        classes exam = new classes();
        exam.start();
    }
}
