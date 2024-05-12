package Tanks;

import processing.core.PApplet;
public class Projectile extends App{
    float x;
    float y;
    float power;
    float gravity = 0.36f;
    float angle;
    float velocity;
    int[] color;
    float addedGravity = 0;
    Player playerThatFired;
    boolean projectileHasHit;

    /**
     * Constructor for Projectile class.
     *
     * @param x The initial x-coordinate of the projectile.
     * @param y The initial y-coordinate of the projectile.
     * @param power The power of the projectile, influencing its speed and trajectory.
     * @param angle The firing angle of the projectile, in radians.
     * @param color An array representing the RGB color of the projectile.
     * @param playerThatFired The player object that fired this projectile.
     */
    public Projectile(float x, float y, float power, float angle, int[] color, Player playerThatFired){
        this.x = x;
        this.y = y;
        this.power = power;
        this.angle = angle;
        this.color = color;
        this.playerThatFired = playerThatFired;
        this.velocity = (float)((0.08 * this.power) + 1);
    }

    /**
     * Updates the score of the player who fired the projectile.
     * This method adds 30 points to the firing player's score.
     */ 
    public void updatePlayerScore(){
        this.playerThatFired.score += 30;
    }


    /**
     * Updates the position of the projectile based on its velocity and angle.
     * It also adjusts for gravity and wind effects.
     */ 
    public void update() {
        this.x += (velocity) * Math.cos(this.angle) + (Wind*0.03); // + wind
        addedGravity = addedGravity + velocity * (gravity / FPS);

        this.y += addedGravity;
        this.y -= (velocity) * Math.sin(this.angle);
    }

    /**
     * Calculates a random wind effect value that can affect the trajectory of the projectile.
     *
     * @return An integer representing the wind effect, which can be positive or negative.
     */
    public int getWind(){
        int change = (random.nextInt(11)-5);
        return change;
    }

    /**
     * Displays the projectile on the given PApplet drawing surface.
     *
     * @param app The PApplet surface on which to draw the projectile.
     */
    public void display(PApplet app) {
        update();
        app.noStroke();
        app.fill(this.color[0], this.color[1], this.color[2]); 
        app.ellipse(this.x, this.y, 10, 10);
    }
}

