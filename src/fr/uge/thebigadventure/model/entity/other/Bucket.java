package fr.uge.thebigadventure.model.entity.other;

import fr.uge.thebigadventure.model.type.entity.OtherType;
import fr.uge.thebigadventure.model.utils.Coordinates;

/**
 * A bucket is a special entity that can be filled with water.
 */
public class Bucket implements SpecialEntity {

  private final String name;
  private final Coordinates position;
  private boolean filled = false;

  /**
   * Creates a new bucket with the given name and position.
   *
   * @param name     the name of the bucket
   * @param position the position of the bucket
   */
  public Bucket(String name, Coordinates position) {
    this.name = name;
    this.position = position;
  }

  @Override
  public String name() {
    return name;
  }

  /**
   * Check if the bucket contains water.
   *
   * @return true if the bucket contains water, false otherwise
   */
  public boolean isFilled() {
    return filled;
  }

  /**
   * Fill the bucket with water.
   */
  public void fill() {
    filled = true;
  }

  /**
   * Empty the bucket.
   */
  public void empty() {
    filled = false;
  }

  @Override
  public Coordinates position() {
    return position;
  }

  @Override
  public OtherType skin() {
    return OtherType.BUCKET;
  }
}
