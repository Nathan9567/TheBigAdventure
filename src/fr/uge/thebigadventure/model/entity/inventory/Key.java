package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;

public class Key implements InventoryItem {

  private final BasicItem item;

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
