package fr.uge.thebigadventure.controller;

import java.util.Arrays;

public record CommandLineParser(String[] args) {

  private static String mapPath;
  private static boolean validate;
  private static boolean dryRun;

  public CommandLineParser {
    if (args.length < 1) {
      System.err.println("""
          Invalid number of arguments.
          Use -h or --help to display help message.
          """);
      System.exit(1);
    }
  }

  public boolean isValidate() {
    return validate;
  }

  public boolean isDryRun() {
    return dryRun;
  }

  public String getMapPath() {
    return mapPath;
  }

  private void addValidateArgument() {
    validate = true;
  }

  private void addDryRunArgument() {
    dryRun = true;
  }

  public void parse() {
    var argIterator = Arrays.stream(args).iterator();
    while (argIterator.hasNext()) {
      var arg = argIterator.next();
      if (help(arg)) {
        System.out.println(helpMessage());
        System.exit(0);
        return;
      } else if (validate(arg)) {
        addValidateArgument();
      } else if (dryRun(arg)) {
        addDryRunArgument();
      } else if (load(arg)) {
        if (!argIterator.hasNext()) {
          throw new IllegalArgumentException("Missing map file");
        }
        mapPath = argIterator.next();
      } else if (arg.endsWith(".map")) {
        mapPath = arg;
      } else {
        throw new IllegalArgumentException("Invalid argument: " + arg);
      }
    }
    checkOptions();
  }

  private void checkOptions() {
    if (mapPath == null) {
      throw new IllegalArgumentException("Missing map file");
    }
    if (validate && dryRun) {
      throw new IllegalArgumentException("Cannot validate and dry-run at the same time");
    }
  }

  private boolean help(String arg) {
    return arg.equals("-h") || arg.equals("--help");
  }

  private boolean validate(String arg) {
    return arg.equals("-v") || arg.equals("--validate");
  }

  private boolean dryRun(String arg) {
    return arg.equals("-d") || arg.equals("--dry-run");
  }

  private boolean load(String arg) {
    return arg.equals("-l") || arg.equals("--level");
  }

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
