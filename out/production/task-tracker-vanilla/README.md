# Task CLI (Java 21)

Simple Task Tracker CLI using only Java standard library.

## Build

```bash
./build.sh
```

## Usage

```bash
# add a task
java -cp out com.example.taskcli.Main add "Buy groceries"

# update
java -cp out com.example.taskcli.Main update 1 "Buy groceries and cook"

# delete
java -cp out com.example.taskcli.Main delete 1

# mark in progress
java -cp out com.example.taskcli.Main mark-in-progress 1

# mark done
java -cp out com.example.taskcli.Main mark-done 1

# list all
java -cp out com.example.taskcli.Main list

# list by status (todo, in-progress, done)
java -cp out com.example.taskcli.Main list todo
```

## Notes
- Stores tasks in `tasks.json` in current directory. Created automatically.
- No external libraries used.
- Contains a simple test harness (UnitTests and IntegrationTests).
```
```

---

## build.sh

```bash
#!/usr/bin/env bash
set -euo pipefail
mkdir -p out
javac -d out $(find src/main/java -name "*.java") $(find src/test/java -name "*.java")

echo "Build successful. Run tests: java -cp out com.example.taskcli.tests.UnitTests"
```

---

## run.sh

```bash
#!/usr/bin/env bash
set -euo pipefail
# wrapper to run Main with arguments
java -cp out com.example.taskcli.Main "$@"
```
