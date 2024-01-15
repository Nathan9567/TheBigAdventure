package fr.uge.thebigadventure.controller;

import fr.uge.thebigadventure.model.GameMap;
import fr.uge.thebigadventure.model.utils.parser.CommandLineParser;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class is responsible for initializing and running the game.
 * It parses the command line arguments, loads the game map, and runs the game loop.
 */
public class GameInitializer {
  private final String[] args;
  private GameMap gameMap;
  private boolean dryRun;

  /**
   * Constructor for the GameInitializer class.
   *
   * @param args the command line arguments.
   */
  public GameInitializer(String[] args) {
    this.args = args;
  }

  /**
   * Parses the command line arguments and loads the game map.
   *
   * @return true if the program should only validate the map, false otherwise.
   */
  public boolean parseArguments() {
    var commandParser = new CommandLineParser(args);
    commandParser.parse(args);
    var mapPath = Path.of(commandParser.getMapPath());

    try {
      gameMap = GameMap.load(mapPath);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot load map from " + mapPath);
    }

    if (commandParser.isValidate()) {
      return true;
    }

    dryRun = commandParser.isDryRun();
    return false;
  }

  /**
   * Runs the game loop.
   * This method is responsible for creating the map controller, handling events, and rendering frames.
   */
  public void runGame() {
    Color bkgdColor = new Color(113, 94, 68, 255);
    Application.run(bkgdColor, context -> {
      boolean update = true;

      ScreenInfo screenInfo = context.getScreenInfo();
      
      // Create map controller
      MapController mapController = new MapController(gameMap, screenInfo, dryRun);

      while (!mapController.isPlayerDead()) {
        update = handleEvent(context, mapController, update);
      }
      context.exit(0);
    });
  }

  /**
   * Handles events in the game loop.
   *
   * @param context       the application context.
   * @param mapController the map controller.
   * @param update        a flag indicating whether the frame should be updated.
   * @return the updated flag.
   */
  private boolean handleEvent(ApplicationContext context, MapController mapController, boolean update) {
    Event event = context.pollOrWaitEvent(30);

    if (mapController.updateNpcControllers()) {
      update = true;
    }
    if (update) {
      renderFrame(context, mapController);
      update = false;
    }

    if (event != null) {
      update = processKeyEvent(context, mapController, event);
    }

    return update;
  }

  /**
   * Renders a frame in the game loop.
   *
   * @param context       the application context.
   * @param mapController the map controller.
   */
  private void renderFrame(ApplicationContext context, MapController mapController) {
    context.renderFrame(graphics2D -> {
      try {
        mapController.updateView(graphics2D);
      } catch (IOException e) {
        throw new IllegalStateException("Cannot render frame");
      }
      graphics2D.dispose();
    });
  }

  /**
   * Processes key events in the game loop.
   *
   * @param context       the application context.
   * @param mapController the map controller.
   * @param event         the event to process.
   * @return a flag indicating whether the frame should be updated.
   */
  private boolean processKeyEvent(ApplicationContext context, MapController mapController, Event event) {
    boolean update = false;
    Action action = event.getAction();
    KeyboardController keyboardController = null;
    if (action == Action.KEY_PRESSED) {
      keyboardController = new KeyboardController(event.getKey());
    }
    if (gameMap.getPlayer() != null && keyboardController != null) {
      mapController.updateMapController(keyboardController);
      update = true;
    }
    if (keyboardController != null && keyboardController.handleQuitControl(context)) {
      context.exit(0);
    }
    return update;
  }
}