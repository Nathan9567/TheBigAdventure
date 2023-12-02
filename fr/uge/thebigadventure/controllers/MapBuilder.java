package fr.uge.thebigadventure.controllers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.Size;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

public class MapBuilder {
  private Size size;
  private Map<String, EntityType> encodings;
  private Map<Coord, Character> data;
  private Map<Coord, Entity> elements;

  public void setSize(Size size) {
    this.size = size;
  }

  public void setEncodings(Map<String, EntityType> encodings) {
    this.encodings = encodings;
  }

  public void setData(Map<Coord, Character> data) {
    this.data = data;
  }

  public void setElements(Map<Coord, Entity> elements) {
    this.elements = elements;
  }
  
  public GameMap toGameMap() {
    var mapData = data.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> {
      var env = encodings.get(String.valueOf(entry.getValue()));
      Objects.requireNonNull(env, "Invalid encoding");
      return env;
    }));
    return new GameMap(size, mapData, elements);
  }
}
