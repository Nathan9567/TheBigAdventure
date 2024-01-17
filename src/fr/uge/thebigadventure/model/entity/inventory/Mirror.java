package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * A mirror is an inventory item.
 */
public class Mirror implements InventoryItem {

  private final BasicItem item;

  /**
   * Creates a mirror with a name and a position.
   * The parameters can be null.
   *
   * @param name     The name of the mirror.
   * @param position The position of the mirror.
   */
  public Mirror(String name, Coordinates position) {
    this.item = new BasicItem(InventoryItemRawType.MIRROR, name, position);
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
}
