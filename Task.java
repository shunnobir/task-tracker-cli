import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    public final int id;
    public String description;
    public TaskStatus status;
    public final LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Task(int id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Task fromJson(String json) throws RuntimeException {
        json = json.replace("{", "").replace("}", "").replace("\"", "");
        var fields = json.split(",");
        var id = fields[0].split(":")[1];
        var desc = fields[1].split(":")[1];
        var status = fields[2].split(":")[1];
        var createdAt = fields[3].split("[a-zA-Z]:")[1];
        var updatedAt = fields[4].split("[a-zA-Z]:")[1];

        try {
            return new Task(Integer.parseInt(id.strip()),
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
