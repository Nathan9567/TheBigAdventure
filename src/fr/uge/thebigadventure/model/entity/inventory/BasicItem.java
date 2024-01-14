package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.InventoryItemRawType;
import fr.uge.thebigadventure.model.utils.Coordinates;

public record BasicItem(InventoryItemRawType skin,
                        String name,
                        Coordinates position) implements InventoryItem {
}
