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
    player PlayerThatFired;
    boolean projectileHasHit;

    public Projectile(float x, float y, float power, float angle, int[] color, player PlayerThatFired){
        this.x = x;
        this.y = y;
        this.power = power;
        this.angle = angle;
        this.color = color;
        this.PlayerThatFired = PlayerThatFired;
        this.velocity = (float)((0.08 * this.power) + 1);
    }

    public void updatePlayerScore(){
        this.PlayerThatFired.score += 30;
    }

    public void update() {
        this.x += (velocity) * Math.cos(this.angle) + (Wind*0.03); // + wind
        addedGravity = addedGravity + velocity * (gravity / FPS);

        this.y += addedGravity;
        this.y -= (velocity) * Math.sin(this.angle);
    }

    public int getWind(){
        int change = (random.nextInt(11)-5);
        if ((Wind + change > 35) || (Wind + change < -35)){
            return 0;
        }
        else{
            return change;
        }
    }

    public void display(PApplet app) {
        update();
        app.noStroke();
        app.fill(this.color[0], this.color[1], this.color[2]); 
        app.ellipse(this.x, this.y, 10, 10);
    }
}

