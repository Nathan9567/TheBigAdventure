package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public class Seed implements InventoryItem {

  private final BasicItem item;

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
