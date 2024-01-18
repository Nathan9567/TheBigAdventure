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

/**
 * The game map.
 * It contains the player, the NPCs, the entities, the cold entities and the map data.
 * It is used to keep track of the game state after building the map.
 */
public class GameMap {
  private final List<NPC> npcs = new ArrayList<>();
  private final List<Personage> personages;
  private final Size size;
  private final Map<Coordinates, EntityType> data;
  private final Map<Coordinates, Entity> entities;
  private final List<Entity> coldEntities;
  private Player player;

  /**
   * Create a new game map.
   *
   * @param size         the size of the map (not null)
   * @param data         the map data (not null)
   * @param entities     the entities
   * @param personages   the personages
   * @param coldEntities the cold entities
   */
  public GameMap(Size size, Map<Coordinates, EntityType> data,
                 Map<Coordinates, Entity> entities,
                 List<Personage> personages, List<Entity> coldEntities) {
    Objects.requireNonNull(size, "Size cannot be null");
    if (size.width() <= 0 || size.height() <= 0) {
      throw new IllegalArgumentException("Invalid map size");
    }
    Objects.requireNonNull(data, "Data cannot be null");
    this.personages = personages != null ? List.copyOf(personages) : null;
    this.size = size;
    this.data = data;
    this.entities = new HashMap<>(entities);
    this.coldEntities = coldEntities;
    splitPersonages();
    if (player == null) {
      throw new IllegalArgumentException("No player found in map");
    }
  }

  /**
   * Load a map from a path. The map is loaded with UTF-8 charset.
   * To load a map with a different charset, use {@link #load(Path, Charset)}.
   *
   * @param path the path of the map
   * @return the game map
   * @throws IOException if an I/O error occurs reading from the file
   *                     or a malformed or unmappable byte sequence is read
   */
  public static GameMap load(Path path) throws IOException {
    return load(path, StandardCharsets.UTF_8);
  }

  /**
   * Load a map from a path with a specific charset.
   *
   * @param path    the path of the map
   * @param charset the charset of the map
   * @return the game map
   * @throws IOException if an I/O error occurs reading from the file
   *                     or a malformed or unmappable byte sequence is read
   */
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

  /**
   * Get the player entity of the map.
   *
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Get the NPCs of the map.
   * The returned list is a copy of the NPCs list.
   *
   * @return the NPCs
   */
  public List<NPC> getNpcs() {
    return List.copyOf(npcs);
  }

  /**
   * Remove an NPC from the map. The NPC is removed from the NPCs list.
   *
   * @param npc the NPC to remove
   */
  public void removeNpc(NPC npc) {
    npcs.remove(npc);
  }

  /**
   * Get the map data.
   * The returned map is a copy of the map data.
   * Usually used to render the map.
   *
   * @return the map data
   */
  public Map<Coordinates, EntityType> data() {
    return Map.copyOf(data);
  }

  /**
   * Put an element in the map. The element is put in the entities map.
   * If an element is already present at the coordinates, it is replaced.
   *
   * @param coords  the coordinates of the element
   * @param element the element to put
   */
  public void putElement(Coordinates coords, Entity element) {
    entities.put(coords, element);
  }

  /**
   * Remove an element from the map. The element is removed from the entities map.
   *
   * @param coords the coordinates of the element to remove
   */
  public void removeElement(Coordinates coords) {
    entities.remove(coords);
  }

  /**
   * Get the entities of the map. The returned map is a copy of the entities map.
   * Usually used to render the map or to get the entities of a specific cell.
   *
   * @return the entities
   */
  public Map<Coordinates, Entity> entities() {
    return Map.copyOf(entities);
  }

  /**
   * Get the cold entities of the map.
   * The returned list is a copy of the cold entities list.
   * Cold entities are entities that don't have coordinates.
   * They are exclusively used to trade with the player.
   *
   * @return the cold entities
   */
  public List<Entity> coldEntities() {
    return List.copyOf(coldEntities);
  }

  /**
   * Get the size of the map. The size is the number of cells in the map.
   *
   * @return the size of the map
   */
  public Size size() {
    return size;
  }
}
