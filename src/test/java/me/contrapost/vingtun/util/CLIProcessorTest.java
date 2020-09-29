package me.contrapost.vingtun.util;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static me.contrapost.vingtun.util.Constants.DEFAULT_FILE_NAME;
import static org.junit.Assert.assertEquals;

public class CLIProcessorTest {

    @Test
    public void shortVersionOfDeckFileCorrectFileNameReturned() {
        String userSpecifiedFileName = "deckFile.txt";
        String[] args = { "-f", userSpecifiedFileName };
        String fileName = CLIProcessor.getFileNameFromCLI(args);
        assertEquals(fileName, userSpecifiedFileName);
    }

    @Test
    public void logVersionOfDeckFileFlagCorrectFileNameReturned() {
        String userSpecifiedFileName = "deckFile.txt";
        String[] args = { "--deckFile", userSpecifiedFileName };
        String fileName = CLIProcessor.getFileNameFromCLI(args);
        assertEquals(fileName, userSpecifiedFileName);
    }

    @Test
    public void deckFileNameNotSpecifiedDefaultFileNameReturned() throws Exception {
        String consoleOutput = tapSystemOut(() -> {
            String[] args = { "--deckFile" };
            String fileName = CLIProcessor.getFileNameFromCLI(args);
            assertEquals(fileName, DEFAULT_FILE_NAME);
        });
        assertEquals(consoleOutput.trim(),
                     "Deck file name wasn't specified. Setting it to the name of the default file 'defaultDeckFile.txt'");
    }

    @Test
    public void helpAndFileFlagsAreUsedTogetherResultsInHelpOutputAndReturnOfFileName() throws Exception {
        String consoleOutput = tapSystemOut(() -> {
            String userSpecifiedFileName = "deckFile.txt";
            String[] args = { "-h", "-f", userSpecifiedFileName };
            String fileName = CLIProcessor.getFileNameFromCLI(args);
            assertEquals(fileName, userSpecifiedFileName);
        });
        assertEquals(consoleOutput.trim(),
                     "usage: 21 game\n" +
                     " -f,--deckFile <arg>   Option specifying that game should be played with\n" +
                     "                       deck from the file <arg>. If <arg> isn't given,\n" +
                     "                       default file 'defaultDeckFile.txt' would be used.\n" +
                     " -h,--help             Prints instructions.");

    }

    @Test
    public void unexpectedFlagResultInHelpOutputAndExitingGame() throws Exception {
        String consoleOutput = tapSystemOut(() -> {
            int status = SystemLambda.catchSystemExit(() -> {
                String[] args = { "--unexpectedFlag" };
                CLIProcessor.getFileNameFromCLI(args);
            });
            assertEquals(status, 1);
        });
        assertEquals(consoleOutput.trim(),
                     "Error parsing command-line arguments!\n" +
                     "Please, follow the instructions below:\n" +
                     "usage: 21 game\n" +
                     " -f,--deckFile <arg>   Option specifying that game should be played with\n" +
                     "                       deck from the file <arg>. If <arg> isn't given,\n" +
                     "                       default file 'defaultDeckFile.txt' would be used.\n" +
                     " -h,--help             Prints instructions.");
    }
}
