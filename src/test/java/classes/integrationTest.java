package classes;

import classes.entries.Article;
import classes.entries.Book;
import classes.entries.Inbook;
import classes.entries.Misc;
import classes.fields.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

public class integrationTest {

    @Test
    void integration() {
        List<BEntry> testList = BibtexParser.parse("C:\\Users\\BluValor\\IdeaProjects\\bibtex_parser_2\\src\\test\\java\\files\\testInput.txt");
        List<Class> classes = new LinkedList<>();
        classes.add(Inbook.class);
        classes.add(Article.class);
        String[] authors = {"Knuth", "Ullman"};
        testList = Searcher.viaEntryType(testList, classes);
        testList = Searcher.viaAuthorsLastNames(testList, authors);
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(testList.get(0));
        String resultString = resultBuilder.toString();
        String[] expected = { ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  INBOOK (inbook-crossref)                                                                         |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Crossref         ->  whole-set                                                                   |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Author           ->  Donald E. Knuth:                                                            |:",
                ":|                   ->   * first name: \"Donald E.\", last name: \"Knuth\"                              |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Title            ->  Fundamental Algorithms and STOC                                             |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Chapter          ->  1.2                                                                         |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Publisher        ->  Addison-Wesley                                                              |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Year             ->  {\\noopsort{1973b}}1973                                                      |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Volume           ->  1                                                                           |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Series           ->  The Art of Computer Programming                                             |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Type             ->  Section                                                                     |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Edition          ->  Second                                                                      |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Note             ->  This is a cross-referencing INBOOK entry                                    |:",
                ":|>-------------------------------------------------------------------------------------------------<|:"};
        String[] resultParsed = resultString.split(System.lineSeparator());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], resultParsed[i]);
        }
    }
}
