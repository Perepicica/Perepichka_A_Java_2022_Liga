package homework2.console;

import homework2.data.DataProvider;
import homework2.entity.Task;
import homework2.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleHandler {

    private static String request;
    private static final Scanner scanner = new Scanner(System.in);

    public static void askForRequest() {
        for (; ; ) {
            System.out.println("Select an action:\n[1] Get user tasks\n[2] Change task status\n[3]End the program");
            switch (getTheOption(3)) {
                case 1 -> askForUserId();
                case 2 -> askForTaskId();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }

    private static int getTheOption(int maxOption) {
        int result;
        while (true) {
            request = scanner.nextLine();
            try {
                result = Integer.parseInt(request);
                if (0 < result && result <= maxOption) return result;
                System.out.println("Wrong option... Try again");
            } catch (NumberFormatException e) {
                System.out.println("Try again... (Type just a number)");
            }
        }
    }

    private static void askForUserId() {
        while (!checkUserId(askForId("Enter user ID", "User ID should be a number"))) {
        }
    }

    private static boolean checkUserId(int id) {
        User user = DataProvider.getUser(id);
        if (user == null) {
            return handleWrongUserId();
        } else filteringTasks(user);
        return true;
    }

    private static boolean handleWrongUserId() {
        System.out.println("No user with such ID\n[1] Try again\n[2] Go back");
        return getTheOption(2) == 2;
    }


    private static void filteringTasks(User user) {
        System.out.println("Would you like to filter tasks?\n[1] Yes\n[2] No");
        switch (getTheOption(2)) {
            case 1 -> askFilterType(user);
            case 2 -> printUserTasks(user.getTasks(), user.getName());
        }
    }

    private static void askFilterType(User user) {
        StringBuilder filterTypes = new StringBuilder();
        Map<Integer, Task.TaskStatus> statuses = new HashMap<>();
        int option = 1;
        for (Task.TaskStatus status : Task.TaskStatus.values()) {
            statuses.put(option, status);
            filterTypes.append("[").append(option).append("] ").append(status).append("\n");
            option++;
        }
        System.out.println(filterTypes);
        printUserTasks(filterTasks(user, statuses.get(getTheOption(Task.TaskStatus.values().length))), user.getName());
    }

    private static List<Task> filterTasks(User user, Task.TaskStatus status) {
        return user.getTasks().stream().filter(task -> task.getStatus().equals(status)).collect(Collectors.toList());
    }


    private static void printUserTasks(List<Task> tasks, String name) {
        System.out.println("Task of user " + name + ":");
        if (tasks.isEmpty()) {
            System.out.println("No tasks for this user or tasks with such status");
            return;
        }
        for (Task task : tasks) {
            System.out.println("Task id: " + task.getId());
            System.out.println("Task header: " + task.getHeader());
            System.out.println("Task description: " + task.getDescription());
            System.out.println("Task deadline: " + task.getDeadLineDate());
            System.out.println("Task status: " + task.getStatus() + "\n");
        }
    }

    private static void askForTaskId() {
        while (true) {
            int id = askForId("Enter task ID", "Task ID should be a number");
            Optional<Task> task = DataProvider.getUsers().entrySet().stream()
                    .flatMap(entry -> entry.getValue().getTasks().stream())
                    .filter(t -> t.getId() == id).findFirst();
            if (task.isPresent()) {
                changeTaskStatus(task.get());
                return;
            } else {
                System.out.println("No task with such ID \n[1] Try again\n[2] Go back");
                if (getTheOption(2) == 2) return;
            }
        }
    }


    private static int askForId(String askingString, String errorString) {
        while (true) {
            System.out.println(askingString);
            request = scanner.nextLine();
            try {
                return Integer.parseInt(request.trim());
            } catch (NumberFormatException e) {
                System.out.println(errorString);
            }
        }
    }

    private static void changeTaskStatus(Task task) {
        StringBuilder stringBuilder = new StringBuilder("Current status is: ")
                .append(task.getStatus())
                .append("\nChose new status:\n");
        Map<Integer, Task.TaskStatus> statusToChange = new HashMap<>();
        int option = 1;
        for (Task.TaskStatus status : Task.TaskStatus.values()) {
            if (!status.equals(task.getStatus())) {
                statusToChange.put(option, status);
                stringBuilder.append("[").append(option).append("] ").append(status).append("\n");
                option++;
            }
        }
        System.out.println(stringBuilder);
        Task.TaskStatus newStatus = statusToChange.get(getTheOption(Task.TaskStatus.values().length - 1));
        task.setStatus(newStatus);
        System.out.println("Task status has been changed to " + newStatus + " successfully!");
    }
}
