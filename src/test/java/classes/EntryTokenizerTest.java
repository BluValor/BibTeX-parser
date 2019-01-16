package classes;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EntryTokenizerTest {

    @Test
    void nextToken() {
        String input = null;
        try {
            input = BibtexParser.prepareInputText("C:\\Users\\BluValor\\IdeaProjects\\bibtex_parser_2\\src\\test\\java\\files\\testInput.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EntryTokenizer testTokenizer = new EntryTokenizer(input);
        assertNotNull(testTokenizer.nextToken());
        assertNotNull(testTokenizer.nextToken());
        assertNull(testTokenizer.nextToken());
    }

    @Test
    void hasMoreTokens() {
        String input = null;
        try {
            input = BibtexParser.prepareInputText("C:\\Users\\BluValor\\IdeaProjects\\bibtex_parser_2\\src\\test\\java\\files\\testInput.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EntryTokenizer testTokenizer = new EntryTokenizer(input);
        assertTrue(testTokenizer.hasMoreTokens());
        testTokenizer.nextToken();
        assertTrue(testTokenizer.hasMoreTokens());
        testTokenizer.nextToken();
        assertFalse(testTokenizer.hasMoreTokens());

    }
}