package fr.uge.thebigadventure.model.type.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the type of available entities in the game.
 * Each entity type is a singleton. The name of the entity type is the name of the enum.
 */
public sealed interface EntityType permits BiomeType, DecorationType, EffectType, InventoryItemType, ObstacleType, OtherType, PersonageType, TransportType {

  /**
   * Map used to find the entity type corresponding to the given string.
   */
  Map<String, EntityType> nameToTypeMap = new HashMap<>();

  /**
   * Returns the entity type corresponding to the given string.
   * The string must be the name of the entity type.
   * WARNING: here we use a map to find the entity type corresponding to the given string.
   * This is not the best way to do it cause if we have a same name for two different entity types
   * we will have a problem.
   *
   * @param string the name of the entity type
   * @return the entity type corresponding to the given string
   */
  static EntityType fromString(String string) {
    if (nameToTypeMap.isEmpty()) {
      nameToTypeMap.putAll(Stream.of(BiomeType.values(),
              DecorationType.values(), ObstacleType.values(),
              EffectType.values(), InventoryItemRawType.values(),
              PersonageType.values(), OtherType.values(),
              TransportType.values(), FoodType.values())
          .flatMap(Arrays::stream)
          .collect(Collectors.toMap(Enum::name, Function.identity())));
    }
    var res = nameToTypeMap.get(string);
    if (res != null)
      return res;
    throw new IllegalArgumentException("Invalid environment type " + string);
  }

  /**
   * Returns the name of the entity type.
   *
   * @return the name of the entity type
   */
  String name();

  /**
   * Returns true if the entity type is an obstacle.
   * By default, an entity type is not an obstacle.
   * Only {@link ObstacleType}, {@link BiomeType} and {@link PersonageType} are obstacles.
   *
   * @return if the entity type is an obstacle.
   */
  default boolean isObstacle() {
    return switch (this) {
      case ObstacleType ignored -> true;
      case BiomeType ignored -> true;
      case PersonageType ignored -> true;
      default -> false;
    };
  }
}
