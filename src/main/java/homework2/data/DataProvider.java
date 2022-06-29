package homework2.data;

import homework2.entity.Task;
import homework2.entity.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataProvider {

    private final static Map<Integer, User> users = new HashMap<>();
    private final static String undefinedUser = "undefined";

    public static User getUser(int userId) {
        return users.get(userId);
    }

    public static void addUser(int id, User user) {
        users.put(id, user);
    }

    public static Map<Integer, User> getUsers() {
        return users;
    }

    public static void addTaskToUser(int userId, int taskId, String header, String description, LocalDate deadLine, Task.TaskStatus status) {
        if (users.get(userId) == null) users.put(userId, new User(undefinedUser));
        users.get(userId).addTask(new Task(taskId, header, description, deadLine, status));
    }

    public static void deleteTask(int userId, int taskId){
        getUser(userId).getTasks().removeIf(task -> task.getId()==taskId);
    }

    public static boolean checkTaskAtUser(int userId, int taskId) {
        for (Task task : users.get(userId).getTasks()) {
            if (task.getId() == taskId) return true;
        }
        return false;
    }
}
