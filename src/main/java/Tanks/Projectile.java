package Tanks;

import processing.core.PApplet;
import processing.core.PVector;
public class Projectile extends App{
    PVector position;
    float power;
    float gravity = 0.36f;
    float angle;
    float velocity;
    float addedGravity = 0;

    public Projectile(PVector position, float power, float angle) {
        this.position = new PVector(position.x*CELLSIZE, position.y*CELLSIZE);
        this.power = power;
        this.angle = angle;
        this.velocity = (float)((0.08 * this.power) + 1);
    }

    public void update() {

        if (Math.toDegrees(this.angle) >= 90){
            this.position.x -= (velocity) * Math.sin(this.angle); // + wind
        }
        else{
            this.position.x += (velocity) * Math.sin(this.angle); // + wind
        }

        addedGravity = addedGravity + velocity * (gravity / FPS);

        this.position.y += addedGravity;
        this.position.y -= (velocity) * Math.cos(this.angle);
    }

    public void display(PApplet app) {
        update();
        app.fill(127); 
        app.stroke(0);
        app.ellipse(this.position.x, this.position.y, 10, 10);  // Draw projectile as a small circle
    }
}

