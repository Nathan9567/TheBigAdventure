package fr.uge.thebigadventure.view;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.view.entity.EntityView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;

public class InventoryView {

  private static final EntityView entityView = new EntityView();
  private final Inventory inventory;
  private final int cellSize;
  private final ScreenInfo screenInfo;

  public InventoryView(Inventory inventory, int cellSize,
                       ScreenInfo screenInfo) {
    this.inventory = inventory;
    this.cellSize = cellSize;
    this.screenInfo = screenInfo;
  }

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
          var imageCoord = new Coordinates(coordinates.x() / cellSize, coordinates.y() / cellSize);
          entityView.drawEntityTile(graphics2D, inventory.items()[i][j].skin(), imageCoord, cellSize);
          System.out.println(inventory.items()[i][j].skin() + " " + coordinates + " " + cellSize);
        }
      }
    }
  }
}
