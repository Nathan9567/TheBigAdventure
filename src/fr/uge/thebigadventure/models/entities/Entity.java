package fr.uge.thebigadventure.models.entities;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

public interface Entity {
  String name();

  EntityType skin();

  default Coord position() {
    return null;
  }
}
