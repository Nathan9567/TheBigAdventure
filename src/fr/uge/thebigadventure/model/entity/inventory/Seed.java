package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.enums.entity.InventoryItemType;

public record Seed(String name, Coordinates position) implements InventoryItem {

  @Override
  public InventoryItemType skin() {
    return InventoryItemType.SEED;
  }

}
