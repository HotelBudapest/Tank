package Tanks;

import java.util.*;

import processing.core.PApplet;
public class explosion extends App{
    public static float terrainHeightBefore = 0.0f;
    public static float terrainHeightAfter = 0.0f;
    public static final int[] INNER = new int[] { 253, 248, 172 };
    public static final int[] MIDDLE = new int[] { 255, 135, 33 };
    public static final int[] OUTER = new int[] { 252, 1, 4 };

    public static final int INNER_RADIUS = 10;
    public static final int MIDDLE_RADIUS = 20;
    public static final int OUTER_RADIUS = 30;
    public static float r = 30f;

    public static void alterTerrain(App app, int xc, float yc) { 

        for (int i = Math.max(0, xc - (int) r); i < Math.min(Terrain.terrainForExplosion.size(), xc + (int) r); i++) {
            // Calculate the horizontal distance from the center of the explosion
            float d = Math.abs(i - xc);
            // Calculate the height of the semicircle at this distance
            float semiCircleHeight = (float) Math.sqrt(Math.max(0, r * r - d * d));

            // Since y increases downwards in computer graphics, we have to "add" to lower
            // the terrain for the crater
            if (Terrain.terrainForExplosion.get(i)< yc + semiCircleHeight && Terrain.terrainForExplosion.get(i) > yc - semiCircleHeight) {
                // Lower the terrain to yc + the semicircle height to create a crater
                terrainHeightBefore = Terrain.terrainForExplosion.get(i);
                Terrain.terrainForExplosion.set(i,yc + semiCircleHeight);
                terrainHeightAfter = Terrain.terrainForExplosion.get(i);
            } else if (Terrain.terrainForExplosion.get(i) < yc - semiCircleHeight) {
                // If the terrain is above the explosion (lower y value), we add to it to
                // "lower" it in screen space
                terrainHeightBefore = Terrain.terrainForExplosion.get(i);
                Terrain.terrainForExplosion.set(i, Terrain.terrainForExplosion.get(i) + 2 * semiCircleHeight);
                // Ensure the terrain does not go below (higher on screen) the semicircle height
                Terrain.terrainForExplosion.set(i, Math.min(Terrain.terrainForExplosion.get(i), yc + semiCircleHeight));
                terrainHeightAfter = Terrain.terrainForExplosion.get(i);
            }
        }

        app.CurrentPlayer.draw(app);
    }

    public static void checkPlayerCollisions(App app, float xc, float yc, player playerShot){
      float[] hitRadiusXLeft = {xc - r, yc - r};
      float[] hitRadiusXRight = {xc + r, yc + r}; 

      for (int i = 0; i < app.playingOnBoard.size(); i++){
        if ((app.playingOnBoard.get(i).x >= hitRadiusXLeft[0]) && (app.playingOnBoard.get(i).x <= hitRadiusXRight[0]) && (app.playingOnBoard.get(i).y >= hitRadiusXLeft[1]) && (app.playingOnBoard.get(i).y <= hitRadiusXRight[1])){
          app.playingOnBoard.get(i).health -= 30;
          playerShot.score += 30;
          if (app.playingOnBoard.get(i).health <= 0){
            drawExplosion(app, app.playingOnBoard.get(i).x, app.playingOnBoard.get(i).y);
            app.playingOnBoard.remove(app.playingOnBoard.get(i));
            if (app.playingOnBoard.isEmpty()){
                app.displayEndGame();
            }
          }
          app.playingOnBoard.get(i).isHit = true;
          // app.playingOnBoard.get(i).inAir(Terrain.terrainForExplosion.get(app.playingOnBoard.get(i).x));
          app.CurrentPlayer.score += 30;
        }
      }
    }

    public static void drawExplosion(App app, float x, float y) {
        for (int i = 0; i < 6; i++) {
          if (i == 0 || i == 1) {
            app.fill(INNER[0], INNER[1], INNER[2]);
            app.ellipse(x, y, INNER_RADIUS * 2, INNER_RADIUS * 2);
          } else if (i == 2 || i == 3) {
            app.fill(MIDDLE[0], MIDDLE[1], MIDDLE[2]);
            app.ellipse(x, y, MIDDLE_RADIUS * 2, MIDDLE_RADIUS * 2);
            app.fill(INNER[0], INNER[1], INNER[2]);
            app.ellipse(x, y, INNER_RADIUS * 2, INNER_RADIUS * 2);
          } else {
            app.fill(OUTER[0], OUTER[1], OUTER[2]);
            app.ellipse(x, y, OUTER_RADIUS * 2, OUTER_RADIUS * 2);
            app.fill(MIDDLE[0], MIDDLE[1], MIDDLE[2]);
            app.ellipse(x, y, MIDDLE_RADIUS * 2, MIDDLE_RADIUS * 2);
            app.fill(INNER[0], INNER[1], INNER[2]);
            app.ellipse(x, y, INNER_RADIUS * 2, INNER_RADIUS * 2);
          }
        }
      }
}
