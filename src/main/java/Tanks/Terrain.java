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

    /**
     * Constructor for the Terrain class.
     *
     * @param trees A list of tree locations as integers representing their x-coordinates.
     * @param treeLoc The image used to represent trees in the terrain.
     * @param player A list of integers representing the x-coordinates of players on the terrain.
     * @param heights A list of integers representing the initial heights of the terrain.
     */
    public Terrain(ArrayList<Integer> trees, PImage treeLoc,ArrayList<Integer> player, ArrayList<Integer> heights){
        this.treesLs = trees;
        this.treeLoc = treeLoc;
        this.players = player;
        this.heights = heights;
    }

    /**
     * Processes the heights list to create a smoothed terrain profile.
     * This method uses a moving average to smooth the terrain heights.
     */
    public void smoothArray(){
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

    /**
     * Draws the terrain on the provided PApplet drawing surface.
     *
     * @param app The PApplet surface on which to draw the terrain.
     */
    public void draw(PApplet app){
        for (int x =0; x < terrainForExplosion.size(); x++){
            app.rect(this.widths.get(x), terrainForExplosion.get(x), 1,app.height - terrainForExplosion.get(x));
        }
    }

    /**
     * Retrieves the x-coordinates for all players currently on the terrain.
     *
     * @return An array of integers representing the x-coordinates of all players.
     */
    public int[] getPlayerCoordsX(){
        int[] result = new int[this.players.size()];
        for (int i = 0; i < this.players.size(); i++){
            result[i] = this.widths.get(this.players.get(i));
            //System.out.println(result[i]);
        }
        return result;
    }

    /**
     * Retrieves the y-coordinates for all players currently on the terrain.
     *
     * @return An array of floats representing the y-coordinates just above the terrain for all players.
     */
    public float[] getPlayerCoordsY(){
        float[] result = new float[this.players.size()];
        for (int i = 0; i < this.players.size(); i++){
            result[i] = terrainForExplosion.get(this.players.get(i)) - 5;
        }
        return result;
    }

    /**
     * Draws trees on the terrain using the specified image for each tree.
     *
     * @param app The App instance on which to draw the trees.
     */
    public void drawTrees(App app){
        if (this.treeLoc != null){
            for (int i = 0; i < this.treesLs.size(); i++){
                int current = treesLs.get(i);
                app.image(treeLoc, widths.get(current)-16, terrainForExplosion.get(current)-30, app.CELLSIZE, app.CELLSIZE);
            }
        }
    }
    
}
