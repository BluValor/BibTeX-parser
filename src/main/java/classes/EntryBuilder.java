package classes;

import classes.entries.Article;
import classes.fields.*;

import java.lang.Number;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/** builds new BEntry objects */
public class EntryBuilder {

    public BEntry entry;
    public Class entryClass;

    /**
     * Constructs new EntryBuilder object with its fields
     *
     * @param entryClass class used for setting fields
     */
    public EntryBuilder(Class entryClass) {
        try {
            this.entry = (BEntry) entryClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.entryClass = entryClass;
    }

    /**
     * sets value of given fields in currently made BEntry object (if it contains field of that Field class)
     *
     * @param fieldClass Class of BField the value should be assigned to
     * @param fieldValue value that is gonna be assigned
     * @return EntryBuilderd which BEntry had possibly changed one of its BField values
     */
    public EntryBuilder setField(Class fieldClass, String fieldValue) {
        for (Field f : entryClass.getDeclaredFields()) {
            setClassIf(fieldClass, fieldValue, f);
        }
        for (Field f : entryClass.getSuperclass().getDeclaredFields()) {
            setClassIf(fieldClass, fieldValue, f);
        }
        return this;
    }

    /**
     * checks if field can be set and executes it
     *
     * @param fieldClass class of the field to set
     * @param fieldValue value to set
     * @param f object field which will have its value set
     */
    private void setClassIf(Class fieldClass, String fieldValue, Field f) {
        if (f.getType() == fieldClass) {
            try {
                BField newValue = new FieldBuilder(fieldClass, fieldValue).buildField();
                f.set(entry, newValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sets value of given fields in currently made BEntry object (if it contains field of that Field class)
     *
     * @param field field to assign to the entry
     * @return EntryBuilderd which BEntry had possibly changed one of its BField values
     */
    public EntryBuilder setField(BField field) {
        for (Field f : entryClass.getDeclaredFields()) {
            setClassIf(field, f);
        }
        for (Field f : entryClass.getSuperclass().getDeclaredFields()) {
            setClassIf(field, f);
        }
        return this;
    }

    /**
     * checks if field can be set and executes it
     *
     * @param field BField object to set to the field
     * @param f object field which will have its value set
     */
    private void setClassIf(BField field, Field f) {
        if (f.getType() == field.getClass()) {
            try {
                f.set(entry, field);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * returns built BEntry object
     *
     * @return value of its entry field
     */
    public BEntry buildEntry() {
        return this.entry;
    }

}
