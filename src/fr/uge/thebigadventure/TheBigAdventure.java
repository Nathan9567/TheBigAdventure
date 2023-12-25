package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.*;
import fr.uge.thebigadventure.models.GameMap;
import fr.uge.thebigadventure.models.entities.personages.Player;
import fr.uge.thebigadventure.views.entities.NPCView;
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
    int nb_tiles = 30;

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
      boolean update = false;

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float cell = width / nb_tiles;

      PlayerController playerController = new PlayerController(player, new PlayerView(player, (int) cell), gameMap);
      var npcControllers = gameMap.getNpcs().stream().map(npc ->
          new NPCController(npc, new NPCView(npc, (int) cell))).toList();
      MapController mapController = new MapController(gameMap, playerController, npcControllers);
      // Print map
      context.renderFrame(graphics2D -> {
        try {
          mapController.updateView(graphics2D, (int) cell);
        } catch (IOException e) {
          throw new IllegalStateException("Cannot render frame");
        }
        graphics2D.dispose();
      });

      while (true) {
        Event event = context.pollOrWaitEvent(30);

        if (event == null) {
          continue;
        }

        // Quit application on key pressed or released
        Action action = event.getAction();
        KeyboardController keyboardController = null;
        if (action == Action.KEY_PRESSED) {
          keyboardController = new KeyboardController(event.getKey());
        }
        if (player != null && keyboardController != null) {
          update = keyboardController.handlePlayerControl(playerController);
        }
        for (var npcController : npcControllers) {
          if (npcController.update(gameMap)) {
            update = true;
          }
        }
        if (update) {
          context.renderFrame(graphics2D -> {
            try {
              mapController.updateView(graphics2D, (int) cell);
            } catch (IOException e) {
              throw new IllegalStateException("Cannot render frame");
            }
            graphics2D.dispose();
          });
        }
        if (keyboardController != null && keyboardController.handleQuitControl(context)) {
          return;
        }
      }
    });
  }

}