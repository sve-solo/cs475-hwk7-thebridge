import java.util.Random;

/**
 * DO NOT MAKE CHANGES TO THIS CLASSs
 */
public class Car implements Runnable {
  private int id;
  private int time;
  private Bridge bridge;

  /**
   * Constructs a car
   * 
   * @param id a non-negative integer
   * @param b  a reference to the OneLaneBridge
   */
  public Car(int id, Bridge b) {
    this.id = id;
    this.time = 0;
    this.bridge = b;
  }

  /**
   * @return the car id
   */
  public int getID() {
    return id;
  }

  /**
   * @return the car's direction
   */
  public boolean getDirection() {
    return 0 == id % 2;
  }

  /**
   * Sets the time when the car was allowed to go
   * on the bridge
   * 
   * @param t a non negative integer time
   */
  public void setEntryTime(int t) {
    time = t;
  }

  /**
   * @return string representation of car
   */
  @Override
  public String toString() {
    return "<car=" + id + ",dir=" + getDirection() + ",t=" + time + ">";
  }

  /**
   * Each car travels a random amount of time before arriving
   * at the bridge. It turn requests to enter the bridge. Once
   * it's allowed, it takes a random amount of time to travel
   * on the bridge, before requesting to exit the bridge.
   */
  @Override
  public void run() {
    Random rand = new Random();
    try {
      Thread.sleep(Math.abs(rand.nextInt()) % 1000);
      bridge.arrive(this);
      Thread.sleep(Math.abs(rand.nextInt()) % 1000);
      bridge.exit(this);
    } catch (InterruptedException e) {
    }
  }
}