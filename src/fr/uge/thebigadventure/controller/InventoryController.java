package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.Size;
import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.view.InventoryView;

import java.awt.*;
import java.io.IOException;

public class InventoryController {

  private final Inventory inventory;
  private final InventoryView inventoryView;
  private Coordinates cursorPosition = new Coordinates(0, 0);
  private boolean inventoryOpen = false;

  public InventoryController(Inventory inventory, InventoryView inventoryView) {
    this.inventory = inventory;
    this.inventoryView = inventoryView;
  }

  public boolean isInventoryOpen() {
    return inventoryOpen;
  }

  public void toggleInventory() {
    inventoryOpen = !inventoryOpen;
  }

  public void moveInventoryCursor(Direction direction) {
    var newPosition = cursorPosition.move(direction);
    var inventorySize = new Size(inventory.items()[0].length, inventory.items().length);
    if (newPosition.notInBounds(inventorySize))
      return;
    cursorPosition = newPosition;
  }

  public void moveItemToMainHand() {
    var item = inventory.items()[cursorPosition.y()][cursorPosition.x()];
    if (item == null)
      return;
    inventory.setMainHand(item);
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    inventoryView.renderInventory(graphics2D, cursorPosition);
  }
}
