package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public record Seed(String name, Coordinates position) implements InventoryItem {

  @Override
  public InventoryItemRawType skin() {
    return InventoryItemRawType.SEED;
  }

}
