import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
//        simpleQueueTest();
        scheduleTasks("taskset1.txt");
        scheduleTasks("taskset2.txt");
        scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
        scheduleTasks("taskset5.txt");
    }

    public static void scheduleTasks(String taskFile) {
        ArrayList<Task> tasksByDeadline = new ArrayList<>();
        ArrayList<Task> tasksByStart = new ArrayList<>();
        ArrayList<Task> tasksByDuration = new ArrayList<>();

        readTasks(taskFile, tasksByDeadline, tasksByStart, tasksByDuration);

        schedule("Deadline Priority : "+ taskFile, tasksByDeadline);
        schedule("Startime Priority : " + taskFile, tasksByStart);
        schedule("Duration Priority : " + taskFile, tasksByDuration);
    }

    public static void simpleQueueTest() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.enqueue(9);
        queue.enqueue(7);
        queue.enqueue(5);
        queue.enqueue(3);
        queue.enqueue(1);
        queue.enqueue(10);
        queue.enqueue(11);
        queue.enqueue(12);
        queue.enqueue(13);
        queue.enqueue(14);
        queue.enqueue(15);


        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }

    /**
     * Reads the task data from a file, and creates the three different sets of tasks for each
     */
    public static void readTasks(String filename,
                                 ArrayList<Task> tasksByDeadline,
                                 ArrayList<Task> tasksByStart,
                                 ArrayList<Task> tasksByDuration) {
        File taskFile = new File(filename);
        int counter = 0;

        try (Scanner input = new Scanner(taskFile)) {
            while (input.hasNextLine()) {
                counter += 1;
                int start = input.nextInt();
                int deadline = input.nextInt();
                int duration = input.nextInt();
                Task startTask = new TaskByStart(counter, start, deadline, duration);
                Task deadlineTask = new TaskByDeadline(counter, start, deadline, duration);
                Task durationTask = new TaskByDuration(counter, start, deadline, duration);
                tasksByStart.add(startTask);
                tasksByDeadline.add(deadlineTask);
                tasksByDuration.add(durationTask);
            }
        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the tasks: " + ex);
        }
    }

    /**
     * Given a set of tasks, schedules them and reports the scheduling results
     */
    public static void schedule(String label, ArrayList<Task> tasks) {
        PriorityQueue<Task> queue = new PriorityQueue<>();
        int clock = 1;
        int tasksLate = 0;
        int lateTime = 0;

        System.out.println(label);

        for (Task task : tasks) {
            if (task.start == clock) {
                queue.enqueue(task);
            }
        }

        while (!queue.isEmpty() || !tasks.isEmpty()) {
            if (!queue.isEmpty()) {
                Task currTask = queue.dequeue();
                currTask.duration -= 1;
                System.out.printf("Time %d: %s ", clock, currTask);

                if (currTask.duration != 0) {
                    System.out.println();
                    queue.enqueue(currTask);
                } else {
                    tasks.remove(currTask);
                    System.out.print("** ");

                    if (currTask.deadline < clock) {
                        lateTime += clock - currTask.deadline;
                        tasksLate += 1;
                        System.out.printf("Late %d\n", (clock - currTask.deadline));
                    } else {
                        System.out.println();
                    }
                }
            } else {
                System.out.printf("Time %d: --- \n", clock);
            }

            clock += 1;
            for (Task task : tasks) {
                if (task.start == clock) {
                    queue.enqueue(task);
                }
            }
        }

        System.out.printf("Tasks late %d Total Late %d\n\n", tasksLate, lateTime);
    }
}
