package classes.fields;

import classes.BField;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Author extends BField {

    public LinkedList<Pair<String, String>> authors = null;

    public Author(String value) {
        super(value);
    }

    /**
     * obtains firstName and lastName for each author from this.value and adds it to this.authors list
     */
    @Override
    public void format() {
        this.authors = new LinkedList<>();
        if (this.value != null) {
            Pattern separator = Pattern.compile("([^|]+)\\s\\|\\s(\\S+)");
            Matcher separated = separator.matcher(this.value);
            while (separated.find()) {
                authors.add(new Pair<>(separated.group(1), separated.group(2)));
            }
        }
    }

    /**
     * cleans up all of its fields
     */
    @Override
    public void cleanUp() {
        super.cleanUp();
        for (int i = 0; i < this.authors.size(); i++) {
            Pair<String, String> author = authors.poll();
            String pKey = author.getKey().replace("#", "").replace("\"", "")
                    .replace("|", "").replaceAll("\\s+", " ")
                    .replace(" and ", "").replaceAll("^\\s", "");
            String pValue = author.getValue().replace("#", "").replace("\"", "")
                    .replace("|", "").replaceAll("\\s+", " ")
                    .replaceAll("^\\s", "");
            authors.addLast(new Pair<>(pKey, pValue));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.value).append(": ");
        for (Pair<String, String> editor : authors)
            builder.append(System.lineSeparator()).append(" * first name: \"").append(editor.getKey())
                    .append("\", last name: \"").append(editor.getValue()).append("\"");
        return builder.toString();
    }

    /**
     * @return {@code LinkedList<String>} of authors last names from this.authors
     */
    public LinkedList<String> getLastNames() {
        LinkedList<String> result = new LinkedList<>();
        for (Pair<String, String> author : authors)
            result.add(author.getValue());
        return result;
    }

}
