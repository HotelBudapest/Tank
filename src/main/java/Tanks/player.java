package Tanks;

import Tanks.App;
import processing.core.PApplet;
import processing.core.PVector;

public class player extends App{
    public PVector coordinates;
    public String type; 
    public PVector coordinateL;
    public PVector coordinatesR;

    public player(PVector coord, String typ){ //, PVector coordinateL, PVector coordinateR
        this.coordinates = coord;
        this.type = type;
        //this.coordinateL = coordinateL;
        //this.coordinatesR = coordinateR;
    }

    public void draw(PApplet app){
        app.fill(0, 0, 255);
        app.rect((this.coordinates.x)*CELLSIZE + 2, (this.coordinates.y)*CELLSIZE, 20, 8, 10);
        app.fill(0, 0, 255);
        app.rect((this.coordinates.x)*CELLSIZE, (this.coordinates.y + 0.1f)*CELLSIZE, 25, 8, 10);
    }

    public void drawLine(PApplet app){
        
        app.stroke(0); // Set arrow color to black
        app.strokeWeight(2); // Set arrow thickness
    

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
    }

}
