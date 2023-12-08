package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.Size;
import fr.uge.thebigadventure.models.Zone;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.models.enums.utils.Behavior;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.models.enums.utils.Kind;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class MapParser {
  private static final Pattern SECTIONS_PATTERN =
      Pattern.compile("\\[(\\w+)](.+?)(?=\\[|\\z)", Pattern.DOTALL);
  private static final Pattern ATTRIBUTES_PATTERN =
      Pattern.compile("\\s*(\\w+)\\s*:\\s*(.+?)(?=\\s*\\w*\\s*:|\\z)",
          Pattern.DOTALL);
  private static final Pattern SIZE_PATTERN =
      Pattern.compile("\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\)\\s*");
  private static final Pattern COORD_PATTERN =
      Pattern.compile("\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\)\\s*");
  private static final Pattern ZONE_PATTERN =
      Pattern.compile("\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\)\\s*\\(\\s*(\\d+)\\s*x\\s*(\\d+)\\)\\s*");
  private static final Pattern ENCODINGS_PATTERN =
      Pattern.compile("\\s*(\\w+?)\\s*\\(\\s*(\\w)\\s*\\)\\s*");
  private static final Pattern GRID_DATA_PATTERN =
      Pattern.compile("\"\"\"\\s*\\n(.+)\\n( *)\"\"\"", Pattern.DOTALL);
  private static final Pattern BOOLEAN_PATTERN =
      Pattern.compile("^true$", Pattern.CASE_INSENSITIVE);
  private static final Pattern LOCK_PATTERN = Pattern.compile("(KEY|LEVER)\\s*(.+)");
  private final String text;
  private final MapBuilder builder = new MapBuilder();
  private int globalOffset = 0;

  public MapParser(String text) {
    this.text = text;
  }

  public static void main(String[] args) throws IOException {
    GameMap gameMap = GameMap.load("resources/test.map");
    System.out.println(gameMap.size());
    System.out.println(gameMap.data());
  }

  public MapBuilder parse() {
    var matcher = SECTIONS_PATTERN.matcher(text);
    var pointer = 0;
    while (matcher.find()) {
      if (matcher.start() != pointer) {
        System.err.println("Error while parsing map : can't read section " +
            "text between character #" + pointer + " and #"
            + matcher.start() + ".");
      }
      globalOffset = matcher.start();
      parseSection(matcher.group(1), matcher.group(2).trim());
      pointer = matcher.end();
    }
    if (pointer != text.length()) {
      System.err.println("Error while parsing map : can't read section text " +
          "after character #" + pointer + ".");
    }
    return builder;
  }

  private void parseSection(String name, String content) {
    switch (name) {
      case "grid" -> parseAttributes(content, this::parseGridAttributes);
      case "element" -> parseElement(content);
      default ->
          System.err.println("Error while parsing map : section \"" + name +
              "\" unknown at character #" + globalOffset + ".");
    }
  }

  private void parseElement(String content) {
    parseAttributes(content, this::parseElementAttributes);
    builder.pushElementBuilder();
  }

  private void parseAttributes(String content,
                               BiConsumer<String, String> attributeParser) {
    var matcher = ATTRIBUTES_PATTERN.matcher(content);
    var pointer = 0;
    while (matcher.find()) {
      if (matcher.start() != pointer) {
        System.err.println("Error while parsing map : can't read attributes " +
            "text between character #" + (globalOffset + pointer) + " and #" +
            (globalOffset + matcher.start()) + ".");
      }
      attributeParser.accept(matcher.group(1), matcher.group(2).trim());
      pointer = matcher.end();
    }
    if (pointer != content.length()) {
      System.err.println("Error while parsing map : can't read attributes " +
          "text after character #" + (globalOffset + pointer) + ".");
    }
  }

  private void parseGridAttributes(String name, String content) {
    switch (name) {
      case "size" -> parseGridAttributeSize(content);
      case "encodings" -> parseGridAttributeEncodings(content);
      case "data" -> parseGridAttributeData(content);
      default ->
          System.err.println("Error while parsing map : grid attribute \"" +
              name + "\".");
    }
  }

  private void parseGridAttributeSize(String content) {
    var matcher = SIZE_PATTERN.matcher(content);
    if (!matcher.matches()) {
      System.err.println("Error while parsing map : invalid size.");
    }
    builder.setSize(new Size(Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2))));
  }

  private void parseGridAttributeEncodings(String content) {
    var encodingMap = new HashMap<String, EntityType>();
    var matcher = ENCODINGS_PATTERN.matcher(content);
    var pointer = 0;
    while (matcher.find()) {
      if (matcher.start() != pointer) {
        System.err.println("Error while parsing map : error in encodings " +
            "before " + matcher.group(1) + "(" + matcher.group(2) + ").");
      }
      if (encodingMap.containsKey(matcher.group(2))) {
        System.err.println("Error while parsing map : error in encodings, " +
            "letter '" + matcher.group(2) + "' " +
            "is already associated with a skin.");
      }
      try {
        encodingMap.put(matcher.group(2), EntityType.fromString(matcher.group(1)));
      } catch (IllegalArgumentException e) {
        System.err.println("Error while parsing map : unknown skin \"" + matcher.group(1) + "\" in encodings.");
      }
      pointer = matcher.end();
    }
    if (pointer != content.length()) {
      System.err.println("Error while parsing map : can't finish " +
          "to read encodings.");
    }
    builder.setEncodings(encodingMap);
  }

  private void parseGridAttributeData(String content) {
    var matcher = GRID_DATA_PATTERN.matcher(content);
    if (!matcher.find())
      System.err.println("Error while parsing map : grid data invalid.");
    HashMap<Coord, Character> map = new HashMap<>();
    var y = 0;
    var x = 0;
    for (var lineMap : matcher.group(1).split("\n")) {
      x = -matcher.group(2).length();
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
    builder.setEffectiveSize(new Size(x, y));
    builder.setData(map);
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
      case "phantomized" -> parseElementAttributePhantomized(content);
      case "teleport" -> parseElementAttributeTeleport(content);
      case "flow" -> parseElementAttributeFlow(content);
      default ->
          System.err.println("Error while parsing map : unknown attribute "
              + name + " skipping.");
    }
  }

  private void parseElementAttributeName(String content) {
    builder.elementBuilder.setName(content);
  }

  private void parseElementAttributeSkin(String content) {
    try {
      builder.elementBuilder.setSkin(EntityType.fromString(content));
    } catch (IllegalArgumentException e) {
      System.err.println("Error while parsing map : unknown skin \"" + content + "\".");
    }
  }

  private void parseElementAttributePlayer(String content) {
    var matcher = BOOLEAN_PATTERN.matcher(content);
    // TODO : test plus en profondeur
    builder.elementBuilder.setPlayer(matcher.matches());
  }

  private void parseElementAttributePosition(String content) {
    var matcher = COORD_PATTERN.matcher(content);
    if (!matcher.matches()) {
      System.err.println("Error while parsing map : invalid position.");
      return;
    }
    builder.elementBuilder.setPosition(new Coord(
        Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2))
        ));
  }

  private void parseElementAttributeHealth(String content) {
    try {
      builder.elementBuilder.setHealth(Integer.parseInt(content));
    } catch (NumberFormatException e) {
      System.err.println("Error while parsing map : health \"" + content + "\" is not a number.");
    }
  }

  private void parseElementAttributeKind(String content) {
    try {
      builder.elementBuilder.setKind(Kind.valueOf(content.toUpperCase(Locale.ROOT)));
    } catch (IllegalArgumentException e) {
      System.err.println("Error while parsing map : invalid kind \"" + content.toUpperCase(Locale.ROOT) + "\".");
    }
  }

  private void parseElementAttributeZone(String content) {
    var matcher = ZONE_PATTERN.matcher(content);
    if (!matcher.matches()) {
      System.err.println("Error while parsing map : invalid zone.");
      return;
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
    try {      
      builder.elementBuilder.setBehavior(Behavior.valueOf(content.toUpperCase(Locale.ROOT)));
    } catch (IllegalArgumentException e) {
      System.err.println("Error while parsing map : invalid behavior \"" + content.toUpperCase(Locale.ROOT) + "\".");
    }
  }

  private void parseElementAttributeDamage(String content) {
    try {      
      builder.elementBuilder.setDamage(Integer.parseInt(content));
    } catch (NumberFormatException e) {
      System.err.println("Error while parsing map : damage \"" + content + "\" is not a number.");
    }
  }

  /*private void parseElementAttributeLocked(String content) {
    var matcher = LOCK_PATTERN.matcher(content);
    matcher.matches();
    builder.elementBuilder.setLocked(matcher.group(1), matcher.group(2));
  }*/
  
  private void parseElementAttributeFlow(String content) {
    try {      
      builder.elementBuilder.setFlow(Direction.valueOf(content));
    } catch (IllegalArgumentException e) {
      System.err.println("Error while parsing map : invalid flow \"" + content + "\".");
    }
  }

  private void parseElementAttributePhantomized(String content) {
    var matcher = BOOLEAN_PATTERN.matcher(content);
    // TODO : test plus en profondeur
    builder.elementBuilder.setPhantomized(matcher.matches());
  }

  private void parseElementAttributeTeleport(String content) {
    builder.elementBuilder.setTeleport(content);
  }
}
