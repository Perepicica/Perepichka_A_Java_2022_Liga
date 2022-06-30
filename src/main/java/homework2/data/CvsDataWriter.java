package homework2.data;

import homework2.entity.Task;

import java.io.*;

import static homework2.data.CsvDataLoader.formatter;


public class CvsDataWriter {

    public static void writeDataToFiles(String userFileName, String taskFileName) throws IOException {
        FileWriter userFileWriter = new FileWriter(userFileName);
        PrintWriter userWriter = new PrintWriter(userFileWriter);
        FileWriter taskFileWriter = new FileWriter(taskFileName);
        PrintWriter taskWriter = new PrintWriter(taskFileWriter);

        writeData(userWriter,taskWriter);

        userFileWriter.close();
        taskFileWriter.close();
    }

    private static void writeData(PrintWriter userWriter, PrintWriter taskWriter) {
        for (var entry : DataProvider.getUsers().entrySet()) {
            userWriter.printf("%d,%s\n",entry.getKey(),entry.getValue().getName());
            for (Task task : entry.getValue().getTasks()) {
                taskWriter.printf("%d,%s,%s,%d,%s,%s\n",
                        task.getId(),
                        task.getHeader(),
                        task.getDescription(),
                        entry.getKey(),
                        task.getDeadLineDate().format(formatter),
                        task.getStatus());
            }
        }
    }

}
