package Tanks;

import processing.core.PApplet;

public class HUD extends App{

    public static void displayTEXTS(App app){
        app.fill(0);
        app.textSize(16);
        app.text("Player " + app.CurrentPlayer.type + "'s Turn", 13, CELLSIZE);
                
        app.fill(0);
        app.textSize(16);
        app.text("Power: " + app.CurrentPlayer.power, WIDTH/2 - 3*CELLSIZE, 2*CELLSIZE); 
    }

    public static void displayHealthBar(App app){
        app.fill(0);
        app.textSize(16);
        app.text("Health: " + app.CurrentPlayer.health, WIDTH/2 - 3*CELLSIZE, CELLSIZE); 
        app.rect(WIDTH/2, CELLSIZE/2 - 1, 150, 25);

    }

}
