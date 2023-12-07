package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record LoreItem(InventoryItemType skin, String name,
                       String text) implements InventoryItem {

  public InventoryItemType skin() {
    return InventoryItemType.BOOK;
  }
}
