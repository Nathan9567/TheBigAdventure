package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Enemy;
import fr.uge.thebigadventure.models.entities.personages.NPC;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.views.entities.PlayerView;

import java.awt.*;
import java.io.IOException;

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
    var personage = map.getNpcs().stream()
        .filter(npc -> npc.position().equals(newPosition))
        .anyMatch(npc -> npc.skin().isObstacle());
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

  public boolean movePlayer(Direction direction) {
    if (!isAllowToMove(direction)) {
      return false;
    }
    player.setDirection(direction);
    player.setPosition(player.position().move(direction));
    return true;
  }

  public void action() {
    var weapon = player.getWeapon();
    if (weapon != null) {
      var targetPosition = player.position().move(player.getDirection());
      var target = map.getNpcs().stream()
          .filter(npc -> npc.position().equals(targetPosition))
          .filter(NPC::isEnemy)
          .findFirst();
      if (target.isPresent()) {
        Enemy npc = (Enemy) target.get();
        npc.setHealth(npc.getHealth() - weapon.damage());
        if (npc.getHealth() <= 0) {
          map.removeNpc(npc);
        }
      }
    }
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    playerView.renderPlayer(graphics2D);
  }

  public void clearLastView(Graphics2D graphics2D) {
    playerView.clearLastPlayerPosition(graphics2D);
  }
}
