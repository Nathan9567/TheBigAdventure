package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;

/**
 * A key is an item that can be used to open a door, a tower, etc.
 */
public class Key implements InventoryItem {

  private final BasicItem item;

  /**
   * Create a new key. The name is mandatory here because it is used to identify the key.
   *
   * @param name     the name of the key
   * @param position the position of the key
   */
  public Key(String name, Coordinates position) {
    Objects.requireNonNull(name);
    this.item = new BasicItem(InventoryItemRawType.KEY, name, position);
  }

  @Override
  public String name() {
    return item.name();
  }

  @Override
  public InventoryItemRawType skin() {
    return InventoryItemRawType.KEY;
  }

  @Override
  public Coordinates position() {
    return item.position();
  }

}
