import java.util.Scanner;

public class abstraction {
    public static void main(String[] args) {
        OnlineExamination examination = new OnlineExamination();
        examination.conductExam();
    }
}

abstract class ExamQuestion {
    private final String questionText;
    private final String[] options;
    private final String correctAnswer;

    protected ExamQuestion(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    protected String getQuestionText() {
        return questionText;
    }

    protected String[] getOptions() {
        return options;
    }

    public abstract void display();

    public boolean verifyAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }
}

class MultipleChoiceQuestion extends ExamQuestion {
    public MultipleChoiceQuestion(String questionText, String[] options, String correctAnswer) {
        super(questionText, options, correctAnswer);
    }

    @Override
    public void display() {
        System.out.println(getQuestionText());
        String[] options = getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((char) ('A' + i) + ". " + options[i]);
        }
    }
}

abstract class ExamSession {
    protected abstract void showResult(int score, int totalQuestions);
}

interface ExamOperations {
    void conductExam();
}

interface ResultOperations {
    void displayInstructions();
}

class OnlineExamination extends ExamSession implements ExamOperations, ResultOperations {
    private final ExamQuestion[] questions = {
        new MultipleChoiceQuestion(
            "1. Which keyword is used to inherit a class in Java?",
            new String[] {"implements", "extends", "inherits", "instanceof"},
            "B"
        ),
        new MultipleChoiceQuestion(
            "2. Which type can contain abstract methods?",
            new String[] {"Abstract class", "String", "Array", "Package"},
            "A"
        ),
        new MultipleChoiceQuestion(
            "3. How does Java support multiple inheritance of type?",
            new String[] {"By extending multiple classes", "By implementing multiple interfaces",
                          "By using multiple main methods", "Java does not support it"},
            "B"
        )
    };

    @Override
    public void displayInstructions() {
        System.out.println("Choose A, B, C, or D for each question.");
        System.out.println("Each correct answer is worth one point.\n");
    }

    @Override
    public void conductExam() {
        int score = 0;
        displayInstructions();

        try (Scanner scanner = new Scanner(System.in)) {
            for (ExamQuestion question : questions) {
                question.display();
                String answer;
                do {
                    System.out.print("Your answer: ");
                    answer = scanner.nextLine().trim();
                    if (!answer.matches("[A-Da-d]")) {
                        System.out.println("Please enter A, B, C, or D.");
                    }
                } while (!answer.matches("[A-Da-d]"));

                if (question.verifyAnswer(answer)) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Incorrect.\n");
                }
            }
        }

        showResult(score, questions.length);
    }

    @Override
    protected void showResult(int score, int totalQuestions) {
        double percentage = (score * 100.0) / totalQuestions;
        System.out.printf("Exam complete! Score: %d/%d (%.1f%%)%n",
                          score, totalQuestions, percentage);
    }
}