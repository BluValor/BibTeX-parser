package classes;

import classes.entries.Article;
import classes.entries.Inbook;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearcherTest {

    @Test
    void viaEntryType() {
        List<BEntry> testList = BibtexParser.parse("C:\\Users\\BluValor\\IdeaProjects\\bibtex_parser_2\\src\\test\\java\\files\\testInput.txt");
        List<Class> classes = new LinkedList<>();
        List<BEntry> expected = new LinkedList<>();
        expected.add(testList.get(0));
        classes.add(Inbook.class);
        classes.add(Article.class);
        testList = Searcher.viaEntryType(testList, classes);
        assertSame(expected.get(0), testList.get(0));
        assertEquals(1, testList.size());
    }

    @Test
    void viaAuthorsLastNames() {
        List<BEntry> testList = BibtexParser.parse("C:\\Users\\BluValor\\IdeaProjects\\bibtex_parser_2\\src\\test\\java\\files\\testInput.txt");
        List<BEntry> expected = new LinkedList<>();
        expected.add(testList.get(0));
        expected.add(testList.get(1));
        String[] authors1 = {"Knuth"};
        testList = Searcher.viaAuthorsLastNames(testList, authors1);
        assertSame(expected.get(0), testList.get(0));
        assertSame(expected.get(1), testList.get(1));
        String[] authors2 = {"Ullman"};
        testList = Searcher.viaAuthorsLastNames(testList, authors2);
        assertTrue(testList.isEmpty());
    }
}