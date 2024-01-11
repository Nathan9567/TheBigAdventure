package fr.uge.thebigadventure.model;

import java.util.Objects;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.type.entity.EntityType;

public record ElementRef(EntityType type, String name) {
  public ElementRef {
    Objects.requireNonNull(type);
    // name can be null
  }

  public boolean looksLike(Object object) {
    return object instanceof Entity entity && type.equals(entity.skin()) && (name == null || name.equals(entity.name()));
  }
}
