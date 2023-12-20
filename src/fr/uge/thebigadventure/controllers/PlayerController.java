package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.views.entities.PlayerView;

public class PlayerController {

  private final Player player;
  private final PlayerView playerView;
  private final GameMap map;

  public PlayerController(Player player, PlayerView playerView, GameMap gameMap) {
    this.player = player;
    this.playerView = playerView;
    this.map = gameMap;
  }
  
  private boolean isAllowToMove(Direction direction) {
    var newPosition = player.position().move(direction);
    if (newPosition.notInBounds(map.size())) {
      return false;
    }
    var entityTypeData = map.data().get(newPosition);
    var entityElement = map.elements().get(newPosition);
    var personage = map.personages().stream()
        .filter(p -> p.position().equals(newPosition))
        .anyMatch(p -> p.skin().isObstacle());
    if (entityTypeData != null) {
      if (entityTypeData.isObstacle()) {
        return false;
      }
    }
    if (personage) {
      return false;
    }
    return entityElement == null || !entityElement.skin().isObstacle();
  }

  public void movePlayer(Direction direction) {
    if (!isAllowToMove(direction)) {
      return;
    }
    player.setDirection(direction);
    player.setPosition(player.position().move(direction));
  }

  public void action() {
    var weapon = player.getWeapon();
    // TODO : Implement action
  }

  public PlayerView getPlayerView() {
    return playerView;
  }
}
