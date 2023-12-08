package fr.uge.thebigadventure.models;

import java.util.Objects;

public record Zone(Coord position, Size size) {
  public Zone {
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(size, "Size cannot be null");
  }

  public boolean contains(Coord coord) {
    return coord.x() >= position.x() && coord.x() < position.x() + size.width()
            && coord.y() >= position.y() && coord.y() < position.y() + size.height();
  }
}
