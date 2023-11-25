package fr.uge.thebigadventure.models.entities.characters;

import fr.uge.thebigadventure.models.enums.entities.CharacterType;
import fr.uge.thebigadventure.models.enums.environment.ObstacleType;
import fr.uge.thebigadventure.models.environments.obstacles.Obstacle;

import java.util.List;

public class Ghost extends Character {

    List<ObstacleType> obstacleList = List.of(
            ObstacleType.BED, ObstacleType.BRICK, ObstacleType.DOOR, ObstacleType.FENCE, ObstacleType.GATE,
            ObstacleType.PILLAR, ObstacleType.PIPE, ObstacleType.STATUE, ObstacleType.TABLE, ObstacleType.WALL
    );

    public Ghost() {
        super(CharacterType.GHOST);
    }

    public boolean canThroughObstacle(Obstacle obstacle) {
        return obstacleList.contains(obstacle.getObstacleType());
    }
}
