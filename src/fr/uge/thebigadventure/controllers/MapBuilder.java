package fr.uge.thebigadventure.controllers;

import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.Size;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapBuilder {
  public ElementBuilder elementBuilder = new ElementBuilder();
  private final List<ElementBuilder> elementBuilders = new ArrayList<>();
  private Size size;
  private Map<String, EntityType> encodings;
  private Map<Coord, Character> data;

  public void setSize(Size size) {
    this.size = size;
  }

  public void setEncodings(Map<String, EntityType> encodings) {
    this.encodings = encodings;
  }

  public void setData(Map<Coord, Character> data) {
    this.data = data;
  }

  public void pushElementBuilder() {
    elementBuilders.add(elementBuilder);
    elementBuilder = new ElementBuilder();
  }

  public GameMap toGameMap() {
    var elements = elementBuilders.stream().map(e -> e.toEntity()).toList(); 
    System.out.println(elements);
    var mapData = data.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> {
      var env = encodings.get(String.valueOf(entry.getValue()));
      Objects.requireNonNull(env, "Invalid encoding");
      return env;
    }));
    return new GameMap(size, mapData, elements);
  }
}
