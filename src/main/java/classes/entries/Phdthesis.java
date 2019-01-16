package classes.entries;

import classes.BEntry;
import classes.fields.*;

public class Phdthesis extends BEntry {
    public Author author;
    public Title title;
    public School school;
    public Year year;

    public Type type;
    public Address address;
    public Month month;
    public Note note;

    @Override
    public boolean hasAllNecessary() {
        return (this.author != null && this.title != null && this.school != null && this.year != null);
    }
}
