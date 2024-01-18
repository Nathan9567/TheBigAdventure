package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * Interface representing a personage.
 * A personage is an entity that can move.
 */
public sealed interface Personage extends Entity permits Ghost, NPC, Player {
  @Override
  PersonageType skin();

  @Override
  Coordinates position();

  /**
   * Sets the position of the personage.
   * This method is used to move the personage.
   *
   * @param position the new position of the personage
   */
  void setPosition(Coordinates position);

  /**
   * Method used to know if the personage is an enemy.
   * If the personage is an enemy, it will attack the player.
   *
   * @return true if the personage is an enemy, false otherwise
   */
  default boolean isEnemy() {
    return switch (this) {
      case Ghost ghost -> ghost.getUnderlyingPersonage().isEnemy();
      case Player ignored -> false;
      case NPC npc -> npc.isEnemy();
    };
  }
}
