package Tanks;

import processing.core.*;
import processing.event.KeyEvent;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    // App app;  // Declare app as a static field to use across all tests

    // @BeforeEach
    // public void setUp() {
    //     this.app = new App();
    //     PApplet.runSketch(new String[] { "Tanks" }, app);
    // }

    @Test
    void testAppMinimalStart() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.setup();
        
        app.delay(1000);
        assertNotNull(app);
    }

    @Test
    void testTerrainNotNull() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        
        app.setup();
        
        app.delay(1000);
        assertNotNull(app.terrain, "Terrain should not be null after app initialization.");
        app.dispose();
    }

    @Test
    void testPlayerListNotNull(){
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        
        app.setup();
        app.delay(1000);
        assertNotNull(app.playingOnBoard, "Player list should not be null.");
        app.dispose();
    }

    @Test
    void checkCurrentPlayer(){
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        
        app.setup();
        app.delay(1000);
        assertNotNull(app.CurrentPlayer, "Current player should not be null.");
        assertEquals(app.CurrentPlayer, app.playingOnBoard.get(0), "Current player should match the one at the turn manager index.");
        app.dispose();
    }

    @Test
    void changingPlayers(){
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        
        app.setup();
        app.delay(1000);
        int originalIndex = app.turnManagerINT;
        app.manageTurns(1);
        assertNotEquals(app.CurrentPlayer, app.playingOnBoard.get(originalIndex), "Current player should change after managing turns.");
        app.dispose();
    }

    @Test
    void ProjectileFired(){
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        
        app.setup();
        app.delay(1000);
        app.turnManagerINT = 0;
        player expectedPlayer = app.playingOnBoard.get(1);
        KeyEvent event = new KeyEvent(app, System.currentTimeMillis(), KeyEvent.PRESS, 0, ' ', java.awt.event.KeyEvent.VK_SPACE);
        
        app.keyPressed(event); 
        player newPlayer = app.CurrentPlayer;
        assertEquals(expectedPlayer, newPlayer ,"Current player should change after space bar press.");
        app.dispose();
        
    }

    // @Test
    // void MoveRight(){
        
    //     app.setup();
    //     app.manageTurns(0);
    //     player initialPlayer = app.CurrentPlayer;
    //     KeyEvent event = new KeyEvent(this, KeyEvent.PRESS, System.currentTimeMillis(), 0, keyCode.get(39), PConstants.CODED);       
    //     app.keyPressed(event); 
    //     player newPlayer = app.CurrentPlayer;
    //     assertNotEquals(initialPlayer, newPlayer, "Current player should change after space bar press.");
    // }

    @Test
    void testPowerChanging(){
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        KeyEvent event = new KeyEvent(app, System.currentTimeMillis(), KeyEvent.PRESS, 0, 'W', java.awt.event.KeyEvent.VK_W);
        app.setup();
        app.delay(1000);
        app.keyPressed(event);
        assertEquals(51, app.CurrentPlayer.power);
        app.dispose();
    }

}
