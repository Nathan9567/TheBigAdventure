package fr.uge.thebigadventure.model.entity;

import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * An entity is an object that can be placed in the world.
 * It can be an obstacle, a player, an enemy, a weapon, a food, etc.
 */
public interface Entity {
  /**
   * The name of the entity.
   *
   * @return the name of the entity
   */
  String name();

  /**
   * The skin of the entity. It is used to display the entity.
   * The skin is an enum and also the name of the image file.
   *
   * @return the skin of the entity
   */
  EntityType skin();

  /**
   * The position of the entity in the world.
   * If the entity is not in the world, it returns null.
   * For example if the entity is in the inventory of a player.
   *
   * @return the position of the entity in the world
   */
  default Coordinates position() {
    return null;
  }
}
