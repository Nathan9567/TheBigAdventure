package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.type.util.Direction;
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

  public boolean handleTradeControl(TradeController tradeController) {
    switch (keyboardKey) {
      case UP -> {
        tradeController.moveTradeCursor(Direction.NORTH);
        return false;
      }
      case DOWN -> {
        tradeController.moveTradeCursor(Direction.SOUTH);
        return false;
      }
      case SPACE -> {
        if (tradeController.trade()) {
          tradeController.toggleTradeInventory();
          return true;
        }
        return false;
      }
      case I -> {
        tradeController.toggleTradeInventory();
        return true;
      }
      default -> {
        return false;
      }
    }
  }

  public void handleInventoryControl(InventoryController inventoryController) {
    switch (keyboardKey) {
      case UP -> inventoryController.moveInventoryCursor(Direction.NORTH);
      case DOWN -> inventoryController.moveInventoryCursor(Direction.SOUTH);
      case RIGHT -> inventoryController.moveInventoryCursor(Direction.EAST);
      case LEFT -> inventoryController.moveInventoryCursor(Direction.WEST);
      case SPACE -> inventoryController.moveItemToMainHand();
      case I -> inventoryController.toggleInventory();
      default -> {
        // Do nothing
      }
    }
  }

  public void handleMapControl(MapController mapController) {
    switch (keyboardKey) {
      case UP -> mapController.movePlayer(Direction.NORTH);
      case DOWN -> mapController.movePlayer(Direction.SOUTH);
      case RIGHT -> mapController.movePlayer(Direction.EAST);
      case LEFT -> mapController.movePlayer(Direction.WEST);
      case SPACE -> mapController.action();
      case I -> mapController.toggleInventory();
      default -> {
        // Do nothing
      }
    }
  }
}
