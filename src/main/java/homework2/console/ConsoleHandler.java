package homework2.console;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static homework2.console.TaskFunctionality.*;
import static homework2.console.UserFunctionality.askForUserId;
import static homework2.data.CsvDataLoader.dateFormat;
import static homework2.data.CsvDataLoader.formatter;

public class ConsoleHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static void askForRequest() {
        for (; ; ) {
            System.out.println("Select an action:\n" +
                    "[1] Get user tasks\n" +
                    "[2] Add Task\n" +
                    "[3] Delete task\n" +
                    "[4] Edit task \n" +
                    "[5] End the program");
            switch (getTheOption(5)) {
                case 1 -> askForUserId();
                case 2 -> addTask();
                case 3 -> deleteTask();
                case 4 -> editTask();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }

    static int getTheOption(int maxOption) {
        int result;
        while (true) {
            try {
                result = Integer.parseInt(scanner.nextLine());
                if (0 < result && result <= maxOption) return result;
                System.out.println("Wrong option... Try again");
            } catch (NumberFormatException e) {
                System.out.println("Try again... (Type just a number)");
            }
        }
    }

    static int askForId(String askingString, String errorString) {
        while (true) {
            System.out.println(askingString);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(errorString);
            }
        }
    }

    static String getString(String message) {
        while (true) {
            System.out.println(message);
            String result = scanner.nextLine();
            if (result.trim().length() > 0) return result;
        }
    }

    static LocalDate getDate(String message) {
        while (true) {
            try {
                System.out.println(message+", follow the pattern "+dateFormat);
                return LocalDate.parse(scanner.nextLine().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Follow the pattern, please");
            }
        }
    }
}
