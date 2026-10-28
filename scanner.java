import java.util.Scanner;

public class scanner {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int score = 0;
        int answer;

        System.out.println("Online Examination");
        System.out.println("Enter the number of your answer (1-4).\n");

        System.out.println("1. Which language is commonly used to write Android apps?");
        System.out.println("1) Python");
        System.out.println("2) Java");
        System.out.println("3) HTML");
        System.out.println("4) SQL");
        System.out.print("Your answer: ");
        answer = input.nextInt();

        if (answer == 2) {
            System.out.println("Correct!\n");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer is 2) Java.\n");
        }

        System.out.println("2. What is 5 + 3?");
        System.out.println("1) 6");
        System.out.println("2) 7");
        System.out.println("3) 8");
        System.out.println("4) 9");
        System.out.print("Your answer: ");
        answer = input.nextInt();

        if (answer == 3) {
            System.out.println("Correct!\n");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer is 3) 8.\n");
        }

        System.out.println("3. Which keyword is used to declare a class in Java?");
        System.out.println("1) class");
        System.out.println("2) method");
        System.out.println("3) define");
        System.out.println("4) new");
        System.out.print("Your answer: ");
        answer = input.nextInt();

        if (answer == 1) {
            System.out.println("Correct!\n");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer is 1) class.\n");
        }

        System.out.println("Exam complete!");
        System.out.println("Your score: " + score + " out of 3");

        input.close();
    }
}