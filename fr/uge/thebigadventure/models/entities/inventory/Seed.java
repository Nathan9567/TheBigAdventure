package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record Seed(String name) implements InventoryItem {

  @Override
  public InventoryItemType skin() {
    return InventoryItemType.SEED;
  }

}
