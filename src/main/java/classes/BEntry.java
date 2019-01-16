package classes;

import classes.fields.Crossref;
import classes.fields.Tag;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/** Stores data from BibTeX entries */
public abstract class BEntry {

    public Tag tag;
    public Crossref crossref;

    public BEntry() {
    }

    /**
     * determins if BEntry ahs all of its necessary fields
     *
     * @return true if all necessary fields ale filled, otherwise false
     */
    public boolean hasAllNecessary() {
        return true;
    }

    /**
     * Inherits nto possesed fields from given BEntry
     *
     * @param referenced BEntry to inherit fields from
     * @return true if any fields were inherited, false otherwise
     */
    public boolean inheritFields(BEntry referenced) {
        boolean returnValue = false;
        for (Field f : this.getClass().getDeclaredFields()) {
            Object check = null;
            try {
                check = f.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (check == null) {
                Object value = referenced.getField(f.getType());
                if (value != null) {
                    try {
                        f.set(this, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    returnValue = true;
                }
            }
        }
        return returnValue;
    }

    /**
     * returns its BField of a given class
     *
     * @param fieldClass class of BField to return
     * @return BField object or null if no BField ot given class is owned
     */
    public Object getField(Class fieldClass) {
        for (Field f : this.getClass().getDeclaredFields()) {
            Object returnValue = null;
            if (f.getType() == fieldClass) {
                try {
                    returnValue = f.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return returnValue;
            }
        }
        return null;
    }

    /**
     * sets value of given fields in this BEntry object (if it contains field of that Field class)
     *
     * @param fieldClass class of the field supposed to be set
     * @param fieldValue vale
     */
    public void setField(Class fieldClass, Object fieldValue) {
        for (Field f : this.getClass().getDeclaredFields()) {
            setFieldIf(fieldClass, fieldValue, f);
        }
        for (Field f : this.getClass().getSuperclass().getDeclaredFields()) {
            setFieldIf(fieldClass, fieldValue, f);
        }
    }
    /**
     * checks if field can be set and executes it
     *
     * @param fieldClass class of the field to set
     * @param fieldValue value to set
     * @param f object field which will have its value set
     */
    public void setFieldIf(Class fieldClass, Object fieldValue, Field f) {
        if (f.getType() == fieldClass) {
            try {
                BField newValue = null;
                try {
                    newValue = (BField) fieldClass.getConstructors()[0].newInstance(fieldValue);
                } catch (InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                f.set(this, newValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * invokes format() method on all of its fields except its supercalss fields
     */
    public void formatFields() {
        for (Field f : this.getClass().getDeclaredFields()) {
            BField field = null;
            try {
                field = (BField) f.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (field != null) {
                field.format();
            }
        }
    }

    /**
     * invokes format() method on all of its superclass fields
     */
    public void formatSuperClassFields() {
        for (Field f : this.getClass().getSuperclass().getDeclaredFields()) {
            BField field = null;
            try {
                field = (BField) f.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (field != null) {
                field.format();
            }
        }
    }

    /**
     * invokes cleanUp() method on all of its fields except its superclass fields
     */
    public void cleanUpFields() {
        for (Field field : this.getClass().getDeclaredFields()) {
            BField tmpField = null;
            try {
                tmpField = (BField) field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (tmpField != null && tmpField.getValue() != null) {
                tmpField.cleanUp();
            }
        }
    }

    /**
     * invokes cleanUp() method on all of its superclass fields
     */
    public void cleanUpSuperClassFields() {
        for (Field field : this.getClass().getSuperclass().getDeclaredFields()) {
            BField tmpField = null;
            try {
                tmpField = (BField) field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (tmpField != null && tmpField.getValue() != null) {
                tmpField.cleanUp();
            }
        }
    }

    /* total wideBand.length() - 103 in:  4 - 15 - 6 - fill - 4 */
    /**
     * @return all information about BEntry in form of String
     */
    @Override
    public String toString() {
        String wideBand = ":|>-------------------------------------------------------------------------------------------------<|:";
        String leftBand = ":|  ";
        String rightBand = "  |:";
        String middleBand = "  ->  ";

        StringBuilder result = new StringBuilder();
        result.append(wideBand).append(System.lineSeparator()).append(leftBand)
                .append(Adjuster.getOnlyName(this.getClass().getName()).toUpperCase()).append(" ").append("(")
                .append(this.tag).append(")")
                .append(fillWhiteSpaces(wideBand.length(),
                        11 + Adjuster.getOnlyName(this.getClass().getName()).length() + ((String) this.tag.getValue()).length()))
                .append(rightBand).append(System.lineSeparator()).append(wideBand).append(System.lineSeparator());

        for (Field f : this.getClass().getSuperclass().getDeclaredFields()) {
            BField field = null;
            try {
                field = (BField) f.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (field != null && field.getClass() != Tag.class) {
                String fieldName = Adjuster.getOnlyName(field.getClass().getName());
                String fieldValue = field.toString();
                result.append(leftBand).append(fieldName).append(fillWhiteSpaces(15, fieldName.length()))
                        .append(middleBand).append(fieldValue)
                        .append(fillWhiteSpaces(wideBand.length() - 29, fieldValue.length())).append(rightBand)
                        .append(System.lineSeparator()).append(wideBand).append(System.lineSeparator());
            }
        }

        for (Field f : this.getClass().getDeclaredFields()) {
            BField field = null;
            try {
                field = (BField) f.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (field != null) {
                String fieldName = Adjuster.getOnlyName(field.getClass().getName());
                String fieldValue = field.toString();
                result.append(leftBand).append(fieldName).append(fillWhiteSpaces(15, fieldName.length()))
                        .append(middleBand).append(manage(wideBand, leftBand, rightBand, middleBand, fieldValue));
            }
        }
        result.append(System.lineSeparator());
        return result.toString();
    }

    private String manage (String wideBand, String leftBand, String rightBand, String middleBand, String value) {
        String[] lines = value.split(System.lineSeparator());
        StringBuilder result = new StringBuilder();
        result.append(lines[0]).append(fillWhiteSpaces(wideBand.length() - 29, lines[0].length())).append(rightBand)
                .append(System.lineSeparator());
        for (int i = 1; i < lines.length; i++) {
            result.append(leftBand).append(fillWhiteSpaces(15, 0)).append(middleBand)
                    .append(lines[i]).append(fillWhiteSpaces(wideBand.length() - 29, lines[i].length()))
                    .append(rightBand).append(System.lineSeparator());
        }
        result.append(wideBand).append(System.lineSeparator());
        return result.toString();
    }

    private String fillWhiteSpaces (int width, int length) {
        StringBuilder whiteSpaces = new StringBuilder();
        for (int i = 0; i < width - length; i++)
            whiteSpaces.append(" ");
        return whiteSpaces.toString();
    };

    /**
     * @return tag value of this BEntry object
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * @return crossref value of this BEntry object
     */
    public Crossref getCrossref() {
        return crossref;
    }
}
