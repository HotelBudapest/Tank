package Tanks;

import Tanks.App;
import processing.core.PApplet;
import processing.core.PVector;

public class player extends App{
    public PVector coordinates;
    public String type; 

    public player(PVector coord, String type){
        this.coordinates = coord;
        this.type = type; 
    }

    public void draw(PApplet app){
        app.fill(0, 0, 255);
        app.rect((this.coordinates.x)*CELLSIZE + 2, (this.coordinates.y)*CELLSIZE, 20, 8, 10);
        app.fill(0, 0, 255);
        app.rect((this.coordinates.x)*CELLSIZE, (this.coordinates.y + 0.1f)*CELLSIZE, 25, 8, 10);
    }
}
