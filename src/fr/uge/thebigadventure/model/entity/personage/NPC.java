package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.Zone;

public sealed interface NPC extends Personage permits Ally, Enemy, Ghost {

  default Zone getZone() {
    return null;
  }

  default boolean isEnemy() {
    return switch (this) {
      case Ally ignored -> false;
      case Enemy ignored -> true;
      case Ghost ignored -> false;
    };
  }

}
