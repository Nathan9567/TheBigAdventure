package fr.uge.thebigadventure.controllers;

import java.util.List;
import fr.uge.thebigadventure.models.Coord;
import fr.uge.thebigadventure.models.Zone;
import fr.uge.thebigadventure.models.entities.DumbEntity;
import fr.uge.thebigadventure.models.entities.Entity;
import fr.uge.thebigadventure.models.enums.entities.EntityType;

public class ElementBuilder {
  private String name;
  private EntityType skin;
  private boolean player;
  private Coord position;
  private int health;
  private String kind;
  private Zone zone;
  private String behavior;
  private int damage;
  private String text;
//  private List<String> steal;
//  private List<Trade> trade;
//  private Lock locked;
//  private Direction flow;
//  private boolean phantomized;
//  private String teleport;
  
  public void setName(String name) {
    this.name = name;
  }

  public void setSkin(EntityType skin) {
    this.skin = skin;
  }

  public void setPlayer(boolean player) {
    this.player = player;
  }

  public void setPosition(Coord position) {
    this.position = position;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public void setZone(Zone zone) {
    this.zone = zone;
  }

  public void setBehavior(String behavior) {
    this.behavior = behavior;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Entity toEntity() {
    return new DumbEntity(name, skin);
  }
}
