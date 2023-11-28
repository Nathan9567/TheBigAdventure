package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.enums.entities.WeaponType;

public class Bolt extends Weapon {
    public Bolt(int damage) {
        super(WeaponType.BOLT, damage);
    }
}
