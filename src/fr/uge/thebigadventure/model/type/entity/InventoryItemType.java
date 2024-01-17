package fr.uge.thebigadventure.model.type.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the type of inventory item.
 * This is a sealed interface, meaning that all implementations must be declared in this file.
 */
public sealed interface InventoryItemType extends EntityType permits InventoryItemRawType, FoodType {

  /**
   * Like the {@link Enum#valueOf(Class, String)} method, but for {@link InventoryItemType}.
   * This method is used to convert a string to an {@link InventoryItemType}.
   *
   * @param string the string to convert
   * @return the corresponding {@link InventoryItemType}
   */
  static InventoryItemType fromString(String string) {
    Map<String, InventoryItemType> nameToTypeMap =
        new HashMap<>(Stream.of(InventoryItemRawType.values(), FoodType.values())
            .flatMap(Arrays::stream)
            .collect(Collectors.toMap(Enum::name, Function.identity())));
    var res = nameToTypeMap.get(string);
    if (res != null)
      return res;
    throw new IllegalArgumentException("Invalid environment type " + string);
  }
}
