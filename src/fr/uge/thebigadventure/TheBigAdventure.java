package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.CommandLineParser;
import fr.uge.thebigadventure.controllers.KeyboardController;
import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.views.MapView;
import fr.uge.thebigadventure.views.entities.EntityView;
import fr.uge.thebigadventure.views.entities.PlayerView;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
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
        var time = System.currentTimeMillis();
        Event event = context.pollOrWaitEvent(50);
        if (event == null) {
          continue;
        }

        // TODO : NPC movement
//        gameMap.personages().stream()
//                .filter(personage -> personage instanceof NPC)
//                .forEach(npc -> {
//                  var npcView = new NPCView();
//                  var npcController = new NPCController((NPC) npc, npcView);
//                  if (npcController.update(gameMap)) {
//                    context.renderFrame(graphics2D -> {
//                      try {
//                        npcView.renderNPC(graphics2D, npc, (int) cell);
//                      } catch (Exception e) {
//                        throw new IllegalStateException("Cannot render frame");
//                      }
//                    });
//                  }
//                });

        // Quit application on key pressed or released
        Action action = event.getAction();
        if (action == Action.KEY_PRESSED) {
          if (player != null) {
            var lastPlayerPosition = player.position();
            KeyboardController.handlePlayerControl(event, player, gameMap);
            context.renderFrame(graphics2D -> {
              try {
                renderFrame(graphics2D, gameMap, player, cell, lastPlayerPosition);
              } catch (Exception e) {
                throw new IllegalStateException("Cannot render frame");
              }
            });
          }
          if (KeyboardController.handleQuitControl(context, event)) {
            return;
          }
        }
      }
    });
  }

  private static void renderFrame(Graphics2D graphics2D, GameMap gameMap,
                                  Player player, float cell, Coord lastPlayerPosition) throws Exception {
    PlayerView.renderPlayer(graphics2D, player, (int) cell);
    if (lastPlayerPosition.equals(player.position())) {
      return;
    }
    EntityView.clearTile(graphics2D, lastPlayerPosition, (int) cell);
    var entityType = gameMap.data().get(lastPlayerPosition);
    var entity = gameMap.elements().get(lastPlayerPosition);
    if (entityType != null) {
      PlayerView.restoreLastTileState(graphics2D, entityType,
              lastPlayerPosition, (int) cell);
    }
    if (entity != null) {
      PlayerView.restoreLastTileState(graphics2D, entity.skin(),
              lastPlayerPosition, (int) cell);
    }
  }
}