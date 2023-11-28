package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.enums.environment.EnvironmentType;
import fr.uge.thebigadventure.models.interpreter.Size;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Parser {

  public static GameMap parse(String map) {
    Pattern pattern = Pattern.compile(
        "\\[(element|grid)](.+?)(?=\\[|\\z)", Pattern.DOTALL);
    var matcher = pattern.matcher(map);
    GameMap game = null;
    while (matcher.find()) {
      switch (matcher.group(1)) {
        case "grid" -> game = parseGrid(matcher.group(2));
        case "element" -> parseElement(matcher.group(2));
        default -> throw new IllegalArgumentException(
            "Invalid map : " + matcher.group(1) + " doesn't exist");
      }
    }
    return game;
  }

  private static void parseElement(String group) {
  }

  private static GameMap parseGrid(String group) {
    Size size = parseSize(group);
    Map<String, EnvironmentType> encodingMap = parseEncoding(group);
    Map<Coord, EnvironmentType> dataMap = parseDataMap(group, size, encodingMap);
    return new GameMap(size, dataMap);
  }


  private static Size parseSize(String line) {
    Pattern pattern = Pattern.compile(
        "\\s*size\\s*:\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\)\\s*");
    var matcher = pattern.matcher(line);
    if (!matcher.find()) {
      throw new IllegalArgumentException("Invalid size");
    }
    return new Size(Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2)));
  }

  private static Map<String, EnvironmentType> parseEncoding(String line) {
    Pattern globalPattern = Pattern.compile("encodings\\s*:((.|\\n)+?)(?=.+:)");
    var globalMatcher = globalPattern.matcher(line);
    if (!globalMatcher.find()) {
      throw new IllegalArgumentException("Invalid encoding");
    }
    Pattern pattern = Pattern.compile("\\s*(.+?)\\s*\\((.+?)\\)");
    var matcher = pattern.matcher(globalMatcher.group(1));
    var encodingMap = new HashMap<String, EnvironmentType>();
    while (matcher.find()) {
      encodingMap.put(matcher.group(2),
          EnvironmentType.fromString(matcher.group(1)));
    }
    return Map.copyOf(encodingMap);
  }

  private static Map<Coord, EnvironmentType> parseDataMap(
      String line, Size size, Map<String, EnvironmentType> encodingMap) {
    Pattern pattern = Pattern.compile("data: \"\"\"\\s*\\n((?>.|\\n)+)\\n([ " +
        "]*)\"\"\"");
    var matcher = pattern.matcher(line);
    if (!matcher.find())
      throw new IllegalArgumentException("Invalid data map");
    HashMap<Coord, EnvironmentType> map = new HashMap<>();
    var y = 0;
    for (var lineMap : matcher.group(1).split("\n")) {
      var x = -matcher.group(2).length();
      for (var charMap : lineMap.toCharArray()) {
        if (charMap == ' ') {
          x++;
          continue;
        }
        var env = encodingMap.get(String.valueOf(charMap));
        if (env == null) {
          throw new IllegalArgumentException(
              "Invalid encoding " + charMap + " doesn't exist");
        }
        map.put(new Coord(x, y), env);
        x++;
      }
      y++;
    }
    return map;
  }

  public static void main(String[] args) throws IOException {
    File file = new File("resources/test.map");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
      stringBuilder.append("\n");
    }
    reader.close();
    GameMap gameMap = parse(stringBuilder.toString());
    System.out.println(gameMap.size());
    System.out.println(gameMap.data());
  }
}
