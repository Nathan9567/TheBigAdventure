package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.enums.entity.InventoryItemType;

public interface InventoryItem extends Entity {

  @Override
  InventoryItemType skin();

  Coordinates position();

  default boolean isWeapon() {
    return false;
  }

}
