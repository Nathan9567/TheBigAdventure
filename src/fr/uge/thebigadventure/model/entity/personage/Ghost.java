package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.entity.obstacle.Obstacle;
import fr.uge.thebigadventure.model.type.entity.ObstacleType;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.List;
import java.util.Objects;

public final class Ghost implements Personage {

  private final Personage personage;
  List<ObstacleType> obstacleList = List.of(
      ObstacleType.BED, ObstacleType.BRICK, ObstacleType.DOOR, ObstacleType.FENCE, ObstacleType.GATE,
      ObstacleType.PILLAR, ObstacleType.PIPE, ObstacleType.STATUE, ObstacleType.TABLE, ObstacleType.WALL
  );

  public Ghost(Enemy enemy) {
    Objects.requireNonNull(enemy);
    this.personage = enemy;
  }

  public Ghost(Ally ally) {
    Objects.requireNonNull(ally);
    this.personage = ally;
  }

  public Ghost(Player player) {
    Objects.requireNonNull(player);
    this.personage = player;
  }

  public boolean canThroughObstacle(Obstacle obstacle) {
    return obstacleList.contains(obstacle.skin());
  }

  @Override
  public String name() {
    return personage.name();
  }

  @Override
  public PersonageType skin() {
    return PersonageType.GHOST;
  }

  @Override
  public Coordinates position() {
    return personage.position();
  }

  @Override
  public void setPosition(Coordinates position) {
    personage.setPosition(position);
  }

  public Personage getUnderlyingPersonage() {
    return personage;
  }
}
