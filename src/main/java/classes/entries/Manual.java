package classes.entries;

import classes.BEntry;
import classes.fields.*;

public class Manual extends BEntry {
    public Title title;

    public Author author;
    public Organization organization;
    public Address address;
    public Edition edition;
    public Month month;
    public Year year;
    public Note note;

    @Override
    public boolean hasAllNecessary() {
        return (this.title != null);
    }
}
