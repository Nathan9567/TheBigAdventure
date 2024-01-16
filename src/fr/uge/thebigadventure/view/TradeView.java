package fr.uge.thebigadventure.view;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.view.entity.EntityView;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for rendering the trading table.
 */
public record TradeView(List<Trade> tradeTable, int cellSize,
                        int nbTilesHeight) {
  private static final EntityView entityView = new EntityView();

  /**
   * Draws a trade on the trading table. The trade is drawn in the top left corner of the cell.
   * The trade is looks like this: [wanted] -> [given].
   *
   * @param graphics2D    the graphics context
   * @param trade         the trade to draw
   * @param topLeftCorner the top left corner of the cell
   * @param extraMargin   the extra margin around the cell
   * @throws IOException if the image cannot be loaded
   */
  private void drawTrade(Graphics2D graphics2D, Trade trade, Coordinates topLeftCorner, double extraMargin) throws IOException {
    var x = (int) (topLeftCorner.x() * cellSize + extraMargin);
    var y = (int) (topLeftCorner.y() * cellSize + extraMargin);
    entityView.drawEntityTile(graphics2D, trade.wanted().type(),
        new Coordinates(x, y), cellSize, 0);
    entityView.drawEntityTile(graphics2D, OtherType.ARROW,
        new Coordinates(x + cellSize, y), cellSize, 0);
    entityView.drawEntityTile(graphics2D, trade.given().type(),
        new Coordinates(x + cellSize * 2, y), cellSize, 0);
  }

  /**
   * Draws the trading cursor on the trading table.
   *
   * @param graphics2D    the graphics context
   * @param topLeftCorner the top left corner of the cell
   * @param extraMargin   the extra margin around the cell
   */
  private void drawTradingCursor(Graphics2D graphics2D, Coordinates topLeftCorner, double extraMargin) {
    var x = (int) (topLeftCorner.x() * cellSize + extraMargin);
    var y = (int) (topLeftCorner.y() * cellSize + extraMargin);
    graphics2D.setColor(new Color(255, 255, 255));
    graphics2D.drawRect(x, y, cellSize * 3, cellSize);
  }

  /**
   * Draws the background of the trading table.
   *
   * @param graphics2D     the graphics context
   * @param topLeftCorner  the top left corner of the cell
   * @param extraMargin    the extra margin around the cell
   * @param nbTradeDisplay the number of trades to display
   */
  private void drawTradingTableBackground(Graphics2D graphics2D,
                                          Coordinates topLeftCorner,
                                          double extraMargin, int nbTradeDisplay) {
    graphics2D.setColor(new Color(199, 199, 199));
    graphics2D.fillRect((int) (topLeftCorner.x() - extraMargin),
        (int) (topLeftCorner.y() - extraMargin),
        (int) (cellSize * 3 + extraMargin * 3),
        Math.min(tradeTable.size(), nbTradeDisplay) * cellSize + (int) (extraMargin * 3));
  }

  /**
   * Renders the trading table in the graphics context.
   *
   * @param graphics2D     the graphics context to render to the trading table
   * @param cursorPosition the position of the cursor in the trading table
   * @throws IOException if the images of the trades cannot be loaded
   */
  public void renderTradingTable(Graphics2D graphics2D, int cursorPosition) throws IOException {
    var extraMargin = cellSize * 0.1;
    var nbTradeDisplay = nbTilesHeight / 2;
    drawTradingTableBackground(graphics2D, new Coordinates(0, 0), extraMargin, nbTradeDisplay);
    int start = Math.max(0, cursorPosition - nbTradeDisplay + 1);
    int end = Math.min(tradeTable.size(), cursorPosition + nbTradeDisplay + 1);
    for (int i = start; i < end; i++) {
      drawTrade(graphics2D, tradeTable.get(i), new Coordinates(0, i - start), extraMargin);
    }
    drawTradingCursor(graphics2D, new Coordinates(0, Math.min(nbTradeDisplay - 1, cursorPosition)), extraMargin);
  }
}