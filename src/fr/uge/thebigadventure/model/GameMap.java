package fr.uge.thebigadventure.model;

import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.personage.Ghost;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.model.entity.personage.Personage;
import fr.uge.thebigadventure.model.entity.personage.Player;
import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Size;
import fr.uge.thebigadventure.model.utils.parser.MapParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GameMap {
  private final List<NPC> npcs = new ArrayList<>();
  private final List<Personage> personages;
  private final Size size;
  private final Map<Coordinates, EntityType> data;
  private final Map<Coordinates, Entity> entities;
  private final List<Entity> coldEntities;
  private Player player;

  public GameMap(Size size, Map<Coordinates, EntityType> data,
                 Map<Coordinates, Entity> entities,
                 List<Personage> personages, List<Entity> coldEntities) {
    Objects.requireNonNull(size, "Size cannot be null");
    if (size.width() <= 0 || size.height() <= 0) {
      throw new IllegalArgumentException("Invalid map size");
    }
    Objects.requireNonNull(data, "Data cannot be null");
    this.personages = List.copyOf(personages);
    this.size = size;
    this.data = data;
    this.entities = new HashMap<>(entities);
    this.coldEntities = coldEntities;
    splitPersonages();
    if (player == null) {
      throw new IllegalArgumentException("No player found in map");
    }
  }

  public static GameMap load(Path path) throws IOException {
    return load(path, StandardCharsets.UTF_8);
  }

  public static GameMap load(Path path, Charset charset) throws IOException {
    String mapText = Files.readString(path, charset);
    var parser = new MapParser(mapText);
    var mapBuilder = parser.parse();
    return mapBuilder.toGameMap();
  }

  private void splitPersonages() {
    for (var personage : personages) {
      switch (personage) {
        case NPC personageNpc -> npcs.add(personageNpc);
        case Player personagePlayer -> player = personagePlayer;
        case Ghost ignored -> {
          // Currently, ghosts are not used in the game
        }
      }
    }
  }

  public Player getPlayer() {
    return player;
  }

  public List<NPC> getNpcs() {
    return List.copyOf(npcs);
  }

  public void removeNpc(NPC npc) {
    npcs.remove(npc);
  }

  public Map<Coordinates, EntityType> data() {
    return Map.copyOf(data);
  }

  public void putElement(Coordinates coords, Entity element) {
    entities.put(coords, element);
  }

  public void removeElement(Coordinates coords) {
    entities.remove(coords);
  }

  public Map<Coordinates, Entity> entities() {
    return Map.copyOf(entities);
  }

  public List<Entity> coldEntities() {
    return List.copyOf(coldEntities);
  }

  public Size size() {
    return size;
  }
}
