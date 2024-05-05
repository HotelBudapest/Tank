package Tanks;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Terrain  extends App{
    ArrayList<PVector> terrainCoordinates = new ArrayList<PVector>();
    ArrayList<Integer> treesLs = new ArrayList<Integer>();
    ArrayList<Integer> players = new ArrayList<Integer>();
    PImage treeLoc;
    public static ArrayList<Integer> heights = new ArrayList<Integer>();
    public static ArrayList<Integer> widths = new ArrayList<Integer>();
    public static ArrayList<Float> terrainForExplosion = new ArrayList<Float>();

    public Terrain(ArrayList<Integer> trees, PImage treeLoc,ArrayList<Integer> player){
        this.treesLs = trees;
        this.treeLoc = treeLoc;
        this.players = player;
    }

    public void smoothArray(){
            // ArrayList<PVector> avg = new ArrayList<PVector>();
            // for (int i = 0; i < this.terrainCoordinates.size(); i++){
            //     if ((i < this.terrainCoordinates.size() - 4)){
            //         float x1 = this.terrainCoordinates.get(i).x;
                    
            //         float y1 = this.terrainCoordinates.get(i).y;
            //         float y2 = this.terrainCoordinates.get(i+1).y;
            //         float y3 = this.terrainCoordinates.get(i+3).y;
            //         float y4 = this.terrainCoordinates.get(i+4).y;
            //         float avgy = (y1+y2+y3+y4)/4;
    
            //         avg.add(new PVector(x1, avgy));
                    
            //     }
            //     else{
            //         avg.add(this.terrainCoordinates.get(i));
            //     }
            // }

        ArrayList<Float> smoothedHeights1 = new ArrayList<>();
        ArrayList<Float> smoothedHeights2 = new ArrayList<>();

        // First pass of moving average
        for (int x = 0; x < heights.size(); x++) {
        float sum = 0;
        int count = 0;
        for (int i = x; i < x + 32 && i < heights.size(); i++) {
            sum += heights.get(i);
            count++;
        }
        smoothedHeights1.add(sum / count);
        }

        // Second pass of moving average
        for (int x = 0; x < smoothedHeights1.size(); x++) {
        float sum2 = 0;
        int count2 = 0;
        for (int i = x; i < x + 32 && i < smoothedHeights1.size(); i++) {
            sum2 += smoothedHeights1.get(i);
            count2++;
        }
            smoothedHeights2.add(sum2 / count2);
        }

        terrainForExplosion = smoothedHeights2;
        
        for (int i = 0; i < terrainForExplosion.size(); i++){
            widths.add(i);
        }

    }

    public void draw(PApplet app){
        for (int x =0; x < terrainForExplosion.size(); x++){
            app.rect(widths.get(x), terrainForExplosion.get(x), 1, HEIGHT - terrainForExplosion.get(x));
        }
    }

    public int[] getPlayerCoordsX(){
        int[] result = new int[this.players.size()];
        for (int i = 0; i < this.players.size(); i++){
            result[i] = widths.get(this.players.get(i));
        }
        return result;
    }

    public float[] getPlayerCoordsY(){
        float[] result = new float[this.players.size()];
        for (int i = 0; i < this.players.size(); i++){
            result[i] = terrainForExplosion.get(this.players.get(i)) - 5;
        }
        return result;
    }

    public void drawTrees(PApplet app){
        if (this.treeLoc != null){
            for (int i = 0; i < this.treesLs.size(); i++){
                int current = treesLs.get(i);
                app.image(treeLoc, widths.get(current)-16, terrainForExplosion.get(current)-30, CELLSIZE, CELLSIZE);
            }
        }
    }
    
}
