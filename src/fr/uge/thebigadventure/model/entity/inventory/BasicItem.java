package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;

public record BasicItem(InventoryItemRawType skin,
                        String name,
                        Coordinates position) implements InventoryItem {
  public BasicItem {
    Objects.requireNonNull(skin, "Skin cannot be null");
  }
}
