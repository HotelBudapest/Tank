package Tanks;

/**
 * The Turret interface provides methods for drawing and moving a turret in the game.
 */
public interface Turret{

    /**
     * Draws the turret on the game canvas.
     * This method is intended to be implemented by any class that represents an object with a turret.
     *
     * @param app The app instance used for drawing the turret.
     */
    public void drawTurret(App app);

    /**
     * Moves the turret to a new position or orientation.
     * This method is intended to be implemented by any class that needs to change the position or angle of a turret.
     *
     * @param changedX The new x-coordinate or angle for the turret, depending on implementation.
     * @param changedY The new y-coordinate or vertical movement for the turret, depending on implementation.
     */
    public void moveTurret(float changedX, float changedY);
}
