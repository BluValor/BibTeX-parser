package classes.entries;

import classes.BEntry;
import classes.fields.*;
import classes.fields.Number;

public class Techreport extends BEntry {
    public Author author;
    public Title title;
    public Institution institution;
    public Year year;

    public Type type;
    public Number number;
    public Address address;
    public Month month;
    public Note note;

    @Override
    public boolean hasAllNecessary() {
        return (this.author != null && this.title != null && this.institution != null && this.year != null);
    }
}
