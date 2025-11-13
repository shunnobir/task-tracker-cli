# Task Tracker CLI

Simple Task Tracker CLI using only Java standard library.

## Build & Run

```bash
# compile everything
javac -d out *.java

# run the main method
java -cp out TaskTrackerCLI
```

## Usage

```bash
# add a task
java -cp out TaskTrackerCLI add "Buy groceries"
# Output: Task added successfully (ID: 1)

# list all
java -cp out TaskTrackerCLI list

# list by status (todo, in-progress, done)
java -cp out TaskTrackerCLI list todo

# mark task as in progress or done
java -cp out TaskTrackerCLI mark-in-progress 2
java -cp out TaskTrackerCLI mark-done 3

# update task description
java -cp out TaskTrackerCLI update 2 "Buy potatoes"

# delete task
java -cp out TaskTrackerCLI delete 1
```

## Notes
- Stores tasks in `tasks.json` in current directory. Created automatically.
- No external libraries used.
- Implements a minimal JSON parser using standard regex library
