package classes;

public class Adjuster {

    /**
     * adjusts obtained BEntry Class name so it can be found by {@code Class.forName()} function
     *
     * @param input entry name
     * @return adjusted entry name
     */
    public static String adjustEntryName(String input) {
        return ("classes.entries." + input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase());
    }

    /**
     * adjusts obtained BField Class name so it can be found by {@code Class.forName()} function
     *
     * @param input field name
     * @return adjusted field name
     */
    public static String adjustFieldName(String input) {
        return "classes.fields." + input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String getOnlyName(String className) {
        return className.substring(className.lastIndexOf('.') + 1);
    }
}
