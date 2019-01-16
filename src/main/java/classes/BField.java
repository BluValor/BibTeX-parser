package classes;

/* Dlaczego dla pola author " | " zamiast " , "? */

/** Stores data from BibTeX fields */
public abstract class BField {

    protected String value;

    public BField() {
    }

    public BField(String value) {
        this.value = value;
    }

    /**
     * returns teh value of this.value field
     *
     * @return this.value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * sets value of this.value field
     *
     * @param value new value to be set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * method to be overridden by specific BFields. May perform additional actions using its this.value field
     */
    public void format() {
    }

    /**
     * cleans up its value field
     */
    public void cleanUp() {
        this.setValue(((String) this.getValue()).replace("#", "")
                .replace("\"", "").replace("|", "")
                .replaceAll("\\s+", " ").replaceAll("^\\s", ""));
    }

    @Override
    public String toString() {
        return this.value;
    }
}
