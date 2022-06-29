package homework2.data;

import homework2.entity.Task;
import homework2.entity.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CsvDataLoader {

    public final static String dateFormat = "d.MM.yyyy";
    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

    public static void readData(String fileName, FileType type) throws IOException {

        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        int pointer = 1;

        switch (type) {
            case USERSDATA -> {
                while ((line = bufferedReader.readLine()) != null) {
                    parseUserLine(line, pointer);
                    pointer++;
                }
            }
            case TASKSDATA -> {
                while ((line = bufferedReader.readLine()) != null) {
                    parseTaskLine(line, pointer);
                    pointer++;
                }
            }
        }

        bufferedReader.close();
        fileReader.close();
    }

    private static void parseUserLine(String line, int pointer) throws IllegalArgumentException {

        String[] lineParts = line.split(",");
        if (lineParts.length != 2 || lineParts[1].trim().length() == 0) {
            throw new IllegalArgumentException(
                    "Wrong data: user data storage, line " + pointer + " : " + "\"" + line + "\"");
        }
        int userId = parseId(lineParts[0], pointer, line);
        if (DataProvider.getUser(userId) != null) System.out.println("WARNING: Multiply USER ID : " + userId);
        DataProvider.addUser(userId, new User(lineParts[1].trim()));
    }

    private static void parseTaskLine(String line, int pointer) {

        String[] lineParts = line.split(",");
        if (lineParts.length != 5 || lineParts[1].trim().length() == 0 || lineParts[2].trim().length() == 0) {
            throw new IllegalArgumentException(
                    "Wrong data: task data storage, line " + pointer + " : " + "\"" + line + "\"");
        }
        int taskId = parseId(lineParts[0], pointer, line);
        int userId = parseId(lineParts[3], pointer, line);

        LocalDate deadLine;
        try {
            deadLine = LocalDate.parse(lineParts[4].trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Wrong data: task data storage, line " + pointer + " : " + "\"" + line + "\"");
        }

        if (DataProvider.getUser(userId) == null) {
            DataProvider.addTaskToUser(
                    userId, taskId, lineParts[1].trim(), lineParts[2].trim(), deadLine, Task.TaskStatus.NEW);
        }

        DataProvider.addTaskToUser(userId, taskId, lineParts[1].trim(), lineParts[2].trim(), deadLine, Task.TaskStatus.NEW);
    }

    private static int parseId(String idStr, int pointer, String line) {
        try {
            return Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Wrong id: Data storage, line " + pointer + " : " + "\"" + line + "\"");
        }
    }

}
