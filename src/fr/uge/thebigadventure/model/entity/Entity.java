package fr.uge.thebigadventure.model.entity;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.EntityType;

public interface Entity {
  String name();

  EntityType skin();

  default Coordinates position() {
    return null;
  }
}