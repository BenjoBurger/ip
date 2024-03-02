package burger.TaskList;

import burger.BurgerException;

import java.util.ArrayList;

import static burger.UI.Utilities.*;

public class TaskList {

    private ArrayList<Task> taskList;

    public int totalTasks;

    public TaskList() {
        taskList = new ArrayList<>();
        totalTasks = 0;
    }
    public int getIdx(String[] textArray) {
        StringBuilder idx = new StringBuilder();
        for (int i = 1; i < textArray.length; i++) {
            idx.append(textArray[i]);
        }
        return Integer.parseInt(idx.toString()) - 1;
    }

    public void deleteTask(int idx) {
        try {
            if (idx < 0 || idx >= totalTasks) {
                throw new BurgerException("Out of Index");
            }
            System.out.println("Noted. I've removed this task:");
            taskList.get(idx).printTask();
            taskList.remove(idx);
            totalTasks--;
            System.out.println();
            printTotalTasks();
            printLine();
        } catch (BurgerException e) {
            printErrorMessage(e);
        }
    }

    /**
     * Returns the task at the specified index.
     * If the index is out of bounds, null is returned.
     *
     * @param idx index of task.
     * @return task.
     * @throws IndexOutOfBoundsException idx is out of bounds.
     */
    public Task getTask(int idx) {
        try {
            return taskList.get(idx);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Adds a new todo to the list.
     *
     * @param todo the todo input by the user.
     */
    public void addTodo(String[] todo) {
        StringBuilder todoText = new StringBuilder(todo[1]);
        for (int i = 2; i < todo.length; i++) {
            todoText.append(' ').append(todo[i]);
        }
        Task newTodo = new Todo(todoText.toString());
        addTask(newTodo);
    }

    /**
     * Adds a new event to the list.
     *
     * @param event the event input by the user.
     * @throws ArrayIndexOutOfBoundsException if deadline format is not followed.
     */
    public void addEvent(String[] event) {
        try {
            StringBuilder eventText = new StringBuilder(event[1]);
            int i = 2;
            while (!event[i].startsWith("/from")) { // appends the name of event task
                eventText.append(' ').append(event[i]);
                i++;
            }
            eventText.append(' ').append("(from: ");
            i++;
            while (!event[i].startsWith("/to")) {
                eventText.append(event[i]).append(' '); // appends the "from" timing
                i++;
            }
            eventText.append("to: ");
            i++;
            while (i < event.length - 1) {
                eventText.append(event[i]).append(' '); // appends the "to" timing
                i++;
            }
            eventText.append(event[i]).append(')');
            Task newEvent = new Event(eventText.toString());
            addTask(newEvent);
        } catch (ArrayIndexOutOfBoundsException e) {
            printInvalidEventInputMessage();
        }
    }

    /**
     * Adds a new deadline to the list.
     *
     * @param deadline the deadline input by the user.
     * @throws ArrayIndexOutOfBoundsException if deadline format is not followed.
     */
    public void addDeadline(String[] deadline) {
        try {
            StringBuilder deadlineText = new StringBuilder(deadline[1]);
            int i = 2;
            while (!deadline[i].startsWith("/")) { // appends the name of deadline task
                deadlineText.append(' ').append(deadline[i]);
                i++;
            }
            deadlineText.append(' ').append("(by: ");
            i++;
            while (i < deadline.length - 1) {
                deadlineText.append(deadline[i]).append(' '); // appends the "by" timing
                i++;
            }
            deadlineText.append(deadline[i]).append(')');
            Task newDeadline = new Deadline(deadlineText.toString());
            addTask(newDeadline);
        } catch (ArrayIndexOutOfBoundsException e) {
            printInvalidDeadlineInputMessage();
        }
    }

    /**
     * Adds task to the list and prints out a message.
     *
     * @param task Task that is being added.
     */
    public void addTask(Task task) {
        taskList.add(task);
        printLine();
        System.out.println("Got it. I've added this task:");
        taskList.get(totalTasks).printTask();
        totalTasks++;
        System.out.println();
        printTotalTasks();
        printLine();
    }

    private void printTotalTasks() {
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Returns the mark from a task in the list.
     *
     * @param idx the index of the task.
     * @param command Command to "mark" or "unmark" the task.
     */
    public void markTask(int idx, String command) {
        try {
            Task currTask = getTask(idx);
            if (currTask == null) {
                throw new BurgerException("Out of index");
            }
            if (command.equals("mark")) {
                System.out.println("Nice! I've marked this task as done:");
                currTask.markDone();
            } else {
                System.out.println("OK, I've marked this task as not done yet:");
                currTask.unmarkDone();
            }
            printTaskWithLine(idx);
        } catch (BurgerException e) {
            printErrorMessage(e);
        }
    }

    /**
     * Prints the task with a horizontal line.
     *
     * @param idx Index of the task.
     */
    public void printTaskWithLine(int idx) {
        taskList.get(idx).printTask();
        System.out.println();
        printLine();
    }

    /**
     * Prints task list.
     */
    public void printTaskList() {
        if (taskList.isEmpty()) {
            System.out.println("Don't be lazy... start doing something");
            printLine();
            return;
        }
        System.out.println("These are the tasks in your save file:");
        for (int i = 0; i < totalTasks; i++) {
            System.out.print(i+1 + ". ");
            taskList.get(i).printTask();
            System.out.println();
        }
        printLine();
    }

    public void add(Task currTask) {
        taskList.add(currTask);
    }
}