package fr.uge.thebigadventure.model.utils;

import java.util.Objects;

/**
 * Represents a trade between two elements.
 *
 * @param wanted The element wanted by the first element.
 *               Must not be null.
 * @param given  The element given by the first element.
 *               Must not be null.
 */
public record Trade(ElementRef wanted, ElementRef given) {
  public Trade {
    Objects.requireNonNull(wanted);
    Objects.requireNonNull(given);
  }
}
