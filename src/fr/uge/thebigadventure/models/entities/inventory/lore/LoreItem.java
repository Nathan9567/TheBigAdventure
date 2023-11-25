package fr.uge.thebigadventure.models.entities.inventory.lore;

import fr.uge.thebigadventure.models.entities.inventory.InventoryItem;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public class LoreItem extends InventoryItem {
    private final String dialog;

    public LoreItem(InventoryItemType itemType, String dialog) {
        super(itemType);
        this.dialog = dialog;
    }

    public String getDialog() {
        return dialog;
    }
}
