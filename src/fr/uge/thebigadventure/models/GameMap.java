package fr.uge.thebigadventure.models;

import fr.uge.thebigadventure.controllers.MapParser;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.entities.personages.Personage;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record GameMap(Size size, Map<Coord, EntityType> data,
                      Map<Coord, Entity> elements,
                      List<Personage> personages) {
  public GameMap {
    Objects.requireNonNull(size, "Size cannot be null");
    if (size.width() <= 0 || size.height() <= 0) {
      throw new IllegalArgumentException("Invalid map size");
    }
    Objects.requireNonNull(data, "Data cannot be null");
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

  // TODO: remove cast to Player
  public Player getPlayer() {
    var playerSearch = personages.stream()
            .filter(personage -> personage instanceof Player)
            .findFirst();
    if (playerSearch.isPresent()) {
      return (Player) playerSearch.get();
    }
    System.err.println("No player found in map");
    return null;
  }
}
