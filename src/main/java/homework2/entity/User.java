package homework2.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    String name;
    List<Task> tasks;

    public User(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }
}
