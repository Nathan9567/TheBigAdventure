package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record Seed(String name, Coordinates position) implements InventoryItem {

  @Override
  public InventoryItemType skin() {
    return InventoryItemType.SEED;
  }

}
