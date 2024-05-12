package Tanks;
import processing.core.PApplet;
import processing.core.PVector;

public class Player extends App implements Turret{
    //public PVector coordinates;
    public float y;
    public int x;
    public String type; 
    public PVector turretCoord;
    public float turretAngle = (float) Math.toRadians(90);
    public int[] color;
    public int health;
    public int power;
    public int score;
    public int shield = 0;
    public int parachutesLeft= 3;
    public boolean parachute_used = false;
    public boolean isHit;
    public int fuel;
    public boolean noTerrainBelow;

    /**
     * Constructor for the Player class.
     *
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     * @param typ The type or identifier for the player.
     * @param color An array of integers representing RGB color values for the player.
     */
    public Player(int x, float y, String typ, int[] color){ //, PVector coordinateL, PVector coordinateR
        this.x = x;
        this.y = y;
        this.type = typ;
        this.turretCoord = new PVector((this.x + (this.x + CELLSIZE))/2, (this.y - 15));
        this.color = color;
        this.health = 100;
        this.score = 0;
        this.power = 50;
        this.fuel = 250;
    }

    /**
     * Draws the player and its attributes on the game canvas.
     *
     * @param app The app instance used for drawing.
     */
    public void draw(App app){
        // if (noTerrainBelow){
        //     if (this.y ==  Terrain.terrainForExplosion.get(this.x)){
        //         noTerrainBelow = false;
        //     }
        //     this.turretCoord.y+=0.5;
        //     this.y += 0.5;
        // }
        if (isHit) {
            if (this.y > Terrain.terrainForExplosion.get(this.x) - 11) {
                isHit = false;
                if(this.parachute_used){
                    this.parachutesLeft--;
                    this.parachute_used = false;
                }
            }
            if (this.parachutesLeft > 0){
                app.image(parachuteIMG, (float)this.x - 14, this.y-CELLSIZE, CELLSIZE, CELLSIZE);
                this.turretCoord.y+=0.5;
                this.y += 0.5;
                this.parachute_used = true;
            }
            else{
                this.turretCoord.y += 2;
                this.y += 2;
                if (this.shield < 1){
                    this.health -= 2;
                }
                if (this.health <= 0){
                    Explosion.drawExplosion(app, this.x, this.y);
                    pastPlayerScores.put(this.type, this.score);
                    app.playingOnBoard.remove(this);
                  }
            }
        }
        if (this.shield > 0){
            app.fill(0, 0, 230, 90);
            app.ellipse(this.x, this.y, CELLSIZE, CELLSIZE);
        }
        app.noStroke();
        app.fill(this.color[0], this.color[1], this.color[2]);
        app.rect((this.x-9) + 2, (this.y), 16, 6, 10);
        app.fill(this.color[0], this.color[1], this.color[2]);
        app.rect((this.x-9), (this.y + 3), 21, 6, 10);
    }

    /**
     * Draws the player's turret on the game canvas.
     *
     * @param app The app instance used for drawing.
     */
    public void drawTurret(App app){
        app.stroke(0);
        app.strokeWeight(4);
        //app.line(this.x, this.y, this.x, this.y - 10);
        app.line((this.x + (this.x + CELLSIZE))/2  - 15, this.y, this.turretCoord.x-15, turretCoord.y);
        app.noStroke();
    }

    /**
     * Draws a line representation for the player, typically used for debugging.
     *
     * @param app The PApplet instance used for drawing.
     */
    public void drawLine(PApplet app){
        app.stroke(0);
        app.strokeWeight(2);

        //app.translate(this.x*CELLSIZE + 2, (this.y)*CELLSIZE);
        app.line(this.x, (this.y) - 100, this.x, (this.y) - 45);
        
        app.line(this.x - 12, (this.y) - 65, this.x, (this.y) - 45);
        app.line(this.x + 12, (this.y) - 65, this.x, (this.y) - 45);
        app.noStroke();
    }

    /**
     * Moves the player to a new position.
     *
     * @param changedX The new x-coordinate of the player.
     * @param changedY The new y-coordinate of the player.
     */
    public void move(int changedX, float changedY){
        if (fuel>0){
            int changeForTurretX = this.x - changedX;
            float changeForTurretY = this.y - changedY;
            this.x = changedX;
            this.y = changedY;
            this.turretCoord = new PVector(this.turretCoord.x - changeForTurretX, this.turretCoord.y - changeForTurretY);
            fuel--;
        }
    }

    /**
     * Moves the player's turret to a new angle.
     *
     * @param changedX The new x-coordinate for the turret.
     * @param changedY The new y-coordinate for the turret.
     */
    public void moveTurret(float changedX, float changedY){
        this.turretCoord.x = changedX;
        this.turretCoord.y = changedY;
    }

}
