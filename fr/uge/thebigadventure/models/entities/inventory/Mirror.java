package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record Mirror(String name) implements InventoryItem {

  public Mirror() {
    this("Mirror");
  }

  @Override
  public InventoryItemType getSkin() {
    return InventoryItemType.MIRROR;
  }

}
