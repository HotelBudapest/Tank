package Tanks;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;

public class Terrain  extends App{

    public int[][] arr = new int[32][32];
    ArrayList<PVector> terrainCoordinates = new ArrayList<PVector>();

    public Terrain(int[][] arr, ArrayList<PVector> points){
        this.arr = arr;
        this.terrainCoordinates = points;
    }

    public void smoothArray(){
        int[][] terrainArray = this.arr;
        int numRows = terrainArray.length;
        int numCols = terrainArray[0].length;
        int[] startPoints = new int[numCols];

        // Step 1: Find the terrain start points for each column
        for (int col = 0; col < numCols; col++) {
            startPoints[col] = numRows; // Initialize with max value, assuming no terrain in this column
            for (int row = 0; row < numRows; row++) {
                if (terrainArray[row][col] == 1) {
                    startPoints[col] = row;
                    break;
                }
            }
        }

        // Step 2: Apply a simple moving average on the start points
        int[] smoothedStartPoints = new int[numCols];
        for (int col = 0; col < numCols; col++) {
            int sum = startPoints[col];
            int count = 1;
            
            // Include left neighbor
            if (col > 3) {
                sum += startPoints[col - 1];
                count++;
                sum += startPoints[col - 2];
                count++;
                sum += startPoints[col - 3];
                count++;
                sum += startPoints[col - 4];
                count++;
            }
            
            
            // Include right neighbor
            if (col < numCols - 4) {
                sum += startPoints[col + 1];
                count++;
                sum += startPoints[col + 2];
                count++;
                sum += startPoints[col + 3];
                count++;
            }
            
            smoothedStartPoints[col] = sum / count;
        }

        // Step 3: Adjust the terrain array based on the smoothed start points
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                if (row >= smoothedStartPoints[col]) {
                    terrainArray[row][col] = 1; // Terrain
                } else {
                    terrainArray[row][col] = 0; // No terrain
                }
            }
        }

        // Now, terrainArray contains your smoothed terrain
        this.arr = terrainArray;

    }

    public void draw(PApplet app){
        app.vertex(0, app.height);
        for (PVector coord : this.terrainCoordinates) {
            app.vertex(coord.x * CELLSIZE, coord.y * CELLSIZE);
          }
        app.vertex(app.width, app.height);
    }
    
}
