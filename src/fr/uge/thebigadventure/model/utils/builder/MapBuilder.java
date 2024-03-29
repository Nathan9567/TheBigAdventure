package fr.uge.thebigadventure.model.utils.builder;

import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.personage.Personage;
import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Size;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A builder to create a game map.
 */
public class MapBuilder {
  private final List<Entity> entities = new ArrayList<>();
  /**
   * Element builder used to build the current element.
   */
  private ElementBuilder elementBuilder = new ElementBuilder();
  private Size size = null;
  private Map<String, EntityType> encodings = null;
  private Map<Coordinates, Character> data = null;
  private Size effectiveSize;

  /**
   * Get the element builder. The element builder is used to build the current element.
   * The current element is the element that will be created when calling {@link #pushElementBuilder()}.
   *
   * @return the element builder.
   */
  public ElementBuilder elementBuilder() {
    return elementBuilder;
  }

  /**
   * Set the size of the map.
   *
   * @param size the size of the map.
   */
  public void setSize(Size size) {
    Objects.requireNonNull(size);
    this.size = size;
  }

  /**
   * Set the effective size of the map.
   *
   * @param effectiveSize the effective size of the map.
   */
  public void setEffectiveSize(Size effectiveSize) {
    Objects.requireNonNull(effectiveSize);
    this.effectiveSize = effectiveSize;
  }

  /**
   * Set the encodings of the map.
   *
   * @param encodings the encodings of the map.
   */
  public void setEncodings(Map<String, EntityType> encodings) {
    Objects.requireNonNull(encodings);
    this.encodings = encodings;
  }

  /**
   * Set the data of the map.
   *
   * @param data the data of the map.
   */
  public void setData(Map<Coordinates, Character> data) {
    Objects.requireNonNull(data);
    this.data = data;
  }

  /**
   * Create the current element and add it to the list of elements.
   * Reset the element builder.
   * 
   * @throws IllegalStateException if the current element cannot be added to the list of the elements of the map.
   */
  public void pushElementBuilder() {
    try {
      var entity = elementBuilder.toEntity();
      if (entity.position() != null && entities.stream().anyMatch(e -> entity.position().equals(e.position())))
        throw new IllegalStateException("an entity is already at this position");
      if (entity.position() == null && entity.name() != null && entities.stream().filter(e -> e.position() == null).anyMatch(e -> entity.name().equals(e.name())))
        throw new IllegalStateException("an unpositioned entity with the same name already exists");
      entities.add(entity);
    } finally {
      elementBuilder = new ElementBuilder();
    }
  }

  /**
   * Validate the state of the map builder.
   *
   * @throws IllegalStateException if the state is invalid.
   */
  public void validateState() {
    if (size == null) {
      throw new IllegalStateException("Grid size undefined in map.");
    }
    if (encodings == null) {
      throw new IllegalStateException("Grid encodings undefined in map.");
    }
    if (data == null) {
      throw new IllegalStateException("Grid data undefined in map.");
    }
    if (!size.equals(effectiveSize)) {
      System.err.println("Grid data does not match the size provided !");
    }
    if (entities.stream().anyMatch(entity -> entity.position() != null && entity.position().notInBounds(size))) {
      System.err.println("An entity is out of bounds.");
    }
  }

  private Map<Coordinates, EntityType> mapDataEncoded() {
    var mapData = new HashMap<Coordinates, EntityType>();
    data.forEach((coord, tileEnc) -> {
      var env = encodings.get(String.valueOf(tileEnc));
      if (env == null) {
        System.err.println("Error in map data : encoding '" + tileEnc + "' has no skin encoded.");
      } else {
        mapData.put(coord, env);
      }
    });
    return mapData;
  }

  /**
   * Create a game map from the current state of the map builder.
   *
   * @return the game map.
   */
  public GameMap toGameMap() {
    validateState();

    var elements = entities.stream()
        .filter(entity -> entity.position() != null)
        .collect(Collectors.toMap(Entity::position, element -> element));

    var coldEntities = entities.stream().filter(entity -> entity.position() == null).toList();

    var personages = elements.values().stream().filter(Personage.class::isInstance)
        .map(Personage.class::cast).toList();

    elements.values().removeAll(personages);

    return new GameMap(size, mapDataEncoded(), elements, personages, coldEntities);
  }

}
