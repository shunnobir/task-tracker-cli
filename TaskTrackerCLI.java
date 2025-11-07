import java.util.Arrays;

public class TaskTrackerCLI {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(TaskTracker.help());
            return;
        }

        TaskTracker tracker = new TaskTracker();
        if (args[0].equals("add")) {
            var desc = processAddOperation(Arrays.copyOfRange(args, 1, args.length));
            if (!desc.isEmpty()) {
                tracker.add(desc);
            }
        } else if (args[0].equals("list")) {
            tracker.list(args);
        } else {
            System.out.printf("error: unknown operation '%s'\n", args[0]);
        }
    }

    private static String processAddOperation(String ...args) {
        if (args.length < 1) {
            System.out.println("'add' operation only expects a single argument. Invalid add operation!");
            return "";
        }

        if (args.length > 1) {
            System.out.println("warning: extraneous arguments are ignored.");
        }

        return args[0];
    }
}
