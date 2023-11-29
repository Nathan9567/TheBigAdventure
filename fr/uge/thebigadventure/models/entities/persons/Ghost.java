package fr.uge.thebigadventure.models.entities.persons;

import fr.uge.thebigadventure.models.enums.entities.PersonType;
import fr.uge.thebigadventure.models.enums.environment.ObstacleType;
import fr.uge.thebigadventure.models.environments.obstacles.Obstacle;

import java.util.List;

public class Ghost extends Person {

  List<ObstacleType> obstacleList = List.of(
      ObstacleType.BED, ObstacleType.BRICK, ObstacleType.DOOR, ObstacleType.FENCE, ObstacleType.GATE,
      ObstacleType.PILLAR, ObstacleType.PIPE, ObstacleType.STATUE, ObstacleType.TABLE, ObstacleType.WALL
  );

  public Ghost() {
    super(PersonType.GHOST);
  }

  public boolean canThroughObstacle(Obstacle obstacle) {
    return obstacleList.contains(obstacle.getObstacleType());
  }
}
