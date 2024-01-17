package fr.uge.thebigadventure.model.type.util;

/**
 * Kind of the entity. It can be a friend, an enemy, an item or an obstacle.
 * It is used to know how to parse the entity in the map file.
 */
public enum Kind {
  FRIEND, ENEMY, ITEM, OBSTACLE
}
