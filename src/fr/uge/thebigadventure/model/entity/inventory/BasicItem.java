package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;

/**
 * Basic implementation of {@link InventoryItem}.
 * It is used to represent a non-special item in the inventory.
 *
 * @param skin     the skin of the item (cannot be null)
 * @param name     the name of the item
 * @param position the position of the item
 */
public record BasicItem(InventoryItemRawType skin,
                        String name,
                        Coordinates position) implements InventoryItem {
  public BasicItem {
    Objects.requireNonNull(skin, "Skin cannot be null");
  }
}
