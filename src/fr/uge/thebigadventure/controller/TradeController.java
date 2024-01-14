package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.view.TradeView;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TradeController {

  private final TradeView tradeView;
  private final InventoryController inventoryController;

  public TradeController(List<Trade> tradeTable, InventoryController inventoryController, int cellSize) {
    this.inventoryController = inventoryController;
    this.tradeView = new TradeView(List.copyOf(tradeTable), cellSize);
  }

  public boolean trade(Trade trade) {
    if (inventoryController.remove(trade.given())) {
      inventoryController.add(trade.wanted());
      return true;
    }
    return false;
  }

  public void render(Graphics2D graphics2D) throws IOException {
    tradeView.renderTradingTable(graphics2D);
  }

}
