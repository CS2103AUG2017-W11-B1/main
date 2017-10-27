package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;


public class AutoCorrectCommandTest {
    private String defaultResult;

    @Before
    public void setUp() {
        defaultResult = "Unknown Command";
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();

    @Test
    /**
     *  Check for the situation where the misspelt word cannot be corrected.
     */
    public void executeCommandIsNotFoundThrowsException() {
        /*
        * Check when the misspelt command with edit distance 1 which
        * cannot be auto-corrected against the wrong corresponding command
        */
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("search", "dad"));
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("add", "seacrh"));

        /*
        * Check when the misspelt command with edit distance 2 which
        * cannot be auto-corrected against the wrong corresponding command
        */
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("add", "ddaw"));
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("add", "sreach"));
    }

    @Test
    /**
     * Check for the input parameter, throws exception when it is null or empty string
     */
    public void executeInputCommandNullException() {
        thrown.expect(IllegalArgumentException.class);
        autoCorrectCommand.correctWord("");
        autoCorrectCommand.correctWord(null);
    }

    @Test
    /**
     * Check the method will return the correct command for edit distance 1 input
     */
    public void executeEditDistance1MatchCommandTest() {
        final String commandSearch = "search";
        final String inputCommandSearch = "serach";
        ArrayList<String> misspeltWordsPoolSearch = autoCorrectCommand.editDistance1(commandSearch);
        assertTrue(misspeltWordsPoolSearch.stream().anyMatch(e -> e.equals(inputCommandSearch)));

        final String commandFind = "find";
        final String inputCommandFind = "fnid";
        ArrayList<String> misspeltWordsPoolFind = autoCorrectCommand.editDistance1(commandFind);
        assertTrue(misspeltWordsPoolFind.stream().anyMatch(e -> e.equals(inputCommandFind)));

        final String commandUndo = "undo";
        final String inputCommandUndo = "unod";
        ArrayList<String> misspeltWordsPoolUndo = autoCorrectCommand.editDistance1(commandUndo);
        assertTrue(misspeltWordsPoolUndo.stream().anyMatch(e -> e.equals(inputCommandUndo)));
    }

    @Test
    /**
     * Check the method will return the correct command for edit distance 2 input
     */
    public void executeEditDistance2MatchCommandTest() {
        final String commandSearch = "search";
        final String inputCommandSearch = "sreach";
        String correctCommandSearch = autoCorrectCommand.correctWord(inputCommandSearch);
        assertEquals(commandSearch, correctCommandSearch);

        final String commandFind = "find";
        final String inputCommandFind = "fndi";
        String correctCommandFind = autoCorrectCommand.correctWord(inputCommandFind);
        assertEquals(commandFind, correctCommandFind);
    }

    @Test
    /**
     * Check the method will not return the correct command if
     * the first alphabet of the input command does not match any available commands.
     */
    public void executeCorrectWordsNotMatchTest() {
        final String defaultResult = "Unknown Command";

        final String inputCommandSearch = "ereach";
        String correctCommandSearch = autoCorrectCommand.correctWord(inputCommandSearch);
        assertEquals(defaultResult, correctCommandSearch);

        final String inputCommandFind = "dda";
        String correctCommandFind = autoCorrectCommand.correctWord(inputCommandFind);
        assertEquals(defaultResult, correctCommandFind);
    }

    @Test
    /**
     * Check the method will return the correct alias
     * as the auto-correct is not meant to auto-correct alias
     */
    public void executeAliasCommandTest() {
        final String inputCommandAdd = "a";
        String correctCommandAdd = autoCorrectCommand.correctWord(inputCommandAdd);
        assertEquals(AddCommand.COMMAND_WORD_ALIAS, correctCommandAdd);

        final String inputCommandUnknown = "m";
        String correctCommandUnknown = autoCorrectCommand.correctWord(inputCommandUnknown);
        assertEquals(inputCommandUnknown, correctCommandUnknown);
    }
}
