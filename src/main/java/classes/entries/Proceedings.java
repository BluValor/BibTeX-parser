package classes.entries;

import classes.BEntry;
import classes.fields.*;
import classes.fields.Number;


public class Proceedings extends BEntry {
    public Title title;
    public Year year;

    public Editor editor;
    public Volume volume;
    public Number number;
    public Series series;
    public Address address;
    public Publisher publisher;
    public Note note;
    public Month month;
    public Organization organization;

    @Override
    public boolean hasAllNecessary() {
        return (this.title != null && this.year != null);
    }
}
