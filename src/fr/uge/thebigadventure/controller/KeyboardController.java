package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.type.util.Direction;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.KeyboardKey;

/**
 * This class is used to handle the keyboard inputs.
 * It is used to control the game.
 *
 * @param keyboardKey The key pressed by the user.
 */
public record KeyboardController(KeyboardKey keyboardKey) {

  /**
   * This method is used to know when the user wants to quit the game.
   *
   * @param context The context of the game.
   * @return True if the user wants to quit the game, false otherwise.
   */
  public boolean handleQuitControl(ApplicationContext context) {
    if (keyboardKey.equals(KeyboardKey.Q)) {
      context.exit(0);
      return true;
    }
    return false;
  }

  /**
   * This method is used to handle the controls of the trade menu.
   * It is used to move the cursor and to trade.
   *
   * @param tradeController The controller of the trade menu.
   * @return True if the user wants to quit the trade menu, false otherwise.
   */
  public boolean handleTradeControl(TradeController tradeController) {
    switch (keyboardKey) {
      case UP, DOWN -> {
        tradeController.moveTradeCursor(
            keyboardKey == KeyboardKey.UP ? Direction.NORTH : Direction.SOUTH);
        return false;
      }
      case SPACE -> {
        if (tradeController.trade()) {
          tradeController.toggleTradeInventory();
        }
        return tradeController.trade();
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

  /**
   * This method is used to handle the controls of the inventory menu.
   * It is used to move the cursor and to move items.
   * It is also used to quit the inventory menu.
   *
   * @param inventoryController The controller of the inventory menu.
   */
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

  /**
   * This method is used to handle the controls of the map.
   * When the user presses a key, the player moves in the corresponding direction.
   * It is also used to do an action and to open the inventory menu.
   *
   * @param mapController The controller of the map.
   */
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
