package fr.uge.thebigadventure.models.entities.characters;

import fr.uge.thebigadventure.models.enums.entities.CharacterType;

public class Character {

    private final CharacterType characterType;

    public Character(CharacterType characterType) {
        this.characterType = characterType;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }
}
