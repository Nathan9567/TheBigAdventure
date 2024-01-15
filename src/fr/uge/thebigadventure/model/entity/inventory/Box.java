package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.Coordinates;

public class Box implements InventoryItem {
  private final BasicItem item;
  private final Direction direction;

  public Box(String name, Coordinates position,
             Direction direction) {
    this.item = new BasicItem(InventoryItemRawType.BOX, name, position);
    this.direction = direction;
  }

  @Override
  public String name() {
    return item.name();
  }

  @Override
  public EntityType skin() {
    return item.skin();
  }

  @Override
  public Coordinates position() {
    return item.position();
  }
  
  public Direction direction() {
    return direction;
  }
}
