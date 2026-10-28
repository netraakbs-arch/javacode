import java.util.Scanner;

public class threadcomm {
    private static final Question[] QUESTIONS = {
        new Question(
            1,
            "Which keyword is used to inherit a class in Java?",
            new String[] {"implements", "extends", "inherits", "super"},
            2
        ),
        new Question(
            2,
            "Which method is the entry point of a Java application?",
            new String[] {"start()", "run()", "main()", "init()"},
            3
        ),
        new Question(
            3,
            "Which collection does not allow duplicate elements?",
            new String[] {"List", "Queue", "Set", "ArrayList"},
            3
        )
    };

    public static void main(String[] args) throws InterruptedException {
        ExamRoom room = new ExamRoom();

        Thread examiner = new Thread(() -> conductExam(room), "Examiner");
        Thread student = new Thread(() -> takeExam(room), "Student");

        examiner.start();
        student.start();

        examiner.join();
        student.join();
    }

    private static void conductExam(ExamRoom room) {
        int score = 0;

        try {
            for (Question question : QUESTIONS) {
                synchronized (room) {
                    room.question = question;
                    room.answer = 0;
                    room.resultReady = false;
                    room.feedbackRead = false;
                    room.notifyAll();

                    while (room.answer == 0) {
                        room.wait();
                    }

                    room.correct = room.answer == question.correctOption;
                    if (room.correct) {
                        score++;
                    }
                    room.resultReady = true;
                    room.notifyAll();

                    while (!room.feedbackRead) {
                        room.wait();
                    }
                }
            }

            synchronized (room) {
                room.finished = true;
                room.finalScore = score;
                room.notifyAll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("The examination was interrupted.");
        }
    }

    private static void takeExam(ExamRoom room) {
        try (Scanner scanner = new Scanner(System.in)) {
            for (int questionNumber = 1; questionNumber <= QUESTIONS.length; questionNumber++) {
                Question question;
                synchronized (room) {
                    while (room.question == null || room.question.number != questionNumber) {
                        room.wait();
                    }
                    question = room.question;
                }

                System.out.println("\nQuestion " + question.number + ": " + question.text);
                for (int i = 0; i < question.options.length; i++) {
                    System.out.println((i + 1) + ". " + question.options[i]);
                }

                int answer = readOption(scanner, question.options.length);
                synchronized (room) {
                    room.answer = answer;
                    room.notifyAll();

                    while (!room.resultReady) {
                        room.wait();
                    }

                    if (room.correct) {
                        System.out.println("Correct!");
                    } else {
                        System.out.println(
                            "Incorrect. Correct answer: "
                                + question.options[question.correctOption - 1]
                        );
                    }

                    room.feedbackRead = true;
                    room.notifyAll();
                }
            }

            synchronized (room) {
                while (!room.finished) {
                    room.wait();
                }
                System.out.println(
                    "\nExam completed. Your score: " + room.finalScore + "/" + QUESTIONS.length
                );
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("The examination was interrupted.");
        }
    }

    private static int readOption(Scanner scanner, int optionCount) {
        while (true) {
            System.out.print("Enter your answer (1-" + optionCount + "): ");
            String input = scanner.nextLine().trim();

            try {
                int answer = Integer.parseInt(input);
                if (answer >= 1 && answer <= optionCount) {
                    return answer;
                }
            } catch (NumberFormatException ignored) {
                // Ask again when the input is not a number.
            }

            System.out.println("Please enter a number from 1 to " + optionCount + ".");
        }
    }

    private static final class ExamRoom {
        private Question question;
        private int answer;
        private boolean correct;
        private boolean resultReady;
        private boolean feedbackRead;
        private boolean finished;
        private int finalScore;
    }

    private static final class Question {
        private final int number;
        private final String text;
        private final String[] options;
        private final int correctOption;

        private Question(int number, String text, String[] options, int correctOption) {
            this.number = number;
            this.text = text;
            this.options = options;
            this.correctOption = correctOption;
        }
    }
}
