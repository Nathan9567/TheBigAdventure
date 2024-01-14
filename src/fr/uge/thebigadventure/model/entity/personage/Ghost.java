package fr.uge.thebigadventure.model.entity.personage;

import java.util.List;

import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.entity.obstacle.Obstacle;
import fr.uge.thebigadventure.model.type.entity.ObstacleType;
import fr.uge.thebigadventure.model.type.entity.PersonageType;

public final class Ghost implements NPC {

  List<ObstacleType> obstacleList = List.of(
      ObstacleType.BED, ObstacleType.BRICK, ObstacleType.DOOR, ObstacleType.FENCE, ObstacleType.GATE,
      ObstacleType.PILLAR, ObstacleType.PIPE, ObstacleType.STATUE, ObstacleType.TABLE, ObstacleType.WALL
  );

  public boolean canThroughObstacle(Obstacle obstacle) {
    return obstacleList.contains(obstacle.skin());
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public PersonageType skin() {
    return null;
  }

  @Override
  public Coordinates position() {
    return null;
  }

  @Override
  public void setPosition(Coordinates position) {
  }
}
