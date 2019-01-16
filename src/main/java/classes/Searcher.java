package classes;

import classes.fields.Author;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/** performs specified searches and returns their results */
public class Searcher {

    /**
     * searches given list of entries for entries of classes specified in given list of classes
     *
     * @param entries {@code List<BEntry>} to search in
     * @param classes {@code List<Class>} to search for
     * @return {@code List<BEntry>}
     */
    public static List<BEntry> viaEntryType(List<BEntry> entries, List<Class> classes) {
        List<BEntry> result = new LinkedList<>();
        for (BEntry entry : entries) {
            for (Class eClass : classes) {
                if (entry.getClass() == eClass)
                    result.add(entry);
            }
        }
        return result;
    }

    /**
     * searches given list of last names for entries with author with last name like one
     * specified in given list of classes
     *
     * @param entries {@code List<BEntry>} to search in
     * @param lastNames {@code List<String>} to search for
     * @return {@code List<BEntry>}
     */
    public static List<BEntry> viaAuthorsLastNames(List<BEntry> entries, String[] lastNames) {
        List<BEntry> result = new LinkedList<>();
        for (BEntry entry : entries) {
            boolean added = false;
            Author value = (Author) entry.getField(Author.class);
            if (value != null) {
                for (String aSearched : lastNames) {
                    for (String author : value.getLastNames())
                        if (!added && aSearched.equals(author)) {
                            result.add(entry);
                            added = true;
                        }
                }
            }
        }
        return result;
    }


}
