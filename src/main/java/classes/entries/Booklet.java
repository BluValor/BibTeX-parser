package classes.entries;

import classes.BEntry;
import classes.fields.*;

public class Booklet extends BEntry {
    public Title title;

    public Author author;
    public Howpublished howpublished;
    public Address address;
    public Month month;
    public Year year;
    public Note note;

    @Override
    public boolean hasAllNecessary() {
        return (this.title != null);
    }
}
