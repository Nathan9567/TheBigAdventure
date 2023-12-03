package fr.uge.thebigadventure.models.entities.decorations;

import fr.uge.thebigadventure.models.entities.EnvironmentObject;
import fr.uge.thebigadventure.models.enums.entities.DecorationType;

public class Decoration extends EnvironmentObject {

  public Decoration(DecorationType decorationType, char encoding) {
    super(decorationType, encoding);
  }

}
