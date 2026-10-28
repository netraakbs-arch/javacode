import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Question[] questions = {
            new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[] {"this", "extends", "implements", "super"},
                "B"
            ),
            new Question(
                "Which method is the entry point of a Java application?",
                new String[] {"start()", "run()", "main()", "init()"},
                "C"
            ),
            new Question(
                "Which data type stores true or false?",
                new String[] {"int", "String", "char", "boolean"},
                "D"
            )
        };

        try (Scanner scanner = new Scanner(System.in)) {
            ExamSystem exam = new ExamSystem(scanner);
            exam.start(questions);
        }
    }
}

// Package-private class: accessible within this package.
class Question {
    private final String prompt;
    private final String[] options;
    private final String correctAnswer;

    public Question(String prompt, String[] options, String correctAnswer) {
        this.prompt = prompt;
        this.options = options.clone();
        this.correctAnswer = correctAnswer;
    }

    public String getPrompt() {
        return prompt;
    }

    public String[] getOptions() {
        return options.clone();
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }
}

class ExamSystem {
    private final Scanner input;

    public ExamSystem(Scanner input) {
        this.input = input;
    }

    public void start(Question[] questions) {
        printInstructions();
        int score = 0;

        for (int i = 0; i < questions.length; i++) {
            Question question = questions[i];

            // Calls the two-parameter overloaded method.
            showQuestion(question, i + 1);

            String answer = readAnswer(question.getOptions().length);
            if (question.isCorrect(answer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect.\n");
            }
        }

        System.out.println("Exam finished. Your score: "
                + score + " out of " + questions.length);
    }

    // Overloaded method 1
    public void showQuestion(Question question) {
        showQuestion(question, 0);
    }

    // Overloaded method 2: same name, different parameter list
    public void showQuestion(Question question, int questionNumber) {
        if (questionNumber > 0) {
            System.out.println("Question " + questionNumber + ":");
        }

        System.out.println(question.getPrompt());
        String[] options = question.getOptions();

        for (int i = 0; i < options.length; i++) {
            char optionLetter = (char) ('A' + i);
            System.out.println(optionLetter + ". " + options[i]);
        }
    }

    private String readAnswer(int optionCount) {
        while (true) {
            System.out.print("Your answer (A-" + (char) ('A' + optionCount - 1) + "): ");
            String answer = input.nextLine().trim().toUpperCase();

            if (answer.length() == 1
                    && answer.charAt(0) >= 'A'
                    && answer.charAt(0) < 'A' + optionCount) {
                return answer;
            }

            System.out.println("Please enter a valid option.");
        }
    }

    // Protected access: available to this class and its subclasses.
    protected void printInstructions() {
        System.out.println("Online Examination System");
        System.out.println("Choose A, B, C, or D for each question.\n");
    }
}