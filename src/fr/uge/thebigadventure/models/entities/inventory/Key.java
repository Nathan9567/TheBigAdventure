package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

import java.util.Objects;

public record Key(String name, Coord position) implements InventoryItem {

  public Key {
    Objects.requireNonNull(name);
  }

  public InventoryItemType skin() {
    return InventoryItemType.KEY;
  }

}
