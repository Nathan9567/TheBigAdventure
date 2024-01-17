package fr.uge.thebigadventure.model.entity.inventory;

import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.type.entity.FoodType;
import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.utils.Coordinates;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Food is an item that can be eaten by the player.
 */
public class Food implements InventoryItem {

  private final String name;
  private final EntityType skin;
  private final Coordinates position;
  private final int foodSupply;
  private boolean isCooked = true;

  /**
   * Create a new food item.
   * If the skin is a {@link FoodType}, the food is considered as cooked.
   *
   * @param skin       the skin of the food (cannot be null)
   * @param foodSupply the food supply of this food
   * @param name       the little name of the food
   * @param position   the position of the food in the world
   */
  public Food(FoodType skin, int foodSupply, String name, Coordinates position) {
    Objects.requireNonNull(skin, "You need a skin for the food");
    this.skin = skin;
    this.name = name;
    this.position = position;
    this.foodSupply = foodSupply;
  }

  /**
   * Create a new food item.
   * If the skin is a {@link PersonageType}, the food is considered as raw.
   * The player will have to cook it before eating it.
   *
   * @param skin       the skin of the food (cannot be null)
   * @param foodSupply the food supply of this food
   * @param name       the little name of the food
   * @param position   the position of the food in the world
   */
  public Food(PersonageType skin, int foodSupply, String name, Coordinates position) {
    Objects.requireNonNull(skin, "You need a skin for the food");
    this.skin = Stream.of(PersonageType.BUNNY, PersonageType.CRAB, PersonageType.FISH, PersonageType.FROG, PersonageType.SNAIL)
        .filter(personageType -> personageType.equals(skin))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("You need a valid skin for the food"));
    this.name = name;
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

  /**
   * Cook the food item.
   */
  public void cook() {
    isCooked = true;
  }

  /**
   * Check if the food is cooked.
   *
   * @return true if the food is cooked, false otherwise
   */
  public boolean isCooked() {
    return isCooked;
  }

  /**
   * Get the food supply of this food.
   *
   * @return the health that the player will gain by eating this food
   */
  public int getFoodSupply() {
    return foodSupply;
  }

  @Override
  public boolean isFood() {
    return true;
  }
}
