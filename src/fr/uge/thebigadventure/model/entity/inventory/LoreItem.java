package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public record LoreItem(InventoryItemRawType skin, String name,
                       String text,
                       Coordinates position) implements InventoryItem {

  public InventoryItemRawType skin() {
    return InventoryItemRawType.BOOK;
  }
}
