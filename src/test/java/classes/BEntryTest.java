package classes;

import classes.entries.Article;
import classes.entries.Book;
import classes.entries.Misc;
import classes.fields.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

class BEntryTest {

    @Test
    void hasAllNecessary() {
        Article testEntry1 = (Article) new EntryBuilder(Article.class)
                .setField(Author.class, "test value 1")
                .setField(Title.class, "test value 2")
                .setField(Journal.class, "test value 3")
                .buildEntry();
        assertFalse(testEntry1.hasAllNecessary());
        testEntry1.setField(Year.class, "test value 4");
        assertTrue(testEntry1.hasAllNecessary());

        Book testEntry2 = (Book) new EntryBuilder(Book.class)
                .setField(Author.class, "test value 1")
                .setField(Title.class, "test value 2")
                .setField(Publisher.class, "test value 3")
                .buildEntry();
        assertFalse(testEntry2.hasAllNecessary());
        testEntry2.setField(Year.class, "test value 4");
        assertTrue(testEntry2.hasAllNecessary());
        testEntry2.setField(Editor.class, "test value 5");
        assertFalse(testEntry2.hasAllNecessary());
    }

    @Test
    void setField() {
        Author testField1 = new Author("field test value");
        Article testEntry1 = (Article) new EntryBuilder(Article.class)
                .setField(testField1)
                .setField(Title.class, "test value 2")
                .setField(Journal.class, "test value 3")
                .setField(Year.class, "test value 4")
                .setField(Tag.class, "test value 5")
                .buildEntry();
        assertNull(testEntry1.note);
        testEntry1.setField(Note.class, "test value 6");
        assertEquals("field test value", testEntry1.author.getValue());
        assertEquals("test value 5", testEntry1.tag.getValue());
        assertEquals("test value 6", testEntry1.note.getValue());
    }

    @Test
    void inheritFields() {
        Article testEntry1 = (Article) new EntryBuilder(Article.class)
                .setField(Note.class, "test value 1 NOTE 1")
                .buildEntry();
        Misc testEntry2 = (Misc) new EntryBuilder(Misc.class)
                .setField(Note.class, "test value 1 NOTE 2")
                .setField(Month.class, "test value 2")
                .buildEntry();
        testEntry1.inheritFields(testEntry2);
        assertEquals("test value 1 NOTE 1", testEntry1.note.getValue());
        assertEquals("test value 2", testEntry1.month.getValue());
    }

    @Test
    void getField() {
        Author testField1 = new Author("field test value");
        Article testEntry1 = (Article) new EntryBuilder(Article.class)
                .setField(testField1)
                .buildEntry();
        assertSame(testField1, testEntry1.getField(Author.class));
    }

    @Test
    void setFieldIf() {
        Article testEntry1 = (Article) new EntryBuilder(Article.class)
                .buildEntry();
        for (Field f : testEntry1.getClass().getDeclaredFields()) {
            if (f.getType() == Note.class)
                testEntry1.setFieldIf(Note.class, "test value 1", f);
            if (f.getType() == Author.class)
                testEntry1.setFieldIf(Note.class, "test value 2", f);
        }
        assertEquals("test value 1", testEntry1.note.getValue());
        assertNull(testEntry1.author);
    }

    @Test
    void formatFields() {
        Misc testEntry1 = (Misc) new EntryBuilder(Misc.class)
                .setField(Author.class, "test value")
                .buildEntry();
        testEntry1.formatFields();
        assertEquals("test value", testEntry1.author.value);
        assertTrue(testEntry1.author.authors.isEmpty());

        Misc testEntry2 = (Misc) new EntryBuilder(Misc.class)
                .setField(Author.class, "test | value")
                .buildEntry();
        testEntry2.formatFields();
        assertEquals("test | value", testEntry2.author.value);
        assertEquals("test", testEntry2.author.authors.getFirst().getKey());
        assertEquals("value", testEntry2.author.authors.getFirst().getValue());
        assertEquals(1, testEntry2.author.authors.size());
    }

    @Test
    void formatSuperClassFields() {
    }

    @Test
    void getTag() {
        Misc testEntry1 = (Misc) new EntryBuilder(Misc.class)
                .setField(Tag.class, "test value")
                .buildEntry();
        assertEquals("test value", testEntry1.getTag().getValue());
    }

    @Test
    void getCrossref() {
        Misc testEntry1 = (Misc) new EntryBuilder(Misc.class)
                .setField(Crossref.class, "test value")
                .buildEntry();
        assertEquals("test value", testEntry1.getCrossref().getValue());
    }

    @Test
    void cleanUpFields() {
        Misc testEntry1 = (Misc) new EntryBuilder(Misc.class)
                .setField(Author.class, "test test | value")
                .setField(Title.class, "\"test value 1  #")
                .setField(Tag.class, "test  | value   2   ")
                .buildEntry();
        testEntry1.formatFields();
        testEntry1.cleanUpFields();
        assertEquals("test test value", testEntry1.author.value);
        assertEquals("test test", testEntry1.author.authors.getFirst().getKey());
        assertEquals("value", testEntry1.author.authors.getFirst().getValue());
        assertEquals("test value 1 ", testEntry1.title.value);
        assertEquals("test  | value   2   ", testEntry1.tag.getValue());
    }

    @Test
    void cleanUpSuperClassFields() {
        Misc testEntry1 = (Misc) new EntryBuilder(Misc.class)
                .setField(Title.class, "\"test value 1  #")
                .setField(Tag.class, "test  | value   2   ")
                .buildEntry();
        testEntry1.cleanUpSuperClassFields();
        assertEquals("\"test value 1  #", testEntry1.title.value);
        assertEquals("test value 2 ", testEntry1.tag.getValue());
    }
}