package Tanks;

import processing.core.*;
import processing.event.KeyEvent;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SampleTest {

    //  App app;  // Declare app as a static field to use across all tests

    // @BeforeEach
    // public void setUp() {
    //     this.app = new App();
    //     PApplet.runSketch(new String[] { "Tanks" }, app);
    //     app.setup();
    //     app.delay(5000);
    // }

    @Test
    void testAppMinimalStart() {
        App app1 = new App();
        
        app1.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app1);
        app1.delay(2000);
        app1.setup();
        app1.delay(2000);
        
        assertNotNull(app1);

        app1.delay(1000);
        
        App app2 = new App();

        app1.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app2);
        app2.delay(2000);
        app2.testing = true;
        app2.setup();
        app2.delay(2000);
        app2.draw();
        
        assertNotNull(app2);

        app2.delay(1000);
    }

    @Test
    void testTerrainNotNull() {
        App app = new App();
        
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        
        assertNotNull(app.terrain, "Terrain should not be null after app initialization.");
        
        app.delay(1000);
    }

    @Test
    void testPlayerListNotNull(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        assertNotNull(app.playingOnBoard, "Player list should not be null.");
        
        app.delay(1000);
    }

    @Test
    void checkCurrentPlayer(){  
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        assertNotNull(app.CurrentPlayer, "Current player should not be null.");
        assertEquals(app.CurrentPlayer, app.playingOnBoard.get(0), "Current player should match the one at the turn manager index.");
        
        app.delay(1000);
    }

    @Test
    void changingPlayers(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        app.turnManagerINT = 0;
        app.manageTurns(1);
        app.delay(2000);
        assertNotEquals(app.CurrentPlayer, app.playingOnBoard.get(0), "Current player should change after managing turns.");
        
        app.delay(1000);
    }

    @Test
    void testDraw(){
        App app = new App();
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(2000);
        assertDoesNotThrow(() ->  {
            app.delay(1000);
            app.draw();});
            
        app.delay(1000);
    }
    

    @Test
    void ProjectileFired(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(2000);
        
        app.delay(2000);
        app.turnManagerINT = 0;
        Player expectedPlayer = app.playingOnBoard.get(1);
        app.delay(3000);
        app.key = App.CODED;
        app.keyCode = App.UP;
        app.delay(2000);
        app.keyPressed(null);
        app.delay(2000);
        app.keyPressed(null);
        app.delay(2000);
        
        app.key = ' ';
        app.keyPressed(null); 
        app.delay(2000);
        Player newPlayer = app.CurrentPlayer;

        app.delay(1000);
        assertEquals(expectedPlayer, newPlayer ,"Current player should change after space bar press.");
        app.delay(1000);
    }


    @Test
    void projectileFiredOutOFBound(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        
        app.delay(2000);
        app.turnManagerINT = 0;
        Player expectedPlayer = app.playingOnBoard.get(1);
        app.delay(3000);
        app.key = App.CODED;
        app.keyCode = App.DOWN;
        app.delay(2000);
        app.keyPressed(null);
        app.delay(2000);
        app.keyPressed(null);
        app.delay(2000);
        
        app.CurrentPlayer.power = 75;
        app.delay(2000);

        app.key = ' ';
        app.keyPressed(null); 
        app.delay(2000);
        Player newPlayer = app.CurrentPlayer;
        System.out.println(app.CurrentPlayer);

        assertEquals(expectedPlayer, newPlayer ,"Current player should change after space bar press.");
        
        app.delay(1000);
    }

    @Test
    void MoveRight(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(3000);

        int initialPlayer = app.CurrentPlayer.x;
        //KeyEvent event = new KeyEvent(this, KeyEvent.PRESS, System.currentTimeMillis(), 0, keyCode.get(39), PConstants.CODED);       
        app.key = App.CODED;
        app.keyCode = App.RIGHT;
        app.keyPressed(null); 
        app.delay(2000);
        int newPlayer = app.CurrentPlayer.x;
        app.delay(1000);
        assertEquals(initialPlayer + 3, newPlayer, "Current player should change after space bar press.");
        
        app.delay(1000);

        initialPlayer = app.CurrentPlayer.x;
        app.delay(1000);
        app.CurrentPlayer.fuel = 0;
        app.keyPressed(null); 
        app.delay(2000);
        newPlayer = app.CurrentPlayer.x;

        assertEquals(initialPlayer, newPlayer, "Current player should change after space bar press.");

        app.delay(1000);

        app.CurrentPlayer.fuel = 100;
        app.CurrentPlayer.x = App.WIDTH - 6; 
        app.CurrentPlayer.y = Terrain.terrainForExplosion.get(app.CurrentPlayer.x);
        app.CurrentPlayer.turretCoord = new PVector(app.CurrentPlayer.x, app.CurrentPlayer.y - 15);
        int initialPlayerPosition = app.CurrentPlayer.x;
        app.delay(1000);
        app.keyPressed(null); 
        app.delay(2000);
        int newPlayerPosition = app.CurrentPlayer.x;
        app.delay(1000);
        assertEquals(initialPlayerPosition, newPlayerPosition, "Current player should change after space bar press.");
        app.delay(1000);
    }

    @Test
    void moveLeft(){
        App app = new App();
        
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(3000);

        int initialPlayer = app.CurrentPlayer.x;
        //KeyEvent event = new KeyEvent(this, KeyEvent.PRESS, System.currentTimeMillis(), 0, keyCode.get(39), PConstants.CODED);       
        app.key = App.CODED;
        app.keyCode = App.LEFT;
        app.keyPressed(null); 
        app.delay(2000);
        int newPlayer = app.CurrentPlayer.x;
        app.delay(1000);
        assertEquals(initialPlayer - 3, newPlayer, "Current player should change after space bar press.");
        app.delay(1000);

        app.CurrentPlayer.x = 6; 
        app.CurrentPlayer.y = Terrain.terrainForExplosion.get(app.CurrentPlayer.x);
        app.CurrentPlayer.turretCoord = new PVector(app.CurrentPlayer.x, app.CurrentPlayer.y - 15);
        int initialPlayerPosition = app.CurrentPlayer.x;
        app.delay(1000);
        app.keyPressed(null); 
        app.delay(2000);
        int newPlayerPosition = app.CurrentPlayer.x;
        app.delay(1000);
        assertEquals(initialPlayerPosition, newPlayerPosition, "Current player should change after space bar press.");
        app.delay(1000);

    }

    @Test
    void testPowerUps(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(3000);


        app.delay(2000);
        app.key = 'P';
        app.keyPressed(null); 

        app.delay(1000);
        
        assertEquals(3, app.CurrentPlayer.parachutesLeft);
        
        app.delay(1000);

        app.key = 'F';
        app.keyPressed(null); 
        app.delay(1000);

        assertEquals(250, app.CurrentPlayer.fuel);

        app.delay(1000);

        app.key = 'R';
        app.keyPressed(null); 
        app.delay(1000);
        
        assertEquals(100, app.CurrentPlayer.health);

        app.delay(1000);

        app.key = 'H';
        app.keyPressed(null); 
        app.delay(1000);

        app.delay(1000);
        
        assertEquals(0, app.CurrentPlayer.shield);
        
        app.delay(1000);

        //////////////////////////

        app.CurrentPlayer.score = 200;
        app.delay(2000);
        app.key = 'p';
        app.keyPressed(null); 
        
        app.delay(2000);

        assertEquals(4, app.CurrentPlayer.parachutesLeft);

        app.delay(2000);

        app.CurrentPlayer.score = 100;
        app.delay(2000);
        app.key = 'f';
        app.keyPressed(null); 
        
        app.delay(2000);

        assertEquals(450, app.CurrentPlayer.fuel);
        
        app.delay(2000);

        app.CurrentPlayer.score = 100;
        app.CurrentPlayer.health = 50;
        app.delay(2000);
        app.key = 'r';
        app.keyPressed(null); 
        
        app.delay(2000);

        assertEquals(70, app.CurrentPlayer.health);
                
        app.delay(2000);

        app.CurrentPlayer.score = 100;
        app.delay(2000);
        app.key = 'h';
        app.keyPressed(null); 
        
        app.delay(2000);

        assertEquals(1, app.CurrentPlayer.shield);

        app.delay(1000);
    }

    @Test
    void moveRighGameOver(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(3000);

        int initialPlayer = app.CurrentPlayer.x;
        app.key = App.CODED;
        app.keyCode = App.RIGHT;
        app.isgameOver = true;
        app.delay(2000);
        app.keyPressed(null); 
        app.delay(2000);
        int newPlayer = app.CurrentPlayer.x;
        app.delay(1000);
        assertNotEquals(initialPlayer + 3, newPlayer, "Current player should change after space bar press.");
        
        app.delay(1000);
    }

    @Test
    void testRestartAfterGameOver(){
        App app = new App();        
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.stageManagerINT=2;
        app.delay(2000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(3000);

        app.stageManagerINT++;
        
        app.delay(2000);
        app.draw();
        app.delay(3000);

        app.key = 'r';
        app.keyPressed(null);
        app.delay(2000);

        assertEquals(0, app.stageManagerINT);
        
        app.delay(1000);
    }

    @Test
    void testTurretExtremes(){
        App app = new App();        
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(3000);

        app.CurrentPlayer.turretAngle = (float)Math.toRadians(0);
        app.delay(2000);
        app.key = App.CODED;
        app.key = App.UP;
        app.delay(2000);
        app.key = App.CODED;
        app.key = App.UP;
        app.delay(2000);
        app.keyPressed(null);
        app.delay(1000);

        assertEquals(0, app.CurrentPlayer.turretAngle);

        app.delay(2000);

        app.CurrentPlayer.turretAngle = (float)Math.toRadians(180);
        app.delay(1000);
        app.key = App.CODED;
        app.key = App.DOWN;
        app.delay(2000);
        app.key = App.CODED;
        app.key = App.DOWN;
        app.delay(1000);
        app.keyPressed(null);
        app.delay(2000);

        assertEquals((float)Math.toRadians(180), app.CurrentPlayer.turretAngle);
        app.delay(1000);
    }

    @Test
    void testWind(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<Player>();
        int weirdWind = -40;
        app.delay(1000);
        app.Wind = weirdWind;
        app.delay(2000);
        app.setup();
        app.delay(2000);
        app.Wind = weirdWind;
        app.key = ' ';
        app.keyPressed(null);
        app.delay(2000);

        assertNotEquals(weirdWind, app.Wind);

        app.delay(2000);
        weirdWind = +40;
        app.delay(1000);
        app.Wind = weirdWind;
        
        app.key = ' ';
        app.keyPressed(null);
        app.delay(2000);

        assertNotEquals(weirdWind, app.Wind);
        app.delay(1000);
    }

    @Test
    void testCombatDeath(){
        App app = new App();
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(2000);

        app.playingOnBoard.get(3).health = 10;
        app.delay(1000);

        app.CurrentPlayer = app.playingOnBoard.get(2);
        app.delay(2000);
        app.CurrentPlayer.turretAngle = 0;
        app.delay(1000);
        app.CurrentPlayer.turretCoord = new PVector((float)670.9936, (float)362.938);
        app.delay(1000);
        app.CurrentPlayer.power = 75;
        app.delay(1000);

        Player previousPlayer = app.CurrentPlayer;

        app.delay(1000);
        app.key = ' ';
        app.keyPressed(null);

        app.delay(2000);

        assertEquals(30, previousPlayer.score);
        app.delay(1000);
    }

    @Test
    void testCombat(){
        App app = new App();
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(2000);

        app.playingOnBoard.get(3).shield = 1;
        app.playingOnBoard.get(3).health = 10;
        app.delay(1000);

        app.CurrentPlayer = app.playingOnBoard.get(2);
        app.delay(2000);
        app.CurrentPlayer.turretAngle = 0;
        app.delay(1000);
        app.CurrentPlayer.turretCoord = new PVector((float)670.9936, (float)362.938);
        app.delay(1000);
        app.CurrentPlayer.power = 75;
        app.delay(1000);

        Player previousPlayer = app.CurrentPlayer;

        app.delay(1000);
        app.key = ' ';
        app.keyPressed(null);

        app.delay(2000);

        assertEquals(0, previousPlayer.score);
        app.delay(1000);
    }

    @Test
    void testCombatNoParachute(){
        App app = new App();
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(2000);

        app.playingOnBoard.get(2).shield = 1;
        app.playingOnBoard.get(3).parachutesLeft = 0;
        app.playingOnBoard.get(3).power = 90;
        app.playingOnBoard.get(3).health = 65;
        app.Wind =0;
        app.playingOnBoard.get(3).shield = 0;
        app.delay(1000);

        app.CurrentPlayer = app.playingOnBoard.get(2);
        app.delay(2000);
        app.CurrentPlayer.turretAngle = 0;
        app.delay(1000);
        app.CurrentPlayer.turretCoord = new PVector((float)670.9936, (float)362.938);
        app.delay(1000);
        app.CurrentPlayer.power = 75;
        app.delay(1000);

        Player previousPlayer = app.CurrentPlayer;

        app.delay(1000);
        app.key = ' ';
        app.keyPressed(null);

        app.delay(2000);

        assertNotEquals(0, previousPlayer.score);
        app.delay(1000);
    }

    @Test
    void furtherCombatTesting(){
        App app = new App();
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(2000);

        app.playingOnBoard.get(3).power = 90;
        app.delay(1000);
        app.CurrentPlayer = app.playingOnBoard.get(2);
        app.delay(2000);
        app.CurrentPlayer.turretAngle = 0;        
        app.delay(1000);
        app.CurrentPlayer.x = 667;
        app.CurrentPlayer.y = 413.4375f;
        app.delay(1000);
        app.CurrentPlayer.turretCoord = new PVector((float)697.9624, (float)412.37643);
        app.delay(1000);
        app.CurrentPlayer.power = 75;
        app.delay(1000);

        Player previousPlayer = app.CurrentPlayer;

        app.delay(1000);
        app.key = ' ';
        app.keyPressed(null);

        app.delay(2000);

        assertEquals(0, previousPlayer.score);
        app.delay(1000);
    }

    
    @Test
    void changeLevels(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<Player>();
        app.delay(2000);
        app.setup();
        app.delay(5000);

        App.pastPlayerScores.put("A", 100);
        app.delay(1000);
        App.pastPlayerScores.put("B", 100);
        app.delay(1000);
        App.pastPlayerScores.put("C", 100);
        app.delay(1000);
        App.pastPlayerScores.put("D", 100);
        app.delay(2000);

        app.playingOnBoard.remove(app.playingOnBoard.size() - 1);
        app.delay(2000);
        app.playingOnBoard.remove(app.playingOnBoard.size() - 1);
        app.delay(2000);
        app.playingOnBoard.remove(app.playingOnBoard.size() - 1);

        app.delay(2000);
        app.draw();
        app.delay(2000);
        assertEquals(1, app.stageManagerINT, "Current player should change after space bar press.");
        app.delay(1000);
    }


    @Test
    void testPowerChanging(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.setup();
        app.delay(2000);

        
        app.CurrentPlayer.power = 100;
        app.key = 'W';
        app.keyPressed(null);
        app.delay(2000);
        assertEquals(100, app.CurrentPlayer.power);

        app.delay(2000);
        app.CurrentPlayer.power = 0;
        app.key = 'S';
        app.keyPressed(null);
        app.delay(2000);
        assertEquals(0, app.CurrentPlayer.power);
        app.delay(1000);

        app.CurrentPlayer.power = 50;
        app.key = 'w';
        app.keyPressed(null);
        app.delay(2000);
        assertEquals(51, app.CurrentPlayer.power);

        app.delay(2000);
        app.key = 's';
        app.keyPressed(null);
        app.delay(2000);
        assertEquals(50, app.CurrentPlayer.power);
        app.delay(1000);
    }

}
