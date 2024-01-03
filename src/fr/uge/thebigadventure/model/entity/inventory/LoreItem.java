package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.enums.entity.InventoryItemType;

public record LoreItem(InventoryItemType skin, String name,
                       String text,
                       Coordinates position) implements InventoryItem {

  public InventoryItemType skin() {
    return InventoryItemType.BOOK;
  }
}
