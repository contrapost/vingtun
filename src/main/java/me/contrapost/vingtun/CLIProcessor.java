package me.contrapost.vingtun;

import org.apache.commons.cli.*;

import static me.contrapost.vingtun.Constants.DEFAULT_FILE_NAME;

public class CLIProcessor {

    private CLIProcessor() {
    }

    public static String getFileNameFromCLI(final String[] args) {
        Options cliOptions = getCLIOptions();
        HelpFormatter formatter = new HelpFormatter();
        String deckFileName = null;

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(cliOptions, args);
            if (cmd.hasOption("f")) {
                deckFileName = cmd.getOptionValue("f");
                if (null == deckFileName) {
                    System.out.println("Deck file name wasn't specified. Setting it to the name of the default file " +
                                       "'" + DEFAULT_FILE_NAME + "'");
                    deckFileName = DEFAULT_FILE_NAME;
                }
            }
            if (cmd.hasOption("h")) {
                formatter.printHelp("21 game", cliOptions, false);
            }
        } catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the instructions below:");

            formatter.printHelp("21 game", cliOptions);
            System.exit(1);
        }

        return deckFileName;
    }

    private static Options getCLIOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f")
                                .longOpt("deckFile")
                                .hasArg(true)
                                .desc("Option specifying that game should be played with deck from the file <arg>. " +
                                      "If <arg> isn't given, default file '" + DEFAULT_FILE_NAME + "' would be used.")
                                .optionalArg(true)
                                .required(false)
                                .build());
        options.addOption(Option.builder("h")
                                .longOpt("help")
                                .desc("Prints instructions.")
                                .required(false)
                                .hasArg(false)
                                .build());
        return options;
    }
}
