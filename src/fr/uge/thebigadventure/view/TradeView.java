package fr.uge.thebigadventure.view;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.view.entity.EntityView;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public record TradeView(List<Trade> tradeTable, int cellSize) {
  private static final EntityView entityView = new EntityView();
  private static final int NB_TRADE_DISPLAYED = 8;

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

  private void drawTradingCursor(Graphics2D graphics2D, Coordinates topLeftCorner, double extraMargin) {
    var x = (int) (topLeftCorner.x() * cellSize + extraMargin);
    var y = (int) (topLeftCorner.y() * cellSize + extraMargin);
    graphics2D.setColor(new Color(255, 255, 255));
    graphics2D.drawRect(x, y, cellSize * 3, cellSize);
  }

  private void drawTradingTableBackground(Graphics2D graphics2D,
                                          Coordinates topLeftCorner, double extraMargin) {
    graphics2D.setColor(new Color(199, 199, 199));
    graphics2D.fillRect((int) (topLeftCorner.x() - extraMargin),
        (int) (topLeftCorner.y() - extraMargin),
        (int) (cellSize * 3 + extraMargin * 3),
        (int) (tradeTable.size() > NB_TRADE_DISPLAYED ? NB_TRADE_DISPLAYED : tradeTable.size() * cellSize + extraMargin * 3));
  }

  public void renderTradingTable(Graphics2D graphics2D, int cursorPosition) throws IOException {
    var extraMargin = cellSize * 0.1;
    drawTradingTableBackground(graphics2D, new Coordinates(0, 0), extraMargin);
    for (int i = 0; i < tradeTable.size() && i < NB_TRADE_DISPLAYED; i++) {
      drawTrade(graphics2D, tradeTable.get(i), new Coordinates(0, i), extraMargin);
    }
    drawTradingCursor(graphics2D, new Coordinates(0, cursorPosition), extraMargin);
  }

}
