package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.view.InventoryView;

import java.awt.*;
import java.io.IOException;

public class InventoryController {

  private final Inventory inventory;
  private final InventoryView inventoryView;

  public InventoryController(Inventory inventory, InventoryView inventoryView) {
    this.inventory = inventory;
    this.inventoryView = inventoryView;
  }

  public void updateView(Graphics2D graphics2D) throws IOException {
    inventoryView.renderInventory(graphics2D);
  }
}
