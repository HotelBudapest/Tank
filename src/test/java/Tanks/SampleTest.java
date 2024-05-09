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
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        
        assertNotNull(app);
    }

    @Test
    void testTerrainNotNull() {
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        
        assertNotNull(app.terrain, "Terrain should not be null after app initialization.");
        // app.dispose();
    }

    @Test
    void testPlayerListNotNull(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        assertNotNull(app.playingOnBoard, "Player list should not be null.");
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
        app.dispose();
    }

    @Test
    void changingPlayers(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        app.turnManagerINT = 0;
        app.manageTurns(1);
        app.delay(2000);
        assertNotEquals(app.CurrentPlayer, app.playingOnBoard.get(0), "Current player should change after managing turns.");
    }

    @Test
    void testDraw(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        assertDoesNotThrow(() ->  {
            app.draw();});
    }

    @Test
    void ProjectileFired(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        
        app.delay(2000);
        app.turnManagerINT = 0;
        player expectedPlayer = app.playingOnBoard.get(1);
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
        app.delay(5000);
        player newPlayer = app.CurrentPlayer;
        System.out.println(app.CurrentPlayer);

        assertEquals(expectedPlayer, newPlayer ,"Current player should change after space bar press.");
        // app.dispose();
    }


    @Test
    void projectileFiredOutOFBound(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);
        
        app.delay(2000);
        app.turnManagerINT = 0;
        player expectedPlayer = app.playingOnBoard.get(1);
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
        app.delay(5000);
        player newPlayer = app.CurrentPlayer;
        System.out.println(app.CurrentPlayer);

        assertEquals(expectedPlayer, newPlayer ,"Current player should change after space bar press.");
        // app.dispose();
    }

    @Test
    void MoveRight(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);

        int initialPlayer = app.CurrentPlayer.x;
        //KeyEvent event = new KeyEvent(this, KeyEvent.PRESS, System.currentTimeMillis(), 0, keyCode.get(39), PConstants.CODED);       
        app.key = App.CODED;
        app.keyCode = App.RIGHT;
        app.keyPressed(null); 
        app.delay(2000);
        int newPlayer = app.CurrentPlayer.x;
        app.delay(1000);
        assertEquals(initialPlayer + 3, newPlayer, "Current player should change after space bar press.");
    }

    @Test
    void moveLeft(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);

        int initialPlayer = app.CurrentPlayer.x;
        //KeyEvent event = new KeyEvent(this, KeyEvent.PRESS, System.currentTimeMillis(), 0, keyCode.get(39), PConstants.CODED);       
        app.key = App.CODED;
        app.keyCode = App.LEFT;
        app.keyPressed(null); 
        app.delay(2000);
        int newPlayer = app.CurrentPlayer.x;
        app.delay(1000);
        assertEquals(initialPlayer - 3, newPlayer, "Current player should change after space bar press.");
    }

    @Test
    void testPowerUps(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(2000);
        app.setup();
        app.delay(5000);

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

    }

    @Test
    void moveRighGameOver(){
        App app = new App();        
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<player>();
        app.delay(2000);
        app.setup();
        app.delay(5000);

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
    }

    @Test
    void testRestartAfterGameOver(){
        App app = new App();        
        app.delay(2000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.stageManagerINT=2;
        app.delay(2000);
        app.playingOnBoard = new ArrayList<player>();
        app.delay(2000);
        app.setup();
        app.delay(5000);

        app.stageManagerINT++;
        
        app.delay(2000);
        app.draw();
        app.delay(3000);

        app.key = 'r';
        app.keyPressed(null);
        app.delay(2000);

        assertEquals(0, app.stageManagerINT);
    }

    @Test
    void testCombat(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<player>();
        app.delay(2000);
        app.setup();
        app.delay(5000);

        app.CurrentPlayer = app.playingOnBoard.get(2);
        app.delay(2000);
        app.CurrentPlayer.turretAngle = 0;
        app.delay(1000);
        app.CurrentPlayer.turretCoord = new PVector((float)670.9936, (float)362.938);
        app.delay(1000);
        app.CurrentPlayer.power = 75;
        app.delay(1000);

        player previousPlayer = app.CurrentPlayer;

        app.delay(1000);
        app.key = ' ';
        app.keyPressed(null);

        app.delay(3000);

        assertNotEquals(0, previousPlayer.score);
    }

    
    @Test
    void changeLevels(){
        App app = new App();
        app.delay(1000);
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.delay(1000);
        app.playingOnBoard = new ArrayList<player>();
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
    }


    @Test
    void testPowerChanging(){
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.setup();
        app.delay(5000);

        //KeyEvent event = new KeyEvent(app, System.currentTimeMillis(), KeyEvent.PRESS, 0, 'W', java.awt.event.KeyEvent.VK_W);
        app.key = 'w';
        app.keyPressed(null);
        app.delay(2000);
        assertEquals(51, app.CurrentPlayer.power);

        app.delay(2000);
        app.key = 's';
        app.keyPressed(null);
        app.delay(2000);
        assertEquals(50, app.CurrentPlayer.power);
    }

}
