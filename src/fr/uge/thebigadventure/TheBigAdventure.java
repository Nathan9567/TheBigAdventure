package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.CommandLineParser;
import fr.uge.thebigadventure.controllers.KeyboardController;
import fr.uge.thebigadventure.controllers.NPCController;
import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.NPC;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.views.MapView;
import fr.uge.thebigadventure.views.entities.NPCView;
import fr.uge.thebigadventure.views.entities.PlayerView;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

      var npcControllerHashMap = new HashMap<NPC, NPCController>();
      gameMap.personages().stream().filter(personage -> personage instanceof NPC)
              .forEach(npc -> npcControllerHashMap.put((NPC) npc, new NPCController((NPC) npc, new NPCView())));

      while (true) {
        Event event = context.pollOrWaitEvent(50);

        renderNPCFrame(context, npcControllerHashMap, gameMap, (int) cell);

        if (event == null) {
          continue;
        }

        // Quit application on key pressed or released
        Action action = event.getAction();
        if (action == Action.KEY_PRESSED) {
          if (player != null) {
            var lastPlayerPosition = player.position();
            KeyboardController.handlePlayerControl(event, player, gameMap);
            context.renderFrame(graphics2D -> {
              try {
                renderPlayerFrame(graphics2D, gameMap, player, cell, lastPlayerPosition);
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

  private static void renderPlayerFrame(Graphics2D graphics2D, GameMap gameMap,
                                        Player player, float cell, Coord lastPlayerPosition) throws Exception {
    PlayerView.renderPlayer(graphics2D, player, (int) cell);
    if (lastPlayerPosition.equals(player.position())) {
      return;
    }
    PlayerView.clearPlayer(graphics2D, lastPlayerPosition, (int) cell);
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

  private static void renderNPCFrame(ApplicationContext context, Map<NPC, NPCController> npcControllerMap,
                                     GameMap gameMap, int cellSize) {
    npcControllerMap.forEach((npc, npcController) -> {
      var lastPosition = npc.position();
      if (npcController.update(gameMap)) {
        System.out.println("Last " + lastPosition);
        context.renderFrame(graphics2D -> {
          System.out.println("Actual " + npc.position());
          var npcView = new NPCView();
          try {
            npcView.renderNPC(graphics2D, npc, cellSize);
            npcView.clearNPC(graphics2D, lastPosition, cellSize);
            npcView.restoreLastTileState(graphics2D, npc.skin(), lastPosition, cellSize);
          } catch (Exception e) {
            throw new IllegalStateException("Cannot render frame");
          }
        });
      }
    });
  }

}