package fr.uge.thebigadventure.models.entities;

import fr.uge.thebigadventure.models.enums.ElementType;

public interface Entity {
  String name();

  ElementType skin();
}
