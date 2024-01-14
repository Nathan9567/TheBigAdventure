package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public interface InventoryItem extends Entity {

  @Override
  EntityType skin();

  @Override
  Coordinates position();

  default boolean isWeapon() {
    return false;
  }

  default boolean isFood() {
    return false;
  }

}
