package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;

public record KeyboardController(KeyboardKey keyboardKey) {

  public boolean handleQuitControl(ApplicationContext context) {
    if (keyboardKey.equals(KeyboardKey.Q)) {
      context.exit(0);
      return true;
    }
    return false;
  }

  public void handlePlayerControl(PlayerController playerController) {
    switch (keyboardKey) {
      case UP -> playerController.movePlayer(Direction.NORTH);
      case DOWN -> playerController.movePlayer(Direction.SOUTH);
      case RIGHT -> playerController.movePlayer(Direction.EAST);
      case LEFT -> playerController.movePlayer(Direction.WEST);
      case SPACE -> {
        // TODO: Action controller
      }
    }
  }
}
