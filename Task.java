import java.time.LocalDateTime;

public class Task {
    final int id;
    String description;
    TaskStatus status;
    final LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Task(int id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return String.format("|\t%d\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|", id, description, status, createdAt, updatedAt);
    }
}
