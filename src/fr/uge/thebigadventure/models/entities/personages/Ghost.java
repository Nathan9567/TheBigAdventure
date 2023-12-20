package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.obstacles.Obstacle;
import fr.uge.thebigadventure.models.enums.entities.ObstacleType;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

import java.util.List;

public class Ghost implements Personage {

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
