package classes.entries;

import classes.BEntry;
import classes.fields.*;
import classes.fields.Number;

public class Article extends BEntry {
    public Author author;
    public Title title;
    public Journal journal;
    public Year year;

    public Volume volume = null;
    public Number number = null;
    public Pages pages = null;
    public Month month = null;
    public Note note = null;

    @Override
    public boolean hasAllNecessary() {
        return (this.author != null && this.title != null && this.journal != null && this.year != null);
    }
}
