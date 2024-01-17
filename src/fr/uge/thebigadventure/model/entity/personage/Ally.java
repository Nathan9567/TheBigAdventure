package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.model.utils.Zone;

import java.util.List;
import java.util.Objects;

/**
 * An ally is a personage that can be traded with.
 * It has a trade table that contains all the items that can be traded with.
 * It has a text that is displayed when the player interacts with it.
 * If these properties are null, the ally just do nothing when the player interacts with it.
 */
public final class Ally implements NPC {

  private final PersonageType skin;
  private final String name;
  private final String text;
  private final Zone zone;
  private final List<Trade> trades;
  private Coordinates position;

  /**
   * Creates an ally with the given properties.
   *
   * @param skin       the skin of the ally
   * @param name       the name of the ally
   * @param position   the position of the ally
   * @param zone       the zone of the ally
   * @param text       the text of the ally
   * @param tradeTable the trade table of the ally
   */
  public Ally(PersonageType skin, String name, Coordinates position, Zone zone,
              String text, List<Trade> tradeTable) {
    Objects.requireNonNull(skin, "Skin cannot be null");
    this.skin = skin;
    this.name = name;
    Objects.requireNonNull(position, "Position cannot be null");
    this.position = position;
    this.zone = zone;
    this.text = text;
    this.trades = tradeTable;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public PersonageType skin() {
    return skin;
  }

  @Override
  public Coordinates position() {
    return position;
  }

  @Override
  public void setPosition(Coordinates position) {
    this.position = position;
  }

  /**
   * Returns the dialog of the ally.
   *
   * @return the dialog of the ally
   */
  public String text() {
    return text;
  }

  @Override
  public Zone getZone() {
    return zone;
  }

  @Override
  public String toString() {
    return "Ally{" +
        "skin=" + skin +
        ", name='" + name + '\'' +
        ", text='" + text + '\'' +
        ", zone=" + zone +
        ", position=" + position +
        '}';
  }

  /**
   * Returns the trade table of the ally.
   * If the trade table is null, returns null,
   * else returns an unmodifiable list of the trade table.
   *
   * @return the trade table of the ally
   */
  public List<Trade> getTradeTable() {
    return trades != null ? List.copyOf(trades) : null;
  }
}
