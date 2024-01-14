package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;

public record Key(String name, Coordinates position) implements InventoryItem {

  public Key {
    Objects.requireNonNull(name);
  }

  public InventoryItemRawType skin() {
    return InventoryItemRawType.KEY;
  }

}
