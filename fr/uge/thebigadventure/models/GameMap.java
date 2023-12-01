package fr.uge.thebigadventure.models;

import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

import java.util.Map;
import java.util.Objects;

public record GameMap(Size size, Map<Coord, EntityType> data, Map<Coord,
    Entity> elements) {
  public GameMap {
    Objects.requireNonNull(size, "Size cannot be null");
    if (size.width() <= 0 || size.height() <= 0) {
      throw new IllegalArgumentException("Invalid map size");
    }
    Objects.requireNonNull(data, "Data cannot be null");
  }


}
