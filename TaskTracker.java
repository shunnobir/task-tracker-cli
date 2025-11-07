import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class TaskTracker {
    private static int taskId = 1;
    private final List<Task> tasks;

    public TaskTracker() {
        tasks = new ArrayList<Task>();
    }

    public void add(String description) {
        try {
            var id = getId();
            tasks.add(new Task(id, description, TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now()));
            System.out.printf("Task added successfully (ID: %d)\n", id);
        } catch (Exception e) {
            System.out.println("error: task could not be added. Operation failed!");
        }
    }

    public void list(String ...args) {
        var filters = List.of("todo", "in-progress", "done");
        var filteredTasks = tasks;
        if (args.length > 0 && args.length != 1) {
            System.out.println("warning: extraneous argument ignored.");
            if (filters.contains(args[0])) {
                filteredTasks = tasks.stream()
                        .filter(t -> t.status.toString().equals(args[0]))
                        .toList();
            }
        }

        if (filteredTasks.isEmpty()) {
            System.out.println("* no tasks to show *");
            return;
        }

        for (Task task: filteredTasks) {
            System.out.println(task);
        }
    }

    private int getId() {
        return taskId++;
    }

    private int revertId() {
        return --taskId;
    }

    public static String help() {
        return "Usage: TaskTrackerCLI {command} [arguments]";
    }
}
