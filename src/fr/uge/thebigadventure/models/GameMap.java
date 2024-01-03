package fr.uge.thebigadventure.models;

import fr.uge.thebigadventure.controllers.MapParser;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.personages.NPC;
import fr.uge.thebigadventure.models.entities.personages.Personage;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameMap {
  private final List<NPC> npcs;
  private final List<Personage> personages;
  private final Size size;
  private final Map<Coordinates, EntityType> data;
  private final Map<Coordinates, Entity> elements;
  private Player player;

  public GameMap(Size size, Map<Coordinates, EntityType> data,
                 Map<Coordinates, Entity> elements,
                 List<Personage> personages) {
    Objects.requireNonNull(size, "Size cannot be null");
    if (size.width() <= 0 || size.height() <= 0) {
      throw new IllegalArgumentException("Invalid map size");
    }
    Objects.requireNonNull(data, "Data cannot be null");
    this.npcs = new ArrayList<>();
    this.personages = List.copyOf(personages);
    this.size = size;
    this.data = data;
    this.elements = elements;
    splitPersonages();
    if (player == null) {
      throw new IllegalArgumentException("No player found in map");
    }
  }

  public static GameMap load(String mapPath) throws IOException {
    File file = new File(mapPath);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
      stringBuilder.append("\n");
    }
    reader.close();
    var parser = new MapParser(stringBuilder.toString());
    var mapBuilder = parser.parse();
    return mapBuilder.toGameMap();
  }

  private void splitPersonages() {
    for (var personage : personages) {
      switch (personage) {
        case NPC personageNpc -> npcs.add(personageNpc);
        case Player personagePlayer -> player = personagePlayer;
      }
    }
  }

  public Player getPlayer() {
    return player;
  }

  public List<NPC> getNpcs() {
    return npcs;
  }

  public void removeNpc(NPC npc) {
    npcs.remove(npc);
  }

  public Map<Coordinates, EntityType> data() {
    return data;
  }

  public Map<Coordinates, Entity> elements() {
    return elements;
  }

  public Size size() {
    return size;
  }
}
