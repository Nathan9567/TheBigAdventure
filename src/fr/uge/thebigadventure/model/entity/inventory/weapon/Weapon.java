package fr.uge.thebigadventure.model.entity.inventory.weapon;

import java.util.Arrays;
import java.util.Objects;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.InventoryItemType;
import fr.uge.thebigadventure.model.type.util.WeaponType;

public record Weapon(InventoryItemType skin, String name, Coordinates position,
                     int damage) implements WeaponInterface {

  public Weapon {
    Objects.requireNonNull(skin);
    if (!isWeaponSkin(skin))
      throw new IllegalArgumentException("The skin must be a weapon type");
  }

  public Weapon(InventoryItemType skin, String name, int damage) {
    this(skin, name, null, damage);
  }

  private boolean isWeaponSkin(InventoryItemType skin) {
    return Arrays.stream(WeaponType.values()).anyMatch(weaponType -> weaponType.name()
        .equals(skin.name()));
  }
}
