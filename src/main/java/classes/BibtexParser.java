package classes;

import classes.fields.*;
import classes.entries.*;
import javafx.util.Pair;
import classes.StringTokenizer;

import javax.management.BadAttributeValueExpException;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BibtexParser {

    /**
     * parses input to BEntry objects
     *
     * @return {@code List<BEntry>} of valid BEntry objects
     */
    public static List<BEntry> parse(String url) {
        List<BEntry> result = new LinkedList<>();
        List<Pair<String, String>> currentStrings = new LinkedList<>();
        String input = null;
        try {
            input = prepareInputText(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addEntries(result, input);
        addStrings(currentStrings, input);
        exeStrAdd(result, currentStrings);
        preCleanUpEntries(result);
        combineEntries(result);
        validateEntries(result);
        formatEntries(result);
        cleanUpEntries(result);
        return result;
    }



    public static String prepareInputText(String url) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(url)));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }
        String line = null;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) {
            result.append(line).append(System.lineSeparator());
        }
        return result.toString();
    }



    /**
     * invokes formatFields() method on all of given entries
     *
     * @param entries {@code List<BEntry>} of entries
     */
    private static void formatEntries(List<BEntry> entries) {
        for (BEntry e : entries) {
            e.formatFields();
        }
    }

    /**
     * formats all superclass fields of entries given in list
     *
     * @param entries {@code List<BEntry>} of BEntries to format
     */
    private static void preCleanUpEntries(List<BEntry> entries) {
        for (BEntry entry : entries) {
            entry.cleanUpSuperClassFields();
        }
    }

    /**
     * formats all except superclass fields of entries given in list
     *
     * @param entries {@code List<BEntry>} of BEntries to format
     */
    public static void cleanUpEntries(List<BEntry> entries) {
        for (BEntry entry : entries) {
            entry.cleanUpFields();
        }
    }

    /**
     * for each of the entries in the given list checks if it has all its necessary fields set. If not it is removed
     * from the list
     *
     * @param entries {@code List<BEntry>} of BEntries that are to be checked for correctness
     */
    public static void validateEntries(List<BEntry> entries) {
        for (int i = 0; i < entries.size(); i++) {
            if (!entries.get(i).hasAllNecessary()) {
                BEntry entry = entries.get(i);
                System.out.println("Entry \"" + Adjuster.getOnlyName(entry.getClass().getName()).toUpperCase() + ", "
                        + entry.getTag() + "\" does not have all necessary fields.");
                entries.remove(i--);
            }
        }
    }

    /**
     * executes the addition of the given strings to the given entries
     *
     * @param entries list of BEntries to loop through looking for patterns of string addition
     * @param strings {@code Pair<String, String>} of strings to add
     */
    public static void exeStrAdd(List<BEntry> entries, List<Pair<String,String>> strings) {
        Pattern hashLeft = Pattern.compile("(#\\s\\S+)");
        Pattern hashRigth = Pattern.compile("(\\S+\\s#)");
        for (BEntry entry : entries) {
            for (Field field : entry.getClass().getDeclaredFields()) {
                String fieldValue = null;
                BField tmpField = null;
                try {
                    tmpField = (BField) field.get(entry);
                    if (tmpField != null)
                        fieldValue = (String) tmpField.getValue();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (fieldValue != null) {
                    strAddWPattern(tmpField, fieldValue, strings, hashLeft);
                    strAddWPattern(tmpField, fieldValue, strings, hashRigth);
                }
            }
        }
    }

    /**
     * checks if the value of the given field is referencing to any of the given strings. If so, the reference is
     * replaced with the string its referencing to
     *
     * @param field field which may have its value changed
     * @param fieldValue current value of the field
     * @param strings {@code Pair<String, String>} of strings to check if any will be added
     * @param pattern currently used pattern for initial search for probable string references
     */
    public static void strAddWPattern(BField field, String fieldValue, List<Pair<String,String>> strings, Pattern pattern) {
        Pattern stringName = Pattern.compile("\\s?(([^#])+)\\s?");
        Matcher hashSearch = pattern.matcher(fieldValue);
        while (hashSearch.find()) {
            String strHashed = hashSearch.group(1);
            Matcher nameSearch = stringName.matcher((strHashed));
            if (nameSearch.find()) {
                String strName = nameSearch.group(1);
                for (Pair<String, String> p : strings) {
                    if (strName.equals(p.getKey())) {
                        String newValue = fieldValue;
                        newValue = newValue.replace(strName + " #", p.getValue());
                        newValue = newValue.replace("# " + strName, p.getValue());
                        field.setValue(newValue);
                    }
                }
            }
        }
    }

    /**
     * combines BEntries using their Crossref values:
     * If Crossref value one one BEntry is equal to Tag of the other, it inherits all
     * not possesed fields from the other one
     *
     * @param entries {@code List} of current entries
     */
    public static void combineEntries(List<BEntry> entries) {
        boolean smthChanged = true;
        while (smthChanged) {
            smthChanged = false;
            for (BEntry entry : entries) {
                if (entry.getCrossref() != null) {
                    for (BEntry reff : entries) {
                        if (((String) entry.getCrossref().getValue()).equalsIgnoreCase((String) reff.getTag().getValue())) {
                            boolean tmp = entry.inheritFields(reff);
                            if (!smthChanged)
                                smthChanged = tmp;
                        }
                    }
                }
            }
        }
    }

    /**
     * adds encountered in the input strings to the {@code List<Pair<String, String>>}
     *
     * @param strings list of currently encountered strings
     * @param input input text in one String
     */
    public static void addStrings(List<Pair<String, String>> strings, String input) {
        StringTokenizer args = new StringTokenizer(input);
        while (args.hasMoreTokens())
            strings.add(args.nextToken());
    }

    /**
     * adds encountered in the input entries to the {@code List<BEntry>}
     *
     * @param entries list of currently encountered entires
     * @param input input text in one String
     */
    public static void addEntries(List<BEntry> entries, String input) {
        EntryTokenizer args = new EntryTokenizer(input);
        while (args.hasMoreTokens())
            entries.add(parseEntry(args.nextToken()));
    }

    /**
     * parses raw entry to BEntry
     *
     * @param pair arguments used in the making of BEntry object
     * @return BEntry object
     */
    public static BEntry parseEntry(Pair<String, LinkedList<String>> pair) {
        Class entryClass = null;
        if (pair.getKey().equalsIgnoreCase("preamble") || pair.getKey().equalsIgnoreCase("comment"))
            return null;
        try {
            entryClass = Class.forName(Adjuster.adjustEntryName(pair.getKey()));
        } catch (ClassNotFoundException e) {
            System.out.println("Entry for name: " + pair.getKey() + " not found.");
            return null;
        }
        LinkedList<String> args = pair.getValue();
        EntryBuilder result = new EntryBuilder(entryClass).setField(Tag.class, args.poll());
        while (args.size() >= 2) {
            BField toCheck = parseField(args.poll(), args.poll());
            if (toCheck != null)
                result.setField(toCheck);
        }
        return result.buildEntry();
    }

    /**
     * parses raw field to BField
     *
     * @param name name of the field
     * @param value vale assigned to the field
     * @return BField object
     */
    public static BField parseField(String name, String value) {
        Class className = null;
        try {
            className = Class.forName(Adjuster.adjustFieldName(name));
        } catch (ClassNotFoundException e) {
//            System.out.println("Field for name: " + name + " not found.");
            return null;
        }
        return new FieldBuilder(className, value).buildField();
    }
}