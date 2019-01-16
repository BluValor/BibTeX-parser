package classes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BibtexParserTest {

    @Test
    void parse() {
        List<BEntry> testList = BibtexParser.parse("C:\\Users\\BluValor\\IdeaProjects\\bibtex_parser_2\\src\\test\\java\\files\\testInput.txt");
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(testList.get(0)).append(testList.get(1));
        String resultString = resultBuilder.toString();
        String[] expected = {":|>-------------------------------------------------------------------------------------------------<|:",
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
                ":|>-------------------------------------------------------------------------------------------------<|:",
                "",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  BOOK (whole-set)                                                                                 |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Author           ->  Donald E. Knuth:                                                            |:",
                ":|                   ->   * first name: \"Donald E.\", last name: \"Knuth\"                              |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Title            ->  The Art of Computer Programming                                             |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Publisher        ->  Addison-Wesley                                                              |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Year             ->  {\\noopsort{1973a}}{\\switchargs{--90}{1968}}                                 |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Series           ->  Four volumes                                                                |:",
                ":|>-------------------------------------------------------------------------------------------------<|:",
                ":|  Note             ->  Seven volumes planned (this is a cross-referenced set of BOOKs)             |:",
                ":|>-------------------------------------------------------------------------------------------------<|:"};
        String[] resultParsed = resultString.split(System.lineSeparator());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], resultParsed[i]);
        }
    }

    @Test
    void prepareInputText() {
    }

    @Test
    void cleanUpEntries() {
    }

    @Test
    void validateEntries() {
    }

    @Test
    void exeStrAdd() {
    }

    @Test
    void strAddWPattern() {
    }

    @Test
    void combineEntries() {
    }

    @Test
    void addStrings() {
    }

    @Test
    void addEntries() {
    }

    @Test
    void parseEntry() {
    }

    @Test
    void parseField() {
    }
}