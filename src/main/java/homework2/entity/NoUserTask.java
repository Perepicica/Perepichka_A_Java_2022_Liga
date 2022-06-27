package homework2.entity;

import java.time.LocalDate;

public class NoUserTask extends Task {
    int userId;

    public NoUserTask(int id, String header, String description, LocalDate deadLineDate, TaskStatus status, int userId) {
        super(id, header, description, deadLineDate, status);
        this.userId = userId;
    }

}
