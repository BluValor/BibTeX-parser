package classes.entries;

import classes.BEntry;
import classes.fields.*;
import classes.fields.Number;

public class Inbook extends BEntry {
    public Author author;
    public Editor editor;
    public Title title;
    public Chapter chapter;
    public Pages pages;
    public Publisher publisher;
    public Year year;

    public Volume volume;
    public Number number;
    public Series series;
    public Type type;
    public Address address;
    public Edition edition;
    public Month month;
    public Note note;

    @Override
    public boolean hasAllNecessary() {
        return ((this.author != null ^ this.editor != null) && this.title != null
                && (this.chapter != null || this.pages != null) && this.publisher != null && this.year != null);
    }
}
