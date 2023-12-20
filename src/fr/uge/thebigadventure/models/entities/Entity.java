package fr.uge.thebigadventure.models.entities;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

public interface Entity {
  String name();

  EntityType skin();

  default Coordinates position() {
    return null;
  }
}
