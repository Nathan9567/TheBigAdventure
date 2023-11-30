package fr.uge.thebigadventure.models.enums.entities;

import fr.uge.thebigadventure.models.enums.ElementType;

public sealed interface EntitiesType extends ElementType permits EffectType, FoodType,
    InventoryItemType, PersonageType, PlayerSkinType, SpecialType, TransportType,
    WeaponType {

}
