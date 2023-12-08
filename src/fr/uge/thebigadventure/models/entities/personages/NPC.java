package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Zone;

public sealed interface NPC extends Personage permits Ally, Enemy {

  default Zone getZone() {
    return null;
  }

}
