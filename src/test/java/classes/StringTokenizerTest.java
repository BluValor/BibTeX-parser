package classes;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StringTokenizerTest {

    @Test
    void nextToken() {
        String input = null;
        try {
            input = BibtexParser.prepareInputText("C:\\Users\\BluValor\\IdeaProjects\\bibtex_parser_2\\src\\test\\java\\files\\testInput.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringTokenizer testTokenizer = new StringTokenizer(input);
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
        StringTokenizer testTokenizer = new StringTokenizer(input);
        assertTrue(testTokenizer.hasMoreTokens());
        testTokenizer.nextToken();
        assertFalse(testTokenizer.hasMoreTokens());
    }
}