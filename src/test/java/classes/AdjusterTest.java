package classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjusterTest {

    @Test
    void adjustEntryName() {
        assertEquals("classes.entries.Test", Adjuster.adjustEntryName("teSt"));
    }

    @Test
    void adjustFieldName() {
        assertEquals("classes.fields.Test", Adjuster.adjustFieldName("teSt"));
    }

    @Test
    void getOnlyName() {
        assertEquals("Test", Adjuster.getOnlyName("classes.fields.Test"));
    }
}