package fr.uge.thebigadventure.models;

public record Coord(int x, int y) {
  public Coord {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid coord");
    }
  }
}
