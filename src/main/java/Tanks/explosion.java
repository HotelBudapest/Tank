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

    public static void alterTerrain(App app, Projectile proj) { 

      // float[] previousHeights = new float[app.playingOnBoard.size()];
      // for (int s = 0; s < app.playingOnBoard.size(); s++){
      //   previousHeights[s] = Terrain.terrainForExplosion.get(app.playingOnBoard.get(s).x);
      //   System.out.println("previous height of " +  app.playingOnBoard.get(s).type + " " + previousHeights[s]);
      // }

      for (int i = Math.max(0, (int)proj.x - (int) r); i < Math.min(Terrain.terrainForExplosion.size(), (int)proj.x + (int) r); i++) {
          
          float d = Math.abs(i - proj.x);
          
          float semiCircleHeight = (float) Math.sqrt(Math.max(0, r * r - d * d));
          if (Terrain.terrainForExplosion.get(i)< proj.y + semiCircleHeight && Terrain.terrainForExplosion.get(i) > proj.y - semiCircleHeight) {
              
              terrainHeightBefore = Terrain.terrainForExplosion.get(i);
              Terrain.terrainForExplosion.set(i,proj.y + semiCircleHeight);
              terrainHeightAfter = Terrain.terrainForExplosion.get(i);
          } else if (Terrain.terrainForExplosion.get(i) < proj.y - semiCircleHeight) {

              terrainHeightBefore = Terrain.terrainForExplosion.get(i);
              Terrain.terrainForExplosion.set(i, Terrain.terrainForExplosion.get(i) + 2 * semiCircleHeight);
              Terrain.terrainForExplosion.set(i, Math.min(Terrain.terrainForExplosion.get(i), proj.y + semiCircleHeight));
              terrainHeightAfter = Terrain.terrainForExplosion.get(i);
          }
      }

      // for (int k = 0; k < app.playingOnBoard.size();k++){
      //   float prev = previousHeights[k];
      //   System.out.println("Later height of " +  app.playingOnBoard.get(k).type + " " + Terrain.terrainForExplosion.get(app.playingOnBoard.get(k).x));
      //   while(prev != Terrain.terrainForExplosion.get(app.playingOnBoard.get(k).x)){
      //     app.playingOnBoard.get(k).y += 0.2f;
      //     prev += 0.2f;
      //   }
      //   //app.playingOnBoard.get(k).noTerrainBelow = false;
      //   //app.playingOnBoard.get(i).y = Terrain.terrainForExplosion.get(app.playingOnBoard.get(i).x);
      // }
      // app.CurrentPlayer.draw(app);
    }

    public static void checkPlayerCollisions(App app, Projectile proj){
      float[] hitRadiusXLeft = {proj.x - r, proj.y - r};
      float[] hitRadiusXRight = {proj.x + r, proj.y + r}; 

      for (int i = 0; i < app.playingOnBoard.size(); i++){
        if ((app.playingOnBoard.get(i).x >= hitRadiusXLeft[0]) && (app.playingOnBoard.get(i).x <= hitRadiusXRight[0])){ //  && (app.playingOnBoard.get(i).y >= hitRadiusXLeft[1]) && (app.playingOnBoard.get(i).y <= hitRadiusXRight[1])
          if ((app.playingOnBoard.get(i).y >= hitRadiusXLeft[1]) && (app.playingOnBoard.get(i).y <= hitRadiusXRight[1])){
            if ((app.playingOnBoard.get(i).shield == 0)){
              app.playingOnBoard.get(i).health -= 30;
              if (app.playingOnBoard.get(i).health < app.playingOnBoard.get(i).power){
                app.playingOnBoard.get(i).power = app.playingOnBoard.get(i).health;
              }
              proj.updatePlayerScore();
            }
            else{
              app.playingOnBoard.get(i).shield--;
            }
          }
          if (app.playingOnBoard.get(i).health <= 0){
            drawExplosion(app, app.playingOnBoard.get(i).x, app.playingOnBoard.get(i).y);
            pastPlayerScores.put(app.playingOnBoard.get(i).type, app.playingOnBoard.get(i).score);
            app.playingOnBoard.remove(app.playingOnBoard.get(i));
          }
          app.playingOnBoard.get(i).isHit = true;
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
