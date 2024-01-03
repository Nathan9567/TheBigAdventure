package fr.uge.thebigadventure.model;

import java.util.Objects;

public record Trade(ElementRef wanted, ElementRef given) {
  public Trade {
    Objects.requireNonNull(wanted);
    Objects.requireNonNull(given);
  }
}
