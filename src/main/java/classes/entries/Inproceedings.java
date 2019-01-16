package classes.entries;

import classes.BEntry;
import classes.fields.*;
import classes.fields.Number;

public class Inproceedings extends BEntry {
    public Author author;
    public Title title;
    public Booktitle booktitle;
    public Year year;

    public Editor editor;
    public Volume volume;
    public Number number;
    public Series series;
    public Pages pages;
    public Address address;
    public Month month;
    public Organization organization;
    public Publisher publisher;
    public Note note;

    @Override
    public boolean hasAllNecessary() {
        return (this.author != null && this.title != null && this.booktitle != null && this.year != null);
    }
}
