package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coordinates;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.Size;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.personages.Personage;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MapBuilder {
  private final List<ElementBuilder> elementBuilders = new ArrayList<>();
  public ElementBuilder elementBuilder = new ElementBuilder();
  private Size size = null;
  private Map<String, EntityType> encodings = null;
  private Map<Coordinates, Character> data = null;
  private Size effectiveSize;

  public void setSize(Size size) {
    this.size = size;
  }

  public void setEffectiveSize(Size effectiveSize) {
    this.effectiveSize = effectiveSize;
  }

  public void setEncodings(Map<String, EntityType> encodings) {
    this.encodings = encodings;
  }

  public void setData(Map<Coordinates, Character> data) {
    this.data = data;
  }

  public void pushElementBuilder() {
    elementBuilders.add(elementBuilder);
    elementBuilder = new ElementBuilder();
  }

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
  }

  public GameMap toGameMap() {
    validateState();
    var elements = elementBuilders.stream()
        .map(ElementBuilder::toEntity)
        .filter(entity -> entity.position() != null)
        .collect(Collectors.toMap(Entity::position, element -> element));
    var personages = elements.values().stream().filter(entity -> entity instanceof Personage)
        .map(entity -> (Personage) entity).toList();
    elements.values().removeAll(personages);
    System.out.println(elements);
    System.out.println(personages);
    var mapData = data.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> {
      var env = encodings.get(String.valueOf(entry.getValue()));
      if (env == null) {
        System.err.println("Error in map data : encoding '" + entry.getValue() + "' has no skin encoded.");
      }
      return env;
    }));
    return new GameMap(size, mapData, elements, personages);
  }

}
