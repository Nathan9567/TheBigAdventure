package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.MapParser;
import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.models.enums.entities.PersonageType;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.views.MapView;
import fr.uge.thebigadventure.views.entities.EntityView;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TheBigAdventure {

  public static void main(String[] args) throws Exception {
    // Number of tiles to show :
    int nb_tiles = 50;

    File file = new File("resources/test.map");
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
    GameMap gameMap = mapBuilder.toGameMap();

    Color bkgdColor = new Color(113, 94, 68, 255);
    Application.run(bkgdColor, context -> {

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();
      System.out.println("size of the screen (" + width + " x " + height + ")");
      float cell = width / nb_tiles;

      Player player = new Player(PersonageType.BABA, "bababa", new Coord(5, 5),
          10);

      for (int i = 0; i < 4; i++) {
        context.renderFrame(graphics2D -> {
          MapView.drawMap(gameMap,
              graphics2D, (int) cell);
          EntityView.drawEntityTile(graphics2D,
              player.skin().getImagePath(), player.getPosition(), (int) cell);
        });
      }

      while (true) {
        Event event = context.pollOrWaitEvent(200);
        System.out.println("event: " + event);
        if (event == null) {
          continue;
        }

        // Quit application on key pressed or released
        Action action = event.getAction();
        if (action == Action.KEY_PRESSED) {
          KeyboardKey key = event.getKey();
          var lastPlayerPosition = player.getPosition();
          switch (key) {
            case RIGHT -> move(player, gameMap, Direction.EAST);
            case LEFT -> move(player, gameMap, Direction.WEST);
            case UP -> move(player, gameMap, Direction.NORTH);
            case DOWN -> move(player, gameMap, Direction.SOUTH);
            default -> {
              context.exit(0);
              return;
            }
          }
          context.renderFrame(graphics2D -> {
            EntityView.drawEntityTile(graphics2D,
                player.skin().getImagePath(), player.getPosition(), (int) cell);
            var entityType = gameMap.data().get(lastPlayerPosition);
            if (lastPlayerPosition.equals(player.getPosition())) {
              return;
            }
            graphics2D.setColor(bkgdColor);
            graphics2D.fill(new Rectangle2D.Float(lastPlayerPosition.x() * (int) cell,
                lastPlayerPosition.y() * (int) cell, (int) cell, (int) cell));
            if (entityType != null) {
              EntityView.drawEntityTile(graphics2D,
                  entityType.getImagePath(), lastPlayerPosition, (int) cell);
            }
          });
        }
      }
    });
  }

  private static void move(Player player, GameMap gameMap,
                           Direction playerDirection) {
    player.setDirection(playerDirection);
    if (player.getPosition().x() == 0 && playerDirection == Direction.WEST
        || player.getPosition().x() == gameMap.size().width() - 1
        && playerDirection == Direction.EAST
        || player.getPosition().y() == 0 && playerDirection == Direction.NORTH
        || player.getPosition().y() == gameMap.size().height() - 1
        && playerDirection == Direction.SOUTH) {
      return;
    }
    var newPosition = player.getPosition().move(playerDirection);
    EntityType entityType = gameMap.data().get(newPosition);
    if (entityType != null && entityType.isObstacle()) {
      return;
    }
    player.setPosition(newPosition);
  }
}