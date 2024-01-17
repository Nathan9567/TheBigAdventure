package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controller.GameInitializer;

/**
 * The main class of the game.
 */
public class TheBigAdventure {
  public static void main(String[] args) {
    GameInitializer gameInitializer = new GameInitializer(args);
    if (!gameInitializer.parseArguments()) {
      gameInitializer.runGame();
    }
  }
}