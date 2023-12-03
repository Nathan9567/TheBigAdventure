package fr.uge.thebigadventure.models.entities.inventory.lore;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record Paper(String name, String text) implements LoreItem {

  @Override
  public InventoryItemType skin() {
    return InventoryItemType.PAPER;
  }
}
