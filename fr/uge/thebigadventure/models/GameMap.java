package fr.uge.thebigadventure.models;

import fr.uge.thebigadventure.models.enums.environment.EnvironmentType;
import fr.uge.thebigadventure.models.interpreter.Size;

import java.util.Map;
import java.util.Objects;

public record GameMap(Size size, Map<Coord, EnvironmentType> data) {
  public GameMap {
    if (size.width() <= 0 || size.height() <= 0) {
      throw new IllegalArgumentException("Invalid map size");
    }
    Objects.requireNonNull(data);
  }


}
