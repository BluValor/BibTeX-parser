package classes.fields;

import classes.BField;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * extends BField, bus has additional {@code List<Pair<String, String>>} of different authors
 */
public class Editor extends BField {

    public LinkedList<Pair<String, String>> editors = null;

    public Editor(String value) {
        super(value);
    }

    /**
     * obtains firstName and lastName for each editor from this.value and adds it to this.authors list
     */
    @Override
    public void format() {
        this.editors = new LinkedList<>();
        if (this.value != null) {
            Pattern separator = Pattern.compile("([^|]+)\\s\\|\\s(\\S+)");
            Matcher separated = separator.matcher(this.value);
            while (separated.find()) {
                editors.add(new Pair<>(separated.group(1), separated.group(2)));
            }
        }
    }

    /**
     * cleans up all of its fields
     */
    @Override
    public void cleanUp() {
        super.cleanUp();
        for (int i = 0; i < this.editors.size(); i++) {
            Pair<String, String> editor = editors.poll();
            String pKey = editor.getKey().replace("#", "").replace("\"", "")
                    .replace("|", "").replaceAll("\\s+", " ")
                    .replace(" and ", "").replaceAll("^\\s", "");
            String pValue = editor.getValue().replace("#", "").replace("\"", "")
                    .replace("|", "").replaceAll("\\s+", " ")
                    .replaceAll("^\\s", "");
            editors.addLast(new Pair<>(pKey, pValue));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.value).append(": ");
        for (Pair<String, String> editor : editors)
            builder.append(System.lineSeparator()).append(" * first name: \"").append(editor.getKey())
                    .append("\", last name: \"").append(editor.getValue()).append("\"");
        return builder.toString();
    }
}
