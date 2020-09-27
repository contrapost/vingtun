package me.contrapost.vingtun;

import org.apache.commons.cli.*;

import java.util.ArrayList;

public class VingtUnApp {

    public static void main(final String[] args) {
        String deckFileName = getFileNameFromCLI(args);
        ArrayList<Card> deck = new Deck(deckFileName).getCards();
        deck.forEach(System.out::println);
    }

    private static String getFileNameFromCLI(final String[] args) {
        Options cliOptions = getCLIOptions();
        HelpFormatter formatter = new HelpFormatter();
        String deckFileName = null;

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(cliOptions, args);
            if (cmd.hasOption("f")) {
                deckFileName = cmd.getOptionValue("f");
                if (null == deckFileName) {
                    System.out.println("Deck file name wasn't specified. Initializing deck with default file 'defaultDeckFile.txt'");
                    deckFileName = "defaultDeckFile.txt";
                }
            }
            if (cmd.hasOption("h")) {
                formatter.printHelp("XXX", cliOptions);
            }
        } catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");

            formatter.printHelp("XXX", cliOptions);
            System.exit(1);
        }

        return deckFileName;
    }

    private static Options getCLIOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f")
                                .longOpt("deckFile")
                                .hasArg(true)
                                .desc("")
                                .optionalArg(true)
                                .required(false)
                                .build());
        options.addOption(Option.builder("h")
                                .longOpt("help")
                                .desc("")
                                .required(false)
                                .hasArg(false)
                                .build());
        return options;
    }
}
