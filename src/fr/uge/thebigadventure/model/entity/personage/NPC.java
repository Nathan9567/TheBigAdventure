package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.utils.Zone;

public sealed interface NPC extends Personage permits Ally, Enemy {

  /**
   * Returns the zone of the NPC. If the NPC is not in a zone, returns null.
   *
   * @return the zone of the NPC
   */
  default Zone getZone() {
    return null;
  }
  
  @Override
  default boolean isEnemy() {
    return switch (this) {
      case Ally ignored -> false;
      case Enemy ignored -> true;
    };
  }

}
