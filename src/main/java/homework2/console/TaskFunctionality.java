package homework2.console;

import homework2.data.DataProvider;
import homework2.entity.Task;

import java.time.LocalDate;

import static homework2.console.ConsoleHandler.*;
import static homework2.data.CsvDataLoader.formatter;

public class TaskFunctionality {
    static void addTask() {
        boolean noUser = false;
        int userId = askForId("Enter user ID", "User ID should be a number");
        if (DataProvider.getUser(userId) == null) {
            System.out.println("Warning: No user with such Id");
            noUser = true;
        }
        int taskId;
        while (true) {
            taskId = askForId("Enter task ID", "Task ID should be a number");
            if (noUser) break;
            if (DataProvider.checkTaskAtUser(userId, taskId)) {
                System.out.println("This user already has task with such ID, try another");
            } else {
                break;
            }
        }
        String header = getString("Enter task header");
        String description = getString("Enter task description");
        LocalDate deadLine = getDate("Enter dead line, follow format: " + formatter);
        DataProvider.addTaskToUser(userId, taskId, header, description, deadLine, Task.TaskStatus.NEW);
        System.out.println("Task was added successfully");
    }

    static void deleteTask(){
        int userId = askForId("Enter user ID","User ID should be a number");
        if(DataProvider.getUser(userId)==null) {
            System.out.println("No such user");
            return;
        }
        int taskId = askForId("Enter task ID","Task ID should be a number");
        if(!DataProvider.checkTaskAtUser(userId,taskId)){
            System.out.println("No such task for this user");
            return;
        }
        DataProvider.deleteTask(userId,taskId);
    }
}
