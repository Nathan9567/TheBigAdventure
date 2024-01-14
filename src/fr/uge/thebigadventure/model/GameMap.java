package fr.uge.thebigadventure.model;

import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.parser.MapParser;
import fr.uge.thebigadventure.model.entity.Entity;
import fr.uge.thebigadventure.model.entity.personage.NPC;
import fr.uge.thebigadventure.model.entity.personage.Personage;
import fr.uge.thebigadventure.model.entity.personage.Player;
import fr.uge.thebigadventure.model.type.entity.EntityType;
import fr.uge.thebigadventure.model.utils.Size;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameMap {
  private final List<NPC> npcs = new ArrayList<>();
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
    this.personages = List.copyOf(personages);
    this.size = size;
    this.data = data;
    this.elements = elements;
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
