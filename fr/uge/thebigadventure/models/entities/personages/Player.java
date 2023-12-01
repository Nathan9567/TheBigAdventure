package fr.uge.thebigadventure.models.entities.personages;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.PlayerSkinType;

import java.util.Arrays;
import java.util.Objects;

public class Player implements PersonageInterface {

  private final PersonageType skin;
  private final String name;
  private final int health;
  private Coord position;

  public Player(PersonageType skin, String name, Coord position, int health) {
    Objects.requireNonNull(skin, "Skin cannot be null");
    PlayerSkinType isPlayerSkin = Arrays.stream(PlayerSkinType.values())
        .filter(playerSkinType -> playerSkinType.name().equals(skin.name()))
        .findFirst()
        .orElse(null);
    Objects.requireNonNull(isPlayerSkin, "Skin must be a valid PlayerSkinType");
    this.skin = skin;
    Objects.requireNonNull(position, "Position cannot be null");
    this.name = name;
    this.position = position;
    this.health = health;
  }

  @Override
  public Coord getPosition() {
    return position;
  }

  public void setPosition(Coord position) {
    this.position = position;
  }

  @Override
  public int getHealth() {
    return health;
  }

  public String name() {
    return name;
  }

  public PersonageType getSkin() {
    return skin;
  }
}
