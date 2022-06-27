package homework2.entity;

import java.time.LocalDate;

public class Task {
    int id;
    String header;
    String description;
    LocalDate deadLineDate;
    TaskStatus status;

    public Task(int id, String header, String description, LocalDate deadLineDate, TaskStatus status) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.deadLineDate = deadLineDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadLineDate() {
        return deadLineDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadLineDate(LocalDate deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public enum TaskStatus {
        NEW("новое"), INWORK("в работе"), DONE("готово");
        private final String russianCode;

        TaskStatus(String code) {
            this.russianCode = code;
        }

        public String getCode() {
            return russianCode;
        }
    }
}

