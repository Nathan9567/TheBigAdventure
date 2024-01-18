package fr.uge.thebigadventure.model.utils;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.type.entity.FoodType;
import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;
import fr.uge.thebigadventure.model.utils.builder.ElementBuilder;

import java.util.Objects;

/**
 * A pattern to match an element by its type and its name.
 */
public record ElementRef(InventoryItemType type, String name) {
  public ElementRef {
    Objects.requireNonNull(type);
    // name can be null
  }

  /**
   * Check if the given object is an {@link Entity} with the same skin and name.
   * @param object the object to check
   * @return true if the object is an entity with the same skin and name
   */
  public boolean looksLike(Object object) {
    return object instanceof Entity entity && type.equals(entity.skin()) && (name == null || name.equals(entity.name()));
  }

  /**
   * Return an {@link InventoryItem} that matches the {@link ElementRef}.
   * @return the inventory item
   */
  public InventoryItem toItem() {
    var builder = new ElementBuilder();
    builder.setSkin(type);
    if (name != null) {
      builder.setName(name);
    }
    return switch (type) {
      case FoodType foodType -> builder.toFoodEntity(foodType);
      case InventoryItemRawType rawType -> builder.toItemEntity(rawType);
    };
  }
}
