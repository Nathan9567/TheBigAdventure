package fr.uge.thebigadventure.models.entities.persons;

import fr.uge.thebigadventure.models.enums.entities.PersonType;

public class Person {

  private final PersonType characterType;

  public Person(PersonType characterType) {
    this.characterType = characterType;
  }

  public PersonType getCharacterType() {
    return characterType;
  }
}
