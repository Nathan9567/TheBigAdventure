package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.models.enums.utils.Direction;

public class ActionController {

  public static void move(Player player, GameMap gameMap,
                          Direction playerDirection) {
    player.setDirection(playerDirection);
    var newPosition = player.position().move(playerDirection);
    if (!newPosition.inBounds(gameMap.size())) {
      return;
    }
    EntityType entityType = gameMap.data().get(newPosition);
    if (entityType != null && entityType.isObstacle()) {
      return;
    }
    player.setPosition(newPosition);
  }
}
