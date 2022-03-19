public class TaskByStart extends Task {

    public TaskByStart(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        int compare = Integer.compare(start, o.start);
        if (compare == 0) {
            compare = Integer.compare(deadline, o.deadline);
        }
        return compare;
    }
}
