package homework2.console;

import homework2.data.DataProvider;
import homework2.entity.Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static homework2.console.ConsoleHandler.*;
import static homework2.console.UserFunctionality.getUser;

public class TaskFunctionality {
    static void addTask() {
        int userId = getUser();
        if (userId == -1) return;
        int taskId = getFreeTaskId(userId);
        if (taskId == -1) return;
        String header = getString("Enter task header");
        String description = getString("Enter task description");
        LocalDate deadLine = getDate("Enter dead line");
        DataProvider.addTaskToUser(userId, taskId, header, description, deadLine, Task.TaskStatus.NEW);
        System.out.println("Task was added successfully");
    }

    static void deleteTask() {
        int userId = getUser();
        if (userId == -1) return;
        Optional<Task> task = getExistingTask(userId);
        if (task.isEmpty()) return;
        DataProvider.deleteTask(userId, task.get());
        System.out.println("Task was deleted successfully");
    }

    static void editTask() {
        int userId = getUser();
        if (userId == -1) return;
        Optional<Task> optionalTask = getExistingTask(userId);
        if (optionalTask.isEmpty()) return;
        Task task = optionalTask.get();
        System.out.println("Current task data:\n" + task);
        System.out.println("What do you want to edit?\n[1] Header\n[2] Description\n[3] Dead line\n[4] Status");
        switch (getTheOption(4)) {
            case 1 -> task.setHeader(getString("Enter new header"));
            case 2 -> task.setDescription(getString("Enter new description"));
            case 3 -> task.setDeadLineDate(getDate("Enter new dead line"));
            case 4 -> changeTaskStatus(task);
        }
        System.out.println("Task was edited successfully");
    }

    static int getFreeTaskId(int userId) {
        while (true) {
            int taskId = askForId("Enter task ID", "Task ID should be a number");
            Optional<Task> task = DataProvider.checkTaskAtUser(userId, taskId);
            if (task.isPresent()) {
                System.out.println("This user has already task with such ID\n[1] Try again\n[2] Go back");
                if (getTheOption(2) == 2) return -1;
            } else return taskId;
        }
    }


    private static void changeTaskStatus(Task task) {
        StringBuilder stringBuilder = new StringBuilder("\nChose new status:\n");
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
    }

    static Optional<Task> getExistingTask(int userId) {
        Optional<Task> task;
        while (true) {
            task = DataProvider.checkTaskAtUser(userId, askForId("Enter task ID", "Task ID should be a number"));
            if (task.isEmpty()) {
                System.out.println("No task with such ID\n[1] Try again\n[2] Go back");
                if (getTheOption(2) == 2) return Optional.empty();
            } else return task;
        }
    }

}
