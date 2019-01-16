package classes;

import classes.fields.Author;
import classes.fields.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BFieldTest {

    @Test
    void getValue() {
        Note testField1 = new Note("test value 1");
        assertEquals("test value 1", testField1.getValue());
    }

    @Test
    void setValue() {
        Note testField1 = new Note("test value 0");
        testField1.setValue("test value 1");
        assertEquals("test value 1", testField1.getValue());
    }

    @Test
    void format() {
        Author testField1 = new Author("test test | value");
        testField1.format();
        assertEquals("test test", testField1.authors.getFirst().getKey());
        assertEquals("value", testField1.authors.getFirst().getValue());
    }

    @Test
    void cleanUp() {
        Author testField1 = new Author("test \" # test | value#");
        testField1.format();
        testField1.cleanUp();
        assertEquals("test test", testField1.authors.getFirst().getKey());
        assertEquals("value", testField1.authors.getFirst().getValue());
        
        Note testField2 = new Note(" \"  test  # value | 0");
        testField2.cleanUp();
        assertEquals("test value 0", testField2.getValue());
    }
}