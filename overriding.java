import java.util.Scanner;

abstract class Question {
    private final String text;
    private final String[] options;
    private final char correctAnswer;

    public Question(String text, String[] options, char correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = Character.toUpperCase(correctAnswer);
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public abstract void display();

    public abstract boolean verifyAnswer(char answer);
}

class MultipleChoiceQuestion extends Question {
    public MultipleChoiceQuestion(String text, String[] options, char correctAnswer) {
        super(text, options, correctAnswer);
    }

    @Override
    public void display() {
        System.out.println(getText());
        String[] options = getOptions();

        for (int i = 0; i < options.length; i++) {
            System.out.println((char) ('A' + i) + ") " + options[i]);
        }
    }

    @Override
    public boolean verifyAnswer(char answer) {
        return Character.toUpperCase(answer) == getCorrectAnswer();
    }
}

public class overriding {
    public static void main(String[] args) {
        Question[] questions = {
            new MultipleChoiceQuestion(
                "Which keyword is used to inherit a class in Java?",
                new String[] {"implements", "extends", "inherits", "super"},
                'B'
            ),
            new MultipleChoiceQuestion(
                "Which method is the entry point of a Java application?",
                new String[] {"start()", "run()", "main()", "init()"},
                'C'
            ),
            new MultipleChoiceQuestion(
                 "Which annotation indicates that a method overrides another method?",
                new String[] {"@Overload", "@Override", "@Inherited", "@Parent"},
                'B'
            )
        };

        int score = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Online Examination ===");
            for (int i = 0; i < questions.length; i++) {
                System.out.println("\nQuestion " + (i + 1) + ":");
                Question question = questions[i];
                question.display();

                char answer;
                while (true) {
                    System.out.print("Your answer (A-D): ");
                    String input = scanner.nextLine().trim();

                    if (input.length() == 1) {
                        answer = Character.toUpperCase(input.charAt(0));
                        if (answer >= 'A' && answer <= 'D') {
                            break;
                        }
                    }

                    System.out.println("Please enter A, B, C, or D.");
                }

                if (question.verifyAnswer(answer)) {
                    System.out.println("Correct!");
                     score++;
                } else {
                    System.out.println(
                        "Incorrect. The correct answer is "
                            + question.getCorrectAnswer() + "."
                    );
                }
            }
        }

        System.out.println("\nExam complete. Your score: " + score + "/" + questions.length);
    }
}
