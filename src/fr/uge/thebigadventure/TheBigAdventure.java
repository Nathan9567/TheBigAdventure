package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controllers.CommandLineParser;
import fr.uge.thebigadventure.controllers.KeyboardController;
import fr.uge.thebigadventure.controllers.MapController;
import fr.uge.thebigadventure.models.GameMap;
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

    Color bkgdColor = new Color(113, 94, 68, 255);
    Application.run(bkgdColor, context -> {
      boolean update = false;

      // Create map controller
      ScreenInfo screenInfo = context.getScreenInfo();
      MapController mapController = new MapController(gameMap, screenInfo);

      while (true) {
        Event event = context.pollOrWaitEvent(30);

        mapController.updateNpcControllers();
        context.renderFrame(graphics2D -> {
          try {
            mapController.updateView(graphics2D);
          } catch (IOException e) {
            throw new IllegalStateException("Cannot render frame");
          }
          graphics2D.dispose();
        });

        if (event == null) {
          continue;
        }

        // Quit application on key pressed or released
        Action action = event.getAction();
        KeyboardController keyboardController = null;
        if (action == Action.KEY_PRESSED) {
          keyboardController = new KeyboardController(event.getKey());
        }
        if (gameMap.getPlayer() != null && keyboardController != null) {
          mapController.updatePlayerController(keyboardController);
        }
        if (keyboardController != null && keyboardController.handleQuitControl(context)) {
          return;
        }
      }
    });
  }

}