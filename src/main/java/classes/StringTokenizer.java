package classes;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTokenizer {

    //key - name, value - value
    public LinkedList<Pair<String, String>> tokens;

    public StringTokenizer() {
        this.tokens = new LinkedList<>();
    }

    public StringTokenizer(String input) {
        this();
        String[] elements = input.split("@");
        Pattern stringPattern = Pattern.compile("string\\{((\\w*-?)+)\\s=\\s(.*)\\}", Pattern.CASE_INSENSITIVE);
        for (String s : elements) {
            Matcher stringMatcher = stringPattern.matcher(s);
            while (stringMatcher.find()) {
                tokens.add(new Pair<>(stringMatcher.group(1), stringMatcher.group(3)));
            }
        }
    }

    public Pair<String, String> nextToken() {
        return this.tokens.poll();
    }

    public boolean hasMoreTokens() {
        return !this.tokens.isEmpty();
    }
}
