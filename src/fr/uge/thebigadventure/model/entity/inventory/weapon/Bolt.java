package fr.uge.thebigadventure.model.entity.inventory.weapon;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;

public record Bolt(String name, Coordinates position,
                   int damage) implements WeaponInterface {

  public Bolt {
    new Weapon(InventoryItemType.BOLT, name, position, damage);
  }

  public Bolt(String name, int damage) {
    this(name, null, damage);
  }

  @Override
  public InventoryItemType skin() {
    return InventoryItemType.BOLT;
  }
}
