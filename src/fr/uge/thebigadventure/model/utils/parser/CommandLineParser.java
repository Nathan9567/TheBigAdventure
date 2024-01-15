package fr.uge.thebigadventure.model.utils.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class is responsible for parsing the command line arguments.
 * It uses a map to associate each argument with its corresponding action.
 */
public class CommandLineParser {

  private static final Map<String, Consumer<String>> ARGUMENTS = new HashMap<>();
  private String mapPath;
  private boolean validate;
  private boolean dryRun;

  /**
   * Constructor for the CommandLineParser class.
   *
   * @param args the command line arguments.
   */
  public CommandLineParser(String[] args) {
    if (args.length < 1) {
      System.err.println("""
          Invalid number of arguments.
          Use -h or --help to display help message.
          """);
      System.exit(1);
    }

    ARGUMENTS.put("-v", arg -> validate = true);
    ARGUMENTS.put("--validate", arg -> validate = true);
    ARGUMENTS.put("-d", arg -> dryRun = true);
    ARGUMENTS.put("--dry-run", arg -> dryRun = true);
    ARGUMENTS.put("-l", this::setMapPath);
    ARGUMENTS.put("--level", this::setMapPath);
    ARGUMENTS.put("-h", arg -> displayHelpAndExit());
    ARGUMENTS.put("--help", arg -> displayHelpAndExit());

    parse(args);
  }

  /**
   * Displays the help message and exits the program.
   */
  private void displayHelpAndExit() {
    System.out.println(helpMessage());
    System.exit(0);
  }

  /**
   * Checks if the validate option is set.
   * The validate option is used to validate a map file without playing it.
   *
   * @return true if the validate option is set, false otherwise.
   */
  public boolean isValidate() {
    return validate;
  }

  /**
   * Checks if the dry-run option is set.
   * The dry-run option is used to test the map without moving the mobs.
   *
   * @return true if the dry-run option is set, false otherwise.
   */
  public boolean isDryRun() {
    return dryRun;
  }

  /**
   * Gets the path of the map file.
   *
   * @return the path of the map file.
   */
  public String getMapPath() {
    return mapPath;
  }

  /**
   * Sets the path of the map file.
   *
   * @param path the path of the map file.
   */
  private void setMapPath(String path) {
    mapPath = path;
  }

  /**
   * Parses the command line arguments.
   *
   * @param args the command line arguments.
   */
  public void parse(String[] args) {
    for (String arg : args) {
      Consumer<String> action = ARGUMENTS.get(arg);
      if (action != null) {
        action.accept(arg);
      } else if (arg.endsWith(".map")) {
        setMapPath(arg);
      } else {
        throw new IllegalArgumentException("Invalid argument: " + arg);
      }
    }
    checkOptions();
  }

  /**
   * Checks the options and throws an exception if the options are not valid.
   */
  private void checkOptions() {
    if (mapPath == null) {
      throw new IllegalArgumentException("Missing map file");
    }
    if (validate && dryRun) {
      throw new IllegalArgumentException("Cannot validate and dry-run at the same time");
    }
  }

  /**
   * Returns the help message.
   *
   * @return the help message.
   */
  private String helpMessage() {
    return """
        Usage: java -jar TheBigAdventure.jar [options] [map]

        Options:
        map is the path to a map file
        -d, --dry-run           map in stroll mode (mob don't move)
        -h, --help              display this help message
        -l, --level <name.map>  load a map file. You can also use the map path
                                as argument without the option.
        -v, --validate          validate a map file without loading it
        """;
  }
}