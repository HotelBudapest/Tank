package Tanks;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32; //8;
    public static final int CELLHEIGHT = 32;

    public static final int CELLAVG = 32;
    public static final int TOPBAR = 0;
    public static int WIDTH = 864; //CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 640; //BOARD_HEIGHT*CELLSIZE+TOPBAR;
    public static final int BOARD_WIDTH = WIDTH/CELLSIZE;
    public static final int BOARD_HEIGHT = 20;
    public int[][] board = new int[32][32];
    ArrayList<Integer> treesLs = new ArrayList<Integer>();
    public Terrain terrain;
    ArrayList<PVector> test = new ArrayList<PVector>();
    
    
    String trees;
    public PImage treepic;
    int R;
    int G;
    int B;

    public static final int INITIAL_PARACHUTES = 1;

    public static final int FPS = 30;

    public String configPath;
    public String backgroundImage;
    String foregroundColour;
    String layout;

    public static Random random = new Random();
	
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);
        JSONObject json = loadJSONObject(configPath);

        JSONArray level = json.getJSONArray("levels");
        JSONObject current = level.getJSONObject(  0);
        layout = current.getString("layout");
        backgroundImage = current.getString("background");
        String[] foregroundColour = current.getString("foreground-colour").split(",");
        System.out.println(Arrays.toString(foregroundColour));
        R = Integer.parseInt(foregroundColour[0]);
        G = Integer.parseInt(foregroundColour[1]);
        B = Integer.parseInt(foregroundColour[2]);
        
        if(current.hasKey("trees")){
            treepic = loadImage(current.getString("trees"));
        }
        else{
            trees = null;
        }

        try{ 
			BufferedReader reader = new BufferedReader(new FileReader(layout));
			
            int i =0; 
            while (true){
                String line = reader.readLine();
                if (line == null){
                    break;
                }
                String[] line_sep = line.split("");
                for (int j = 0; j < line_sep.length; j++){
                    if (line_sep[j].equals("X")){
                        board[i][j] = 1;
                    }
                    else if (line_sep[j].equals("T")){
                        treesLs.add(j*10);
                    }
                }
                i++;
            }

            for (int s = 0; s < board.length; s++){
                for (int t = 0; t < board[0].length; t++){
                    if (board[t][s] == 1){
                        for (float n = 0; n <= 1; n+=0.1f){
                            test.add(new PVector(s + n, t));
                        }
                    }
                }
            }
            
            terrain = new Terrain(test, treesLs, treepic);
            
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();

            reader.close();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        

    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(KeyEvent event){
        
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TODO - powerups, like repair and extra fuel and teleport? - or maybe leave this as an extension


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    float findTerrainStart(int col) {
        for (int row = 0; row < board.length; row++) {
          if (board[row][col] == 1) {
            return row;
          }
        }
        return board.length; // Return the bottom if no '1' is found
      }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        

        //----------------------------------
        //display HUD:
        //----------------------------------
        //TODO

        //----------------------------------
        //display scoreboard:
        //----------------------------------
        //TODO
        
		//----------------------------------
        //----------------------------------

        //TODO: Check user action

        background(255); 
        image(loadImage(backgroundImage), 0, 0, width, height);
        
        noStroke();
        fill(R, G, B);
        beginShape();
        terrain.draw(this);
        endShape(CLOSE);

        terrain.drawTrees(this);
    }


    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}
