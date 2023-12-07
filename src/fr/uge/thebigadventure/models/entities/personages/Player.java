package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.models.enums.utils.PlayerSkinType;

import java.util.Arrays;
import java.util.Objects;

public class Player implements Personage {

  private final PersonageType skin;
  private final String name;
  private final int health;
  private Coord position;
  private Direction direction;

  public Player(PersonageType skin, String name, Coord position, int health) {
    Objects.requireNonNull(skin, "Skin cannot be null");
    boolean isPlayerSkin = Arrays.stream(PlayerSkinType.values())
        .anyMatch(playerSkinType -> playerSkinType.name().equals(skin.name()));
    if (!isPlayerSkin) {
      throw new IllegalArgumentException("Skin must be a valid PlayerSkinType");
    }
    this.skin = skin;
    Objects.requireNonNull(position, "Position cannot be null");
    this.name = name;
    this.position = position;
    this.health = health;
    this.direction = null;
  }

  @Override
  public Coord position() {
    return position;
  }

  public void setPosition(Coord position) {
    this.position = position;
  }

  public int getHealth() {
    return health;
  }

  public String name() {
    return name;
  }

  public PersonageType skin() {
    return skin;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
