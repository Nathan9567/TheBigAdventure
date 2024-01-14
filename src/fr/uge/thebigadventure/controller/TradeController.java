package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.view.TradeView;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TradeController {

  private final TradeView tradeView;
  private final InventoryController inventoryController;
  private final List<Trade> tradeTable;
  private int cursorPosition = 0;
  private boolean isTradeOpen = false;

  public TradeController(List<Trade> tradeTable, InventoryController inventoryController, int cellSize) {
    this.inventoryController = inventoryController;
    this.tradeTable = tradeTable;
    this.tradeView = new TradeView(List.copyOf(tradeTable), cellSize);
  }

  public boolean trade() {
    return trade(tradeTable.get(cursorPosition));
  }

  private boolean trade(Trade trade) {
    if (inventoryController.remove(trade.wanted())) {
      inventoryController.add(trade.given());
      return true;
    }
    return false;
  }

  public void render(Graphics2D graphics2D) throws IOException {
    tradeView.renderTradingTable(graphics2D, cursorPosition);
  }

  public void moveTradeCursor(Direction direction) {
    switch (direction) {
      case NORTH -> cursorPosition = Math.max(0, cursorPosition - 1);
      case SOUTH ->
          cursorPosition = Math.min(tradeView.tradeTable().size() - 1, cursorPosition + 1);
      default -> {
        // Do nothing
      }
    }
  }

  public boolean isTradeOpen() {
    return isTradeOpen;
  }

  public void toggleTradeInventory() {
    inventoryController.toggleInventory();
    isTradeOpen = !isTradeOpen;
  }
}
