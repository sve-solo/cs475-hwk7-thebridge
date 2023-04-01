import java.util.ArrayList;

/**
 * DO NOT MAKE CHANGES TO THIS CLASS.
 * An abstract bridge class.
 * @author David
 */

public abstract class Bridge {

  // shared vars
  protected int currentTime;        // current time
  protected ArrayList<Car> bridge;  // list of cars currently on bridge
  protected boolean direction;      // current allowed flow of traffic

  /*
   * Inits a Bridge
   */
  public Bridge() {
    currentTime = 0;
    bridge = new ArrayList<>();
    direction = true;
  }

  abstract public void arrive(Car car) throws InterruptedException;

  abstract public void exit(Car car) throws InterruptedException;
}
