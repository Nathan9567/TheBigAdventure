package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.CommandLineParser;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.models.enums.entities.EntityType;
import fr.uge.thebigadventure.models.enums.utils.Direction;
import fr.uge.thebigadventure.views.MapView;
import fr.uge.thebigadventure.views.entities.EntityView;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;

public class TheBigAdventure {

  public static void main(String[] args) throws IllegalAccessException {
    // Number of tiles to show :
    int nb_tiles = 70;

    var commandParser = new CommandLineParser(args);
    commandParser.parse();
    var mapPath = commandParser.getMapPath();

    GameMap gameMap;
    try {
      gameMap = GameMap.load(mapPath);
    } catch (IOException e) {
      throw new IllegalAccessException("Cannot load map from " + mapPath);
    }

    if (commandParser.isValidate()) {
      return;
    }

    if (commandParser.isDryRun()) {
      System.out.println("Not implemented yet");
      return;
    }

    Player player = gameMap.getPlayer();

    Color bkgdColor = new Color(113, 94, 68, 255);
    Application.run(bkgdColor, context -> {

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float cell = width / nb_tiles;

      // Print map
      context.renderFrame(graphics2D -> {
        MapView.drawMap(gameMap, graphics2D, (int) cell, bkgdColor);
        graphics2D.dispose();
      });

      while (true) {
        Event event = context.pollOrWaitEvent(500);
        if (event == null) {
          continue;
        }

        // Quit application on key pressed or released
        Action action = event.getAction();
        if (action == Action.KEY_PRESSED) {
          KeyboardKey key = event.getKey();
          var lastPlayerPosition = player.position();
          switch (key) {
            case UP -> move(player, gameMap, Direction.NORTH);
            case DOWN -> move(player, gameMap, Direction.SOUTH);
            case RIGHT -> move(player, gameMap, Direction.EAST);
            case LEFT -> move(player, gameMap, Direction.WEST);
            case SPACE -> {
              // TODO: interact with entity or environment
            }
            case Q -> {
              context.exit(0);
              return;
            }
          }
          context.renderFrame(graphics2D -> {
            // Print player
            try {
              EntityView.drawEntityTile(graphics2D,
                  player.skin(), player.position(), (int) cell);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
            // Clear last player position
            if (lastPlayerPosition.equals(player.position())) {
              return;
            }
            EntityView.clearTile(graphics2D, lastPlayerPosition, (int) cell);
            // Print tile at last player position
            var entityType = gameMap.data().get(lastPlayerPosition);
            var entity = gameMap.elements().get(lastPlayerPosition);
            if (entityType != null) {
              try {
                EntityView.drawEntityTile(graphics2D,
                    entityType, lastPlayerPosition, (int) cell);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            } else if (entity != null) {
              try {
                EntityView.drawEntityTile(graphics2D,
                    entity.skin(), lastPlayerPosition, (int) cell);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            }
          });
        }
      }
    });
  }

  private static void move(Player player, GameMap gameMap,
                           Direction playerDirection) {
    player.setDirection(playerDirection);
    var newPosition = player.position().move(playerDirection);
    if (!newPosition.inBounds(gameMap.size())) {
      return;
    }
    EntityType entityType = gameMap.data().get(newPosition);
    if (entityType != null && entityType.isObstacle()) {
      return;
    }
    player.setPosition(newPosition);
  }
}