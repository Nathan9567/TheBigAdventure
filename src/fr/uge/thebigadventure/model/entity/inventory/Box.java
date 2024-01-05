package fr.uge.thebigadventure.model.entity.inventory;

import java.util.Objects;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;
import fr.uge.thebigadventure.model.type.util.Direction;

public record Box(InventoryItemType skin, String name, Coordinates position,
                  Direction direction) implements InventoryItem {
  public Box {
    Objects.requireNonNull(skin);
  }

  public Box(InventoryItemType itemType) {
    this(itemType, null, null, null);
  }

  public Box(InventoryItemType itemType, String name) {
    this(itemType, name, null, null);
  }

}
