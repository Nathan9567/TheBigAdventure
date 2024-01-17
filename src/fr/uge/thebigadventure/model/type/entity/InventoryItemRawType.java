package fr.uge.thebigadventure.model.type.entity;

/**
 * The raw type of inventory items, that means the {@link FoodType} are not
 * included. We got an interface {@link InventoryItemType} to be able to
 * iterate over all the inventory items.
 */
public enum InventoryItemRawType implements InventoryItemType {
  BOLT, BOOK, BOX, CASH, CLOCK, COG, CRYSTAL, CUP, DRUM, FLAG, GEM, GUITAR,
  HIHAT, KEY, LAMP, LEAF, MIRROR, MOON, ORB, PANTS, PAPER, PLANET, RING, ROSE,
  SAX, SCISSORS, SEED, SHIRT, SHOVEL, STAR, STICK, SUN, SWORD, TRUMPET, VASE
}
