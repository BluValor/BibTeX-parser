package classes.entries;

import classes.BEntry;
import classes.fields.*;
import classes.fields.Number;

public class Incollection extends BEntry {
    public Author author;
    public Title title;
    public Booktitle booktitle;
    public Publisher publisher;
    public Year year;

    public Editor editor;
    public Volume volume;
    public Number number;
    public Series series;
    public Type type;
    public Chapter chapter;
    public Pages pages;
    public Address address;
    public Edition edition;
    public Month month;
    public Note note;

    @Override
    public boolean hasAllNecessary() {
        return (this.author != null && this.title != null && this.booktitle != null && this.publisher != null
                && this.year != null);
    }
}
