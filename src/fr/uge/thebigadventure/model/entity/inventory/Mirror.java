package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;

public record Mirror(String name,
                     Coordinates position) implements InventoryItem {

  @Override
  public InventoryItemType skin() {
    return InventoryItemType.MIRROR;
  }

}
