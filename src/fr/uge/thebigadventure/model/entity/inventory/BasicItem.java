package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;

public record BasicItem(InventoryItemType skin,
                        String name,
                        Coordinates position) implements InventoryItem {
}
