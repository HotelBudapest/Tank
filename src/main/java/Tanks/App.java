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
import java.util.List;

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
    HashMap<String, Integer> playerLs = new HashMap<String, Integer>();
    ArrayList<player> playingOnBoard = new ArrayList<player>();
    public HashMap<String ,int[]> playerColorValues = new HashMap<String ,int[]>();
    player CurrentPlayer;
    int turnManagerINT = 0;

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
        JSONObject current = level.getJSONObject( 0);
        layout = current.getString("layout");
        backgroundImage = current.getString("background");
        String[] foregroundColour = current.getString("foreground-colour").split(",");
        System.out.println(Arrays.toString(foregroundColour));
        R = Integer.parseInt(foregroundColour[0]);
        G = Integer.parseInt(foregroundColour[1]);
        B = Integer.parseInt(foregroundColour[2]);
        JSONObject playerColoursJson = json.getJSONObject("player_colours");
        String[] playerTypes = {"A", "B", "C", "D", "E"};
        for (String type : playerTypes) {
            // Check if the type exists in the JSON
            if (playerColoursJson.hasKey(type)) {
                String colorStr = playerColoursJson.getString(type);
                String[] colorComponents = colorStr.split(",");
                int[] colorValues = new int[colorComponents.length];
                for (int k = 0; k < colorComponents.length; k++) {
                    colorValues[k] = Integer.parseInt(colorComponents[k]);
                }
                playerColorValues.put(type, colorValues);
            }
        }
        
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
                    else if ((line_sep[j].equals("A")) || line_sep[j].equals("B") || line_sep[j].equals("C") || line_sep[j].equals("D") || line_sep[j].equals("E")){ //((line_sep[j].equals("A")) || line_sep[j].equals("B") || line_sep[j].equals("C") || line_sep[j].equals("D"))
                        /*playerLs.add(new player(new PVector(j*10, i), "A"));
                        System.out.println(playerLs.get(lame).coordinates);
                        lame++;*/
                        if (line_sep[j].equals("A")){
                            playerLs.put("A",j*10);
                        }
                        else if (line_sep[j].equals("B")){
                            playerLs.put("B",j*10);
                        }
                        else if (line_sep[j].equals("C")){   
                            playerLs.put("C",j*10);
                        }
                        else if (line_sep[j].equals("D")){  
                            playerLs.put("D",j*10);
                        }
                        else if (line_sep[j].equals("E")){  
                            playerLs.put("E",j*10);
                        }
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

            Map<String, Integer> sortedMap = new TreeMap<>(playerLs);
            
            ArrayList<Integer> sortedplayer = new ArrayList<Integer>();
            for (int k = 0; k < sortedMap.size(); k++){
                if (k == 0){
                    String srt = "A";
                    sortedplayer.add(sortedMap.get(srt));
                }
                else if (k == 1){
                    String srt = "B";
                    sortedplayer.add(sortedMap.get(srt));
                }
                else if (k == 2){
                    String srt = "C";
                    sortedplayer.add(sortedMap.get(srt));
                }
                else if (k == 3){
                    String srt = "D";
                    sortedplayer.add(sortedMap.get(srt));
                }
                else if (k == 4){
                    String srt = "E";
                    sortedplayer.add(sortedMap.get(srt));
                }
            }

            terrain = new Terrain(test, treesLs, treepic, sortedplayer);
            
            
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();
            terrain.smoothArray();

            PVector[] coords = terrain.getPlayerCoords();
            for (int s =0; s < coords.length; s++){
                if (s == 0){
                    String srt = "A";
                    playingOnBoard.add(new player(coords[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 1){
                    String srt = "B";
                    playingOnBoard.add(new player(coords[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 2){
                    String srt = "C";
                    playingOnBoard.add(new player(coords[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 3){
                    String srt = "D";
                    playingOnBoard.add(new player(coords[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 4){
                    String srt = "E";
                    playingOnBoard.add(new player(coords[s], srt, playerColorValues.get(srt)));
                }
                System.out.println(coords[s].x + " " + coords[s].y + " " + playingOnBoard.get(s).type);
            }

            CurrentPlayer = playingOnBoard.get(0);

            reader.close();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        

    }

    public void manageTurns(int n){
        CurrentPlayer = playingOnBoard.get(n%(playingOnBoard.size())); 
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(KeyEvent event){
        if (key == ' '){
            turnManagerINT++;
            manageTurns(turnManagerINT);
        }
        if (key == CODED) {
            if ((keyCode == LEFT) && (terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 4 > 1)){
                CurrentPlayer.move(terrain.terrainCoordinates.get(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 4).x - 0.3f, terrain.terrainCoordinates.get(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 4).y - 0.3f);
                terrain.players.set(turnManagerINT%(playingOnBoard.size()), terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 1);
                CurrentPlayer.draw(this);
            } 
            else if ((keyCode == RIGHT) &&(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 2 < 265)){
                CurrentPlayer.move(terrain.terrainCoordinates.get(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 2).x - 0.3f, terrain.terrainCoordinates.get(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 2).y - 0.3f);
                terrain.players.set(turnManagerINT%(playingOnBoard.size()), terrain.players.get(turnManagerINT%(playingOnBoard.size())) + 1);
                CurrentPlayer.draw(this);
            }
            if ((keyCode == UP) && (CurrentPlayer.turretAngle > Math.toRadians(0))){
                CurrentPlayer.turretAngle -= Math.toRadians(3);
                float changeX = ((CurrentPlayer.coordinates.x + (CurrentPlayer.coordinates.x + 1))/2 - (2/CELLSIZE)) + (float)((0.45)*((Math.cos(CurrentPlayer.turretAngle + Math.toRadians(3)))));
                float changeY = CurrentPlayer.coordinates.y - (float)((0.45)*((Math.sin(CurrentPlayer.turretAngle + Math.toRadians(3)))));
                CurrentPlayer.moveTurret(changeX, changeY);
            } else if ((keyCode == DOWN)  && (CurrentPlayer.turretAngle <= Math.toRadians(180))){
                CurrentPlayer.turretAngle += Math.toRadians(3);
                float changeX = ((CurrentPlayer.coordinates.x + (CurrentPlayer.coordinates.x + 1))/2 - (2/CELLSIZE)) + (float)((0.45)*((Math.cos(CurrentPlayer.turretAngle - Math.toRadians(3)))));
                float changeY =  CurrentPlayer.coordinates.y - (float)((0.45)*((Math.sin(CurrentPlayer.turretAngle - Math.toRadians(3)))));
                CurrentPlayer.moveTurret(changeX, changeY);
            }
        }
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

        for (int i =0; i < playingOnBoard.size(); i++){
            player current = playingOnBoard.get(i);
            current.draw(this);
            if (current == CurrentPlayer){
                current.drawLine(this);
            }
        }

    }


    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}
