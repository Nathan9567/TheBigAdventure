package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.entities.obstacles.Obstacle;
import fr.uge.thebigadventure.models.enums.entities.ObstacleType;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;

import java.util.List;

public class Ghost implements PersonageInterface {

  List<ObstacleType> obstacleList = List.of(
      ObstacleType.BED, ObstacleType.BRICK, ObstacleType.DOOR, ObstacleType.FENCE, ObstacleType.GATE,
      ObstacleType.PILLAR, ObstacleType.PIPE, ObstacleType.STATUE, ObstacleType.TABLE, ObstacleType.WALL
  );

//  public Ghost() {
//    super(PersonageType.GHOST);
//  }

  public boolean canThroughObstacle(Obstacle obstacle) {
    return obstacleList.contains(obstacle.getObstacleType());
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public PersonageType getSkin() {
    return null;
  }

  @Override
  public Coord getPosition() {
    return null;
  }
}
