package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;
import fr.uge.thebigadventure.models.enums.utils.Direction;

import java.util.Objects;

public record Box(InventoryItemType skin, String name, Coord position,
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
