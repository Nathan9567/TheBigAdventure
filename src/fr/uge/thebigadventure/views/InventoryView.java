package fr.uge.thebigadventure.views;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.entities.inventory.Inventory;
import fr.uge.thebigadventure.views.entities.EntityView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;

public record InventoryView(Inventory inventory, int cellSize,
                            ScreenInfo screenInfo) {

  private static final EntityView entityView = new EntityView();

  public void renderInventory(Graphics2D graphics2D) throws IOException {
    for (int i = 0; i < inventory.items().length; i++) {
      for (int j = 0; j < inventory.items()[i].length; j++) {
        var coordinates = new Coordinates((int) (screenInfo.getWidth() / 2 - cellSize * 4 + j * cellSize),
            (int) (screenInfo.getHeight() / 2 - cellSize * 2 + i * cellSize));
        graphics2D.setColor(new Color(119, 119, 119, 158));
        graphics2D.fillRect(coordinates.x(), coordinates.y(), cellSize, cellSize);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(coordinates.x(), coordinates.y(), cellSize, cellSize);
        if (inventory.items()[i][j] != null) {
          entityView.drawEntityTile(graphics2D, inventory.items()[i][j].skin(), coordinates, cellSize);
          System.out.println(inventory.items()[i][j].skin() + " " + coordinates + " " + cellSize);
        }
      }
    }
  }
}
