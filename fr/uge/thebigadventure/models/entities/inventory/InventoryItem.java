package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public class InventoryItem {

    private final InventoryItemType itemType;
    protected String name;

    public InventoryItem(InventoryItemType itemType) {
        this.itemType = itemType;
    }

    public InventoryItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }
}
