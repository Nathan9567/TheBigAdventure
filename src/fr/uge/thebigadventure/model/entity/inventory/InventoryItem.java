package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.EntityType;

public interface InventoryItem extends Entity {

  @Override
  EntityType skin();

  Coordinates position();

  default boolean isWeapon() {
    return false;
  }

}
