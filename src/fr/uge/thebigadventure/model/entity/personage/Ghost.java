package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.entity.obstacle.Obstacle;
import fr.uge.thebigadventure.model.type.entity.ObstacleType;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.List;
import java.util.Objects;

/**
 * A ghost is a personage that can go through some obstacles.
 */
public final class Ghost implements Personage {

  private final Personage personage;
  private final List<ObstacleType> obstacleList = List.of(
      ObstacleType.BED, ObstacleType.BRICK, ObstacleType.DOOR, ObstacleType.FENCE, ObstacleType.GATE,
      ObstacleType.PILLAR, ObstacleType.PIPE, ObstacleType.STATUE, ObstacleType.TABLE, ObstacleType.WALL
  );

  /**
   * Create a ghost from an enemy.
   *
   * @param enemy the enemy to create a ghost from.
   */
  public Ghost(Enemy enemy) {
    Objects.requireNonNull(enemy);
    this.personage = enemy;
  }

  /**
   * Create a ghost from an ally.
   *
   * @param ally the ally to create a ghost from.
   */
  public Ghost(Ally ally) {
    Objects.requireNonNull(ally);
    this.personage = ally;
  }

  /**
   * Create a ghost from a player.
   *
   * @param player the player to create a ghost from.
   */
  public Ghost(Player player) {
    Objects.requireNonNull(player);
    this.personage = player;
  }

  /**
   * Check if the ghost can go through the given obstacle.
   * This list of obstacles is defined in the specification.
   *
   * @param obstacle the obstacle to check.
   * @return true if the ghost can go through the obstacle, false otherwise.
   */
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

  /**
   * Get the underlying personage. This method is used to get the personage
   * that is wrapped by the ghost.
   *
   * @return the underlying personage.
   */
  public Personage getUnderlyingPersonage() {
    return personage;
  }
}
