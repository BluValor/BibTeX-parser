package classes;

import java.lang.reflect.InvocationTargetException;

/** builds new BField objects */
public class FieldBuilder {

    public BField field;

    /**
     * Constructs new FieldBuilder object with BField field with its value set
     *
     * @param fieldClass class of BFiled
     * @param fieldValue value for BField constructor
     */
    public FieldBuilder(Class fieldClass, String fieldValue) {
        try {
            this.field = (BField) fieldClass.getConstructors()[0].newInstance(fieldValue);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns built BField object
     *
     * @return value of its field field
     */
    public BField buildField() {
        return this.field;
    }
}
