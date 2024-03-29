package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.inventory.InventoryItem;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.ElementRef;
import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.view.TradeView;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Controller for the trade system.
 */
public class TradeController {

  private final TradeView tradeView;
  private final InventoryController inventoryController;
  private final List<Entity> coldEntities;
  private final List<Trade> tradeTable;
  private int cursorPosition = 0;
  private boolean isTradeOpen = false;

  /**
   * Constructor for the TradeController.
   *
   * @param tradeTable          The list of trades.
   * @param tradeView           The view of the trade system.
   * @param inventoryController The inventory controller.
   * @param coldEntities        The list of entities that can be traded.
   */
  public TradeController(List<Trade> tradeTable, TradeView tradeView, InventoryController inventoryController, List<Entity> coldEntities) {
    this.inventoryController = inventoryController;
    this.tradeTable = tradeTable;
    this.tradeView = tradeView;
    this.coldEntities = coldEntities;
  }

  private InventoryItem findInColdEntities(ElementRef ref) {
    return coldEntities.stream().filter(e -> e instanceof InventoryItem i && ref.looksLike(i)).map(InventoryItem.class::cast).findFirst().orElse(null);
  }

  /**
   * Trade with the entity at the cursor position.
   *
   * @return True if the trade was successful, false otherwise.
   */
  public boolean trade() {
    return trade(tradeTable.get(cursorPosition));
  }

  private boolean trade(Trade trade) {
    if (inventoryController.remove(trade.wanted())) {
      var entity = findInColdEntities(trade.given());
      if (entity != null) {
        inventoryController.add(entity);
      } else {
        inventoryController.add(trade.given());
      }
      return true;
    }
    return false;
  }

  /**
   * Render the trading table with the cursor at the cursor position.
   *
   * @param graphics2D The graphics to render on.
   * @throws IOException If an image cannot be loaded.
   */
  public void render(Graphics2D graphics2D) throws IOException {
    tradeView.renderTradingTable(graphics2D, cursorPosition);
  }

  /**
   * Move the cursor in the given direction.
   * The cursor cannot go out of bounds and must be moved to the
   * {@link Direction#NORTH} or the {@link Direction#SOUTH} direction.
   * The cursor position is not changed if the direction is not valid.
   *
   * @param direction The direction to move the cursor.
   */
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

  /**
   * Check if the trade table is open. The trade table is open if the player
   * is in front of an entity that can be traded.
   *
   * @return True if the trade table is open, false otherwise.
   */
  public boolean isTradeOpen() {
    return isTradeOpen;
  }

  /**
   * Toggle the trade inventory. If the trade inventory is open, it will be closed
   * and vice versa.
   */
  public void toggleTradeInventory() {
    inventoryController.toggleInventory();
    isTradeOpen = !isTradeOpen;
  }
}
