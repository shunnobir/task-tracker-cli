import java.util.Arrays;

public class TaskTrackerCLI {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(TaskTracker.help());
            return;
        }

        TaskTracker tracker = new TaskTracker();
        switch (args[0]) {
            case "add" -> {
                var desc = processAddOperation(Arrays.copyOfRange(args, 1, args.length));
                if (!desc.isEmpty()) {
                    tracker.add(desc);
                }
            }
            case "list" -> tracker.list(Arrays.copyOfRange(args, 1, args.length));
            case "mark-in-progress", "mark-done" -> tracker.mark(args);
            case "update" -> tracker.update(Arrays.copyOfRange(args, 1, args.length));
            case "delete" -> tracker.delete(Arrays.copyOfRange(args, 1, args.length));
            default -> System.out.printf("error: unknown operation '%s'\n", args[0]);
        }
        tracker.save();
    }

    private static String processAddOperation(String ...args) {
        if (args.length < 1) {
            System.out.println("error: 'add' expects one argument; none was present");
            return "";
        }

        if (args.length > 1) {
            System.out.println("warning: extraneous arguments are ignored.");
        }

        return args[0];
    }
}
