package fr.uge.thebigadventure.models;

import java.util.Objects;

public record Zone(Coordinates position, Size size) {
  public Zone {
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(size, "Size cannot be null");
  }

  public boolean contains(Coordinates coordinates) {
    return coordinates.x() >= position.x() && coordinates.x() < position.x() + size.width()
        && coordinates.y() >= position.y() && coordinates.y() < position.y() + size.height();
  }
}
