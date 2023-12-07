package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record Bolt(String name, int damage) implements WeaponInterface {

  public Bolt {
    new Weapon(InventoryItemType.BOLT, name, damage);
  }

  @Override
  public InventoryItemType skin() {
    return InventoryItemType.BOLT;
  }
}
