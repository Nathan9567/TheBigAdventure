package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public class Mirror implements InventoryItem {

  private final BasicItem item;

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
