package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.entity.inventory.Food;
import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.ElementRef;
import fr.uge.thebigadventure.model.utils.Size;
import fr.uge.thebigadventure.view.InventoryView;

import java.awt.*;
import java.io.IOException;

/**
 * This class is responsible for controlling the inventory in the game.
 * It handles the movement of the cursor, the opening and closing of the inventory,
 * the moving of items from the inventory to the main hand and the eating of items.
 */
public class InventoryController {

  private final Inventory inventory;
  private final InventoryView inventoryView;
  private Coordinates cursorPosition = new Coordinates(0, 0);
  private boolean inventoryOpen = false;

  /**
   * Constructor for the InventoryController.
   * It takes an inventory and an inventory view as parameters.
   *
   * @param inventory     The inventory to be controlled.
   * @param inventoryView The view of the inventory.
   */
  public InventoryController(Inventory inventory, InventoryView inventoryView) {
    this.inventory = inventory;
    this.inventoryView = inventoryView;
  }

  /**
   * Checks if the inventory is open.
   *
   * @return True if the inventory is open, false otherwise.
   */
  public boolean isInventoryOpen() {
    return inventoryOpen;
  }

  /**
   * Toggles the state of the inventory (open/closed).
   */
  public void toggleInventory() {
    inventoryOpen = !inventoryOpen;
  }

  /**
   * Moves the inventory cursor in the given direction.
   * If the cursor is at the edge of the inventory, it does nothing.
   *
   * @param direction The direction to move the cursor in.
   */
  public void moveInventoryCursor(Direction direction) {
    var newPosition = cursorPosition.move(direction);
    var inventorySize = new Size(inventory.items()[0].length, inventory.items().length);
    if (newPosition.notInBounds(inventorySize))
      return;
    cursorPosition = newPosition;
  }

  /**
   * Moves the item at the cursor position to the main hand.
   */
  public void moveItemToMainHand() {
    var item = inventory.items()[cursorPosition.y()][cursorPosition.x()];
    if (item == null)
      return;
    inventory.setMainHand(item);
  }

  /**
   * Eats the item in the main hand.
   * If there is no item in the main hand or if the item is not food, it does nothing.
   *
   * @return The amount of health gained from eating the item.
   */
  public int eatMainHand() {
    var item = inventory.mainHand();
    if (item == null) {
      return 0;
    }
    if (item.isFood() && inventory.removeItem(item)) {
      return item instanceof Food food ? food.getFoodSupply() : 1;
    }
    return 0;
  }

  /**
   * Updates the view of the inventory.
   * It draws the inventory and the cursor on the given graphics context.
   *
   * @param graphics2D The graphics context to draw on.
   * @throws IOException If the inventory view fails to load the images.
   */
  public void updateView(Graphics2D graphics2D) throws IOException {
    inventoryView.renderInventory(graphics2D, cursorPosition);
  }

  /**
   * Removes the given item from the inventory if it is present.
   *
   * @param given The item to be removed.
   * @return True if the item was successfully removed, false otherwise.
   */
  public boolean remove(ElementRef given) {
    return inventory.removeItem(given);
  }

  /**
   * Adds the given item to the inventory.
   * If the inventory is full, it does nothing.
   *
   * @param wanted The item to be added.
   */
  public void add(ElementRef wanted) {
    inventory.addItem(wanted.toItem());
  }

  /**
   * Adds the given item to the inventory.
   * If the inventory is full, it does nothing.
   *
   * @param item The item to be added.
   */
  public void add(InventoryItem item) {
    inventory.addItem(item);
  }
}