package Tanks;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;

public class Terrain  extends App{
    ArrayList<PVector> terrainCoordinates = new ArrayList<PVector>();

    public Terrain(ArrayList<PVector> points){
        this.terrainCoordinates = points;
    }

    public void smoothArray(){
        ArrayList<PVector> avg = new ArrayList<PVector>();
        for (int i = 0; i < this.terrainCoordinates.size(); i++){
            if ((i < this.terrainCoordinates.size() - 4)){
                float x1 = this.terrainCoordinates.get(i).x;
                
                float y1 = this.terrainCoordinates.get(i).y;
                float y2 = this.terrainCoordinates.get(i+1).y;
                float y3 = this.terrainCoordinates.get(i+3).y;
                float y4 = this.terrainCoordinates.get(i+4).y;
                float avgy = (y1+y2+y3+y4)/4;
   
                avg.add(new PVector(x1, avgy));
            }
            else{
                avg.add(this.terrainCoordinates.get(i));
            }
        }

        this.terrainCoordinates = avg;

    }

    public void draw(PApplet app){
        app.vertex(0, app.height);
        for (PVector coord : this.terrainCoordinates) {
            app.vertex(coord.x * CELLSIZE, coord.y * CELLSIZE);
          }
        app.vertex(app.width, app.height);
    }
    
}
