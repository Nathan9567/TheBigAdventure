package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;

public record Box(InventoryItemRawType skin, String name, Coordinates position,
                  Direction direction) implements InventoryItem {
  public Box {
    Objects.requireNonNull(skin);
  }

  public Box(InventoryItemRawType itemType) {
    this(itemType, null, null, null);
  }

  public Box(InventoryItemRawType itemType, String name) {
    this(itemType, name, null, null);
  }

}
