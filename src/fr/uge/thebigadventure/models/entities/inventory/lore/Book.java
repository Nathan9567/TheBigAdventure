package fr.uge.thebigadventure.models.entities.inventory.lore;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public class Book extends LoreItem {
    public Book(String dialog) {
        super(InventoryItemType.BOOK, dialog);
    }
}
