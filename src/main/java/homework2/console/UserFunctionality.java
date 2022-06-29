package homework2.console;

import homework2.data.DataProvider;
import homework2.entity.Task;
import homework2.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static homework2.console.ConsoleHandler.*;

public class UserFunctionality {
    static void askForUserId() {
        int userId = getUser();
        if(userId == -1) return;
        filteringTasks(DataProvider.getUser(userId));
    }

    static int getUser() {
        int userId;
        while (true) {
            userId = askForId("Enter user ID", "User ID should be a number");
            if (DataProvider.getUser(userId) == null) {
                System.out.println("No user with such ID\n[1] Try again\n[2] Go back");
                if (getTheOption(2) == 2) return -1;
            } else return userId;
        }
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

}
