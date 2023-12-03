package fr.uge.thebigadventure.models.entities;

import fr.uge.thebigadventure.models.enums.entities.EntityType;

public record DumbEntity(String name, EntityType skin) implements Entity {
  @Override
  public EntityType getSkin() {
    return skin;
  }
}
