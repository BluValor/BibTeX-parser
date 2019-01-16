import classes.*;

import java.util.*;

import classes.entries.Article;
import classes.entries.Book;
import classes.entries.Incollection;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.File;


@Command(
        name = "BibTeX parser",
        description = "Parses BibTeX .bib files. Provides ability to read and to search within parsed file.",
        mixinStandardHelpOptions = true,
        version = "BibTeX_parser 1.0"
)
public class Main implements Runnable {

    @Option(names = { "-p", "--path" }, description = "BibTeX file url.", paramLabel = "PATH")
    private String path;

    @Option(names = { "-a", "--authors" }, description = "search arguments: authors last names separated with comma ','",
            split = ",", paramLabel = "AUTHORS")
    private String[] lastNames;

    @Option(names = { "-e", "--entries" }, description = "search arguments: entries names separated with comma ','",
            split = ",", paramLabel = "ENTRIES")
    private String[] entries;

//    C:\Users\BluValor\IdeaProjects\bibtex_parser_2\src\main\java\classes\files\input.txt
    public static void main(String[] args) {
        CommandLine.run(new Main(), args);
    }

    /**
     * executes parsing, searching and printing results
     */
    public void run() {
        System.out.println();
        List<BEntry> result = BibtexParser.parse(path);

        if (lastNames != null)
            result = Searcher.viaAuthorsLastNames(result, lastNames);

        if (entries != null) {
            List<Class> entryClasses = new LinkedList<>();
            for (String entry : entries) {
                try {
                    Class entryClass = Class.forName(Adjuster.adjustEntryName(entry));
                    entryClasses.add(entryClass);
                } catch (ClassNotFoundException e) {
                    System.out.println("There is no entry class for name " + entry);
                }
            }
            result = Searcher.viaEntryType(result, entryClasses);
        }
        "".split(",\n");
        print(result);
    }

    /**
     * prints out given entries
     *
     * @param entries entries to print
     */
    public void print(List<BEntry> entries) {
        System.out.println();
        for (BEntry entry : entries)
            System.out.println(entry);
    }
}
