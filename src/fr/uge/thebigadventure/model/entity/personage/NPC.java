package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.utils.Zone;

public sealed interface NPC extends Personage permits Ally, Enemy {

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
