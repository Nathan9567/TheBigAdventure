package fr.uge.thebigadventure.model.utils;

import fr.uge.thebigadventure.model.GameMap;

import java.util.Objects;

/**
 * A zone in a {@link GameMap}, a positioned area.
 *
 * @param position the position of the zone
 *                 (the top left corner of the zone)
 * @param size     the size of the zone
 */
public record Zone(Coordinates position, Size size) {
  public Zone {
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(size, "Size cannot be null");
  }

  /**
   * Check if the zone contains the given coordinates.
   * The zone contains the coordinates if the coordinates are in the zone.
   *
   * @param coordinates the coordinates to check
   * @return true if the zone contains the coordinates, false otherwise
   */
  public boolean contains(Coordinates coordinates) {
    return coordinates.x() >= position.x() && coordinates.x() < position.x() + size.width()
        && coordinates.y() >= position.y() && coordinates.y() < position.y() + size.height();
  }
}
