package fr.uge.thebigadventure.models.enums.entities;

public enum ObstacleType implements EntityType {
  BED, BOG, BOMB, BRICK, CHAIR, CLIFF, DOOR, FENCE, FORT, GATE, HEDGE, HOUSE,
  HUSK, HUSKS, LOCK, MONITOR, PIANO, PILLAR, PIPE, ROCK, RUBBLE, SHELL, SIGN,
  SPIKE, STATUE, STUMP, TABLE, TOWER, TREE, TREES, WALL;

  @Override
  public String folder() {
    return "obstacles";
  }
}
