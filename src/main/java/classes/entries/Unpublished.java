package classes.entries;

import classes.BEntry;
import classes.fields.*;

public class Unpublished extends BEntry {
    public Author author;
    public Title title;
    public Note note;

    public Month month;
    public Year year;

    @Override
    public boolean hasAllNecessary() {
        return (this.author != null && this.title != null && this.note != null);
    }
}
