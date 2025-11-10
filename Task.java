import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Task {
    public final long id;
    public String description;
    public TaskStatus status;
    public final LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Task(long id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Task fromMap(Map<String, String> entry) throws RuntimeException {
        var id = entry.get("id");
        var desc = entry.get("description");
        var status = entry.get("status");
        var createdAt = entry.get("createdAt");
        var updatedAt = entry.get("updatedAt");

        try {
            return new Task(Long.parseLong(id.strip()),
                              desc.strip(),
                              TaskStatus.valueOf(status.strip().toUpperCase()),
                              LocalDateTime.parse(createdAt.strip(), dateTimeFormatter),
                              LocalDateTime.parse(updatedAt.strip(), dateTimeFormatter));
        } catch (Exception e) {
            throw new RuntimeException("error: invalid json format");
        }
    }

    @Override
    public String toString() {
        var outFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return String.format("(%3d, %-35s, %-12s, %-12s, %-12s)", id,
                description,
                status,
                createdAt.format(outFormat),
                updatedAt.format(outFormat));
    }

    public String toJson() {
        return String.format("{ \"id\": \"%d\", \"description\": \"%s\", " +
                "\"status\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": \"%s\" }",
                id,
                description,
                status,
                createdAt.format(dateTimeFormatter),
                updatedAt.format(dateTimeFormatter));
    }
}
