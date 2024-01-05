package fr.uge.thebigadventure.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.Size;
import fr.uge.thebigadventure.model.Trade;
import fr.uge.thebigadventure.model.ElementRef;
import fr.uge.thebigadventure.model.Zone;
import fr.uge.thebigadventure.model.enums.entity.EntityType;
import fr.uge.thebigadventure.model.enums.util.Behavior;
import fr.uge.thebigadventure.model.enums.util.Direction;
import fr.uge.thebigadventure.model.enums.util.Kind;

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
      Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
  private static final Pattern LOCK_PATTERN = Pattern.compile("(KEY|LEVER)\\s*(.+)");
  private static final Pattern TRADE_PATTERN = Pattern.compile("(\\w+)\\s*->\\s*(\\w+)(\\s+(\\w+))?");
  private final String text;
  private final MapBuilder builder = new MapBuilder();
  private int sectionPointer = 0;
  private int attributePointer = 0;

  public MapParser(String text) {
    this.text = text;
  }

  public static void main(String[] args) throws IOException {
    GameMap gameMap = GameMap.load(Path.of("resources/test.map"));
    System.out.println(gameMap.size());
    System.out.println(gameMap.data());
    System.out.println(gameMap.elements());
  }

  private void errorLine(int pointer, String msg) {
    var line = text.substring(0, pointer).split("\n", -1).length;
    System.err.println("Error while parsing map at line " + line + " : " + msg);
  }
  
  public MapBuilder parse() {
    var matcher = SECTIONS_PATTERN.matcher(text);
    var pointer = 0;
    while (matcher.find()) {
      sectionPointer = matcher.start();
      if (matcher.start() != pointer) {
        errorLine(sectionPointer, "Cannot parse a section until here");
      }
      parseSection(matcher.group(1), matcher.group(2).trim());
      pointer = matcher.end();
    }
    if (pointer != text.length()) {
      errorLine(pointer, "Cannot parse any section from here");
    }
    return builder;
  }

  private void parseSection(String name, String content) {
    switch (name) {
      case "grid" -> parseAttributes(content, this::parseGridAttributes);
      case "element" -> parseElement(content);
      default -> errorLine(sectionPointer, "Section \"" + name + "\" unknown");
    }
  }

  private void parseElement(String content) {
    parseAttributes(content, this::parseElementAttributes);
    try {
      builder.pushElementBuilder();
    } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
      errorLine(sectionPointer, "can't create element : " + e.getMessage());
    } // TODO custom exception ?
  }

  private void parseAttributes(String content,
                               BiConsumer<String, String> attributeParser) {
    var matcher = ATTRIBUTES_PATTERN.matcher(content);
    var pointer = 0;
    while (matcher.find()) {
      attributePointer = sectionPointer + matcher.start();
      if (matcher.start() != pointer) {
        errorLine(attributePointer, "Cannot parse an attribute until here");
      }
      attributeParser.accept(matcher.group(1), matcher.group(2).trim());
      pointer = matcher.end();
    }
    if (pointer != content.length()) {
      errorLine(sectionPointer + pointer, "Cannot parse any attribute from here");
    }
  }

  private void parseGridAttributes(String name, String content) {
    switch (name) {
      case "size" -> parseGridAttributeSize(content);
      case "encodings" -> parseGridAttributeEncodings(content);
      case "data" -> parseGridAttributeData(content);
      default -> errorLine(attributePointer, "Unknown grid attribute \"" + name + "\"");
    }
  }

  private void parseGridAttributeSize(String content) {
    var matcher = SIZE_PATTERN.matcher(content);
    if (!matcher.matches()) {
      errorLine(attributePointer, "Invalid size");
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
        errorLine(attributePointer + pointer, "Cannot parse an encoding until here");
      }
      if (encodingMap.containsKey(matcher.group(2))) {
        errorLine(attributePointer + pointer, "Error in encodings, letter '" + matcher.group(2) + "' is already associated with a skin");
      } else {
        try {
            encodingMap.put(matcher.group(2), EntityType.fromString(matcher.group(1)));
        } catch (IllegalArgumentException e) {
          errorLine(attributePointer + pointer, "Unknown skin \"" + matcher.group(1) + "\" in encodings");
        }
      }
      pointer = matcher.end();
    }
    if (pointer != content.length()) {
      errorLine(attributePointer + pointer, "Cannot parse any encoding from here");
    }
    builder.setEncodings(encodingMap);
  }

  private void parseGridAttributeData(String content) {
    var matcher = GRID_DATA_PATTERN.matcher(content);
    if (!matcher.find())
      errorLine(attributePointer, "Grid data invalid");
    HashMap<Coordinates, Character> map = new HashMap<>();
    var y = 0;
    var x = 0;
    for (var lineMap : matcher.group(1).split("\n")) {
      x = -matcher.group(2).length();
      for (var charMap : lineMap.toCharArray()) {
        if (charMap == ' ') {
          x++;
          continue;
        }
        map.put(new Coordinates(x, y), charMap);
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
      case "locked" -> parseElementAttributeLocked(content);
      case "steal" -> parseElementAttributeSteal(content);
      case "trade" -> parseElementAttributeTrade(content);
      default -> errorLine(attributePointer, "Unknown element attribute \"" + name + "\"");
    }
  }

  private void parseElementAttributeName(String content) {
    builder.elementBuilder.setName(content);
  }

  private void parseElementAttributeSkin(String content) {
    try {
      builder.elementBuilder.setSkin(EntityType.fromString(content));
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, "Unknown skin \"" + content + "\"");
    }
  }

  private boolean parseAttributeBoolean(String content) {
    var matcher = BOOLEAN_PATTERN.matcher(content);
    var bool = false;
    if (matcher.matches()) {
      bool = matcher.group().equalsIgnoreCase("true");
    } else {
      errorLine(attributePointer, "\"" + content + "\" is not a boolean, consider using \"true\" or \"false\", defaulting to false");
    }
    return bool;
  }

  private void parseElementAttributePlayer(String content) {
    builder.elementBuilder.setPlayer(parseAttributeBoolean(content));
  }

  private void parseElementAttributePosition(String content) {
    var matcher = COORD_PATTERN.matcher(content);
    if (!matcher.matches()) {
      errorLine(attributePointer, "Invalid position");
      return;
    }
    builder.elementBuilder.setPosition(new Coordinates(
        Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2))
    ));
  }

  private void parseElementAttributeHealth(String content) {
    try {
      builder.elementBuilder.setHealth(Integer.parseInt(content));
    } catch (NumberFormatException e) {
      errorLine(attributePointer, "health \"" + content + "\" is not a number");
    }
  }

  private void parseElementAttributeKind(String content) {
    try {
      builder.elementBuilder.setKind(Kind.valueOf(content.toUpperCase(Locale.ROOT)));
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, "invalid kind \"" + content + "\"");
    }
  }

  private void parseElementAttributeZone(String content) {
    var matcher = ZONE_PATTERN.matcher(content);
    if (!matcher.matches()) {
      errorLine(attributePointer, "invalid zone");
      return;
    }
    builder.elementBuilder.setZone(
        new Zone(
            new Coordinates(
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
      errorLine(attributePointer, "invalid behavior \"" + content + "\"");
    }
  }

  private void parseElementAttributeDamage(String content) {
    try {
      builder.elementBuilder.setDamage(Integer.parseInt(content));
    } catch (NumberFormatException e) {
      errorLine(attributePointer, "damage \"" + content + "\" is not a number");
    }
  }

  private void parseElementAttributeTrade(String content) {
    var trades = Arrays.stream(content.split(",")).map(String::trim).map(s -> {
      var matcher = TRADE_PATTERN.matcher(s);
      if (!matcher.matches()) {
        errorLine(attributePointer, "can't parse trade \"" + s + "\"");
        return null;
      }
      try {
        return new Trade(
            parseTradeElementRef(matcher.group(1), null),
            parseTradeElementRef(matcher.group(2), matcher.group(4))
            );
      } catch (NullPointerException e) {
        return null;
      }
    }).filter(Predicate.not(Objects::isNull)).toList();

    if (trades.isEmpty()) {
      errorLine(attributePointer, "can't parse any trade");
    } else {
      builder.elementBuilder.setTrades(trades);
    }
  }

  private ElementRef parseTradeElementRef(String skin, String name) {
    var type = getEntityTypeFromAttribute(skin);
    if (type == null) {
      return null;
    }
    return new ElementRef(type, name);
  }

  private EntityType getEntityTypeFromAttribute(String skin) {
    try {
      var type = EntityType.fromString(skin);
      return type;
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, "Unknown skin \"" + skin + "\"");
    }
    return null;
  }

  private void parseElementAttributeSteal(String content) {
    var steal = Arrays.stream(content.split(","))
        .map(String::trim)
        .map(this::getEntityTypeFromAttribute)
        .filter(Predicate.not(Objects::isNull)).toList();
    builder.elementBuilder.setSteal(steal);
  }

  private void parseElementAttributeLocked(String content) {
    var matcher = LOCK_PATTERN.matcher(content);
    if (!matcher.matches()) {
      errorLine(attributePointer, "invalid locked");
      return;
    }
    builder.elementBuilder.setLocked(new ElementRef(
        EntityType.fromString(matcher.group(1)),
        matcher.group(2).trim()
        ));
  }

  private void parseElementAttributeFlow(String content) {
    try {
      builder.elementBuilder.setFlow(Direction.valueOf(content));
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, "invalid flow \"" + content + "\"");
    }
  }

  private void parseElementAttributePhantomized(String content) {
    builder.elementBuilder.setPhantomized(parseAttributeBoolean(content));
  }

  private void parseElementAttributeTeleport(String content) {
    builder.elementBuilder.setTeleport(content);
  }
}
