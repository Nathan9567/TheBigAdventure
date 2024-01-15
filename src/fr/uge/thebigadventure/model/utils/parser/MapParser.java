package fr.uge.thebigadventure.model.utils.parser;

import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;
import fr.uge.thebigadventure.model.type.util.Behavior;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.type.util.Kind;
import fr.uge.thebigadventure.model.utils.*;
import fr.uge.thebigadventure.model.utils.builder.MapBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
  private static final Pattern TEXT_PATTERN =
      Pattern.compile("\"\"\"\\s*\\R(.+)\\R( *)\"\"\"", Pattern.DOTALL);
  private static final Pattern BOOLEAN_PATTERN =
      Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
  private static final Pattern LOCK_PATTERN = Pattern.compile("(KEY|LEVER)\\s*(.+)");
  private static final Pattern TRADE_PATTERN = Pattern.compile("(\\w+)\\s*->\\s*(\\w+)(\\s+(\\w+))?");
  private static final String UNKNOWN_SKIN = "Unknown skin";
  private final MapBuilder builder = new MapBuilder();
  private final String text;
  private int sectionPointer = 0;
  private int attributePointer = 0;

  /**
   * Create a new map parser.
   *
   * @param text the text to parse
   */
  public MapParser(String text) {
    this.text = text;
  }

  /**
   * Print an error message with the line number.
   *
   * @param pointer the character number in the text where the error occurred
   * @param msg     the error message
   */
  private void errorLine(int pointer, String msg) {
    var line = text.substring(0, pointer).split("\\R", -1).length;
    System.err.println("Error while parsing map at line " + line + " : " + msg);
  }

  /**
   * Parse the text and return the map builder.
   *
   * @return the map builder
   */
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
    } catch (IllegalArgumentException | IllegalStateException |
             NullPointerException e) {
      errorLine(sectionPointer, "can't create element : " + e.getMessage());
    }
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
      default ->
          errorLine(attributePointer, "Unknown grid attribute \"" + name + "\"");
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
          errorLine(attributePointer + pointer, UNKNOWN_SKIN + " \"" + matcher.group(1) + "\" in encodings");
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
    var matcher = TEXT_PATTERN.matcher(content);
    if (!matcher.find())
      errorLine(attributePointer, "Grid data invalid");
    HashMap<Coordinates, Character> map = new HashMap<>();
    var y = 0;
    var x = 0;
    var startCol = matcher.group(2).length();
    var linesIterator = matcher.group(1).lines().iterator();
    while (linesIterator.hasNext()) {
      var lineMap = linesIterator.next();
      x = -startCol;
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
      case "text" -> parseElementAttributeText(content);
      case "steal" -> parseElementAttributeSteal(content);
      case "trade" -> parseElementAttributeTrade(content);
      case "flow" -> parseElementAttributeFlow(content);
      case "locked" -> parseElementAttributeLocked(content);
      case "phantomized" -> parseElementAttributePhantomized(content);
      case "teleport" -> parseElementAttributeTeleport(content);
      default ->
          errorLine(attributePointer, "Unknown element attribute \"" + name + "\"");
    }
  }

  private void parseElementAttributeName(String content) {
    builder.elementBuilder().setName(content);
  }

  private void parseElementAttributeText(String content) {
    if (content.startsWith("\"\"\"")) {
      var matcher = TEXT_PATTERN.matcher(content);
      if (!matcher.find()) {
        errorLine(attributePointer, "Bad text format");
        return;
      }
      var nSpaces = matcher.group(2).length();
      var textAttribute = matcher.group(1).lines().map(
          s -> s.substring(nSpaces)).collect(Collectors.joining("\n"));
      builder.elementBuilder().setText(textAttribute);
    } else {
      builder.elementBuilder().setText(content);
    }
  }

  private void parseElementAttributeSkin(String content) {
    try {
      builder.elementBuilder().setSkin(EntityType.fromString(content));
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, UNKNOWN_SKIN + " \"" + content + "\"");
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
    builder.elementBuilder().setPlayer(parseAttributeBoolean(content));
  }

  private void parseElementAttributePosition(String content) {
    var matcher = COORD_PATTERN.matcher(content);
    if (!matcher.matches()) {
      errorLine(attributePointer, "Invalid position");
      return;
    }
    builder.elementBuilder().setPosition(new Coordinates(
        Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2))
    ));
  }

  private void parseElementAttributeHealth(String content) {
    try {
      builder.elementBuilder().setHealth(Integer.parseInt(content));
    } catch (NumberFormatException e) {
      errorLine(attributePointer, "health \"" + content + "\" is not a number");
    }
  }

  private void parseElementAttributeKind(String content) {
    try {
      builder.elementBuilder().setKind(Kind.valueOf(content.toUpperCase(Locale.ROOT)));
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
    builder.elementBuilder().setZone(
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
      builder.elementBuilder().setBehavior(Behavior.valueOf(content.toUpperCase(Locale.ROOT)));
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, "invalid behavior \"" + content + "\"");
    }
  }

  private void parseElementAttributeDamage(String content) {
    try {
      builder.elementBuilder().setDamage(Integer.parseInt(content));
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
      builder.elementBuilder().setTrades(trades);
    }
  }

  private ElementRef parseTradeElementRef(String skin, String name) {
    var type = getInventoryItemTypeFromAttribute(skin);
    if (type == null) {
      return null;
    }
    return new ElementRef(type, name);
  }

  private InventoryItemType getInventoryItemTypeFromAttribute(String skin) {
    try {
      return InventoryItemType.fromString(skin);
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, UNKNOWN_SKIN + " \"" + skin + "\"");
    }
    return null;
  }

  private void parseElementAttributeSteal(String content) {
    var steal = Arrays.stream(content.split(","))
        .map(String::trim)
        .map(this::getInventoryItemTypeFromAttribute)
        .filter(Predicate.not(Objects::isNull)).toList();
    builder.elementBuilder().setSteal(steal);
  }

  private void parseElementAttributeLocked(String content) {
    var matcher = LOCK_PATTERN.matcher(content);
    if (!matcher.matches()) {
      errorLine(attributePointer, "invalid locked");
      return;
    }
    builder.elementBuilder().setLocked(new ElementRef(
        InventoryItemRawType.valueOf(matcher.group(1)),
        matcher.group(2).trim()
    ));
  }

  private void parseElementAttributeFlow(String content) {
    try {
      builder.elementBuilder().setFlow(Direction.valueOf(content));
    } catch (IllegalArgumentException e) {
      errorLine(attributePointer, "invalid flow \"" + content + "\"");
    }
  }

  private void parseElementAttributePhantomized(String content) {
    builder.elementBuilder().setPhantomized(parseAttributeBoolean(content));
  }

  private void parseElementAttributeTeleport(String content) {
    builder.elementBuilder().setTeleport(content);
  }
}
