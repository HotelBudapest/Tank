package Tanks;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
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
    public int[][] treesLs = new int[32][32];
    public ArrayList<Terrain> terrainLS = new ArrayList<Terrain>();
    
    String trees;
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
        JSONObject current = level.getJSONObject(  1);
        layout = current.getString("layout");
        backgroundImage = current.getString("background");
        String[] foregroundColour = current.getString("foreground-colour").split(",");
        System.out.println(Arrays.toString(foregroundColour));
        R = Integer.parseInt(foregroundColour[0]);
        G = Integer.parseInt(foregroundColour[1]);
        B = Integer.parseInt(foregroundColour[2]);
        
        if(current.hasKey("trees")){
            trees = current.getString("trees");
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
                        treesLs[i][j] = 1;
                    }
                }
                i++;
            }

            smoothArray(board);

            for (int k = 0; k < board.length; k++){
                System.out.println(Arrays.toString(board[k]));
            }
            reader.close();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        

    }



    public void smoothArray(int[][] terrainArray){
        int numRows = terrainArray.length;
        int numCols = terrainArray[0].length;
        int[] startPoints = new int[numCols];

        // Step 1: Find the terrain start points for each column
        for (int col = 0; col < numCols; col++) {
            startPoints[col] = numRows; // Initialize with max value, assuming no terrain in this column
            for (int row = 0; row < numRows; row++) {
                if (terrainArray[row][col] == 1) {
                    startPoints[col] = row;
                    break;
                }
            }
        }

        // Step 2: Apply a simple moving average on the start points
        int[] smoothedStartPoints = new int[numCols];
        for (int col = 0; col < numCols; col++) {
            int sum = startPoints[col];
            int count = 1;
            
            // Include left neighbor
            if (col > 0) {
                sum += startPoints[col - 1];
                count++;
            }
            
            // Include right neighbor
            if (col < numCols - 1) {
                sum += startPoints[col + 1];
                count++;
            }
            
            smoothedStartPoints[col] = sum / count;
        }

        // Step 3: Adjust the terrain array based on the smoothed start points
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                if (row >= smoothedStartPoints[col]) {
                    terrainArray[row][col] = 1; // Terrain
                } else {
                    terrainArray[row][col] = 0; // No terrain
                }
            }
        }

        // Now, terrainArray contains your smoothed terrain
        board = terrainArray;

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

        fill(150); // Choose a color for your terrain
        beginShape(); // Begin your terrain shape
        
        // The first and last points need to be added twice for curveVertex to work properly
        // This is a workaround for the way Processing creates curves
        float firstX = 0;
        float firstY = findTerrainStart(0) * CELLSIZE; 
        float lastX = (board[0].length - 1) * CELLSIZE;
        float lastY = findTerrainStart(board[0].length - 1) * CELLSIZE;
        
        // Start with the bottom-left corner of the screen
        vertex(0, height);
        
        // Add the first point twice
        curveVertex(firstX, firstY);
        curveVertex(firstX, firstY);
        
        // Add a curveVertex for each '1' in the terrain array
        for (int col = 0; col < board[0].length; col++) {
          float x = col * CELLSIZE;
          float y = findTerrainStart(col) * CELLSIZE;
          curveVertex(x, y);
        }
        
        // Add the last point twice
        curveVertex(lastX, lastY);
        curveVertex(lastX, lastY);
        
        // Connect to the bottom-right corner of the screen
        vertex(width, height);
        
        endShape(CLOSE); 

        for (int i = 0; i < treesLs.length; i++){
            for (int j = 0; j < treesLs[0].length; j++){
                if (treesLs[i][j] == 1){
                    image(loadImage(trees), j*CELLSIZE, i*CELLSIZE, 30, 30);
                }
            }
        }
    }


    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}
