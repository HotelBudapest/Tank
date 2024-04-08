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
    String trees;

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
        JSONObject current = level.getJSONObject(0);
        layout = current.getString("layout");
        backgroundImage = current.getString("background");
        String foregroundColour = current.getString("foreground-colour");
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
                        board[i][j] = 2;
                    }
                }
                i++;
            }

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

        background(255); // Optional: Clears the background with white color
        image(loadImage(backgroundImage), 0, 0, width, height);

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (board[i][j] == 1){
                    for (int k = i; k <board.length;k++){
                        noStroke();
                        fill(255,255,255);
                        rect(j*CELLSIZE, k*CELLSIZE, CELLSIZE, CELLSIZE);
                    }
                }
                else if (board[i][j] == 2){
                    image(loadImage(trees), j*CELLSIZE, i*CELLSIZE, 30, 30);
                }
            }
        }
    }


    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}
