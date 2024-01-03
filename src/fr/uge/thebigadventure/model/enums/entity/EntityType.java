package fr.uge.thebigadventure.model.enums.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public sealed interface EntityType permits EffectType,
    InventoryItemType, PersonageType, OtherType, TransportType,
    BiomeType, DecorationType, ObstacleType {

  Map<String, EntityType> nameToTypeMap = new HashMap<>();

  static EntityType fromString(String string) {
    if (nameToTypeMap.isEmpty()) {
      nameToTypeMap.putAll(Stream.of(BiomeType.values(),
              DecorationType.values(), ObstacleType.values(),
              EffectType.values(), InventoryItemType.values(),
              PersonageType.values(), OtherType.values(),
              TransportType.values())
          .flatMap(Arrays::stream)
          .collect(Collectors.toMap(Enum::name, Function.identity())));
    }
    var res = nameToTypeMap.get(string);
    if (res != null)
      return res;
    throw new IllegalArgumentException("Invalid environment type " + string);
  }

  String name();

  default boolean isObstacle() {
    return switch (this) {
      case ObstacleType ignored -> true;
      case BiomeType ignored -> true;
      case PersonageType ignored -> true;
      default -> false;
    };
  }
}
