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
java -cp out com.example.taskcli.Main add "Buy groceries"

# list all
java -cp out com.example.taskcli.Main list

# list by status (todo, in-progress, done)
java -cp out com.example.taskcli.Main list todo
```

## Notes
- Stores tasks in `task.json` in current directory. Created automatically.
- No external libraries used.
- Implements a minimal JSON parser using standard regex library
