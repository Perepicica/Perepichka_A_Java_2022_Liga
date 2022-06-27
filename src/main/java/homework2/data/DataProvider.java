package homework2.data;

import homework2.entity.NoUserTask;
import homework2.entity.Task;
import homework2.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {

    private final static Map<Integer, User> users = new HashMap<>();
    private final static List<NoUserTask> noUserTasks = new ArrayList<>();

    public static User getUser(int userId){
        return users.get(userId);
    }
    public static void addUser(int id, User user){
        users.put(id, user);
    }

    public static void addTaskToUser(int userid, Task task){
        try {
            users.get(userid).addTask(task);
        }catch (NullPointerException e){
            addNoUserTask(
                    new NoUserTask(task.getId(),
                            task.getHeader(),
                            task.getDescription(),
                            task.getDeadLineDate(),
                            task.getStatus(),
                            userid));
        }
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
