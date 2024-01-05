package fr.uge.thebigadventure.model;

import java.util.Objects;

import fr.uge.thebigadventure.model.enums.entity.EntityType;

public record ElementRef(EntityType type, String name) {
  public ElementRef {
    Objects.requireNonNull(type);
    // name can be null
  }
}
