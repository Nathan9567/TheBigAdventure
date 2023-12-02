package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.Size;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MapParser {
  private final String text;
  private final MapBuilder builder;
  private static Pattern SECTIONS_PATTERN = Pattern.compile("\\[(\\w+)\\](.+?)(?=\\[|\\z)", Pattern.DOTALL);
  private static Pattern ATTRIBUTES_PATTERN = Pattern.compile("\\s*(\\w+)\\s*:\\s*(.+?)(?=\\s*\\w*\\s*:|\\z)", Pattern.DOTALL);
  private static Pattern SIZE_PATTERN = Pattern.compile("\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\)\\s*");
  private static Pattern ENCODINGS_PATTERN = Pattern.compile("\\s*(\\w+?)\\s*\\(\\s*(\\w)\\s*\\)\\s*");
  private static Pattern GRID_DATA_PATTERN = Pattern.compile("\\\"\\\"\\\"\\s*\\n(.+)\\n([ ]*)\\\"\\\"\\\"", Pattern.DOTALL);
  
  public MapParser(String text) {
    this.text = text;
    this.builder = new MapBuilder();
  }

  public MapBuilder parse() {
    var matcher = SECTIONS_PATTERN.matcher(text);
    var pointer = 0;
    while (matcher.find()) {
      parseSection(matcher.group(1), matcher.group(2));
      if (matcher.start() != pointer) {
        throw new IllegalArgumentException(
            "Invalid map : garbage between character " + pointer + " and " + matcher.start() + ".");
      }
      pointer = matcher.end();
    }
    if (pointer != text.length()) {
      throw new IllegalArgumentException(
          "Invalid map : stop reading at character " + pointer + ".");
    }
    return builder;
  }

  private void parseSection(String name, String content) {
    switch (name) {
      case "grid" -> parseGrid(content);
      case "element" -> parseElement(content);
      default -> throw new IllegalArgumentException(
          "Invalid map : " + name + " doesn't exist");
    }
  }

  private void parseGrid(String content) {
    var matcher = ATTRIBUTES_PATTERN.matcher(content);
    var pointer = 0;
    while (matcher.find()) {
      parseGridAttributes(matcher.group(1), matcher.group(2));
      if (matcher.start() != pointer) {
        throw new IllegalArgumentException(
            "Invalid map : bad grids attributes.");
      }
      pointer = matcher.end();
    }
    if (pointer != content.length()) {
      throw new IllegalArgumentException(
          "Invalid map : stop reading grids attributes.");
    }
  }

  private void parseGridAttributes(String name, String content) {
    switch (name) {
      case "size" -> parseGridAttributeSize(content);
      case "encodings" -> parseGridAttributeEncodings(content);
      case "data" -> parseGridAttributeData(content);
      default -> throw new IllegalArgumentException("Invalid map : grid attribute " + name + " doest not exists.");
    }
  }

  private void parseGridAttributeSize(String content) {
    var matcher = SIZE_PATTERN.matcher(content);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid size");
    }
    builder.setSize(new Size(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
  }

  private void parseGridAttributeEncodings(String content) {
    var encodingMap = new HashMap<String, EntityType>();
    var matcher = ENCODINGS_PATTERN.matcher(content);
    var pointer = 0;
    while (matcher.find()) {
      encodingMap.put(matcher.group(2), EntityType.fromString(matcher.group(1)));
      if (matcher.start() != pointer) {
        throw new IllegalArgumentException("Invalid map : bad grid encodings.");
      }
      pointer = matcher.end();
    }
    if (pointer != content.length()) {
      throw new IllegalArgumentException("Invalid map : bad grid encodings.");
    }
    builder.setEncodings(encodingMap);
  }

  private void parseGridAttributeData(String content) {
    var matcher = GRID_DATA_PATTERN.matcher(content);
    if (!matcher.find())
      throw new IllegalArgumentException("Invalid data map");
    HashMap<Coord, Character> map = new HashMap<>();
    var y = 0;
    for (var lineMap : matcher.group(1).split("\n")) {
      var x = -matcher.group(2).length();
      for (var charMap : lineMap.toCharArray()) {
        if (charMap == ' ') {
          x++;
          continue;
        }
        map.put(new Coord(x, y), charMap);
        x++;
      }
      y++;
    }
    builder.setData(map);
  }
  
  private void parseElement(String group) {
    builder.setElements(Map.of());
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
    
    var parser = new MapParser(stringBuilder.toString());
    var mapBuilder = parser.parse();
    GameMap gameMap = mapBuilder.toGameMap();
    System.out.println(gameMap.size());
    System.out.println(gameMap.data());
  }
}
