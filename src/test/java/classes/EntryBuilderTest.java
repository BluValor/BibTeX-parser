package classes;

import classes.entries.Article;
import classes.fields.Author;
import classes.fields.Journal;
import classes.fields.Title;
import classes.fields.Year;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryBuilderTest {

    @Test
    void setField() {
        Article testEntry = (Article) new EntryBuilder(Article.class)
                .setField(Author.class, "test value 1")
                .setField(Title.class, "test value 2")
                .setField(Journal.class, "test value 3")
                .setField(Year.class, "test value 4")
                .buildEntry();
        assertEquals("test value 1", testEntry.author.value);
    }

    @Test
    void buildEntry() {
        BEntry testEntry = new EntryBuilder(Article.class)
                .setField(Author.class, "test value 1")
                .setField(Title.class, "test value 2")
                .setField(Journal.class, "test value 3")
                .setField(Year.class, "4")
                .buildEntry();
        assertSame(Article.class, testEntry.getClass());
    }
}