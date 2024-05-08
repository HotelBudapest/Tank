package Tanks;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.*;
import java.util.*;

import jogamp.graph.curve.tess.Loop;

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
    ArrayList<Integer> terrainHeightsInitial = new ArrayList<Integer>();

    public boolean isgameOver;

    public Terrain terrain;

    //ArrayList<PVector> test = new ArrayList<PVector>();
    HashMap<String, Integer> playerLs = new HashMap<String, Integer>();
    ArrayList<player> playingOnBoard = new ArrayList<player>();
    public HashMap<String ,int[]> playerColorValues = new HashMap<String ,int[]>();
    player CurrentPlayer;
    ArrayList<player> backupForEndGame = new ArrayList<player>();

    
    public static HashMap<String, Integer> pastPlayerScores = new HashMap<String, Integer>();

    int turnManagerINT = 0;
    int stageManagerINT = 0;
    int arektaManager = 0;
    float startTime;

    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    public int Wind = random.nextInt(71) - 35;

    String trees;
    public PImage treepic;
    public PImage fuelIMG;
    public PImage Wind1;
    public PImage Wind2;
    player previousPlayer;

    public static PImage parachuteIMG;
    protected int R;
    protected int G;
    protected int B;

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
        
        startTime = frameCount;
        JSONObject json = loadJSONObject(configPath);

        JSONArray level = json.getJSONArray("levels");
        JSONObject current = level.getJSONObject(stageManagerINT%3);
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

        parachuteIMG  = loadImage("src/main/resources/Tanks/parachute.png");
        fuelIMG = loadImage("src/main/resources/Tanks/fuel.png");
        Wind1 = loadImage("src/main/resources/Tanks/wind.png");
        Wind2 = loadImage("src/main/resources/Tanks/wind-1.png");

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
                        treesLs.add(j*CELLSIZE);
                    }
                    else if ((line_sep[j].equals("A")) || line_sep[j].equals("B") || line_sep[j].equals("C") || line_sep[j].equals("D") || line_sep[j].equals("E")){ //((line_sep[j].equals("A")) || line_sep[j].equals("B") || line_sep[j].equals("C") || line_sep[j].equals("D"))
                        /*playerLs.add(new player(new PVector(j*10, i), "A"));
                        System.out.println(playerLs.get(lame).coordinates);
                        lame++;*/
                        if (line_sep[j].equals("A")){
                            playerLs.put("A",j*CELLSIZE);
                        }
                        else if (line_sep[j].equals("B")){
                            playerLs.put("B",j*CELLSIZE);
                        }
                        else if (line_sep[j].equals("C")){   
                            playerLs.put("C",j*CELLSIZE);
                        }
                        else if (line_sep[j].equals("D")){  
                            playerLs.put("D",j*CELLSIZE);
                        }
                        else if (line_sep[j].equals("E")){  
                            playerLs.put("E",j*CELLSIZE);
                        }
                    }
                }
                i++;
            }

            for (int s = 0; s < board.length; s++){
                for (int t = 0; t < board[0].length; t++){
                    if (board[t][s] == 1){
                        /*for (float n = 0; n <= 1; n+=0.1f){
                            test.add(new PVector(s + n, t));
                        }*/
                        for (int b =0; b < 32; b++){
                            terrainHeightsInitial.add(t*CELLSIZE);
                            //Terrain.widths.add((s + b));
                        }
                    }
                }
            }

            Map<String, Integer> sortedMap = new TreeMap<>(playerLs);

            String[] keys = {"A", "B", "C", "D", "E"};
            ArrayList<Integer> sortedplayer = new ArrayList<Integer>();
            for (String key : keys) {
                if (sortedMap.containsKey(key)) {
                    sortedplayer.add(sortedMap.get(key));
                }
            }

            terrain = new Terrain(treesLs, treepic, sortedplayer, terrainHeightsInitial);
            
            terrain.smoothArray();

            /*for (int j = 0; j < terrain.terrainCoordinates.size(); j++){
                explosiveCoords.put(terrain.terrainCoordinates.get(j).x*CELLSIZE, terrain.terrainCoordinates.get(j).y*CELLSIZE);
            }*/
            

            int[] coordsX = terrain.getPlayerCoordsX();
            float[] coordsY = terrain.getPlayerCoordsY();

            for (int s =0; s < coordsY.length; s++){
                if (s == 0){
                    String srt = "A";
                    playingOnBoard.add(new player(coordsX[s], coordsY[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 1){
                    String srt = "B";
                    playingOnBoard.add(new player(coordsX[s], coordsY[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 2){
                    String srt = "C";
                    playingOnBoard.add(new player(coordsX[s], coordsY[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 3){
                    String srt = "D";
                    playingOnBoard.add(new player(coordsX[s], coordsY[s], srt, playerColorValues.get(srt)));
                }
                else if (s == 4){
                    String srt = "E";
                    playingOnBoard.add(new player(coordsX[s], coordsY[s], srt, playerColorValues.get(srt)));
                }
                System.out.println(coordsX[s] + " " + coordsY[s] + " " + playingOnBoard.get(s).type);
            }

            CurrentPlayer = playingOnBoard.get(0);
            backupForEndGame = new ArrayList<player>(playingOnBoard);

            reader.close();

            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }

    }

    public void changeLevel(){
        for (int i = 0; i < playingOnBoard.size(); i++){
            pastPlayerScores.put(playingOnBoard.get(i).type, playingOnBoard.get(i).score);
        }
        System.out.println(pastPlayerScores.toString());
        noLoop();
        stageManagerINT++;
        if (stageManagerINT >= 3){
            sortPlayers(backupForEndGame);
            isgameOver = true;
            HUD.displayEndGame(this, 0);
        }
        turnManagerINT = 0;
        board = new int[32][32];
        treesLs = new ArrayList<Integer>();
        terrainHeightsInitial = new ArrayList<Integer>();
        projectiles = new ArrayList<Projectile>();
        playingOnBoard = new ArrayList<player>();
        setup();
        for (int i = 0; i < playingOnBoard.size(); i++){
            playingOnBoard.get(i).score = pastPlayerScores.get(playingOnBoard.get(i).type);
        }
        loop();
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
            Projectile projToBeAdded = new Projectile(CurrentPlayer.turretCoord.x - 15, CurrentPlayer.turretCoord.y, CurrentPlayer.power, CurrentPlayer.turretAngle, CurrentPlayer.color, CurrentPlayer);
            Wind += projToBeAdded.getWind();
            if (Wind > 35){
                Wind = 35;
            }
            else if (Wind < -35){
                Wind = -35;
            }
            projectiles.add(projToBeAdded);
            turnManagerINT++;
            manageTurns(turnManagerINT);
        }
        if ((key == CODED) && (!isgameOver)){
            if ((keyCode == LEFT) && (CurrentPlayer.x - 3 > 3)){ // terrain.terrainCoordinates.get(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 4).x - 0.3f, terrain.terrainCoordinates.get(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 4).y - 0.3f
                CurrentPlayer.move(CurrentPlayer.x - 3, Terrain.terrainForExplosion.get(CurrentPlayer.x - 3) - 3); // 
                terrain.players.set(turnManagerINT%(playingOnBoard.size()), terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 1);
                CurrentPlayer.draw(this);
            } 
            else if ((keyCode == RIGHT) &&(CurrentPlayer.x + 3 < WIDTH - 3)){
                CurrentPlayer.move(CurrentPlayer.x + 3, Terrain.terrainForExplosion.get(CurrentPlayer.x + 3) - 3); // , terrain.terrainCoordinates.get(terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 4).y - 0.3f
                terrain.players.set(turnManagerINT%(playingOnBoard.size()), terrain.players.get(turnManagerINT%(playingOnBoard.size())) - 1);
                CurrentPlayer.draw(this);
            }
            if ((keyCode == UP) && (CurrentPlayer.turretAngle > Math.toRadians(0))){
                CurrentPlayer.turretAngle -= 0.1f;
                float changeX = ((CurrentPlayer.x + (CurrentPlayer.x + CELLSIZE))/2) + (float)((15)*((Math.cos(CurrentPlayer.turretAngle))));
                float changeY = CurrentPlayer.y - (float)((15)*((Math.sin(CurrentPlayer.turretAngle))));
                CurrentPlayer.moveTurret(changeX, changeY);
            } else if ((keyCode == DOWN)  && (CurrentPlayer.turretAngle <= Math.toRadians(180))){
                CurrentPlayer.turretAngle += 0.1f;
                float changeX = ((CurrentPlayer.x + (CurrentPlayer.x + CELLSIZE))/2) + (float)((15)*((Math.cos(CurrentPlayer.turretAngle))));
                float changeY = CurrentPlayer.y - (float)((15)*((Math.sin(CurrentPlayer.turretAngle))));
                CurrentPlayer.moveTurret(changeX, changeY);
                //float changeX = ((CurrentPlayer.x + (CurrentPlayer.x + 1))/2 - (2/CELLSIZE)) + (float)((0.45)*((Math.cos(CurrentPlayer.turretAngle - Math.toRadians(3)))));
                //float changeY =  CurrentPlayer.y - (float)((0.45)*((Math.sin(CurrentPlayer.turretAngle - Math.toRadians(3)))));
                //CurrentPlayer.moveTurret(changeX, changeY);
            }
        }
        if (key == 'W' || key == 'w') {
            if (CurrentPlayer.power < CurrentPlayer.health){
                CurrentPlayer.power += 36/FPS;
            }
        }
        if (key == 'S' || key == 's') {
            if (CurrentPlayer.power > 0){
                CurrentPlayer.power -= 36/FPS;
            }
        }
        if (key == 'H' || key == 'h') {
            if (CurrentPlayer.score >= 20){
                CurrentPlayer.shield++;
                CurrentPlayer.score -= 20;
            }
        }
        if (key == 'p' || key == 'P') {
            if (CurrentPlayer.score >= 15){
                CurrentPlayer.parachutesLeft++;
                CurrentPlayer.score -= 15;
            }
        }
        if (key == 'F' || key == 'f') {
            if (CurrentPlayer.score >= 10){
                CurrentPlayer.fuel+=200;
                CurrentPlayer.score -= 10;
            }
        }
        if (key == 'y' || key == 'Y') {
            // for (int i = 65; i < 65 + pastPlayerScores.size(); i++){

            // }
            sortPlayers(backupForEndGame);
            isgameOver = true;
        }
        if (key == 'R' || key == 'r') {
            if (isgameOver){
                stageManagerINT = 0;
                isgameOver = false;
                restartGame();
            }
            else{
                if ((CurrentPlayer.score >= 20) && (CurrentPlayer.health <= 80)){
                    CurrentPlayer.health+=20;
                    CurrentPlayer.score -= 20;
                }
            }
        }
    }

    public void restartGame(){
        noLoop();
        turnManagerINT = 0;
        board = new int[32][32];
        treesLs = new ArrayList<Integer>();
        terrainHeightsInitial = new ArrayList<Integer>();
        arektaManager = 0;
        projectiles = new ArrayList<Projectile>();
        playingOnBoard = new ArrayList<player>();
        backupForEndGame = new ArrayList<player>();
        setup();
        loop();
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

    public void sortPlayers(ArrayList<player> players){
        for (int i = 1; i < players.size(); i++) {
            player current = players.get(i);
            int j = i - 1;
            while (j >= 0 && players.get(j).score < current.score) {
                players.set(j + 1, players.get(j));
                j = j - 1;
            }
            players.set(j + 1, current);
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        //TODO: Check user action

        if (isgameOver){
            if(((frameCount - startTime)/FPS)%1 == 0){
                fill(0);
                textSize(25);
                text("Player " + backupForEndGame.get(0).type +  " Wins!", WIDTH/2 - 6 * CELLSIZE, HEIGHT/2 - 6 * CELLSIZE + 15);
                
                fill(backupForEndGame.get(0).color[0], backupForEndGame.get(0).color[1], backupForEndGame.get(0).color[2], 20);
                stroke(0);
                rect(WIDTH/2 - 7 * CELLSIZE, HEIGHT/2 - 5 * CELLSIZE, 450, 100);
                fill(0);
                textSize(40);
                text("Final Scores", WIDTH/2 - 5 * CELLSIZE, HEIGHT/2 - 3 * CELLSIZE);
                fill(backupForEndGame.get(0).color[0], backupForEndGame.get(0).color[1], backupForEndGame.get(0).color[2], 20);
                rect(WIDTH/2 - 7 * CELLSIZE, HEIGHT/2 - 2 * CELLSIZE, 450, 250);
                HUD.displayEndGame(this, arektaManager%backupForEndGame.size());
                arektaManager++;
                if (arektaManager >= backupForEndGame.size()){
                    noLoop();
                }
            }
        }
        else{
                //background(255);
            image(loadImage(backgroundImage), 0, 0, WIDTH, HEIGHT);

            fill(R, G, B);
            terrain.draw(this);

            terrain.drawTrees(this);

            HUD.displayTEXTS(this);
            HUD.displayHealthBar(this);
            HUD.displayScoreBoard(this);
            
            //HUD.display(this);

            if (playingOnBoard.size() == 1){
                changeLevel();
            }
            if (stageManagerINT >= 3){
                sortPlayers(backupForEndGame);
                isgameOver = true;
            }

            for (int i =0; i < playingOnBoard.size(); i++){
                player current = playingOnBoard.get(i);
                current.draw(this);
                if (current == CurrentPlayer){
                    current.drawLine(this);
                }
            }

            for (int s = 0; s < projectiles.size(); s++){
                Projectile proj = projectiles.get(s);
                proj.update();
                proj.display(this);
                //System.out.println("Projectile of: " + proj.PlayerThatFired.type);
                //System.out.println(explosiveCoords.get(proj.x));
                try{
                    if (proj.y >= Terrain.terrainForExplosion.get((int) proj.x)) {
                        explosion.alterTerrain(this, proj);
                        noStroke();
                        explosion.drawExplosion(this, proj.x, proj.y);
                        explosion.checkPlayerCollisions(this, proj);
                        projectiles.remove(s);
                    }
                }
                catch(IndexOutOfBoundsException e){
                    projectiles.remove(s);
                }
            }
            }
        }
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


    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}
