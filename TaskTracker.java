import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class TaskTracker {
    private final String taskFile = "tasks.json";
    private static long taskId = 1;
    private final List<Task> tasks;

    public TaskTracker() {
        tasks = new ArrayList<>();
        load();
    }

    public void add(String description) {
        try {
            var id = getId();
            tasks.add(new Task(id, description, TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now()));
            System.out.printf("Task added successfully (ID: %d)\n", id);
        } catch (Exception e) {
            revertId();
            System.out.println("error: task could not be added. Operation failed!");
        }
    }

    public void add(Task task) {
        Optional<Task> findTask = tasks.stream()
                .filter(t -> t.id == task.id)
                .findFirst();
        if (findTask.isPresent()) {
            return;
        }

        tasks.add(task);
        taskId = Math.max(taskId, task.id) + 1;
    }

    public void list(String ...args) {
        var filters = List.of("todo", "in-progress", "done");
        var filteredTasks = tasks;
        if (args.length > 0) {
            if (args.length != 1) {
                System.out.println("warning: extraneous arguments are ignored");
            }
            if (filters.contains(args[0])) {
                filteredTasks = tasks.stream()
                        .filter(t -> t.status.toString().equals(args[0]))
                        .toList();
            }
        }

        if (filteredTasks.isEmpty()) {
            System.out.println("* no tasks found *");
            return;
        }

        System.out.printf("(%3s, %-35s, %-12s, %-12s, %-12s)\n", "id", "description", "status", "created at", "updated at");
        for (Task task: filteredTasks) {
            System.out.println(task);
        }
    }

    public void mark(String ...args) {
        if (args.length < 2) {
            System.out.printf("error: expected two arguments, found %d\n", args.length);
            return;
        }

        if (args.length > 2) {
            System.out.println("warning: extraneous arguments are ignored");
        }

        try {
            var id = Long.parseLong(args[1]);
            var filteredTask = tasks.stream().filter(t -> t.id == id).findFirst();
            if (filteredTask.isEmpty()) {
                System.out.println("error: invalid task id; task not found");
                return;
            }
            var task = filteredTask.get();
            var status = args[0];
            switch (status) {
                case "mark-in-progress" -> task.status = TaskStatus.IN_PROGRESS;
                case "mark-done" -> task.status = TaskStatus.DONE;
                default -> System.out.println("error: invalid status");
            }
        } catch (Exception e) {
            System.out.println("error: could not filter task");
        }
    }

    private long getId() {
        return taskId++;
    }

    private void revertId() {
        --taskId;
    }

    /* DONE: implemented a minimal json parser */
    private void load() {
        final Path p = Path.of(taskFile);
        if (!Files.exists(p)) {
            return;
        }

        try {
            String content = Files.readString(p);
            List<Map<String, String>> entries = JSONParser.parse(content);
            for (var entry: entries) {
                try {
                    add(Task.fromMap(entry));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    tasks.clear();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("error: could not read file 'tasks.json'");
        }
    }

    public void save() {
        if (tasks.isEmpty()) {
            return;
        }

        final Path p = Path.of(taskFile);
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (Task task: tasks) {
            sb.append(task.toJson(1))
                    .append(",\n");
        }
        sb.delete(sb.length()-2, sb.length()); /* delete the last comma */
        sb.append("\n]");

        try {
            Files.writeString(p, sb.toString());
        } catch (IOException e) {
            System.out.println("error: could not save tasks; logging all tasks!");
            for (Task task: tasks) System.out.println(task);
        }
    }

    public static String help() {
        return "Usage: TaskTrackerCLI {command} [arguments]";
    }
}
