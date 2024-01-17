package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.utils.ElementRef;

import java.util.Arrays;
import java.util.Objects;

/**
 * The inventory of the player. It contains all the items of the player.
 * The inventory is a 2D array of {@link InventoryItem}.
 */
public class Inventory {

  private final InventoryItem[][] items = new InventoryItem[4][9];

  /**
   * Get the items of the inventory.
   * The first dimension of the array is the row of the inventory.
   * The second dimension of the array is the column of the inventory.
   *
   * @return the items of the inventory
   */
  public InventoryItem[][] items() {
    return items;
  }

  /**
   * Get the main hand of the player.
   * The main hand is the first item of the first row of the inventory.
   *
   * @return the main hand of the player
   */
  public InventoryItem mainHand() {
    return items[0][0];
  }

  /**
   * Add an item to the inventory at the first free slot.
   * If there is no free slot, the item is not added to the inventory.
   *
   * @param item the item to add to the inventory
   * @return true if the item was added, false otherwise
   */
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
   * by its reference.
   * For example, when you want to remove an item from the inventory,
   * but you only have the skin.
   * In this case, you can use this method to remove the item from the inventory
   * by its skin independent of its position and name in the inventory.
   *
   * @param item the item to remove by its reference
   * @return true if the item was removed, false otherwise
   */
  public boolean removeItem(ElementRef item) {
    return removeItemOrRef(item);
  }

  /**
   * Swap an item from the inventory with the main hand.
   * If the item is not in the inventory, the item is not swapped.
   *
   * @param item the item to swap with the main hand
   */
  private void swapItemWithMainHand(InventoryItem item) {
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

  /**
   * Set the main hand of the player.
   * The main hand is the first item of the first row of the inventory.
   * If the item is not in the inventory, the item is not set as main hand.
   *
   * @param item the item to set as main hand
   */
  public void setMainHand(InventoryItem item) {
    Objects.requireNonNull(item, "You need an item to set it as main hand");
    swapItemWithMainHand(item);
  }
}
