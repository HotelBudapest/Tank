package Tanks;
import processing.core.PApplet;
import processing.core.PVector;

public class player extends App{
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

    public player(int x, float y, String typ, int[] color){ //, PVector coordinateL, PVector coordinateR
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

    public void draw(PApplet app){
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
                    this.health -= 1;
                }
            }
        }
        app.noStroke();
        app.fill(this.color[0], this.color[1], this.color[2]);
        app.rect((this.x-9) + 2, (this.y), 16, 6, 10);
        app.fill(this.color[0], this.color[1], this.color[2]);
        app.rect((this.x-9), (this.y + 3), 21, 6, 10);
        app.stroke(0);
        app.strokeWeight(4);
        //app.line(this.x, this.y, this.x, this.y - 10);
        app.line((this.x + (this.x + CELLSIZE))/2  - 15, this.y, this.turretCoord.x-15, turretCoord.y);
        app.noStroke();
    }

    public void drawLine(PApplet app){
        app.stroke(0);
        app.strokeWeight(2);

        //app.translate(this.x*CELLSIZE + 2, (this.y)*CELLSIZE);
        app.line(this.x, (this.y) - 100, this.x, (this.y) - 45);
        
        app.line(this.x - 12, (this.y) - 65, this.x, (this.y) - 45);
        app.line(this.x + 12, (this.y) - 65, this.x, (this.y) - 45);
        app.noStroke();
    }

    public void move(int changedX, float changedY){
        /*int changedX;
        int changedY;
        if (direction == 'L') {
            changedX = -1;
        }*/
        if (fuel>0){
            int changeForTurretX = this.x - changedX;
            float changeForTurretY = this.y - changedY;
            this.x = changedX;
            this.y = changedY;
            this.turretCoord = new PVector(this.turretCoord.x - changeForTurretX, this.turretCoord.y - changeForTurretY);
            fuel--;
        }
    }

    public void moveTurret(float changedX, float changedY){
        this.turretCoord.x = changedX;
        this.turretCoord.y = changedY;
    }

}
