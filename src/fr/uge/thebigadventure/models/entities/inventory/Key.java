package fr.uge.thebigadventure.models.entities.inventory;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

import java.util.Objects;

public class Key extends InventoryItem {
    public Key(String name) {
        super(InventoryItemType.KEY);
        Objects.requireNonNull(name);
        this.name = name;
    }
}
