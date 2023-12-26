package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public interface InventoryItem extends Entity {

  @Override
  InventoryItemType skin();

  Coordinates position();

  default boolean isWeapon() {
    return false;
  }

}
