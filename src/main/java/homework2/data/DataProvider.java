package homework2.data;

import homework2.entity.NoUserTask;
import homework2.entity.Task;
import homework2.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {

    private final static Map<Integer, User> users = new HashMap<>();
    private final static List<NoUserTask> noUserTasks = new ArrayList<>();

    public static User getUser(int userId) {
        return users.get(userId);
    }

    public static void addUser(int id, User user) {
        users.put(id, user);
    }

    public static void addTaskToUser(int userId, int taskId, String header, String description, LocalDate deadLine, Task.TaskStatus status) {
        try {
            users.get(userId).addTask(new Task(taskId, header, description, deadLine, status));
        } catch (NullPointerException e) {
            addNoUserTask(new NoUserTask(taskId, header, description, deadLine, status, userId));
        }

    }

    public static boolean checkTaskAtUser(int userId, int taskId){
        for(Task task:users.get(userId).getTasks()){
            if(task.getId()==taskId) return true;
        }
        return false;
    }

    public static void addNoUserTask(NoUserTask task) {
        noUserTasks.add(task);
    }

    public static Map<Integer, User> getUsers() {
        return users;
    }

    public static List<NoUserTask> getNoUserTasks() {
        return noUserTasks;
    }
}
