package fr.uge.thebigadventure.model.entity.personage;

import fr.uge.thebigadventure.model.type.entity.PersonageType;
import fr.uge.thebigadventure.model.utils.Coordinates;
import fr.uge.thebigadventure.model.utils.Trade;
import fr.uge.thebigadventure.model.utils.Zone;

import java.util.List;
import java.util.Objects;

public final class Ally implements NPC {

  private final PersonageType skin;
  private final String name;
  private final String text;
  private final Zone zone;
  private final List<Trade> trades;
  private Coordinates position;

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

  public String text() {
    return text;
  }

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

  public List<Trade> getTradeTable() {
    return trades != null ? List.copyOf(trades) : null;
  }
}
