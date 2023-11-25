package fr.uge.thebigadventure.models.environments.obstacles;

import fr.uge.thebigadventure.models.enums.environment.ObstacleType;
import fr.uge.thebigadventure.models.environments.EnvironmentObject;

public class Obstacle extends EnvironmentObject {

    private final ObstacleType obstacleType;

    public Obstacle(ObstacleType obstacleType, char encoding) {
        super(obstacleType, encoding);
        this.obstacleType = obstacleType;
    }

    public ObstacleType getObstacleType() {
        return obstacleType;
    }
}
