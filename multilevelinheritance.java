import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

abstract class Question {
    private final String prompt;

    Question(String prompt) {
        this.prompt = prompt;
    }

    public void displayQuestion() {

        System.out.println(prompt);
    }

    public abstract boolean isCorrect(String answer);

    public abstract String getCorrectAnswerText();
}

class ObjectiveQuestion extends Question {
    private final char correctAnswer;

    ObjectiveQuestion(String prompt, char correctAnswer) {
        super(prompt);
        this.correctAnswer = Character.toUpperCase(correctAnswer);
    }

    @Override
    public boolean isCorrect(String answer) {
        return answer.trim().equalsIgnoreCase(String.valueOf(correctAnswer));
    }

    protected char getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String getCorrectAnswerText() {
        return String.valueOf(correctAnswer);
    }
}

class MultipleChoiceQuestion extends ObjectiveQuestion {
    private final String[] options;

    MultipleChoiceQuestion(String prompt, String[] options, char correctAnswer) {
        super(prompt, correctAnswer);
        if (options == null || options.length < 2) {
            throw new IllegalArgumentException("A question must have at least two options.");
        }
        if (Character.toUpperCase(correctAnswer) < 'A'
                || Character.toUpperCase(correctAnswer) >= 'A' + options.length) {
            throw new IllegalArgumentException("The correct answer must match one of the options.");
        }
        this.options = options.clone();
    }

    @Override
    public void displayQuestion() {
        super.displayQuestion();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%c) %s%n", 'A' + i, options[i]);
        }
    }

    @Override
    public String getCorrectAnswerText() {
        char answer = getCorrectAnswer();
        return answer + ") " + options[answer - 'A'];
    }

    public int getOptionCount() {
        return options.length;
    }
}

public class multilevelinheritance {
    public static void main(String[] args) {
        List<MultipleChoiceQuestion> questions = Arrays.asList(
                new MultipleChoiceQuestion(
                        "Which keyword is used to inherit a class in Java?",
                        new String[]{"implements", "extends", "inherits", "instanceof"},
                        'B'),
                new MultipleChoiceQuestion(
                        "Which method is the entry point of a standard Java application?",
                        new String[]{"start()", "run()", "main()", "init()"},
                        'C'),
                new MultipleChoiceQuestion(
                        "Which data type stores true or false values?",
                        new String[]{"int", "String", "char", "boolean"},
                        'D')
        );

        int score = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Online Examination System ===");
            System.out.println("Enter the letter for your answer.\n");

            for (int i = 0; i < questions.size(); i++) {
                MultipleChoiceQuestion question = questions.get(i);
                System.out.printf("Question %d of %d:%n", i + 1, questions.size());
                question.displayQuestion();

                String answer = readAnswer(scanner, question.getOptionCount());
                if (question.isCorrect(answer)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect.");
                }
                System.out.println("Correct answer: " + question.getCorrectAnswerText() + "\n");
            }
        }

        System.out.printf("Exam complete. Your score: %d out of %d.%n", score, questions.size());
    }

    private static String readAnswer(Scanner scanner, int optionCount) {
        while (true) {
            System.out.print("Your answer: ");
            String answer = scanner.nextLine().trim().toUpperCase();
            if (answer.length() == 1 && answer.charAt(0) >= 'A'
                    && answer.charAt(0) < 'A' + optionCount) {
                return answer;
            }
            System.out.println("Please enter a valid option letter.");
        }
    }
}
