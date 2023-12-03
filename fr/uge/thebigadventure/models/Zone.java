package fr.uge.thebigadventure.models;

import java.util.Objects;

public record Zone(Coord position, Size size) {
  public Zone {
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(size, "Size cannot be null");

  }
}
