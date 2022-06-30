package homework2;

import homework2.console.ConsoleHandler;
import homework2.data.CsvDataLoader;
import homework2.data.CvsDataWriter;
import homework2.data.FileType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Wrong number of arguments.");
        }

        try {
            CsvDataLoader.readData(args[0], FileType.USERSDATA);
        } catch (IOException e) {
            System.out.println("Wrong path of user data storage.");
        }

        try {
            CsvDataLoader.readData(args[1], FileType.TASKSDATA);
        } catch (IOException e) {
            System.out.println("Wrong path of task data storage.");
        }

        if(!ConsoleHandler.askForRequest()) return;

        try {
            CvsDataWriter.writeDataToFiles(args[0],args[1]);
        } catch (IOException e){
            System.out.println("Something went wrong during data saving...");
        }

    }

}

