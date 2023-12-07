package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.Size;
import fr.uge.thebigadventure.models.Zone;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.models.enums.utils.Behavior;
import fr.uge.thebigadventure.models.enums.utils.Kind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class MapParser {
  private static final Pattern SECTIONS_PATTERN = Pattern.compile("\\[(\\w+)](.+?)(?=\\[|\\z)", Pattern.DOTALL);
  private static final Pattern ATTRIBUTES_PATTERN = Pattern.compile("\\s*(\\w+)\\s*:\\s*(.+?)(?=\\s*\\w*\\s*:|\\z)", Pattern.DOTALL);
  private static final Pattern SIZE_PATTERN = Pattern.compile("\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\)\\s*");
  private static final Pattern COORD_PATTERN = Pattern.compile("\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\)\\s*");
  private static final Pattern ZONE_PATTERN = Pattern.compile("\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\)\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\)\\s*");
  private static final Pattern ENCODINGS_PATTERN = Pattern.compile("\\s*(\\w+?)\\s*\\(\\s*(\\w)\\s*\\)\\s*");
  private static final Pattern GRID_DATA_PATTERN = Pattern.compile("\"\"\"\\s*\\n(.+)\\n([ ]*)\"\"\"", Pattern.DOTALL);
  private static final Pattern BOOLEAN_PATTERN = Pattern.compile("^true$", Pattern.CASE_INSENSITIVE);
  private final String text;
  private final MapBuilder builder;

  public MapParser(String text) {
    this.text = text;
    this.builder = new MapBuilder();
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

  public MapBuilder parse() {
    var matcher = SECTIONS_PATTERN.matcher(text);
    var pointer = 0;
    while (matcher.find()) {
      parseSection(matcher.group(1), matcher.group(2).trim());
      if (matcher.start() != pointer) {
        throw new IllegalArgumentException("Invalid map : garbage between character " + pointer + " and " + matcher.start() + ".");
      }
      pointer = matcher.end();
    }
    if (pointer != text.length()) {
      throw new IllegalArgumentException("Invalid map : stop reading at character " + pointer + ".");
    }
    return builder;
  }

  private void parseSection(String name, String content) {
    switch (name) {
      case "grid" -> parseAttributes(content, this::parseGridAttributes);
      case "element" -> parseElement(content);
      default ->
          throw new IllegalArgumentException("Invalid map : " + name + " doesn't exist");
    }
  }

  private void parseAttributes(String content, BiConsumer<String, String> attributeParser) {
    var matcher = ATTRIBUTES_PATTERN.matcher(content);
    var pointer = 0;
    while (matcher.find()) {
      attributeParser.accept(matcher.group(1), matcher.group(2));
      if (matcher.start() != pointer) {
        throw new IllegalArgumentException("Invalid map : bad attributes.");
      }
      pointer = matcher.end();
    }
    if (pointer != content.length()) {
      throw new IllegalArgumentException("Invalid map : stopped reading attributes.");
    }
  }

  private void parseGridAttributes(String name, String content) {
    switch (name) {
      case "size" -> parseGridAttributeSize(content);
      case "encodings" -> parseGridAttributeEncodings(content);
      case "data" -> parseGridAttributeData(content);
      default ->
          throw new IllegalArgumentException("Invalid map : grid attribute " + name + " doest not exists.");
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

  private void parseElement(String content) {
    parseAttributes(content, this::parseElementAttributes);
    builder.pushElementBuilder();
  }

  private void parseElementAttributes(String name, String content) {
    switch (name) {
      case "name" -> parseElementAttributeName(content);
      case "skin" -> parseElementAttributeSkin(content);
      case "player" -> parseElementAttributePlayer(content);
      case "position" -> parseElementAttributePosition(content);
      case "health" -> parseElementAttributeHealth(content);
      case "kind" -> parseElementAttributeKind(content);
      case "zone" -> parseElementAttributeZone(content);
      case "behavior" -> parseElementAttributeBehavior(content);
      case "damage" -> parseElementAttributeDamage(content);
      default -> {
        System.out.println("unknown attribute " + name + " skipping.");
        // throw new IllegalArgumentException("Invalid map : element attribute " + name + " doest not exists.");
      }
    }
  }

  private void parseElementAttributeName(String content) {
    builder.elementBuilder.setName(content);
  }

  private void parseElementAttributeSkin(String content) {
    builder.elementBuilder.setSkin(EntityType.fromString(content));
  }

  private void parseElementAttributePlayer(String content) {
    var matcher = BOOLEAN_PATTERN.matcher(content);
    builder.elementBuilder.setPlayer(matcher.matches());
  }

  private void parseElementAttributePosition(String content) {
    var matcher = COORD_PATTERN.matcher(content);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid position");
    }
    builder.elementBuilder.setPosition(new Coord(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
  }

  private void parseElementAttributeHealth(String content) {
    builder.elementBuilder.setHealth(Integer.parseInt(content));
  }

  private void parseElementAttributeKind(String content) {
    builder.elementBuilder.setKind(Kind.valueOf(content.toUpperCase()));
  }

  private void parseElementAttributeZone(String content) {
    var matcher = ZONE_PATTERN.matcher(content);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid position");
    }
    builder.elementBuilder.setZone(
        new Zone(
            new Coord(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2))
            ),
            new Size(
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4))
            )
        )
    );
  }

  private void parseElementAttributeBehavior(String content) {
    builder.elementBuilder.setBehavior(Behavior.valueOf(content.toUpperCase()));
  }

  private void parseElementAttributeDamage(String content) {
    builder.elementBuilder.setDamage(Integer.parseInt(content));
  }
}