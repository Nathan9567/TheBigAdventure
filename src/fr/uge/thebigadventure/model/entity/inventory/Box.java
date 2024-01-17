package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.type.util.Direction;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * This class represents a Box item in the inventory.
 */
public class Box implements InventoryItem {
  private final BasicItem item;
  private final Direction direction;

  /**
   * Constructor for the Box class. It takes the name of the box, its position
   * in the inventory and the direction it is facing.
   * The parameters are used to create a BasicItem object.
   * All the parameters are can be null and the skin type is set to {@link InventoryItemRawType BOX}.
   *
   * @param name      The name of the box.
   * @param position  The position of the box in the inventory.
   * @param direction The direction the box is facing.
   */
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

  /**
   * This method returns the direction the box is floating.
   * It should be null if the box is not floating.
   *
   * @return The direction the box is floating.
   */
  public Direction direction() {
    return direction;
  }
}