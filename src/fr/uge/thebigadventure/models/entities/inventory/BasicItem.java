package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record BasicItem(InventoryItemType skin,
                        String name, Coord position) implements InventoryItem {
}
