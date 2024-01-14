package fr.uge.thebigadventure.view;

import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Size;
import fr.uge.thebigadventure.model.entity.inventory.Inventory;
import fr.uge.thebigadventure.view.entity.EntityView;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

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

  private void drawInventorySlot(Graphics2D graphics2D, Coordinates coordinates,
                                 int margin) {
    graphics2D.setColor(new Color(140, 140, 140));
    graphics2D.fillRect(coordinates.x() + margin,
        coordinates.y() + margin, cellSize - margin,
        cellSize - margin);
  }

  private void drawInventoryItem(Graphics2D graphics2D, Coordinates slot,
                                 Coordinates imageCoordinates, int margin) throws IOException {
    imageCoordinates = new Coordinates(imageCoordinates.x() + margin,
        imageCoordinates.y() + margin);
    entityView.drawEntityTile(graphics2D, inventory.items()[slot.x()][slot.y()].skin(),
        imageCoordinates, cellSize - margin, 0);
  }

  private void drawInventoryBackground(Graphics2D graphics2D, Coordinates coordinates,
                                       Size size) {
    var extraMargin = cellSize * 0.1;
    graphics2D.setColor(new Color(199, 199, 199));
    graphics2D.fillRect((int) (coordinates.x() - extraMargin),
        (int) (coordinates.y() - extraMargin),
        (int) (size.width() * cellSize + extraMargin * 2),
        (int) (size.height() * cellSize + extraMargin * 2));
  }

  private void drawCursor(Graphics2D graphics2D, Coordinates slot, int strokeSize) {
    graphics2D.setColor(new Color(255, 255, 255));
    graphics2D.setStroke(new BasicStroke(strokeSize));
    graphics2D.drawRect((int) (screenInfo.getWidth() / 2 - cellSize * 4 + slot.x() * cellSize),
        (int) (screenInfo.getHeight() / 2 - cellSize * 2 + slot.y() * cellSize),
        cellSize, cellSize);
  }

  private void drawItemName(Graphics2D graphics2D, Coordinates slot, int margin) {
    if (inventory.items()[slot.y()][slot.x()] == null)
      return;
    graphics2D.setColor(new Color(255, 255, 255));
    graphics2D.drawString(inventory.items()[slot.y()][slot.x()].name(),
        (int) (screenInfo.getWidth() / 2 - cellSize * 4 + slot.x() * cellSize + margin),
        (int) (screenInfo.getHeight() / 2 - cellSize * 2 + slot.y() * cellSize + cellSize - margin));
  }

  private void drawInventory(Graphics2D graphics2D, Size inventorySize,
                             Coordinates topLeftCorner, int sideMargin) throws IOException {
    drawInventoryBackground(graphics2D, topLeftCorner, inventorySize);
    for (int j = 0; j < inventory.items().length; j++) {
      for (int i = 0; i < inventory.items()[j].length; i++) {
        topLeftCorner = new Coordinates(
            (int) (screenInfo.getWidth() / 2 - cellSize * 4 + i * cellSize),
            (int) (screenInfo.getHeight() / 2 - cellSize * 2 + j * cellSize)
        );
        drawInventorySlot(graphics2D, topLeftCorner, sideMargin);
        if (inventory.items()[j][i] != null) {
          var itemSlot = new Coordinates(j, i);
          drawInventoryItem(graphics2D, itemSlot, topLeftCorner, sideMargin);
        }
      }
    }
  }

  public void renderInventory(Graphics2D graphics2D, Coordinates cursor) throws IOException {
    Objects.requireNonNull(graphics2D, "graphics2D cannot be null");
    Objects.requireNonNull(cursor, "cursor cannot be null");
    var startCoordinates = new Coordinates(
        (int) (screenInfo.getWidth() / 2 - cellSize * 4),
        (int) (screenInfo.getHeight() / 2 - cellSize * 2)
    );
    var size = new Size(inventory.items()[0].length, inventory.items().length);
    var sideMargin = cellSize * 0.05;
    drawInventory(graphics2D, size, startCoordinates, (int) sideMargin);
    drawCursor(graphics2D, cursor, (int) sideMargin);
    drawItemName(graphics2D, cursor, (int) sideMargin);
  }
}
