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
    int[] color;
    float addedGravity = 0;

    public Projectile(float x, float y, float power, float angle, int[] color){
        this.x = x;
        this.y = y;
        this.power = power;
        this.angle = angle;
        this.color = color;
        this.velocity = (float)((0.08 * this.power) + 1);
    }

    public void update() {
        this.x += (velocity) * Math.cos(this.angle); // + wind
        addedGravity = addedGravity + velocity * (gravity / FPS);

        this.y += addedGravity;
        this.y -= (velocity) * Math.sin(this.angle);

    }

    public void display(PApplet app) {
        update();
        app.fill(this.color[0], this.color[1], this.color[2]); 
        app.ellipse(this.x, this.y, 10, 10);
    }
}

