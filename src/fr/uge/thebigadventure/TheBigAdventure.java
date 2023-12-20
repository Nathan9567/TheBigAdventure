package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.CommandLineParser;
import fr.uge.thebigadventure.controllers.KeyboardController;
import fr.uge.thebigadventure.controllers.NPCController;
import fr.uge.thebigadventure.controllers.PlayerController;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.NPC;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.views.MapView;
import fr.uge.thebigadventure.views.entities.PlayerView;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
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

      PlayerController playerController = new PlayerController(player, new PlayerView(player, (int) cell), gameMap);
      // Print map
      MapView mapView = new MapView();
      context.renderFrame(graphics2D -> {
        mapView.drawMap(gameMap, graphics2D, (int) cell, bkgdColor);
        graphics2D.dispose();
      });

//      var npcControllerHashMap = new HashMap<NPC, NPCController>();
//      gameMap.personages().stream().filter(personage -> personage instanceof NPC)
//          .forEach(npc -> npcControllerHashMap.put((NPC) npc, new NPCController((NPC) npc, new NPCView())));

      while (true) {
        Event event = context.pollOrWaitEvent(50);
//        context.renderFrame(graphics2D -> {
//          renderNPCFrame(graphics2D, npcControllerHashMap, gameMap, (int) cell);
//          graphics2D.dispose();
//        });

        if (event == null) {
          continue;
        }

        // Quit application on key pressed or released
        Action action = event.getAction();
        if (action == Action.KEY_PRESSED) {
          KeyboardController keyboardController = new KeyboardController(event.getKey());
          if (player != null) {
            keyboardController.handlePlayerControl(playerController);
            context.renderFrame(graphics2D -> {
              try {
                mapView.drawMap(gameMap, graphics2D, (int) cell, bkgdColor);
                playerController.getPlayerView().showPlayer(graphics2D);
              } catch (Exception e) {
                throw new IllegalStateException("Cannot render frame");
              }
            });
          }
          if (keyboardController.handleQuitControl(context)) {
            return;
          }
        }
      }
    });
  }

  private static void renderNPCFrame(Graphics2D graphics2D, Map<NPC, NPCController> npcControllerMap,
                                     GameMap gameMap, int cellSize) {
    npcControllerMap.forEach((npc, npcController) -> {
      var lastPosition = npc.position();
      if (npcController.update(gameMap)) {
        System.out.println("Last " + lastPosition);
        try {
          npcController.getNpcView().renderNPC(graphics2D, npc, cellSize);
          npcController.getNpcView().clearNPC(graphics2D, lastPosition, cellSize);
          npcController.getNpcView().restoreLastTileState(graphics2D, npc.skin(), lastPosition, cellSize);
        } catch (Exception e) {
          throw new IllegalStateException("Cannot render frame");
        }
      }
    });
  }

}