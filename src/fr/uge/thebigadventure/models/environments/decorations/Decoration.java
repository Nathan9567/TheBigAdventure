package fr.uge.thebigadventure.models.environments.decorations;

import fr.uge.thebigadventure.models.enums.environment.DecorationType;
import fr.uge.thebigadventure.models.environments.EnvironmentObject;

public class Decoration extends EnvironmentObject {

    public Decoration(DecorationType decorationType, char encoding) {
        super(decorationType, encoding);
    }

}
