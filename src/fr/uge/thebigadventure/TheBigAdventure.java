package fr.uge.thebigadventure;

import fr.uge.thebigadventure.controller.GameInitializer;

public class TheBigAdventure {
  public static void main(String[] args) {
    GameInitializer gameInitializer = new GameInitializer(args);
    if (!gameInitializer.parseArguments()) {
      gameInitializer.runGame();
    }
  }
}