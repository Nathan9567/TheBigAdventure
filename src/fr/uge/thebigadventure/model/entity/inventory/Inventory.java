package fr.uge.thebigadventure.model.entity.inventory;

import java.util.Arrays;
import java.util.Objects;

public class Inventory {

  private final InventoryItem[][] items = new InventoryItem[4][9];

  public InventoryItem[][] items() {
    return items;
  }

  public InventoryItem mainHand() {
    return items[0][0];
  }

  public boolean addItem(InventoryItem item) {
    Objects.requireNonNull(item, "You need an item to add it to the inventory");
    for (var i = 0; i < items.length; i++) {
      for (var j = 0; j < items[i].length; j++) {
        if (items[i][j] == null) {
          items[i][j] = item;
          return true;
        }
      }
    }
    return false;
  }

  public boolean removeItem(InventoryItem item) {
    Objects.requireNonNull(item, "You need an item to remove it from the inventory");
    for (var i = 0; i < items.length; i++) {
      for (var j = 0; j < items[i].length; j++) {
        if (items[i][j] == item) {
          items[i][j] = null;
          return true;
        }
      }
    }
    return false;
  }

  public void swapItemWithMainHand(InventoryItem item) {
    Objects.requireNonNull(item, "You need an item to swap it from the inventory");
    if (item.equals(mainHand()))
      return;

    for (var i = 0; i < items.length; i++)
      for (var j = 0; j < items[i].length; j++)
        if (items[i][j] == item) {
          items[i][j] = mainHand();
          items[0][0] = item;
          return;
        }
  }

  @Override
  public String toString() {
    return "Inventory{" +
        "items=" + Arrays.deepToString(items) +
        ", mainHand=" + mainHand() +
        '}';
  }

  public void setMainHand(InventoryItem item) {
    Objects.requireNonNull(item, "You need an item to set it as main hand");
    swapItemWithMainHand(item);
  }

  public void eatItem(InventoryItem item) {

  }
}
