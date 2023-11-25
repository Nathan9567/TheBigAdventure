package fr.uge.thebigadventure.models.entities.weapons;

import fr.uge.thebigadventure.models.enums.entities.WeaponType;

public class Shovel extends Weapon {
    public Shovel(int damage) {
        super(WeaponType.SHOVEL, damage);
    }
}
