package fr.uge.thebigadventure.model.entity.inventory;

import java.util.Objects;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;

public record Key(String name, Coordinates position) implements InventoryItem {

  public Key {
    Objects.requireNonNull(name);
  }

  public InventoryItemType skin() {
    return InventoryItemType.KEY;
  }

}
