package classes;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** divides input into components (valide ntry names and their valid values) */
public class EntryTokenizer {

    // key - name, 1 - tag, 2n - field name, 2n+1 - field value
    private LinkedList<Pair<String, LinkedList<String>>> tokens;

    public EntryTokenizer() {
        this.tokens = new LinkedList<>();
    }

    /**
     * divides input into components and put them into {@code List} tokens
     *
     * @param input raw input String
     */
    public EntryTokenizer(String input) {
        this();
        String[] elements = input.split("@");
        Pattern startPattern = Pattern.compile("^(\\w*)\\{((\\w*-?)+),");
        Pattern fieldPattern = Pattern.compile("(\\w*)\\s=(((\"[^\"]+\")|(\\w*)\\s*#?\\s*)*)((,)|(\\s*}))");
        for (String s : elements) {
            Matcher startMatcher = startPattern.matcher(s);
            while (startMatcher.find()) {
                LinkedList<String> tmp = new LinkedList<>();
                tmp.add(startMatcher.group(2));
                Matcher fieldMatcher = fieldPattern.matcher(s);
                while (fieldMatcher.find()) {
                    tmp.add(fieldMatcher.group(1));
                    tmp.add(fieldMatcher.group(2));
                }
                tokens.add(new Pair<>(startMatcher.group(1), tmp));
            }

        }
    }

    public Pair<String, LinkedList<String>> nextToken() {
        return this.tokens.poll();
    }

    public boolean hasMoreTokens() {
        return !this.tokens.isEmpty();
    }
}
