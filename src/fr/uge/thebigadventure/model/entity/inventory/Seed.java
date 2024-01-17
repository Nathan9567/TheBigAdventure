package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * A seed is an item that can be planted in the ground.
 */
public class Seed implements InventoryItem {

  private final BasicItem item;

  /**
   * Create a new seed. The seed is created at the given position.
   * Name and position can be null because they can be used in the inventory.
   *
   * @param name     The name of the seed.
   * @param position The position of the seed.
   */
  public Seed(String name, Coordinates position) {
    this.item = new BasicItem(InventoryItemRawType.SEED, name, position);
  }

  @Override
  public InventoryItemRawType skin() {
    return item.skin();
  }

  @Override
  public String name() {
    return item.name();
  }

  @Override
  public Coordinates position() {
    return item.position();
  }
}
