package Tanks;

import processing.core.PApplet;
import processing.core.PVector;
public class Projectile extends App{
    float x;
    float y;
    float power;
    float gravity = 0.36f;
    float angle;
    float velocity;
    float addedGravity = 0;

    public Projectile(float x, float y, float power, float angle){
        this.x = x;
        this.y = y;
        this.power = power;
        this.angle = angle;
        this.velocity = (float)((0.08 * this.power) + 1);
    }

    public void update() {

        if (Math.toDegrees(this.angle) >= 90){
            this.x -= (velocity) * Math.sin(this.angle); // + wind
        }
        else{
            this.x += (velocity) * Math.sin(this.angle); // + wind
        }

        addedGravity = addedGravity + velocity * (gravity / FPS);

        this.y += addedGravity;
        this.y -= (velocity) * Math.cos(this.angle);
    }

    public void display(PApplet app) {
        update();
        app.fill(127); 
        app.ellipse(this.x, this.y, 10, 10);  // Draw projectile as a small circle
    }
}

