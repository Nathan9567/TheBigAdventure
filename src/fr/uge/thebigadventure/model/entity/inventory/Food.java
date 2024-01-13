package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.Coordinates;
import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.type.entity.FoodType;
import fr.uge.thebigadventure.model.type.entity.PersonageType;

import java.util.Objects;
import java.util.stream.Stream;

public class Food implements InventoryItem {

  private final String name;
  private final EntityType skin;
  private final Coordinates position;
  private boolean isCooked = true;
  private int foodSupply = 0;

  public Food(FoodType skin, int foodSupply, String name, Coordinates position) {
    Objects.requireNonNull(skin, "You need a skin for the food");
    this.skin = skin;
    this.name = name;
    this.position = position;
    this.foodSupply = foodSupply;
  }

  public Food(PersonageType skin, int foodSupply, String name, Coordinates position) {
    Objects.requireNonNull(skin, "You need a skin for the food");
    Stream.of(PersonageType.BUNNY, PersonageType.CRAB, PersonageType.FISH, PersonageType.FROG, PersonageType.SNAIL)
        .filter(personageType -> personageType.equals(skin))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(skin.name() + " is not a food"));
    this.name = name;
    this.skin = skin;
    this.position = position;
    this.isCooked = false;
    this.foodSupply = foodSupply;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public EntityType skin() {
    return skin;
  }

  @Override
  public Coordinates position() {
    return position;
  }

  public void cook() {
    isCooked = true;
  }

  public boolean isCooked() {
    return isCooked;
  }

  public int getFoodSupply() {
    return foodSupply;
  }
}
