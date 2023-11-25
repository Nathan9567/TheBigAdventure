package fr.uge.thebigadventure.models.entities.inventory.lore;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public class Paper extends LoreItem {
    public Paper(String dialog) {
        super(InventoryItemType.PAPER, dialog);
    }
}
