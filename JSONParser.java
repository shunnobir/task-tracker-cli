import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class JSONParser {
    public static List<Map<String, String>> parse(String json) {
        /* This implements an "almost" safe JSON parser. We are ignoring syntax errors here. */
        List<Map<String, String>> entries = new ArrayList<>();

        json = fixJson(json);
        Pattern pattern = Pattern.compile("\\s*\\{(\\s*\"[^\"]+\"\\s*:\\s*\"[^\"]+\",?\\s*)+\\s*}\\s*,?");
        Matcher matcher = pattern.matcher(json);

        var entryStrings = matcher.results()
                .map(MatchResult::group)
                .toList();
        if (!entryStrings.isEmpty()) {
            pattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]+)\"");
            for (String entry: entryStrings) {
                matcher = pattern.matcher(entry);
                Map<String, String> keyVal = new HashMap<>();
                while (matcher.find()) {
                    var key = matcher.group(1);
                    var value = matcher.group(2);
                    keyVal.put(key, value);
                }
                entries.add(keyVal);
            }
        }

        return entries;
    }

    private static String fixJson(String json) {
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length()-1);
        if (!json.startsWith("{")) json = "{" + json;
        if (!json.endsWith("}")) json = json + "}";
        return json;
    }
}
