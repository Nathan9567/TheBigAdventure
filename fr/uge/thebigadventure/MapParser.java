package fr.uge.thebigadventure;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapParser {
  private static final HashMap<String, Element> encodingMap = new HashMap<>();
  private static final Pattern sizePattern = Pattern.compile("size: \\((\\d+) x (\\d+)\\)");
  private static final Pattern encodingsPattern = Pattern.compile("encodings:((.|\\n)+?)(?=.+:)");
  private static final Pattern encodingElementPattern = Pattern.compile("\\s*(.+?)\\((.+?)\\)");
  private static final Pattern dataPattern = Pattern.compile("data: \\\"\\\"\\\"(.+?)\\\"\\\"\\\"", Pattern.DOTALL);

  private static int[] parseSize(String line) {
    Matcher matcher = sizePattern.matcher(line);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid size format");
    }
    return new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))};
  }

  private static void parseEncodings(String line) {
    Matcher encodingMatcher = encodingsPattern.matcher(line);
    if (!encodingMatcher.matches()) {
      throw new IllegalArgumentException("Invalid encodings format");
    }
    Matcher elemMatcher = encodingElementPattern.matcher(encodingMatcher.group(1));
    while (elemMatcher.find()) {
//    Faudra créer la méthode valueOf là, comme ça on pourra faire ça :
//    encodingMap.put(elemMatcher.group(1), Element.valueOf(elemMatcher.group(2)));
    }
  }

  private static Element[][] parseData(String line) {
    Matcher dataMatcher = dataPattern.matcher(line);
    if (!dataMatcher.matches()) {
      throw new IllegalArgumentException("Invalid data format");
    }
    String[] lines = dataMatcher.group(1).split("\n");
    Element[][] data = new Element[lines.length][];
    for (int i = 0; i < lines.length; i++) {
      String[] cells = lines[i].split(" ");
      data[i] = new Element[cells.length];
      for (int j = 0; j < cells.length; j++) {
        data[i][j] = encodingMap.get(cells[j]);
      }
    }
    return data;
  }

  public static Map parse(Scanner scanner) {
    Matcher sizeMatcher = sizePattern.matcher(scanner.nextLine());

    Matcher encodingsMatcher = encodingsPattern.matcher(scanner.nextLine());


    if (!sizeMatcher.matches() || !encodingsMatcher.matches()) {
      throw new IllegalArgumentException("Invalid map format");
    }

    Map map = new Map(Integer.parseInt(sizeMatcher.group(1)), Integer.parseInt(sizeMatcher.group(2)));


  }
}
