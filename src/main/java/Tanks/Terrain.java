package Tanks;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Terrain{
    ArrayList<PVector> terrainCoordinates = new ArrayList<PVector>();
    ArrayList<Integer> treesLs = new ArrayList<Integer>();
    ArrayList<Integer> players = new ArrayList<Integer>();
    PImage treeLoc;
    ArrayList<Integer> heights = new ArrayList<Integer>();
    public ArrayList<Integer> widths = new ArrayList<Integer>();
    public static ArrayList<Float> terrainForExplosion = new ArrayList<Float>();

    public Terrain(ArrayList<Integer> trees, PImage treeLoc,ArrayList<Integer> player, ArrayList<Integer> heights){
        this.treesLs = trees;
        this.treeLoc = treeLoc;
        this.players = player;
        this.heights = heights;
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

        ArrayList<Float> initialHeights = new ArrayList<Float>();
        for (int x = 0; x < this.heights.size(); x++) {
            float sum = 0;
            int count = 0;
            for (int i = x; i < x + 32 && i < this.heights.size(); i++) {
                sum += this.heights.get(i);
                count++;
            }
            initialHeights.add(sum / count);
        }

        ArrayList<Float> finalHeights = new ArrayList<Float>();
        for (int x = 0; x < initialHeights.size(); x++) {
            float sum2 = 0;
            int count2 = 0;
            for (int i = x; i < x + 32 && i < initialHeights.size(); i++) {
                sum2 += initialHeights.get(i);
                count2++;
            }
            finalHeights.add(sum2 / count2);
        }

        terrainForExplosion = finalHeights;
        
        for (int i = 0; i < terrainForExplosion.size(); i++){
            this.widths.add(i);
        }

    }

    public void draw(PApplet app){
        for (int x =0; x < terrainForExplosion.size(); x++){
            app.rect(this.widths.get(x), terrainForExplosion.get(x), 1,app.height - terrainForExplosion.get(x));
        }
    }

    public int[] getPlayerCoordsX(){
        int[] result = new int[this.players.size()];
        for (int i = 0; i < this.players.size(); i++){
            result[i] = this.widths.get(this.players.get(i));
            //System.out.println(result[i]);
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

    public void drawTrees(App app){
        if (this.treeLoc != null){
            for (int i = 0; i < this.treesLs.size(); i++){
                int current = treesLs.get(i);
                app.image(treeLoc, widths.get(current)-16, terrainForExplosion.get(current)-30, app.CELLSIZE, app.CELLSIZE);
            }
        }
    }
    
}
