package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * A LoreItem is an item that contains text to be read by the player.
 */
public class LoreItem implements InventoryItem {

  private final BasicItem item;
  private final String text;

  /**
   * Creates a new LoreItem. The skin must be BOOK or PAPER.
   * The text is the text to be displayed when the player reads the item.
   *
   * @param skin     the skin of the item
   * @param name     the name of the item
   * @param text     the lore text
   * @param position the position in the map
   */
  public LoreItem(InventoryItemRawType skin, String name,
                  String text, Coordinates position) {
    if (skin != InventoryItemRawType.BOOK && skin != InventoryItemRawType.PAPER)
      throw new IllegalArgumentException("skin must be BOOK or PAPER");
    this.item = new BasicItem(skin, name, position);
    this.text = text;
  }

  @Override
  public String name() {
    return item.name();
  }

  @Override
  public InventoryItemRawType skin() {
    return item.skin();
  }

  @Override
  public Coordinates position() {
    return item.position();
  }

  /**
   * The text of the lore item to be displayed when the player reads it.
   *
   * @return the text of the lore item
   */
  public String text() {
    return text;
  }
}
