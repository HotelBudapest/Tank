package Tanks;

public class HUD extends App{

    static int displayIndex = 0;
    static long lastUpdateTime = 0;
    public static boolean started = false;

    public static void displayTEXTS(App app){
        app.pushStyle();
        app.fill(0);
        app.textSize(16);
        app.text("Player " + app.CurrentPlayer.type + "'s Turn", 13, CELLSIZE);
        app.popStyle();

        app.pushStyle();
        app.fill(0);
        app.textSize(16);
        app.text("Power: " + app.CurrentPlayer.power, WIDTH/2 - 3*CELLSIZE, 2*CELLSIZE); 
        app.popStyle();

        app.pushStyle();
        app.image(app.fuelIMG,  WIDTH/2 - 8*CELLSIZE, 10, 25, 25);
        app.popStyle();

        app.pushStyle();
        app.fill(0);
        app.textSize(16);
        app.text(app.CurrentPlayer.fuel, WIDTH/2 - 7*CELLSIZE, CELLSIZE);
        app.popStyle();
        
        // app.pushStyle();
        // app.fill(0);
        // app.textSize(16);
        // app.text("Parachutes: " + app.CurrentPlayer.parachutesLeft, WIDTH/2 - 3*CELLSIZE, 5*CELLSIZE);
        // app.popStyle();

        app.pushStyle();
        app.image(parachuteIMG, WIDTH/2 - 8*CELLSIZE, CELLSIZE + 15, 25, 25);
        app.fill(0);
        app.textSize(16);
        app.text(app.CurrentPlayer.parachutesLeft, WIDTH/2 - 7*CELLSIZE, CELLSIZE + 35);
        app.popStyle();

        app.pushStyle();
        app.fill(0, 0, 230, 90);
        app.ellipse(WIDTH/2 - 8*CELLSIZE + 12, CELLSIZE + 55, 15, 15);
        app.fill(0);
        app.textSize(16);
        app.text(app.CurrentPlayer.shield, WIDTH/2 - 7*CELLSIZE, CELLSIZE + 62);
        app.popStyle();

        if (app.Wind >= 0){
            app.pushStyle();
            app.image(app.Wind1, WIDTH - 3*CELLSIZE, 10, 65, 65);
            app.fill(0);
            app.textSize(16);
            app.text(app.Wind, WIDTH - 30, CELLSIZE + 16);
            app.popStyle();
        }
        else{
            app.pushStyle();
            app.image(app.Wind2, WIDTH - 3*CELLSIZE, 10, 65, 65);
            app.fill(0);
            app.textSize(16);
            app.text(app.Wind, WIDTH - 30, CELLSIZE + 16);
            app.popStyle();
        }

        
    }

    public static void displayHealthBar(App app){
        app.pushStyle();
        app.fill(0);
        app.textSize(16);
        app.text("Health: " + app.CurrentPlayer.health, WIDTH/2 - 3*CELLSIZE, CELLSIZE); 
        app.popStyle();

        app.pushStyle();
        app.stroke(0);
        app.fill(255);
        app.rect(WIDTH/2, CELLSIZE/2 - 1, 150, 25);
        app.stroke(0);
        app.fill(app.CurrentPlayer.color[0], app.CurrentPlayer.color[1], app.CurrentPlayer.color[2]);
        app.rect(WIDTH/2, CELLSIZE/2 - 1, 1.5f * app.CurrentPlayer.health, 25);

        app.stroke(200);
        app.fill(app.CurrentPlayer.color[0], app.CurrentPlayer.color[1], app.CurrentPlayer.color[2]);
        app.rect(WIDTH/2, CELLSIZE/2 - 1, 1.5f * app.CurrentPlayer.power, 25);
        app.popStyle();
        
    }

    public static void displayScoreBoard(App app){
        app.pushStyle();
        app.stroke(0);
        app.fill(0, 0, 0, 0);
        app.rect(WIDTH - 6*CELLSIZE, 3*CELLSIZE,  180, 35);
        app.fill(0);
        app.textSize(25);
        app.text("Scores", WIDTH - 6*CELLSIZE + 15, 3*CELLSIZE + 25); 
        app.stroke(0);
        app.fill(0, 0, 0, 0);
        app.rect(WIDTH - 6*CELLSIZE, 4*CELLSIZE,  180, 110);
        int j = 25;
        for (int i = 0; i < app.playingOnBoard.size(); i ++){
            Player current = app.playingOnBoard.get(i);
            app.fill(current.color[0], current.color[1], current.color[2]);
            app.textSize(15);
            app.text("Player "+ current.type, WIDTH - 6*CELLSIZE + 15, 4*CELLSIZE + j); 
            app.fill(0);
            app.textSize(15);
            app.text(current.score, WIDTH - 2*CELLSIZE + 15, 4*CELLSIZE + j); 
            j+=25;
        }
        app.popStyle();
    }

    public static void displayEndGame(App app, int i){
        Player current = app.backupForEndGame.get(i);
        app.fill(current.color[0], current.color[1], current.color[2]);
        app.textSize(40);
        app.text("Player " + current.type, WIDTH/2- 6*CELLSIZE , HEIGHT/2 + (i) * 2 * CELLSIZE - 15);
        app.fill(0);
        app.textSize(40);
        app.text(current.score, WIDTH/2 + 4*CELLSIZE, HEIGHT/2 + (i) *  2 * CELLSIZE - 15);

        // long time = app.millis();
        // if (time%1000 == 0){

        // }
        
        }
    }

