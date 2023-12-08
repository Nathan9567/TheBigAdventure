package fr.uge.thebigadventure.models.entities.inventory.weapons;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.InventoryItemType;

public record Bolt(String name, Coord position,
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