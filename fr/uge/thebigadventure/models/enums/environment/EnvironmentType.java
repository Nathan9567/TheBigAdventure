package fr.uge.thebigadventure.models.enums.environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public sealed interface EnvironmentType permits BiomeType, DecorationType, ObstacleType {

  Map<String, EnvironmentType> nameToTypeMap = new HashMap<>();

  static EnvironmentType fromString(String string) {
    if (nameToTypeMap.isEmpty()) {
      nameToTypeMap.putAll(Arrays.stream(BiomeType.values()).collect(Collectors.toMap(BiomeType::name, Function.identity())));
      nameToTypeMap.putAll(Arrays.stream(DecorationType.values()).collect(Collectors.toMap(DecorationType::name, Function.identity())));
      nameToTypeMap.putAll(Arrays.stream(ObstacleType.values()).collect(Collectors.toMap(ObstacleType::name, Function.identity())));
    }
    var res = nameToTypeMap.get(string);
    if (res != null)
      return res;
    throw new IllegalArgumentException("Invalid environment type " + string);
  }
}
