package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;

public class KeyboardController {

  public static void handlePlayerControl(Event event, Player player, GameMap gameMap) {
    KeyboardKey key = event.getKey();
    switch (key) {
      case UP -> ActionController.move(player, gameMap, Direction.NORTH);
      case DOWN -> ActionController.move(player, gameMap, Direction.SOUTH);
      case RIGHT -> ActionController.move(player, gameMap, Direction.EAST);
      case LEFT -> ActionController.move(player, gameMap, Direction.WEST);
      case SPACE -> {
        // TODO: interact with entity or environment
      }
    }
  }


  public static boolean handleQuitControl(ApplicationContext context, Event event) {
    KeyboardKey key = event.getKey();
    if (key == KeyboardKey.Q) {
      context.exit(0);
      return true;
    }
    return false;
  }
}
