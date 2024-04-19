package Tanks;

import Tanks.App;
import processing.core.PApplet;
import processing.core.PVector;

public class player extends App{
    public PVector coordinates;
    public String type; 
    public PVector turretCoord;
    public double turretAngle = Math.toRadians(90);

    public player(PVector coord, String typ){ //, PVector coordinateL, PVector coordinateR
        this.coordinates = coord;
        this.type = type;
        this.turretCoord = new PVector((this.coordinates.x + (this.coordinates.x + 1))/2, (this.coordinates.y - ((float)15/CELLSIZE)));
        //this.coordinateL = coordinateL;
        //this.coordinatesR = coordinateR;
    }

    public void draw(PApplet app){
        app.fill(0, 0, 255);
        app.rect((this.coordinates.x)*CELLSIZE + 2, (this.coordinates.y)*CELLSIZE, 20, 8, 10);
        app.fill(0, 0, 255);
        app.rect((this.coordinates.x)*CELLSIZE, (this.coordinates.y + 0.1f)*CELLSIZE, 25, 8, 10);
        app.stroke(0);
        app.strokeWeight(2);
        //app.line(this.coordinates.x*CELLSIZE, this.coordinates.y*CELLSIZE, this.coordinates.x*CELLSIZE, this.coordinates.y*CELLSIZE - 10);
        app.line((this.coordinates.x + (this.coordinates.x + 1))/2*CELLSIZE - 2 , this.coordinates.y*CELLSIZE, this.turretCoord.x*CELLSIZE - 2, turretCoord.y*CELLSIZE);
        app.noStroke();
    }

    public void drawLine(PApplet app){
        
        app.stroke(0);
        app.strokeWeight(2);

        //app.translate(this.coordinates.x*CELLSIZE + 2, (this.coordinates.y)*CELLSIZE);
        app.line(this.coordinates.x*CELLSIZE + 12, (this.coordinates.y)*CELLSIZE - 100, this.coordinates.x*CELLSIZE + 12, (this.coordinates.y)*CELLSIZE - 45);
        
        app.line(this.coordinates.x*CELLSIZE, (this.coordinates.y)*CELLSIZE - 65, this.coordinates.x*CELLSIZE + 12, (this.coordinates.y)*CELLSIZE - 45);
        app.line(this.coordinates.x*CELLSIZE + 24, (this.coordinates.y)*CELLSIZE - 65, this.coordinates.x*CELLSIZE + 12, (this.coordinates.y)*CELLSIZE - 45);
        app.noStroke();
        // Draw the arrowhead
    }

    public void move(float changedX, float changedY){
        this.coordinates.x = changedX;
        this.coordinates.y = changedY;
        turretCoord = new PVector((this.coordinates.x + (this.coordinates.x + 1))/2, (this.coordinates.y - ((float)15/CELLSIZE)));
    }

    public void moveTurret(float changedX, float changedY){
        this.turretCoord.x = changedX;
        this.turretCoord.y = changedY;
    }

}