package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.enums.utils.WeaponType;

public class Bolt extends Weapon {
  public Bolt(int damage) {
    super(WeaponType.BOLT, damage);
  }
}
