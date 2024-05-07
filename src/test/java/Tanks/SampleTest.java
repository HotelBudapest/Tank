package Tanks;


import processing.core.PApplet;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {


    public App app;
    public Terrain terrain;

    @BeforeEach
    void setUp() {
        this.app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.setup();
    }


    @Test
    void testTerrainNotNull() {
        assertNotNull(app.terrain);
    }

    @Test
    void testPlayerListNotNull(){
        assertNotNull(app.playingOnBoard);
    }


}
