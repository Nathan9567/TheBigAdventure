package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.utils.ElementRef;

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

  private <T> boolean removeItemOrRef(T item) {
    Objects.requireNonNull(item, "You need an item to remove it from the inventory");
    for (var i = 0; i < items.length; i++) {
      for (var j = 0; j < items[i].length; j++) {
        var isEquals = switch (item) {
          case InventoryItem inventoryItem -> inventoryItem.equals(items[i][j]);
          case ElementRef elementRef -> elementRef.looksLike(items[i][j]);
          default ->
              throw new IllegalStateException("Unexpected value: " + item);
        };
        if (isEquals) {
          items[i][j] = null;
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Remove an item from the inventory
   *
   * @param item the item to remove
   * @return true if the item was removed, false otherwise
   */
  public boolean removeItem(InventoryItem item) {
    return removeItemOrRef(item);
  }

  /**
   * Remove an item from the inventory by its reference.
   * This method is useful when you want to remove an item from the inventory
   * but you don't have the item itself but only its reference.
   * For example, when you want to remove an item from the inventory
   * but you only have the item's name.
   * In this case, you can use this method to remove the item from the inventory
   * by its name.
   *
   * @param item the item to remove by its reference
   * @return true if the item was removed, false otherwise
   */
  public boolean removeItem(ElementRef item) {
    return removeItemOrRef(item);
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
}
