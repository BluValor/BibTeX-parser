package classes;

import classes.fields.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldBuilderTest {

    @Test
    void buildField() {
        BField test = new FieldBuilder(Address.class, "test value").buildField();
        assertEquals("test value", test.getValue());
    }
}