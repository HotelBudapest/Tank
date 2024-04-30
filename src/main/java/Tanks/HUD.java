package Tanks;

import processing.core.PApplet;

public class HUD extends App{

    public void display(App app){
        if (CurrentPlayer != null){
            app.fill(0);
            app.textSize(20);
            app.text("Player " + CurrentPlayer.type + "'s Turn", CELLSIZE, CELLSIZE);
        }
    }

}
