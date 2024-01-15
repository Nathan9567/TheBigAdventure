package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public class LoreItem implements InventoryItem {

  private final BasicItem item;
  private final String text;

  public LoreItem(InventoryItemRawType skin, String name,
                  String text,
                  Coordinates position) {
    this.item = new BasicItem(skin, name, position);
    this.text = text;
  }

  @Override
  public String name() {
    return item.name();
  }

  @Override
  public InventoryItemRawType skin() {
    return InventoryItemRawType.BOOK;
  }

  @Override
  public Coordinates position() {
    return item.position();
  }

  public String text() {
    return text;
  }
}
