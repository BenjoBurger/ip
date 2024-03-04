package burger.Storage;

import burger.TaskList.Task;
import burger.TaskList.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class BurgerFileClass {
    static final int TDEINDEX = 0;
    static final int MARKINDEX = 1;

    static final int TASKINDEX = 2;

    public static final String DEFAULT_PATHNAME = java.nio.file.Paths.get("data","burger.txt")
            .normalize().toString();
    private static String pathName;

    public BurgerFileClass() {
        pathName = DEFAULT_PATHNAME;
    }

    public BurgerFileClass(String filePath) {
        pathName = filePath;
    }

    public static void getSaveFile(TaskList newList) {
        try {
            readFromFile(newList);
            System.out.println("File is found!");
            System.out.println("Current List: ");
            newList.printTaskList();
        } catch (IOException e) {
            System.out.println("File is not found! But we will create one for you!");
        }
    }

    public static void readFromFile(TaskList list) throws IOException {
        Files.createDirectories(Paths.get("data"));
        File f = new File(pathName); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        while (s.hasNext()) {
            String[] currLineArray = s.nextLine().split("\\|");
            char currTDE = currLineArray[TDEINDEX].charAt(0);
            char currMark = currLineArray[MARKINDEX].charAt(0);
            String currTask = currLineArray[TASKINDEX];
            addFromSaveFile(currTDE, currMark, currTask, list);
            list.totalTasks++;
        }
    }

    public static void addFromSaveFile(char tde, char mark, String task, TaskList list) {
        Task currTask = new Task(task, tde);
        if (mark == 'X') {
            currTask.markDone();
        }
        list.add(currTask);
    }

    public void setSaveFile(TaskList newList) {
        System.out.print("Saving file");
        try {
            FileWriter fw = new FileWriter(pathName);
            int i = 0;
            String textToWrite;
            while (i < newList.totalTasks) {
                textToWrite = newList.getTask(i).getTDE() + "|" + newList.getTask(i).getTick() + "|"
                        + newList.getTask(i).getName();
                fw.write(textToWrite + System.lineSeparator());
                System.out.print(".");
                i++;
            }
            fw.close();
            System.out.println();
            System.out.println("File successfully saved!");
        } catch (IOException e) {
            System.out.println();
            System.out.println("OH NO! File not saved!");
        }
    }
}
