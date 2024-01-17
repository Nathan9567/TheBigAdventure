package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * An inventory item is an object that can be placed in the inventory.
 * It can be a weapon, a food, etc.
 */
public interface InventoryItem extends Entity {
  @Override
  Coordinates position();

  /**
   * Indicates if the item is a weapon.
   * If it is, it can be used to attack.
   *
   * @return true if the item is a weapon, false otherwise
   */
  default boolean isWeapon() {
    return false;
  }

  /**
   * Indicates if the item is food. If it is, it can maybe be eaten
   * depends on if the player is hungry or not and if the food is edible
   *
   * @return true if the item is food, false otherwise
   */
  default boolean isFood() {
    return false;
  }

}
