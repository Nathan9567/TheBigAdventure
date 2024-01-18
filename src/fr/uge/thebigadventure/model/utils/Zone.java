package fr.uge.thebigadventure.model.utils;

import java.util.Objects;

/**
 * A zone in a {@link GameMap}, a positionned area.
 */
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
